package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class jewel_guardian_pyton extends warrior_aggressive_immediate
{
	public jewel_guardian_pyton(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(337);
		if (timer_id == 233704)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(337);
		AddTimerEx(233704, 1000 * 3 * 60);
		super.onEvtSpawn();
	}

}