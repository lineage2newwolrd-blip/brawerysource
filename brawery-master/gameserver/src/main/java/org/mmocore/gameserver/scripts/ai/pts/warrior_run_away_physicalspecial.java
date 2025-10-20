package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_run_away_physicalspecial extends warrior
{
	public int flee_x = 0;
	public int flee_y = 0;
	public int flee_z = 0;
	public int PhysicalSpecial = 264241153;

	public warrior_run_away_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!AiUtils.IsNull(top_desire_target))
		{
			c_ai0 = top_desire_target;
		}
		else
		{
			c_ai0 = attacker;
		}
		if (!IsNullCreature(top_desire_target))
		{
			i0 = AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(78708737/*@skill_1201_1*/));
			if (i0 >= 0 && DistFromMe(top_desire_target) > 40)
			{
				if (i_ai0 == 1)
				{
					i_ai0 = 3;
					RemoveAllDesire();
				}
				if (CanAttack(top_desire_target))
				{
					if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
					{
						if (damage == 0)
						{
							damage = 1;
						}
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 100);
					}
				}
				else
				{
					RemoveAttackDesire(top_desire_target.getObjectId());
					if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
					{
						if (damage == 0)
						{
							damage = 1;
						}
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 100);
					}
				}
			}
		}
		if (actor.currentHp < actor.getMaxHp() / 2.000000f && (flee_x != 0 && flee_y != 0 && flee_z != 0) && i_ai0 == 0)
		{
			i0 = AiUtils.Rand(100);
			if (i0 < 20)
			{
				i_ai0 = 1;
				RemoveAllDesire();
				AddMoveToDesire(flee_x, flee_y, flee_z, 2000);
				return;
			}
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtMoveFinished(int x, int y, int z)
	{

		if (i_ai0 == 1)
		{
			if ((flee_x == x && flee_y == y && flee_z == z))
			{
				RemoveAllDesire();
				AddMoveAroundDesire(1000, 50);
				i_ai0 = 2;
				if (AiUtils.Rand(100) < 50)
				{
					AddTimerEx(2001, 1000);
				}
				else
				{
					AddTimerEx(2002, 1000);
				}
			}
		}
		if (i_ai0 == 3)
		{
			if ((start_x == x && start_y == y && start_z == z))
			{
				i_ai0 = 0;
			}
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;

		if ((timer_id == 2001 && i_ai0 == 2))
		{
			if (!AiUtils.IsNull(c_ai0))
			{
				AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
				i0 = AiUtils.GetIndexFromCreature(c_ai0);
				BroadcastScriptEvent(ScriptEvent.SCE_WATERING_QUIZ_NUMBER, i0, 400);
			}
			i_ai0 = 3;
		}
		if ((timer_id == 2002 && i_ai0 == 2))
		{
			AddMoveToDesire(start_x, start_y, start_z, 50000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (script_event_arg1 == ScriptEvent.SCE_WATERING_QUIZ_NUMBER)
		{
			if (p_state != State.ATTACK)
			{
				c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
				if (!AiUtils.IsNull(c0))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

}