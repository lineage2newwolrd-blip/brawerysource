package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class lesser_warrior_passive extends warrior_passive
{
	public lesser_warrior_passive(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundLimitedDesire(5, 5, 200);
	}

}