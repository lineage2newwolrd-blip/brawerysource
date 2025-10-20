package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class young_brown_keltir extends warrior_passive
{
	public young_brown_keltir(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 165) == 1 && AiUtils.OwnItemCount(target, 1160) < 13)
		{
			random1_list.SetInfo(0, target);
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
				SetCurrentQuestID(165);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(3) < 1)
					{
						GiveItem1(target, 1160, 1);
						if (AiUtils.OwnItemCount(target, 1160) >= 12)
						{
							SoundEffect(target, "ItemSound.quest_middle");
							SetFlagJournal(target, 165, 2);
							ShowQuestMark(target, 165);
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}