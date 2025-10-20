package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_physicalspecial_summon extends party_leader
{
	public int Buff = 458752001;
	public int PhysicalSpecial = 458752001;
	public int silhouette = 1020130;
	public String ai_type = "warrior";

	public party_leader_physicalspecial_summon(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i1 = 0;
		int i2 = 0;
		final NpcInstance actor = getActor();

		if (actor.currentHp < actor.getMaxHp() * 0.500000f && i_ai0 < 2 && i_ai1 == 0)
		{
			i1 = AiUtils.Rand(50) - 25;
			i2 = AiUtils.Rand(50) - 25;
			CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + i1), AiUtils.FloatToInt(actor.getY() + i2), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			i_ai0 = i_ai0 + 1;
			i_ai1 = 1;
			AddTimerEx(6006, 5 * 1000);
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 6006)
		{
			i_ai1 = 0;
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}