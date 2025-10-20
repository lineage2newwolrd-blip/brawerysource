package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class king_bugbear extends warrior_aggressive
{
	public king_bugbear(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(226);
		if (timer_id == 22602)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(231);
		i0 = actor.param1;
		if (i0 > 0)
		{
			c0 = AiUtils.GetCreatureFromIndex(i0);
			if (c0 !=null)
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 2000);
			}
		}
		SetCurrentQuestID(226);
		AddTimerEx(22602, 1000 * 200);
		super.onEvtSpawn();
	}

}