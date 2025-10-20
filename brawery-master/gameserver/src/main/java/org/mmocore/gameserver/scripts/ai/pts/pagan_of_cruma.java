package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class pagan_of_cruma extends warrior_passive
{
	public pagan_of_cruma(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(134);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c0 = AiUtils.GetCreatureFromIndex(actor.param1);
					if (!AiUtils.IsNull(c0))
					{
						if (AiUtils.HaveMemo(c0, 134) >= 1 && AiUtils.GetMemoState(c0, 134) == 3 && AiUtils.OwnItemCount(c0, 10337) < 3 && DistFromMe(c0) <= 1500)
						{
							GiveItem1(c0, 10337, 1);
							if (AiUtils.OwnItemCount(c0, 10337) >= 2)
							{
								SetFlagJournal(c0, 134, 4);
								ShowQuestMark(c0, 134);
								SoundEffect(c0, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(c0, "ItemSound.quest_itemget");
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

		SetCurrentQuestID(134);
		if (timer_id == 13401)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		int i0 = 0;

		SetCurrentQuestID(134);
		AddTimerEx(13401, 1000 * 60);
		super.onEvtSpawn();
	}

}