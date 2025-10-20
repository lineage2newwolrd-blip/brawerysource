package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class royal_rush_healer2 extends wizard
{
	public int W_RangeHeal = 266403841;
	public int W_LongRangeDDMagic = 266403841;
	public int W_SelfRangeDDMagic = 262209537;
	public int W_SelfRangeDeBuff = 262209537;

	public royal_rush_healer2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai0 == 0)
			{
				if (AiUtils.Rand(100) < 33)
				{
					if (Skill_GetConsumeMP(W_SelfRangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(W_SelfRangeDeBuff) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_SelfRangeDeBuff) == 0)
						{
							AddUseSkillDesire(actor, W_SelfRangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(actor, W_SelfRangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				if (AiUtils.Rand(100) < 33 && DistFromMe(attacker) > 100)
				{
					if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
						{
							AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(W_SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_SelfRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(actor, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(W_RangeHeal) < actor._currentMp && Skill_GetConsumeHP(W_RangeHeal) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_RangeHeal) == 0)
				{
					AddUseSkillDesire(attacker, W_RangeHeal, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(attacker, W_RangeHeal, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		h0 = GetMaxHateInfo(0);
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
			if (i_ai0 != 1)
			{
				if (AiUtils.Rand(100) < 33 && DistFromMe(h0.attacker) > 100)
				{
					if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
						{
							AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(W_SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_SelfRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(actor, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

}