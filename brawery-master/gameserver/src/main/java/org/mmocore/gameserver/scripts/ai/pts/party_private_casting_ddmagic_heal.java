package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_casting_ddmagic_heal extends party_private
{
	public int MagicHeal = 264568833;

	public party_private_casting_ddmagic_heal(final NpcInstance actor)
	{
		super(actor);
		DDMagic = 267845633;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		i0 = AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100);
		if (AiUtils.Rand(100) < 33 && i0 < 70)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (DistFromMe(attacker) > 100)
		{
			if (!IsNullCreature(top_desire_target))
			{
				if ((AiUtils.Rand(100) < 33 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && top_desire_target == attacker)
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
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		i0 = AiUtils.FloatToInt(privat.currentHp / privat.getMaxHp() * 100);
		if (AiUtils.Rand(100) < 33 && privat != actor && i0 < 70)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(privat, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (DistFromMe(attacker) > 100)
		{
			if ((attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())) && AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
				{
					AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

}