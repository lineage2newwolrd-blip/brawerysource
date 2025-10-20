package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_aggressive_use_power_shot extends warrior_aggressive_physicalspecial
{
	public warrior_aggressive_use_power_shot(final NpcInstance actor){super(actor);}

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

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;

		if (script_event_arg2 == 10033)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg3);
			if (!IsNullCreature(c0))
			{
				RemoveAllAttackDesire();
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1 * 200);
				}
			}
		}
	}

}