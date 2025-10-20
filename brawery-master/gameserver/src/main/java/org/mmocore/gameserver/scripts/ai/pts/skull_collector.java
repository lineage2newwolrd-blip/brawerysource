package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class skull_collector extends wizard_ag_corpse_necro_summon
{
	public skull_collector(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 24) == 1 && AiUtils.GetMemoState(target, 24) == 11 && AiUtils.OwnItemCount(target, 7151) == 0)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 633) == 1 && (AiUtils.GetMemoState(target, 633) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 633) == (1 * 10 + 2)))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 633) == 1 && (AiUtils.GetMemoState(target, 633) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 633) == (1 * 10 + 2)))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 633) == 1 && (AiUtils.GetMemoState(target, 633) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 633) == (1 * 10 + 2)))
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
					SetCurrentQuestID(24);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 10)
						{
							GiveItem1(target, 7151, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
							SetFlagJournal(target, 24, 10);
							ShowQuestMark(target, 24);
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(633);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 414)
						{
							if (AiUtils.OwnItemCount(target, 7544) >= 199)
							{
								if (AiUtils.OwnItemCount(target, 7544) < 200)
								{
									GiveItem1(target, 7544, 1);
									SoundEffect(target, "ItemSound.quest_middle");
								}
								SetFlagJournal(target, 633, 2);
								ShowQuestMark(target, 633);
								SetMemoState(target, 633, 1 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7544, 1);
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