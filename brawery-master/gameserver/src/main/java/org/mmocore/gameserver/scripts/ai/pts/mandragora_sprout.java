package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class mandragora_sprout extends warrior_pa_casting_3skill_magical2
{
	public mandragora_sprout(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
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
					SetCurrentQuestID(330);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 330) == 1 && AiUtils.OwnItemCount(c1, 1420) == 1 && AiUtils.OwnItemCount(c1, 1424) + AiUtils.OwnItemCount(c1, 1425) + AiUtils.OwnItemCount(c1, 1429) + AiUtils.OwnItemCount(c1, 1430) + AiUtils.OwnItemCount(c1, 1433) + AiUtils.OwnItemCount(c1, 1437) + AiUtils.OwnItemCount(c1, 1438) + AiUtils.OwnItemCount(c1, 1441) < 5 && AiUtils.OwnItemCount(c1, 1421) == 1 && AiUtils.OwnItemCount(c1, 1424) == 0 && AiUtils.OwnItemCount(c1, 1425) == 0)
						{
							i0 = AiUtils.Rand(100);
							if (i0 < 70)
							{
								if (AiUtils.OwnItemCount(c1, 1422) < 40)
								{
									if (GetCurrentTick() - c1.quest_last_reward_time > 1)
									{
										c1.quest_last_reward_time = GetCurrentTick();
										GiveItem1(c1, 1422, 1);
										if (AiUtils.OwnItemCount(c1, 1422) == 40)
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
							else if (i0 < 77)
							{
								if (AiUtils.OwnItemCount(c1, 1423) < 40)
								{
									if (GetCurrentTick() - c1.quest_last_reward_time > 1)
									{
										c1.quest_last_reward_time = GetCurrentTick();
										GiveItem1(c1, 1423, 1);
										if (AiUtils.OwnItemCount(c1, 1423) == 40)
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
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(221);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 221)!=0 && AiUtils.OwnItemCount(c1, 3239) == 1 && AiUtils.OwnItemCount(c1, 3264) == 1 && AiUtils.OwnItemCount(c1, 3243) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3265) < 20 && AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3265, 1);
								if (AiUtils.OwnItemCount(c1, 3265) >= 20)
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
				case 2:
				{
					SetCurrentQuestID(216);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 216) == 1 && AiUtils.OwnItemCount(c1, 3120) == 1 && AiUtils.OwnItemCount(c1, 3121) == 0)
						{
							if (AiUtils.Rand(100) <= 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3121, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								SetFlagJournal(c1, 216, 4);
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(709);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c0 = Pledge_GetLeader(target);
						if (!IsNullCreature(c0))
						{
							if (AiUtils.HaveMemo(c0, 709) == 1 && AiUtils.GetMemoState(c0, 709) % 10 == 5)
							{
								GiveItem1(target, 13849, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
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