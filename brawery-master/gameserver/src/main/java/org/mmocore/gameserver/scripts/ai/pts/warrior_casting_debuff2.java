package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_debuff2 extends warrior
{
	public int DeBuff1 = 264568833;
	public int DeBuff2 = 264568833;

	public warrior_casting_debuff2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		i_ai2 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
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
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff1)) <= 0)
					{
						if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
						{
							AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff2)) <= 0)
					{
						if (Skill_GetConsumeMP(DeBuff2) < actor._currentMp && Skill_GetConsumeHP(DeBuff2) < actor.currentHp && Skill_InReuseDelay(DeBuff2) == 0)
						{
							AddUseSkillDesire(attacker, DeBuff2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
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

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff1)) <= 0)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff2)) <= 0)
			{
				if (Skill_GetConsumeMP(DeBuff2) < actor._currentMp && Skill_GetConsumeHP(DeBuff2) < actor.currentHp && Skill_InReuseDelay(DeBuff2) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
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
					if (AiUtils.GetAbnormalLevel(top_desire_target, Skill_GetAbnormalType(DeBuff1)) <= 0)
					{
						if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
						{
							AddUseSkillDesire(top_desire_target, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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