package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class leto_lizardman_wizard extends warrior_passive_casting_ddmagic
{
	public leto_lizardman_wizard(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(226);
		if (timer_id == 22608)
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
		AddTimerEx(22608, 1000 * 200);
		super.onEvtSpawn();
	}

}