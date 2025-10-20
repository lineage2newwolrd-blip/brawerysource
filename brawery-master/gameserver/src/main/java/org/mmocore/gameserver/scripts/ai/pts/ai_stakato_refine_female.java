package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_stakato_refine_female extends party_private_ag_couple_follower_female
{
	public ai_stakato_refine_female(final NpcInstance actor){super(actor);}

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

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!actor.getLeader().isAlive() && i_ai0 == 0)
		{
			if (actor.currentHp > actor.getMaxHp() * 0.700000f)
			{
				if (!AiUtils.IsNull(top_desire_target))
				{
					CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 0);
					CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 0);
					CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 0);
					i_ai0 = 1;
				}
			}
		}
	}

}