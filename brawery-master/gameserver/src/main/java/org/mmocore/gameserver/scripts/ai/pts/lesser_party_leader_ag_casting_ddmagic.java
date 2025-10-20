package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class lesser_party_leader_ag_casting_ddmagic extends party_leader_ag_casting_ddmagic
{
	public lesser_party_leader_ag_casting_ddmagic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundLimitedDesire(5, 5, 200);
	}

}