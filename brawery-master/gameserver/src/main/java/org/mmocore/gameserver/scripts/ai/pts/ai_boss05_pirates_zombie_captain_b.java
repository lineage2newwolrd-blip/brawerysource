package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_boss05_pirates_zombie_captain_b extends warrior_aggressive_physicalspecial
{
	public int different_level_9_attacked = 295895041;
	public int different_level_9_see_spelled = 276234241;

	public ai_boss05_pirates_zombie_captain_b(final NpcInstance actor)
	{
		super(actor);
		PhysicalSpecial = 266534918;
	}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		actor._desirePoint = AiUtils.FloatToInt(actor.getX());
		actor._respawn_minion = AiUtils.FloatToInt(actor.getY());
		actor.flag = AiUtils.FloatToInt(actor.getZ());
		LookNeighbor(1500);
		AddTimerEx(1051, 1000);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (timer_id == 1051)
		{
			if ((start_x - actor.getX()) * (start_x - actor.getX()) + ((start_y - actor.getY()) * (start_y - actor.getY())) > 1500 * 1500)
			{
				i0 = 1;
			}
			else
			{
				i0 = 0;
			}
			if (i0 == 1)
			{
				RemoveAllDesire();
				InstantTeleport(actor, start_x, start_y, start_z);
				AddTimerEx(1052, 3000);
			}
			AddTimerEx(1051, 10 * 1000);
		}
		if (timer_id == 1052)
		{
			LookNeighbor(1000);
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
		if (creature.getZ() > actor.getZ() - 100 && creature.getZ() < actor.getZ() + 100)
		{
			if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
			{
				return;
			}
			if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
			{
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
			}
			MakeAttackEvent(creature, 9, 1);
		}
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
		if (attacker.getLevel() <= actor.getLevel() + 8)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				if (damage == 0)
				{
					damage = 1;
				}
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 20000);
			}
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

}