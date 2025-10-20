package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class neer_bodyguard extends warrior_aggressive_immediate
{
	public neer_bodyguard(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(225);
		if (timer_id == 22501)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(225);
		AddTimerEx(22501, 1000 * 200);
		super.onEvtSpawn();
	}

}