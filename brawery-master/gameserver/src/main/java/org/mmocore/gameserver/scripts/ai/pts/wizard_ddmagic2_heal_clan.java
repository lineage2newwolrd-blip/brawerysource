package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class wizard_ddmagic2_heal_clan extends wizard_ddmagic2
{
	public int MagicHeal = 458752001;

	public wizard_ddmagic2_heal_clan(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai2 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if ((privat == boss && i_ai2 == 0))
		{
			if (privat.currentHp < privat.getMaxHp() * 0.500000f)
			{
				if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
				{
					AddUseSkillDesire(privat, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		if (skill_name_id == MagicHeal)
		{
			i_ai2 = 1;
			AddTimerEx(777, 2 * 1000);
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 777)
		{
			if (i_ai2 == 1)
			{
				i_ai2 = 0;
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}