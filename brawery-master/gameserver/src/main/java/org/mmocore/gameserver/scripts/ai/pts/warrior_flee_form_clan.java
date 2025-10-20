package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class warrior_flee_form_clan extends warrior_physicalspecial
{
	public int Distance = 250;

	public warrior_flee_form_clan(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtClanDead(Creature attacker, Creature victim)
	{
		int i0 = 0;

		AddFleeDesireEx(victim, Distance, 10000);
		super.onEvtClanDead(attacker, victim);
	}

}