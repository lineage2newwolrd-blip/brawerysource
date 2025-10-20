package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_saint_selfrange_ddmagic extends wizard_saint_middle_ddmagic
{
	public int W_SelfRangeDDMagic = 264306689;

	public wizard_saint_selfrange_ddmagic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		Party party0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		party0 = AiUtils.GetParty(attacker);
		if ((!IsNullParty(party0) || top_desire_target != attacker) && DistFromMe(attacker) < 40)
		{
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(W_SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_SelfRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(actor, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}