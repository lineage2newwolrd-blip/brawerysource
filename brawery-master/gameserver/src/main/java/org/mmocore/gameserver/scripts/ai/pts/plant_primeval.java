package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class plant_primeval extends warrior_hold
{
	public int SelfRangeDeBuff1 = 458752001;
	public int ProbSelfRangeDeBuff1 = 100;
	public int DeBuff_interval = 0;
	public int Social1 = 1;
	public int Social1_Frame = -1;
	public int is_dbg = 0;

	public plant_primeval(final NpcInstance actor){super(actor);}

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
		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": timer_id:" + "[" + AiUtils.IntToStr(5001) + "]" + " debuff_interval:" + "[" + AiUtils.IntToStr(DeBuff_interval) + "]";
			Say(s0);
			s0 = "";
		}
		if (is_dbg > 2)
		{
			i_ai3 = 0;
			i_ai4 = 0;
			AddTimerEx(5002, DeBuff_interval * 1000 / 2);
		}
		AddTimerEx(5001, DeBuff_interval * 1000);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": attacked::" + "attacker:" + attacker.getName();
			Say(s0);
			s0 = "";
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill_finished::" + "target:" + target.getName() + ":skill_name_id::" + "[" + AiUtils.IntToStr(skill_name_id) + "]" + ":success::" + AiUtils.IntToStr(skill_name_id) + "]";
			Say(s0);
			s0 = "";
		}
		if (is_dbg > 1)
		{
			i_ai4 = i_ai4 + 1;
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{

	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": timer_fired_ex::" + "timer_id" + "[" + AiUtils.IntToStr(5001) + "]";
			Say(s0);
			s0 = "";
		}
		if (timer_id == 5001)
		{
			if (AiUtils.Rand(100) <= ProbSelfRangeDeBuff1)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeDeBuff1) + "]" + "target:" + actor.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(SelfRangeDeBuff1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDeBuff1) < actor.currentHp && Skill_InReuseDelay(SelfRangeDeBuff1) == 0)
				{
					AddUseSkillDesire(actor, SelfRangeDeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (is_dbg > 2)
				{
					i_ai3 = i_ai3 + 1;
					s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": timer_id:" + "[" + AiUtils.IntToStr(5001) + "]" + " debuff_interval:" + "[" + AiUtils.IntToStr(DeBuff_interval) + "]";
					Say(s0);
					s0 = "";
				}
			}
			AddTimerEx(5001, DeBuff_interval * 1000);
		}
		else if (timer_id == 5002)
		{
			if (is_dbg > 2)
			{
				s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": timer_id::" + "[" + AiUtils.IntToStr(timer_id) + "]" + "debuff casting try:" + "[" + AiUtils.IntToStr(i_ai3) + "]" + "debuff casting success:" + "[" + AiUtils.IntToStr(i_ai4) + "]";
				Say(s0);
				s0 = "";
				AddTimerEx(5002, DeBuff_interval * 1000 / 2);
			}
		}
	}

}