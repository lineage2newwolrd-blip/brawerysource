package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class taik_orc_seeker extends warrior_passive_physicalspecial
{
	public taik_orc_seeker(final NpcInstance actor){super(actor);}

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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(360);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 360) == 1 && AiUtils.Rand(100) < 50)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 5872, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
						if (AiUtils.HaveMemo(c1, 360) == 1 && AiUtils.Rand(100) < 10)
						{
							if (AiUtils.OwnItemCount(c1, 5871) < 4)
							{
								if (GetCurrentTick() - c1.quest_last_reward_time > 1)
								{
									c1.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c1, 5871, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							else if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 5870, 1);
								DeleteItem1(c1, 5871, AiUtils.OwnItemCount(c1, 5871));
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(713);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c0 = Pledge_GetLeader(target);
						if (!IsNullCreature(c0))
						{
							if (AiUtils.HaveMemo(c0, 713) == 1 && AiUtils.GetMemoState(c0, 713) % 100 == 2)
							{
								i0 = AiUtils.GetMemoState(c0, 713);
								i1 = AiUtils.GetMemoStateEx(c0, 713, 1);
								if (i1 >= 99)
								{
									SetMemoState(c0, 713, i0 + 10);
									if (AiUtils.GetMemoState(c0, 713) / 100 < 5)
									{
										SetFlagJournal(c0, 713, 3);
									}
									else if (AiUtils.GetMemoState(c0, 713) / 100 >= 5)
									{
										SetFlagJournal(c0, 713, 5);
									}
									ShowQuestMark(c0, 713);
									SoundEffect(c0, "ItemSound.quest_middle");
								}
								SetMemoStateEx(c0, 713, 1, i1 + 1);
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