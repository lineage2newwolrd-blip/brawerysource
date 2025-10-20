package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class messenger_angel_r extends warrior_ag_casting_3skill_magical3_r
{
	public messenger_angel_r(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
		Creature c2 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
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
		if (AiUtils.HaveMemo(target, 350) == 1)
		{
			random1_list.SetInfo(1, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(372);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(100) < 29)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						i1 = AiUtils.Party_GetCount(c1);
						i2 = 0;
						if (i1 == 0 && AiUtils.HaveMemo(c1, 372) == 1)
						{
							c2 = c1;
						}
						for (i0 = 0; i0 < i1; i0++)
						{
							c0 = AiUtils.Party_GetCreature(c1, i0);
							if (AiUtils.HaveMemo(c0, 372) == 1)
							{
								i3 = AiUtils.Rand(1000);
								if (i2 < i3)
								{
									i2 = i3;
									c2 = c0;
								}
							}
						}
						if (!IsNullCreature(c2) && DistFromMe(c2) <= 1500)
						{
							if (GetCurrentTick() - c2.quest_last_reward_time > 1)
							{
								c2.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c2, 5969, 1);
								SoundEffect(c2, "ItemSound.quest_itemget");
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