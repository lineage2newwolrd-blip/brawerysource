package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ghoul extends warrior_pa_slow_type2
{
	public ghoul(final NpcInstance actor){super(actor);}

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
		NpcInstance npc0 = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3129) == 1 && AiUtils.OwnItemCount(target, 3130) < 70)
		{
			always_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3129) == 1 && AiUtils.OwnItemCount(target, 3130) < 70)
		{
			always_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3129) == 1 && AiUtils.OwnItemCount(target, 3130) < 70)
				{
					always_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		always_list.SetInfo(3, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 66) == 1 && AiUtils.GetMemoState(target, 66) == 8 && AiUtils.OwnItemCount(target, 9775) < 30)
		{
			always_list.SetInfo(4, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 137) == 1 && AiUtils.GetMemoState(target, 137) == 5 && AiUtils.OwnItemCount(target, 10340) < 30)
		{
			random1_list.SetInfo(5, target);
		}
		if (AiUtils.HaveMemo(target, 137) == 1 && AiUtils.GetMemoState(target, 137) == 5 && AiUtils.OwnItemCount(target, 10340) < 30)
		{
			random1_list.SetInfo(5, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 137) == 1 && AiUtils.GetMemoState(target, 137) == 5 && AiUtils.OwnItemCount(target, 10340) < 30)
				{
					random1_list.SetInfo(5, target);
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
						if (AiUtils.HaveMemo(c1, 212)!=0 && AiUtils.GetMemoState(c1, 212) == 5 && AiUtils.OwnItemCount(c1, 2638) < 10 && AiUtils.OwnItemCount(c1, 2639) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 2638) == 9)
							{
								if (AiUtils.Rand(2) <= 1)
								{
									GiveItem1(c1, 2639, 1);
									DeleteItem1(c1, 2638, AiUtils.OwnItemCount(c1, 2638));
									SoundEffect(c1, "ItemSound.quest_middle");
									SetFlagJournal(c1, 212, 6);
									ShowQuestMark(c1, 212);
								}
							}
							else if (AiUtils.Rand(2) <= 1)
							{
								GiveItem1(c1, 2638, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(216);
					while (!AiUtils.IsNull(target = code_info.Next()))
					{
						if (DistFromMe(target) <= 1500)
						{
							SetCurrentQuestID(216);
							GiveItem1(target, 3130, 5);
							if (AiUtils.OwnItemCount(target, 3130) >= 65)
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
						if (AiUtils.HaveMemo(c1, 214)!=0 && AiUtils.OwnItemCount(c1, 2705) == 1 && AiUtils.OwnItemCount(c1, 2711) == 1 && AiUtils.OwnItemCount(c1, 2715) == 1 && AiUtils.OwnItemCount(c1, 2716) < 10)
						{
							GiveItem1(c1, 2716, 1);
							if (AiUtils.OwnItemCount(c1, 2716) >= 10)
							{
								SoundEffect(c1, "ItemSound.quest_middle");
								SetFlagJournal(c1, 214, 29);
								ShowQuestMark(c1, 214);
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(333);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 333) == 1 && AiUtils.OwnItemCount(c1, 3671) >= 1)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							if (AiUtils.Rand(2) == 0)
							{
								GiveItem1(c1, 3848, 1);
							}
							if (AiUtils.Rand(100) < 15)
							{
								GiveItem1(c1, 3440, 1);
							}
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(66);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (AiUtils.OwnItemCount(target, 9775) < 30)
						{
							if (AiUtils.OwnItemCount(target, 9775) >= 29)
							{
								SetFlagJournal(target, 66, 8);
								ShowQuestMark(target, 66);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							GiveItem1(target, 9775, 1);
							if (i4 < 20 && AiUtils.OwnItemCount(target, 9775) < 29)
							{
								GiveItem1(target, 9775, 1);
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
			if (code_info.getCode() == 5)
			{
				SetCurrentQuestID(137);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i0 = AiUtils.Rand(100);
					if (i0 < 92)
					{
						if (AiUtils.OwnItemCount(target, 10340) >= 29)
						{
							GiveItem1(target, 10340, 1);
							SetFlagJournal(target, 137, 3);
							ShowQuestMark(target, 137);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							GiveItem1(target, 10340, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}