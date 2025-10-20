package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class cursed_observer extends wizard_ag_ddmagic2_curse
{
	public cursed_observer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i4 = 0;
		int i5 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 660)!=0 && AiUtils.GetMemoState(target, 660) == 2)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 660)!=0 && AiUtils.GetMemoState(target, 660) == 2)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 660)!=0 && AiUtils.GetMemoState(target, 660) == 2)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 66) == 1 && AiUtils.GetMemoState(target, 66) == 3 && AiUtils.OwnItemCount(target, 9773) < 30)
		{
			always_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 135) == 1 && AiUtils.GetMemoState(target, 135) == 4)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 135) == 1 && AiUtils.GetMemoState(target, 135) == 4)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 135) == 1 && AiUtils.GetMemoState(target, 135) == 4)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 1)
			{
				SetCurrentQuestID(66);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (AiUtils.OwnItemCount(target, 9773) < 30)
					{
						if (AiUtils.OwnItemCount(target, 9773) >= 29)
						{
							SetFlagJournal(target, 66, 4);
							ShowQuestMark(target, 66);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						GiveItem1(target, 9773, 1);
						if (i4 < 40 && AiUtils.OwnItemCount(target, 9773) < 29)
						{
							GiveItem1(target, 9773, 1);
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(660);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 63)
						{
							GiveItem1(target, 8074, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(135);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 423)
						{
							if (AiUtils.OwnItemCount(target, 10328) < 9)
							{
								GiveItem1(target, 10328, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(target, 10328) == 9)
							{
								GiveItem1(target, 10328, 1);
								if (AiUtils.OwnItemCount(target, 10328) == 9 && AiUtils.OwnItemCount(target, 10329) == 10 && AiUtils.OwnItemCount(target, 10330) == 10)
								{
									SetMemoState(target, 135, 5);
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 135, 4);
									ShowQuestMark(target, 135);
								}
								else
								{
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
							else if (AiUtils.OwnItemCount(target, 10329) < 9)
							{
								GiveItem1(target, 10329, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(target, 10329) == 9)
							{
								GiveItem1(target, 10329, 1);
								if (AiUtils.OwnItemCount(target, 10328) == 10 && AiUtils.OwnItemCount(target, 10329) == 9 && AiUtils.OwnItemCount(target, 10330) == 10)
								{
									SetMemoState(target, 135, 5);
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 135, 4);
									ShowQuestMark(target, 135);
								}
								else
								{
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
							else if (AiUtils.OwnItemCount(target, 10330) < 9)
							{
								GiveItem1(target, 10330, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(target, 10330) == 9)
							{
								GiveItem1(target, 10330, 1);
								if (AiUtils.OwnItemCount(target, 10328) == 10 && AiUtils.OwnItemCount(target, 10329) == 10 && AiUtils.OwnItemCount(target, 10330) == 9)
								{
									SetMemoState(target, 135, 5);
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 135, 4);
									ShowQuestMark(target, 135);
								}
								else
								{
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 265)
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