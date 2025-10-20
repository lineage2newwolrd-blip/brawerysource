package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class tumran_bugbear extends warrior_passive
{
	public tumran_bugbear(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
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
		always_list.SetInfo(0, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 62) == 1 && AiUtils.GetMemoState(target, 62) == 5 && AiUtils.OwnItemCount(target, 9751) < 1)
		{
			random1_list.SetInfo(1, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(171);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 171)!=0 && AiUtils.GetMemoState(c1, 171) == 5)
					{
						if (GetCurrentTick() - c1.quest_last_reward_time > 1)
						{
							if (AiUtils.OwnItemCount(c1, 4241) == 0)
							{
								GiveItem1(c1, 4241, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
							else if (AiUtils.OwnItemCount(c1, 4241) >= 1 && AiUtils.OwnItemCount(c1, 4242) == 0)
							{
								if (AiUtils.Rand(100) <= 19)
								{
									GiveItem1(c1, 4242, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							else if (AiUtils.OwnItemCount(c1, 4241) >= 1 && AiUtils.OwnItemCount(c1, 4242) >= 1 && AiUtils.OwnItemCount(c1, 4243) == 0)
							{
								if (AiUtils.Rand(100) <= 19)
								{
									GiveItem1(c1, 4243, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							else if (AiUtils.OwnItemCount(c1, 4241) >= 1 && AiUtils.OwnItemCount(c1, 4242) >= 1 && AiUtils.OwnItemCount(c1, 4243) >= 1 && AiUtils.OwnItemCount(c1, 4244) == 0)
							{
								if (AiUtils.Rand(100) <= 19)
								{
									GiveItem1(c1, 4244, 1);
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			if (code_info.getCode() == 1)
			{
				SetCurrentQuestID(62);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 500 && AiUtils.OwnItemCount(target, 9751) < 1)
					{
						SoundEffect(target, "ItemSound.quest_itemget");
						GiveItem1(target, 9751, 1);
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}