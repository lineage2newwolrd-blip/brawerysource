package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class beamer extends wizard_ag_ddmagic2_curse
{
	public beamer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 328)!=0)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 42) == 1 && AiUtils.GetMemoState(target, 42) == (2 * 10 + 1))
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 42) == 1 && AiUtils.GetMemoState(target, 42) == (2 * 10 + 1))
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 42) == 1 && AiUtils.GetMemoState(target, 42) == (2 * 10 + 1))
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 1)
			{
				SetCurrentQuestID(214);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 214)!=0 && AiUtils.OwnItemCount(c1, 2676) == 1 && AiUtils.OwnItemCount(c1, 2690) == 1 && AiUtils.OwnItemCount(c1, 2694) == 1 && AiUtils.OwnItemCount(c1, 2695) < 5)
					{
						if (AiUtils.Rand(100) < 100)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2695, 1);
							if (AiUtils.OwnItemCount(c1, 2695) >= 4 && AiUtils.OwnItemCount(c1, 2696) >= 5 && AiUtils.OwnItemCount(c1, 2697) >= 2)
							{
								SetFlagJournal(c1, 214, 17);
								ShowQuestMark(c1, 214);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(328);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 78)
						{
							GiveItem1(target, 1347, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else if (i0 < 79)
						{
							GiveItem1(target, 1366, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(42);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if ((i4 < 1000 && 1000 != 0))
						{
							if (AiUtils.OwnItemCount(target, 7548) + 1 >= 30)
							{
								if (AiUtils.OwnItemCount(target, 7548) < 30)
								{
									GiveItem1(target, 7548, 30 - AiUtils.OwnItemCount(target, 7548));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 42, 3);
									ShowQuestMark(target, 42);
								}
								SetMemoState(target, 42, 2 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7548, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}