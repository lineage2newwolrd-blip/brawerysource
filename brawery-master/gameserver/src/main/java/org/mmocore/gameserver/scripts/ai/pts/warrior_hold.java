package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class warrior_hold extends warrior
{

	public warrior_hold(final NpcInstance actor)
	{
		super(actor);
		DoNothing_DecayRatio = 1.000000f;
	}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveToDesire(start_x, start_y, start_z, 30);
	}

	@Override
	protected void onEvtMoveFinished(int x, int y, int z)
	{

		if ((x == start_x && y == start_y && z == start_z))
		{
			AddDoNothingDesire(40, 30);
		}
		else
		{
			AddMoveToDesire(start_x, start_y, start_z, 30);
		}
	}

}