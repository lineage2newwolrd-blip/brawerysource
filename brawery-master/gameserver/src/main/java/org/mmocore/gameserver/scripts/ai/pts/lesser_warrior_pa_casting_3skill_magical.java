package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class lesser_warrior_pa_casting_3skill_magical extends warrior_pa_casting_3skill_magical
{
	public lesser_warrior_pa_casting_3skill_magical(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundLimitedDesire(5, 5, 200);
	}

}