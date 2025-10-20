package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPointRail;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class lesser_patrol_watcher extends warrior_aggressive_casting_curse
{
	public String SuperPointName = "";
	public SuperPointRail SuperPointMethod = SuperPointRail.FollowRail_Restart;
	public int SuperPointDesire = 50;
	public int BroadCastRange = 450;

	public lesser_patrol_watcher(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai4 = 0;
		if (!IsNullString(SuperPointName))
		{
			AddMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
			ChangeMoveType(MoveType.SLOW);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNodeArrived(int script_event_arg1, int script_event_arg2, int script_event_arg3, boolean success)
	{
		String s0 = null;

		if (!IsNullString(SuperPointName))
		{
			AddMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
			ChangeMoveType(MoveType.SLOW);
		}
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (attacker.isPlayer() && i_ai0 == 0)
		{
			i_ai0 = 1;
			c_ai0 = attacker;
			AddTimerEx(2519011, 6 * 1000);
			AddTimerEx(2519012, 2 * 60 * 1000);
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{

		if (creature.isPlayer() && i_ai0 == 0)
		{
			i_ai0 = 1;
			c_ai0 = creature;
			if (AiUtils.Rand(2) < 1)
			{
				Say(AiUtils.MakeFString(1800875, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(1800876, "", "", "", "", ""));
			}
			AddTimerEx(2519011, 6 * 1000);
			AddTimerEx(2519012, 2 * 60 * 1000);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 2519010)
		{
			LookNeighbor(450);
			AddTimerEx(2519010, 30 * 1000);
		}
		if (timer_id == 2519011)
		{
			if (i_ai4 == 1)
			{
				return;
			}
			if (actor.getClassId() == 1022668)
			{
				Shout(AiUtils.MakeFString(1800865, "", "", "", "", ""));
			}
			else
			{
				Shout(AiUtils.MakeFString(1800861, "", "", "", "", ""));
			}
			if (!IsNullCreature(c_ai0))
			{
				BroadcastScriptEvent(ScriptEvent.SCE_ONE_POINT_ATTACK, AiUtils.GetIndexFromCreature(c_ai0), BroadCastRange);
			}
		}
		if (timer_id == 2519012)
		{
			i_ai0 = 0;
		}
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{

		i_ai4 = 1;
	}

}