package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ant_patrol extends warrior_passive_physicalspecial
{
	public ant_patrol(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 370)!=0)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 370)!=0)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 370)!=0)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 142) == 1 && AiUtils.GetMemoState(target, 142) == 7 && GetOneTimeQuestFlag(target, 142) == 0)
		{
			random1_list.SetInfo(4, target);
		}
		if (AiUtils.HaveMemo(target, 142) == 1 && AiUtils.GetMemoState(target, 142) == 7 && GetOneTimeQuestFlag(target, 142) == 0)
		{
			random1_list.SetInfo(4, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 142) == 1 && AiUtils.GetMemoState(target, 142) == 7 && GetOneTimeQuestFlag(target, 142) == 0)
				{
					random1_list.SetInfo(4, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(5, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(5, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(5, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(217);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 217)!=0 && AiUtils.GetMemoState(c1, 217) == 6 && AiUtils.OwnItemCount(c1, 2750) < 10 && AiUtils.OwnItemCount(c1, 2755)!=0 && AiUtils.OwnItemCount(c1, 2753) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 2750) >= 4)
							{
								GiveItem1(c1, 2753, 1);
								DeleteItem1(c1, 2750, AiUtils.OwnItemCount(c1, 2750));
								SoundEffect(c1, "Itemsound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2752) >= 1 && AiUtils.OwnItemCount(c1, 2754) >= 1)
								{
									SetFlagJournal(c1, 217, 7);
									ShowQuestMark(c1, 217);
								}
							}
							else
							{
								GiveItem1(c1, 2750, 1);
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
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 3 && AiUtils.OwnItemCount(c1, 3298) < 10)
						{
							if (AiUtils.Rand(2) <= 1)
							{
								if (AiUtils.OwnItemCount(c1, 3298) == 9)
								{
									GiveItem1(c1, 3298, 1);
									SetMemoState(c1, 224, 4);
									SoundEffect(c1, "Itemsound.quest_middle");
									SetFlagJournal(c1, 224, 4);
									ShowQuestMark(c1, 224);
								}
								else
								{
									GiveItem1(c1, 3298, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(218);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 218)!=0 && AiUtils.OwnItemCount(c1, 3144) == 1 && AiUtils.OwnItemCount(c1, 3149) == 1 && AiUtils.OwnItemCount(c1, 3162) < 20)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3162, 2);
								if (AiUtils.OwnItemCount(c1, 3162) >= 18)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3161) >= 10 && AiUtils.OwnItemCount(c1, 3163) >= 20)
									{
										SetFlagJournal(c1, 218, 5);
										ShowQuestMark(c1, 218);
									}
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
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 3:
				{
					SetCurrentQuestID(370);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 101)
						{
							GiveItem1(target, 5916, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(142);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(1000);
						if (i0 < 421)
						{
							if (AiUtils.OwnItemCount(target, 10352) >= 29)
							{
								DeleteItem1(target, 10352, AiUtils.OwnItemCount(target, 10352));
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 142, 5);
								ShowQuestMark(target, 142);
								SetMemoState(target, 142, 8);
							}
							else
							{
								GiveItem1(target, 10352, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 5:
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