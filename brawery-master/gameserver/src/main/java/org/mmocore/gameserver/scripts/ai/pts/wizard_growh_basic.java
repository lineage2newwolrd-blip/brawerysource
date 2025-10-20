package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class wizard_growh_basic extends wizard_ddmagic2
{
	public int silhouette1 = 1020130;
	public String ai_type1 = "default_npc";
	public int silhouette2 = 1020130;
	public String ai_type2 = "default_npc";
	public int silhouette_ex = 33;
	public String ai_type_ex = "default_npc";
	public int silhouette_ex2 = 33;
	public String ai_type_ex2 = "default_npc";
	public int TakeSocial = 5;

	public wizard_growh_basic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}