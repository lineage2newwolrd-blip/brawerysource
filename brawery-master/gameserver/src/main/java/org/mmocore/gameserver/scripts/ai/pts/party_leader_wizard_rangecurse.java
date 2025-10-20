package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_wizard_rangecurse extends party_leader_wizard_dd2
{
	public int RangeBuff = 263979009;
	public int DeBuff = 265224199;
	public int DeBuffCancel = 69206028;
	public int RangeDD = 262209537;

	public party_leader_wizard_rangecurse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai1 = 1;
		i_ai3 = 0;
		i_ai4 = 0;
		c_ai2 = actor;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		i0 = AiUtils.Rand(100);
		if (i_ai1 == 1)
		{
			if (Skill_GetConsumeMP(RangeBuff) < actor._currentMp && Skill_GetConsumeHP(RangeBuff) < actor.currentHp && Skill_InReuseDelay(RangeBuff) == 0)
			{
				AddUseSkillDesire(actor, RangeBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai1 = 0;
		}
		if ((i_ai3 == 0 && i_ai4 == 0))
		{
			i_ai3 = 1;
			AddTimerEx(5001, 5000);
		}
		else if ((i_ai3 == 1 && i_ai4 == 0))
		{
			i_ai4 = 1;
		}
		if (!IsNullCreature(top_desire_target))
		{
			if ((i0 < 10 && top_desire_target != attacker))
			{
				if (Skill_GetConsumeMP(RangeDD) < actor._currentMp && Skill_GetConsumeHP(RangeDD) < actor.currentHp)
				{
					if (Skill_InReuseDelay(RangeDD) == 0)
					{
						AddUseSkillDesire(attacker, RangeDD, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, RangeDD, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{

		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{

		super.onEvtSeeSpell(skill_name_id, speller, target);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		final NpcInstance actor = getActor();

		if (c_ai2 != actor)
		{
			if (Skill_GetConsumeMP(DeBuffCancel) < actor._currentMp && Skill_GetConsumeHP(DeBuffCancel) < actor.currentHp)
			{
				if (Skill_InReuseDelay(DeBuffCancel) == 0)
				{
					AddUseSkillDesire(c_ai2, DeBuffCancel, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(c_ai2, DeBuffCancel, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(c_ai2, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		top_desire_target = getTopHated();

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
					AddUseSkillDesire(top_desire_target, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 9999999);
					c_ai2 = top_desire_target;
				}
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}