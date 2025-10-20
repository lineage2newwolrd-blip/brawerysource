package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class road_scavenger extends warrior_passive
{
	public road_scavenger(final NpcInstance actor){super(actor);}

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
		always_list.SetInfo(1, target);
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		always_list.SetInfo(3, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 420) == 1 && AiUtils.OwnItemCount(target, 3826) == 1 && AiUtils.OwnItemCount(target, 3827) < 20)
		{
			random1_list.SetInfo(4, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 64) == 1 && AiUtils.GetMemoState(target, 64) == 4 && AiUtils.OwnItemCount(target, 9755) == 0)
		{
			always_list.SetInfo(5, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 138) == 1 && AiUtils.GetMemoState(target, 138) == 4 && AiUtils.OwnItemCount(target, 10342) < 10)
		{
			random1_list.SetInfo(6, target);
		}
		if (AiUtils.HaveMemo(target, 138) == 1 && AiUtils.GetMemoState(target, 138) == 4 && AiUtils.OwnItemCount(target, 10342) < 10)
		{
			random1_list.SetInfo(6, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 138) == 1 && AiUtils.GetMemoState(target, 138) == 4 && AiUtils.OwnItemCount(target, 10342) < 10)
				{
					random1_list.SetInfo(6, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(7, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(7, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(7, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(224);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 10 && AiUtils.OwnItemCount(c1, 3302) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3304)!=0 && AiUtils.OwnItemCount(c1, 3305)!=0 && AiUtils.OwnItemCount(c1, 3303)!=0)
							{
								GiveItem1(c1, 3302, 1);
								SetMemoState(c1, 224, 11);
								SoundEffect(c1, "Itemsound.quest_middle");
								SetFlagJournal(c1, 224, 11);
								ShowQuestMark(c1, 224);
							}
							else
							{
								GiveItem1(c1, 3302, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(224);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 10 && AiUtils.OwnItemCount(c1, 3302) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3304)!=0 && AiUtils.OwnItemCount(c1, 3305)!=0 && AiUtils.OwnItemCount(c1, 3303)!=0)
							{
								GiveItem1(c1, 3302, 1);
								SetMemoState(c1, 224, 11);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 3302, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(223);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 223)!=0 && AiUtils.OwnItemCount(c1, 3284)!=0 && AiUtils.OwnItemCount(c1, 3291) < 10)
						{
							if (AiUtils.OwnItemCount(c1, 3291) >= 9)
							{
								GiveItem1(c1, 3291, 1);
								SoundEffect(c1, "Itemsound.quest_middle");
								SetFlagJournal(c1, 223, 11);
								ShowQuestMark(c1, 223);
							}
							else
							{
								GiveItem1(c1, 3291, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(225);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 225) == 1 && AiUtils.OwnItemCount(c1, 2800) == 1 && AiUtils.OwnItemCount(c1, 2801) < 4 && AiUtils.OwnItemCount(c1, 2803) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 2801) < 3)
							{
								if (AiUtils.Rand(100) < 100)
								{
									c1.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c1, 2801, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
							}
							else if (AiUtils.Rand(100) < 100)
							{
								DeleteItem1(c1, 2801, AiUtils.OwnItemCount(c1, 2801));
								GiveItem1(c1, 2803, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2804) >= 1)
								{
									SetFlagJournal(c1, 225, 15);
									ShowQuestMark(c1, 225);
								}
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(64);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 20)
						{
							GiveItem1(target, 9755, 1);
							SetFlagJournal(target, 64, 6);
							ShowQuestMark(target, 64);
							SoundEffect(target, "ItemSound.quest_middle");
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
				case 4:
				{
					SetCurrentQuestID(420);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 50)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 3827, 1);
							if (AiUtils.OwnItemCount(target, 3827) >= 19)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							Say(AiUtils.MakeFString(42046, "", "", "", "", ""));
						}
					}
					break;
				}
				case 6:
				{
					SetCurrentQuestID(138);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 46)
						{
							if (AiUtils.OwnItemCount(target, 10342) >= 9)
							{
								GiveItem1(target, 10342, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 10342, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 7:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 30)
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

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(223);
		switch (i_quest0)
		{
			case 0:
			{
				c_quest0 = attacker;
				if (AiUtils.HaveMemo(c_quest0, 223)!=0 && AiUtils.OwnItemCount(c_quest0, 3284) > 0 && AiUtils.OwnItemCount(c_quest0, 3291) < 100)
				{
					if (AiUtils.Rand(2) == 1)
					{
						if (AiUtils.Rand(10) < 7)
						{
							CreateOnePrivate(27089, "road_collector", 0, 1);
						}
						else
						{
							CreateOnePrivate(27089, "road_collector", 0, 1);
							CreateOnePrivate(27089, "road_collector", 0, 1);
						}
					}
				}
				i_quest0 = 1;
				break;
			}
			case 1:
			{
				i_quest0 = 2;
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