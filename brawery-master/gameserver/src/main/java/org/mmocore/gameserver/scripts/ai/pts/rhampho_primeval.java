package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class rhampho_primeval extends warrior
{
	public int SelfBuff1 = 0;
	public int LongRangeDDMagic1 = 0;
	public int PhysicalSpecial1 = 0;
	public int PhysicalSpecial2 = 0;
	public int ProbSelfBuff1 = 0;
	public int ProbLongRangeDDMagic1 = 0;
	public int ProbPhysicalSpecial1 = 0;
	public int ProbPhysicalSpecial2 = 0;
	public int mobile_type = 0;
	public int LongRangeSkillDist = 100;
	public int BroadCastReception = 0;
	public int ag_type = 0;
	public int HpChkRate4 = 50;
	public int HpChkRate5 = 30;
	public int ProbCond4 = 30;
	public int ProbMultiplier1 = 2;
	public int is_dbg = 0;

	public rhampho_primeval(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": created";
			Say(s0);
			s0 = "";
		}
		i_ai2 = 0;
		i_ai3 = 1;
		i_ai4 = GetCurrentTick();
		i_quest0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": created";
			Say(s0);
			s0 = "";
		}
		if (!IsInCombatMode(actor))
		{
			i_ai2 = 0;
			i_ai3 = 1;
			if (mobile_type == 0)
			{
				AddMoveToDesire(start_x, start_y, start_z, 30);
			}
		}
		if (i_quest0 == 1)
		{
			if (GetCurrentTick() - i_ai4 > 60 * 10)
			{
				BroadcastScriptEvent(ScriptEvent.SCE_SAILREN_PRIVATE_DESPAWN, 0, 8000);
			}
		}
		super.onEvtNoDesire();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		String s0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		i_ai4 = GetCurrentTick();
		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": attacked" + " attacker:" + attacker.getName();
			Say(s0);
			s0 = "";
		}
		if (actor.currentHp / actor.getMaxHp() * 100 <= HpChkRate4)
		{
			if (is_dbg > 1)
			{
				s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + "skill prob multiplier:" + "[" + AiUtils.IntToStr(ProbMultiplier1) + "]";
				Say(s0);
				s0 = "";
			}
			i_ai3 = ProbMultiplier1;
		}
		else
		{
			i_ai3 = 1;
		}
		if (actor.currentHp / actor.getMaxHp() * 100 <= HpChkRate5)
		{
			if (i_ai2 == 0)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfBuff1) + "]" + "target:" + actor.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(SelfBuff1) < actor._currentMp && Skill_GetConsumeHP(SelfBuff1) < actor.currentHp && Skill_InReuseDelay(SelfBuff1) == 0)
				{
					AddUseSkillDesire(actor, SelfBuff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 10000000);
				}
				i_ai2 = 1;
			}
		}
		if (DistFromMe(attacker) < LongRangeSkillDist)
		{
			if (!IsNullCreature(attacker))
			{
				if (AiUtils.Rand(100) <= ProbLongRangeDDMagic1 * i_ai3)
				{
					if (is_dbg > 1)
					{
						s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(LongRangeDDMagic1) + "]" + "target:" + attacker.getName();
						Say(s0);
						s0 = "";
					}
					if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(LongRangeDDMagic1) == 0)
					{
						AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		else if (!IsNullCreature(top_desire_target))
		{
			if (AiUtils.Rand(100) <= ProbLongRangeDDMagic1 * i_ai3)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(LongRangeDDMagic1) + "]" + "target:" + top_desire_target.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(LongRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(top_desire_target, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) <= ProbPhysicalSpecial1 * i_ai3)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial1) + "]" + "target:" + top_desire_target.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
				{
					AddUseSkillDesire(top_desire_target, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) <= ProbPhysicalSpecial2 * i_ai3)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial2) + "]" + "target:" + top_desire_target.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
				{
					AddUseSkillDesire(top_desire_target, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		String s0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": script_event";
			Say(s0);
			s0 = "";
		}
		if (script_event_arg1 == ScriptEvent.SCE_SAILREN_PRIVATE_NODESIRE)
		{
			i_quest0 = 1;
		}
		else if (script_event_arg1 == ScriptEvent.SCE_ONE_POINT_ATTACK)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0) && c0.isPlayer() && BroadCastReception == 1)
			{
				if (is_dbg > 2)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": script_event::" + "replace top_desire_target as " + c0.getName();
					Say(s0);
					s0 = "";
				}
				top_desire_target = c0;
			}
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		String s0 = null;
		final NpcInstance actor = getActor();

		i_ai4 = GetCurrentTick();
		if (Skill_GetEffectPoint(skill_name_id) > 0 && AiUtils.Rand(100) <= ProbCond4)
		{
			if (target.isPlayer())
			{
				if (AiUtils.Rand(2) == 1)
				{
					if (is_dbg > 1)
					{
						s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial1) + "]" + "target:" + speller.getName();
						Say(s0);
						s0 = "";
					}
					if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
					{
						AddUseSkillDesire(speller, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial2) + "]" + "target:" + speller.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
				{
					AddUseSkillDesire(speller, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtSeeSpell(skill_name_id, speller, target);
	}

	@Override
	protected void onEvtAbnormalChanged(Creature speller, int skill_name_id, int skill_id, int skill_level)
	{
		String s0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (is_dbg > 1)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": abnormal_status_changed" + " speller:" + speller.getName() + ":skill_level::" + "[" + AiUtils.IntToStr(skill_level) + "]" + ":skill_id::" + "[" + AiUtils.IntToStr(skill_id) + "]";
			Say(s0);
			s0 = "";
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		i_ai4 = GetCurrentTick();
		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::" + "ag_type=" + "[" + AiUtils.IntToStr(ag_type) + "]";
			Say(s0);
			s0 = "";
		}
		if (ag_type == 1 && creature.isPlayer())
		{
			if (!IsInCombatMode(actor))
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(LongRangeDDMagic1) + "]" + "target:" + creature.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(LongRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(creature, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
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

}