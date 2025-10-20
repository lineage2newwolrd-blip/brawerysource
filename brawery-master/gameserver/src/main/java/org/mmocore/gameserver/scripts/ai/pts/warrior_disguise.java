package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_disguise extends warrior
{
	public int silhouette = 1020130;
	public String ai_type = "warrior";

	public warrior_disguise(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai2 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (i_ai2 == 0)
		{
			CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			i_ai2 = 1;
			Despawn();
		}
	}

}