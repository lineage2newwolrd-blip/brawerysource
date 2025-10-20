package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class lesser_party_private_physicalspecial extends party_private_physicalspecial
{
	public lesser_party_private_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundLimitedDesire(5, 5, 200);
	}

}