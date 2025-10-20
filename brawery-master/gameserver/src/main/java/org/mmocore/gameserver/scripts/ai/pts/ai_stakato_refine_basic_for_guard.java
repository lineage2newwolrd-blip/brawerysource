package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_stakato_refine_basic_for_guard extends ai_stakato_refine_basic
{
	public int DESPAWN_TIMER = 1125;

	public ai_stakato_refine_basic_for_guard(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (IsMyBossAlive())
		{
			if (DistFromMe(boss) > 300)
			{
				InstantTeleport(actor, AiUtils.FloatToInt(boss.getX()), AiUtils.FloatToInt(boss.getY()), AiUtils.FloatToInt(boss.getZ()));
			}
			else
			{
				AddFollowDesire(boss, 5);
			}
		}
		else
		{
			Despawn();
		}
	}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(DESPAWN_TIMER, 5 * 60 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == DESPAWN_TIMER)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}