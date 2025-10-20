package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_3skill_magical_revival extends warrior_casting_3skill_magical
{
	public int silhouette = 1020130;
	public String ai_type = "warrior";

	public warrior_casting_3skill_magical_revival(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		if (!IsNullCreature(top_desire_target))
		{
			if (top_desire_target.isPlayer())
			{
				c_ai0 = top_desire_target;
			}
		}
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		final NpcInstance actor = getActor();

		CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(c_ai0), 0);
	}

}