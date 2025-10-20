package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;

public class warrior_passive_casting_sleep_magic extends warrior_casting_sleep_magic
{
	public warrior_passive_casting_sleep_magic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if ((p_state == State.MOVE_AROUND && i6 < 2))
		{
			if (Skill_GetConsumeMP(SleepMagic) < actor._currentMp && Skill_GetConsumeHP(SleepMagic) < actor.currentHp && Skill_InReuseDelay(SleepMagic) == 0)
			{
				AddUseSkillDesire(creature, SleepMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtSeeCreature(creature);
	}

}