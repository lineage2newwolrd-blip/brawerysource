package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.ai.ScriptEvent;

public class party_private_ag_velociraptor extends party_private_ag_physicalspecial
{
	public party_private_ag_velociraptor(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		String s0 = null;

		i_ai4 = GetCurrentTick();
		i_quest0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		String s0 = null;

		if (script_event_arg1 == ScriptEvent.SCE_SAILREN_PRIVATE_NODESIRE)
		{
			i_quest0 = 1;
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

	@Override
	protected void onEvtNoDesire()
	{
		default_maker maker0 = null;

		if (i_quest0 == 1)
		{
			if (GetCurrentTick() - i_ai4 > 60 * 10)
			{
				BroadcastScriptEvent(ScriptEvent.SCE_SAILREN_PRIVATE_DESPAWN, 0, 8000);
			}
		}
		super.onEvtNoDesire();
	}

}