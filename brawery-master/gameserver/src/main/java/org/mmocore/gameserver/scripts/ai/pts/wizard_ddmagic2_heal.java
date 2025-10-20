package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_ddmagic2_heal extends wizard_ddmagic2
{
	public int MagicHeal = 266403844;

	public wizard_ddmagic2_heal(final NpcInstance actor)
	{
		super(actor);
		W_ShortRangeDDMagic = 272039937;
		W_LongRangeDDMagic = 272629761;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 70)
			{
				if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
				{
					AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if (((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && GetHateInfoCount() == 0) && AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(victim, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}