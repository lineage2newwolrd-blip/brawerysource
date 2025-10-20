package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_solina_knight extends warrior_physicalspecial_buff
{
	public int TIMER = 555;

	public ai_solina_knight(final NpcInstance actor)
	{
		super(actor);
		PhysicalSpecial = 413663233;
		Buff = 413728769;
	}

	@Override
	protected void onEvtSpawn()
	{

		i_ai3 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 20 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 50 && i_ai4 == 0)
		{
			i_ai4 = 1;
			c0 = attacker;
			if (!IsNullCreature(c0))
			{
				CreateOnePrivateEx(18909, "ai_solina_warrior", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(c0), 0);
				Say(AiUtils.MakeFString(60013, "", "", "", "", ""));
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;

		if (script_event_arg1 == ScriptEvent.SCE_ATK_ME && AiUtils.Rand(100) < 10 && i_ai3 == 0)
		{
			switch (AiUtils.Rand(3))
			{
				case 0:
				{
					Say(AiUtils.MakeFString(60014, "", "", "", "", ""));
					break;
				}
				case 1:
				{
					Say(AiUtils.MakeFString(60015, "", "", "", "", ""));
					break;
				}
				case 2:
				{
					Say(AiUtils.MakeFString(60016, "", "", "", "", ""));
					break;
				}
			}
			i_ai3 = 1;
			AddTimerEx(TIMER, 10 * 1000);
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == TIMER)
		{
			i_ai3 = 0;
		}
	}

}