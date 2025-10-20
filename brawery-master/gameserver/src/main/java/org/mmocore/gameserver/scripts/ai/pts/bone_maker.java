package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class bone_maker extends wizard_ag_corpse_necro_summon
{
	public bone_maker(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 633) == 1 && (AiUtils.GetMemoState(target, 633) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 633) == (1 * 10 + 2)))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 633) == 1 && (AiUtils.GetMemoState(target, 633) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 633) == (1 * 10 + 2)))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 633) == 1 && (AiUtils.GetMemoState(target, 633) == (1 * 10 + 1) || AiUtils.GetMemoState(target, 633) == (1 * 10 + 2)))
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
				SetCurrentQuestID(633);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 436)
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
			}
		}
		super.onEvtDead(attacker);
	}

}