package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class devil_bat extends warrior_ag_corpse_vampire
{
	public devil_bat(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 632) == 1 && (AiUtils.GetMemoState(target, 632) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 632) == (1 * 10 + 2)))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 632) == 1 && (AiUtils.GetMemoState(target, 632) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 632) == (1 * 10 + 2)))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 632) == 1 && (AiUtils.GetMemoState(target, 632) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 632) == (1 * 10 + 2)))
				{
					random1_list.SetInfo(0, target);
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
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(632);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if ((i4 < 452 && 452 != 0))
					{
						if (AiUtils.OwnItemCount(target, 7542) >= 199)
						{
							if (AiUtils.OwnItemCount(target, 7542) < 200)
							{
								GiveItem1(target, 7542, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							SetFlagJournal(target, 632, 2);
							ShowQuestMark(target, 632);
							SetMemoState(target, 632, 1 * 10 + 2);
						}
						else
						{
							GiveItem1(target, 7542, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}