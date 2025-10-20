package org.mmocore.gameserver.model.entity.events.impl.fightclub;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.model.Effect;
import org.mmocore.gameserver.model.Skill;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubTeam;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.network.lineage.serverpackets.SkillCoolTime;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Playable;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.skills.AbnormalEffect;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KoreanStyleEvent extends AbstractFightClub
{
	private static final long MAX_FIGHT_TIME = 90000L;
	protected final FightClubPlayer[] _fightingPlayers;
	private final int[] lastTeamChosenSpawn;
	protected long _lastKill;

	public KoreanStyleEvent(MultiValueSet<String> set)
	{
		super(set);
		_lastKill = 0L;
		_fightingPlayers = new FightClubPlayer[2];
		lastTeamChosenSpawn = new int[]
		{
			0,
			0
		};
	}

	@Override
	public void onKilled(Creature actor, Creature victim)
	{
		if (actor != null && actor.isPlayable())
		{
			FightClubPlayer realActor = getFightClubPlayer(actor.getPlayer());
			if (victim.isPlayer() && realActor != null)
			{
				realActor.increaseKills(true);
				updatePlayerScore(realActor);
				sendMessageToPlayer(realActor, MESSAGE_TYPES.GM, (realActor.getPlayer().getLanguage() == Language.RUSSIAN ?"Вы убили ":"You have killed ") + victim.getName());
			}
			actor.getPlayer().sendUserInfo();
		}

		if (victim.isPlayer())
		{
			FightClubPlayer realVictim = getFightClubPlayer(victim);
			realVictim.increaseDeaths();
			if (actor != null)
				sendMessageToPlayer(realVictim, MESSAGE_TYPES.GM, (realVictim.getPlayer().getLanguage() == Language.RUSSIAN ?"Вас убил ":"You have been killed by ") + actor.getName());
			victim.broadcastCharInfo();

			_lastKill = System.currentTimeMillis();
		}
		checkFightingPlayers();
		super.onKilled(actor, victim);
	}

	@Override
	public void loggedOut(Player player)
	{
		super.loggedOut(player);
		for (FightClubPlayer fPlayer : _fightingPlayers)
		{
			if (fPlayer != null && fPlayer.getPlayer() != null && fPlayer.getPlayer().equals(player))
				checkFightingPlayers();
		}
	}

	@Override
	public boolean leaveEvent(Player player, boolean teleportTown)
	{
		super.leaveEvent(player, teleportTown);
		try
		{
			if (player.isRooted())
				player.stopRooted();
		}
		catch (IllegalStateException e)
		{
		}
		player.stopAbnormalEffect(AbnormalEffect.ROOT);
		if (getState() != EVENT_STATE.STARTED)
			return true;
		for (FightClubPlayer fPlayer : _fightingPlayers)
		{
			if (fPlayer != null && fPlayer.getPlayer() != null && fPlayer.getPlayer().equals(player))
				checkFightingPlayers();
		}
		return true;
	}

	@Override
	public void startEvent()
	{
		super.startEvent();
		for (FightClubPlayer fPlayer : getPlayers(FIGHTING_PLAYERS, REGISTERED_PLAYERS))
		{
			Player player = fPlayer.getPlayer();
			if (player.isDead())
				player.doRevive();
			if (player.isFakeDeath())
				player.setFakeDeath(false);
			player.sitDown(null);
		}
	}

	@Override
	public void startRound()
	{
		super.startRound();
		checkFightingPlayers();
		_lastKill = System.currentTimeMillis();
		ThreadPoolManager.getInstance().schedule(new CheckFightersInactive(this), 5000L);
	}

	@Override
	public void endRound()
	{
		super.endRound();
		super.unrootPlayers();
	}

	private void checkFightingPlayers()
	{
		if (getState() == EVENT_STATE.OVER || getState() == EVENT_STATE.NOT_ACTIVE)
			return;
		boolean changed = false;
		for (int i = 0; i < _fightingPlayers.length; i++)
		{
			FightClubPlayer oldPlayer = _fightingPlayers[i];
			if (oldPlayer == null || !isPlayerActive(oldPlayer.getPlayer()) || getFightClubPlayer(oldPlayer.getPlayer()) == null)
			{
				if (oldPlayer != null && !oldPlayer.getPlayer().isDead())
				{
					oldPlayer.getPlayer().doDie(null);
					return;
				}
				FightClubPlayer newPlayer = chooseNewPlayer(i + 1);
				if (newPlayer == null)
				{
					for (FightClubTeam team : getTeams())
					{
						if (team.getIndex() != (i + 1))
							team.incScore(1);
					}
					endRound();
					return;
				}
				newPlayer.getPlayer().setLastActiveTime();
				_fightingPlayers[i] = newPlayer;
				changed = true;
			}
		}

		if (changed)
		{
			StringBuilder msg = new StringBuilder();
			for (int i = 0; i < _fightingPlayers.length; i++)
			{
				if (i > 0)
					msg.append(" VS ");
				msg.append(_fightingPlayers[i].getPlayer().getName());
			}
			sendMessageToFighting(MESSAGE_TYPES.SCREEN_BIG, msg.toString(), false);
			preparePlayers();
		}
	}

	private FightClubPlayer chooseNewPlayer(int teamIndex)
	{
		List<FightClubPlayer> alivePlayersFromTeam = new ArrayList<FightClubPlayer>();
		for (FightClubPlayer fPlayer : getPlayers(FIGHTING_PLAYERS))
		{
			if (fPlayer.getPlayer().isSitting() && fPlayer.getTeam().getIndex() == teamIndex)
			{
				alivePlayersFromTeam.add(fPlayer);
			}
		}

		if (alivePlayersFromTeam.isEmpty())
			return null;
		if (alivePlayersFromTeam.size() == 1)
			return alivePlayersFromTeam.get(0);
		return Rnd.get(alivePlayersFromTeam);
	}

	private void preparePlayers()
	{
		for (int i = 0; i < _fightingPlayers.length; i++)
		{
			FightClubPlayer fPlayer = _fightingPlayers[i];
			Player player = fPlayer.getPlayer();
			try
			{
				if (player.isBlocked())
					player.unblock();
				if (player.isRooted())
					player.stopRooted();
			}
			catch (IllegalStateException e)
			{

			}
			player.stopAbnormalEffect(AbnormalEffect.ROOT);
			player.standUp();
			player.setLastActiveTime();
			player.resetReuse();
			player.sendPacket(new SkillCoolTime(player));
			healFull(player);
			if (player.getServitor() != null && !player.getServitor().isDead())
				healFull(player.getServitor());
			Location loc = getMap().getKeyLocations()[i];
			player.teleToLocation(loc, getReflection());
		}
	}

	private static void healFull(Playable playable)
	{
		cleanse(playable);
		playable.setCurrentHp(playable.getMaxHp(), false);
		playable.setCurrentMp(playable.getMaxMp());
		playable.setCurrentCp(playable.getMaxCp());
	}

	private static void cleanse(Playable playable)
	{
		try
		{
			for (Effect e : playable.getEffectList().getAllEffects())
			{
				if (e.isOffensive() && e.isCancelable())
					e.exit();
			}
		}
		catch (IllegalStateException e)
		{
		}
	}

	@Override
	public boolean canAttack(Creature target, Creature attacker, Skill skill, boolean force, boolean nextAttackCheck)
	{
		if (getState() != EVENT_STATE.STARTED)
			return false;
		if (target == null || !target.isPlayable() || attacker == null || !attacker.isPlayable())
			return false;
		if (isFighting(target) && isFighting(attacker))
			return true;
		return false;
	}

	private boolean isFighting(Creature actor)
	{
		for (FightClubPlayer fPlayer : _fightingPlayers)
		{
			if (fPlayer != null && fPlayer.getPlayer() != null && fPlayer.getPlayer().equals(actor.getPlayer()))
				return true;
		}
		return false;
	}

	protected static class CheckFightersInactive implements Runnable
	{
		private final KoreanStyleEvent _fightClub;

		public CheckFightersInactive(KoreanStyleEvent fightClub)
		{
			_fightClub = fightClub;
		}

		@Override
		public void run()
		{
			if (_fightClub.getState() != EVENT_STATE.STARTED)
				return;

			if (_fightClub._lastKill + MAX_FIGHT_TIME < System.currentTimeMillis())
			{
				double lowerHp = Double.MAX_VALUE;
				Player playerToKill = null;
				for (FightClubPlayer fPlayer : _fightClub._fightingPlayers)
				{
					if (fPlayer != null && fPlayer.getPlayer() != null)
					{
						if (!fPlayer.getPlayer().getNetConnection().isConnected())
						{
							playerToKill = fPlayer.getPlayer();
							fPlayer.addTotalAfkSeconds((int)_fightClub._badgeWin * 60);
							lowerHp = -100.0;
						}
						else if (System.currentTimeMillis() - fPlayer.getPlayer().getLastActiveTime() > 8000L)
						{
							playerToKill = fPlayer.getPlayer();
							fPlayer.addTotalAfkSeconds((int)_fightClub._badgeWin * 60);
							lowerHp = -100.0;
						}
						else if (fPlayer.getPlayer().getCurrentHpPercents() < lowerHp)
						{
							playerToKill = fPlayer.getPlayer();
							lowerHp = fPlayer.getPlayer().getCurrentHpPercents();
						}
					}
				}

				if (playerToKill != null)
					playerToKill.doDie(null);
			}

			ThreadPoolManager.getInstance().schedule(this, 5000L);
		}
	}

	@Override
	protected Location getSinglePlayerSpawnLocation(FightClubPlayer fPlayer)
	{
		Location[] spawnLocations = getMap().getTeamSpawns().get(fPlayer.getTeam().getIndex());
		int ordinalTeamIndex = fPlayer.getTeam().getIndex() - 1;
		int lastSpawnIndex = lastTeamChosenSpawn[ordinalTeamIndex];
		lastSpawnIndex++;
		if (lastSpawnIndex >= spawnLocations.length)
			lastSpawnIndex = 0;
		lastTeamChosenSpawn[ordinalTeamIndex] = lastSpawnIndex;
		return spawnLocations[lastSpawnIndex];
	}

	@Override
	protected int getRewardForWinningTeam(FightClubPlayer fPlayer, boolean atLeast1Kill)
	{
		return super.getRewardForWinningTeam(fPlayer, false);
	}

	@Override
	protected void handleAfk(FightClubPlayer fPlayer, boolean setAsAfk)
	{
	}

	@Override
	protected void unrootPlayers()
	{
	}
	@Override
	protected void healOnStartRound(){}

	@Override
	protected boolean inScreenShowBeScoreNotKills()
	{
		return false;
	}

	@Override
	protected boolean inScreenShowBeTeamNotInvidual()
	{
		return false;
	}

	@Override
	protected boolean isAfkTimerStopped(Player player)
	{
		return player.isSitting() || super.isAfkTimerStopped(player);
	}

	@Override
	public boolean canStandUp(Player player)
	{
		for (FightClubPlayer fPlayer : _fightingPlayers)
		{
			if (fPlayer != null && fPlayer.getPlayer().equals(player) && !player.isRooted())
				return true;
		}
		return false;
	}

	@Override
	protected List<List<Player>> spreadTeamInPartys(FightClubTeam team)
	{
		return Collections.emptyList();
	}

	@Override
	protected void createParty(List<Player> listOfPlayers)
	{
	}

    @Override
    public EventType getType()
    {
        return EventType.FIGHT_CLUB_EVENT;
    }

}
