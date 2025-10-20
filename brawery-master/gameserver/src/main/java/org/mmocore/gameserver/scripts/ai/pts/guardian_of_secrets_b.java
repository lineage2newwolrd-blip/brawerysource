package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class guardian_of_secrets_b extends warrior_passive
{
	public guardian_of_secrets_b(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 71) == 1 && target.getClassId() == 20 && AiUtils.GetMemoState(target, 71) == 6 && AiUtils.OwnItemCount(target, 7300) == 0)
		{
			always_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 74) == 1 && target.getClassId() == 3 && AiUtils.GetMemoState(target, 74) == 6 && AiUtils.OwnItemCount(target, 7303) == 0)
		{
			always_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 77) == 1 && target.getClassId() == 51 && AiUtils.GetMemoState(target, 77) == 6 && AiUtils.OwnItemCount(target, 7306) == 0)
		{
			always_list.SetInfo(2, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 80) == 1 && target.getClassId() == 23 && AiUtils.GetMemoState(target, 80) == 6 && AiUtils.OwnItemCount(target, 7309) == 0)
		{
			always_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 83) == 1 && target.getClassId() == 24 && AiUtils.GetMemoState(target, 83) == 6 && AiUtils.OwnItemCount(target, 7312) == 0)
		{
			always_list.SetInfo(4, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 86) == 1 && target.getClassId() == 17 && AiUtils.GetMemoState(target, 86) == 6 && AiUtils.OwnItemCount(target, 7315) == 0)
		{
			always_list.SetInfo(5, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 89) == 1 && target.getClassId() == 27 && AiUtils.GetMemoState(target, 89) == 6 && AiUtils.OwnItemCount(target, 7318) == 0)
		{
			always_list.SetInfo(6, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 92) == 1 && target.getClassId() == 28 && AiUtils.GetMemoState(target, 92) == 6 && AiUtils.OwnItemCount(target, 7321) == 0)
		{
			always_list.SetInfo(7, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 94) == 1 && target.getClassId() == 13 && AiUtils.GetMemoState(target, 94) == 6 && AiUtils.OwnItemCount(target, 7323) == 0)
		{
			always_list.SetInfo(8, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 97) == 1 && target.getClassId() == 33 && AiUtils.GetMemoState(target, 97) == 6 && AiUtils.OwnItemCount(target, 7326) == 0)
		{
			always_list.SetInfo(9, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 100) == 1 && target.getClassId() == 57 && AiUtils.GetMemoState(target, 100) == 6 && AiUtils.OwnItemCount(target, 7329) == 0)
		{
			always_list.SetInfo(10, target);
		}
		target = attacker;
		if (((AiUtils.HaveMemo(target, 68) == 1 && (target.getClassId() == 128 || target.getClassId() == 129)) && AiUtils.GetMemoState(target, 68) == 6) && AiUtils.OwnItemCount(target, 9726) == 0)
		{
			always_list.SetInfo(11, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(71);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 71, 1) < 9)
						{
							SetMemoStateEx(target, 71, 1, AiUtils.GetMemoStateEx(target, 71, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7300, 1);
							SetFlagJournal(target, 71, 7);
							ShowQuestMark(target, 71);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(74);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 74, 1) < 9)
						{
							SetMemoStateEx(target, 74, 1, AiUtils.GetMemoStateEx(target, 74, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7303, 1);
							SetFlagJournal(target, 74, 7);
							ShowQuestMark(target, 74);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(77);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 77, 1) < 9)
						{
							SetMemoStateEx(target, 77, 1, AiUtils.GetMemoStateEx(target, 77, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7306, 1);
							SetFlagJournal(target, 77, 7);
							ShowQuestMark(target, 77);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(80);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 80, 1) < 9)
						{
							SetMemoStateEx(target, 80, 1, AiUtils.GetMemoStateEx(target, 80, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7309, 1);
							SetFlagJournal(target, 80, 7);
							ShowQuestMark(target, 80);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(83);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 83, 1) < 9)
						{
							SetMemoStateEx(target, 83, 1, AiUtils.GetMemoStateEx(target, 83, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7312, 1);
							SetFlagJournal(target, 83, 7);
							ShowQuestMark(target, 83);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(86);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 86, 1) < 9)
						{
							SetMemoStateEx(target, 86, 1, AiUtils.GetMemoStateEx(target, 86, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7315, 1);
							SetFlagJournal(target, 86, 7);
							ShowQuestMark(target, 86);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 6:
				{
					SetCurrentQuestID(89);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 89, 1) < 9)
						{
							SetMemoStateEx(target, 89, 1, AiUtils.GetMemoStateEx(target, 89, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7318, 1);
							SetFlagJournal(target, 89, 7);
							ShowQuestMark(target, 89);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 7:
				{
					SetCurrentQuestID(92);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 92, 1) < 9)
						{
							SetMemoStateEx(target, 92, 1, AiUtils.GetMemoStateEx(target, 92, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7321, 1);
							SetFlagJournal(target, 92, 7);
							ShowQuestMark(target, 92);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 8:
				{
					SetCurrentQuestID(94);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 94, 1) < 9)
						{
							SetMemoStateEx(target, 94, 1, AiUtils.GetMemoStateEx(target, 94, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7323, 1);
							SetFlagJournal(target, 94, 7);
							ShowQuestMark(target, 94);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 9:
				{
					SetCurrentQuestID(97);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 97, 1) < 9)
						{
							SetMemoStateEx(target, 97, 1, AiUtils.GetMemoStateEx(target, 97, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7326, 1);
							SetFlagJournal(target, 97, 7);
							ShowQuestMark(target, 97);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 10:
				{
					SetCurrentQuestID(100);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 100, 1) < 9)
						{
							SetMemoStateEx(target, 100, 1, AiUtils.GetMemoStateEx(target, 100, 1) + 1);
						}
						else
						{
							GiveItem1(target, 7329, 1);
							SetFlagJournal(target, 100, 7);
							ShowQuestMark(target, 100);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 11:
				{
					SetCurrentQuestID(68);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.GetMemoStateEx(target, 68, 1) < 9)
						{
							SetMemoStateEx(target, 68, 1, AiUtils.GetMemoStateEx(target, 68, 1) + 1);
						}
						else
						{
							GiveItem1(target, 9726, 1);
							SetFlagJournal(target, 68, 7);
							ShowQuestMark(target, 68);
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