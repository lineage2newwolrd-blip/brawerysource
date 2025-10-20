package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class party_leader_wizard_dd2_soulshot_summon extends party_leader_wizard_dd2_summon
{
	public party_leader_wizard_dd2_soulshot_summon(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}