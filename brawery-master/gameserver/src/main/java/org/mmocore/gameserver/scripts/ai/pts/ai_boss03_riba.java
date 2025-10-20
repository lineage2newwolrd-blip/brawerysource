package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_boss03_riba extends warrior_passive_casting_hold_magic
{
	public int different_level_9_attacked = 295895041;
	public int different_level_9_see_spelled = 276234241;

	public ai_boss03_riba(final NpcInstance actor)
	{
		super(actor);
		HoldMagic = 265224196;
	}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(4001, 90000 + AiUtils.Rand(240000));
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 4001)
		{
			if (AiUtils.Rand(100) < 66)
			{
				RandomizeAttackDesire();
			}
			AddTimerEx(4001, 90000 + AiUtils.Rand(240000));
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.getLevel() > actor.getLevel() + 8)
		{
			if (AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(different_level_9_attacked)) == -1)
			{
				if (different_level_9_attacked == 295895041)
				{
					CastBuffForQuestReward(attacker, different_level_9_attacked);
					RemoveAttackDesire(attacker.getObjectId());
					return;
				}
				else
				{
					CastBuffForQuestReward(attacker, different_level_9_attacked);
				}
			}
		}
		else if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (damage == 0)
			{
				damage = 1;
			}
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 20000);
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		final NpcInstance actor = getActor();

		if (speller.getLevel() > actor.getLevel() + 8)
		{
			if (AiUtils.GetAbnormalLevel(speller, Skill_GetAbnormalType(different_level_9_see_spelled)) == -1)
			{
				if (different_level_9_see_spelled == 295895041)
				{
					CastBuffForQuestReward(speller, different_level_9_see_spelled);
					RemoveAttackDesire(speller.getObjectId());
					return;
				}
				else
				{
					CastBuffForQuestReward(speller, different_level_9_see_spelled);
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (creature.getLevel() > actor.getLevel() + 8)
		{
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(different_level_9_attacked)) == -1)
			{
				if (different_level_9_attacked == 295895041)
				{
					CastBuffForQuestReward(creature, different_level_9_attacked);
					RemoveAttackDesire(creature.getObjectId());
					return;
				}
				else
				{
					CastBuffForQuestReward(creature, different_level_9_attacked);
				}
			}
		}
		else
		{
			super.onEvtSeeCreature(creature);
		}
	}

}