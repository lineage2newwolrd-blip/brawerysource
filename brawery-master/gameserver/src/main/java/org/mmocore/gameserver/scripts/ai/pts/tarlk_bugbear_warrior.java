package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class tarlk_bugbear_warrior extends warrior_aggressive_casting_enchant_clan
{
	public tarlk_bugbear_warrior(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3696)!=0 && AiUtils.OwnItemCount(target, 3722) + AiUtils.OwnItemCount(target, 3723) + AiUtils.OwnItemCount(target, 3724) + AiUtils.OwnItemCount(target, 3725) + AiUtils.OwnItemCount(target, 3726) < 5)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3759)!=0 && AiUtils.OwnItemCount(target, 3801) < 30)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.OwnItemCount(target, 4333)!=0 && AiUtils.HaveMemo(target, 504)!=0 && AiUtils.OwnItemCount(target, 4332) < 30)
		{
			random1_list.SetInfo(2, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			code_info.getCode();
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) < 2)
						{
							if (AiUtils.OwnItemCount(target, 3722) == 0)
							{
								CreateOnePrivate(27144, "talk_raider_athu", 0, 1);
							}
							else if (AiUtils.OwnItemCount(target, 3723) == 0)
							{
								CreateOnePrivate(27145, "talk_raider_lanka", 0, 1);
							}
							else if (AiUtils.OwnItemCount(target, 3724) == 0)
							{
								CreateOnePrivate(27146, "talk_raider_triska", 0, 1);
							}
							else if (AiUtils.OwnItemCount(target, 3725) == 0)
							{
								CreateOnePrivate(27147, "talk_raider_motura", 0, 1);
							}
							else if (AiUtils.OwnItemCount(target, 3726) == 0)
							{
								CreateOnePrivate(27148, "talk_raider_kalath", 0, 1);
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 73)
						{
							GiveItem1(target, 3801, 1);
							if (AiUtils.OwnItemCount(target, 3801) >= 30)
							{
								SoundEffect(target, "ItemSound.quest_midddle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(504);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) < 7)
						{
							target.quest_last_reward_time = GetCurrentTick();
							if (AiUtils.OwnItemCount(target, 4332) == 29)
							{
								GiveItem1(target, 4332, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 4332, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
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