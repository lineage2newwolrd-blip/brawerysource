package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class guardian_of_secrets_c extends warrior_passive
{
	public guardian_of_secrets_c(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 72) == 1 && target.getClassId() == 21 && AiUtils.GetMemoState(target, 72) == 6 && AiUtils.OwnItemCount(target, 7301) == 0)
		{
			always_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 75) == 1 && target.getClassId() == 46 && AiUtils.GetMemoState(target, 75) == 6 && AiUtils.OwnItemCount(target, 7304) == 0)
		{
			always_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 78) == 1 && target.getClassId() == 52 && AiUtils.GetMemoState(target, 78) == 6 && AiUtils.OwnItemCount(target, 7307) == 0)
		{
			always_list.SetInfo(2, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 81) == 1 && target.getClassId() == 36 && AiUtils.GetMemoState(target, 81) == 6 && AiUtils.OwnItemCount(target, 7310) == 0)
		{
			always_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 84) == 1 && target.getClassId() == 37 && AiUtils.GetMemoState(target, 84) == 6 && AiUtils.OwnItemCount(target, 7313) == 0)
		{
			always_list.SetInfo(4, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 87) == 1 && target.getClassId() == 30 && AiUtils.GetMemoState(target, 87) == 6 && AiUtils.OwnItemCount(target, 7316) == 0)
		{
			always_list.SetInfo(5, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 90) == 1 && target.getClassId() == 40 && AiUtils.GetMemoState(target, 90) == 6 && AiUtils.OwnItemCount(target, 7319) == 0)
		{
			always_list.SetInfo(6, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 93) == 1 && target.getClassId() == 41 && AiUtils.GetMemoState(target, 93) == 6 && AiUtils.OwnItemCount(target, 7322) == 0)
		{
			always_list.SetInfo(7, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 96) == 1 && target.getClassId() == 34 && AiUtils.GetMemoState(target, 96) == 6 && AiUtils.OwnItemCount(target, 7325) == 0)
		{
			always_list.SetInfo(8, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 99) == 1 && target.getClassId() == 55 && AiUtils.GetMemoState(target, 99) == 6 && AiUtils.OwnItemCount(target, 7328) == 0)
		{
			always_list.SetInfo(9, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 69) == 1 && target.getClassId() == 130 && AiUtils.GetMemoState(target, 69) == 6 && AiUtils.OwnItemCount(target, 9727) == 0)
		{
			always_list.SetInfo(10, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(72);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 72, 1) < 9)
						{
							SetMemoStateEx(target, 72, 1, AiUtils.GetMemoStateEx(target, 72, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7301, 1);
							SetFlagJournal(target, 72, 7);
							ShowQuestMark(target, 72);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(75);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 75, 1) < 9)
						{
							SetMemoStateEx(target, 75, 1, AiUtils.GetMemoStateEx(target, 75, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7304, 1);
							SetFlagJournal(target, 75, 7);
							ShowQuestMark(target, 75);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(78);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 78, 1) < 9)
						{
							SetMemoStateEx(target, 78, 1, AiUtils.GetMemoStateEx(target, 78, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7307, 1);
							SetFlagJournal(target, 78, 7);
							ShowQuestMark(target, 78);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(81);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 81, 1) < 9)
						{
							SetMemoStateEx(target, 81, 1, AiUtils.GetMemoStateEx(target, 81, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7310, 1);
							SetFlagJournal(target, 81, 7);
							ShowQuestMark(target, 81);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(84);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 84, 1) < 9)
						{
							SetMemoStateEx(target, 84, 1, AiUtils.GetMemoStateEx(target, 84, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7313, 1);
							SetFlagJournal(target, 84, 7);
							ShowQuestMark(target, 84);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(87);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 87, 1) < 9)
						{
							SetMemoStateEx(target, 87, 1, AiUtils.GetMemoStateEx(target, 87, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7316, 1);
							SetFlagJournal(target, 87, 7);
							ShowQuestMark(target, 87);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 6:
				{
					SetCurrentQuestID(90);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 90, 1) < 9)
						{
							SetMemoStateEx(target, 90, 1, AiUtils.GetMemoStateEx(target, 90, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7319, 1);
							SetFlagJournal(target, 90, 7);
							ShowQuestMark(target, 90);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 7:
				{
					SetCurrentQuestID(93);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 93, 1) < 9)
						{
							SetMemoStateEx(target, 93, 1, AiUtils.GetMemoStateEx(target, 93, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7322, 1);
							SetFlagJournal(target, 93, 7);
							ShowQuestMark(target, 93);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 8:
				{
					SetCurrentQuestID(96);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 96, 1) < 9)
						{
							SetMemoStateEx(target, 96, 1, AiUtils.GetMemoStateEx(target, 96, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7325, 1);
							SetFlagJournal(target, 96, 7);
							ShowQuestMark(target, 96);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 9:
				{
					SetCurrentQuestID(99);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 99, 1) < 9)
						{
							SetMemoStateEx(target, 99, 1, AiUtils.GetMemoStateEx(target, 99, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7328, 1);
							SetFlagJournal(target, 99, 7);
							ShowQuestMark(target, 99);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 10:
				{
					SetCurrentQuestID(69);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 69, 1) < 9)
						{
							SetMemoStateEx(target, 69, 1, AiUtils.GetMemoStateEx(target, 69, 1) + 1);
						}
						else
						{
							GiveItem1(target, 9727, 1);
							SetFlagJournal(target, 69, 7);
							ShowQuestMark(target, 69);
							SoundEffect(target, "ItemSound.quest_middle");
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