package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_enchant_lab_clan1 extends warrior
{
	public int Buff1 = 458752001;
	public int Buff2 = 458752001;
	public int Buff3 = 458752001;
	public int Buff4 = 458752001;

	public warrior_casting_enchant_lab_clan1(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.GetAbnormalLevel(victim, Skill_GetAbnormalType(Buff1)) <= 0)
		{
			if (Skill_GetConsumeMP(Buff1) < actor._currentMp && Skill_GetConsumeHP(Buff1) < actor.currentHp && Skill_InReuseDelay(Buff1) == 0)
			{
				AddUseSkillDesire(victim, Buff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else if (AiUtils.GetAbnormalLevel(victim, Skill_GetAbnormalType(Buff1)) >= 0)
		{
			if (Skill_GetConsumeMP(Buff2) < actor._currentMp && Skill_GetConsumeHP(Buff2) < actor.currentHp && Skill_InReuseDelay(Buff2) == 0)
			{
				AddUseSkillDesire(victim, Buff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else if (AiUtils.GetAbnormalLevel(victim, Skill_GetAbnormalType(Buff2)) >= 0)
		{
			if (Skill_GetConsumeMP(Buff3) < actor._currentMp && Skill_GetConsumeHP(Buff3) < actor.currentHp && Skill_InReuseDelay(Buff3) == 0)
			{
				AddUseSkillDesire(victim, Buff3, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else if (AiUtils.GetAbnormalLevel(victim, Skill_GetAbnormalType(Buff3)) >= 0)
		{
			if (Skill_GetConsumeMP(Buff4) < actor._currentMp && Skill_GetConsumeHP(Buff4) < actor.currentHp && Skill_InReuseDelay(Buff4) == 0)
			{
				AddUseSkillDesire(victim, Buff4, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}