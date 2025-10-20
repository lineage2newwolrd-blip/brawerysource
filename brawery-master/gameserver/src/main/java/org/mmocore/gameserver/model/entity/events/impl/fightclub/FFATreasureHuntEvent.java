package org.mmocore.gameserver.model.entity.events.impl.fightclub;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.Language;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public class FFATreasureHuntEvent extends AbstractFightClub
{
	private static final int CHEST_ID = 53013;
	private final double badgesOpenChest;
	private final int scoreForKilledPlayer;
	private final int scoreForChest;
	private final long timeForRespawningChest;
	private final int numberOfChests;
	private final Collection<NpcInstance> spawnedChests;
	private FightClubPlayer _winner;


	public FFATreasureHuntEvent(MultiValueSet<String> set)
	{
		super(set);
		badgesOpenChest = set.getDouble("badgesOpenChest");
		scoreForKilledPlayer = set.getInteger("scoreForKilledPlayer");
		scoreForChest = set.getInteger("scoreForChest");
		timeForRespawningChest = set.getLong("timeForRespawningChest");
		numberOfChests = set.getInteger("numberOfChests");
		spawnedChests = new CopyOnWriteArrayList<NpcInstance>();
	}

	@Override
	public void onKilled(Creature actor, Creature victim)
	{
		if (actor != null && actor.isPlayable())
		{
			FightClubPlayer realActor = getFightClubPlayer(actor.getPlayer());
			if (realActor != null)
			{
				if (victim.isPlayer())
				{
					realActor.increaseKills(true);
					realActor.increaseScore(scoreForKilledPlayer);
					updatePlayerScore(realActor);
					sendMessageToPlayer(realActor, MESSAGE_TYPES.GM, (realActor.getPlayer().getLanguage() == Language.RUSSIAN ?"Вы убили ":"You have killed ")+victim.getName());
				}
				actor.getPlayer().sendUserInfo();
			}
		}

		if (victim.isPlayer())
		{
			FightClubPlayer realVictim = getFightClubPlayer(victim);
			if (realVictim != null)
			{
				realVictim.increaseDeaths();
				if (actor != null)
					sendMessageToPlayer(realVictim, MESSAGE_TYPES.GM, (realVictim.getPlayer().getLanguage() == Language.RUSSIAN ?"Вас убил ":"You have been killed by ")+actor.getName());
				victim.getPlayer().sendUserInfo();
			}
		}

		super.onKilled(actor, victim);
	}

	private void spawnChest()
	{
		spawnedChests.add(chooseLocAndSpawnNpc(CHEST_ID, getMap().getKeyLocations(), 0));
	}

	@Override
	public void startRound()
	{
		super.startRound();

		for (int i = 0;i< numberOfChests;i++)
			spawnChest();
	}

	@Override
	public void stopEvent()
	{
		int bestScore = -1;
		FightClubPlayer bestPlayer = null;
		for(FightClubPlayer iFPlayer : getPlayers(FIGHTING_PLAYERS))
			if(iFPlayer.getPlayer() != null && iFPlayer.getPlayer().isOnline())
				if(iFPlayer.getScore() > bestScore)
				{
					bestScore = iFPlayer.getScore();
					bestPlayer = iFPlayer;
				}
		_winner = bestPlayer;

		super.stopEvent();

		for (NpcInstance chest : spawnedChests)
			if (chest != null && !chest.isDead())
				chest.deleteMe();
		spawnedChests.clear();
	}

	@Override
	protected void updatePlayerScore(FightClubPlayer fPlayer)
	{
		_scores.put(getScorePlayerName(fPlayer), fPlayer.getScore());
		_scoredUpdated = true;

		if(!isTeamed())
			updateScreenScores();
	}
	/**
	 * @return should it disappear?
	 */
	public boolean openTreasure(Player player, NpcInstance npc)
	{
		FightClubPlayer fPlayer = getFightClubPlayer(player);
		if (fPlayer == null)
			return false;
		if (getState() != EVENT_STATE.STARTED)
			return false;

		fPlayer.increaseEventSpecificScore("chest");
		fPlayer.increaseScore(scoreForChest);
		updatePlayerScore(fPlayer);
		player.sendUserInfo();

		ThreadPoolManager.getInstance().schedule(new SpawnChest(this), timeForRespawningChest *1000L);

		spawnedChests.remove(npc);

		return true;
	}
	
	private static class SpawnChest implements Runnable
	{
		private final FFATreasureHuntEvent event;
		private SpawnChest(FFATreasureHuntEvent event)
		{
			this.event = event;
		}
		
		@Override
		public void run()
		{
			if (event.getState() != EVENT_STATE.NOT_ACTIVE)
				event.spawnChest();
		}
	}


	@Override
	protected int getRewardForWinningTeam(FightClubPlayer fPlayer, boolean atLeast1Kill)
	{
		if (fPlayer.equals(_winner))
			return (int) _badgeWin;
		return super.getRewardForWinningTeam(fPlayer, true);
	}

	@Override
	protected int getBadgesEarned(FightClubPlayer fPlayer, int currentValue, boolean isTopKiller)
	{
		int newValue = currentValue + addMultipleBadgeToPlayer(fPlayer.getEventSpecificScore("chest"), badgesOpenChest);
		return super.getBadgesEarned(fPlayer, newValue, isTopKiller);
	}

	@Override
	public String getVisibleTitle(Player player, String currentTitle, boolean toMe)
	{
		FightClubPlayer fPlayer = getFightClubPlayer(player);

		if (fPlayer == null)
			return currentTitle;

		return "Chests: " + fPlayer.getEventSpecificScore("chest") + " Kills: "+fPlayer.getKills(true);
	}

    @Override
    public EventType getType()
    {
        return EventType.FIGHT_CLUB_EVENT;
    }

}
