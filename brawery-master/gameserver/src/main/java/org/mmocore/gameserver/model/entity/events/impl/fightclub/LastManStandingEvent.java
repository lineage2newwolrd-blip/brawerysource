package org.mmocore.gameserver.model.entity.events.impl.fightclub;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.EventsConfig;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubEventManager;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.object.components.variables.PlayerVariables;
import org.mmocore.gameserver.utils.Language;

import java.util.concurrent.TimeUnit;

public class LastManStandingEvent extends AbstractFightClub
{
	private static final long MAX_DELAY_BETWEEN_DEATHS = 30000L;
	private FightClubPlayer _winner;
	private boolean setHero;
	private long lastKill;
	
	public LastManStandingEvent(MultiValueSet<String> set)
	{
		super(set);
		lastKill = 0L;
		setHero = set.getBool("setWinnerHero");
	}
	
	@Override
	public void onKilled(Creature actor, Creature victim)
	{
		if (actor != null && actor.isPlayable())
		{
			FightClubPlayer fActor = getFightClubPlayer(actor.getPlayer());
			if (fActor != null && victim.isPlayer())
			{
				fActor.increaseKills(true);
				updatePlayerScore(fActor);
				sendMessageToPlayer(fActor, MESSAGE_TYPES.GM, (fActor.getPlayer().getLanguage() == Language.RUSSIAN ?"Вы убили ":"You have killed ")+victim.getName());
			}
			else if (victim.isPet())
			{
				
			}
			actor.getPlayer().sendUserInfo();
		}
		
		if (victim.isPlayer())
		{
			FightClubPlayer fVictim = getFightClubPlayer(victim);
			fVictim.increaseDeaths();
			if (actor != null)
				sendMessageToPlayer(fVictim, MESSAGE_TYPES.GM, (fVictim.getPlayer().getLanguage() == Language.RUSSIAN ?"Вас убил ":"You have been killed by ")+actor.getName());
			victim.getPlayer().sendUserInfo();
			lastKill = System.currentTimeMillis();
			
			leaveEvent(fVictim.getPlayer(), true);
		}
		
		super.onKilled(actor, victim);
	}

	@Override
	public void startEvent()
	{
		super.startEvent();

		lastKill = System.currentTimeMillis();
	}

	@Override
	public void startRound()
	{
		super.startRound();
		ThreadPoolManager.getInstance().schedule(new InactivityCheck(), 60000);
	}

	@Override
	public boolean leaveEvent(Player player, boolean teleportTown)
	{
		boolean result = super.leaveEvent(player, teleportTown);
		if (result)
			checkRoundOver();
		return result;
	}

	private boolean checkRoundOver()
	{
		if (getState() != EVENT_STATE.STARTED)
			return true;
		
		int alivePlayers = 0;
		FightClubPlayer aliveFPlayer = null;
		
		for (FightClubPlayer iFPlayer : getPlayers(FIGHTING_PLAYERS))
		{
			if (isPlayerActive(iFPlayer.getPlayer()))
			{
				alivePlayers++;
				aliveFPlayer = iFPlayer;
			}
			if (aliveFPlayer == null)
				if (!iFPlayer.getPlayer().isDead())
					aliveFPlayer = iFPlayer;
		}
		
		if (alivePlayers <= 1)
		{
			_winner = aliveFPlayer;
			if (_winner != null)
			{
				_winner.increaseScore(1);
				announceWinnerPlayer(false, _winner);
			}
			if(setHero)
				setHero(_winner.getPlayer());
			updateScreenScores();
			setState(EVENT_STATE.OVER);

			ThreadPoolManager.getInstance().schedule(new Runnable() {
				
				@Override
				public void run()
				{
					endRound();
				}
			}, 5L*1000L);
			if (_winner != null)
				FightClubEventManager.getInstance().sendToAllMsg(this, _winner.getPlayer().getName() + " Won Last Man Standing Event!");
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean inScreenShowBeScoreNotKills()
	{
		return false;
	}
	
	private class InactivityCheck extends RunnableImpl {

		@Override
		public void runImpl() throws Exception
		{
			if (getState() == EVENT_STATE.NOT_ACTIVE)
				return;
			boolean finished = checkRoundOver();
			if (!finished && lastKill + MAX_DELAY_BETWEEN_DEATHS < System.currentTimeMillis())
			{
				killOnePlayer();
			}
			
			ThreadPoolManager.getInstance().schedule(this, 60000);
		}
	}
	
	private void killOnePlayer()
	{
		double playerToKillHp = Double.MAX_VALUE;
		Player playerToKill = null;
		for (FightClubPlayer fPlayer : getPlayers(FIGHTING_PLAYERS))
			if (fPlayer != null && fPlayer.getPlayer() != null && !fPlayer.getPlayer().isDead())
			{
				if (fPlayer.isAfk())
				{
					playerToKillHp = -1.0;
					playerToKill = fPlayer.getPlayer();
				}
				else if (fPlayer.getPlayer().getCurrentHpPercents() + (fPlayer.getKills(true)*10) < playerToKillHp)
				{
					playerToKill = fPlayer.getPlayer();
					playerToKillHp = fPlayer.getPlayer().getCurrentHpPercents() + (fPlayer.getKills(true)*10);
				}
			}

		if (playerToKill != null)
			playerToKill.doDie(null);
	}

	@Override
	protected int getRewardForWinningTeam(FightClubPlayer fPlayer, boolean atLeast1Kill)
	{
		if (fPlayer.equals(_winner))
			return (int) _badgeWin;
		return super.getRewardForWinningTeam(fPlayer, true);
	}

	@Override
	public String getVisibleTitle(Player player, String currentTitle, boolean toMe)
	{
		FightClubPlayer realPlayer = getFightClubPlayer(player);
		
		if (realPlayer == null)
			return currentTitle;
		
		return "Kills: " + realPlayer.getKills(true);
	}

    @Override
    public EventType getType()
    {
        return EventType.FIGHT_CLUB_EVENT;
    }


	public static void setHero(Player player) {
		if (player == null || player.isHero() || player.getCustomPlayerComponent().isTemporalHero())
			return;
		long period = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(EventsConfig.TempHeroDurationMin);
		player.getPlayerVariables().set(PlayerVariables.TEMPORAL_HERO, period, period);
		player.getCustomPlayerComponent().startTemporalHero();
		player.broadcastPacket(new SocialAction(player.getObjectId(), SocialAction.GIVE_HERO));
		player.broadcastUserInfo(true);
	}

}
