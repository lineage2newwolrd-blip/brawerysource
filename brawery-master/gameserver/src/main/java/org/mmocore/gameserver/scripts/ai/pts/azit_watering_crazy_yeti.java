package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.AiUtils;

public class azit_watering_crazy_yeti extends warrior_aggressive
{

	public azit_watering_crazy_yeti(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		int reply = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		Shout(AiUtils.MakeFString(1010627, "", "", "", "", ""));
		actor.residence_id = 62;
		super.onEvtSpawn();
	}

}