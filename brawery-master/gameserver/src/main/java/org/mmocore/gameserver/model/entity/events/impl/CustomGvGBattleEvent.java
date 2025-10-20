package org.mmocore.gameserver.model.entity.events.impl;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.listener.actor.player.OnAnswerListener;
import org.mmocore.gameserver.model.Effect;
import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.model.entity.events.objects.CustomPlayerSnapshotObject;
import org.mmocore.gameserver.model.entity.olympiad.CompType;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ConfirmDlg;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchFirecracker;
import org.mmocore.gameserver.network.lineage.serverpackets.ExPVPMatchRecord;
import org.mmocore.gameserver.network.lineage.serverpackets.ExPVPMatchRecord.Member;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchMessage;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchScore;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchUserInfo;
import org.mmocore.gameserver.network.lineage.serverpackets.ExPVPMatchUserDie;
import org.mmocore.gameserver.network.lineage.serverpackets.PlaySound;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.scripts.npc.model.events.CustomObservationManagerInstance;
import org.mmocore.gameserver.utils.Language;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class CustomGvGBattleEvent extends AbstractCustomBattleEvent
{
	private int _blueKills = 0;
	private int _redKills = 0;
	private int _blueDeadTimer = 0;
	private int _redDeadTimer = 0;
	private boolean _nextRound = false;
	private Future<?> _stopTask;
	private int _answerValue = 0;

	public CustomGvGBattleEvent(MultiValueSet<String> set)
	{
		super(set);
	}

	protected CustomGvGBattleEvent(int id, CustomGvGEvent parent, String player1, String player2)
	{
		super(id, CompType.TEAM.ordinal(), parent, player1, player2);
		CustomObservationManagerInstance.broadcast(new CustomMessage("CustomGvGBattleEvent.BattleBegin").addNumber(getArenaId() + 1).addString(player1).addString(player2));
	}

	@Override
	protected Instant startTime()
	{
		return Instant.now().plusSeconds(60);
	}

	@Override
	public void startEvent()
	{
		super.startEvent();

		sendPackets(PlaySound.B04_S01, new ExEventMatchMessage(2));
	}

	@Override
	public void stopEvent()
	{
		if (_state != State.STARTED)
			return;

		checkWinner();

		final String player1 = getTeamName1();
		final String player2 = getTeamName2();
		String winner = null;
		switch(_winner)
		{
			case BLUE:
				winner = player1;
				_blueScore++;
				break;
			case RED:
				winner = player2;
				_redScore++;
				break;
		}
		if(winner != null)
		{
			sendPackets(new SystemMessage(SystemMsg.C1S_PARTY_HAS_WON_THE_DUEL).addString(winner));
			CustomObservationManagerInstance.broadcast(new CustomMessage("CustomGvGBattleEvent.BattleEnd").addString(player1).addString(player2).addString(winner));			
		}
		else
		{
			sendPackets(SystemMsg.THE_DUEL_HAS_ENDED_IN_A_TIE);
			CustomObservationManagerInstance.broadcast(new CustomMessage("CustomGvGBattleEvent.BattleTie").addString(player1).addString(player2));						
		}
		sendPacket(new ExEventMatchScore(_blueScore, _redScore));


		if(_nextRound) {
			preNextRound();
		}
		else {
			// отсылка START нужна для очистки предыдущих результатов.
			broadcastRecord(ExPVPMatchRecord.START, TeamType.NONE);
			broadcastRecord(ExPVPMatchRecord.FINISH, _blueScore > _redScore ? TeamType.BLUE : _redScore > _blueScore ? TeamType.RED : TeamType.NONE);
			super.stopEvent(); // после вывода результатов, там очистка команд
		}
	}

	private void preNextRound()
	{
		_blueKills = 0;
		_redKills = 0;
		_blueDeadTimer = 0;
		_redDeadTimer = 0;
		_redRes = 0;
		_blueRes = 0;

		if (_winnerCheckTask != null)
		{
			_winnerCheckTask.cancel(false);
			_winnerCheckTask = null;
		}

		if(_stopTask != null)
		{
			_stopTask.cancel(false);
			_stopTask = null;
		}
		_stopTask = ThreadPoolManager.getInstance().schedule(new RunnableImpl()
		{
			@Override
			public void runImpl() throws Exception
			{
				// отсылка START нужна для очистки предыдущих результатов.
				broadcastRecord(ExPVPMatchRecord.START, TeamType.NONE);
				broadcastRecord(ExPVPMatchRecord.FINISH, _blueScore > _redScore ? TeamType.BLUE : _redScore > _blueScore ? TeamType.RED : TeamType.NONE);
				CustomGvGBattleEvent.super.stopEvent();
			}
		}, 15000L);

		_answerValue = 0;
		String message = null;
		List<CustomPlayerSnapshotObject> team;
		Player player;
		team = getObjects(TeamType.BLUE);
		if((player = team.get(0).getPlayer())!=null && player.getParty()!=null)
			player = player.getParty().getGroupLeader();

		if(player!=null){
			message = player.getLanguage() == Language.RUSSIAN ?"Начать переигровку матча?":"Do you want to start re-match?";
			if(message != null)
				player.ask(new ConfirmDlg(SystemMsg.S1, 15000).addString(message), new ActionAnswerListener(this, 1));
		}

		team = getObjects(TeamType.RED);
		if((player = team.get(0).getPlayer())!=null && player.getParty()!=null)
			player = player.getParty().getGroupLeader();

		if(player!=null){
			message = player.getLanguage() == Language.RUSSIAN ?"Начать переигровку матча?":"Do you want to start re-match?";
			if(message != null)
				player.ask(new ConfirmDlg(SystemMsg.S1, 15000).addString(message), new ActionAnswerListener(this, 2));
		}

	}

	private class ActionAnswerListener implements OnAnswerListener
	{
		private final CustomGvGBattleEvent event;
		private final int team;

		private ActionAnswerListener(CustomGvGBattleEvent event, int team)
		{
			this.event = event;
			this.team = team;
		}

		@Override
		public void sayYes()
		{
			if(team == 1)// Blue
			{
				event._answerValue++;
			}
			else if(team == 2)// Red
			{
				event._answerValue++;
			}

			if(event._answerValue > 1 && event._stopTask != null)
			{
				event._stopTask.cancel(false);
				event._stopTask = null;
				event.startNextRound();
			}
		}

		@Override
		public void sayNo() {
		}

	}

	private void startNextRound()
	{
		for (CustomPlayerSnapshotObject s : this)
			if (s.getPlayer()!= null)
				s.preRound();

		clearActions();
		reCalcNextTime(false);
	}

	@Override
	public void onEffectIconsUpdate(Player player, Effect[] effects)
	{
		if (_state != State.STARTED)
			return;

		super.onEffectIconsUpdate(player, effects);
	}

	@Override
	public void onStatusUpdate(Player player)
	{
		if (_state != State.STARTED && _state != State.TELEPORT_PLAYERS)
			return;

		sendPacketToObservers(new ExEventMatchUserInfo(player));
	}

	@Override
	public void announce(int i)
	{
		switch (i){
			case 5:
				sendPackets(new ExEventMatchMessage(8));
				return;
			case 4:
				sendPackets(new ExEventMatchMessage(7));
				return;
			case 3:
				sendPackets(new ExEventMatchMessage(6));
				return;
			case 2:
				sendPackets(new ExEventMatchMessage(5));
				return;
			case 1:
				sendPackets(new ExEventMatchMessage(4));
				return;
			default:
				sendPackets(new SystemMessage(SystemMsg.THE_DUEL_WILL_BEGIN_IN_S1_SECONDS).addNumber(i));
		}
	}

	@Override
	public void onDie(Player player)
	{
		switch (player.getTeam())
		{
			case NONE:
				return;
			case BLUE:
				_redKills++;
				break;
			case RED:
				_blueKills++;
				break;
		}

//		sendPacket(new ExPVPMatchUserDie(_blueKills, _redKills));
	}

	private void broadcastRecord(int type, TeamType winner)
	{
		List<Member> blueTeam;
		List<Member> redTeam;
		List<CustomPlayerSnapshotObject> team;

		team = getObjects(TeamType.BLUE);
		blueTeam = new ArrayList<Member>(team.size());
		for (CustomPlayerSnapshotObject s : team)
			blueTeam.add(s.getMatchRecord());

		team = getObjects(TeamType.RED);
		redTeam = new ArrayList<Member>(team.size());
		for (CustomPlayerSnapshotObject s : team)
			redTeam.add(s.getMatchRecord());

		if (type == ExPVPMatchRecord.UPDATE)
			sendPackets(new ExPVPMatchRecord(type, winner, _blueScore, _redScore, blueTeam, redTeam), new ExPVPMatchUserDie(_blueScore, _redScore));
		else
			sendPacket(new ExPVPMatchRecord(type, winner, _blueScore, _redScore, blueTeam, redTeam));
	}

	@Override
	protected boolean checkWinnerInProgress()
	{
		if (!isInProgress())
			return false;

		boolean allBlueDead = true;
		boolean allRedDead = true;
		boolean allBlueExited = true;
		boolean allRedExited = true;
		List<CustomPlayerSnapshotObject> blueTeam;

		blueTeam = getObjects(TeamType.BLUE);
		for (CustomPlayerSnapshotObject s : blueTeam)
		{
			if (!s.isDead())
				allBlueDead = false;
			if (!s.isExited())
				allBlueExited = false;
		}

		List<CustomPlayerSnapshotObject> redTeam;
		redTeam = getObjects(TeamType.RED);
		for (CustomPlayerSnapshotObject s : redTeam)
		{
			if (!s.isDead())
				allRedDead = false;
			if (!s.isExited())
				allRedExited = false;
		}

		if (allBlueExited || allRedExited)
		{
			if (allBlueExited && allRedExited)
				_winner = TeamType.NONE;
			else
				_winner = allBlueExited ? TeamType.RED : TeamType.BLUE;

			return true;
		}

		if (allBlueDead)
			_blueDeadTimer++;
		else
			_blueDeadTimer = 0;

		if (allRedDead)
			_redDeadTimer++;
		else
			_redDeadTimer = 0;

		if (_blueDeadTimer > 6 || _redDeadTimer > 6)
			sendPacket(new ExEventMatchMessage(6));
		if (_blueDeadTimer > 7 || _redDeadTimer > 7)
			sendPacket(new ExEventMatchMessage(5));
		if (_blueDeadTimer > 8 || _redDeadTimer > 8)
			sendPacket(new ExEventMatchMessage(4));
		if (_blueDeadTimer > 9 || _redDeadTimer > 9) {
				for (CustomPlayerSnapshotObject s : redTeam)
					if(s.getPlayer()!=null && _blueDeadTimer > 9) {
						s.getPlayer().sendPacket(new ExEventMatchMessage(1));
						sendPacket(new ExEventMatchFirecracker(s.getObjectId()));
					}
					else if(s.getPlayer()!=null && _redDeadTimer > 9)
						s.getPlayer().sendPacket(new ExEventMatchMessage(3));

				for (CustomPlayerSnapshotObject s : blueTeam)
					if(s.getPlayer()!=null && _redDeadTimer > 9) {
						s.getPlayer().sendPacket(new ExEventMatchMessage(1));
						sendPacket(new ExEventMatchFirecracker(s.getObjectId()));
					}
					else if(s.getPlayer()!=null && _blueDeadTimer > 9)
						s.getPlayer().sendPacket(new ExEventMatchMessage(3));

				sendPacketToObservers(new ExEventMatchMessage(1));
			}

		if (_blueDeadTimer > 10 || _redDeadTimer > 10)
		{
			if (_blueDeadTimer != _redDeadTimer)
				_winner = (_blueDeadTimer > _redDeadTimer) ? TeamType.RED : TeamType.BLUE;
			else
				_winner = TeamType.NONE;
			_nextRound = true;
			return true;
		}

		return false;
	}

	private void checkWinner()
	{
		if (_winner != TeamType.NONE)
			return;

		boolean aliveBlue = false;
		boolean aliveRed = false;
		List<CustomPlayerSnapshotObject> team;

		team = getObjects(TeamType.BLUE);
		for (CustomPlayerSnapshotObject s : team)
			if (!s.isDead())
				aliveBlue = true;

		team = getObjects(TeamType.RED);
		for (CustomPlayerSnapshotObject s : team)
			if (!s.isDead())
				aliveRed = true;

		if (aliveBlue == aliveRed) // все умерли или все живы
		{
			if (_blueKills == _redKills) // одинаково фрагов - ничья
				_winner = TeamType.NONE;
			else
				_winner = _blueKills > _redKills ? TeamType.BLUE : TeamType.RED;
		}
		else
			_winner = aliveRed ? TeamType.RED : TeamType.BLUE; // одна из команд полностью мертвая
	}
}