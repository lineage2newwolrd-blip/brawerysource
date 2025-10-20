package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_physicalspecial_area extends warrior_physicalspecial
{
	public String AreaName = "warrior_physicalspecial_area_default";
	public int physicalspecial = 458752001;

	public warrior_physicalspecial_area(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AiUtils.Area_SetOnOff(AreaName, true);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{

		AiUtils.Area_SetOnOff(AreaName, false);
		super.onEvtDead(attacker);
	}

}