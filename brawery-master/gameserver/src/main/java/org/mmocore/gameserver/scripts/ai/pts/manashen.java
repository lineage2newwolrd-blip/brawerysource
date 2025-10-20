package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class manashen extends warrior_passive_physicalspecial
{
	public manashen(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i4 = 0;
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
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3695)!=0 && AiUtils.OwnItemCount(target, 3715) < 20)
		{
			random1_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 343)!=0 && IsInCategory(Category.wizard_group, target.getClassId()))
		{
			random1_list.SetInfo(4, target);
		}
		target = attacker;
		if ((AiUtils.HaveMemo(target, 66) == 1 && (AiUtils.GetMemoState(target, 66) == 25 || AiUtils.GetMemoState(target, 66) == 26)) && AiUtils.OwnItemCount(target, 9780) < 10)
		{
			always_list.SetInfo(5, target);
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
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 10 && AiUtils.OwnItemCount(c1, 3305) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3302)!=0 && AiUtils.OwnItemCount(c1, 3304)!=0 && AiUtils.OwnItemCount(c1, 3303)!=0)
							{
								GiveItem1(c1, 3305, 1);
								SetMemoState(c1, 224, 11);
								SoundEffect(c1, "Itemsound.quest_middle");
								SetFlagJournal(c1, 224, 11);
								ShowQuestMark(c1, 224);
							}
							else
							{
								GiveItem1(c1, 3305, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
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
						if (AiUtils.HaveMemo(c1, 220)!=0 && AiUtils.OwnItemCount(c1, 3204)!=0 && AiUtils.OwnItemCount(c1, 3205) < 10)
						{
							if (AiUtils.Rand(20) < 20)
							{
								if (AiUtils.OwnItemCount(c1, 3205) == 9)
								{
									GiveItem1(c1, 3205, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3206) >= 10 && AiUtils.OwnItemCount(c1, 3207) >= 10)
									{
										SetFlagJournal(c1, 220, 2);
										ShowQuestMark(c1, 220);
									}
								}
								else
								{
									GiveItem1(c1, 3205, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
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
						if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3352) == 0 && AiUtils.OwnItemCount(c1, 3348)!=0 && AiUtils.OwnItemCount(c1, 3340) < 30)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3340) >= 28)
								{
									GiveItem1(c1, 3340, 2);
									SoundEffect(c1, "Itemsound.quest_middle");
								}
								else
								{
									GiveItem1(c1, 3340, 2);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(66);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 840 && AiUtils.OwnItemCount(target, 9780) < 10)
						{
							if (AiUtils.GetMemoState(target, 66) == 25 && AiUtils.OwnItemCount(target, 9780) < 1)
							{
								SetFlagJournal(target, 66, 15);
								ShowQuestMark(target, 66);
								SoundEffect(target, "ItemSound.quest_middle");
								SetMemoState(target, 66, 26);
							}
							else if (AiUtils.GetMemoState(target, 66) == 26 && AiUtils.OwnItemCount(target, 9780) >= 9)
							{
								SetFlagJournal(target, 66, 16);
								ShowQuestMark(target, 66);
								SoundEffect(target, "ItemSound.quest_middle");
								SetMemoState(target, 66, 27);
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							GiveItem1(target, 9780, 1);
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
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 60)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 3715, 1);
							if (AiUtils.OwnItemCount(target, 3715) >= 20)
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
				case 4:
				{
					SetCurrentQuestID(343);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 63)
						{
							GiveItem1(target, 4364, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						if (AiUtils.GetMemoStateEx(target, 343, 1) > 1)
						{
							if (AiUtils.Rand(100) <= 12)
							{
								SetMemoStateEx(target, 343, 1, AiUtils.GetMemoStateEx(target, 343, 1) - 1);
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