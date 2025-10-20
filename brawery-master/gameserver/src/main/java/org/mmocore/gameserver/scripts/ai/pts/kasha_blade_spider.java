package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class kasha_blade_spider extends warrior_passive_casting_curse
{
	public kasha_blade_spider(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
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
		always_list.SetInfo(0, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 3 && AiUtils.OwnItemCount(target, 8545) < 6)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 3 && AiUtils.OwnItemCount(target, 8545) < 6)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 3 && AiUtils.OwnItemCount(target, 8545) < 6)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		always_list.SetInfo(3, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 268)!=0 && AiUtils.GetMemoState(target, 268) == 1)
		{
			random1_list.SetInfo(4, target);
		}
		if (AiUtils.HaveMemo(target, 268)!=0 && AiUtils.GetMemoState(target, 268) == 1)
		{
			random1_list.SetInfo(4, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 268)!=0 && AiUtils.GetMemoState(target, 268) == 1)
				{
					random1_list.SetInfo(4, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(415);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 415) == 1 && AiUtils.OwnItemCount(c_quest0, 1595) == 1 && i_quest0 == 1)
						{
							if (AiUtils.OwnItemCount(c_quest0, 1601) == 4)
							{
								DeleteItem1(c_quest0, 1601, AiUtils.OwnItemCount(c_quest0, 1601));
								DeleteItem1(c_quest0, 1595, AiUtils.OwnItemCount(c_quest0, 1595));
								GiveItem1(c_quest0, 1598, 1);
								SetFlagJournal(c_quest0, 415, 5);
								ShowQuestMark(c_quest0, 415);
								SoundEffect(c_quest0, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c_quest0, 1601, 1);
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(416);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 416) == 1 && AiUtils.OwnItemCount(c_quest0, 1616) == 1 && AiUtils.OwnItemCount(c_quest0, 1618) < 1 && i_quest0 == 1)
						{
							if (AiUtils.OwnItemCount(c_quest0, 1617) >= 1 && AiUtils.OwnItemCount(c_quest0, 1618) < 1 && AiUtils.OwnItemCount(c_quest0, 1619) >= 1)
							{
								GiveItem1(c_quest0, 1618, 1);
								SetFlagJournal(c_quest0, 416, 2);
								ShowQuestMark(c_quest0, 416);
								SoundEffect(c_quest0, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c_quest0, 1618, 1);
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(419);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						c1.quest_last_reward_time = 0;
						if (AiUtils.HaveMemo(c1, 419) == 1 && AiUtils.OwnItemCount(c1, 3421) == 1)
						{
							if (AiUtils.OwnItemCount(c1, 3426) < 50 && AiUtils.Rand(100) < 100)
							{
								GiveItem1(c1, 3426, 1);
								if (AiUtils.OwnItemCount(c1, 3426) >= 50)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_getitem");
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
				case 4:
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
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(415);
		if (AiUtils.HaveMemo(attacker, 415)!=0)
		{
			switch (i_quest0)
			{
				case 0:
				{
					if (attacker.getWeaponType() != 5 && attacker.getWeaponType() != 9)
					{
						i_quest0 = 2;
					}
					else
					{
						i_quest0 = 1;
					}
					c_quest0 = attacker;
					break;
				}
				case 1:
				{
					if (c_quest0 != attacker || (attacker.getWeaponType() != 5 && attacker.getWeaponType() != 9))
					{
						i_quest0 = 2;
					}
					break;
				}
				case 2:
				{
					break;
				}
			}
		}
		SetCurrentQuestID(416);
		if (AiUtils.HaveMemo(attacker, 416)!=0)
		{
			switch (i_quest0)
			{
				case 0:
				{
					i_quest0 = 1;
					if (attacker.getPlayer() !=null)
					{
						c_quest0 = attacker.getPlayer();
					}
					else
					{
						c_quest0 = attacker;
					}
					break;
				}
				case 1:
				{
					if (attacker.getPlayer() !=null)
					{
						if (c_quest0 != attacker.getPlayer())
						{
							i_quest0 = 2;
						}
					}
					else if (c_quest0 != attacker)
					{
						i_quest0 = 2;
					}
					break;
				}
				case 2:
				{
					break;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}