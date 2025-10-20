package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_boss02_b02_perum extends warrior_aggressive_casting_ddmagic
{
	public int different_level_9_attacked = 295895041;
	public int different_level_9_see_spelled = 276234241;

	public ai_boss02_b02_perum(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(4001, 90000 + AiUtils.Rand(240000));
		AddTimerEx(9002, 3000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		int i5 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (timer_id == 9002)
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (!InMyTerritory(actor))
				{
					i2 = AiUtils.FloatToInt(actor.getY());
					i3 = AiUtils.FloatToInt(start_y);
					i4 = AiUtils.FloatToInt(top_desire_target.getZ());
					i5 = AiUtils.FloatToInt(actor.getZ());
					if (i2 > 113000)
					{
						if (i2 - i3 > 1000 || (i4 - i5 > 1000 || i5 - i4 > 1000))
						{
							InstantTeleport(actor, start_x, start_y, start_z);
						}
					}
				}
			}
			AddTimerEx(9002, 1000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtDesireManipulation(Creature speller, int desire)
	{

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