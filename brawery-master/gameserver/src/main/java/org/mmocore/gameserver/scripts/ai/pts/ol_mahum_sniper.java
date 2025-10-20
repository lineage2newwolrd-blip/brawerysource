package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ol_mahum_sniper extends warrior_passive_use_bow
{
	public ol_mahum_sniper(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

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
					SetCurrentQuestID(333);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 333) == 1 && AiUtils.OwnItemCount(c1, 3672) >= 1)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							if (AiUtils.Rand(2) == 0)
							{
								GiveItem1(c1, 3849, 1);
							}
							if (AiUtils.Rand(100) < 11)
							{
								GiveItem1(c1, 3441, 1);
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(709);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c0 = Pledge_GetLeader(target);
						if (!IsNullCreature(c0))
						{
							if (DistFromMe(c0) <= 1500 && AiUtils.HaveMemo(c0, 709) == 1 && AiUtils.GetMemoState(c0, 709) / 10 == 2)
							{
								i0 = c0.param1;
								if (i0 >= 0)
								{
									i1 = AiUtils.Rand(100);
									if (i1 < c0.param1)
									{
										CreateOnePrivateEx(27392, "q_bloody_senior", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), actor.getObjectId());
									}
									else
									{
										c0.param1 = i0 + 1;
									}
								}
								else
								{
									c0.param1 = 1;
								}
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