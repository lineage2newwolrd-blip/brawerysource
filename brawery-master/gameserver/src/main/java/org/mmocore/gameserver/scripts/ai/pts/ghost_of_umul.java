package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ghost_of_umul extends warrior_aggressive_physicalspecial
{
	public ghost_of_umul(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		NpcInstance npc0 = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(22);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					npc0 = AiUtils.GetNPCFromID(actor.param3);
					if (!AiUtils.IsNull(npc0))
					{
						npc0.i_quest0 = 0;
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Creature c1 = null;

		SetCurrentQuestID(22);
		c1 = attacker;
		if (AiUtils.HaveMemo(c1, 22) == 1 && AiUtils.GetMemoState(c1, 22) == 10 && AiUtils.OwnItemCount(c1, 7144) >= 1)
		{
			SetMemoState(c1, 22, 11);
		}
		else if (AiUtils.HaveMemo(c1, 22) == 1 && AiUtils.GetMemoState(c1, 22) == 11 && AiUtils.OwnItemCount(c1, 7144) >= 1)
		{
			if (i_quest0 == 1)
			{
				DeleteItem1(c1, 7144, AiUtils.OwnItemCount(c1, 7144));
				GiveItem1(c1, 7145, 1);
				SoundEffect(c1, "ItemSound.quest_itemget");
				SetFlagJournal(c1, 22, 11);
				ShowQuestMark(c1, 22);
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

		SetCurrentQuestID(22);
		if (timer_id == 2203)
		{
			i_quest0 = 1;
		}
		else if (timer_id == 2204)
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

		SetCurrentQuestID(22);
		AddTimerEx(2203, 1000 * 90);
		AddTimerEx(2204, 1000 * 120);
		i0 = actor.param1;
		if (i0 > 0)
		{
			c0 = AiUtils.GetCreatureFromIndex(i0);
			if (c0 !=null)
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 2000);
			}
		}
		super.onEvtSpawn();
	}

}