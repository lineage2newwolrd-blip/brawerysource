package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class cave_keeper extends warrior_passive_physicalspecial
{
	public cave_keeper(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.GetMemoState(target, 337) == 70000 && AiUtils.OwnItemCount(target, 3861) == 0 && AiUtils.HaveMemo(target, 337) == 1)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.GetMemoState(target, 337) == 70000 && AiUtils.OwnItemCount(target, 3861) == 0 && AiUtils.HaveMemo(target, 337) == 1)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.GetMemoState(target, 337) == 70000 && AiUtils.OwnItemCount(target, 3861) == 0 && AiUtils.HaveMemo(target, 337) == 1)
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
				SetCurrentQuestID(337);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(5) == 0)
					{
						CreateOnePrivate(27167, "abyssal_jewel_3", 0, 1);
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}