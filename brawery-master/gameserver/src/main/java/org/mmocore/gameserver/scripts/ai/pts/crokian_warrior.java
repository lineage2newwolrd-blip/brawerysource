package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class crokian_warrior extends warrior_aggressive_casting_enchant_clan
{
	public crokian_warrior(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
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
		target = attacker;
		if (AiUtils.HaveMemo(target, 140) == 1 && AiUtils.GetMemoState(target, 140) == 4)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 140) == 1 && AiUtils.GetMemoState(target, 140) == 4)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 140) == 1 && AiUtils.GetMemoState(target, 140) == 4)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 141) == 1 && AiUtils.GetMemoState(target, 141) == 2 && GetOneTimeQuestFlag(target, 141) == 0 && AiUtils.OwnItemCount(target, 10350) < 30)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 141) == 1 && AiUtils.GetMemoState(target, 141) == 2 && GetOneTimeQuestFlag(target, 141) == 0 && AiUtils.OwnItemCount(target, 10350) < 30)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 141) == 1 && AiUtils.GetMemoState(target, 141) == 2 && GetOneTimeQuestFlag(target, 141) == 0 && AiUtils.OwnItemCount(target, 10350) < 30)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(3, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(345);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 345)!=0 && AiUtils.GetMemoState(c1, 345) == 1)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								i0 = AiUtils.Rand(100);
								if (i0 <= 5)
								{
									if (AiUtils.OwnItemCount(c1, 4274) == 0)
									{
										GiveItem1(c1, 4274, 1);
									}
									else
									{
										GiveItem1(c1, 4280, 1);
									}
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 <= 11)
								{
									if (AiUtils.OwnItemCount(c1, 4275) == 0)
									{
										GiveItem1(c1, 4275, 1);
									}
									else
									{
										GiveItem1(c1, 4280, 1);
									}
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 <= 17)
								{
									if (AiUtils.OwnItemCount(c1, 4276) == 0)
									{
										GiveItem1(c1, 4276, 1);
									}
									else
									{
										GiveItem1(c1, 4280, 1);
									}
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 <= 23)
								{
									if (AiUtils.OwnItemCount(c1, 4277) == 0)
									{
										GiveItem1(c1, 4277, 1);
									}
									else
									{
										GiveItem1(c1, 4280, 1);
									}
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 <= 29)
								{
									if (AiUtils.OwnItemCount(c1, 4278) == 0)
									{
										GiveItem1(c1, 4278, 1);
									}
									else
									{
										GiveItem1(c1, 4280, 1);
									}
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
								else if (i0 <= 60)
								{
									GiveItem1(c1, 4280, 1);
								}
							}
						}
					}
					break;
				}
				case 3:
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
					break;
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 1:
				{
					SetCurrentQuestID(140);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 100)
						{
							GiveItem1(target, 10347, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(141);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 100)
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
			}
		}
		super.onEvtDead(attacker);
	}

}