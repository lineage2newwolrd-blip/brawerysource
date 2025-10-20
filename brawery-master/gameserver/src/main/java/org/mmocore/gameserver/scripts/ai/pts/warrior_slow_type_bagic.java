package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_slow_type_bagic extends warrior
{
	public int DDMagicSlow = 262209537;

	public warrior_slow_type_bagic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();

		if (i_ai0 == 1)
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target == attacker)
				{
					i_ai1 = 1;
				}
			}
		}
		else
		{
			AddTimerEx(2001, 5000);
			i_ai0 = 1;
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
				i_ai0 = 0;
				i_ai1 = 0;
			}
			else if (i_ai1 == 0)
			{
				if (!IsNullCreature(top_desire_target) && AiUtils.Rand(100) < 50)
				{
					if (Skill_GetConsumeMP(DDMagicSlow) < actor._currentMp && Skill_GetConsumeHP(DDMagicSlow) < actor.currentHp && Skill_InReuseDelay(DDMagicSlow) == 0)
					{
						AddUseSkillDesire(top_desire_target, DDMagicSlow, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			AddTimerEx(2001, 5000);
			i_ai1 = 0;
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK)
		{
			if (AiUtils.Rand(100) < 10)
			{
				if (Skill_GetConsumeMP(DDMagicSlow) < actor._currentMp && Skill_GetConsumeHP(DDMagicSlow) < actor.currentHp && Skill_InReuseDelay(DDMagicSlow) == 0)
				{
					AddUseSkillDesire(attacker, DDMagicSlow, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}