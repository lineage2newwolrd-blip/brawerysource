package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class spite_soul_leader extends party_leader_pa_physicalspecial
{
	public spite_soul_leader(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 663) == 1 && AiUtils.GetMemoState(target, 663) >= 1 && AiUtils.GetMemoState(target, 663) <= 4)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 663) == 1 && AiUtils.GetMemoState(target, 663) >= 1 && AiUtils.GetMemoState(target, 663) <= 4)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 663) == 1 && AiUtils.GetMemoState(target, 663) >= 1 && AiUtils.GetMemoState(target, 663) <= 4)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(2, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(386);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 44)
						{
							c1 = GetLastAttacker();
							if (c1.getPlayer() !=null)
							{
								c1 = c1.getPlayer();
							}
							i1 = AiUtils.Party_GetCount(c1);
							i2 = 0;
							if (i1 == 0)
							{
								if (AiUtils.HaveMemo(c1, 386)!=0)
								{
									c2 = c1;
								}
							}
							else
							{
								for (i0 = 0; i0 < i1; i0++)
								{
									c0 = AiUtils.Party_GetCreature(c1, i0);
									if (AiUtils.HaveMemo(c0, 386)!=0)
									{
										i3 = AiUtils.Rand(1000);
										if (i2 <= i3)
										{
											i2 = i3;
											c2 = c0;
										}
									}
								}
							}
							if (!IsNullCreature(c2) && DistFromMe(c2) <= 1500)
							{
								c2.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c2, 6363, 1);
								SoundEffect(c2, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(503);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						c2 = Pledge_GetLeader(c1);
						if (!IsNullCreature(c2))
						{
							if (AiUtils.HaveMemo(c2, 503) == 1 && AiUtils.GetMemoState(c2, 503) == 5000 && DistFromMe(c2) <= 1500)
							{
								i0 = AiUtils.Rand(100);
								c2.quest_last_reward_time = GetCurrentTick();
								if (i0 < 10)
								{
									if (AiUtils.OwnItemCount(c2, 14855) < 10)
									{
										GiveItem1(c2, 14855, 1);
									}
								}
								else if (i0 < 60)
								{
									GiveItem1(c2, 14856, 1);
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
			if (code_info.getCode() == 1)
			{
				SetCurrentQuestID(663);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i0 = AiUtils.Rand(1000);
					if (i0 < 100)
					{
						GiveItem1(target, 8766, 2);
						SoundEffect(target, "ItemSound.quest_itemget");
					}
					else
					{
						GiveItem1(target, 8766, 1);
						SoundEffect(target, "ItemSound.quest_itemget");
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}