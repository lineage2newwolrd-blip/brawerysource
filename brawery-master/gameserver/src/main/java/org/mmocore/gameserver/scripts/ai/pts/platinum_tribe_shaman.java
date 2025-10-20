package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class platinum_tribe_shaman extends warrior_ag_casting_3skill_magical
{
	public platinum_tribe_shaman(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
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
		if (AiUtils.HaveMemo(target, 234) == 1 && AiUtils.GetMemoState(target, 234) == 8 && AiUtils.OwnItemCount(target, 14362) > 0)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 234) == 1 && AiUtils.GetMemoState(target, 234) == 8 && AiUtils.OwnItemCount(target, 14362) > 0)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 234) == 1 && AiUtils.GetMemoState(target, 234) == 8 && AiUtils.OwnItemCount(target, 14362) > 0)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 373)!=0)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 373)!=0)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 373)!=0)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 350) == 1)
		{
			random1_list.SetInfo(2, target);
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
		target = attacker;
		if ((AiUtils.HaveMemo(target, 348)!=0 && (AiUtils.GetMemoStateEx(target, 348, 0) == 12 || AiUtils.GetMemoStateEx(target, 348, 0) == 13)) && AiUtils.OwnItemCount(target, 4294) > 0)
		{
			random1_list.SetInfo(4, target);
		}
		if ((AiUtils.HaveMemo(target, 348)!=0 && (AiUtils.GetMemoStateEx(target, 348, 0) == 12 || AiUtils.GetMemoStateEx(target, 348, 0) == 13)) && AiUtils.OwnItemCount(target, 4294) > 0)
		{
			random1_list.SetInfo(4, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if ((AiUtils.HaveMemo(target, 348)!=0 && (AiUtils.GetMemoStateEx(target, 348, 0) == 12 || AiUtils.GetMemoStateEx(target, 348, 0) == 13)) && AiUtils.OwnItemCount(target, 4294) > 0)
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
					SetCurrentQuestID(234);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						GiveItem1(target, 14361, 1);
						DeleteItem1(target, 14362, 1);
						if (AiUtils.OwnItemCount(target, 14361) >= 29)
						{
							SetFlagJournal(target, 234, 9);
							ShowQuestMark(target, 234);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(373);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 658)
						{
							GiveItem1(target, 6008, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else if (i4 < 100)
						{
							GiveItem1(target, 6019, 2);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(350);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if ((target == c_quest0 && i_quest0 == 2))
						{
							if ((AiUtils.OwnItemCount(target, 4651) + AiUtils.OwnItemCount(target, 4652) + AiUtils.OwnItemCount(target, 4653) + AiUtils.OwnItemCount(target, 4654) + AiUtils.OwnItemCount(target, 4655) + AiUtils.OwnItemCount(target, 4656) + AiUtils.OwnItemCount(target, 4657) + AiUtils.OwnItemCount(target, 4658) + AiUtils.OwnItemCount(target, 4659) + AiUtils.OwnItemCount(target, 4660) + AiUtils.OwnItemCount(target, 4661) + AiUtils.OwnItemCount(target, 5579) + AiUtils.OwnItemCount(target, 5582) + AiUtils.OwnItemCount(target, 5914) + AiUtils.OwnItemCount(target, 4629) + AiUtils.OwnItemCount(target, 4630) + AiUtils.OwnItemCount(target, 4631) + AiUtils.OwnItemCount(target, 4632) + AiUtils.OwnItemCount(target, 4633) + AiUtils.OwnItemCount(target, 4634) + AiUtils.OwnItemCount(target, 4635) + AiUtils.OwnItemCount(target, 4636) + AiUtils.OwnItemCount(target, 4637) + AiUtils.OwnItemCount(target, 4638) + AiUtils.OwnItemCount(target, 4639) + AiUtils.OwnItemCount(target, 5577) + AiUtils.OwnItemCount(target, 5580) + AiUtils.OwnItemCount(target, 5908) + AiUtils.OwnItemCount(target, 4640) + AiUtils.OwnItemCount(target, 4641) + AiUtils.OwnItemCount(target, 4642) + AiUtils.OwnItemCount(target, 4643) + AiUtils.OwnItemCount(target, 4644) + AiUtils.OwnItemCount(target, 4645) + AiUtils.OwnItemCount(target, 4646) + AiUtils.OwnItemCount(target, 4647) + AiUtils.OwnItemCount(target, 4648) + AiUtils.OwnItemCount(target, 4649) + AiUtils.OwnItemCount(target, 4650) + AiUtils.OwnItemCount(target, 5578) + AiUtils.OwnItemCount(target, 5581) + AiUtils.OwnItemCount(target, 5911) + AiUtils.OwnItemCount(target, 9571) + AiUtils.OwnItemCount(target, 10161) + AiUtils.OwnItemCount(target, 9570) + AiUtils.OwnItemCount(target, 10160) + AiUtils.OwnItemCount(target, 9572) + AiUtils.OwnItemCount(target, 10162) + AiUtils.OwnItemCount(target, 10482) + AiUtils.OwnItemCount(target, 10481) + AiUtils.OwnItemCount(target, 10480) + AiUtils.OwnItemCount(target, 13072) + AiUtils.OwnItemCount(target, 13073) + AiUtils.OwnItemCount(target, 13071) + AiUtils.OwnItemCount(target, 15542) + AiUtils.OwnItemCount(target, 15543) + AiUtils.OwnItemCount(target, 15541)) == 1)
							{
								i0 = AiUtils.Rand(100);
								if (i0 <= 9)
								{
									if (AiUtils.OwnItemCount(target, 4651) == 1)
									{
										DeleteItem1(target, 4651, 1);
										GiveItem1(target, 4652, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4652) == 1)
									{
										DeleteItem1(target, 4652, 1);
										GiveItem1(target, 4653, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4653) == 1)
									{
										DeleteItem1(target, 4653, 1);
										GiveItem1(target, 4654, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4654) == 1)
									{
										DeleteItem1(target, 4654, 1);
										GiveItem1(target, 4655, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4655) == 1)
									{
										DeleteItem1(target, 4655, 1);
										GiveItem1(target, 4656, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4656) == 1)
									{
										DeleteItem1(target, 4656, 1);
										GiveItem1(target, 4657, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4657) == 1)
									{
										DeleteItem1(target, 4657, 1);
										GiveItem1(target, 4658, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4658) == 1)
									{
										DeleteItem1(target, 4658, 1);
										GiveItem1(target, 4659, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4659) == 1)
									{
										DeleteItem1(target, 4659, 1);
										GiveItem1(target, 4660, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4660) == 1)
									{
										DeleteItem1(target, 4660, 1);
										GiveItem1(target, 4661, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4629) == 1)
									{
										DeleteItem1(target, 4629, 1);
										GiveItem1(target, 4630, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4630) == 1)
									{
										DeleteItem1(target, 4630, 1);
										GiveItem1(target, 4631, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4631) == 1)
									{
										DeleteItem1(target, 4631, 1);
										GiveItem1(target, 4632, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4632) == 1)
									{
										DeleteItem1(target, 4632, 1);
										GiveItem1(target, 4633, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4633) == 1)
									{
										DeleteItem1(target, 4633, 1);
										GiveItem1(target, 4634, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4634) == 1)
									{
										DeleteItem1(target, 4634, 1);
										GiveItem1(target, 4635, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4635) == 1)
									{
										DeleteItem1(target, 4635, 1);
										GiveItem1(target, 4636, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4636) == 1)
									{
										DeleteItem1(target, 4636, 1);
										GiveItem1(target, 4637, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4637) == 1)
									{
										DeleteItem1(target, 4637, 1);
										GiveItem1(target, 4638, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4638) == 1)
									{
										DeleteItem1(target, 4638, 1);
										GiveItem1(target, 4639, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4640) == 1)
									{
										DeleteItem1(target, 4640, 1);
										GiveItem1(target, 4641, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4641) == 1)
									{
										DeleteItem1(target, 4641, 1);
										GiveItem1(target, 4642, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4642) == 1)
									{
										DeleteItem1(target, 4642, 1);
										GiveItem1(target, 4643, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4643) == 1)
									{
										DeleteItem1(target, 4643, 1);
										GiveItem1(target, 4644, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4644) == 1)
									{
										DeleteItem1(target, 4644, 1);
										GiveItem1(target, 4645, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4645) == 1)
									{
										DeleteItem1(target, 4645, 1);
										GiveItem1(target, 4646, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4646) == 1)
									{
										DeleteItem1(target, 4646, 1);
										GiveItem1(target, 4647, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4647) == 1)
									{
										DeleteItem1(target, 4647, 1);
										GiveItem1(target, 4648, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4648) == 1)
									{
										DeleteItem1(target, 4648, 1);
										GiveItem1(target, 4649, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else if (AiUtils.OwnItemCount(target, 4649) == 1)
									{
										DeleteItem1(target, 4649, 1);
										GiveItem1(target, 4650, 1);
										SoundEffect(target, "ItemSound.quest_itemget");
										ShowSystemMessage(target, 974);
									}
									else
									{
										ShowSystemMessage(target, 978);
									}
								}
								else
								{
									ShowSystemMessage(target, 975);
								}
							}
							else
							{
								ShowSystemMessage(target, 977);
							}
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
						if (AiUtils.Rand(1000) < 935)
						{
							GiveItem1(target, 7586, 5);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else
						{
							GiveItem1(target, 7586, 4);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(348);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.HaveMemo(target, 348)!=0 && AiUtils.GetMemoStateEx(target, 348, 0) == 12 && AiUtils.OwnItemCount(target, 4294) > 0)
						{
							target.quest_last_reward_time = GetCurrentTick();
							i0 = AiUtils.GetMemoStateEx(target, 348, 1);
							i0 = i0 + 600;
							SetMemoStateEx(target, 348, 1, i0);
							if (AiUtils.GetMemoStateEx(target, 348, 1) + 600 > 80000)
							{
								GiveItem1(target, 4295, 1);
								DeleteItem1(target, 4294, 1);
								RemoveMemo(target, 348);
								AiUtils.AddLog(3, target, 348);
								SoundEffect(target, "ItemSound.quest_finish");
							}
						}
						if (AiUtils.HaveMemo(target, 348)!=0 && AiUtils.GetMemoStateEx(target, 348, 0) == 13 && AiUtils.OwnItemCount(target, 4294) > 0)
						{
							target.quest_last_reward_time = GetCurrentTick();
							i0 = AiUtils.GetMemoStateEx(target, 348, 1);
							i0 = i0 + 600;
							SetMemoStateEx(target, 348, 1, i0);
							if (AiUtils.GetMemoStateEx(target, 348, 1) + 600 > 100000)
							{
								GiveItem1(target, 4295, 1);
								DeleteItem1(target, 4294, 1);
								SetMemoStateEx(target, 348, 0, 14);
								SoundEffect(target, "ItemSound.quest_middle");
							}
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Creature c0 = null;
		Creature c1 = null;
		Creature c2 = null;
		int i0 = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		final NpcInstance actor = getActor();

		SetCurrentQuestID(350);
		switch (i_quest0)
		{
			case 0:
			{
				i_quest0 = 1;
				if (skill_name_id / 65536 == 137363457 / 65536 && actor.currentHp <= actor.getMaxHp() * 0.500000f)
				{
					i_quest0 = 2;
					c_quest0 = attacker;
				}
				break;
			}
			case 1:
			{
				if (skill_name_id / 65536 == 137363457 / 65536 && actor.currentHp <= actor.getMaxHp() * 0.500000f)
				{
					i_quest0 = 2;
					c_quest0 = attacker;
				}
				break;
			}
			case 2:
			{
				break;
			}
		}
		SetCurrentQuestID(348);
		c1 = attacker;
		if (c1.getPlayer() !=null)
		{
			c1 = c1.getPlayer();
		}
		i1 = AiUtils.Party_GetCount(c1);
		i2 = 0;
		if (i1 == 0)
		{
			if ((AiUtils.HaveMemo(c1, 348)!=0 && (AiUtils.GetMemoStateEx(c1, 348, 0) == 12 || AiUtils.GetMemoStateEx(c1, 348, 0) == 13)) && AiUtils.OwnItemCount(c1, 4294) > 0)
			{
				c2 = c1;
			}
		}
		else
		{
			for (i0 = 0; i0 < i1; i0++)
			{
				c0 = AiUtils.Party_GetCreature(c1, i0);
				if ((AiUtils.HaveMemo(c0, 348)!=0 && (AiUtils.GetMemoStateEx(c0, 348, 0) == 12 || AiUtils.GetMemoStateEx(c0, 348, 0) == 13)) && AiUtils.OwnItemCount(c0, 4294) > 0)
				{
					i3 = AiUtils.Rand(1000);
					if (i2 <= i3)
					{
						i2 = i3;
						c2 = c0;
					}
				}
			}
		}
		if (!IsNullCreature(c2) && DistFromMe(c2) <= 1500)
		{
			if (AiUtils.HaveMemo(c2, 348)!=0 && AiUtils.GetMemoStateEx(c2, 348, 0) == 12 && AiUtils.OwnItemCount(c2, 4294) > 0)
			{
				if (GetCurrentTick() - c2.quest_last_reward_time > 1)
				{
					c2.quest_last_reward_time = GetCurrentTick();
					i0 = AiUtils.GetMemoStateEx(c2, 348, 1);
					i0 = i0 + 60;
					SetMemoStateEx(c2, 348, 1, i0);
					if (AiUtils.GetMemoStateEx(c2, 348, 1) + 60 > 80000)
					{
						GiveItem1(c2, 4295, 1);
						DeleteItem1(c2, 4294, 1);
						RemoveMemo(c2, 348);
						AiUtils.AddLog(3, c2, 348);
						SoundEffect(c2, "ItemSound.quest_finish");
					}
				}
			}
			if (AiUtils.HaveMemo(c2, 348)!=0 && AiUtils.GetMemoStateEx(c2, 348, 0) == 13 && AiUtils.OwnItemCount(c2, 4294) > 0)
			{
				if (GetCurrentTick() - c2.quest_last_reward_time > 1)
				{
					c2.quest_last_reward_time = GetCurrentTick();
					i0 = AiUtils.GetMemoStateEx(c2, 348, 1);
					i0 = i0 + 60;
					SetMemoStateEx(c2, 348, 1, i0);
					if (AiUtils.GetMemoStateEx(c2, 348, 1) + 60 > 100000)
					{
						GiveItem1(c2, 4295, 1);
						DeleteItem1(c2, 4294, 1);
						SetMemoStateEx(c2, 348, 0, 14);
						SoundEffect(c2, "ItemSound.quest_middle");
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}