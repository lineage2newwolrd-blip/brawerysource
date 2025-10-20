package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class ai_special_susceptor extends warrior_aggressive_casting_ddmagic
{
	public ai_special_susceptor(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		sit_on_stop = 1;
	}

}