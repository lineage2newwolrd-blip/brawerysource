package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_couple_captain_nurse extends party_leader_couple_captain
{
	public int DDMagic = 458752001;
	public int SelfRangeDDMagic = 458752001;

	public party_leader_couple_captain_nurse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (DistFromMe(attacker) > 100 && AiUtils.Rand(100) < 20 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
				if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (DistFromMe(attacker) > 100 && AiUtils.Rand(100) < 20 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}