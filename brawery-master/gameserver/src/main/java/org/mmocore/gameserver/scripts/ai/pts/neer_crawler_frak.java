package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class neer_crawler_frak extends warrior_ag_casting_3skill_magical2
{
	public neer_crawler_frak(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(213);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 213)!=0 && AiUtils.OwnItemCount(c1, 2648) == 1 && AiUtils.OwnItemCount(c1, 2653) == 0)
						{
							if (AiUtils.Rand(100) < 50)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2653, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								SetFlagJournal(c1, 213, 3);
								ShowQuestMark(c1, 213);
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(333);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 333) == 1 && AiUtils.OwnItemCount(c1, 3671) >= 1)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							if (AiUtils.Rand(2) == 0)
							{
								GiveItem1(c1, 3848, 1);
							}
							if (AiUtils.Rand(100) < 12)
							{
								GiveItem1(c1, 3440, 1);
							}
						}
					}
					break;
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

}