package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class wizard_ddmagic2_soulshot extends wizard_ddmagic2
{
	public wizard_ddmagic2_soulshot(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}