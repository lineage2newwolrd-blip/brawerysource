package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class guardian_basilisk extends warrior_passive_casting_enchant_self
{
	public guardian_basilisk(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
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
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3695)!=0 && AiUtils.OwnItemCount(target, 3709) < 40)
		{
			random1_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3732)!=0 && AiUtils.OwnItemCount(target, 3774) < 100)
		{
			random1_list.SetInfo(4, target);
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
					SetCurrentQuestID(217);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 217)!=0 && AiUtils.GetMemoState(c1, 217) == 6 && AiUtils.OwnItemCount(c1, 2749) < 10 && AiUtils.OwnItemCount(c1, 2755)!=0 && AiUtils.OwnItemCount(c1, 2752) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 2749) >= 4)
							{
								GiveItem1(c1, 2752, 1);
								DeleteItem1(c1, 2749, AiUtils.OwnItemCount(c1, 2749));
								SoundEffect(c1, "Itemsound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2754) >= 1 && AiUtils.OwnItemCount(c1, 2753) >= 1)
								{
									SetFlagJournal(c1, 217, 7);
									ShowQuestMark(c1, 217);
								}
							}
							else
							{
								GiveItem1(c1, 2749, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(220);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 220)!=0 && AiUtils.OwnItemCount(c1, 3204)!=0 && AiUtils.OwnItemCount(c1, 3207) < 10)
						{
							if (AiUtils.Rand(20) < 20)
							{
								if (AiUtils.OwnItemCount(c1, 3207) == 9)
								{
									GiveItem1(c1, 3207, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3205) >= 10 && AiUtils.OwnItemCount(c1, 3206) >= 10)
									{
										SetFlagJournal(c1, 220, 2);
										ShowQuestMark(c1, 220);
									}
								}
								else
								{
									GiveItem1(c1, 3207, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(218);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 218)!=0 && AiUtils.OwnItemCount(c1, 3144) == 1 && AiUtils.OwnItemCount(c1, 3149) == 1 && AiUtils.OwnItemCount(c1, 3161) < 10)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3161, 2);
								if (AiUtils.OwnItemCount(c1, 3161) >= 8)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3163) >= 20 && AiUtils.OwnItemCount(c1, 3162) >= 20)
									{
										SetFlagJournal(c1, 218, 5);
										ShowQuestMark(c1, 218);
									}
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
				case 3:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 90)
						{
							GiveItem1(target, 3709, 1);
							if (AiUtils.OwnItemCount(target, 3709) >= 40)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
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
						if (AiUtils.Rand(100) < 60)
						{
							GiveItem1(target, 3774, 2);
							if (AiUtils.OwnItemCount(target, 3774) >= 100)
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
							GiveItem1(target, 3774, 1);
							if (AiUtils.OwnItemCount(target, 3774) >= 100)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
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
						if (i0 < 46)
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
						if (AiUtils.Rand(100) < 30)
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