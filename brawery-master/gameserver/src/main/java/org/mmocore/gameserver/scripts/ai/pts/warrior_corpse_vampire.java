package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_corpse_vampire extends warrior_physicalspecial
{
	public int DeBuff = 262209537;
	public int DDMagic1 = 262209537;
	public int SelfBuff = 263979009;

	public warrior_corpse_vampire(final NpcInstance actor)
	{
		super(actor);
		PhysicalSpecial = 264241153;
	}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai0 = 0;
		if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
		{
			AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		AddTimerEx(2001, 10000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 2001)
		{
			if (p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
				{
					AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			AddTimerEx(2001, 10000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff)) <= 0)
					{
						if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
						{
							AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 50 && i_ai0 == 0)
					{
						if (Skill_GetConsumeMP(DDMagic1) < actor._currentMp && Skill_GetConsumeHP(DDMagic1) < actor.currentHp && Skill_InReuseDelay(DDMagic1) == 0)
						{
							AddUseSkillDesire(attacker, DDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						i_ai0 = 1;
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}