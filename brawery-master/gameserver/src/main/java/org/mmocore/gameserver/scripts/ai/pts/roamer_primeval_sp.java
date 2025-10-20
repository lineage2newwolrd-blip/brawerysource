package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPointRail;
import org.mmocore.gameserver.utils.AiUtils;

public class roamer_primeval_sp extends roamer_primeval
{
	public String SuperPointName = "";
	public SuperPointRail SuperPointMethod = SuperPointRail.FollowRail_Restart;
	public int SuperPointDesire = 50;

	public roamer_primeval_sp(final NpcInstance actor)
	{
		super(actor);
		mobile_type = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		super.onEvtSpawn();
		if (!IsNullString(SuperPointName))
		{
			if (mobile_type == 1)
			{
				AddMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
			}
		}
		else if (is_dbg > 0)
		{
			s0 = "[DBG1]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": super point name is null";
			Say(s0);
			s0 = "";
		}
	}

	@Override
	protected void onEvtNodeArrived(int script_event_arg1, int script_event_arg2, int script_event_arg3, boolean success)
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": node_arrived";
			Say(s0);
			s0 = "";
		}
		if ((script_event_arg3 > -1 && mobile_type == 1))
		{
			if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": play social" + "[" + AiUtils.IntToStr(script_event_arg3) + "]";
					Say(s0);
					s0 = "";
				}
				AddEffectActionDesire(actor, script_event_arg3, 10, 10000000);
			}
		}
		if (!IsNullString(SuperPointName))
		{
			if (mobile_type == 1)
			{
				AddMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
			}
		}
	}

}