package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_wizard_dd2_summon extends party_leader_wizard_dd2
{
	public int silhouette = 1020130;
	public String ai_type = "warrior";
	public int silhouette2 = 1020130;
	public String ai_type2 = "warrior";
	public int silhouette3 = 1020130;
	public String ai_type3 = "warrior";

	public party_leader_wizard_dd2_summon(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i1 = 0;
		int i2 = 0;
		final NpcInstance actor = getActor();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		if (i_ai1 == 0)
		{
			i1 = AiUtils.Rand(50) - 25;
			i2 = AiUtils.Rand(50) - 25;
			CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + i1), AiUtils.FloatToInt(actor.getY() + i2), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			i1 = AiUtils.Rand(100) - 50;
			i2 = AiUtils.Rand(100) - 50;
			CreateOnePrivateEx(silhouette2, ai_type2, 0, 0, AiUtils.FloatToInt(actor.getX() + i1), AiUtils.FloatToInt(actor.getY() + i2), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			i1 = AiUtils.Rand(100) - 50;
			i2 = AiUtils.Rand(100) - 50;
			CreateOnePrivateEx(silhouette3, ai_type3, 0, 0, AiUtils.FloatToInt(actor.getX() + i1), AiUtils.FloatToInt(actor.getY() + i2), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			i_ai1 = 1;
		}
	}

}