package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class dailaon_lad extends warrior_passive_use_power_shot
{
	public dailaon_lad(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
		Creature c2 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
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
					SetCurrentQuestID(354);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c0 = GetLastAttacker();
						if (c0.getPlayer() !=null)
						{
							c0 = c0.getPlayer();
						}
						i1 = AiUtils.Party_GetCount(c0);
						if (i1 != 0)
						{
							for (i0 = 0; i0 < i1; i0++)
							{
								c1 = AiUtils.Party_GetCreature(c0, i0);
								i2 = AiUtils.Rand(1000);
								if (i2 > i3 && AiUtils.HaveMemo(c1, 354)!=0)
								{
									i3 = i2;
									c2 = c1;
								}
							}
						}
						else
						{
							c2 = c0;
						}
						if (AiUtils.HaveMemo(c2, 354)!=0 && !IsNullCreature(c2) && DistFromMe(c2) <= 1500)
						{
							if (GetCurrentTick() - c2.quest_last_reward_time > 1)
							{
								if (AiUtils.Rand(100) < 91)
								{
									c2.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c2, 5863, 1);
									SoundEffect(c2, "ItemSound.quest_itemget");
								}
								if (AiUtils.Rand(10) == 5)
								{
									GiveItem1(c2, 5864, 1);
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(711);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c0 = Pledge_GetLeader(target);
						if (!IsNullCreature(c0))
						{
							if (AiUtils.HaveMemo(c0, 711) == 1 && AiUtils.GetMemoState(c0, 711) / 1000 >= 1 && AiUtils.GetMemoState(c0, 711) / 1000 < 101 && DistFromMe(c0) <= 1500)
							{
								i0 = AiUtils.GetMemoState(c0, 711);
								if (AiUtils.GetMemoState(c0, 711) / 1000 < 100)
								{
									SetMemoState(c0, 711, i0 + 1000);
								}
								else
								{
									SetMemoState(c0, 711, i0 + 1000);
									SetFlagJournal(c0, 711, 6);
									ShowQuestMark(c0, 711);
									SoundEffect(c0, "ItemSound.quest_middle");
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