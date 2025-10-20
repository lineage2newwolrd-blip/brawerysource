package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_run_away_physicalspecial_golem extends warrior
{
	public int PhysicalSpecial = 458752001;
	public int LongRangePhysicalSpecial = 458752001;

	public warrior_run_away_physicalspecial_golem(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		c_ai1 = attacker;
		if (i_ai1 == 0 && DistFromMe(attacker) < 100)
		{
			i_ai1 = 1;
			AddTimerEx(1003, 2000);
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (DistFromMe(attacker) > 200)
		{
			if (Skill_GetConsumeMP(LongRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(LongRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(LongRangePhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, LongRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 1002)
		{
			if (!IsNullCreature(c_ai1) && DistFromMe(c_ai1) > 200)
			{
				if (Skill_GetConsumeMP(LongRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(LongRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(LongRangePhysicalSpecial) == 0)
				{
					AddUseSkillDesire(c_ai1, LongRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			AddTimerEx(1002, 5000);
		}
		if (timer_id == 1003)
		{
			AddFleeDesire(c_ai1, 1000);
			i_ai1 = 0;
		}
	}

}