package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class party_private_use_bow extends party_private
{
	public party_private_use_bow(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai2 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (i_ai2 == 0 && DistFromMe(attacker) < 100)
		{
			AddTimerEx(100002, 2000);
			i_ai2 = 1;
			c_ai1 = attacker;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 100002)
		{
			AddFleeDesire(c_ai1, 10000);
			i_ai2 = 0;
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}