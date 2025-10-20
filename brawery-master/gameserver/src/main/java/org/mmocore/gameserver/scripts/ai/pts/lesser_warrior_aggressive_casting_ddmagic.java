package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class lesser_warrior_aggressive_casting_ddmagic extends warrior_aggressive_casting_ddmagic
{
	public lesser_warrior_aggressive_casting_ddmagic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundLimitedDesire(5, 5, 200);
	}

}