package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_slow_type2 extends warrior_slow_type_bagic
{

	public warrior_slow_type2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (DistFromMe(attacker) > 200 && AiUtils.Rand(100) < 70)
		{
			if (Skill_GetConsumeMP(DDMagicSlow) < actor._currentMp && Skill_GetConsumeHP(DDMagicSlow) < actor.currentHp && Skill_InReuseDelay(DDMagicSlow) == 0)
			{
				AddUseSkillDesire(attacker, DDMagicSlow, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}