package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class timak_orc extends warrior_passive
{
	public timak_orc(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i4 = 0;
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
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 350) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 66) == 1 && AiUtils.GetMemoState(target, 66) == 32)
		{
			always_list.SetInfo(4, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(220);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 220)!=0 && AiUtils.OwnItemCount(c1, 3209)!=0 && AiUtils.OwnItemCount(c1, 3217)!=0 && AiUtils.OwnItemCount(c1, 3219) < 20)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3219) == 19)
								{
									GiveItem1(c1, 3219, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3218) >= 20)
									{
										SetFlagJournal(c1, 220, 7);
										ShowQuestMark(c1, 220);
									}
								}
								else
								{
									GiveItem1(c1, 3219, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(232);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 232) == 1 && AiUtils.OwnItemCount(c1, 3391) == 1 && AiUtils.OwnItemCount(c1, 3394) == 1 && AiUtils.OwnItemCount(c1, 3404) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3403) < 10 && AiUtils.Rand(100) <= 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3403, 1);
								if (AiUtils.OwnItemCount(c1, 3403) >= 10)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(66);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.GetMemoStateEx(target, 66, 1);
						if (i4 < 5)
						{
							SetMemoStateEx(target, 66, 1, i4 + 1);
						}
						else if (i4 >= 4)
						{
							SetMemoStateEx(target, 66, 1, 0);
							CreateOnePrivateEx(27336, "lady_of_crimson", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
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
				case 2:
				{
					SetCurrentQuestID(336);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 80)
						{
							GiveItem1(target, 3482, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 3:
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
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
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
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}