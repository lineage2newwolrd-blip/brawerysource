package org.mmocore.gameserver.model.entity.events.impl.fightclub;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.model.zone.ZoneType;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.SimpleSpawner;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LuckyCreaturesEvent extends AbstractFightClub
{
	private final int _monstersCount;
	private final int[] _monsterTemplates;
	private final int _respawnSeconds;
	
	private List<NpcInstance> _monsters = new CopyOnWriteArrayList<NpcInstance>();
	private List<Long> _deathTimes = new CopyOnWriteArrayList<Long>();
	
	public LuckyCreaturesEvent(MultiValueSet<String> set)
	{
		super(set);
		_monstersCount = set.getInteger("monstersCount", 1);
		_respawnSeconds = set.getInteger("monstersRespawn", 60);
		_monsterTemplates = parseExcludedSkills(set.getString("monsterTemplates", "14200"));
		
	}
	
	@Override
	public void onKilled(Creature actor, Creature victim)
	{
		if (victim.isMonster() && actor != null && actor.isPlayable())
		{
			FightClubPlayer fActor = getFightClubPlayer(actor.getPlayer());
			fActor.increaseKills(true);
			updatePlayerScore(fActor);
			actor.getPlayer().sendUserInfo();

			_deathTimes.add(System.currentTimeMillis()+_respawnSeconds*1000);
			_monsters.remove(victim);
		}
		
		super.onKilled(actor, victim);
	}

	@Override
	public void startEvent()
	{
		super.startEvent();
		
		ThreadPoolManager.getInstance().schedule(new RespawnThread(), 30000);
		
		for (Zone zone : getReflection().getZones())
			zone.setType(ZoneType.peace_zone);
	}

	@Override
	public void startRound()
	{
		super.startRound();
		
		System.out.println("spawning "+_monstersCount+" monsters");
		for (int i = 0;i<_monstersCount;i++)
			spawnMonster();
	}

	@Override
	public void stopEvent()
	{
		super.stopEvent();
		
		for (NpcInstance npc : _monsters)
			if (npc != null)
				npc.doDecay();

		_monsters.clear();
	}

	private void spawnMonster()
	{
		Zone zone = getReflection().getZones().iterator().next();
		Location loc = Location.findPointToStay(zone.getTerritory().getRandomLoc(getReflection().getGeoIndex()), 50, getReflection().getGeoIndex());

		int template = Rnd.get(_monsterTemplates);
		SimpleSpawner spawn = new SimpleSpawner(template);
		spawn.setLoc(loc);
		spawn.setAmount(1);
		spawn.setRespawnDelay(0);
		spawn.setReflection(getReflection());
		NpcInstance monster = spawn.spawnOne();
		spawn.stopRespawn();

		_monsters.add(monster);
	}
	
	private class RespawnThread extends RunnableImpl
	{
		@Override
		public void runImpl() throws Exception
		{
			if (getState() == EVENT_STATE.OVER || getState() == EVENT_STATE.NOT_ACTIVE)
				return;
			
			long current = System.currentTimeMillis();
			List<Long> toRemove = new ArrayList<Long>();
			for (Long deathTime : _deathTimes)
			{
				if (deathTime < current)
				{
					spawnMonster();
					toRemove.add(deathTime);
				}
			}
			
			for (Long l : toRemove)
				_deathTimes.remove(l);

			ThreadPoolManager.getInstance().schedule(this, 10000L);
		}
	}

	@Override
	protected boolean inScreenShowBeScoreNotKills()
	{
		return false;
	}

	@Override
	public boolean isFriend(Creature c1, Creature c2)
	{
		return !(c1.isMonster() || c2.isMonster());
	}

	@Override
	public String getVisibleTitle(Player player, String currentTitle, boolean toMe)
	{
		FightClubPlayer fPlayer = getFightClubPlayer(player);
		
		if (fPlayer == null)
			return currentTitle;
		
		return "Kills: "+fPlayer.getKills(true);
	}

    @Override
    public EventType getType()
    {
        return EventType.FIGHT_CLUB_EVENT;
    }

}
