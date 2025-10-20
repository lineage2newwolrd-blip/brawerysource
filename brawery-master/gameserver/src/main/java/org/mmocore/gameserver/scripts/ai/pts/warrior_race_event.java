package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_race_event extends warrior_physicalspecial
{
	public int stamp_item = 10013;

	public warrior_race_event(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(343343109/*@skill_5239_5*/)) > 0)
		{
			if (DistFromMe(attacker) < 100 && AiUtils.Rand(100) < 10)
			{
				if (AiUtils.OwnItemCount(attacker, stamp_item) == 0 && i_ai0 == 0)
				{
					GiveItem1(attacker, stamp_item, 1);
					i_ai0 = 1;
					AddTimerEx(2001, 30 * 60 * 1000);
				}
				else if (AiUtils.OwnItemCount(attacker, stamp_item) == 1 && i_ai0 == 0)
				{
					GiveItem1(attacker, stamp_item, 1);
					i_ai0 = 2;
					AddTimerEx(2001, 30 * 60 * 1000);
				}
				else if (AiUtils.OwnItemCount(attacker, stamp_item) == 2 && i_ai0 == 0)
				{
					GiveItem1(attacker, stamp_item, 1);
					i_ai0 = 3;
					AddTimerEx(2001, 30 * 60 * 1000);
				}
				else if (AiUtils.OwnItemCount(attacker, stamp_item) == 3 && i_ai0 == 0)
				{
					GiveItem1(attacker, stamp_item, 1);
					i_ai0 = 4;
					AddTimerEx(2001, 30 * 60 * 1000);
				}
			}
		}
		else
		{
			AddUseSkillDesire(attacker, 300023809/*@skill_4578_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			Say(AiUtils.MakeFString(1800107, "", "", "", "", ""));
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{

	}

	@Override
	protected void onEvtNoDesire()
	{

	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 2001)
		{
			i_ai0 = 0;
		}
	}

}