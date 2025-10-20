package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_boss01_guard_ant extends warrior_aggressive
{
	public int different_level_9_attacked = 295895041;
	public int different_level_9_see_spelled = 276234241;

	public ai_boss01_guard_ant(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(3001, 90000 + AiUtils.Rand(240000));
		AddTimerEx(3002, 10 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 3001)
		{
			if (AiUtils.Rand(100) < 66)
			{
				RandomizeAttackDesire();
			}
			AddTimerEx(3001, 90000 + AiUtils.Rand(240000));
		}
		if (timer_id == 3002)
		{
			if (!InMyTerritory(actor))
			{
				InstantTeleport(actor, start_x, start_y, start_z);
				RemoveAllAttackDesire();
			}
			AddTimerEx(3002, 10 * 1000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (attacker.getZ() - actor.getZ() > 500 || attacker.getZ() - actor.getZ() < -500)
		{
		}
		else if (attacker.getLevel() > actor.getLevel() + 8)
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
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (speller.getZ() - actor.getZ() > 500 || speller.getZ() - actor.getZ() < -500)
		{
		}
		else if (speller.getLevel() > actor.getLevel() + 8)
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

		if (creature.getZ() - actor.getZ() > 500 || creature.getZ() - actor.getZ() < -500)
		{
		}
		else if (creature.getLevel() > actor.getLevel() + 8)
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