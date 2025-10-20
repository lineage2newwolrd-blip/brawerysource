package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;

public class warrior_ag_physicalspecial_hold extends warrior_physicalspecial_hold
{
	public warrior_ag_physicalspecial_hold(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{

		if (creature.isPlayer() || IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			AddAttackDesire(creature, DesireMove.STAND, 1 * 50);
		}
	}

}