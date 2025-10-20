package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class cruma_warrior_casting_summon_pc extends warrior_pa_casting_summon_pc
{

	public cruma_warrior_casting_summon_pc(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!IsNullCreature(top_desire_target))
		{
			if (AiUtils.Rand(100) < 15 && top_desire_target == c_ai0)
			{
				if (PhysicalSpecial != 0)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(c_ai0, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

}