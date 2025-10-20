package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class grima extends warrior_aggressive_immediate
{
	public grima(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(334);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 334)!=0 && AiUtils.GetMemoState(c1, 334) == 2 && c1.flag == 2)
					{
						if (AiUtils.Rand(1000) < 33)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								i0 = AiUtils.Rand(1000);
								if (i0 == 0)
								{
									GiveItem1(c1, 57, 100000000);
								}
								else
								{
									GiveItem1(c1, 57, 900000);
								}
								SoundEffect(c1, "ItemSound.quest_itemget");
								c1.flag = 0;
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

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(334);
		if (timer_id == 2336002)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(334);
		AddTimerEx(2336002, 1000 * 200);
		Say(AiUtils.MakeFString(33422, "", "", "", "", ""));
		super.onEvtSpawn();
	}

}