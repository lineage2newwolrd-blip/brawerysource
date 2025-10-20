package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_pabelgolem extends party_leader
{
	public int PhysicalSpecial = 458752001;
	public int SelfRangePhysicalSpecial = 458752001;
	public int DeBuff = 458752001;

	public party_leader_pabelgolem(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Party party0 = null;
		final NpcInstance actor = getActor();

		party0 = AiUtils.GetParty(attacker);
		if (IsNullParty(party0) && AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else if (!IsNullParty(party0) && AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(SelfRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(SelfRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(SelfRangePhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, SelfRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 20)
		{
			if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
			{
				AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

}