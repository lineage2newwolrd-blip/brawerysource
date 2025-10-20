package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class brilliant_cry extends wizard_ag_saint_range_ddmagic2
{
	public brilliant_cry(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
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
		if (AiUtils.HaveMemo(target, 626) == 1 && AiUtils.GetMemoState(target, 626) == (1 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 626) == 1 && AiUtils.GetMemoState(target, 626) == (1 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 626) == 1 && AiUtils.GetMemoState(target, 626) == (1 * 10 + 1))
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 627) == 1 && AiUtils.GetMemoState(target, 627) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 627) == 1 && AiUtils.GetMemoState(target, 627) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 627) == 1 && AiUtils.GetMemoState(target, 627) == (1 * 10 + 1))
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 236) == 1 && GetOneTimeQuestFlag(target, 236) == 0 && AiUtils.GetMemoState(target, 236) == 21 && AiUtils.OwnItemCount(target, 9743) < 62)
		{
			always_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 236) == 1 && GetOneTimeQuestFlag(target, 236) == 0 && AiUtils.GetMemoState(target, 236) == 21 && AiUtils.OwnItemCount(target, 9743) < 62)
		{
			always_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 236) == 1 && GetOneTimeQuestFlag(target, 236) == 0 && AiUtils.GetMemoState(target, 236) == 21 && AiUtils.OwnItemCount(target, 9743) < 62)
				{
					always_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 2)
			{
				SetCurrentQuestID(236);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i0 = AiUtils.Rand(100);
					if (i0 < 70)
					{
						GiveItem1(target, 9743, 1);
						if (AiUtils.OwnItemCount(target, 9743) >= 61)
						{
							SetFlagJournal(target, 236, 13);
							ShowQuestMark(target, 236);
							SoundEffect(target, "ItemSound.quest_middle");
							SetMemoState(target, 236, 22);
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
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
					SetCurrentQuestID(626);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 795)
						{
							if (AiUtils.OwnItemCount(target, 7169) + 1 >= 300)
							{
								if (AiUtils.OwnItemCount(target, 7169) < 300)
								{
									GiveItem1(target, 7169, 300 - AiUtils.OwnItemCount(target, 7169));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 626, 2);
									ShowQuestMark(target, 626);
									SetMemoState(target, 626, 1 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7169, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(627);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 820)
						{
							if (AiUtils.OwnItemCount(target, 7171) + 1 >= 300)
							{
								if (AiUtils.OwnItemCount(target, 7171) < 300)
								{
									GiveItem1(target, 7171, 300 - AiUtils.OwnItemCount(target, 7171));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 627, 2);
									ShowQuestMark(target, 627);
									SetMemoState(target, 627, 1 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7171, 1);
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