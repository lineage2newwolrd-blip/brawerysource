package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class enchanted_monstereye extends wizard_ag_ddmagic2_curse
{
	public enchanted_monstereye(final NpcInstance actor){super(actor);}

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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
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
						if (AiUtils.HaveMemo(c1, 232) == 1 && AiUtils.OwnItemCount(c1, 3391) == 1 && AiUtils.OwnItemCount(c1, 3396) == 1 && AiUtils.OwnItemCount(c1, 3411) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3410) < 20)
							{
								GiveItem1(c1, 3410, 1);
								if (AiUtils.OwnItemCount(c1, 3410) >= 20)
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
						if (AiUtils.HaveMemo(c1, 228)!=0 && AiUtils.OwnItemCount(c1, 2847) == 1 && AiUtils.OwnItemCount(c1, 2863) == 1 && AiUtils.OwnItemCount(c1, 2853) < 10)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2853, 1);
							if (AiUtils.OwnItemCount(c1, 2853) >= 10)
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
				case 2:
				{
					SetCurrentQuestID(222);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 222)!=0 && AiUtils.OwnItemCount(c1, 2766) > 0 && AiUtils.OwnItemCount(c1, 2775) < 10 && AiUtils.GetMemoState(c1, 222) == 1)
						{
							i0 = AiUtils.GetMemoStateEx(c1, 222, 1);
							SetMemoStateEx(c1, 222, 1, i0 + 1);
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2775, 1);
							if (AiUtils.OwnItemCount(c1, 2775) >= 10)
							{
								SoundEffect(c1, "ItemSound.quest_middle");
								if (i0 >= 9)
								{
									SetFlagJournal(c1, 222, 3);
									SetMemoStateEx(c1, 222, 1, 0);
								}
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(343);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 343)!=0 && IsInCategory(Category.wizard_group, c1.getClassId()))
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								if (AiUtils.Rand(100) < 63)
								{
									GiveItem1(c1, 4364, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							if (AiUtils.GetMemoStateEx(c1, 343, 1) > 1)
							{
								if (AiUtils.Rand(100) <= 12)
								{
									SetMemoStateEx(c1, 343, 1, AiUtils.GetMemoStateEx(c1, 343, 1) - 1);
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
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

}