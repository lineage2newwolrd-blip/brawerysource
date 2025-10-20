package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_ag_band_archer extends warrior_aggressive_use_power_shot
{
	public warrior_ag_band_archer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (script_event_arg2 == 10033)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg3);
			if (!IsNullCreature(c0))
			{
				RemoveAllAttackDesire();
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1 * 200);
				}
			}
		}
		if (script_event_arg2 == 10043)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg3);
			if (!IsNullCreature(c0))
			{
				ChangeMoveType(MoveType.FAST);
				AddMoveToDesire(AiUtils.FloatToInt(c0.getX()), AiUtils.FloatToInt(c0.getY()), AiUtils.FloatToInt(c0.getZ()), 10000000);
			}
		}
		if (script_event_arg1 == ScriptEvent.SCE_ATTACK_CENTER)
		{
			AddMoveToDesire(script_event_arg2, script_event_arg3, AiUtils.FloatToInt(actor.getZ()), 1000);
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		RemoveAllDesire();
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(creature, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		if (SeeCreatureAttackerTime == -1)
		{
			if (SetAggressiveTime == -1)
			{
				if (GetLifeTime() >= AiUtils.Rand(5) + 3 && InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
			else if (SetAggressiveTime == 0)
			{
				if (InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
			else
			{
				if (GetLifeTime() > SetAggressiveTime + AiUtils.Rand(4) && InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
		}
		else
		{
			if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
			{
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;

		if (AiUtils.Rand(100) < 20 && speller.isPlayer() && IsInCategory(Category.cleric_group, speller.getClassId()))
		{
			RemoveAllAttackDesire();
			if (speller.isPlayer() || IsInCategory(Category.summon_npc_group, speller.getClassId()))
			{
				AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, 1 * 200000);
			}
		}
	}

}