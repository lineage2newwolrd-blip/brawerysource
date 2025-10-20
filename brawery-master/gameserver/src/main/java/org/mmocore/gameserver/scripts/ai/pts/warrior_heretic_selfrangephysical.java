package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_heretic_selfrangephysical extends warrior_casting_curse
{
	public int SelfRangePhysicalSpecial = 458752001;

	public warrior_heretic_selfrangephysical(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			if (i_ai4 == 0)
			{
				i_ai4 = 1;
			}
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(SelfRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(SelfRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(SelfRangePhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, SelfRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			if (i_ai4 == 0)
			{
				i_ai4 = 1;
			}
		}
		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK)
		{
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(SelfRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(SelfRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(SelfRangePhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, SelfRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtOutOfTerritory()
	{

		if (i_ai4 == 0)
		{
			RemoveAllAttackDesire();
			AddMoveToDesire(start_x, start_y, start_z, 100);
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;

		if (script_event_arg1 == ScriptEvent.SCE_PROTECT_LEADER)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				i_ai4 = 1;
				RemoveAllAttackDesire();
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1 * 200);
				}
			}
		}
		else if (script_event_arg1 == ScriptEvent.SCE_STOP_ATTACK)
		{
			i_ai4 = 0;
			RemoveAllAttackDesire();
			AddMoveToDesire(start_x, start_y, start_z, 100);
		}
		else if (script_event_arg1 == ScriptEvent.SCE_VALAKAS_ATTACK)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				RemoveAllAttackDesire();
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1 * 200);
				}
			}
		}
	}

}