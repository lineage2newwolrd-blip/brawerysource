package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;

public class warrior_aggressive_immediate extends warrior
{
	public warrior_aggressive_immediate(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
		{
			AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
		}
		super.onEvtSeeCreature(creature);
	}

}