package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class egg_primeval extends warrior_hold
{
	public int BroadCastRange = 500;
	public int ProbBroadCast = 80;
	public String Privates = "";

	public egg_primeval(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		CreatePrivates(Privates);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (AiUtils.Rand(100) <= ProbBroadCast)
		{
			BroadcastScriptEvent(ScriptEvent.SCE_ONE_POINT_ATTACK, AiUtils.GetIndexFromCreature(attacker), BroadCastRange);
		}
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{

		if (AiUtils.Rand(100) < 3)
		{
			if (!IsNullCreature(attacker))
			{
				if (GetInventoryInfo(attacker, 0) >= GetInventoryInfo(attacker, 1) * 0.800000f || GetInventoryInfo(attacker, 2) >= GetInventoryInfo(attacker, 3) * 0.800000f)
				{
					ShowSystemMessageStr(attacker, AiUtils.MakeFString(1800879, "", "", "", "", ""));
					return;
				}
				GetItemData(attacker, 14828);
				CreatePet(attacker, 14828, 1016067, 55);
				ShowSystemMessageStr(attacker, AiUtils.MakeFString(1800878, "", "", "", "", ""));
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{

	}

}