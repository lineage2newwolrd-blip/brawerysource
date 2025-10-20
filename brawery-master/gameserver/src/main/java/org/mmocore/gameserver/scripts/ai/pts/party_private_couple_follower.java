package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_couple_follower extends party_private
{
	public int silhouette = 1020130;
	public String ai_type = "warrior";

	public party_private_couple_follower(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
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
					CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ() + 10), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 1);
					CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 10), AiUtils.FloatToInt(actor.getZ() + 10), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 2);
					CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 5), AiUtils.FloatToInt(actor.getY() + 5), AiUtils.FloatToInt(actor.getZ() + 10), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 3);
					i_ai0 = 1;
				}
			}
		}
	}

}