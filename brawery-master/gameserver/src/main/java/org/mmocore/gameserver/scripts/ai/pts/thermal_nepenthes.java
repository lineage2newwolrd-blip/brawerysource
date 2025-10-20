package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class thermal_nepenthes extends warrior_ag_casting_enchant_rangephysical_poison
{
	public thermal_nepenthes(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 624) == 1 && AiUtils.GetMemoState(target, 624) == (1 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 624) == 1 && AiUtils.GetMemoState(target, 624) == (1 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 624) == 1 && AiUtils.GetMemoState(target, 624) == (1 * 10 + 1))
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
				SetCurrentQuestID(624);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 1000)
					{
						if (AiUtils.OwnItemCount(target, 7202) + 1 >= 50)
						{
							if (AiUtils.OwnItemCount(target, 7202) < 50)
							{
								GiveItem1(target, 7202, 50 - AiUtils.OwnItemCount(target, 7202));
								SoundEffect(target, "ItemSound.quest_middle");
							}
							if (AiUtils.OwnItemCount(target, 7204) >= 50 && AiUtils.OwnItemCount(target, 7203) >= 50)
							{
								SetFlagJournal(target, 624, 2);
								ShowQuestMark(target, 624);
								SetMemoState(target, 624, 1 * 10 + 2);
							}
						}
						else
						{
							GiveItem1(target, 7202, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}