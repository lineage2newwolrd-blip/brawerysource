package org.mmocore.gameserver.ai;

import org.jts.dataparser.data.holder.CategoryDataHolder;
import org.jts.dataparser.data.holder.SkillDataHolder;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.jts.dataparser.data.holder.skilldata.SkillData;
import org.jts.dataparser.data.holder.skilldata.abnormal.AbnormalType;
import org.mmocore.commons.lang.reference.HardReference;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.GameServer;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.configuration.config.AiConfig;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.mmocore.gameserver.data.xml.holder.SuperPointHolder;
import org.mmocore.gameserver.geoengine.GeoEngine;
import org.mmocore.gameserver.manager.GameTimeManager;
import org.mmocore.gameserver.model.Skill;
import org.mmocore.gameserver.model.Territory;
import org.mmocore.gameserver.model.instances.MonsterInstance;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.QuestEventType;
import org.mmocore.gameserver.model.quest.QuestState;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.network.lineage.components.ChatType;
import org.mmocore.gameserver.network.lineage.components.HtmlMessage;
import org.mmocore.gameserver.network.lineage.components.NpcString;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.Die;
import org.mmocore.gameserver.network.lineage.serverpackets.NpcSay;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.GameObject;
import org.mmocore.gameserver.object.Playable;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.object.components.npc.AggroList;
import org.mmocore.gameserver.object.components.npc.AggroList.HateInfo;
import org.mmocore.gameserver.object.components.npc.AggroList.AggroInfo;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoinCoordinate;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoint;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPointRail;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.taskmanager.AiTaskManager;
import org.mmocore.gameserver.utils.AiUtils;
import org.mmocore.gameserver.utils.ChatUtils;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.PositionUtils;
import org.mmocore.gameserver.utils.ai.Fstring;
import org.mmocore.gameserver.world.World;
import org.mmocore.gameserver.world.WorldRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class NpcAI extends CharacterAI implements Runnable {

	protected int _pathfindFails;
	public static final int TaskDefaultWeight = 1;
	public int start_x, start_y, start_z;
	protected State p_state = State.IDLE;
	/* Список заданий */
	protected final NavigableSet<Task> _tasks = new ConcurrentSkipListSet<>(TaskComparator.getInstance());
	private final ConcurrentMap<HardReference<? extends Creature>, Task> _attackTasks = new ConcurrentHashMap<>();
	private final ConcurrentMap<HardReference<? extends Creature>, Task> _castTasks = new ConcurrentHashMap<>();
	protected final Comparator<TargetContains> _nearestTargetComparator;
	protected int MAX_PURSUE_RANGE;

	protected long _lastFactionNotifyTime;
	protected long _minFactionNotifyInterval;
	protected long _lastActiveCheck;
	protected long _lastSocialAction;
	protected long _lastAttackAction;
	protected long _checkAggroTimestamp;
	protected static final Logger LOGGER = LoggerFactory.getLogger(NpcAI.class);
	/**
	 * Время актуальности состояния атаки
	 */
	protected long _attackTimeout = Long.MAX_VALUE;

	protected AtomicBoolean is_active_ai = new AtomicBoolean();
	protected ScheduledFuture<?> aiTask;
	// superpoint
	private int currentPointCounter = 0;
	private long superpoint_wait_time = 0;
	private boolean returningBack, lastPoint;

	protected Creature c_ai0, c_ai1, c_ai2, c_ai3, c_ai4, top_desire_target;
	protected int i_ai0, i_ai1, i_ai2, i_ai3, i_ai4;
	protected int i_quest0, i_quest1, i_quest2, i_quest9;
	protected Creature c_quest0, c_quest1, c_quest2;
	protected HateInfo h0 = AggroList.EMPTY_HATE_INFO;
	private boolean isAggresive;

	public Map<String, String> onShiftMap = new HashMap<>();

	/*
	 Задержка, перед переключением в активный режим после атаки, если цель не найдена (вне зоны досягаемости, убита, очищен хейт)
	 */
	public int getMaxAttackTimeout() {
		return 90000;
	}

	/**
	 * Задержка, перед переключением в режим поиска целей после атаки, если цель не найдена (вне зоны досягаемости, убита, очищен хейт)
	 */
	public int getMaxAggroTimeout() {
		return 3000;
	}

	public NpcAI(final NpcInstance actor) {
		super(actor);
		start_x = actor.getSpawnedLoc().x;
		start_y = actor.getSpawnedLoc().y;
		start_z = actor.getSpawnedLoc().z;
		_nearestTargetComparator = new NearestTargetComparator();
		MAX_PURSUE_RANGE = actor.getParameter("MaxPursueRange", actor.isRaid() ? AiConfig.MAX_PURSUE_RANGE_RAID : actor.isUnderground() ? AiConfig.MAX_PURSUE_UNDERGROUND_RANGE : AiConfig.MAX_PURSUE_RANGE);
		_minFactionNotifyInterval = actor.getParameter("FactionNotifyInterval", 5000);
	}

	protected enum State {
		IDLE,//пустой
		DO_NOTHING,//ничего не делает
		MOVE_AROUND,//перемещение около
		ATTACK,//атакует
		USE_SKILL,//кастует
		EFFECT_ACTION,//выполняет действие эффекта
		MOVE_TO,//передвигается

		//NOT ACTUALLY USED
		FLEE,//убегает
		FOLLOW,//привязан за кем то
		DECAYING,//исчезает

		//DEPRICATED
		MOVE_TO_WAY_POINT,//передвигается к точке маршрута
		//NOT EVER USED
		CHASE,//преследует
		GET_ITEM,//собирает предметы
		PET_DEFAULT,//стандартный пета
		MOVE_TO_TARGET,//передвигается к цели
		MOVE_SUPERPOINT;//передвигается по маршруту
	}

	protected enum MoveType {
		SLOW,
		FAST
	}

	protected enum DesireType {
		ATTACK,
		HEAL
	}

	protected enum DesireMove {
		STAND,
		MOVE_TO_TARGET
	}

	public enum TaskType {
		MOVE,
		ATTACK,
		CAST,
		SOCIAL,
		SUPERPOINT
	}

	@Override
	public void runImpl() {
		if (!is_active_ai.get())
			return;

		// проверяем, если NPC вышел в неактивный регион, отключаем AI
		if (!isGlobalAI() && System.currentTimeMillis() - _lastActiveCheck > 60000L) {
			_lastActiveCheck = System.currentTimeMillis();
			final NpcInstance actor = getActor();
			final WorldRegion region = actor == null ? null : actor.getCurrentRegion();
			if (region == null || !region.isActive()) {
				stopAITask();
				return;
			}
		}
		if (doTask())
			nextTask();
	}

	@Override
	public void startAITask() {
		if (is_active_ai.compareAndSet(false, true)) {
			aiTask = AiTaskManager.getInstance().scheduleAtFixedRate(this, 0L, AiConfig.AI_TASK_ACTIVE_DELAY);
		}
		setState(State.DO_NOTHING);
	}

	@Override
	public final void stopAITask() {
		if (is_active_ai.compareAndSet(true, false)) {
			aiTask.cancel(false);
			aiTask = null;
		}
		setState(State.IDLE);
	}

	@Override
	public boolean isActive() {
		return is_active_ai.get();
	}

	protected void clearTasks() {
		_tasks.clear();
		_attackTasks.clear();
		_castTasks.clear();
		NpcInstance actor = getActor();
		actor.stopMove();
		actor.getAggroList().clear(true);
		setAttackTimeout(Long.MAX_VALUE);
		setAttackTarget(null);
		actor.setWalking();
		EquipItem(0);
	}

	protected void clearVars() {
		NpcInstance actor = getActor();
		currentPointCounter = 0;
		superpoint_wait_time = 0;
		returningBack = false;
		lastPoint = false;
		c_ai0 = null;
		c_ai1 = null;
		c_ai2 = null;
		c_ai3 = null;
		c_ai4 = null;
		top_desire_target = null;
		h0 = AggroList.EMPTY_HATE_INFO;
		c_quest0 = null;
		c_quest1 = null;
		c_quest2 = null;
		i_ai0 = 0;
		i_ai1 = 0;
		i_ai2 = 0;
		i_ai3 = 0;
		i_ai4 = 0;
		i_quest0 = 0;
		i_quest1 = 0;
		i_quest2 = 0;
		i_quest9 = 0;
		actor.setTargetable(actor.getParameter(NpcInstance.TARGETABLE, true));
		actor.setShowName(actor.getParameter(NpcInstance.SHOW_NAME, true));
	}

	protected void nextTask() {
		removeTask(_tasks.pollFirst());
	}

	public void removeTask(final Task task) {
		if (task == null)
			return;
		if (task.type == TaskType.CAST)
			_castTasks.remove(task.target);
		if (task.type == TaskType.ATTACK)
			_attackTasks.remove(task.target);
		_tasks.remove(task);
	}


	protected boolean doTask() {
		final NpcInstance actor = getActor();
		if (!actor.isVisible() || actor.isDead() || actor.isAttackingNow() || actor.isCastingNow() || actor.isActionsDisabled() || actor.isAfraid()) {
			return false;
		}
		final long now = System.currentTimeMillis();
		if (_tasks.size() == 0) {
			if (isAggresive && now - _checkAggroTimestamp > AiConfig.AGGRO_CHECK_INTERVAL && _lastAttackAction < now) {
				_checkAggroTimestamp = now;
				int aggroRange = Math.max(actor.getAggroRange(), actor.param1);
				if (actor.isBoss() || actor.hasPrivates())
					aggroRange = Math.min(aggroRange, 3000);
				else
					aggroRange = Math.min(aggroRange, 450);
				if (aggroRange > 0)
					LookNeighbor(aggroRange);
			} else if (_lastSocialAction < now && !actor.isInCombat()) {
				clearTasks();
				setState(State.DO_NOTHING);
				notifyEvent(CtrlEvent.EVT_NO_DESIRE);
			}
			return false;
		}

		final Task currentTask = _tasks.first();
		if (currentTask == null)
			return true;

		switch (currentTask.type) {
			// Задание "прибежать в заданные координаты"
			case MOVE: {
				if (actor.isMovementDisabled() || actor.isMoving) {
					return false;
				}

				if (_lastSocialAction > now) {
					return false;
				}

				if (actor.isInRange(currentTask.loc, 20)) {
					notifyEvent(CtrlEvent.EVT_ARRIVED, currentTask.loc.x, currentTask.loc.y, currentTask.loc.z);
					return true;
				}

				if (currentTask.state != null)
					setState(currentTask.state);

				if (_pathfindFails > 5) {
					clientStopMoving();
					_pathfindFails = 0;
					actor.teleToLocation(currentTask.loc);
					notifyEvent(CtrlEvent.EVT_ARRIVED, currentTask.loc.x, currentTask.loc.y, currentTask.loc.z);
					return true;
				}

				if (currentTask.moveSpeed != null)
					ChangeMoveType(currentTask.moveSpeed);

				if (!actor.moveToLocation(currentTask.loc, 0, true)) {
					_pathfindFails++;
					return false;
				}
				if (_lastSocialAction < now)
					_lastSocialAction = System.currentTimeMillis() + currentTask.delay * 1000;
			}
			break;
			// Задание "SUPERPOINT"
			case SUPERPOINT: {
				if (actor.isMovementDisabled() || actor.isMoving) {
					return false;
				}

				if (_lastSocialAction > now) {
					return false;
				}

				if (currentTask.loc == null || actor.isInRange(currentTask.loc, 20)) {

					if (now < superpoint_wait_time)
						return false;


					final List<SuperPoinCoordinate> coords = currentTask.superpoint.getCoordinats();
					final SuperPoinCoordinate currentPointCoordinate = coords.get(currentPointCounter);
					if (currentPointCoordinate.getDelayInSec() > 0 && superpoint_wait_time == 0) {
						superpoint_wait_time = now + (currentPointCoordinate.getDelayInSec() * 1000L);
					}

					if (currentPointCoordinate.getMsgId() > 0) {
						ChatUtils.say(actor, currentPointCoordinate.getMsgRadius(), currentPointCoordinate.getMsgChatType(), NpcString.valueOf(currentPointCoordinate.getMsgId()));
					}

					if (currentTask.loc != null)
						notifyEvent(CtrlEvent.EVT_NODE_ARRIVED, currentPointCounter, 0, currentPointCoordinate.getSocialId(), lastPoint);

					//superpoint_wait_time = 0;

					if (currentTask.superpoint.isRunning())
						actor.setRunning();
					else
						actor.setWalking();

					/*switch (currentTask.superpoint.getRail()) {
						case FollowRail: {
							addTaskMove(currentPointCoordinate, currentTask.superpoint, 10);
							if (returningBack)
								currentPointCounter--;
							else
								currentPointCounter++;

							if (currentPointCounter >= coords.size() - 1)
								returningBack = true;

							if (currentPointCounter == 0)
								returningBack = false;
							break;
						}
						case Random: {
							currentPointCounter = Rnd.get(coords.size());

							addTaskMove(currentPointCoordinate, currentTask.superpoint, 10);
							break;
						}
						case FollowRail_Restart: {
							addTaskMove(currentPointCoordinate, currentTask.superpoint, 10);
							currentPointCounter++;

							if (currentPointCounter > coords.size() - 1)
								currentPointCounter = 0;
							break;
						}
						case FollowRail_Restart_Teleport: {
							if (currentPointCounter > coords.size() - 1) {
								actor.teleToLocation(coords.get(0));
								currentPointCounter = 0;
								break;
							}
							addTaskMove(currentPointCoordinate, currentTask.superpoint, 10);
							currentPointCounter++;


							break;
						}
					}*/


					switch (currentTask.superpoint.getType()) {
						case LENGTH: {
							if (returningBack)
								currentPointCounter--;
							else
								currentPointCounter++;

							if (currentPointCounter >= coords.size() - 1) {
								returningBack = true;
								lastPoint = true;
							} else if (lastPoint)
								lastPoint = false;

							if (currentPointCounter == 0)
								returningBack = false;
							break;
						}
						case ROUND: {
							currentPointCounter++;

							if (currentPointCounter >= coords.size() - 1) {
								currentPointCounter = 0;
								lastPoint = true;
							} else if (lastPoint)
								lastPoint = false;
							break;
						}
						case RANDOM: {
							currentPointCounter = Rnd.get(coords.size() - 1);
							break;
						}
						case DELETE: {
							if (lastPoint) {
								actor.deleteMe();
								if (currentTask.loc != null)
									notifyEvent(CtrlEvent.EVT_NODE_ARRIVED, currentPointCounter, 0, currentPointCoordinate.getSocialId(), lastPoint);
								return true;
							}
							if (currentPointCounter >= coords.size() - 1)
								lastPoint = true;
							else if (lastPoint)
								lastPoint = false;
							currentPointCounter++;
							break;
						}
						case FINISH: {
							currentPointCounter++;
							if (currentPointCounter >= coords.size() - 1) {
								actor.stopMove();
							} else if (lastPoint)
								lastPoint = false;
							break;
						}
					}
					currentTask.loc = currentPointCoordinate;
				}

				if (currentTask.state != null)
					setState(currentTask.state);

				if (_pathfindFails > 5) {
					clientStopMoving();
					_pathfindFails = 0;
					actor.teleToLocation(currentTask.loc);
					final SuperPoinCoordinate currentPointCoordinate = currentTask.superpoint.getCoordinats().get(currentPointCounter);
					notifyEvent(CtrlEvent.EVT_NODE_ARRIVED, currentPointCounter, 0, currentPointCoordinate.getSocialId(), lastPoint);
					return true;
				}

				if (currentTask.moveSpeed != null)
					ChangeMoveType(currentTask.moveSpeed);

				if (!actor.moveToLocation(currentTask.loc, 0, true)) {
					_pathfindFails++;
					return false;
				}
			}
			break;
			// Задание "добежать - ударить"
			case ATTACK: {
				final Creature target = currentTask.target.get();
				if (now > getAttackTimeout() || !checkTarget(target, MAX_PURSUE_RANGE)) {
					RemoveAllMoveDesire();
					if (target != null)
						notifyEvent(CtrlEvent.EVT_ATK_FINISHED, target);
					return true;
				}

				setState(State.ATTACK);
				actor.setRunning();
				setAttackTarget(target);
				_lastAttackAction = now + getMaxAggroTimeout();

				if (actor.getRealDistance3D(target) <= actor.getPhysicalAttackRange() + 40 && GeoEngine.canSeeTarget(actor, target, false)) {
					clientStopMoving();
					_pathfindFails = 0;
					actor.doAttack(target);
					return false;
				}

				if (currentTask.moveType == DesireMove.STAND)
					return false;

				if (actor.isMovementDisabled()) {
					return false;
				}

				return !tryMoveToTarget(target);
			}
			// Задание "добежать - применить скилл"
			case CAST: {
				final Creature target = currentTask.target.get();
				if (currentTask.desireType == DesireType.ATTACK) {
					if (now > getAttackTimeout() || !checkTarget(target, MAX_PURSUE_RANGE)) {
						RemoveAllMoveDesire();
						return true;
					}
				} else if (currentTask.desireType == DesireType.HEAL && (target == null || target.isAlikeDead() || !actor.isInRange(target, 2000))) {
					RemoveAllMoveDesire();
					return true;
				}

				setState(State.USE_SKILL);
				actor.setRunning();
				_lastAttackAction = now + getMaxAggroTimeout();

				if (actor.isMuted(currentTask.skill) || actor.isSkillDisabled(currentTask.skill) || currentTask.skill.isDisabled()) {
					return false;
				}

				final Skill skill = currentTask.skill.getTemplate();
				final boolean isAoE = skill.getTargetType() == Skill.SkillTargetType.TARGET_AURA;
				final int castRange = skill.getAOECastRange();


				if (currentTask.desireType == DesireType.ATTACK)
					setAttackTarget(target);

				if (actor.getRealDistance3D(target) <= castRange + 60 && GeoEngine.canSeeTarget(actor, target, false)) {
					clientStopMoving();
					_pathfindFails = 0;
					actor.doCast(currentTask.skill, isAoE ? actor : target, !target.isPlayable());
					return true;
				}

				if (currentTask.moveType == DesireMove.STAND) {
					return false;
				}

				if (actor.isMovementDisabled()) {
					return false;
				}
				return !tryMoveToTarget(target);
			}
			// Задание "выполнить соц. действие"
			case SOCIAL: {
				if (actor.isActionsDisabled() || actor.isMoving || actor.isInCombat()) {
					return false;
				}
				if (_lastSocialAction < now) {
					setState(State.EFFECT_ACTION);
					actor.broadcastPacket(new SocialAction(actor.getObjectId(), currentTask.social));
					_lastSocialAction = now + currentTask.delay;
					return true;
				}
				return false;
			}
		}

		return false;
	}

	public static class Task {
		public TaskType type;
		public SkillEntry skill;
		public HardReference<? extends Creature> target;
		public Location loc;
		public SuperPoint superpoint;
		public int social;
		public int delay;
		public int weight = TaskDefaultWeight;
		public DesireMove moveType;
		public MoveType moveSpeed;
		public State state;
		public DesireType desireType;
		public long addedTime;
	}

	private static class TaskComparator implements Comparator<Task> {
		private static final Comparator<Task> instance = new TaskComparator();

		public static Comparator<Task> getInstance() {
			return instance;
		}

		@Override
		public int compare(final Task o1, final Task o2) {
			if (o1 == null || o2 == null) {
				return 0;
			}
			int diff = o2.weight - o1.weight;
			if (diff == 0)
				return (int) (o2.addedTime - o1.addedTime);
			return diff;
		}
	}

	public void setState(final State state) {
		if (p_state != state) {
			p_state = state;
		}
	}

	public String stateAsString() {
		return p_state.toString();
	}

	public void addTaskAttack(final Creature target, final DesireMove moveType, final int weight) {
		final Task task = new Task();
		task.type = TaskType.ATTACK;
		task.target = target.getRef();
		task.moveType = moveType;
		task.weight = weight;
		task.addedTime = System.currentTimeMillis();
		_tasks.add(task);
		_attackTasks.put(task.target, task);
	}

	public void addTaskMove(final Location loc, final int delay, final State state, final MoveType moveSpeed, final int weight) {
		final Task task = new Task();
		task.type = TaskType.MOVE;
		task.loc = loc;
		task.weight = weight;
		task.state = state;
		task.moveSpeed = moveSpeed;
		task.delay = delay;
		task.addedTime = System.currentTimeMillis();
		_tasks.add(task);
	}

	public void addTaskSuperpoint(final SuperPoint superpoint, final MoveType moveSpeed, final int weight) {
		final Task task = new Task();
		task.type = TaskType.SUPERPOINT;
		task.superpoint = superpoint;
		task.state = State.MOVE_SUPERPOINT;
		task.moveSpeed = moveSpeed;
		task.weight = weight;
		task.addedTime = System.currentTimeMillis();
		_tasks.add(task);
	}

	public void addTaskSocial(final int socialType, final int socialDelay, final int weight) {
		final Task task = new Task();
		task.type = TaskType.SOCIAL;
		task.social = socialType;
		task.delay = socialDelay;
		task.weight = weight;
		task.addedTime = System.currentTimeMillis();
		_tasks.add(task);
	}

	public void addTaskCast(final Creature target, final SkillEntry skill, final DesireType desireType, final DesireMove moveType, final int weight) {
		final Task task = new Task();
		task.type = TaskType.CAST;
		task.target = target.getRef();
		task.skill = skill;
		task.desireType = desireType;
		task.moveType = moveType;
		task.weight = weight;
		task.addedTime = System.currentTimeMillis();
		_tasks.add(task);
		_castTasks.put(task.target, task);
	}

	@Override
	public NpcInstance getActor() {
		return (NpcInstance) _actor;
	}

	private static int calcWeight(double point, int currentWeight, int limit) {
		return point + currentWeight > limit ? limit : (int) (point + currentWeight);
	}

	protected void AddAttackDesire(Creature target, DesireMove moveType, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		int weight;
		Task task = _attackTasks.get(target.getRef());
		if (task != null) {
			weight = calcWeight(point, task.weight, Integer.MAX_VALUE);
			removeTask(task);
		} else
			weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;

		getActor().getAggroList().addDamageHate(target, 0, (int) point);
		setAttackTimeout(getMaxAttackTimeout() + System.currentTimeMillis());
		addTaskAttack(target, moveType, weight);
	}

	protected void AddUseSkillDesire(Creature target, int pts_skill_id, DesireType desireType, DesireMove moveType, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		SkillEntry using_skill = SkillTable.getInstance().getSkillEntry(pts_skill_id);
		if (using_skill == null)
			return;
		Task task = _castTasks.get(target.getRef());
		if (task != null) {
			removeTask(task);
		}
		int weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;

		if (desireType == DesireType.ATTACK) {
			AggroInfo ai = getActor().getAggroList().get(target);
			if (ai == null || ai.hate == 0) {
				getActor().getAggroList().addDamageHate(target, 0, 1);
			}
			setAttackTimeout(getMaxAttackTimeout() + System.currentTimeMillis());
		}
		addTaskCast(target, using_skill, desireType, moveType, weight);
	}

	protected void AddMoveToDesire(int x, int y, int z, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		int weight;
		Task task = _tasks.stream().filter(t -> t.type == TaskType.MOVE).findFirst().orElse(null);
		if (task != null) {
			weight = calcWeight(point, task.weight, Integer.MAX_VALUE);
			removeTask(task);
		} else
			weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;
		addTaskMove(new Location(x, y, z), 0, State.MOVE_TO, null, weight);
	}

	protected void AddFollowDesire(NpcInstance boss, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		if (boss == null)
			return;
		int weight;
		Task task = _tasks.stream().filter(t -> t.type == TaskType.MOVE).findFirst().orElse(null);
		if (task != null) {
			weight = calcWeight(point, task.weight, Integer.MAX_VALUE);
			removeTask(task);
		} else
			weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;
		addTaskMove(boss.getMinionPosition(), 0, State.FOLLOW, boss.isRunning() ? MoveType.FAST : MoveType.SLOW, weight);

	}

	protected void AddMoveAroundDesire(int time, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		if (!Rnd.chance(5))
			return;
		int weight;
		Task task = _tasks.stream().filter(t -> t.type == TaskType.MOVE).findFirst().orElse(null);
		if (task != null) {
			weight = calcWeight(point, task.weight, Integer.MAX_VALUE);
			removeTask(task);
		} else
			weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;
		NpcInstance actor = getActor();
		if (actor.getDistance(actor.getSpawnedLoc()) > AiConfig.MAX_DRIFT_RANGE) {
			addTaskMove(Location.findPositionInRange(getActor(), getActor().getSpawnedLoc(), 0, AiConfig.MAX_DRIFT_RANGE), time, State.MOVE_AROUND, MoveType.SLOW, weight);
		} else
			addTaskMove(Location.findPointToStay(getActor(), getActor().getSpawnedLoc(), 0, AiConfig.MAX_DRIFT_RANGE), time, State.MOVE_AROUND, MoveType.SLOW, weight);
	}

	protected void AddMoveAroundLimitedDesire(int time, double point, int limit) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		if (!Rnd.chance(5))
			return;
		int weight;
		Task task = _tasks.stream().filter(t -> t.type == TaskType.MOVE).findFirst().orElse(null);
		if (task != null) {
			weight = calcWeight(point, task.weight, limit);
			removeTask(task);
		} else
			weight = calcWeight(point, 0, limit);
		if (weight <= 0)
			return;
		NpcInstance actor = getActor();
		if (actor.getDistance(actor.getSpawnedLoc()) > AiConfig.MAX_DRIFT_RANGE) {
			addTaskMove(Location.findPositionInRange(getActor(), getActor().getSpawnedLoc(), 0, AiConfig.MAX_DRIFT_RANGE), time, State.MOVE_AROUND, MoveType.SLOW, weight);
		} else
			addTaskMove(Location.findPointToStay(getActor(), getActor().getSpawnedLoc(), 0, AiConfig.MAX_DRIFT_RANGE), time, State.MOVE_AROUND, MoveType.SLOW, weight);
	}

	protected void AddEffectActionDesire(Creature target, int type, int action_time, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		int weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;
		addTaskSocial(type, action_time < 100 ? action_time * 1000 : action_time, weight);
	}

	protected void AddMoveSuperPointDesire(String superPointName, SuperPointRail type, double point) {
		AddMoveSuperPointDesire(superPointName, type, point, 1);
	}

	protected void AddMoveSuperPointDesire(String superPointName, SuperPointRail type, double point, double isRunning) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		SuperPoint superpoint = SuperPointHolder.getInstance().getSuperPointsByName(superPointName);
		if (superpoint == null)
			return;
		superpoint.setRail(type);
		int weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;
		addTaskSuperpoint(superpoint, isRunning > 0 ? MoveType.FAST : MoveType.SLOW, weight);
	}

	protected void AddFleeDesire(Creature attacker, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		int weight;
		Task task = _tasks.stream().filter(t -> t.type == TaskType.MOVE).findFirst().orElse(null);
		if (task != null) {
			weight = calcWeight(point, task.weight, Integer.MAX_VALUE);
			removeTask(task);
		} else
			weight = calcWeight(point, 0, Integer.MAX_VALUE);
		final int range = (int) (0.71 * getActor().calculateAttackDelay() / 1000 * getActor().getMoveSpeed());
		if (weight <= 0)
			return;
		addTaskMove(Location.findFleePosition(getActor(), attacker, range), 0, State.FLEE, null, weight);
	}

	protected void AddFleeDesireEx(Creature attacker, int distance, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		int weight;
		Task task = _tasks.stream().filter(t -> t.type == TaskType.MOVE).findFirst().orElse(null);
		if (task != null) {
			weight = calcWeight(point, task.weight, Integer.MAX_VALUE);
			removeTask(task);
		} else
			weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;
		addTaskMove(Location.findFleePosition(getActor(), attacker, distance), 0, State.FLEE, null, weight);
	}

	protected void AddMoveFreewayDesire(int id, int type, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		SuperPoint superpoint = SuperPointHolder.getInstance().getSuperPointsByName(String.valueOf(id));
		if (superpoint == null)
			return;

		superpoint.setRail(SuperPointRail.values()[type]);
		int weight = calcWeight(point, 0, Integer.MAX_VALUE);
		if (weight <= 0)
			return;
		addTaskSuperpoint(superpoint, null, weight);
	}

	protected void AddDoNothingDesire(int time, double point) {
		if (_actor == null || _actor.isDead() || !_actor.isVisible())
			return;
		if (_lastSocialAction < System.currentTimeMillis())
			_lastSocialAction = System.currentTimeMillis() + time * 1000;
	}

	protected void RemoveAllAttackDesire() {
		_attackTasks.clear();
		_tasks.stream()
				.filter(task -> task.type == TaskType.ATTACK || task.type == TaskType.CAST)
				.forEach(this::removeTask);
	}

	protected void RemoveAttackDesire(int objectId) {
		_tasks.stream()
				.filter(task -> (task.type == TaskType.ATTACK || task.type == TaskType.CAST) && task.target.get().getObjectId() == objectId)
				.forEach(this::removeTask);
	}

	protected void RemoveAllCastDesire() {
		_castTasks.clear();
		_tasks.stream()
				.filter(task -> task.type == TaskType.CAST)
				.forEach(this::removeTask);
	}

	protected void RemoveAllMoveDesire() {
		_tasks.stream()
				.filter(task -> task.type == TaskType.MOVE)
				.forEach(this::removeTask);
	}

	protected void RemoveAllDesire() {
		_tasks.clear();
		_attackTasks.clear();
		_castTasks.clear();
	}

	protected void RemoveDesire(int type) {
		if (type == 11)
			RemoveAllCastDesire();
	}

	protected void RandomizeAttackDesire() {
		List<Task> tasks = new ArrayList<>(_tasks);
		Collections.shuffle(tasks, new Random());
		int size = tasks.size();
		_tasks.stream()
				.filter(task -> task.type == TaskType.ATTACK || task.type == TaskType.CAST)
				.forEach(task -> {
					removeTask(task);
					addTaskAttack(task.target.get(), task.moveType, tasks.get(Rnd.get(size)).weight);
				});
	}

	protected int Skill_GetAbnormalType(int value) {
		SkillData skillInfo = SkillDataHolder.getInstance().getSkillInfo(value);
		if (skillInfo != null && skillInfo.abnormal_type != null)
			return AbnormalType.valueFrom(skillInfo.abnormal_type).ordinal();
		return 0;
	}

	protected int Skill_GetEffectPoint(int pts_skill_id) {
		SkillData skillInfo = SkillDataHolder.getInstance().getSkillInfo(pts_skill_id);
		if (skillInfo != null)
			return -skillInfo.effect_point;
		return 0;

	}

	protected int Skill_GetConsumeMP(int pts_skill_id) {
		SkillData skillInfo = SkillDataHolder.getInstance().getSkillInfo(pts_skill_id);
		if (skillInfo != null)
			return skillInfo.mp_consume1 + skillInfo.mp_consume2;
		return 0;
	}

	protected int Skill_GetConsumeHP(int pts_skill_id) {
		SkillData skillInfo = SkillDataHolder.getInstance().getSkillInfo(pts_skill_id);
		if (skillInfo != null)
			return skillInfo.hp_consume;
		return 0;
	}

	protected int Skill_InReuseDelay(int pts_skill_id) {
		return getActor().isSkillDisabled(pts_skill_id) ? 1 : 0;
	}

	protected void Dispel(Creature actor, int abnormal_type) {
		actor.getEffectList().stopEffects(AbnormalType.values()[abnormal_type]);
	}

	protected void ShowPage(final Player player, final String fileName) {
		if (player == null) {
			return;
		}

		final HtmlMessage npcReply = new HtmlMessage(5);
		npcReply.setFile(fileName);
		player.sendPacket(npcReply);
	}

	protected void ShowSystemMessage(final Creature target, final int message_id) {
		if (target == null || target.getPlayer() == null)
			return;
		SystemMsg msg = SystemMsg.valueOf(message_id);
		if (msg == null)
			return;
		target.getPlayer().sendPacket(new SystemMessage(msg));
	}

	protected void ShowSystemMessageStr(final Creature target, final Fstring fstring) {
		if (target == null || target.getPlayer() == null)
			return;
		SystemMsg template = SystemMsg.valueOf(fstring.getId());
		if (template == null)
			return;
		SystemMessage message = new SystemMessage(template);
		for (String param : fstring.getParams())
			if (param != null && !param.isEmpty())
				message.addString(param);
		target.getPlayer().sendPacket(message);
	}

	protected void Shout(Fstring fstring) {
		ChatUtils.say(getActor(), ServerConfig.CHAT_RANGE, new NpcSay(getActor(), ChatType.SHOUT, fstring.getId(), fstring.getParams()));
	}

	protected void Shout(String text) {
		ChatUtils.say(getActor(), ServerConfig.CHAT_RANGE, new NpcSay(getActor(), ChatType.SHOUT, NpcString.NONE, text));
	}

	protected void Say(Fstring fstring) {
		ChatUtils.say(getActor(), ServerConfig.CHAT_RANGE, new NpcSay(getActor(), ChatType.NPC_ALL, fstring.getId(), fstring.getParams()));
	}

	protected void Say(String text) {
		ChatUtils.say(getActor(), ServerConfig.CHAT_RANGE, new NpcSay(getActor(), ChatType.NPC_ALL, NpcString.NONE, text));
	}

	protected void SayFStr(int FString, String arg0, String arg1, String arg2, String arg3, String arg4) {
		ChatUtils.say(getActor(), NpcString.valueOf(FString), arg0, arg1, arg2, arg3, arg4);
	}

	protected boolean IsMyBossAlive() {
		NpcInstance master = getActor().getLeader();
		return master != null && master.isVisible() && !master.isDead();
	}

	protected boolean IsNullString(String arg) {
		return arg == null || arg.isEmpty();
	}

	protected boolean IsNullCreature(Creature arg) {
		return arg == null;
	}

	protected boolean IsNullHateInfo(HateInfo arg) {
		return arg == null || arg == AggroList.EMPTY_HATE_INFO;
	}

	protected boolean IsNullParty(Party arg) {
		return arg == null;
	}

	protected boolean IsInCategory(Category category, int class_id) {
		List<Integer> lst = CategoryDataHolder.getInstance().getClasses(category);
		if (lst != null && !lst.isEmpty())
			return lst.contains(class_id);
		return false;
	}

	protected boolean IsInCategory(int categoryId, int class_id) {
		if (categoryId < 0 || categoryId >= Category.values().length)
			return false;
		return IsInCategory(Category.values()[categoryId], class_id);
	}

	protected boolean IsInCombatMode(Creature target) {
		return target.isInCombat();
	}

	protected boolean InMyTerritory(Creature target) {
		NpcInstance actor = getActor();
		Territory territory = actor.getTerritory();
		return territory == null || territory.isInside(target.getX(), target.getY(), target.getZ());
	}

	protected boolean IsWeaponEquippedInHand(Creature target) {
		return target.getWeaponType() != 0;
	}

	protected void EquipItem(int wepId) {
		getActor().setActiveWeaponItem(wepId);
	}

	protected void SetEnchantOfWeapon(int value) {
		getActor().setActiveWeaponEchant(value);
	}

	protected void CreatePrivates(String privates) {
		NpcInstance actor = getActor();
		try {
			if (actor.hasPrivates()) {
				actor.getPrivatesList().useSpawnPrivates();
			} else {
				actor.getPrivatesList().createPrivatesPTS(privates);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("Cannot cast Privates, npcId=" + getActor().getNpcId() + " : " + privates);
		}
	}

	protected void CreateOnePrivate(int classId, String ai, int weight_point, int respawn_time) {
		NpcInstance actor = getActor();
		if (!actor.hasPrivates()) {
			actor.getPrivatesList().createOnePrivate(classId - 1000000, respawn_time, weight_point);
		}
	}

	protected void CreateOnePrivateEx(int npcId, String ai, int weight_point, int delay, int x, int y, int z, int heading, int param1, int param2, int param3) {
		if (npcId > 1000000)
			npcId -= 1000000;
		getActor().getPrivatesList().createOnePrivateEx(npcId, weight_point, delay, x, y, z, heading, param1, param2, param3);
	}

	protected void UseSoulShot(int dmg_mod) {
		getActor().setChargedSoulShot(dmg_mod > 0 ? ItemInstance.CHARGED_SOULSHOT : 0);
	}

	protected void UseSpiritShot(int dmg_mod, float speed_bonus, float heal_bonus) {
		if (speed_bonus > 50f || heal_bonus > 2f)
			getActor().setChargedSoulShot(dmg_mod > 0 ? ItemInstance.CHARGED_BLESSED_SPIRITSHOT : 0);
		else
			getActor().setChargedSoulShot(dmg_mod > 0 ? ItemInstance.CHARGED_SPIRITSHOT : 0);
	}

	protected void Despawn() {
		getActor().doDie(_actor);
		try {
			getActor().decayOrDelete();
		} catch (Exception e) {
			LOGGER.info("DespawnError NpcId:" + getActor().getNpcId() + " spawnedLoc:" + getActor().getSpawnedLoc());
		}
	}

	protected void InstantTeleport(Creature actor, int x, int y, int z) {
		actor.teleToLocation(x, y, z, 0);
	}

	protected void TeleportTo(Creature attacker, Player player) {
		if (player != null)
			attacker.teleToLocation(player.getLoc());
	}

	protected void UseSkill(Creature creature, int pts_skill_id) {
		if (creature == null)
			return;
		SkillEntry using_skill = SkillTable.getInstance().getSkillEntry(pts_skill_id);
		if (using_skill == null)
			return;
		getActor().altUseSkill(using_skill, creature, false);
	}

	protected void InstantRandomTeleportInMyTerritory() {
		int geoIndex = getActor().getGeoIndex();
		Location loc = getActor().getTerritory() != null ? getActor().getTerritory().getRandomLoc(geoIndex) : getActor().getSpawnedLoc();
		getActor().teleToLocation(Location.findPointToStay(loc, 150, geoIndex));
	}

	protected double DistFromMe(Creature target) {
		return getActor().getDistance(target);
	}

	protected void Suicide() {
		getActor().doDie(_actor);
	}

	protected void ChangeStatus(Status status) {
		switch (status) {
			case NAME_INVISIBLE:
				getActor().setShowName(false);
				break;
			case NAME_VISIBLE:
				getActor().setShowName(true);
				break;
			case TARGET_DISABLE:
				getActor().setTargetable(false);
				break;
			case TARGET_ENABLE:
				getActor().setTargetable(true);
				break;
		}
	}

	protected int GetOneTimeQuestFlag(Creature attacker, int quest_id) {
		if (attacker instanceof Player)
			return ((Player) attacker).isQuestCompleted(quest_id) ? 1 : 0;
		return 0;
	}

	protected Creature getTopHated() {
		Creature s = getActor().getAggroList().getMostHated();
		if (s != null)
			return s;
		return top_desire_target;
	}

	protected HateInfo GetMaxHateInfo(int unk0) {
		HateInfo h = getActor().getAggroList().getTopHateInfo();
		if (h != null)
			return h;
		return h0;
	}

	protected void AddHateInfo(Creature creature, int hate, int unk0, int unk1, int unk2) {
		getActor().getAggroList().addDamageHate(creature, 0, hate);
		setAttackTimeout(getMaxAttackTimeout() + System.currentTimeMillis());
	}

	protected void RemoveAllHateInfoIF(int unk0, int unk1) {
		switch (unk0) {
			case 0://SELF_AGGR
				//getActor().getAggroList().clear(true);
				//h0 = AggroList.EMPTY_HATE_INFO;
				break;
			case 1://CLAN_AGGR
				break;
			case 3://RANGE ? AGR value?
				//getActor().getAggroList().clear(unk1);
				//h0 = AggroList.EMPTY_HATE_INFO;
				break;
			default:
				return;
		}
	}

	protected void RemoveHateInfoByCreature(Creature target) {
		if (target != null)
			getActor().getAggroList().remove(target, true);
	}

	protected int GetHateInfoCount() {
		return getActor().getAggroList().size();
	}

	protected int GetLifeTime() {
		return (int) (getActor().getTimeAlive() / 1000);
	}

	protected Creature GetLastAttacker() {
		return getActor().getLastAttacker();
	}

	protected Creature Pledge_GetLeader(Creature member) {
		return member == null ? null : member.getClan() == null ? null : member.getClan().getLeader().getPlayer();
	}

	protected Creature GetMemberOfParty(Party party, int index) {
		return party == null ? null : party.getPartyMembers().get(index);
	}

	protected int HasAcademyMaster(Creature target) {
		if (target == null || target.getPlayer() == null)
			return 0;
		Player player = target.getPlayer();
		if (player.getClanMembership() != null)
			return player.getClanMembership().getSponsor() > 0 ? 1 : 0;
		return 0;
	}

	protected Creature GetAcademyMaster(Creature target) {
		if (target == null || target.getPlayer() == null)
			return null;
		Player player = target.getPlayer();
		if (player.getClanMembership() != null) {
			int objId = player.getClanMembership().getSponsor();
			return objId > 0 ? AiUtils.GetCreatureFromIndex(objId) : null;
		}
		return null;
	}

	protected Creature Maker_FindNpcByKey(int objId) {
		return AiUtils.GetCreatureFromIndex(objId);
	}


	protected int GetPathfindFailCount() {
		return _pathfindFails;
	}

	protected int GetAngleFromTarget(Creature target) {
		if (target == null) {
			return 0;
		}
		final double angleChar;
		final double angleTarget;
		double angleDiff;
		angleChar = PositionUtils.calculateHeadingFrom(getActor(), target);
		angleTarget = target.getHeading();
		angleDiff = angleChar - angleTarget;
		int MAX_HEADING = 65536;
		if (angleDiff < 0) {
			angleDiff += MAX_HEADING;
		} else if (angleDiff >= MAX_HEADING) {
			angleDiff -= MAX_HEADING;
		}
		return (int) angleDiff;
	}

	protected int GetDirection(Creature actor) {
		if (actor == null) {
			return 0;
		}
		return actor.getHeading();
	}

	protected int GetTimeHour() {
		return GameTimeManager.getInstance().getGameHour();
	}

	protected void LookNeighbor(int radius) {
		NpcInstance actor = getActor();
		List<TargetContains> targets = new ArrayList<>();
		List<Playable> chars;

		chars = World.getAroundPlayables(actor, radius, Math.min(radius, 450));
		for (Creature creature : chars) {
			if (creature == null) {
				continue;
			}
			double distance = actor.getDistance3D(creature);
			TargetContains target = new TargetContains(distance, creature);
			targets.add(target);
		}
		Collections.sort(targets, _nearestTargetComparator);
		for (TargetContains cha : targets) {
			if (cha.getCreature() != null && checkAggression(cha.getCreature())) {
				notifyEvent(CtrlEvent.EVT_SEE_CREATURE, cha.getCreature());
			}
		}
	}

	/**
	 * Оповестить дружественные цели об атаке.
	 *
	 * @param attacker
	 * @param hate
	 */
	protected void notifyAttack(final Creature attacker, final double hate, final SkillEntry se) {
		final NpcInstance actor = getActor();
		int weight = hate > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) hate;
		final NpcInstance master = actor.getLeader();

		if (System.currentTimeMillis() - _lastFactionNotifyTime > _minFactionNotifyInterval) {
			_lastFactionNotifyTime = System.currentTimeMillis();


			//Оповестить лидера об атаке
			if (master != null && !master.isDead() && master.isVisible() && master.isInRange(actor, master.isRaid() ? 1500 : 600)) {
				master.getAI().notifyEvent(CtrlEvent.EVT_PARTY_ATTACKED, attacker, actor, weight, se);
			}
			//Оповестить своих минионов об атаке
			if (actor.hasPrivates()) {
				if (actor.getPrivatesList().hasAlivePrivates())
					actor.getPrivatesList().getAlivePrivates().stream().filter(minion -> minion != actor).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_ATTACKED, attacker, actor, weight, se));
			}
			if (actor.hasOnePrivateEx()) {
				if (actor.getPrivatesList().hasAliveOnePrivateEx())
					actor.getPrivatesList().getAliveOnePrivateEx().stream().filter(minion -> minion != actor).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_ATTACKED, attacker, actor, weight, se));
			}
			//Оповестить минионов лидера об атаке
			if (actor.getPrivateParty() != null) {
				actor.getPrivateParty().getAlivePrivates().stream().filter(minion -> minion != actor && minion.isInRange(actor, 600)).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_ATTACKED, attacker, actor, weight, se));
				actor.getPrivateParty().getAliveOnePrivateEx().stream().filter(minion -> minion != actor && minion.isInRange(actor, 600)).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_ATTACKED, attacker, actor, weight, se));
			}

			//Оповестить социальных мобов
			if (actor.getFaction().isNone()) {
				return;
			}
			final int range = actor.getFaction().getRange();
			World.getAroundNpc(actor).stream().filter(npc -> npc.isVisible() && !npc.isDead() && npc.isInFaction(actor) && npc.isInRangeZ(actor, range)).forEach(npc -> npc.getAI().notifyEvent(CtrlEvent.EVT_CLAN_ATTACKED, actor, attacker, weight, se));
		}
	}

	/**
	 * Оповестить дружественные цели о смерти.
	 *
	 * @param attacker
	 */
	protected void notifyDead(final Creature attacker) {
		final NpcInstance actor = getActor();
		final NpcInstance master = actor.getLeader();
		if (master != null && !master.isDead() && master.isVisible()) {
			master.getAI().notifyEvent(CtrlEvent.EVT_PARTY_DEAD, attacker, actor);
		}

		//Оповестить своих минионов об атаке
		if (actor.hasPrivates()) {
			if (actor.getPrivatesList().hasAlivePrivates())
				actor.getPrivatesList().getAlivePrivates().stream().filter(minion -> minion != actor).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_DEAD, attacker, actor));
		}
		if (actor.hasOnePrivateEx()) {
			if (actor.getPrivatesList().hasAliveOnePrivateEx())
				actor.getPrivatesList().getAliveOnePrivateEx().stream().filter(minion -> minion != actor).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_DEAD, attacker, actor));
		}
		//Оповестить минионов лидера об атаке
		if (actor.getPrivateParty() != null) {
			actor.getPrivateParty().getAlivePrivates().stream().filter(minion -> minion != actor).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_DEAD, attacker, actor));
			actor.getPrivateParty().getAliveOnePrivateEx().stream().filter(minion -> minion != actor).forEach(minion -> minion.getAI().notifyEvent(CtrlEvent.EVT_PARTY_DEAD, attacker, actor));
		}

		//Оповестить социальных мобов
		if (actor.getFaction().isNone()) {
			return;
		}
		final int range = actor.getFaction().getRange();
		World.getAroundNpc(actor).stream().filter(npc -> npc.isVisible() && !npc.isDead() && npc.isInFaction(actor) && npc.isInRangeZ(actor, range)).forEach(npc -> npc.getAI().notifyEvent(CtrlEvent.EVT_CLAN_DEAD, attacker, actor));
	}

	protected List<NpcInstance> activeFactionTargets() {
		final NpcInstance actor = getActor();
		if (actor.getFaction().isNone()) {
			return Collections.emptyList();
		}

		final int range = actor.getFaction().getRange();
		final List<NpcInstance> npcFriends = World.getAroundNpc(actor).stream().filter(npc -> npc.isVisible() && !npc.isDead() && npc.isInFaction(actor) && npc.isInRangeZ(actor, range)).collect(Collectors.toList());
		return npcFriends;
	}

	protected void MakeAttackEvent(Creature target, double hate, int notify_unk) {
		AddAttackDesire(target, DesireMove.MOVE_TO_TARGET, hate);
	}


	protected boolean CanAttack(final Creature target) {
		if (target == null)
			return false;
		if (target.isAlikeDead())
			return false;
		if (target.isNpc() && target.isInvul()) {
			return false;
		}
		if (target.isPlayable()) {
			if (!canSeeInSilentMove((Playable) target)) {
				return false;
			}
			if (!canSeeInHide((Playable) target)) {
				return false;
			}
			/*if(target.isFollow && !target.isPlayer() && target.getFollowTarget() != null && target.getFollowTarget().isPlayer())
					return;*/
			if (target.isPlayer() && ((Player) target).isGM() && target.isInvisible()) {
				return false;
			}
			if (((Playable) target).getNonAggroTime() > System.currentTimeMillis()) {
				return false;
			}
			if (target.isPlayer() && !target.getPlayer().isActive()) {
				return false;
			}
		}

		if (!target.isInRangeZ(getActor().getLoc(), MAX_PURSUE_RANGE)) {
			return false;
		}

		if (!GeoEngine.canSeeTarget(getActor(), target, false)) {
			return false;
		}
		return true;
	}

	protected boolean checkAggression(final Creature target) {
		return CanAttack(target) && target.isInRangeZ(getActor().getSpawnedLoc(), getActor().getAggroRange() * 3);
	}

	protected boolean checkTarget(Creature target, int range) {
		NpcInstance actor = getActor();
		if (target == null || target.isAlikeDead() || !actor.isInRangeZ(target, range))
			return false;

		// если не видим чаров в хайде - не атакуем их
		final boolean hidden = target.isPlayable() && (!canSeeInHide((Playable) target) || ((Playable) target).getNonAggroTime() > System.currentTimeMillis());

		if (!hidden && actor.isConfused())
			return true;

		//В состоянии атаки атакуем всех, на кого у нас есть хейт
		final AggroInfo ai = actor.getAggroList().get(target);
		if (ai != null) {
			if (hidden) {
				ai.hate = 0; // очищаем хейт
				return false;
			}
			return ai.hate > 0;
		}
		return false;
	}

	protected void ChangeMoveType(MoveType moveType) {
		if (moveType == MoveType.SLOW)
			getActor().setWalking();
		else if (moveType == MoveType.FAST)
			getActor().setRunning();
	}

	public void setAttackTimeout(final long time) {
		_attackTimeout = time;
	}

	protected long getAttackTimeout() {
		return _attackTimeout;
	}

	protected boolean tryMoveToTarget(Creature target) {
		return tryMoveToTarget(target, 0);
	}

	protected boolean tryMoveToTarget(Creature target, int range) {
		NpcInstance actor = getActor();

		if (!actor.followToCharacter(target, actor.getPhysicalAttackRange(), false)) {
			_pathfindFails++;
		}

		//setState(State.CHASE);

		if (_pathfindFails >= 5) {

			_pathfindFails = 0;
			double moveTime = actor.getDistance(target) / actor.getMoveSpeed();
			if (moveTime < 2) {
				Location loc = GeoEngine.moveCheckForAI(target.getLoc(), actor.getLoc(), actor.getGeoIndex());
				if (!GeoEngine.canMoveToCoord(actor.getX(), actor.getY(), actor.getZ(), loc.x, loc.y, loc.z, actor.getGeoIndex())) // Для подстраховки
					loc = target.getLoc();
				/*if (target.isPlayable()) {
					AggroInfo hate = actor.getAggroList().get(target);
					if (hate == null || (actor.getReflection() != ReflectionManager.DEFAULT && actor.isRaid())) // bless freya
					{
						returnHome();
						return false;
					}
				}*/
				if (GeoEngine.canSeeTarget(actor, target, false)) {
					actor.teleToLocation(loc);
					return true;
				}
			}
			return false;

		}
		return true;
	}

	/**
	 * Определяет, может ли этот тип АИ видеть персонажей в режиме Silent Move.
	 *
	 * @param target L2Playable цель. В ПТС за параметр отвечат спец. эфффект нпц : ex_crt_effect=1 в npcdata
	 * @return true если цель видна в режиме Silent Move
	 */
	protected boolean canSeeInSilentMove(final Playable target) {
		if (getActor().getParameter("canSeeInSilentMove", false)) {
			return true;
		}
		return !target.isSilentMoving();
	}

	protected boolean canSeeInHide(final Playable target) {
		if (getActor().getParameter("canSeeInHide", false)) {
			return true;
		}

		return !target.isInvisible();
	}

	public void BroadcastScriptEvent(final int event, final int obj1Id, final int radius) {
		for (Creature creature : World.getAroundCharacters(getActor(), radius, radius)) {
			creature.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, event, obj1Id, null);
		}
	}

	public void BroadcastScriptEventEx(final int event, final int obj1Id, final int obj2Id, final int radius) {
		for (Creature creature : World.getAroundCharacters(getActor(), radius, radius)) {
			creature.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, event, obj1Id, obj2Id);
		}
	}

	@Override
	public void notifyEvent(final CtrlEvent evt, final Object[] args) {
		final NpcInstance actor = getActor();
		if (actor == null || !actor.isVisible()) {
			return;
		}

		actor.getListeners().onAiEvent(evt, args);
		switch (evt) {
			case EVT_THINK:
				onEvtThink();
				break;
			case EVT_SEE_CREATURE:
				onEvtSeeCreature((Creature) args[0]);
				break;
			case EVT_ATTACKED:
				final int damage = ((Number) args[2]).intValue();
				if (args[1] != null) {
					onEvtAttacked((Creature) args[0], ((SkillEntry) args[1]).hashCode(), ((SkillEntry) args[1]).getId(), damage);
					notifyAttack((Creature) args[0], damage, ((SkillEntry) args[1]));
				} else {
					onEvtAttacked((Creature) args[0], 0, 0, damage);
					notifyAttack((Creature) args[0], damage, null);
				}
				break;
			case EVT_CLAN_ATTACKED:
				if (args[0] != null && !((Creature) args[0]).isNpc()) {
					onEvtClanObjectAttacked((Creature) args[1], (Creature) args[0], ((Number) args[2]).intValue());
				} else {
					if (args[3] != null)
						onEvtClanAttacked((Creature) args[1], (Creature) args[0], ((Number) args[2]).intValue(), ((SkillEntry) args[3]).hashCode(), ((SkillEntry) args[3]).getId());
					else
						onEvtClanAttacked((Creature) args[1], (Creature) args[0], ((Number) args[2]).intValue());
				}
				break;
			case EVT_ABNORMAL_CHANGE:
				if (args[1] != null) {
					onEvtAbnormalChanged((Creature) args[0], ((SkillEntry) args[1]).hashCode(), ((SkillEntry) args[1]).getId(), ((SkillEntry) args[1]).getLevel());
				} else {
					onEvtAbnormalChanged((Creature) args[0], 0, 0, 0);
				}
				break;
			case EVT_SCRIPT_EVENT:
				int event = 0;
				try {
					event = ((Number) args[0]).intValue();
				} catch (ClassCastException e) {
					break;
				}
				onEvtScriptEvent(event, args[1] != null ? ((Number) args[1]).intValue() : 0, args[2] != null ? ((Number) args[2]).intValue() : 0);
				break;
			case EVT_TIMER_FIRED_EX:
				onEvtTimerFiredEx(((Number) args[0]).intValue());
				break;
			case EVT_DEAD:
				actor.setLastAttacker((Creature) args[0]);
				onEvtDead((Creature) args[0]);
				actor.abortAttack(true, true);
				actor.abortCast(true, true);
				actor.stopMove();
				actor.broadcastPacket(new Die(actor));
				setState(State.DECAYING);
				notifyDead((Creature) args[0]);
				break;
			case EVT_SEE_SPELL:
				if (actor.getTemplate().event_flag == 0)
					break;
				if (args[2] != null)
					onEvtSeeSpell(((SkillEntry) args[0]).hashCode(), (Creature) args[1], (Creature) args[2]);
				else
					onEvtSpelled(((SkillEntry) args[0]).hashCode(), (Creature) args[1]);
				break;
			case EVT_AGGRESSION:
				int hate = ((Number) args[1]).intValue();
				onEvtDesireManipulation((Creature) args[0], hate);
				notifyAttack((Creature) args[0], hate, null);
				break;
			case EVT_FINISH_CASTING:
				onEvtUseSkillFinished(args[0] != null ? ((SkillEntry) args[0]).hashCode() : 0, (Creature) args[1], (Boolean) args[2]);
				break;
			case EVT_HIT_END:
				onEvtThink();
				break;
			case EVT_PARTY_ATTACKED:
				if (args[3] != null)
					onEvtPartyAttacked((Creature) args[0], (NpcInstance) args[1], ((Number) args[2]).intValue(), ((SkillEntry) args[3]).hashCode(), ((SkillEntry) args[3]).getId());
				else
					onEvtPartyAttacked((Creature) args[0], (NpcInstance) args[1], ((Number) args[2]).intValue());
				break;
			case EVT_PARTY_DEAD:
				onEvtPartyDead((Creature) args[0], (NpcInstance) args[1]);
				break;
			case EVT_SPAWN:
				onEvtSpawn();
				int aggroRange = Math.max(actor.getAggroRange(), actor.param1);
				if (actor.getTemplate().event_flag == 1 && aggroRange > 0)
					isAggresive = true;
				break;
			case EVT_ATK_FINISHED:
				onEvtAtkFinished((Creature) args[0]);
				break;
			case EVT_NO_DESIRE:
				onEvtNoDesire();
				break;
			case EVT_NODE_ARRIVED:
				onEvtNodeArrived(((Number) args[0]).intValue(), ((Number) args[1]).intValue(), ((Number) args[2]).intValue(), lastPoint);
				break;
			case EVT_ARRIVED:
				if (args[0] != null)
					onEvtMoveFinished(((Number) args[0]).intValue(), ((Number) args[1]).intValue(), ((Number) args[2]).intValue());
				else
					onEvtMoveFinished(actor.getX(), actor.getY(), actor.getZ());
				break;
			case EVT_OUT_TERRITORY:
				onEvtOutOfTerritory();
				break;
			case EVT_CLAN_DEAD:
				onEvtClanDead((Creature) args[0], (Creature) args[1]);
				break;
			case EVT_DESPAWN:
				onEvtDeSpawn();
				break;
			case EVT_FORGET_OBJECT:
				onEvtForgetObject((GameObject) args[0]);
				break;
			case EVT_FAKE_DEATH:
				onEvtFakeDeath();
				break;
			/*
			case EVT_ARRIVED_TARGET:
				onEvtArrivedTarget();
				break;
			case EVT_ARRIVED_BLOCKED:
				onEvtArrivedBlocked((Location) args[0]);
				break;
			case EVT_FORGET_OBJECT:
				onEvtForgetObject((GameObject) args[0]);
				break;
			case EVT_FAKE_DEATH:
				onEvtFakeDeath();
				break;
			case EVT_TALK_SELECTED:
				onEvtTalkSelected(params);
				break;
			case EVT_MENU_SELECTED:
				onEvtMenuSelected((Player) args[0], ((Number) args[1]).intValue(), ((Number) args[2]).intValue());
				break;*/
		}
	}

	/*public void notifyEvent(final CtrlEvent evt, EventParam params) {
		final Creature actor = getActor();
		if (actor == null || !actor.isVisible()) {
			return;
		}

		switch (evt) {
			onEvtAbnormalChanged(params)
		}
	}*/

	protected class TargetContains {
		private final double distance;
		private final Creature creature;

		public TargetContains(double distance, Creature creature) {
			this.distance = distance;
			this.creature = creature;
		}

		public double getDistance() {
			return distance;
		}

		public Creature getCreature() {
			return creature;
		}
	}

	protected static class NearestTargetComparator implements Comparator<TargetContains> {
		@Override
		public int compare(final TargetContains o1, final TargetContains o2) {
			double diff = o1.getDistance() - o2.getDistance();
			if (diff < 0D) {
				return -1;
			}
			return diff > 0 ? 1 : 0;
		}
	}

	/*@SuppressWarnings("unchecked")
	public <V> V getParam(String key, V def_val) {
		try{
			if(ai_params.isEmpty() || ai_params.get(key) ==null)
				return def_val;
			return (V) ai_params.get(key);
		}
		catch (ClassCastException cc){
			LOGGER.error("Cannot cast AI param: " + key + " for npc: " + getActor().getNpcId());
			return def_val;
		}
	}*/


	protected void onEvtAttacked(final Creature attacker, final int skill_name_id, final int skill_id, final int damage) {

		final Player player = attacker.getPlayer();

		if (player != null) {
			final List<QuestState> quests = player.getQuestsForEvent(getActor(), QuestEventType.ATTACKED_WITH_QUEST);
			if (quests != null) {
				for (final QuestState qs : quests) {
					qs.getQuest().notifyAttack(getActor(), qs);
				}
			}
		}
	}

	protected void onEvtTimerFiredEx(final int timer_id) {
	}

	protected void onEvtSeeCreature(final Creature creature) {
	}

	protected void onEvtScriptEvent(final int arg1, final int arg2, final int arg3) {
	}

	protected void onEvtSeeSpell(final int skill_name_id, final Creature speller, final Creature target) {
	}

	protected void onEvtSpelled(final int skill_name_id, final Creature speller) {
	}

	protected void onEvtDesireManipulation(final Creature speller, final int desire) {
	}

	protected void onEvtUseSkillFinished(final int skill_name_id, Creature target, boolean success) {
	}

	protected void onEvtPartyAttacked(final Creature attacker, final NpcInstance privat, final int damage) {
		onEvtPartyAttacked(attacker, privat, damage, 0, 0);
	}

	protected void onEvtPartyAttacked(final Creature attacker, final NpcInstance privat, final int damage, final int skill_name_id, final int skill_id) {
	}

	protected void onEvtPartyDead(final Creature attacker, final NpcInstance privat) {
	}

	protected void onEvtAtkFinished(final Creature target) {
	}

	protected void onEvtNoDesire() {
	}

	protected void onEvtNodeArrived(final int arg1, final int arg2, final int arg3, final boolean success) {
	}

	protected void onEvtOutOfTerritory() {
	}

	protected void onEvtClanObjectAttacked(final Creature attacker, final Creature victim, final int damage) {
	}

	protected void onEvtClanDead(final Creature attacker, final Creature victim) {
	}

	protected void onEvtMoveFinished(final int arg1, final int arg2, final int arg3) {
	}

	protected void onEvtAbnormalChanged(final Creature speller, final int skill_name_id, final int skill_id, final int skill_level) {
	}


	@Override
	protected void onEvtClanAttacked(final Creature attacker, final Creature victim, final int damage) {
		onEvtClanAttacked(attacker, victim, damage, 0, 0);
	}

	protected void onEvtClanAttacked(final Creature attacker, final Creature victim, final int damage, final int skill_name_id, final int skill_id) {
	}

	@Override
	protected void onEvtDead(final Creature attacker) {
	}

	@Override
	public void onEvtDeSpawn() {
		// Удаляем все задания
		clearTasks();
		clearVars();
	}

	protected enum Status {
		NAME_INVISIBLE,
		NAME_VISIBLE,
		TARGET_DISABLE,
		TARGET_ENABLE
	}

	@Override
	public boolean isPTSAI() {
		return true;
	}

	protected int GetCurrentTick() {
		return GameServer.getInstance().uptime();
	}

	protected int GetTick() {
		return GameServer.getInstance().uptimeInMillis();
	}

	protected void Log(String log) {
		LOGGER.info(log);
	}

	//DEPRICATED METHODS FROM OLDER CHRONICLES
	protected void AddMoveToWayPointDesire(String waypoint, int waypointdelay, int unk0, int unk1) {
	}

	protected void onEvtWayPointFinished(int way_point_index, int next_way_point_index) {
	}

	protected int GetWayPointDelay(int waypointdelay, int way_point_index) {
		return 0;
	}


	/*TODO InstanceZone methods*/
	protected void InstantZone_MarkRestriction() {
	}

	protected void InstantZone_Finish(int arg) {
	}

	protected int InstantZone_GetId() {
		return getActor().getReflectionId();
	}

	/*TODO SSQ methods*/
	protected int GetSSQStatus() {
		return -1;
	}

	protected int GetSSQSealOwner(int arg) {
		return -1;
	}

	/*TODO Quest Reward methods*/
	protected void onEvtTalkSelected(final Player talker) {
	}

	protected void SetCurrentQuestID(int id) {
	}

	protected void VoiceEffect(Creature player, String voice, int delay) {
	}

	protected void SoundEffect(Creature player, String sound) {
	}

	protected void ShowQuestionMark(Creature player, int state) {
		ShowQuestMark(player, state);
	}

	protected void ShowQuestMark(Creature player, int state) {
	}

	protected void SetMemoStateEx(Creature player, int quest, int unk, int state) {
	}

	protected void SetMemoState(Creature player, int quest, int state) {
	}

	protected void RemoveMemo(Creature player, int quest) {
	}

	protected void DropItem1(Creature player, int item_id, int item_count) {
	}

	protected void GiveItem1(Creature player, int item_id, int item_count) {
	}

	protected void DeleteItem1(Creature player, int item_id, int item_count) {
	}

	public static int GetItemData(Creature player, int item_id) {
		return 0;
	}

	public static void CreatePet(Creature player, int item_id, int pts_npc_id, int level) {
	}

	protected int GetInventoryInfo(Creature creature, int type) {
		if (creature == null)
			return 0;
		Player player = creature.getPlayer();
		if (player == null)
			return 0;
		switch (type) {
			case 0:
				return player.getInventory().getSize();
			case 1:
			case 3:
				return player.getInventoryLimit();
			case 2:
				return player.getInventory().getQuestSize();
			default:
				return 0;
		}
	}

	protected void SetFlagJournal(Creature player, int quest, int flag) {
	}

	protected void SetJournal(Creature player, int quest, int flag) {
	}

	protected void DeleteRadar(Creature player, int x, int y, int z, int flag) {
	}

	protected void ShowRadar(Creature player, int x, int y, int z, int flag) {
	}

	protected void CastBuffForQuestReward(Creature attacker, int pts_skill_id) {
	}


	/*TODO other methods*/
	protected default_maker GetMyMaker() {
		//return getActor().getDefaultMaker();
		return default_maker.EMPTY_MAKER;
	}

	protected int Maker_GetNpcCount() {
		return 0;
	}

	protected boolean IsSpoiled() {
		if (getActor().isMonster() && (((MonsterInstance) _actor).isSpoiled()))
			return true;
		return false;
	}

	protected int sit_on_stop = 0;


}