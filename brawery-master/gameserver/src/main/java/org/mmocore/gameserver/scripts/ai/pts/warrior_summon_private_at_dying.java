package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_summon_private_at_dying extends warrior
{
	public String Privates = "orc:party_private:1:20sec";
	public int SummonPrivateRate = 0;

	public warrior_summon_private_at_dying(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();

		if (!IsNullCreature(top_desire_target))
		{
			if (top_desire_target == attacker)
			{
				i_ai3 = AiUtils.GetIndexFromCreature(attacker);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{

		if (AiUtils.Rand(100) < SummonPrivateRate)
		{
			CreatePrivates(Privates);
			BroadcastScriptEvent(ScriptEvent.SCE_ATTACK_BOSS_TARGET, i_ai3, 500);
			AddTimerEx(5001, 1500);
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;

		if (timer_id == 5001)
		{
			BroadcastScriptEvent(ScriptEvent.SCE_ATTACK_BOSS_TARGET, i_ai3, 500);
			AddTimerEx(5001, 1500);
			super.onEvtTimerFiredEx(timer_id);
		}
	}

}