package org.mmocore.gameserver.scripts.ai;

import gnu.trove.map.TIntObjectMap;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.Skill;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;

import java.util.HashMap;
import java.util.Map;

/**
 * AI рейд Scarlet Van Halisha
 * @author SYS & iRock
 */
public class ScarletVanHalisha extends Fighter
{
	// Боевые скилы байума
	private final SkillEntry DAEMON_ATTACK, DAEMON_YOKE, DAEMON_FIELD, DAEMON_DRAIN;

	public ScarletVanHalisha(NpcInstance actor)
	{
		super(actor);
		TIntObjectMap<SkillEntry> skills = getActor().getTemplate().getSkills();
		DAEMON_ATTACK = skills.get(5014);
		DAEMON_YOKE = skills.get(5016);
		DAEMON_FIELD = skills.get(5018);
		DAEMON_DRAIN = skills.get(5019);
	}

	@Override
	protected boolean createNewTask()
	{
		NpcInstance actor = getActor();
		if(actor == null)
			return true;

		clearTasks();

		Creature target;
		if((target = prepareTarget()) == null)
			return false;

		// Шансы использования скилов
		int s_yoke = 15;
		int s_field = actor.getCurrentHpPercents() > 70 ? 0 : 15;
		int s_drain = actor.getCurrentHpPercents() > 70 ? 0 : 15;

		SkillEntry r_skill = null;
		Map<SkillEntry, Integer> d_skill = new HashMap<SkillEntry, Integer>();
		double distance = actor.getDistance(target);

		if(Rnd.chance(s_yoke))
			addDesiredSkill(d_skill, target, distance, DAEMON_YOKE);
		if(Rnd.chance(s_field))
			addDesiredSkill(d_skill, target, distance, DAEMON_FIELD);
		if(Rnd.chance(s_drain))
			addDesiredSkill(d_skill, target, distance, DAEMON_DRAIN);
		r_skill = selectTopSkill(d_skill);

		// Использовать скилл если можно, иначе атаковать скилом DAEMON_ATTACK
		if(r_skill == null)
			r_skill = DAEMON_ATTACK;
		else if(r_skill.getTemplate().getTargetType() == Skill.SkillTargetType.TARGET_SELF)
			target = actor;

		// Добавить новое задание
		addTaskCast(target, r_skill);
		return true;
	}
}