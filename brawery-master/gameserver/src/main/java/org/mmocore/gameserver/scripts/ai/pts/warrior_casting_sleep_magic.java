package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_sleep_magic extends warrior
{
	public int SleepMagic = 265158657;

	public warrior_casting_sleep_magic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		i_ai2 = 0;
		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (i_ai1 == 1)
		{
			if (top_desire_target == attacker)
			{
				i_ai2 = 1;
			}
		}
		else
		{
			AddTimerEx(2001, 5000);
			i_ai1 = 1;
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			i6 = AiUtils.Rand(100);
			if (i_ai0 == 0)
			{
				i_ai0 = 1;
			}
			else if (i_ai0 == 1)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (top_desire_target != attacker && i6 < 30 && actor.currentHp > actor.getMaxHp() / 10.000000f)
					{
						if (Skill_GetConsumeMP(SleepMagic) < actor._currentMp && Skill_GetConsumeHP(SleepMagic) < actor.currentHp && Skill_InReuseDelay(SleepMagic) == 0)
						{
							AddUseSkillDesire(attacker, SleepMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (timer_id == 2001)
		{
			if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
			{
				i_ai1 = 0;
				i_ai2 = 0;
			}
			else if (i_ai2 == 0)
			{
				if (!IsNullCreature(top_desire_target) && AiUtils.Rand(100) < 50)
				{
					if (AiUtils.GetAbnormalLevel(top_desire_target, Skill_GetAbnormalType(SleepMagic)) <= 0)
					{
						if (Skill_GetConsumeMP(SleepMagic) < actor._currentMp && Skill_GetConsumeHP(SleepMagic) < actor.currentHp && Skill_InReuseDelay(SleepMagic) == 0)
						{
							AddUseSkillDesire(top_desire_target, SleepMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
				}
			}
			AddTimerEx(2001, 5000);
			i_ai2 = 0;
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}