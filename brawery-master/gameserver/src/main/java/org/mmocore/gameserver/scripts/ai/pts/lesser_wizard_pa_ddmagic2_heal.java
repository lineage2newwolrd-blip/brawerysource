package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class lesser_wizard_pa_ddmagic2_heal extends wizard_pa_ddmagic2_heal
{
	public lesser_wizard_pa_ddmagic2_heal(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundLimitedDesire(5, 5, 200);
	}

}