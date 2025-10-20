package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_stakato_refine_nurse extends party_leader_ag_couple_captain_nurse
{
	public ai_stakato_refine_nurse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		default_maker maker0 = null;
		final NpcInstance actor = getActor();

		maker0 = GetMyMaker();
		if (!AiUtils.IsNull(maker0))
		{
			AiUtils.SendMakerScriptEvent(maker0, 20090501, GetLifeTime() + actor.param3, GetCurrentTick());
		}
		else
		{
			Say("메이커가 Null 입니다.");
		}
		super.onEvtDead(attacker);
	}

}