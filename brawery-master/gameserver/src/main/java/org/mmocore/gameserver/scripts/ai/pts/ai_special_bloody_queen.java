package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_special_bloody_queen extends warrior_aggressive_casting_curse
{
	public int DDMagic = 262209537;

	public ai_special_bloody_queen(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i1 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			i1 = AiUtils.Rand(100);
			if (DistFromMe(attacker) > 300 && i_ai0 == 0)
			{
				if (i1 < 80)
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
			else if (DistFromMe(attacker) > 100 && i_ai0 == 0)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if ((top_desire_target == attacker && i1 < 70) || i1 < 30)
					{
						if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
						{
							AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						i_ai0 = 1;
					}
				}
			}
			else if (i1 < 10)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if ((top_desire_target == attacker && i_ai0 == 0))
					{
						if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
						{
							AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						i_ai0 = 1;
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i1 = 0;
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			i1 = AiUtils.Rand(100);
			if (DistFromMe(attacker) > 100 && i_ai0 == 0)
			{
				if (i1 < 40)
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i1 = 0;
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && InMyTerritory(actor))
		{
			i1 = AiUtils.Rand(100);
			if (DistFromMe(creature) > 100 && i_ai0 == 0)
			{
				if (i1 < 20)
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(creature, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

}