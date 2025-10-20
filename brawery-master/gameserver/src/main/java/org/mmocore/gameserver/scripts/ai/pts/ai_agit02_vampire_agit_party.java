package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_agit02_vampire_agit_party extends ai_agit02_doom_knight_agit_party
{
	public ai_agit02_vampire_agit_party(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			if (AiUtils.Rand(100) < 10 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff)) <= 0)
			{
				if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, damage / actor.getMaxHp() / 0.050000f * 1000);
		}
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{

	}

}