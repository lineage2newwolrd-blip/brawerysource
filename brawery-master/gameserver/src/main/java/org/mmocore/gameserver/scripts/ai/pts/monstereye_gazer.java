package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class monstereye_gazer extends wizard_pa_ddmagic2_curse
{
	public monstereye_gazer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 330) == 1 && AiUtils.OwnItemCount(target, 1420) == 1 && AiUtils.OwnItemCount(target, 1424) + AiUtils.OwnItemCount(target, 1425) + AiUtils.OwnItemCount(target, 1429) + AiUtils.OwnItemCount(target, 1430) + AiUtils.OwnItemCount(target, 1433) + AiUtils.OwnItemCount(target, 1437) + AiUtils.OwnItemCount(target, 1438) + AiUtils.OwnItemCount(target, 1441) < 5 && AiUtils.OwnItemCount(target, 1439) == 1 && AiUtils.OwnItemCount(target, 1440) < 30)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 42) == 1 && AiUtils.GetMemoState(target, 42) == (2 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 42) == 1 && AiUtils.GetMemoState(target, 42) == (2 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 42) == 1 && AiUtils.GetMemoState(target, 42) == (2 * 10 + 1))
				{
					random1_list.SetInfo(1, target);
				}
			}
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
					SetCurrentQuestID(330);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(10);
						if (i0 < 7)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 1440, 1);
							if (AiUtils.OwnItemCount(target, 1440) == 30)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
						else
						{
							target.quest_last_reward_time = GetCurrentTick();
							if (AiUtils.OwnItemCount(target, 1440) == 29)
							{
								GiveItem1(target, 1440, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 1440, 2);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(42);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if ((i4 < 1000 && 1000 != 0))
						{
							if (AiUtils.OwnItemCount(target, 7548) + 1 >= 30)
							{
								if (AiUtils.OwnItemCount(target, 7548) < 30)
								{
									GiveItem1(target, 7548, 30 - AiUtils.OwnItemCount(target, 7548));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 42, 3);
									ShowQuestMark(target, 42);
								}
								SetMemoState(target, 42, 2 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7548, 1);
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