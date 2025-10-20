package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class warrior_casting_3skill_curse extends warrior_casting_3skill_magical
{
	public int DeBuff = 265224199;
	public int DeBuffCancel = 69206028;

	public warrior_casting_3skill_curse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai3 = 0;
		i_ai4 = 0;
		c_ai2 = actor;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if ((i_ai3 == 0 && i_ai4 == 0))
		{
			i_ai3 = 1;
			AddTimerEx(5001, 5000);
		}
		else if ((i_ai3 == 1 && i_ai4 == 0))
		{
			i_ai4 = 1;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		final NpcInstance actor = getActor();

		if (c_ai2 != actor)
		{
			if (Skill_GetConsumeMP(DeBuffCancel) < actor._currentMp && Skill_GetConsumeHP(DeBuffCancel) < actor.currentHp && Skill_InReuseDelay(DeBuffCancel) == 0)
			{
				AddUseSkillDesire(c_ai2, DeBuffCancel, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (timer_id == 5001)
		{
			if ((i_ai3 == 1 && i_ai4 == 1))
			{
				AddTimerEx(5001, 5000);
				i_ai4 = 0;
			}
			else if ((i_ai3 == 1 && i_ai4 == 0))
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(top_desire_target, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					c_ai2 = top_desire_target;
				}
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}