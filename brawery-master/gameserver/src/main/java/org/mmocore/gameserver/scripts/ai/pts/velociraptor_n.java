package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class velociraptor_n extends warrior_ag_physicalspecial_velociraptor
{
	public velociraptor_n(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 641) == 1)
		{
			always_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 641) == 1)
		{
			always_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 641) == 1)
				{
					always_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 111) == 1 && AiUtils.GetMemoState(target, 111) == 4)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 111) == 1 && AiUtils.GetMemoState(target, 111) == 4)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 111) == 1 && AiUtils.GetMemoState(target, 111) == 4)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 642) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 642) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 642) == 1)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(641);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 1000 && AiUtils.OwnItemCount(target, 8782) <= 29)
					{
						if (AiUtils.OwnItemCount(target, 8782) < 29)
						{
							GiveItem1(target, 8782, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else if (AiUtils.OwnItemCount(target, 8782) == 29)
						{
							GiveItem1(target, 8782, 1);
							SoundEffect(target, "ItemSound.quest_middle");
							SetFlagJournal(target, 641, 2);
							ShowQuestMark(target, 641);
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
				case 1:
				{
					SetCurrentQuestID(111);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 250 && AiUtils.OwnItemCount(target, 8768) <= 49)
						{
							if (AiUtils.OwnItemCount(target, 8768) < 49)
							{
								GiveItem1(target, 8768, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(target, 8768) >= 49)
							{
								GiveItem1(target, 8768, 1);
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 111, 5);
								ShowQuestMark(target, 111);
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(642);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 309)
						{
							GiveItem1(target, 8774, 1);
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