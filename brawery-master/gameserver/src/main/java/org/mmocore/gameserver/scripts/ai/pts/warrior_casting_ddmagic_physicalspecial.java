package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_ddmagic_physicalspecial extends warrior
{
	public int W_LongRangeDDMagic = 272039937;
	public int PhysicalSpecialRange = 264241153;

	public warrior_casting_ddmagic_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (DistFromMe(attacker) > 200 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (AiUtils.Rand(100) < 33 && top_desire_target != attacker && DistFromMe(attacker) < 200)
				{
					if (Skill_GetConsumeMP(PhysicalSpecialRange) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecialRange) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecialRange) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecialRange, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 33 && top_desire_target != attacker)
				{
					if (Skill_GetConsumeMP(PhysicalSpecialRange) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecialRange) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecialRange) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecialRange, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!IsNullCreature(top_desire_target))
		{
			if (DistFromMe(top_desire_target) > 200 && top_desire_target.isPlayer())
			{
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
				{
					AddUseSkillDesire(top_desire_target, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

}