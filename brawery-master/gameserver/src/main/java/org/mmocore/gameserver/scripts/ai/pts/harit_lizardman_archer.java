package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class harit_lizardman_archer extends warrior_aggressive_casting_enchant_self
{
	public harit_lizardman_archer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		int weapon_class_id = 0;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 350) == 1)
		{
			random1_list.SetInfo(1, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(335);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (GetCurrentTick() - c1.quest_last_reward_time > 1)
					{
						c1.quest_last_reward_time = GetCurrentTick();
						if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3751)!=0 && AiUtils.OwnItemCount(c1, 3793) < 40)
						{
							if (AiUtils.Rand(100) < 98)
							{
								GiveItem1(c1, 3793, 1);
								if (AiUtils.OwnItemCount(c1, 3793) >= 40)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
						}
						if (AiUtils.HaveMemo(c1, 335)!=0 && actor.last_blow_weapon_class_id == 3471 && (AiUtils.OwnItemCount(c1, 3692)!=0 || AiUtils.OwnItemCount(c1, 3693)!=0))
						{
							if (AiUtils.Rand(100) < 60)
							{
								if (AiUtils.OwnItemCount(c1, 3697)!=0)
								{
									if (AiUtils.OwnItemCount(c1, 3698)!=0)
									{
										GiveItem1(c1, 3699, 1);
										DeleteItem1(c1, 3698, AiUtils.OwnItemCount(c1, 3698));
									}
									else if (AiUtils.OwnItemCount(c1, 3699)!=0)
									{
										GiveItem1(c1, 3700, 1);
										DeleteItem1(c1, 3699, AiUtils.OwnItemCount(c1, 3699));
									}
									else if (AiUtils.OwnItemCount(c1, 3700)!=0)
									{
										GiveItem1(c1, 3701, 1);
										DeleteItem1(c1, 3700, AiUtils.OwnItemCount(c1, 3700));
									}
									else if (AiUtils.OwnItemCount(c1, 3701)!=0)
									{
										GiveItem1(c1, 3702, 1);
										DeleteItem1(c1, 3701, AiUtils.OwnItemCount(c1, 3701));
									}
									else if (AiUtils.OwnItemCount(c1, 3702)!=0)
									{
										GiveItem1(c1, 3703, 1);
										DeleteItem1(c1, 3702, AiUtils.OwnItemCount(c1, 3702));
									}
									else if (AiUtils.OwnItemCount(c1, 3703)!=0)
									{
										GiveItem1(c1, 3704, 1);
										SoundEffect(c1, "ItemSound.quest_jackpot");
										DeleteItem1(c1, 3703, AiUtils.OwnItemCount(c1, 3703));
									}
									else if (AiUtils.OwnItemCount(c1, 3704)!=0)
									{
										GiveItem1(c1, 3705, 1);
										SoundEffect(c1, "ItemSound.quest_jackpot");
										DeleteItem1(c1, 3704, AiUtils.OwnItemCount(c1, 3704));
									}
									else if (AiUtils.OwnItemCount(c1, 3705)!=0)
									{
										GiveItem1(c1, 3706, 1);
										SoundEffect(c1, "ItemSound.quest_jackpot");
										DeleteItem1(c1, 3705, AiUtils.OwnItemCount(c1, 3705));
									}
									else if (AiUtils.OwnItemCount(c1, 3706)!=0)
									{
										GiveItem1(c1, 3707, 1);
										SoundEffect(c1, "ItemSound.quest_jackpot");
										DeleteItem1(c1, 3706, AiUtils.OwnItemCount(c1, 3706));
									}
								}
							}
							else if (AiUtils.OwnItemCount(c1, 3697)!=0 && (AiUtils.OwnItemCount(c1, 3698) >= 1 || AiUtils.OwnItemCount(c1, 3699) >= 1 || AiUtils.OwnItemCount(c1, 3700) >= 1 || AiUtils.OwnItemCount(c1, 3701) >= 1 || AiUtils.OwnItemCount(c1, 3702) >= 1 || AiUtils.OwnItemCount(c1, 3703) >= 1 || AiUtils.OwnItemCount(c1, 3704) >= 1 || AiUtils.OwnItemCount(c1, 3705) >= 1 || AiUtils.OwnItemCount(c1, 3706) >= 1))
							{
								DeleteItem1(c1, 3698, AiUtils.OwnItemCount(c1, 3698));
								DeleteItem1(c1, 3699, AiUtils.OwnItemCount(c1, 3699));
								DeleteItem1(c1, 3700, AiUtils.OwnItemCount(c1, 3700));
								DeleteItem1(c1, 3701, AiUtils.OwnItemCount(c1, 3701));
								DeleteItem1(c1, 3702, AiUtils.OwnItemCount(c1, 3702));
								DeleteItem1(c1, 3703, AiUtils.OwnItemCount(c1, 3703));
								DeleteItem1(c1, 3704, AiUtils.OwnItemCount(c1, 3704));
								DeleteItem1(c1, 3705, AiUtils.OwnItemCount(c1, 3705));
								DeleteItem1(c1, 3706, AiUtils.OwnItemCount(c1, 3706));
								GiveItem1(c1, 3708, 1);
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			if (code_info.getCode() == 1)
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