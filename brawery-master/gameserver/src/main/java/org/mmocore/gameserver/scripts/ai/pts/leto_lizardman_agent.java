package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class leto_lizardman_agent extends warrior_passive
{
	public leto_lizardman_agent(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(226);
		if (timer_id == 22607)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(226);
		AddTimerEx(22607, 1000 * 200);
		super.onEvtSpawn();
	}

}