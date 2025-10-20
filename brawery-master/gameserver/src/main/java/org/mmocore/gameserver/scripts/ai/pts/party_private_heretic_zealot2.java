package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_heretic_zealot2 extends party_private_physicalspecial
{
	public int PhysicalSpecial2 = 458752001;

	public party_private_heretic_zealot2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			if (i_ai4 == 0)
			{
				i_ai4 = 1;
			}
		}
		if (i_ai0 == 1)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
					{
						AddUseSkillDesire(top_desire_target, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else if (!IsNullCreature(attacker))
				{
					if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			if (i_ai4 == 0)
			{
				i_ai4 = 1;
			}
		}
		if (i_ai0 == 1)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
						{
							AddUseSkillDesire(top_desire_target, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
				}
				else if (!IsNullCreature(attacker))
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
						{
							AddUseSkillDesire(attacker, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
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
		final NpcInstance boss = getActor().getLeader();

		if (script_event_arg3 == AiUtils.GetIndexFromCreature(boss))
		{
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

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		Creature c0 = null;
		top_desire_target = getTopHated();
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (privat == boss)
		{
			i_ai0 = 1;
			if (!IsNullCreature(top_desire_target))
			{
				c0 = top_desire_target;
				if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
				{
					AddUseSkillDesire(c0, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyDead(attacker, privat);
	}

}