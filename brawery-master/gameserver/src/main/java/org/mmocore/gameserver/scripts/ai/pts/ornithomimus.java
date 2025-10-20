package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ornithomimus extends party_private_primeval
{
	public ornithomimus(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 125) == 1 && AiUtils.GetMemoState(target, 125) == 3)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 125) == 1 && AiUtils.GetMemoState(target, 125) == 3)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 125) == 1 && AiUtils.GetMemoState(target, 125) == 3)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 111) == 1 && AiUtils.GetMemoState(target, 111) == 11)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 111) == 1 && AiUtils.GetMemoState(target, 111) == 11)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 111) == 1 && AiUtils.GetMemoState(target, 111) == 11)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 643) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 643) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 643) == 1)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			code_info.getCode();
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(125);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 330 && AiUtils.OwnItemCount(target, 8779) <= 1)
						{
							if (AiUtils.OwnItemCount(target, 8779) < 1)
							{
								GiveItem1(target, 8779, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(target, 8779) >= 1)
							{
								GiveItem1(target, 8779, 1);
								if (AiUtils.OwnItemCount(target, 8780) >= 2)
								{
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 125, 4);
									ShowQuestMark(target, 125);
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(111);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 330 && AiUtils.OwnItemCount(target, 8770) <= 9)
						{
							if (AiUtils.OwnItemCount(target, 8770) < 9)
							{
								GiveItem1(target, 8770, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(target, 8770) == 9)
							{
								GiveItem1(target, 8770, 1);
								if (AiUtils.OwnItemCount(target, 8772) >= 10 && AiUtils.OwnItemCount(target, 8771) >= 10)
								{
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 111, 11);
									ShowQuestMark(target, 111);
								}
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(643);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 116)
						{
							GiveItem1(target, 8776, 2);
						}
						else
						{
							GiveItem1(target, 8776, 1);
						}
						SoundEffect(target, "ItemSound.quest_itemget");
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}