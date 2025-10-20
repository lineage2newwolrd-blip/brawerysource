package org.mmocore.gameserver.model.entity.events.impl;

import org.apache.commons.lang3.StringUtils;
import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.data.xml.holder.EventHolder;
import org.mmocore.gameserver.data.xml.holder.InstantZoneHolder;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.objects.DoorObject;
import org.mmocore.gameserver.model.entity.events.objects.SpawnExObject;
import org.mmocore.gameserver.network.lineage.components.IBroadcastPacket;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.scripts.npc.model.events.CustomObservationManagerInstance;
import org.mmocore.gameserver.templates.InstantZone;
import org.mmocore.gameserver.utils.Location;

import java.util.List;

public abstract class AbstractCustomObservableEvent extends SingleMatchEvent
{
	private static final String DOORS = "doors";
	private static final String SPAWNS = "spawns";

	private final int _instanceId;
	private final boolean _fence;

	private Reflection _reflection;
	private int _arenaId = 0;
	private Location _observerCoords = null;
	protected State _state = State.NONE;
	private String team1, team2;

	public AbstractCustomObservableEvent(MultiValueSet<String> set)
	{
		super(set);
		_instanceId = set.getInteger("instance_id", getId());
		_fence = set.getBool("fence_up", false);
	}

	protected AbstractCustomObservableEvent(int id, int status, int type, String player1, String player2)
	{
		super(id, StringUtils.EMPTY);
		final AbstractCustomObservableEvent source = EventHolder.getInstance().getEvent(EventType.PVP_EVENT, id);
		source.cloneTo(this);

		// копируем двери потому что в каждом новом рефлекте они свои
		final List<DoorObject> doorList = source.getObjects(DOORS);
		if (!doorList.isEmpty())
			for (DoorObject door : doorList)
				addObject(DOORS, new DoorObject(door.getId()));

		final List<SpawnExObject> spawnList = source.getObjects(SPAWNS);
		if (!spawnList.isEmpty())
			for (SpawnExObject spawn : spawnList)
				addObject(SPAWNS, new SpawnExObject(spawn));

		_instanceId = source.getInstanceId();
		_fence = source.isFence();
		final InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(_instanceId);
		_reflection = new Reflection();
		_reflection.init(instantZone);
		_observerCoords = instantZone.getTeleportCoords().size() > 1 ? instantZone.getTeleportCoords().get(2) : instantZone.getTeleportCoord();

		_arenaId = CustomObservationManagerInstance.registerBattle(this);
		team1 = player1;
		team2 = player2;
	}

	public int getInstanceId()
	{
		return _instanceId;
	}

	public boolean isFence(){
		return _fence;
	}

	public int getStateId()
	{
		return _state.ordinal() == 3 ? 0 : _state.ordinal();
	}

	public int getArenaId()
	{
		return _arenaId;
	}

	public String getTeamName1()
	{
		return team1;
	}

	public String getTeamName2()
	{
		return team2;
	}

	public Location getObserverCoords()
	{
		return _observerCoords;
	}

	public void addObserver(Player player)
	{
		//
	}

	public void removeObserver(Player player)
	{
		//
	}

	@Override
	public void stopEvent()
	{
		super.stopEvent();
		CustomObservationManagerInstance.unRegisterBattle(this);
	}

	@Override
	public EventType getType()
	{
		return EventType.PVP_EVENT;
	}

	@Override
	public Reflection getReflection()
	{
		return _reflection;
	}

	@Override
	public void sendPacket(IBroadcastPacket packet)
	{
		for (Creature c : _reflection.getPlayersAndObservers())
			c.sendPacket(packet);
	}

	@Override
	public void sendPackets(IBroadcastPacket... packets)
	{
		for (Creature c : _reflection.getPlayersAndObservers())
			c.sendPacket(packets);
	}

	public void sendPacketToObservers(IBroadcastPacket packet)
	{
		for (Creature c : _reflection.getObservers())
			c.sendPacket(packet);		
	}

	public static enum State
	{
		NONE,
		TELEPORT_PLAYERS,
		STARTED,
		FINISHED
	}
}