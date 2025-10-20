package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_berserker_physicalspecial extends warrior_berserker
{
	public int PhysicalSpecial = 264241153;
	public int SelfRangePhysicalSpecial = 273416194;
	public int DeBuff = 264568833;

	public warrior_berserker_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
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
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff)) <= 0 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK)
		{
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtClanDead(Creature attacker, Creature victim)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if ((i_ai1 < MaxRoarCount && victim != actor))
		{
			i0 = Fury + i_ai1;
			if (Skill_GetConsumeMP(i0) < actor._currentMp && Skill_GetConsumeHP(i0) < actor.currentHp && Skill_InReuseDelay(i0) == 0)
			{
				AddUseSkillDesire(actor, i0, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			if (!IsNullCreature(top_desire_target))
			{
				if (Skill_GetConsumeMP(SelfRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(SelfRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(SelfRangePhysicalSpecial) == 0)
				{
					AddUseSkillDesire(actor, SelfRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			i_ai1 = i_ai1 + 1;
		}
		super.onEvtClanDead(attacker, victim);
	}

}