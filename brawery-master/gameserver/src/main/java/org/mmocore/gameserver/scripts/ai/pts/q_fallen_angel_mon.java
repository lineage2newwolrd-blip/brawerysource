package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class q_fallen_angel_mon extends warrior_passive
{
	public q_fallen_angel_mon(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		CodeInfo code_info = null;
		int i0 = 0;
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
		always_list.SetInfo(0, target);
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				always_list.SetInfo(0, target);
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(142);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					npc0 = AiUtils.GetNPCFromID(actor.param3);
					c0 = AiUtils.GetCreatureFromIndex(actor.param1);
					if (!AiUtils.IsNull(npc0))
					{
						if (npc0.i_quest0 == 1)
						{
							if (!AiUtils.IsNull(c0))
							{
								npc0.i_quest0 = 0;
								if (AiUtils.HaveMemo(c0, 142) == 1 && AiUtils.GetMemoState(c0, 142) == 8 && GetOneTimeQuestFlag(c0, 142) == 0)
								{
									SetMemoState(c0, 142, 9);
									SetFlagJournal(c0, 142, 6);
									ShowQuestMark(c0, 142);
									SoundEffect(c0, "ItemSound.quest_middle");
									GiveItem1(c0, 10353, 1);
								}
							}
						}
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
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;
		NpcInstance npc0 = null;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(142);
		if (timer_id == 14201)
		{
			npc0 = AiUtils.GetNPCFromID(actor.param3);
			c0 = AiUtils.GetCreatureFromIndex(actor.param1);
			if (!AiUtils.IsNull(npc0))
			{
				if (npc0.i_quest0 == 1)
				{
					if (!AiUtils.IsNull(c0))
					{
						npc0.i_quest0 = 0;
					}
				}
			}
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(142);
		AddTimerEx(14201, 1000 * 120);
		super.onEvtSpawn();
	}

}