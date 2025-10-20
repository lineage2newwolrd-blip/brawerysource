package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class royal_rush_wizard_rangedebuff extends royal_rush_wizard_ddmagic2
{
	public int W_RangeDeBuff = 272629761;

	public royal_rush_wizard_rangedebuff(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai0 == 0)
			{
				if (AiUtils.Rand(100) < 33)
				{
					if (Skill_GetConsumeMP(W_RangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(W_RangeDeBuff) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_RangeDeBuff) == 0)
						{
							AddUseSkillDesire(attacker, W_RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(attacker, W_RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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

}