package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class enchanted_stone_golem extends warrior_pa_slow_type1
{
	public enchanted_stone_golem(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
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
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3729)!=0 && AiUtils.OwnItemCount(target, 3771) < 50)
		{
			random1_list.SetInfo(2, target);
		}
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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(229);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 229) == 1 && AiUtils.OwnItemCount(c1, 3309) == 1 && AiUtils.OwnItemCount(c1, 3310) == 1)
						{
							if (AiUtils.Rand(100) <= 100 && AiUtils.OwnItemCount(c1, 3313) < 20)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3313, 1);
								if (AiUtils.OwnItemCount(c1, 3313) >= 20)
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
				case 1:
				{
					SetCurrentQuestID(228);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 228)!=0 && AiUtils.OwnItemCount(c1, 2847) == 1 && AiUtils.OwnItemCount(c1, 2863) == 1 && AiUtils.OwnItemCount(c1, 2854) < 10)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2854, 1);
							if (AiUtils.OwnItemCount(c1, 2854) >= 10)
							{
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
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
				case 2:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						GiveItem1(target, 3771, 1);
						if (AiUtils.OwnItemCount(target, 3771) >= 50)
						{
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 62)
						{
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
						if (AiUtils.Rand(100) < 65)
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