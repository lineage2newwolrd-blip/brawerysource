package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class enchanted_iron_golem extends warrior_pa_slow_type1
{
	public enchanted_iron_golem(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 36) == 1 && AiUtils.GetMemoState(target, 36) == (1 * 10 + 1) && GetOneTimeQuestFlag(target, 36) == 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 36) == 1 && AiUtils.GetMemoState(target, 36) == (1 * 10 + 1) && GetOneTimeQuestFlag(target, 36) == 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 36) == 1 && AiUtils.GetMemoState(target, 36) == (1 * 10 + 1) && GetOneTimeQuestFlag(target, 36) == 0)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
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
						if (AiUtils.HaveMemo(c1, 228)!=0 && AiUtils.OwnItemCount(c1, 2847) == 1 && AiUtils.OwnItemCount(c1, 2863) == 1 && AiUtils.OwnItemCount(c1, 2855) < 10)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2855, 1);
							if (AiUtils.OwnItemCount(c1, 2855) >= 10)
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
				case 1:
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
								if (AiUtils.Rand(100) < 68)
								{
									GiveItem1(c1, 4364, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							if (AiUtils.GetMemoStateEx(c1, 343, 1) > 1)
							{
								if (AiUtils.Rand(100) <= 13)
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
			if (code_info.getCode() == 2)
			{
				SetCurrentQuestID(36);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 500)
					{
						if (AiUtils.OwnItemCount(target, 7163) + 1 >= 5)
						{
							if (AiUtils.OwnItemCount(target, 7163) < 5)
							{
								GiveItem1(target, 7163, 5 - AiUtils.OwnItemCount(target, 7163));
								SoundEffect(target, "ItemSound.quest_middle");
							}
							SetFlagJournal(target, 36, 2);
							ShowQuestMark(target, 36);
							SetMemoState(target, 36, 1 * 10 + 2);
						}
						else
						{
							GiveItem1(target, 7163, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}