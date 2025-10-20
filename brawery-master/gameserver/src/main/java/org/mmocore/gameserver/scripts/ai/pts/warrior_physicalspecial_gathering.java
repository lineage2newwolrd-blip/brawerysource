package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_physicalspecial_gathering extends warrior_physicalspecial
{
	public warrior_physicalspecial_gathering(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(3100, 5000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 3100)
		{
			BroadcastScriptEvent(ScriptEvent.SCE_GATHERING_NPC, AiUtils.GetIndexFromCreature(actor), 500);
			AddTimerEx(3100, 5000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}