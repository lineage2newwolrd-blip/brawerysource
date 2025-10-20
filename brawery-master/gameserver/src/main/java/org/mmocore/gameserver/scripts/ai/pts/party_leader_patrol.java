package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;

public class party_leader_patrol extends party_leader
{
	String WayPoints;
	int WayPointDelays;

	public party_leader_patrol(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(40, 5);
	}

	@Override
	protected void onEvtSpawn()
	{

		AddMoveToWayPointDesire(WayPoints, WayPointDelays, 1, 10);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtWayPointFinished(int way_point_index, int next_way_point_index)
	{

		i_ai1 = next_way_point_index;
		AddTimerEx(100001, GetWayPointDelay(WayPointDelays, way_point_index) * 1000);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 100001)
		{
			if (i_ai1 > 0)
			{
				AddMoveToWayPointDesire(WayPoints, WayPointDelays, i_ai1, 10);
			}
			else
			{
				AddMoveToWayPointDesire(WayPoints, WayPointDelays, 1, 10);
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}