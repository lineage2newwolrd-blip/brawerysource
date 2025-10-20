package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class q_bloody_senior extends warrior_aggressive
{
	public q_bloody_senior(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(709);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c0 = Pledge_GetLeader(target);
					if (!IsNullCreature(c0))
					{
						if (DistFromMe(c0) <= 1500 && AiUtils.HaveMemo(c0, 709) == 1 && AiUtils.GetMemoState(c0, 709) / 10 == 2)
						{
							GiveItem1(c0, 13850, 1);
							i0 = AiUtils.GetMemoState(c0, 709);
							SetMemoState(c0, 709, i0 + 10);
							SetFlagJournal(c0, 709, 7);
							ShowQuestMark(c0, 709);
							SoundEffect(c0, "ItemSound.quest_middle");
							Say(AiUtils.MakeFString(70956, "", "", "", "", ""));
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
		int i0 = 0;
		Player talker = null;

		SetCurrentQuestID(709);
		if (timer_id == 70902)
		{
			Say(AiUtils.MakeFString(70957, "", "", "", "", ""));
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		Creature c1 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(709);
		c0 = AiUtils.GetCreatureFromIndex(actor.param1);
		if (!IsNullCreature(c0))
		{
			if (c0.isPlayer())
			{
				Say(AiUtils.MakeFString(70955, c0.getName(), "", "", "", ""));
			}
		}
		AddTimerEx(70902, 1000 * 120);
		super.onEvtSpawn();
	}

}