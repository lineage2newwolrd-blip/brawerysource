package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class bloody_bee extends warrior_passive_casting_curse
{
	public bloody_bee(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
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
				SetCurrentQuestID(330);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 330) == 1 && AiUtils.OwnItemCount(c1, 1420) == 1 && AiUtils.OwnItemCount(c1, 1424) + AiUtils.OwnItemCount(c1, 1425) + AiUtils.OwnItemCount(c1, 1429) + AiUtils.OwnItemCount(c1, 1430) + AiUtils.OwnItemCount(c1, 1433) + AiUtils.OwnItemCount(c1, 1437) + AiUtils.OwnItemCount(c1, 1438) + AiUtils.OwnItemCount(c1, 1441) < 5 && AiUtils.OwnItemCount(c1, 1426) == 1)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 80)
						{
							if (AiUtils.OwnItemCount(c1, 1427) < 20)
							{
								if (GetCurrentTick() - c1.quest_last_reward_time > 1)
								{
									c1.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c1, 1427, 1);
									if (AiUtils.OwnItemCount(c1, 1427) == 20)
									{
										SoundEffect(c1, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
								}
							}
						}
						else if (i0 > 95)
						{
							if (AiUtils.OwnItemCount(c1, 1428) < 10)
							{
								if (GetCurrentTick() - c1.quest_last_reward_time > 1)
								{
									c1.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c1, 1428, 1);
									if (AiUtils.OwnItemCount(c1, 1428) == 10)
									{
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