package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class crimson_spider extends warrior_aggressive
{
	public crimson_spider(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 261) == 1 && AiUtils.OwnItemCount(target, 1087) < 8)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 419) == 1 && AiUtils.OwnItemCount(target, 3419) == 1 && AiUtils.OwnItemCount(target, 3424) < 50)
		{
			random1_list.SetInfo(1, target);
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
					SetCurrentQuestID(261);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						GiveItem1(target, 1087, 1);
						if (AiUtils.OwnItemCount(target, 1087) >= 7)
						{
							SoundEffect(target, "ItemSound.quest_middle");
							SetFlagJournal(target, 261, 2);
							ShowQuestMark(target, 261);
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(419);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 60)
						{
							GiveItem1(target, 3424, 1);
							if (AiUtils.OwnItemCount(target, 3424) >= 50)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_middle");
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