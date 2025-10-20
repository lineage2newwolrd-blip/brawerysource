package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class weird_bee extends warrior_aggressive_casting_curse
{
	public weird_bee(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
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
					SetCurrentQuestID(330);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 330) == 1 && AiUtils.OwnItemCount(c1, 1420) == 1 && AiUtils.OwnItemCount(c1, 1424) + AiUtils.OwnItemCount(c1, 1425) + AiUtils.OwnItemCount(c1, 1429) + AiUtils.OwnItemCount(c1, 1430) + AiUtils.OwnItemCount(c1, 1433) + AiUtils.OwnItemCount(c1, 1437) + AiUtils.OwnItemCount(c1, 1438) + AiUtils.OwnItemCount(c1, 1441) < 5 && AiUtils.OwnItemCount(c1, 1426) == 1)
						{
							i0 = AiUtils.Rand(100);
							if (i0 < 92)
							{
								if (AiUtils.OwnItemCount(c1, 1427) < 20)
								{
									if (GetCurrentTick() - c1.quest_last_reward_time > 1)
									{
										c1.quest_last_reward_time = GetCurrentTick();
										GiveItem1(c1, 1427, 1);
										if (AiUtils.OwnItemCount(c1, 1427) == 20)
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
							else if (AiUtils.OwnItemCount(c1, 1428) < 10)
							{
								if (GetCurrentTick() - c1.quest_last_reward_time > 1)
								{
									c1.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c1, 1428, 1);
									if (AiUtils.OwnItemCount(c1, 1428) == 10)
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
					break;
				}
				case 1:
				{
					SetCurrentQuestID(231);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 231)!=0 && AiUtils.GetMemoState(c1, 231) == 4 && AiUtils.OwnItemCount(c1, 2876) < 10 && AiUtils.OwnItemCount(c1, 2875) == 1)
						{
							if (AiUtils.OwnItemCount(c1, 2876) == 9)
							{
								GiveItem1(c1, 2876, 1);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 2876, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
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
				SetCurrentQuestID(134);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i0 = AiUtils.Rand(100);
					i1 = AiUtils.Rand(100);
					if (AiUtils.OwnItemCount(target, 10336) >= 1)
					{
						if (i1 < 100)
						{
							CreateOnePrivateEx(27339, "pagan_of_cruma", 0, 0, AiUtils.FloatToInt(actor.getX()) + 20, AiUtils.FloatToInt(actor.getY()) + 20, AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), actor.getObjectId());
							DeleteItem1(target, 10336, 1);
						}
						else
						{
							DeleteItem1(target, 10336, 1);
						}
					}
					else if (i0 < 75)
					{
						GiveItem1(target, 10335, 1);
						SoundEffect(target, "ItemSound.quest_itemget");
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}