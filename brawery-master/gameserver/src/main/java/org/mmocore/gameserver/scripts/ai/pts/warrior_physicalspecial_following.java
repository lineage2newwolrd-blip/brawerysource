package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_physicalspecial_following extends warrior_physicalspecial
{
	public warrior_physicalspecial_following(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		float f0 = 0;
		float f1 = 0;

		if (script_event_arg1 == ScriptEvent.SCE_GATHERING_NPC)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				f0 = c0.getX() - 50 + AiUtils.Rand(100);
				f1 = c0.getY() - 50 + AiUtils.Rand(100);
				AddMoveToDesire(AiUtils.FloatToInt(f0), AiUtils.FloatToInt(f1), AiUtils.FloatToInt(c0.getZ()), 15);
			}
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

}