package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_hero extends monster_parameter
{
	public int HeroSkill = 262209537;

	public warrior_hero(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (actor.param3 != 0)
		{
			c0 = AiUtils.GetCreatureFromIndex(actor.param3);
			if (!IsNullCreature(c0))
			{
				if (Skill_GetConsumeMP(HeroSkill) < actor._currentMp && Skill_GetConsumeHP(HeroSkill) < actor.currentHp && Skill_InReuseDelay(HeroSkill) == 0)
				{
					AddUseSkillDesire(c0, HeroSkill, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		AddTimerEx(3001, 15 * 1000);
		AddTimerEx(3002, 8000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;

		if (timer_id == 3001)
		{
			Despawn();
		}
		if (timer_id == 3002)
		{
			i0 = 1000434 + AiUtils.Rand(7);
			Say(AiUtils.MakeFString(i0, "", "", "", "", ""));
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}