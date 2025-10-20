package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class breka_orc_shaman extends wizard_pa_ddmagic2
{
	public breka_orc_shaman(final NpcInstance actor){super(actor);}

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
		NpcInstance npc0 = null;
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
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3125) == 1 && AiUtils.OwnItemCount(target, 3127) == 1 && AiUtils.OwnItemCount(target, 3128) < 30)
		{
			always_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3125) == 1 && AiUtils.OwnItemCount(target, 3127) == 1 && AiUtils.OwnItemCount(target, 3128) < 30)
		{
			always_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3125) == 1 && AiUtils.OwnItemCount(target, 3127) == 1 && AiUtils.OwnItemCount(target, 3128) < 30)
				{
					always_list.SetInfo(3, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(4, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3741)!=0 && AiUtils.OwnItemCount(target, 3783) < 50)
		{
			random1_list.SetInfo(5, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 64) == 1 && AiUtils.GetMemoState(target, 64) == 2 && AiUtils.OwnItemCount(target, 9754) < 20)
		{
			always_list.SetInfo(6, target);
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
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 6 && AiUtils.OwnItemCount(c1, 3299) < 10)
						{
							if (AiUtils.OwnItemCount(c1, 3299) == 9)
							{
								GiveItem1(c1, 3299, 1);
								GiveItem1(c1, 3301, 1);
								SetMemoState(c1, 224, 7);
								SoundEffect(c1, "Itemsound.quest_middle");
								SetFlagJournal(c1, 224, 7);
								ShowQuestMark(c1, 224);
							}
							else
							{
								GiveItem1(c1, 3299, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(230);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3352) == 0 && AiUtils.OwnItemCount(c1, 3349)!=0 && AiUtils.OwnItemCount(c1, 3341) < 30)
						{
							if (AiUtils.OwnItemCount(c1, 3341) >= 28)
							{
								GiveItem1(c1, 3341, 2);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 3341, 2);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
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
						if (AiUtils.HaveMemo(c1, 232) == 1 && AiUtils.OwnItemCount(c1, 3391) == 1 && AiUtils.OwnItemCount(c1, 3392) == 1 && AiUtils.OwnItemCount(c1, 3397) == 1 && AiUtils.OwnItemCount(c1, 3400) == 0 && AiUtils.OwnItemCount(c1, 3399) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3398) < 20 && AiUtils.Rand(100) <= 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3398, 2);
								if (AiUtils.OwnItemCount(c1, 3398) >= 18)
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
				case 3:
				{
					SetCurrentQuestID(216);
					while (!AiUtils.IsNull(target = code_info.Next()))
					{
						if (DistFromMe(target) <= 1500)
						{
							SetCurrentQuestID(216);
							if (AiUtils.OwnItemCount(target, 3128) >= 29)
							{
								GiveItem1(target, 3128, 1);
								DeleteItem1(target, 3127, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 3128, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(214);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 214)!=0 && AiUtils.OwnItemCount(c1, 2676) == 1 && AiUtils.OwnItemCount(c1, 2690) == 1 && AiUtils.OwnItemCount(c1, 2694) == 1 && AiUtils.OwnItemCount(c1, 2696) < 5)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2696, 1);
								if (AiUtils.OwnItemCount(c1, 2695) >= 5 && AiUtils.OwnItemCount(c1, 2696) >= 4 && AiUtils.OwnItemCount(c1, 2697) >= 2)
								{
									SetFlagJournal(c1, 214, 17);
									ShowQuestMark(c1, 214);
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
				case 6:
				{
					SetCurrentQuestID(64);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 9754) >= 19)
						{
							GiveItem1(target, 9754, 1);
							SoundEffect(target, "ItemSound.quest_middle");
							SetFlagJournal(target, 64, 3);
							ShowQuestMark(target, 64);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							GiveItem1(target, 9754, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
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
				case 5:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 93)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 3783, 1);
							if (AiUtils.OwnItemCount(target, 3783) >= 50)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
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
						if (AiUtils.Rand(1000) < 255)
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