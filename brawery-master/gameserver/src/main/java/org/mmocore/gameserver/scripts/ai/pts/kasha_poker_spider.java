package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class kasha_poker_spider extends warrior_passive
{
	public kasha_poker_spider(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 3 && AiUtils.OwnItemCount(target, 8545) < 6)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 3 && AiUtils.OwnItemCount(target, 8545) < 6)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 3 && AiUtils.OwnItemCount(target, 8545) < 6)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 419) == 1 && AiUtils.OwnItemCount(target, 3421) == 1 && AiUtils.OwnItemCount(target, 3426) < 50)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 268)!=0 && AiUtils.GetMemoState(target, 268) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 268)!=0 && AiUtils.GetMemoState(target, 268) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 268)!=0 && AiUtils.GetMemoState(target, 268) == 1)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(3, target);
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
					SetCurrentQuestID(415);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (target.getWeaponType() == 5 || target.getWeaponType() == 9)
						{
							i0 = AiUtils.Rand(100);
							if (i0 < 70)
							{
								GiveItem1(target, 8545, 1);
								if (AiUtils.OwnItemCount(target, 8545) >= 5)
								{
									SetFlagJournal(target, 415, 16);
									ShowQuestMark(target, 415);
									SoundEffect(target, "ItemSound.quest_middle");
								}
								else
								{
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(419);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 75)
						{
							GiveItem1(target, 3426, 1);
							if (AiUtils.OwnItemCount(target, 3426) >= 50)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(268);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 10869) == 29)
						{
							GiveItem1(target, 10869, 1);
							SoundEffect(target, "ItemSound.quest_middle");
							SetMemoState(target, 268, 2);
							SetFlagJournal(target, 268, 2);
							ShowQuestMark(target, 268);
						}
						else
						{
							GiveItem1(target, 10869, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 11)
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