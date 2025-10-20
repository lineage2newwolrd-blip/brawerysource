package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class canni_stakato_event_private extends ai_stakato_refine_cannibal_private
{
	public canni_stakato_event_private(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		c0 = AiUtils.GetCreatureFromIndex(actor.param3);
		i_quest9 = 0;
		if (!IsNullCreature(c0))
		{
			if (i_quest9 !=0)
			{
				Say("공격할 대상은 " + c0.getName() + " 입니다");
			}
			c_ai4 = c0;
			AddTimerEx(2009, 1);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;
		int i1 = 0;
		Creature c0 = null;
		Party party0 = null;

		if (timer_id == 2009)
		{
			MakeAttackEvent(c_ai4, 100, 0);
		}
	}

}