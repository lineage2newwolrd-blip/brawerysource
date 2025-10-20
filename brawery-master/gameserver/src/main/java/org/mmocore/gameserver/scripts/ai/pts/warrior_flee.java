package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class warrior_flee extends warrior
{
	public warrior_flee(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundDesire(40, 20);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		AddFleeDesire(attacker, 30);
	}

}