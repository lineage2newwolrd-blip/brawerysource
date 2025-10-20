package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class kadif_werewolf extends warrior_passive
{
	public kadif_werewolf(final NpcInstance actor){super(actor);}

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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(380);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 380) == 1 && AiUtils.Rand(10) < 5 && AiUtils.OwnItemCount(c1, 5896) < 20)
					{
						if (GetCurrentTick() - c1.quest_last_reward_time > 1)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 5896, 1);
							if (AiUtils.OwnItemCount(c1, 5896) >= 19 && AiUtils.OwnItemCount(c1, 5895) >= 4 && AiUtils.OwnItemCount(c1, 5897) >= 10)
							{
								SetFlagJournal(c1, 380, 2);
								ShowQuestMark(c1, 380);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
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