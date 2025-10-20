package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class ol_mahum_support_troop extends warrior_aggressive_physicalspecial
{
	public ol_mahum_support_troop(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(171);
		if (timer_id == 17101)
		{
			Say(AiUtils.MakeFString(17151, "", "", "", "", ""));
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

		SetCurrentQuestID(171);
		AddTimerEx(17101, 1000 * 200);
		i0 = actor.param1;
		if (i0 > 0)
		{
			c0 = AiUtils.GetCreatureFromIndex(i0);
			if (c0 !=null)
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 2000);
			}
		}
		super.onEvtSpawn();
	}

}