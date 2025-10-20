package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_3skill_approach_revival extends warrior_casting_3skill_approach
{
	public int silhouette = 1020130;
	public String ai_type = "warrior";

	public warrior_casting_3skill_approach_revival(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai4 = -1;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		if (!IsNullCreature(top_desire_target))
		{
			if (top_desire_target.isPlayer())
			{
				i_ai4 = AiUtils.GetIndexFromCreature(top_desire_target);
			}
			else if (IsInCategory(Category.summon_npc_group, top_desire_target.getClassId()) && !IsNullCreature(top_desire_target.getPlayer()))
			{
				i_ai4 = AiUtils.GetIndexFromCreature(top_desire_target);
			}
		}
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai4 != -1)
		{
			CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, i_ai4, 0);
		}
	}

}