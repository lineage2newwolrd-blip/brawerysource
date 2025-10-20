package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class king_araneid extends warrior_aggressive_physicalspecial
{
	public king_araneid(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
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
		if (AiUtils.HaveMemo(target, 298) == 1 && AiUtils.GetMemoState(target, 298) == (2 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 298) == 1 && AiUtils.GetMemoState(target, 298) == (2 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 298) == 1 && AiUtils.GetMemoState(target, 298) == (2 * 10 + 1))
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 118) == 1 && AiUtils.GetMemoState(target, 118) == 4 && AiUtils.OwnItemCount(target, 8063) < 8)
		{
			always_list.SetInfo(1, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 1)
			{
				SetCurrentQuestID(118);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(10) < 7)
					{
						if (HasAcademyMaster(target) == 1)
						{
							c0 = GetAcademyMaster(target);
							if (!IsNullCreature(c0) && DistFromMe(c0) <= 1500)
							{
								GiveItem1(target, 8063, 1);
								if (AiUtils.OwnItemCount(target, 8063) >= 7)
								{
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 118, 8);
									ShowQuestMark(target, 118);
								}
								else
								{
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(298);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 540)
					{
						if (AiUtils.OwnItemCount(target, 7184) + 1 >= 50)
						{
							if (AiUtils.OwnItemCount(target, 7184) < 50)
							{
								GiveItem1(target, 7184, 50 - AiUtils.OwnItemCount(target, 7184));
								SoundEffect(target, "ItemSound.quest_middle");
							}
							if (AiUtils.OwnItemCount(target, 7183) >= 50)
							{
								SetFlagJournal(target, 298, 3);
								ShowQuestMark(target, 298);
								SetMemoState(target, 298, 2 * 10 + 2);
							}
						}
						else
						{
							GiveItem1(target, 7184, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}