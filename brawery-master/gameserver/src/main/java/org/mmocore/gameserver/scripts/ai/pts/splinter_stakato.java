package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class splinter_stakato extends warrior_run_away_physicalspecial
{
	public splinter_stakato(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 628) == 1 && AiUtils.OwnItemCount(target, 7247) < 1 && AiUtils.OwnItemCount(target, 7248) < 100)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 628) == 1 && AiUtils.OwnItemCount(target, 7247) < 1 && AiUtils.OwnItemCount(target, 7248) < 100)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 628) == 1 && AiUtils.OwnItemCount(target, 7247) < 1 && AiUtils.OwnItemCount(target, 7248) < 100)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 629) == 1 && GetOneTimeQuestFlag(target, 629) == 0 && AiUtils.GetMemoState(target, 629) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 629) == 1 && GetOneTimeQuestFlag(target, 629) == 0 && AiUtils.GetMemoState(target, 629) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 629) == 1 && GetOneTimeQuestFlag(target, 629) == 0 && AiUtils.GetMemoState(target, 629) == (1 * 10 + 1))
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (12 * 10 + 1) && target.isSubClassActive() != 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (12 * 10 + 1) && target.isSubClassActive() != 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (12 * 10 + 1) && target.isSubClassActive() != 0)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 662)!=0)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 662)!=0)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 662)!=0)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(4, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(4, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(4, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			code_info.getCode();
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(628);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 50)
						{
							if (AiUtils.OwnItemCount(target, 7246) >= 1)
							{
								if (AiUtils.OwnItemCount(target, 7248) >= 99)
								{
									GiveItem1(target, 7248, 1);
									SoundEffect(target, "ItemSound.quest_middle");
								}
								else
								{
									GiveItem1(target, 7248, 1);
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
							else if (AiUtils.OwnItemCount(target, 7248) >= 99)
							{
								GiveItem1(target, 7248, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 7248, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(629);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if ((i4 < 599 && 599 != 0))
						{
							GiveItem1(target, 7250, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(241);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 100)
						{
							if (AiUtils.OwnItemCount(target, 7598) + 1 >= 5)
							{
								if (AiUtils.OwnItemCount(target, 7598) < 5)
								{
									GiveItem1(target, 7598, 5 - AiUtils.OwnItemCount(target, 7598));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 241, 15);
									ShowQuestMark(target, 241);
									SetMemoState(target, 241, 12 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7598, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(662);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 493)
						{
							GiveItem1(target, 8765, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 10)
						{
							GiveItem1(target, 7586, 2);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else
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