package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_growth_step4 extends wizard_growh_basic
{
	public int HoldMagic = 265224193;
	public int SelfRangeDDMagic = 264306689;
	public int SelfBuff = 263979009;

	public wizard_growth_step4(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai1 = 0;
		i_ai2 = 0;
		i_ai4 = 1;
		c_ai0 = AiUtils.GetCreatureFromIndex(actor.param1);
		i_ai3 = actor.param2;
		if (IsNullCreature(c_ai0))
		{
			Say("수정. 주인이 없습니다");
		}
		else
		{
			AddHateInfo(c_ai0, 100, 0, 1, 1);
		}
		i_ai2 = 0;
		BroadcastScriptEvent(ScriptEvent.SCE_HERE_COMES_CRAZY_PET, AiUtils.GetIndexFromCreature(actor), 700);
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
			{
				AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (!IsNullCreature(c_ai0))
		{
			if (DistFromMe(c_ai0) > 100)
			{
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(c_ai0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(c_ai0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
				{
					AddUseSkillDesire(c_ai0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(c_ai0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
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
		h0 = GetMaxHateInfo(0);
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai0 == 0)
			{
				i0 = 0;
				if (!IsNullHateInfo(h0))
				{
					if (!IsNullCreature(h0.attacker))
					{
						i0 = 1;
					}
				}
				if (i0 == 1)
				{
					if (h0.attacker != attacker)
					{
						if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp)
						{
							if (Skill_InReuseDelay(SelfRangeDDMagic) == 0)
							{
								AddUseSkillDesire(attacker, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(attacker, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
						}
					}
				}
			}
		}
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
					if (AiUtils.GetAbnormalLevel(top_desire_target, Skill_GetAbnormalType(HoldMagic)) <= 0)
					{
						if (Skill_GetConsumeMP(HoldMagic) < actor._currentMp && Skill_GetConsumeHP(HoldMagic) < actor.currentHp && Skill_InReuseDelay(HoldMagic) == 0)
						{
							AddUseSkillDesire(top_desire_target, HoldMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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