package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class crimson_bind extends warrior_passive_casting_enchant_self
{
	public crimson_bind(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3733)!=0 && AiUtils.OwnItemCount(target, 3775) < 50)
		{
			random1_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 51) == 1 && GetOneTimeQuestFlag(target, 51) == 0 && AiUtils.GetMemoState(target, 51) == (1 * 10 + 1))
		{
			random1_list.SetInfo(4, target);
		}
		if (AiUtils.HaveMemo(target, 51) == 1 && GetOneTimeQuestFlag(target, 51) == 0 && AiUtils.GetMemoState(target, 51) == (1 * 10 + 1))
		{
			random1_list.SetInfo(4, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 51) == 1 && GetOneTimeQuestFlag(target, 51) == 0 && AiUtils.GetMemoState(target, 51) == (1 * 10 + 1))
				{
					random1_list.SetInfo(4, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 138) == 1 && AiUtils.GetMemoState(target, 138) == 4 && AiUtils.OwnItemCount(target, 10342) < 10)
		{
			random1_list.SetInfo(5, target);
		}
		if (AiUtils.HaveMemo(target, 138) == 1 && AiUtils.GetMemoState(target, 138) == 4 && AiUtils.OwnItemCount(target, 10342) < 10)
		{
			random1_list.SetInfo(5, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 138) == 1 && AiUtils.GetMemoState(target, 138) == 4 && AiUtils.OwnItemCount(target, 10342) < 10)
				{
					random1_list.SetInfo(5, target);
				}
			}
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
					SetCurrentQuestID(230);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3352) == 0 && AiUtils.OwnItemCount(c1, 3349)!=0 && AiUtils.OwnItemCount(c1, 3342) < 30)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3342) >= 24)
								{
									GiveItem1(c1, 3342, 6);
									SoundEffect(c1, "Itemsound.quest_middle");
								}
								else
								{
									GiveItem1(c1, 3342, 6);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
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
						if (AiUtils.HaveMemo(c1, 222)!=0 && AiUtils.OwnItemCount(c1, 2765) > 0 && AiUtils.OwnItemCount(c1, 2773) < 10 && AiUtils.GetMemoState(c1, 222) == 1)
						{
							i0 = AiUtils.GetMemoStateEx(c1, 222, 1);
							SetMemoStateEx(c1, 222, 1, i0 + 1);
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2773, 1);
							if (AiUtils.OwnItemCount(c1, 2773) >= 10)
							{
								SoundEffect(c1, "ItemSound.quest_middle");
								if (i0 >= 9)
								{
									SetFlagJournal(c1, 222, 3);
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
						if (AiUtils.HaveMemo(c1, 214)!=0 && AiUtils.OwnItemCount(c1, 2705) == 1 && AiUtils.OwnItemCount(c1, 2711) == 1 && AiUtils.OwnItemCount(c1, 2715) == 1 && AiUtils.OwnItemCount(c1, 2718) < 5)
						{
							GiveItem1(c1, 2718, 1);
							if (AiUtils.OwnItemCount(c1, 2718) >= 5)
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
						target.quest_last_reward_time = GetCurrentTick();
						GiveItem1(target, 3775, 1);
						if (AiUtils.OwnItemCount(target, 3775) >= 50)
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
					SetCurrentQuestID(51);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if ((i4 < 1000 && 1000 != 0))
						{
							if (AiUtils.OwnItemCount(target, 7622) + 1 >= 100)
							{
								if (AiUtils.OwnItemCount(target, 7622) < 100)
								{
									GiveItem1(target, 7622, 100 - AiUtils.OwnItemCount(target, 7622));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 51, 2);
									ShowQuestMark(target, 51);
								}
								SetMemoState(target, 51, 1 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7622, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(138);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 100)
						{
							if (AiUtils.OwnItemCount(target, 10342) >= 9)
							{
								GiveItem1(target, 10342, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 10342, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
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
						if (AiUtils.Rand(100) < 65)
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