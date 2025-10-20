package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_run_away_fiend_archer extends wizard
{
	public int flee_x = 0;
	public int flee_y = 0;
	public int flee_z = 0;
	public int W_FiendArcher = 272039937;

	public wizard_run_away_fiend_archer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;

		i_ai3 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		float f0 = 0;
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
				if (i_ai3 == 1)
				{
					i_ai3 = 3;
					RemoveAllDesire();
				}
			}
		}
		if (actor.currentHp < actor.getMaxHp() / 2.000000f && (flee_x != 0 && flee_y != 0 && flee_z != 0) && i_ai3 == 0)
		{
			i0 = AiUtils.Rand(100);
			if (i0 < 10)
			{
				i_ai3 = 1;
				RemoveAllDesire();
				AddMoveToDesire(flee_x, flee_y, flee_z, 2000);
				return;
			}
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (GetHateInfoCount() == 0)
			{
				f0 = 0;
				if (SetHateGroup >= 0)
				{
					if (IsInCategory(SetHateGroup, attacker.getClassId()))
					{
						f0 = f0 + SetHateGroupRatio;
					}
				}
				if (attacker.getClassId() == SetHateOccupation)
				{
					f0 = f0 + SetHateOccupationRatio;
				}
				if (SetHateRace == attacker.getRaceId())
				{
					f0 = f0 + SetHateRaceRatio;
				}
				f0 = 1.000000f * damage / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * damage / (actor.getLevel() + 7)));
				AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 100 + 300), 0, 1, 1);
			}
			else
			{
				f0 = 0;
				if (SetHateGroup >= 0)
				{
					if (IsInCategory(SetHateGroup, attacker.getClassId()))
					{
						f0 = f0 + SetHateGroupRatio;
					}
				}
				if (attacker.getClassId() == SetHateOccupation)
				{
					f0 = f0 + SetHateOccupationRatio;
				}
				if (SetHateRace == attacker.getRaceId())
				{
					f0 = f0 + SetHateRaceRatio;
				}
				f0 = 1.000000f * damage / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * damage / (actor.getLevel() + 7)));
				AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 100), 0, 1, 1);
			}
		}
		h0 = GetMaxHateInfo(0);
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (h0.attacker == attacker)
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_FiendArcher) == 0)
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (AiUtils.Rand(100) < 10)
			{
				if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_FiendArcher) == 0)
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		RemoveAllHateInfoIF(1, 0);
		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && GetHateInfoCount() == 0)
		{
			if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_FiendArcher) == 0)
				{
					AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if ((timer_id == 2001 && i_ai3 == 2))
		{
			if (p_state != State.ATTACK)
			{
				if (!AiUtils.IsNull(c_ai0))
				{
					AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
					i0 = AiUtils.GetIndexFromCreature(c_ai0);
					BroadcastScriptEvent(ScriptEvent.SCE_WATERING_QUIZ_NUMBER, i0, 400);
				}
				i_ai3 = 3;
			}
		}
		if ((timer_id == 2002 && i_ai0 == 2))
		{
			AddMoveToDesire(start_x, start_y, start_z, 50000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		h0 = GetMaxHateInfo(0);
		i0 = 0;
		if (!IsNullHateInfo(h0))
		{
			if (!IsNullCreature(h0.attacker))
			{
				i0 = 1;
			}
		}
		if (i0 == 1)
		{
			if (DistFromMe(h0.attacker) < 100)
			{
				AddFleeDesire(h0.attacker, 1000);
				if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_FiendArcher) == 0)
					{
						AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_FiendArcher) == 0)
				{
					AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
	}

	@Override
	protected void onEvtMoveFinished(int x, int y, int z)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai3 == 1)
		{
			if ((flee_x == x && flee_y == y && flee_z == z))
			{
				RemoveAllDesire();
				AddMoveAroundDesire(1000, 50);
				i_ai3 = 2;
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
		else if (i_ai3 == 3)
		{
			if ((start_x == x && start_y == y && start_z == z))
			{
				i_ai3 = 0;
			}
		}
		else
		{
			h0 = GetMaxHateInfo(0);
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_FiendArcher) == 0)
					{
						AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

}