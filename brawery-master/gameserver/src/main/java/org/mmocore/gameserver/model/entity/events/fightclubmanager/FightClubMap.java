package org.mmocore.gameserver.model.entity.events.fightclubmanager;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.templates.ZoneTemplate;
import org.mmocore.gameserver.utils.Location;

import java.util.Map;

public class FightClubMap
{
	private final String _name;
	private final String[] _events;
	private final int[] _teamsCount;
	private final int _minAllPlayers;
	private final int _maxAllPlayers;
	private final Map<Integer, Location[]> _teamSpawns;//<teamIndex, Location[]>
	private final Map<Integer, Map<String, ZoneTemplate>> _territories;//<teamIndex, ZoneObject[]>
	private final Location[] _keyLocations;
	
	public FightClubMap(MultiValueSet<String> params, Map<Integer, Location[]> teamSpawns, Map<Integer, Map<String, ZoneTemplate>> territories,
	                    Location[] keyLocations)
	{
		// Params
		_name = params.getString("name");
		_events = params.getString("events").split(";");
		_minAllPlayers = Integer.parseInt(params.getString("minAllPlayers", "-1"));
		_maxAllPlayers = Integer.parseInt(params.getString("maxAllPlayers", "-1"));
		
		//Team Counts
		String[] teamCounts = params.getString("teamsCount", "-1").split(";");
		_teamsCount = new int[teamCounts.length];
		for (int i = 0;i<teamCounts.length;i++)
			_teamsCount[i] = Integer.parseInt(teamCounts[i]);
		
		// Objects
		_teamSpawns = teamSpawns;
		_territories = territories;
		_keyLocations = keyLocations;
	}

	public String getName()
	{
		return _name;
	}
	
	public String[] getEvents()
	{
		return _events;
	}
	
	public int[] getTeamCount()
	{
		return _teamsCount;
	}
	
	public int getMinAllPlayers()
	{
		return _minAllPlayers;
	}
	
	public int getMaxAllPlayers()
	{
		return _maxAllPlayers;
	}
	
	public Map<Integer, Location[]> getTeamSpawns()
	{
		return _teamSpawns;
	}
	
	public Location[] getPlayerSpawns()
	{
		return _teamSpawns.get(-1);
	}
	
	public Map<Integer, Map<String, ZoneTemplate>> getTerritories()
	{
		return _territories;
	}
	
	public Location[] getKeyLocations()
	{
		return _keyLocations;
	}
}
