package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_fishing_block extends warrior_physicalspecial
{
	public int SayType = 0;

	public warrior_fishing_block(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		c_ai0 = AiUtils.GetCreatureFromIndex(actor.summoner_id);
		if (!AiUtils.IsNull(c_ai0))
		{
			AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 2000);
			i0 = 1010400 + (SayType * 9) + AiUtils.Rand(3);
			Say(AiUtils.MakeFString(i0, c_ai0.getName(), "", "", "", ""));
		}
		else
		{
			Despawn();
		}
		AddTimerEx(3000, 1000 * 5 * 10);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;

		if (AiUtils.Rand(100) < 33)
		{
			i0 = 1010400 + 3 + (SayType * 9) + AiUtils.Rand(3);
			Say(AiUtils.MakeFString(i0, "", "", "", "", ""));
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 3000)
		{
			if ((p_state == State.ATTACK && p_state == State.USE_SKILL))
			{
				AddTimerEx(3000, 1000 * 5 * 10);
			}
			else
			{
				Despawn();
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		int i0 = 0;

		i0 = 1010400 + 6 + (SayType * 9) + AiUtils.Rand(3);
		Say(AiUtils.MakeFString(i0, "", "", "", "", ""));
		super.onEvtDead(attacker);
	}

}