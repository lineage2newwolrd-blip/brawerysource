package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class grandis extends warrior_ag_casting_enchant_1of4
{
	public grandis(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
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
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3735)!=0 && AiUtils.OwnItemCount(target, 3777) < 100)
		{
			random1_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3746)!=0 && AiUtils.OwnItemCount(target, 3788) < 1)
		{
			random1_list.SetInfo(4, target);
		}
		target = attacker;
		if ((AiUtils.HaveMemo(target, 66) == 1 && (AiUtils.GetMemoState(target, 66) == 21 || AiUtils.GetMemoState(target, 66) == 22)) && AiUtils.OwnItemCount(target, 9778) < 10)
		{
			always_list.SetInfo(5, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(6, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(6, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(6, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(219);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 219)!=0 && AiUtils.OwnItemCount(c1, 3190)!=0 && AiUtils.OwnItemCount(c1, 3192)!=0 && AiUtils.OwnItemCount(c1, 3198) == 0 && AiUtils.OwnItemCount(c1, 3193)!=0 && AiUtils.OwnItemCount(c1, 3194) < 1)
						{
							if (AiUtils.OwnItemCount(c1, 3194) >= 0)
							{
								GiveItem1(c1, 3194, 1);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 3194, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
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
						if (AiUtils.HaveMemo(c1, 222)!=0 && AiUtils.OwnItemCount(c1, 2778) > 0 && AiUtils.OwnItemCount(c1, 2781) < 3 && AiUtils.GetMemoState(c1, 222) == 2)
						{
							i0 = AiUtils.GetMemoStateEx(c1, 222, 1);
							SetMemoStateEx(c1, 222, 1, i0 + 1);
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2781, 1);
							if (AiUtils.OwnItemCount(c1, 2781) >= 3)
							{
								SoundEffect(c1, "ItemSound.quest_middle");
								if (i0 >= 5)
								{
									SetFlagJournal(c1, 222, 5);
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
				case 2:
				{
					SetCurrentQuestID(214);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 214)!=0 && AiUtils.OwnItemCount(c1, 2677) == 1 && AiUtils.OwnItemCount(c1, 2691) == 1 && AiUtils.OwnItemCount(c1, 2705) == 1 && AiUtils.OwnItemCount(c1, 2708) == 0)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2708, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(66);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 780 && AiUtils.OwnItemCount(target, 9778) < 10)
						{
							if (AiUtils.GetMemoState(target, 66) == 21 && AiUtils.OwnItemCount(target, 9778) < 1)
							{
								SetFlagJournal(target, 66, 12);
								ShowQuestMark(target, 66);
								SoundEffect(target, "ItemSound.quest_middle");
								SetMemoState(target, 66, 22);
								GiveItem1(target, 9778, 1);
							}
							else if (AiUtils.GetMemoState(target, 66) == 22 && AiUtils.OwnItemCount(target, 9778) >= 9)
							{
								SetFlagJournal(target, 66, 13);
								ShowQuestMark(target, 66);
								SoundEffect(target, "ItemSound.quest_middle");
								SetMemoState(target, 66, 23);
								DeleteItem1(target, 9778, AiUtils.OwnItemCount(target, 9778));
								GiveItem1(target, 9779, 1);
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
								GiveItem1(target, 9778, 1);
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
				case 3:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						GiveItem1(target, 3777, 2);
						if (AiUtils.OwnItemCount(target, 3777) >= 100)
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
				case 4:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) < 2)
						{
							CreateOnePrivate(27160, "grandis_chief_gok_magok", 0, 1);
						}
					}
					break;
				}
				case 6:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 39)
						{
							GiveItem1(target, 7586, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}