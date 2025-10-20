package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_heal extends warrior
{
	public int MagicHeal = 264568833;

	public warrior_casting_heal(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		i0 = AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100);
		if (AiUtils.Rand(100) < 33 && i0 < 70)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 33 && p_state != State.ATTACK)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(victim, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}