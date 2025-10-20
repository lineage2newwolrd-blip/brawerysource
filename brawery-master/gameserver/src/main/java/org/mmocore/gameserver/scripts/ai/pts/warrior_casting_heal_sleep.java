package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_heal_sleep extends warrior_casting_heal
{
	public int MagicSleep = 265158657;

	public warrior_casting_heal_sleep(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if ((attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())) && !IsNullCreature(top_desire_target))
		{
			if (AiUtils.Rand(100) < 33 && top_desire_target != attacker)
			{
				if (Skill_GetConsumeMP(MagicSleep) < actor._currentMp && Skill_GetConsumeHP(MagicSleep) < actor.currentHp && Skill_InReuseDelay(MagicSleep) == 0)
				{
					AddUseSkillDesire(attacker, MagicSleep, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!IsNullCreature(top_desire_target))
		{
			if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
			{
				if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
				{
					AddUseSkillDesire(victim, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}