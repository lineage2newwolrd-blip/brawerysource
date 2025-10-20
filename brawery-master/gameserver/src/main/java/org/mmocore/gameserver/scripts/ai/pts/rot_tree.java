package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class rot_tree extends warrior_passive_casting_curse
{
	public rot_tree(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3748)!=0 && AiUtils.OwnItemCount(target, 3790) < 40)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 356) == 1 && AiUtils.OwnItemCount(target, 5866) < 50)
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
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 77)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 3790, 1);
							if (AiUtils.OwnItemCount(target, 3790) >= 40)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(356);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 73)
						{
							GiveItem1(target, 5866, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
							if (AiUtils.OwnItemCount(target, 5866) >= 49 && AiUtils.OwnItemCount(target, 5865) >= 50)
							{
								SetFlagJournal(target, 356, 3);
								ShowQuestMark(target, 356);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(target, 5866) >= 49)
							{
								SetFlagJournal(target, 356, 2);
								ShowQuestMark(target, 356);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
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