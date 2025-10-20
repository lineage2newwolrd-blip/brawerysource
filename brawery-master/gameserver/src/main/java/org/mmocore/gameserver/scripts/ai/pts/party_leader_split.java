package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_split extends warrior
{
	public int silhouette = 1020130;
	public String ai_type = "warrior";

	public party_leader_split(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		i0 = AiUtils.Skill_IsMagic(skill_name_id);
		if ((i0 == 0 && i_ai1 == 0))
		{
			if (skill_name_id != 0)
			{
				CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
				CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 20), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
				CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 40), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
				CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 20), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
				CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 40), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
				CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 60), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
				i_ai1 = 1;
				Despawn();
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}