package org.mmocore.gameserver.model.entity.events.objects;

import org.apache.commons.lang3.ArrayUtils;
import org.mmocore.commons.lang.reference.HardReference;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.listener.CharListener;
import org.mmocore.gameserver.listener.actor.OnCurrentHpDamageListener;
import org.mmocore.gameserver.listener.actor.OnDeathFromUndyingListener;
import org.mmocore.gameserver.listener.actor.OnMagicUseListener;
import org.mmocore.gameserver.listener.actor.OnReviveListener;
import org.mmocore.gameserver.listener.actor.player.OnPlayerEnterListener;
import org.mmocore.gameserver.listener.actor.player.OnPlayerExitListener;
import org.mmocore.gameserver.listener.actor.player.OnPlayerSummonServitorListener;
import org.mmocore.gameserver.listener.actor.player.OnTeleportListener;
import org.mmocore.gameserver.manager.ReflectionManager;
import org.mmocore.gameserver.model.Effect;
import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.model.entity.Hero;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.entity.events.impl.AbstractCustomBattleEvent;
import org.mmocore.gameserver.model.entity.events.impl.TournamentGvGBattleEvent;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.Die;
import org.mmocore.gameserver.network.lineage.serverpackets.ExPVPMatchRecord.Member;
import org.mmocore.gameserver.network.lineage.serverpackets.Revive;
import org.mmocore.gameserver.network.lineage.serverpackets.SkillCoolTime;
import org.mmocore.gameserver.network.lineage.serverpackets.SkillList;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Servitor;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.object.components.items.LockType;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.skills.SkillEntryType;
import org.mmocore.gameserver.skills.TimeStamp;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.PlayerUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomPlayerSnapshotObject implements Serializable
{
	private AbstractCustomBattleEvent owner;
	private final int objectId;
	private HardReference<Player> playerRef;
	private final TeamType team;
	private final Member matchRecord;
	private final String clanName;
	private final int level;
	private final int leader;

	private final boolean removeBuffs;
	private final boolean disableHeroSkills;
	private final boolean disableClanSkills;
	private final boolean clearCooldown;
	private final int[] forbiddenItems;
	private final int physWeaponEnchantLock;
	private final int magicWeaponEnchantLock;
	private final int armorEnchantLock;
	private final int accEnchantLock;
	private final int maxBres;

	private final CharListener playerListeners = new PlayerListener();
	private final CharListener petListeners = new PetListener();

	private volatile boolean storedOnEnter = false;
	private volatile boolean restoredOnExit = false;
	private volatile boolean reEntered = false;
	private boolean isExited = false;
	private boolean isDead = false;
	private int reflectionId = Integer.MIN_VALUE;
	private double damage = 0;
	
	private int classId;
	private List<Integer> summonedServitors = new ArrayList<>();
	private LockType lockType;
	private int[] lockedItems;
	private Location returnLoc;
	private double storedHp;
	private double storedMp;
	private double storedCp;
	private List<TimeStamp> storedReuses = Collections.emptyList();
	private List<Effect> storedEffects = Collections.emptyList();

	private Location currentLoc = null;
	private double currentHp = 0;
	private double currentMp = 0;
	private double currentCp = 0;
	private List<TimeStamp> currentReuses = null;
	private List<Effect> currentEffects = null;

	public CustomPlayerSnapshotObject(AbstractCustomBattleEvent owner, Player player, TeamType team, int leader, boolean removeBuffs, boolean disableHeroSkills, boolean disableClanSkills, boolean clearCooldown, int[] forbiddenItems, int physWeaponEnchantLock, int magicWeaponEnchantLock, int armorEnchantLock, int accEnchantLock, int maxBres)
	{
		this.owner = owner;
		this.objectId = player.getObjectId();
		playerRef = player.getRef();
		this.team = team;
		matchRecord = new Member(player.getName(), 0, 0);
		level = player.getLevel();
		this.leader = leader;
		clanName = player.getClan() != null ? player.getClan().getName() : "";
		this.removeBuffs = removeBuffs;
		this.disableHeroSkills = disableHeroSkills;
		this.disableClanSkills = disableClanSkills;
		this.clearCooldown = clearCooldown;
		this.forbiddenItems = forbiddenItems;
		this.physWeaponEnchantLock = physWeaponEnchantLock;
		this.magicWeaponEnchantLock = magicWeaponEnchantLock;
		this.armorEnchantLock = armorEnchantLock;
		this.accEnchantLock = accEnchantLock;
		this.maxBres = maxBres;
		player.addEvent(owner);
		player.addListener(playerListeners);
	}

	public final int getObjectId()
	{
		return objectId;
	}

	public final Player getPlayer()
	{
		return playerRef.get();
	}

	public final boolean isDead()
	{
		if (isDead)
			return true;
		final Player player = getPlayer();
		if (player == null || player.isTeleporting()) // во время телепорта игроки считаются мертвыми
			return true;

		return false;
	}

	public final boolean isExited()
	{
		return isExited;
	}

	public final Member getMatchRecord()
	{
		return matchRecord;
	}

	public final int getClassId()
	{
		return classId;
	}

	public final int getLevel()
	{
		return level;
	}

	public final int isLeader()
	{
		return leader;
	}

	public final String getClanName()
	{
		return clanName;
	}

	public final int getKilled()
	{
		return matchRecord.deaths;
	}

	public final int getKills()
	{
		return matchRecord.kills;
	}

	public final double getDamage()
	{
		return damage;
	}

	public final AbstractCustomBattleEvent getOwner()
	{
		return owner;
	}

	/**
	 * Вызывается при входе в игру во время загрузки, ставит листенеры для полного восстановления состояния
	 */
	public final boolean updatePlayer(Player player)
	{
		if (player.getObjectId() != objectId)
			return false;
		if (!storedOnEnter)
		{
			if (currentLoc != null)
				return false;

			playerRef = player.getRef();
			player.addEvent(owner);
			player.addListener(playerListeners);
			return true;
		}

		playerRef = player.getRef();

		if (!restoredOnExit)
			return false;

		if (!owner.canReEnter(player))
			return false;

		if (reEntered)
			return false;
		reEntered = true;

		player.addEvent(owner);
		player.addListener(playerListeners);

		player._stablePoint = returnLoc;
		player.setTeam(team);
		player.setUndying(true);
		player.setLoc(currentLoc);
		player.setReflection(reflectionId);
		return true;
	}

	public final void teleport(Location loc, Reflection ref)
	{
		currentLoc = loc; // признак того что был порт на арену
		reflectionId = ref.getId();

		final Player player = getPlayer();
		if (player == null)
		{
			isDead = true;
			isExited = true;
			return;
		}

		isExited = false;
		isDead = player.isDead();
		player.teleToLocation(loc, ref);
	}

	public final void onStart()
	{
		if (!storedOnEnter || isExited)
			return;

		final Player player = getPlayer();
		if (player != null)
		{
			PlayerUtils.updateAttackableFlags(player);
		}
	}

	public final void teleportBack(long delay)
	{
		if (isExited)
			return;

		final Player player = getPlayer();
		if (player == null)
			return;

		player._stablePoint = null;
		player.startFrozen();
		final Servitor pet = player.getServitor();
		if (pet != null)
			pet.startFrozen();

		PlayerUtils.updateAttackableFlags(player);

		ThreadPoolManager.getInstance().schedule(new RunnableImpl()
		{
			@Override
			public void runImpl() throws Exception
			{
				player.stopFrozen();
				if (pet != null)
					pet.stopFrozen();
				player.teleToLocation(returnLoc, ReflectionManager.DEFAULT);
			}
		}, delay);
	}

	public final void heal()
	{
		if (isDead || isExited)
			return;
		
		final Player player = getPlayer();
		if (player == null)
			return;

		player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
		player.setCurrentCp(player.getMaxCp());
		player.broadcastUserInfo(true);
	}

	public final void onPlayerReEnter(Player player)
	{
		if (!storedOnEnter || !restoredOnExit)
			return;
		if (!reEntered)
			return;

		if(owner instanceof TournamentGvGBattleEvent)
			reEntered = false;

		boolean cheater = player.getClassId() != classId;

		restoreEffects(player, currentEffects, cheater);

		player.setCurrentHpMp(currentHp, currentMp);
		player.setCurrentCp(currentCp);

		boolean updateSkills = false;

		if (disableHeroSkills && player.isHero())
		{
			Hero.removeSkills(player);
			updateSkills = true;
		}

		if (disableClanSkills && player.getClan() != null)
		{
			player.disableSkillsByEntryType(SkillEntryType.CLAN);
			updateSkills = true;
		}

		restoreReuses(player, currentReuses);

		if (updateSkills)
			player.sendPacket(new SkillList(player));

		player.sendPacket(new SkillCoolTime(player));
		lockEnchant(player, true);

		lockItems(player, false);

		restoredOnExit = false;
		isExited = false;

		player.setTeam(team);
		player.setUndying(true);

		isDead = player.isDead();
		if (isDead)
			player.broadcastPacket(new Die(player));

		PlayerUtils.updateAttackableFlags(player);
	}

	public final boolean storeStateOnEnter(Player player)
	{
		classId = player.getClassId();
		returnLoc = player._stablePoint == null ? player.getReflection().getReturnLoc() == null ? player.getLoc() : player.getReflection().getReturnLoc() : player._stablePoint;
		player._stablePoint = returnLoc;
		storedCp = player.getCurrentCp();
		storedHp = player.getCurrentHp();
		storedMp = player.getCurrentMp();

		storedEffects = storeEffects(player);

		if (removeBuffs)
			clearEffects(player);

		boolean updateReuses = false;

		if (disableHeroSkills && player.isHero())
		{
			Hero.removeSkills(player);
			updateReuses = true;
		}

		if (disableClanSkills && player.getClan() != null)
		{
			player.disableSkillsByEntryType(SkillEntryType.CLAN);
			updateReuses = true;
		}
		if (clearCooldown)
		{
			storedReuses = storeReuses(player);
			if (!storedReuses.isEmpty())
				updateReuses = true;
		}
		
		if (updateReuses)
			player.sendPacket(new SkillCoolTime(player));

		lockItems(player, true);
		lockEnchant(player, true);

		return true;
	}

	public final boolean restoreStateOnExit(boolean storeCurrentState)
	{
		if (restoredOnExit || !storedOnEnter)
			return false;
		restoredOnExit = true;

		final Player player = getPlayer();
		if (player == null)
			return false;

		try
		{
			if (!reEntered && storeCurrentState)
			{
				currentLoc = player.getLoc();
				currentHp = player.getCurrentHp();
				currentMp = player.getCurrentMp();
				currentCp = player.getCurrentCp();
				currentEffects = storeEffects(player);
				currentReuses = storeReuses(player);
			}
			else
				reEntered = true; // принудительно отключаем повторный вход

			final boolean cheater = player.getClassId() != classId;

			restoreEffects(player, storedEffects, cheater);

			if (player.isDead())
			{
				player.setCurrentHpMp(storedHp, storedMp, true);
				player.broadcastPacket(new Revive(player));
			}
			else
				player.setCurrentHpMp(storedHp, storedMp, false);
			player.setCurrentCp(storedCp);

			final Servitor pet = player.getServitor();
			if (pet != null && pet.isPet())
				if (pet.isDead())
					pet.setCurrentHp(1, true);

			boolean updateReuses = false;

			if (!cheater)
			{
				if (disableHeroSkills && player.isHero() && player.getClassId() == player.getPlayerClassComponent().getBaseClassId())
				{
					Hero.addSkills(player);
					updateReuses = true;
				}

				if (disableClanSkills && player.getClan() != null && clanName.equals(player.getClan().getName()) && player.getClan().getReputationScore() >= 0)
				{
					player.enableSkillsByEntryType(SkillEntryType.CLAN);
					updateReuses = true;
				}

				if (clearCooldown && restoreReuses(player, storedReuses))
					updateReuses = true;
			}
			else
			{
				setAllReuses(player);
				updateReuses = true;				
			}

			if (updateReuses)
				player.sendPacket(new SkillCoolTime(player));

			lockEnchant(player, false);
			unlockItems(player);
		}
		finally
		{
			isDead = true;
			player.removeListener(playerListeners);
			player.removeEvent(owner);
			player.setTeam(TeamType.NONE);
			player.setUndying(false);

			final Servitor pet = player.getServitor();
			if (pet != null)
			{
				if (pet.isPet())
				{
					pet.removeListener(petListeners);
					pet.setUndying(false);
				}
				pet.setTeam(TeamType.NONE);
			}
		}

		return true;
	}

	public final void removeEvent(AbstractCustomBattleEvent battleEvent){
		Player player = getPlayer();
		player.removeListener(playerListeners);
		player.removeEvent(battleEvent);
	}

	public final void updateEvent(AbstractCustomBattleEvent oldEvent, AbstractCustomBattleEvent newEvent){

		owner = newEvent;

		if(getPlayer()==null)
			return;
		getPlayer().removeEvent(oldEvent);
		getPlayer().addEvent(newEvent);
	}

	public final TeamType getTeam(){
		return team;
	}

	public final void preRound(){
		if (isDead())
			getPlayer().doRevive();
		lockItems(getPlayer(),false);
		if(removeBuffs)
			clearEffects(getPlayer());
		if(clearCooldown)
			clearReuses(getPlayer());
		reEntered = false;
	}

	public static void clearReuses(Player player)
	{
		final Collection<TimeStamp> reuseList = player.getSkillReuses();
		if (reuseList.isEmpty())
			return;
		for (TimeStamp t : reuseList)
			if (t != null && t.hasNotPassed())
			{
				SkillEntry skill = player.getKnownSkill(t.getId());
				if (skill == null || skill.getLevel() != t.getLevel())
					continue;
				player.enableSkill(skill);
			}
	}

	private static List<Effect> storeEffects(Player player)
	{
		final List<Effect> effectList = player.getEffectList().getAllEffects();
		if (effectList.isEmpty())
			return Collections.emptyList();

		final List<Effect>effects = new ArrayList<Effect>(effectList.size());
		for(Effect e : effectList)
			if (e != null)
			{
				if (e.getSkill().getTemplate().isToggle())
					continue;
				Effect effect = e.getTemplate().getEffect(e.getEffector(), e.getEffected(), e.getSkill());
				effect.setCount(e.getCount());
				effect.setPeriod(e.getCount() == 1 ? e.getPeriod() - e.getTime() : e.getPeriod());

				effects.add(effect);
			}

		return effects;
	}

	public static void clearEffects(Creature actor)
	{
		for (Effect e : actor.getEffectList().getAllEffects())
		{
			if (e == null)
				continue;
			if (e.getSkill().getTemplate().isToggle())
				continue;
			actor.sendPacket(new SystemMessage(SystemMsg.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(e.getSkill()));
			e.exit();
		}

		if (!actor.isPlayer())
			return;

		actor.getPlayer().deleteCubics();

		final Servitor servitor = actor.getServitor();
		if (servitor == null)
			return;

		for (Effect e : servitor.getEffectList().getAllEffects())
			if (e != null && !e.getSkill().getTemplate().isToggle())
				e.exit();
	}

	private static void restoreEffects(Player player, List<Effect> effects, boolean clearOnly)
	{
		for (Effect e : player.getEffectList().getAllEffects())
			if (e != null && !e.getSkill().getTemplate().isToggle())
				e.exit();

		if (clearOnly || effects == null || effects.isEmpty())
			return;

		int size = effects.size();
		for (Effect e : effects)
		{
			player.getEffectList().addEffect(e);
			e.fixStartTime(size--);
		}
	}

	private static List<TimeStamp> storeReuses(Player player)
	{
		final Collection<TimeStamp> reuseList = player.getSkillReuses();
		if (reuseList.isEmpty())
			return Collections.emptyList();

		final List<TimeStamp> reuses = new ArrayList<TimeStamp>(reuseList.size());
		for (TimeStamp t : reuseList)
			if (t != null && t.hasNotPassed())
			{
				reuses.add(t);
				SkillEntry skill = player.getKnownSkill(t.getId());
				if (skill == null || skill.getLevel() != t.getLevel())
					continue;
				player.enableSkill(skill);
			}

		return reuses;
	}

	private static boolean restoreReuses(Player player, List<TimeStamp> reuses)
	{
		boolean result = false;
		final Collection<TimeStamp> reuseList = player.getSkillReuses();
		if (!reuseList.isEmpty())
			for (TimeStamp t : reuseList)
				if (t != null && t.hasNotPassed())
				{
					SkillEntry skill = player.getKnownSkill(t.getId());
					if (skill == null || skill.getLevel() != t.getLevel())
						continue;
					player.enableSkill(skill);
					result = true;
				}		

		if (reuses != null && !reuses.isEmpty())
			for (TimeStamp t : reuses)
				if (t.hasNotPassed())
				{
					SkillEntry skill = SkillTable.getInstance().getSkillEntry(t.getId(), t.getLevel());
					player.disableSkill(skill, t.getEndTime() - System.currentTimeMillis());
					result = true;
				}

		return result;
	}

	private static void setAllReuses(Player player)
	{
		// someone is cheating, disabling all skills
		for (SkillEntry skill : player.getAllSkills())
			if (skill != null && skill.getTemplate().getReuseDelay() > 0)
				player.disableSkill(skill, skill.getTemplate().getReuseDelay());
	}

	private void lockItems(Player player, boolean save)
	{
		if (forbiddenItems == null)
			return;

		if (save)
		{
			lockedItems = player.getInventory().getLockItems();
			lockType = player.getInventory().getLockType();
		}

		for (ItemInstance item : player.getInventory().getPaperdollItems())
			if (item != null && ArrayUtils.contains(forbiddenItems, item.getItemId()))
				player.getInventory().unEquipItem(item);

		player.getInventory().lockItems(LockType.allow, forbiddenItems);
	}

	public final void lockItems(Player player, int item)
	{
		int[] locked = ArrayUtils.add(forbiddenItems, item);
		player.getInventory().lockItems(LockType.allow, locked);
	}

	private void unlockItems(Player player)
	{
		if (forbiddenItems == null)
			return;

		if (lockType == LockType.none)
			player.getInventory().unlock();
		else
		{
			for (ItemInstance item : player.getInventory().getPaperdollItems())
				if (item != null && ArrayUtils.contains(lockedItems, item.getItemId()))
					player.getInventory().unEquipItem(item);

			player.getInventory().lockItems(lockType, lockedItems);
		}
	}

	private void lockEnchant(Player player, boolean lock){
		if(lock){
			for (ItemInstance item : player.getInventory().getItems()) {
				if (!item.canBeEnchanted())
					continue;
				if (item.isWeapon()){
					if (item.getTemplate().isMagicWeapon())
						item.setTemporaryEnchant(magicWeaponEnchantLock);
					else
						item.setTemporaryEnchant(physWeaponEnchantLock);
				}
				if (item.isArmor())
					item.setTemporaryEnchant(armorEnchantLock);
				if (item.isAccessory())
					item.setTemporaryEnchant(accEnchantLock);
			}
		}
		else{
			for (ItemInstance item : player.getInventory().getItems())
				item.setTemporaryEnchant(-1);
		}

	}

	private class PlayerListener implements OnPlayerEnterListener, OnPlayerExitListener, OnTeleportListener, OnCurrentHpDamageListener, OnDeathFromUndyingListener, OnReviveListener, OnPlayerSummonServitorListener, OnMagicUseListener
	{
		@Override
		public void onPlayerEnter(Player player)
		{
			onPlayerReEnter(player);
		}

		@Override
		public void onPlayerExit(Player player)
		{
			if (isExited)
				return;

			isExited = true;
			restoreStateOnExit(true);
		}

		@Override
		public void onMagicUse(Creature actor, SkillEntry skill, Creature target, boolean alt)
		{
			if(skill!=null && skill.getId() == 2049 && maxBres >= 0){
				if(owner instanceof AbstractCustomBattleEvent)
					owner.incBlessRes(team, maxBres);
			}
		}

		@Override
		public void onTeleport(Player player, int x, int y, int z, Reflection reflection)
		{
			if (reflection.getId() != reflectionId)
			{
				if (player.getReflectionId() != reflectionId || isExited)
					return;

				isExited = true;
				player._stablePoint = null;
				restoreStateOnExit(false);
			}
			else
			{
				if (player.getReflectionId() == reflectionId || storedOnEnter || restoredOnExit)
					return;

				storedOnEnter = true;
				storeStateOnEnter(player);
				player.setTeam(team);
				player.setUndying(true);

				lockEnchant(player, true);

				final Servitor servitor = player.getServitor();
				if (servitor != null)
				{
					if (servitor.isPet())
					{
						servitor.unSummon(false, false);
						if (!owner.allowPets())
						{
							return;
						}
						servitor.addListener(petListeners);
						servitor.setUndying(true);
					}
					servitor.setTeam(team);
					summonedServitors.add(servitor.getId());
				}
			}
		}

		@Override
		public void onCurrentHpDamage(Creature actor, double damage, Creature attacker, SkillEntry skill)
		{
			if (!storedOnEnter || restoredOnExit || isDead || isExited)
				return;

			if (!owner.isInProgress())
				return;

			// считаем дамаг от простых ударов и атакующих скиллов
			if (actor == attacker || (skill != null && !skill.getTemplate().isOffensive()))
				return;

			damage += Math.min(actor.getCurrentHp(), damage);
		}

		@Override
		public void onDeathFromUndying(Creature actor, Creature killer)
		{
			if (!storedOnEnter || restoredOnExit)
				return;

			isDead = true;
			actor.doDie(null); // no karma or exp penalty
			final Player pk = killer.getPlayer();
			if (pk == null)
				return;

			TeamType pkTeam = pk.getTeam();
			if (pkTeam != TeamType.NONE && pkTeam != team && owner.equals(pk.getEvent(owner.getClass())))
			{
				matchRecord.deaths++;
				List<CustomPlayerSnapshotObject> team = owner.getObjects(pkTeam);
				team.stream().filter(pkEvent -> pkEvent.getPlayer() == pk).forEach(pkEvent -> pkEvent.matchRecord.kills++);

				owner.onDie(actor.getPlayer());
			}
			actor.broadcastCharInfo(); // отображение команды после смерти
		}

		@Override
		public void onRevive(Creature actor)
		{
			if (!storedOnEnter || restoredOnExit)
				return;

			if (actor.getReflectionId() == reflectionId)
			{
				isDead = false;
				actor.setUndying(true);
				actor.setTeam(team);
				actor.broadcastCharInfo();				
			}
		}

		@Override
		public void onSummonServitor(Player player, Servitor servitor)
		{
			if (!storedOnEnter || restoredOnExit)
				return;

			if (player.getReflectionId() == reflectionId)
			{
				if (servitor.isPet())
				{
					if (!owner.allowPets())
					{
						servitor.unSummon(false, false);
						return;
					}
					servitor.addListener(petListeners);
					servitor.setUndying(true);
					if (servitor.getCurrentHp() == 1.0) // restoreStateOnExit() оживляет мертвого пета, давая ему 1hp
						servitor.doDie(null);
				}
				if (!summonedServitors.contains(servitor.getId()))
				{
					summonedServitors.add(servitor.getId());
					if (removeBuffs)
						clearEffects(servitor);
				}
			}
		}
	}

	private class PetListener implements OnDeathFromUndyingListener, OnReviveListener
	{
		@Override
		public void onDeathFromUndying(Creature actor, Creature killer)
		{
			if (!storedOnEnter || restoredOnExit)
				return;

			actor.doDie(null); // no karma or exp penalty
			actor.broadcastCharInfo(); // отображение команды после смерти
		}

		@Override
		public void onRevive(Creature actor)
		{
			if (!storedOnEnter || restoredOnExit)
				return;

			if (actor.getReflectionId() == reflectionId)
			{
				actor.setUndying(true);
				actor.setTeam(team);
				actor.broadcastCharInfo();				
			}
		}
	}
}