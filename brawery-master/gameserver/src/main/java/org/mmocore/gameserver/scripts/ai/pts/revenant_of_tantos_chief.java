package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class revenant_of_tantos_chief extends warrior_passive
{
	public revenant_of_tantos_chief(final NpcInstance actor){super(actor);}

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
					if (AiUtils.HaveMemo(c1, 220)!=0 && AiUtils.OwnItemCount(c1, 3235)!=0 && AiUtils.OwnItemCount(c1, 3236) == 0)
					{
						GiveItem1(c1, 3236, 1);
						SetFlagJournal(c1, 220, 10);
						ShowQuestMark(c1, 220);
						SoundEffect(c1, "Itemsound.quest_middle");
						Say(AiUtils.MakeFString(22056, "", "", "", "", ""));
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
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		SetCurrentQuestID(220);
		switch (i_quest0)
		{
			case 0:
			{
				c_quest0 = attacker;
				if (AiUtils.HaveMemo(c_quest0, 220)!=0 && AiUtils.OwnItemCount(c_quest0, 3236) == 0)
				{
					Say(AiUtils.MakeFString(22055, "", "", "", "", ""));
					i_quest0 = 1;
				}
				break;
			}
			case 1:
			{
				if (AiUtils.HaveMemo(c_quest0, 220)!=0 && AiUtils.OwnItemCount(c_quest0, 3236) == 0 && actor.currentHp < actor.getMaxHp() / 3.000000f)
				{
					Say(AiUtils.MakeFString(22057, "", "", "", "", ""));
					i_quest0 = 2;
				}
				break;
			}
			case 2:
			{
				break;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		SetCurrentQuestID(220);
		if (timer_id == 22005)
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
		AddTimerEx(22005, 1000 * 200);
		super.onEvtSpawn();
	}

}