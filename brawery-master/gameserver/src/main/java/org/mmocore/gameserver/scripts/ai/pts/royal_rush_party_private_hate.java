package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class royal_rush_party_private_hate extends party_private
{
	public int MobHate = 262209537;
	public int SelfBuff = 266403841;

	public royal_rush_party_private_hate(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
		{
			AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtSpawn();
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
					if (Skill_GetConsumeMP(MobHate) < actor._currentMp && Skill_GetConsumeHP(MobHate) < actor.currentHp && Skill_InReuseDelay(MobHate) == 0)
					{
						AddUseSkillDesire(attacker, MobHate, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 10)
		{
			if (Skill_GetConsumeMP(MobHate) < actor._currentMp && Skill_GetConsumeHP(MobHate) < actor.currentHp && Skill_InReuseDelay(MobHate) == 0)
			{
				AddUseSkillDesire(attacker, MobHate, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

}