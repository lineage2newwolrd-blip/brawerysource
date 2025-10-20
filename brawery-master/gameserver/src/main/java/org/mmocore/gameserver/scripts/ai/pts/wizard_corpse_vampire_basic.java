package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_corpse_vampire_basic extends wizard_ddmagic2
{
	public int DeBuff = 272039937;
	public int DDMagic = 272039937;

	public wizard_corpse_vampire_basic(final NpcInstance actor)
	{
		super(actor);
		W_ShortRangeDDMagic = 262209537;
		W_LongRangeDDMagic = 262209537;
	}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
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
				if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 90 && p_state != State.ATTACK)
				{
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 50 && i_ai0 == 0)
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

}