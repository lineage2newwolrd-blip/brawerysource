package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class guardian_of_secrets_a extends warrior_passive
{
	public guardian_of_secrets_a(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 70) == 1 && target.getClassId() == 5 && AiUtils.GetMemoState(target, 70) == 6 && AiUtils.OwnItemCount(target, 7299) == 0)
		{
			always_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 73) == 1 && target.getClassId() == 2 && AiUtils.GetMemoState(target, 73) == 6 && AiUtils.OwnItemCount(target, 7302) == 0)
		{
			always_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 76) == 1 && target.getClassId() == 48 && AiUtils.GetMemoState(target, 76) == 6 && AiUtils.OwnItemCount(target, 7305) == 0)
		{
			always_list.SetInfo(2, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 79) == 1 && target.getClassId() == 8 && AiUtils.GetMemoState(target, 79) == 6 && AiUtils.OwnItemCount(target, 7308) == 0)
		{
			always_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 82) == 1 && target.getClassId() == 9 && AiUtils.GetMemoState(target, 82) == 6 && AiUtils.OwnItemCount(target, 7311) == 0)
		{
			always_list.SetInfo(4, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 85) == 1 && target.getClassId() == 16 && AiUtils.GetMemoState(target, 85) == 6 && AiUtils.OwnItemCount(target, 7314) == 0)
		{
			always_list.SetInfo(5, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 88) == 1 && target.getClassId() == 12 && AiUtils.GetMemoState(target, 88) == 6 && AiUtils.OwnItemCount(target, 7317) == 0)
		{
			always_list.SetInfo(6, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 91) == 1 && target.getClassId() == 14 && AiUtils.GetMemoState(target, 91) == 6 && AiUtils.OwnItemCount(target, 7320) == 0)
		{
			always_list.SetInfo(7, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 95) == 1 && target.getClassId() == 6 && AiUtils.GetMemoState(target, 95) == 6 && AiUtils.OwnItemCount(target, 7324) == 0)
		{
			always_list.SetInfo(8, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 98) == 1 && target.getClassId() == 43 && AiUtils.GetMemoState(target, 98) == 6 && AiUtils.OwnItemCount(target, 7327) == 0)
		{
			always_list.SetInfo(9, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 67) == 1 && target.getClassId() == 127 && AiUtils.GetMemoState(target, 67) == 6 && AiUtils.OwnItemCount(target, 9725) == 0)
		{
			always_list.SetInfo(10, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(70);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 70, 1) < 9)
						{
							SetMemoStateEx(target, 70, 1, AiUtils.GetMemoStateEx(target, 70, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7299, 1);
							SetFlagJournal(target, 70, 7);
							ShowQuestMark(target, 70);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(73);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 73, 1) < 9)
						{
							SetMemoStateEx(target, 73, 1, AiUtils.GetMemoStateEx(target, 73, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7302, 1);
							SetFlagJournal(target, 73, 7);
							ShowQuestMark(target, 73);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(76);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 76, 1) < 9)
						{
							SetMemoStateEx(target, 76, 1, AiUtils.GetMemoStateEx(target, 76, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7305, 1);
							SetFlagJournal(target, 76, 7);
							ShowQuestMark(target, 76);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(79);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 79, 1) < 9)
						{
							SetMemoStateEx(target, 79, 1, AiUtils.GetMemoStateEx(target, 79, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7308, 1);
							SetFlagJournal(target, 79, 7);
							ShowQuestMark(target, 79);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(82);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 82, 1) < 9)
						{
							SetMemoStateEx(target, 82, 1, AiUtils.GetMemoStateEx(target, 82, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7311, 1);
							SetFlagJournal(target, 82, 7);
							ShowQuestMark(target, 82);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(85);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 85, 1) < 9)
						{
							SetMemoStateEx(target, 85, 1, AiUtils.GetMemoStateEx(target, 85, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7314, 1);
							SetFlagJournal(target, 85, 7);
							ShowQuestMark(target, 85);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 6:
				{
					SetCurrentQuestID(88);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 88, 1) < 9)
						{
							SetMemoStateEx(target, 88, 1, AiUtils.GetMemoStateEx(target, 88, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7317, 1);
							SetFlagJournal(target, 88, 7);
							ShowQuestMark(target, 88);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 7:
				{
					SetCurrentQuestID(91);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 91, 1) < 9)
						{
							SetMemoStateEx(target, 91, 1, AiUtils.GetMemoStateEx(target, 91, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7320, 1);
							SetFlagJournal(target, 91, 7);
							ShowQuestMark(target, 91);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 8:
				{
					SetCurrentQuestID(95);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 95, 1) < 9)
						{
							SetMemoStateEx(target, 95, 1, AiUtils.GetMemoStateEx(target, 95, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7324, 1);
							SetFlagJournal(target, 95, 7);
							ShowQuestMark(target, 95);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 9:
				{
					SetCurrentQuestID(98);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 98, 1) < 9)
						{
							SetMemoStateEx(target, 98, 1, AiUtils.GetMemoStateEx(target, 98, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7327, 1);
							SetFlagJournal(target, 98, 7);
							ShowQuestMark(target, 98);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 10:
				{
					SetCurrentQuestID(67);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 67, 1) < 9)
						{
							SetMemoStateEx(target, 67, 1, AiUtils.GetMemoStateEx(target, 67, 1) + 1);
						}
						else
						{
							GiveItem1(target, 9725, 1);
							SetFlagJournal(target, 67, 7);
							ShowQuestMark(target, 67);
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