package org.mmocore.gameserver.model.entity.events.impl;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.model.Effect;
import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.model.entity.events.objects.CustomPlayerSnapshotObject;
import org.mmocore.gameserver.model.entity.olympiad.CompType;
import org.mmocore.gameserver.model.zone.ZoneType;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchFirecracker;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchManage;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchMessage;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchScore;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchUserInfo;
import org.mmocore.gameserver.network.lineage.serverpackets.PlaySound;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.scripts.npc.model.events.CustomObservationManagerInstance;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.world.World;

import java.time.Instant;
import java.util.List;

public class TournamentGvGBattleEvent extends AbstractCustomBattleEvent
{
	private boolean _isPaused = false;

	public TournamentGvGBattleEvent(MultiValueSet<String> set)
	{
		super(set);
	}

	protected TournamentGvGBattleEvent(int id, TournamentGvGEvent parent, String player1, String player2)
	{
		super(id, CompType.TEAM.ordinal(), parent, player1, player2);
		callActions(onInitActions);
	}

	@Override
	protected Instant startTime()
	{
		return Instant.now().plusSeconds(120);
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
		if(_blueScore > _redScore)
			_winner = TeamType.BLUE;
		else if(_redScore > _blueScore)
			_winner = TeamType.RED;
		else _winner = TeamType.NONE;

		final String player1 = getTeamName1();
		final String player2 = getTeamName2();
		String winner = null;
		switch(_winner)
		{
			case BLUE:
				winner = player1;
				break;
			case RED:
				winner = player2;
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

		List<CustomPlayerSnapshotObject> admins = getObjects(TeamType.NONE);
		for (CustomPlayerSnapshotObject s : admins)
			if(s.getPlayer()!=null)
				s.removeEvent(this);
		_state = State.STARTED;

		super.stopEvent(); // после вывода результатов, там очистка команд
	}

	protected void startNextRound(boolean nextRound, boolean clear)
	{
		_redRes = 0;
		_blueRes = 0;
		for (CustomPlayerSnapshotObject s : this)
			if (s.getPlayer()!= null) {
				if(clear)
					s.preRound();
				s.heal();
			}
		if(!nextRound){
			CustomObservationManagerInstance.broadcast(new CustomMessage("CustomGvGBattleEvent.BattleBegin").addNumber(getArenaId() + 1).addString(getTeamName1()).addString(getTeamName2()));
			return;
		}
		clearActions();
		callActions(onInitActions);

	}

	@Override
	public void onEffectIconsUpdate(Player player, Effect[] effects)
	{
		if (_state == State.NONE)
			return;

		super.onEffectIconsUpdate(player, effects);
	}

	@Override
	public void onStatusUpdate(Player player)
	{
		if (_state == State.NONE)
			return;

		sendPacketToObservers(new ExEventMatchUserInfo(player));
	}

	@Override
	public void teleportPlayers(final String name, final ZoneType zoneType)
	{
		super.teleportPlayers(name, zoneType);

		if(name.equalsIgnoreCase("participants"))
			return;

		Location loc = getReflection().getInstancedZone().getTeleportCoords().get(2);
		List<CustomPlayerSnapshotObject> team = getObjects(TeamType.NONE);
		for (CustomPlayerSnapshotObject m : team)
			m.teleport(loc, getReflection());
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

	}

	protected void message(String msg){
		//0 - msg, 1-finish, 2-start, 3 - game over, 4 - "1", 5 - "2", 6 - "3", 7 - "4", 8 - "5"
		sendPacket(new ExEventMatchMessage(0, msg));
	}

	@Override
	protected boolean checkWinnerInProgress()
	{
		return false;
	}

	protected void setScore(int blue, int red)
	{
		_blueScore = blue;
		_redScore = red;
		sendPacket(new ExEventMatchScore(_blueScore, _redScore));
	}

	protected void chooseWinner()
	{
		TeamType winner = TeamType.NONE;
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

		if (allBlueDead || allRedDead)
		{
			if (allBlueDead && allRedDead)
				winner = TeamType.NONE;
			else
				winner = allBlueDead ? TeamType.RED : TeamType.BLUE;
		}

		if (allBlueExited || allRedExited)
		{
			if (allBlueExited && allRedExited)
				winner = TeamType.NONE;
			else
				winner = allBlueExited ? TeamType.RED : TeamType.BLUE;
		}

		if(winner == TeamType.RED)
			_redScore++;
		else if(winner == TeamType.BLUE)
			_blueScore++;

		for (CustomPlayerSnapshotObject s : redTeam)
			if(s.getPlayer()!=null && winner == TeamType.RED) {
				s.getPlayer().sendPacket(new ExEventMatchMessage(1));
				sendPacket(new ExEventMatchFirecracker(s.getObjectId()));
			}
			else if(s.getPlayer()!=null && winner == TeamType.BLUE)
				s.getPlayer().sendPacket(new ExEventMatchMessage(3));

		for (CustomPlayerSnapshotObject s : blueTeam)
			if(s.getPlayer()!=null && winner == TeamType.BLUE) {
				s.getPlayer().sendPacket(new ExEventMatchMessage(1));
				sendPacket(new ExEventMatchFirecracker(s.getObjectId()));
			}
			else if(s.getPlayer()!=null && winner == TeamType.RED)
				s.getPlayer().sendPacket(new ExEventMatchMessage(3));


		List<CustomPlayerSnapshotObject> admins = getObjects(TeamType.NONE);
		for (CustomPlayerSnapshotObject s : admins)
			if(s.getPlayer()!=null)
				s.getPlayer().sendPacket(new ExEventMatchMessage(1));

		sendPacketToObservers(new ExEventMatchMessage(1));

		sendPacketToObservers(new ExEventMatchScore(_blueScore, _redScore));

		clearActions();
		_state = State.FINISHED;
	}

	public boolean isPaused(){
		return _isPaused;
	}

	public int getMenuState(){
		if(_isPaused)
			return 3;
		if(_state == State.STARTED || _state == State.TELEPORT_PLAYERS )
			return 2;
		return 1;
	}

	public int getMenuTeamState(TeamType team){
		if(getObjects(team).isEmpty())
			return 0;
		return 1;
	}

	public void setMenuPlayers(ExEventMatchManage packet){

		List<CustomPlayerSnapshotObject> team;
		team = getObjects(TeamType.BLUE);
		for (CustomPlayerSnapshotObject s : team)
		{
			packet.addPlayer(s.getTeam().ordinal(), s.getObjectId(), s.getMatchRecord().name, s.getPlayer()==null? s.getClassId() : s.getPlayer().getClassId(), s.getLevel(), s.isLeader());
		}

		team = getObjects(TeamType.RED);
		for (CustomPlayerSnapshotObject s : team)
		{
			packet.addPlayer(s.getTeam().ordinal(), s.getObjectId(), s.getMatchRecord().name, s.getPlayer()==null? s.getClassId() : s.getPlayer().getClassId(), s.getLevel(), s.isLeader());
		}
	}

	public void handleFirecracker(Player activeChar, String player)
	{
		if(player != null)
		{
			Player obj = World.getPlayer(player);
			if(obj != null)
				sendPacket(new ExEventMatchFirecracker(obj.getObjectId()));
		}
	}

	public void pause(boolean pause){
		if (_isPaused == pause)
			return;
		if(pause) {
			pauseActions();
		}
		else
			resumeActions();

		blockPlayers(pause);
		_isPaused = pause;
	}

	private void blockPlayers(boolean block){
		List<CustomPlayerSnapshotObject> team;
		team = getObjects(TeamType.BLUE);
		for (CustomPlayerSnapshotObject s : team)
			if(s.getPlayer()!=null) {
				if (block)
					s.getPlayer().startFrozen();
				else
					s.getPlayer().stopFrozen();
			}

		team = getObjects(TeamType.RED);
		for (CustomPlayerSnapshotObject s : team)
			if(s.getPlayer()!=null) {
				if (block)
					s.getPlayer().startFrozen();
				else
					s.getPlayer().stopFrozen();
			}
	}
}