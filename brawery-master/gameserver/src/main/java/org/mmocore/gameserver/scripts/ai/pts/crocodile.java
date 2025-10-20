package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class crocodile extends warrior_aggressive
{
	public crocodile(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 338)!=0)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 32) == 1 && GetOneTimeQuestFlag(target, 32) == 0 && AiUtils.GetMemoState(target, 32) == (3 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 32) == 1 && GetOneTimeQuestFlag(target, 32) == 0 && AiUtils.GetMemoState(target, 32) == (3 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 32) == 1 && GetOneTimeQuestFlag(target, 32) == 0 && AiUtils.GetMemoState(target, 32) == (3 * 10 + 1))
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 35) == 1 && AiUtils.GetMemoState(target, 35) == (2 * 10 + 1))
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 35) == 1 && AiUtils.GetMemoState(target, 35) == (2 * 10 + 1))
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 35) == 1 && AiUtils.GetMemoState(target, 35) == (2 * 10 + 1))
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 141) == 1 && AiUtils.GetMemoState(target, 141) == 2 && GetOneTimeQuestFlag(target, 141) == 0 && AiUtils.OwnItemCount(target, 10350) < 30)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 141) == 1 && AiUtils.GetMemoState(target, 141) == 2 && GetOneTimeQuestFlag(target, 141) == 0 && AiUtils.OwnItemCount(target, 10350) < 30)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 141) == 1 && AiUtils.GetMemoState(target, 141) == 2 && GetOneTimeQuestFlag(target, 141) == 0 && AiUtils.OwnItemCount(target, 10350) < 30)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(4, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(5, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(5, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(5, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 4)
			{
				SetCurrentQuestID(711);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c0 = Pledge_GetLeader(target);
					if (!IsNullCreature(c0))
					{
						if (AiUtils.HaveMemo(c0, 711) == 1 && AiUtils.GetMemoState(c0, 711) / 1000 >= 1 && AiUtils.GetMemoState(c0, 711) / 1000 < 101 && DistFromMe(c0) <= 1500)
						{
							i0 = AiUtils.GetMemoState(c0, 711);
							if (AiUtils.GetMemoState(c0, 711) / 1000 < 100)
							{
								SetMemoState(c0, 711, i0 + 1000);
							}
							else
							{
								SetMemoState(c0, 711, i0 + 1000);
								SetFlagJournal(c0, 711, 6);
								ShowQuestMark(c0, 711);
								SoundEffect(c0, "ItemSound.quest_middle");
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
					SetCurrentQuestID(338);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						GiveItem1(target, 4337, 1);
						SoundEffect(target, "ItemSound.quest_itemget");
						if (AiUtils.Rand(100) < 19)
						{
							GiveItem1(target, 4337, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(32);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(500);
						if (i4 < 500)
						{
							if (AiUtils.OwnItemCount(target, 7166) + 1 >= 20)
							{
								if (AiUtils.OwnItemCount(target, 7166) <= 20)
								{
									GiveItem1(target, 7166, 20 - AiUtils.OwnItemCount(target, 7166));
									SoundEffect(target, "ItemSound.quest_middle");
								}
								SetFlagJournal(target, 32, 4);
								ShowQuestMark(target, 32);
								SetMemoState(target, 32, 3 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7166, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(35);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 500)
						{
							if (AiUtils.OwnItemCount(target, 7162) + 1 >= 10)
							{
								if (AiUtils.OwnItemCount(target, 7162) < 10)
								{
									GiveItem1(target, 7162, 10 - AiUtils.OwnItemCount(target, 7162));
									SoundEffect(target, "ItemSound.quest_middle");
								}
								SetFlagJournal(target, 35, 3);
								ShowQuestMark(target, 35);
								SetMemoState(target, 35, 2 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7162, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(141);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 53)
						{
							if (AiUtils.OwnItemCount(target, 10350) >= 29)
							{
								GiveItem1(target, 10350, 1);
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 141, 3);
								ShowQuestMark(target, 141);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 10350, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 36)
						{
							GiveItem1(target, 7586, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}