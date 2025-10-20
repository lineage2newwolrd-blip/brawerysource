package org.mmocore.gameserver.model.entity.events.impl;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.data.xml.holder.EventHolder;
import org.mmocore.gameserver.data.xml.holder.InstantZoneHolder;
import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.objects.CustomPlayerSnapshotObject;
import org.mmocore.gameserver.model.entity.olympiad.Olympiad;
import org.mmocore.gameserver.model.zone.ZoneType;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.network.lineage.components.IBroadcastPacket;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.templates.InstantZone;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractCustomStarterEvent extends AbstractDuelEvent
{
	private final int[] _battleEventIds;
	private final boolean _isArena;
	private final boolean _allowPets;
	private final boolean _olympiadMode;
	private final boolean _removeBuffs;
	private final boolean _disableHeroSkills;
	private final boolean _disableClanSkills;
	private final boolean _clearCooldown;
	private final int[] _forbiddenItems;
	private final int _physWeaponEnchantLock;
	private final int _magicWeaponEnchantLock;
	private final int _armorEnchantLock;
	private final int _accEnchantLock;
	private final int _maxBres;

	private final List<CustomPlayerSnapshotObject> _participants = new CopyOnWriteArrayList<>();

	public AbstractCustomStarterEvent(MultiValueSet<String> set)
	{
		super(set);

		final int[] defId = { getId() + 1 };
		_battleEventIds = set.getIntegerArray("battle_event_ids", defId);
		_isArena = set.getBool("is_arena", false);
		_allowPets = set.getBool("allow_pets", true);
		_olympiadMode = set.getBool("olympiad_mode", false);
		_removeBuffs = set.getBool("remove_buffs", true);
		_disableHeroSkills = set.getBool("disable_hero_skills", true);
		_disableClanSkills = set.getBool("disable_clan_skills", true);
		_clearCooldown = set.getBool("clear_cooldown", true);
		_forbiddenItems = set.getIntegerArray("forbidden_items", null);
		_physWeaponEnchantLock = set.getInteger("phys_weapon_enchant_lock", -1);
		_magicWeaponEnchantLock = set.getInteger("magic_weapon_enchant_lock", -1);
		_armorEnchantLock = set.getInteger("armor_enchant_lock", -1);
		_accEnchantLock = set.getInteger("accessory_enchant_lock", -1);
		_maxBres = set.getInteger("max_bres", -1);
	}

	@Override
	public void reCalcNextTime(boolean onInit)
	{
		if(onInit)
			return;

		registerActions();
	}

	@Override
	protected Instant startTime()
	{
		return Instant.now();
	}

	@Override
	public EventType getType()
	{
		return EventType.PVP_EVENT;
	}

	@Override
	public boolean isInProgress()
	{
		return true;
	}

	@Override
	public void findEvent(Player player)
	{
		for (CustomPlayerSnapshotObject participant : _participants)
			if (participant.getObjectId() == player.getObjectId())
			{
				participant.updatePlayer(player);
				return;
			}
	}

	protected int getArenaId(int battleId){
		for(int i=0; i < _battleEventIds.length; i++)
			if(_battleEventIds[i] == battleId)
				return i+1;
		return 0;
	}

	public boolean isArena()
	{
		return _isArena;
	}

	public boolean allowPets()
	{
		return _allowPets;
	}

	public boolean isOlympiadMode()
	{
		return _olympiadMode;
	}

	private AbstractCustomBattleEvent getRandomBattleEvent(Player player, Player targetPlayer)
	{
		AbstractCustomBattleEvent battleEvent = null;
		IBroadcastPacket result = null;
		for (int idx = Rnd.get(_battleEventIds.length), i = 0; i < _battleEventIds.length; i++, idx = (idx + 1) % _battleEventIds.length)
		{
			battleEvent = EventHolder.getInstance().getEvent(EventType.PVP_EVENT, _battleEventIds[idx]);
			if (battleEvent != null)
			{
				InstantZone iz = InstantZoneHolder.getInstance().getInstantZone(battleEvent.getInstanceId());
				if (iz != null)
				{
					result = iz.canCreate();
					if (result == null)
						return battleEvent;
				}
			}
		}

		if (result != null)
		{
			player.sendPacket(result);
			targetPlayer.sendPacket(result);
		}
		else
		{
			player.sendPacket(SystemMsg.SYSTEM_ERROR);
			targetPlayer.sendPacket(SystemMsg.SYSTEM_ERROR);
		}

		return null;
	}

	protected AbstractCustomBattleEvent getBattleEvent(Player player, Player targetPlayer, int arenaId)
	{
		if (arenaId == 0)
			return getRandomBattleEvent(player, targetPlayer);

		if (arenaId < 0 || arenaId > _battleEventIds.length)
		{
			if (_battleEventIds.length < 2)
				player.sendPacket(new CustomMessage("CustomBattleEvent.InvalidArena"));
			else
				player.sendPacket(new CustomMessage("CustomBattleEvent.InvalidArenaNumber").addNumber(_battleEventIds.length));
			return null;
		}

		IBroadcastPacket result = null;
		AbstractCustomBattleEvent battleEvent = EventHolder.getInstance().getEvent(EventType.PVP_EVENT, _battleEventIds[arenaId - 1]);
		if (battleEvent != null)
		{
			InstantZone iz = InstantZoneHolder.getInstance().getInstantZone(battleEvent.getInstanceId());
			if (iz != null)
			{
				result = iz.canCreate();
				if (result == null)
				{
					result = new CustomMessage("CustomBattleEvent.ArenaSelected").addNumber(arenaId);
					player.sendPacket(result);
					targetPlayer.sendPacket(result);
					return battleEvent;
				}
			}
		}

		if (result != null)
		{
			player.sendPacket(result);
			targetPlayer.sendPacket(result);
		}
		else
		{
			player.sendPacket(SystemMsg.SYSTEM_ERROR);
			targetPlayer.sendPacket(SystemMsg.SYSTEM_ERROR);
		}

		return null;
	}

	protected void addParticipant(AbstractCustomBattleEvent event, Player player, TeamType team, int leader)
	{
		CustomPlayerSnapshotObject participant = new CustomPlayerSnapshotObject(event, player, team, leader, _removeBuffs, _disableHeroSkills, _disableClanSkills,_clearCooldown, _forbiddenItems, _physWeaponEnchantLock, _magicWeaponEnchantLock, _armorEnchantLock, _accEnchantLock, _maxBres);
		_participants.add(participant);
		event.addObject(team, participant);
	}

	protected void updateParticipant(AbstractCustomBattleEvent oldEvent, AbstractCustomBattleEvent newEvent, CustomPlayerSnapshotObject participant)
	{
		participant.updateEvent(oldEvent, newEvent);
		newEvent.addObject(participant.getTeam(), participant);
	}

	protected void unRegisterBattle(AbstractCustomBattleEvent battle)
	{
		for (CustomPlayerSnapshotObject participant : _participants)
			if (battle.equals(participant.getOwner()))
				_participants.remove(participant);
	}

	protected static IBroadcastPacket checkPlayer(Player requestor, Player target)
	{
		if (target.isInCombat())
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_CURRENTLY_ENGAGED_IN_BATTLE).addName(target);
		if (target.isDead() || target.isAlikeDead() || target.isFakeDeath() || target.getCurrentHpPercents() < 50 || target.getCurrentMpPercents() < 50 || target.getCurrentCpPercents() < 50)
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1S_HP_OR_MP_IS_BELOW_50).addName(target);
		if (target.getEvent(SingleMatchEvent.class) != null || target.isInZone(ZoneType.epic))
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_CURRENTLY_ENGAGED_IN_BATTLE).addName(target);
		if (target.isInZone(ZoneType.SIEGE) || target.getEvent(SiegeEvent.class) != null)
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_PARTICIPATING_IN_A_SIEGE_WAR).addName(target);
		if (target.isInOlympiadMode() || Olympiad.isRegisteredInComp(target))
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_PARTICIPATING_IN_THE_OLYMPIAD).addName(target);
		if (target.isCursedWeaponEquipped() || target.getKarma() > 0 || target.getPvpFlag() > 0)
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_IN_A_CHAOTIC_STATE).addName(target);
		if (target.isInStoreMode())
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_CURRENTLY_ENGAGED_IN_A_PRIVATE_STORE_OR_MANUFACTURE).addName(target);
		if (target.isMounted() || target.isInBoat())
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_CURRENTLY_RIDING_A_BOAT_STEED_OR_STRIDER).addName(target);
		if (target.isFishing())
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_CURRENTLY_FISHING).addName(target);
		if (target.isInCombatZone() || target.isInWater() || target.isInZone(ZoneType.no_restart) || !target.getReflection().isDefault())
			return new SystemMessage(SystemMsg.C1_CANNOT_MAKE_A_CHALLENGE_TO_A_DUEL_BECAUSE_C1_IS_CURRENTLY_IN_A_DUELPROHIBITED_AREA_PEACEFUL_ZONE__SEVEN_SIGNS_ZONE__NEAR_WATER__RESTART_PROHIBITED_AREA).addName(target);
		if (!requestor.isInRangeZ(target, 1200) && !requestor.isGM())
			return new SystemMessage(SystemMsg.C1_CANNOT_RECEIVE_A_DUEL_CHALLENGE_BECAUSE_C1_IS_TOO_FAR_AWAY).addName(target);
		if (target.isTransformed())
			return new SystemMessage(SystemMsg.C1_CANNOT_DUEL_BECAUSE_C1_IS_CURRENTLY_POLYMORPHED).addName(target);
		return null;
	}
}