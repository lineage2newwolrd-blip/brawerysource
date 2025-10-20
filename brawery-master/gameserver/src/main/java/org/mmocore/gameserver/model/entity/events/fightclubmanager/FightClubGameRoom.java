package org.mmocore.gameserver.model.entity.events.fightclubmanager;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.data.xml.holder.FightClubMapHolder;
import org.mmocore.gameserver.listener.actor.player.OnPlayerExitListener;
import org.mmocore.gameserver.model.base.ClassId;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubEventManager.CLASSES;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.Util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FightClubGameRoom
{

	private class LeaveListener implements OnPlayerExitListener
	{
		@Override
		public void onPlayerExit(Player player)
		{
			leaveRoom(player);
		}
	}
	
	private final FightClubMap _map;
	private AbstractFightClub _event;
	private final int _roomMaxPlayers;
	private final int _teamsCount;
	
	//Players in FFA:
	private List<Player> _players;
	
	//Leave listener
	private LeaveListener _leaveListener = new LeaveListener();
	
	public FightClubGameRoom(AbstractFightClub event)
	{
		_event = event;

		_players = new CopyOnWriteArrayList<>();
		
		String eventName = Util.getChangedEventName(event);
		_map = Rnd.get(FightClubMapHolder.getInstance().getMapsForEvent(eventName));
		_roomMaxPlayers = _map.getMaxAllPlayers();
		if (event.isTeamed())
			_teamsCount = Rnd.get(_map.getTeamCount());
		else
			_teamsCount = 0;
	}
	
	public void leaveRoom(Player player)
	{
		_players.remove(player);
		
		player.removeListener(_leaveListener);
	}
	
	public int getMaxPlayers()
	{
		return _roomMaxPlayers;
	}
	
	public int getTeamsCount()
	{
		return _teamsCount;
	}
	
	public int getSlotsLeft()
	{
		return getMaxPlayers() - getPlayersCount();
	}
	
	public AbstractFightClub getGame()
	{
		return _event;
	}
	
	public int getPlayersCount()
	{
		return _players.size();
	}
	
	public FightClubMap getMap()
	{
		return _map;
	}
	
	/**
	 * !!! @Don't @change @that @list!!!
	 */
	public List<Player> getAllPlayers()
	{
		return _players;
	}

	synchronized void addAlonePlayer(Player player)
	{
		player.setFightClubGameRoom(this);
		addPlayerToTeam(player);
	}
	
	public boolean containsPlayer(Player player)
	{
		return _players.contains(player);
	}
	
	/**
	 * Adding players to team and party
	 */
	private void addPlayerToTeam(Player player)
	{
		_players.add(player);
	}
	
	public static CLASSES getPlayerClassGroup(Player player)
	{
		CLASSES classType = null;
		for (CLASSES iClassType : CLASSES.values())
			for (ClassId classId : iClassType.getClasses())
				if (classId == player.getPlayerClassComponent().getClassId())
					classType = iClassType;
		return classType;
	}
}
