package org.mmocore.gameserver.model.entity.events.impl;

import org.jts.dataparser.data.holder.PetDataHolder;
import org.mmocore.commons.collections.JoinedIterator;
import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.manager.FenceBuilderManager;
import org.mmocore.gameserver.model.Effect;
import org.mmocore.gameserver.model.entity.events.objects.CustomPlayerSnapshotObject;
import org.mmocore.gameserver.model.zone.ZoneType;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchScore;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchSpelledInfo;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchTeamInfo;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.Skill;
import org.mmocore.gameserver.model.base.RestartType;
import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.PositionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public abstract class AbstractCustomBattleEvent extends AbstractCustomObservableEvent implements Iterable<CustomPlayerSnapshotObject>
{
	private AbstractCustomStarterEvent _parent = null;
	private boolean _isArena = false;
	private boolean _allowPets = false;

	protected ScheduledFuture<?> _winnerCheckTask = null;
	protected TeamType _winner = TeamType.NONE;
	private boolean _canReEnter = false;
	protected int _redScore = 0;
	protected int _blueScore = 0;
	protected int _redRes = 0;
	protected int _blueRes = 0;
	private List<Integer> _fences = new ArrayList<>();

	private final class CheckWinnerTask implements Runnable
	{
		public void run()
		{
			if (checkWinnerInProgress())
				stopEvent();
		}
	}

	public AbstractCustomBattleEvent(MultiValueSet<String> set)
	{
		super(set);
	}

	protected AbstractCustomBattleEvent(int id, int type, AbstractCustomStarterEvent parent, String player1, String player2)
	{
		super(id, 1, type, player1, player2);

		_parent = parent;
		_isArena = parent.isArena();
		_allowPets = parent.allowPets();
	}

	@Override
	public void initEvent()
	{
		//
	}

	@Override
	public void startEvent()
	{
		_state = State.STARTED;
		_winnerCheckTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new CheckWinnerTask(), 1000L, 1000L);

		for (CustomPlayerSnapshotObject s : this)
			s.onStart();

		super.startEvent();
	}

	@Override
	public void stopEvent()
	{
		if (_state != State.STARTED)
			return;

		_state = State.NONE;

		if (_winnerCheckTask != null)
		{
			_winnerCheckTask.cancel(false);
			_winnerCheckTask = null;
		}

		for (CustomPlayerSnapshotObject s : this)
			if(s.getPlayer()!=null && s.getPlayer().getReflectionId() == getReflection().getId())
				s.teleportBack(5000L);

		removeObjects(TeamType.BLUE);
		removeObjects(TeamType.RED);

		List<CustomPlayerSnapshotObject> admins = getObjects(TeamType.NONE);
		for (CustomPlayerSnapshotObject s : admins)
			if(s.getPlayer()!=null && s.getPlayer().getReflectionId() == getReflection().getId())
				s.teleportBack(5000L);

		if (!getReflection().isDefault())
			getReflection().startCollapseTimer(10000L);

		super.stopEvent();
		clearActions();
		getObjects().clear();
		if(_parent!=null)
			_parent.unRegisterBattle(this);
		_parent = null;
	}

	@Override
	public void addObserver(Player player)
	{
		List<CustomPlayerSnapshotObject> team;
		List<Player> redList = new ArrayList<>();
		List<Player> blueList = new ArrayList<>();

		team = getObjects(TeamType.BLUE);
		for (CustomPlayerSnapshotObject s : team)
			if(s.getPlayer()!=null){
				player.sendPacket(new ExEventMatchSpelledInfo(s.getPlayer()));
				blueList.add(s.getPlayer());
			}
		player.sendPacket(new ExEventMatchTeamInfo(blueList, TeamType.BLUE.ordinal()));

		team = getObjects(TeamType.RED);
		for (CustomPlayerSnapshotObject s : team)
			if(s.getPlayer()!=null){
				player.sendPacket(new ExEventMatchSpelledInfo(s.getPlayer()));
				redList.add(s.getPlayer());
			}
		player.sendPacket(new ExEventMatchTeamInfo(redList, TeamType.RED.ordinal()));

		sendPacket(new ExEventMatchScore(_blueScore, _redScore));
	}

	@Override
	public void onEffectIconsUpdate(Player player, Effect[] effects)
	{
		boolean empty = true;

		final ExEventMatchSpelledInfo packet = new ExEventMatchSpelledInfo();
		for (Effect e : effects)
			if (e.isInUse()) {
				empty = false;
				e.addMatchSpelledIcon(player, packet);
			}
		if(empty)
			packet.addSpellRecivedPlayer(player);

		sendPacketToObservers(packet);

	}

	@Override
	public boolean isInProgress()
	{
		return _state != State.NONE;
	}

	@Override
	public void reCalcNextTime(boolean onInit)
	{
		if(onInit)
			return;

		registerActions();
	}

	@Override
	public void action(String name, boolean start)
	{
		if (name.equalsIgnoreCase("reenter"))
		{
			_canReEnter = start;
			return;
		}
		else if (name.equalsIgnoreCase("heal"))
		{
			for (CustomPlayerSnapshotObject s : this)
				s.heal();
			return;
		}

		super.action(name, start);
	}

	@Override
	public SystemMsg checkForAttack(Creature target, Creature attacker, Skill skill, boolean force)
	{
		if(!canAttack(target, attacker, skill, force, false))
			return SystemMsg.INVALID_TARGET;

		return null;
	}

	@Override
	public boolean canAttack(Creature target, Creature attacker, Skill skill, boolean force, boolean nextAttackCheck)
	{
		if (attacker.getTeam() == TeamType.NONE || target.getTeam() == TeamType.NONE)
			return false;

		if (!isBattleStarted() && (attacker.getTeam() != target.getTeam()))
			return false;

		if (attacker.getTeam() == target.getTeam())
		{
			if (!force)
				return false;
			if (!_isArena && skill != null && skill.isPvpSkill())
				return false;
		}

		if (_isArena)
			return true;

		final Player targetPlayer = target.getPlayer();
		if (targetPlayer != null)
		{
			if (targetPlayer.getPvpFlag() == 0 && skill != null && skill.isPvpSkill())
				return false;

			return !nextAttackCheck;
		}

		return true;
	}

	@Override
	public SystemMsg canUseItem(Player player, ItemInstance item)
	{
		if (!_allowPets && PetDataHolder.getInstance().isPetControlItem(item.getItemId()))
			return SystemMsg.PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME;

		return null;
	}

	@Override
	public boolean checkPvPFlag(Creature target)
	{
		return super.checkPvPFlag(target) || !_isArena || _state == State.NONE;
	}

	@Override
	public Iterator<CustomPlayerSnapshotObject> iterator()
	{
		return new JoinedIterator<>(getObjects(TeamType.BLUE).iterator(), getObjects(TeamType.RED).iterator());
	}

	@Override
	public void teleportPlayers(final String name, final ZoneType zoneType)
	{
		_state = State.TELEPORT_PLAYERS;

		Location loc1 = getReflection().getInstancedZone().getTeleportCoords().get(0);
		Location loc2 = getReflection().getInstancedZone().getTeleportCoords().get(1);
		List<CustomPlayerSnapshotObject> team;
		team = getObjects(TeamType.BLUE);
		for (CustomPlayerSnapshotObject m : team)
			m.teleport(loc1, getReflection());
		team = getObjects(TeamType.RED);
		for (CustomPlayerSnapshotObject m : team)
			m.teleport(loc2, getReflection());

		if(isFence()){
			fenceUp();
		}
	}

	public void fenceUp(){
		Location loc1 = getReflection().getInstancedZone().getTeleportCoords().get(0);
		Location loc2 = getReflection().getInstancedZone().getTeleportCoords().get(1);
		int dist = (int)loc1.distance(loc2);
		Location centerLoc = PositionUtils.calculateAnglePoint(loc1, loc2, 0, dist/2, getReflection().getGeoIndex());
		if(Math.abs(Math.abs(loc1.x) - Math.abs(loc2.x)) > Math.abs(Math.abs(loc1.y) - Math.abs(loc2.y)))
			_fences.add(FenceBuilderManager.getInstance().spawnFence(centerLoc, getReflection(), 2, (int)(dist*1.5), dist));
		else
			_fences.add(FenceBuilderManager.getInstance().spawnFence(centerLoc, getReflection(), 2, dist, (int)(dist*1.5)));
	}

	public void fenceDown(){
		for(int id : _fences)
			FenceBuilderManager.getInstance().deleteFence(id);
	}

	public final void incBlessRes(TeamType type, int max){

		if (type == TeamType.NONE)
			return;

		if(max == 0)
			disableRes(type);

		if (type == TeamType.BLUE){
			_blueRes++;
			if(_blueRes >= max)
				disableRes(TeamType.BLUE);
		}
		else {
			_redRes++;
			if(_redRes >= max)
				disableRes(TeamType.RED);
		}
	}

	public final void disableRes(TeamType type){

		if (type == TeamType.NONE)
			return;

		List<CustomPlayerSnapshotObject> team = getObjects(type);
		for (CustomPlayerSnapshotObject s : team)
			if (s.getPlayer()!=null)
				s.lockItems(s.getPlayer(), 3936);
	}

	public final List<Player> getParticipants(){
		List<Player> all = new ArrayList<>();
		List<CustomPlayerSnapshotObject> team;
		Player player;
		team = getObjects(TeamType.BLUE);
		for (CustomPlayerSnapshotObject m : team) {
			player = m.getPlayer();
			if(player!=null)
				all.add(player);
		}
		team = getObjects(TeamType.RED);
		for (CustomPlayerSnapshotObject m : team) {
			player = m.getPlayer();
			if(player!=null)
				all.add(player);
		}
		return all;
	}

	@Override
	public Location getRestartLoc(Player player, RestartType type)
	{
		if (player.getReflection() != getReflection() || type != RestartType.TO_VILLAGE)
			return null;

		return player._stablePoint;
	}

	public boolean isBattleStarted()
	{
		return _state == State.STARTED;
	}

	public boolean canReEnter(Player player)
	{
		return _canReEnter;
	}

	public boolean allowPets()
	{
		return _allowPets;
	}

	protected abstract boolean checkWinnerInProgress();
}