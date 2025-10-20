package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_physicalspecial_shot_range extends warrior
{
	public int LongRangePhysicalSpecial = 458752001;
	public int PhysicalSpecial = 458752001;
	public int SelfRangePhysicalSpecial = 458752001;

	public warrior_physicalspecial_shot_range(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		i_ai3 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		i_ai3 = AiUtils.GetIndexFromCreature(attacker);
		if (DistFromMe(attacker) > 200)
		{
			if (Skill_GetConsumeMP(LongRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(LongRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(LongRangePhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, LongRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(SelfRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(SelfRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(SelfRangePhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, SelfRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (timer_id == 1002)
		{
			c0 = AiUtils.GetCreatureFromIndex(i_ai3);
			if (!IsNullCreature(c0))
			{
				if (DistFromMe(c0) > 200)
				{
					if (Skill_GetConsumeMP(LongRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(LongRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(LongRangePhysicalSpecial) == 0)
					{
						AddUseSkillDesire(c0, LongRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			else
			{
				i_ai3 = 0;
			}
			AddTimerEx(1002, 5000);
		}
	}

}