package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class triyol_zzolda extends warrior_aggressive_physicalspecial
{
	public triyol_zzolda(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Creature c1 = null;
		NpcInstance npc0 = null;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(25);
		c1 = attacker;
		if (AiUtils.FloatToInt(actor.currentHp) <= 30)
		{
			if (AiUtils.HaveMemo(c1, 25) == 1 && AiUtils.GetMemoState(c1, 25) == 8 && AiUtils.OwnItemCount(c1, 7158) == 0 && c1.getObjectId() == actor.param2)
			{
				GiveItem1(c1, 7158, 1);
				SoundEffect(c1, "ItemSound.quest_itemget");
				SetFlagJournal(c1, 25, 8);
				ShowQuestMark(c1, 25);
				npc0 = AiUtils.GetNPCFromID(actor.param3);
				if (!AiUtils.IsNull(npc0))
				{
					npc0.i_quest0 = 0;
				}
				Say(AiUtils.MakeFString(2551, "", "", "", "", ""));
				Despawn();
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;
		NpcInstance npc0 = null;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(25);
		if (timer_id == 2501)
		{
			c0 = AiUtils.GetCreatureFromIndex(actor.param1);
			if (c0 !=null)
			{
				Say(AiUtils.MakeFString(2550, c0.getName(), "", "", "", ""));
			}
		}
		else if (timer_id == 2502)
		{
			npc0 = AiUtils.GetNPCFromID(actor.param3);
			if (!AiUtils.IsNull(npc0))
			{
				npc0.i_quest0 = 0;
			}
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(25);
		AddTimerEx(2501, 500);
		AddTimerEx(2502, 1000 * 120);
		i0 = actor.param1;
		if (i0 > 0)
		{
			c0 = AiUtils.GetCreatureFromIndex(i0);
			if (c0 !=null)
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 20000);
			}
		}
		super.onEvtSpawn();
	}

}