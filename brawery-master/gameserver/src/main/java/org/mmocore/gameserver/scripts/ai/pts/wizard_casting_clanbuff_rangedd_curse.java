package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_casting_clanbuff_rangedd_curse extends wizard_casting_clanbuff_curse
{
	public int RangeDD = 458752001;

	public wizard_casting_clanbuff_rangedd_curse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!IsNullCreature(top_desire_target))
		{
			if (AiUtils.Rand(100) < 3 && top_desire_target != attacker)
			{
				if (Skill_GetConsumeMP(RangeDD) < actor._currentMp && Skill_GetConsumeHP(RangeDD) < actor.currentHp && Skill_InReuseDelay(RangeDD) == 0)
				{
					AddUseSkillDesire(attacker, RangeDD, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}