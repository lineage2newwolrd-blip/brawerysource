package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class buffalo_slave extends warrior_ag_see_skill
{
	public buffalo_slave(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 602) == 1 && AiUtils.GetMemoState(target, 602) == (1 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 602) == 1 && AiUtils.GetMemoState(target, 602) == (1 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 602) == 1 && AiUtils.GetMemoState(target, 602) == (1 * 10 + 1))
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 603) == 1 && AiUtils.GetMemoState(target, 603) == (7 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 603) == 1 && AiUtils.GetMemoState(target, 603) == (7 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 603) == 1 && AiUtils.GetMemoState(target, 603) == (7 * 10 + 1))
				{
					random1_list.SetInfo(1, target);
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
					SetCurrentQuestID(602);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 560)
						{
							if (AiUtils.OwnItemCount(target, 7189) + 1 >= 100)
							{
								GiveItem1(target, 7189, 100 - AiUtils.OwnItemCount(target, 7189));
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 602, 2);
								ShowQuestMark(target, 602);
								SetMemoState(target, 602, 1 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7189, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(603);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 519)
						{
							if (AiUtils.OwnItemCount(target, 7190) + 1 >= 200)
							{
								if (AiUtils.OwnItemCount(target, 7190) < 200)
								{
									GiveItem1(target, 7190, 200 - AiUtils.OwnItemCount(target, 7190));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 603, 8);
									ShowQuestMark(target, 603);
									SetMemoState(target, 603, 7 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7190, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}