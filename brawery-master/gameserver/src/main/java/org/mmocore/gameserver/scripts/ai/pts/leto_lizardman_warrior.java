package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class leto_lizardman_warrior extends warrior_passive
{
	public leto_lizardman_warrior(final NpcInstance actor){super(actor);}

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
		final NpcInstance actor = getActor();

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
		target = attacker;
		always_list.SetInfo(4, target);
		target = attacker;
		always_list.SetInfo(5, target);
		target = attacker;
		always_list.SetInfo(6, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 420) == 1 && AiUtils.OwnItemCount(target, 3822) == 1 && AiUtils.OwnItemCount(target, 3823) < 20)
		{
			random1_list.SetInfo(7, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 300) == 1 && AiUtils.GetMemoState(target, 300) == (1 * 10 + 1))
		{
			random1_list.SetInfo(8, target);
		}
		if (AiUtils.HaveMemo(target, 300) == 1 && AiUtils.GetMemoState(target, 300) == (1 * 10 + 1))
		{
			random1_list.SetInfo(8, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 300) == 1 && AiUtils.GetMemoState(target, 300) == (1 * 10 + 1))
				{
					random1_list.SetInfo(8, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 186) == 1 && AiUtils.GetMemoState(target, 186) == 2 && GetOneTimeQuestFlag(target, 186) == 0)
		{
			random1_list.SetInfo(9, target);
		}
		if (AiUtils.HaveMemo(target, 186) == 1 && AiUtils.GetMemoState(target, 186) == 2 && GetOneTimeQuestFlag(target, 186) == 0)
		{
			random1_list.SetInfo(9, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 186) == 1 && AiUtils.GetMemoState(target, 186) == 2 && GetOneTimeQuestFlag(target, 186) == 0)
				{
					random1_list.SetInfo(9, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(212);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 212)!=0 && AiUtils.GetMemoState(c1, 212) == 9 && AiUtils.OwnItemCount(c1, 2641) < 20)
						{
							if (AiUtils.OwnItemCount(c1, 2641) == 19)
							{
								GiveItem1(c1, 2641, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								SetFlagJournal(c1, 212, 12);
								ShowQuestMark(c1, 212);
							}
							else
							{
								GiveItem1(c1, 2641, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(224);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 13 && AiUtils.OwnItemCount(c1, 3306) < 140)
						{
							if ((AiUtils.OwnItemCount(c1, 3306) - 10) * 5 > AiUtils.Rand(100))
							{
								CreateOnePrivateEx(27090, "serpent_demon_kadesh", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
								DeleteItem1(c1, 3306, AiUtils.OwnItemCount(c1, 3306));
								SoundEffect(c1, "Itemsound.quest_before_battle");
							}
							else
							{
								GiveItem1(c1, 3306, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(223);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 223)!=0 && AiUtils.OwnItemCount(c1, 3285)!=0 && AiUtils.OwnItemCount(c1, 3292) < 10)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3292) >= 9)
								{
									GiveItem1(c1, 3292, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									SetFlagJournal(c1, 223, 13);
									ShowQuestMark(c1, 223);
								}
								else
								{
									GiveItem1(c1, 3292, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(230);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3352) == 0 && AiUtils.OwnItemCount(c1, 3347)!=0 && AiUtils.OwnItemCount(c1, 3337) < 30)
						{
							if (AiUtils.OwnItemCount(c1, 3337) >= 29)
							{
								GiveItem1(c1, 3337, 1);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 3337, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(229);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 229) == 1 && AiUtils.OwnItemCount(c1, 3309) == 1 && AiUtils.OwnItemCount(c1, 3310) == 1)
						{
							if (AiUtils.Rand(100) <= 100 && AiUtils.OwnItemCount(c1, 3312) < 20)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3312, 1);
								if (AiUtils.OwnItemCount(c1, 3312) >= 20)
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
				case 5:
				{
					SetCurrentQuestID(213);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 213)!=0 && AiUtils.OwnItemCount(c1, 2667) == 1 && AiUtils.OwnItemCount(c1, 2671) == 0)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2671, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2668) >= 1 && AiUtils.OwnItemCount(c1, 2669) >= 1 && AiUtils.OwnItemCount(c1, 2670) >= 1)
								{
									SetFlagJournal(c1, 213, 16);
									ShowQuestMark(c1, 213);
								}
							}
						}
					}
					break;
				}
				case 6:
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
						if (AiUtils.HaveMemo(c1, 214)!=0 && AiUtils.OwnItemCount(c1, 2675) == 1 && AiUtils.OwnItemCount(c1, 2689) == 1 && AiUtils.OwnItemCount(c1, 2686) == 1 && AiUtils.OwnItemCount(c1, 2687) < 5)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2687, 1);
								if (AiUtils.OwnItemCount(c1, 2687) >= 4)
								{
									SetFlagJournal(c1, 214, 12);
									ShowQuestMark(c1, 214);
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
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 7:
				{
					SetCurrentQuestID(420);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 50)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 3823, 1);
							if (AiUtils.OwnItemCount(target, 3823) >= 19)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							Say(AiUtils.MakeFString(42049, "", "", "", "", ""));
						}
					}
					break;
				}
				case 8:
				{
					SetCurrentQuestID(300);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 790)
						{
							if (AiUtils.OwnItemCount(target, 7139) + 1 >= 60)
							{
								if (AiUtils.OwnItemCount(target, 7139) < 60)
								{
									GiveItem1(target, 7139, 60 - AiUtils.OwnItemCount(target, 7139));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 300, 2);
									ShowQuestMark(target, 300);
									SetMemoState(target, 300, 1 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7139, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 9:
				{
					SetCurrentQuestID(186);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 88 && AiUtils.OwnItemCount(target, 10367) < 1)
						{
							GiveItem1(target, 10367, 1);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}