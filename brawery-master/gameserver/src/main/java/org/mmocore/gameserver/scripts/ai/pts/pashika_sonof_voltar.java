package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class pashika_sonof_voltar extends warrior_aggressive_immediate
{
	public pashika_sonof_voltar(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(220);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 220)!=0 && AiUtils.OwnItemCount(c1, 3209)!=0 && AiUtils.OwnItemCount(c1, 3210)!=0 && AiUtils.OwnItemCount(c1, 3223)!=0 && AiUtils.OwnItemCount(c1, 3221) == 0)
					{
						if (AiUtils.OwnItemCount(c1, 3222)!=0)
						{
							GiveItem1(c1, 3221, 1);
							DeleteItem1(c1, 3223, 1);
							SoundEffect(c1, "Itemsound.quest_middle");
						}
						else
						{
							GiveItem1(c1, 3221, 1);
							SoundEffect(c1, "Itemsound.quest_itemget");
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

		SetCurrentQuestID(220);
		if (timer_id == 22001)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(220);
		AddTimerEx(22001, 1000 * 200);
		super.onEvtSpawn();
	}

}