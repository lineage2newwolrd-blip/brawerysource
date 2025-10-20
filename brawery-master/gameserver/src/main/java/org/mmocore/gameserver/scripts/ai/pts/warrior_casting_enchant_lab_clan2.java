package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_enchant_lab_clan2 extends warrior_casting_enchant_lab_clan1
{
	public int SelfBuff1 = 458752001;
	public int SelfBuff2 = 458752001;

	public warrior_casting_enchant_lab_clan2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Party party0 = null;
		final NpcInstance actor = getActor();

		party0 = AiUtils.GetParty(attacker);
		if (!IsNullParty(party0))
		{
			if (party0.getMemberCount() >= 8)
			{
				if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(SelfBuff2)) <= 0)
				{
					if (Skill_GetConsumeMP(SelfBuff2) < actor._currentMp && Skill_GetConsumeHP(SelfBuff2) < actor.currentHp && Skill_InReuseDelay(SelfBuff2) == 0)
					{
						AddUseSkillDesire(attacker, SelfBuff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			else if (party0.getMemberCount() >= 6)
			{
				if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(SelfBuff1)) <= 0)
				{
					if (Skill_GetConsumeMP(SelfBuff1) < actor._currentMp && Skill_GetConsumeHP(SelfBuff1) < actor.currentHp && Skill_InReuseDelay(SelfBuff1) == 0)
					{
						AddUseSkillDesire(attacker, SelfBuff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}