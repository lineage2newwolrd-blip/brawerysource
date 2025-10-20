package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_casting_clanbuff_rangecurse extends wizard_ddmagic2
{
	public int Buff1 = 263979009;
	public int Buff2 = 264110083;
	public int RangeDeBuff = 458752001;

	public wizard_casting_clanbuff_rangecurse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai2 = 0;
		i_ai3 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai3 == 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff1) < actor._currentMp && Skill_GetConsumeHP(Buff1) < actor.currentHp && Skill_InReuseDelay(Buff1) == 0)
				{
					AddUseSkillDesire(actor, Buff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(Buff2) < actor._currentMp && Skill_GetConsumeHP(Buff2) < actor.currentHp && Skill_InReuseDelay(Buff2) == 0)
				{
					AddUseSkillDesire(actor, Buff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				i_ai3 = 1;
			}
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(RangeDeBuff)) <= 0 && top_desire_target != attacker)
				{
					if (Skill_GetConsumeMP(RangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(RangeDeBuff) < actor.currentHp)
					{
						if (Skill_InReuseDelay(RangeDeBuff) == 0)
						{
							AddUseSkillDesire(attacker, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(attacker, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			if (i_ai3 == 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff1) < actor._currentMp && Skill_GetConsumeHP(Buff1) < actor.currentHp && Skill_InReuseDelay(Buff1) == 0)
				{
					AddUseSkillDesire(actor, Buff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(Buff2) < actor._currentMp && Skill_GetConsumeHP(Buff2) < actor.currentHp && Skill_InReuseDelay(Buff2) == 0)
				{
					AddUseSkillDesire(actor, Buff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				i_ai3 = 1;
			}
			if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(RangeDeBuff)) <= 0)
			{
				if (Skill_GetConsumeMP(RangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(RangeDeBuff) < actor.currentHp)
				{
					if (Skill_InReuseDelay(RangeDeBuff) == 0)
					{
						AddUseSkillDesire(attacker, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}