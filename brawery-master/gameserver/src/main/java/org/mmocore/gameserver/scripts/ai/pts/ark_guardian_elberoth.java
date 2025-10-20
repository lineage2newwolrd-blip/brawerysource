package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ark_guardian_elberoth extends warrior_passive
{
	public ark_guardian_elberoth(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(348);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 348)!=0 && AiUtils.GetMemoStateEx(c1, 348, 0) < 8 && AiUtils.GetMemoStateEx(c1, 348, 1) % 1000 / 100 == 1 && AiUtils.OwnItemCount(c1, 4292) == 0 && AiUtils.OwnItemCount(c1, 4397) == 0)
					{
						i0 = AiUtils.GetMemoStateEx(c1, 348, 1);
						i0 = i0 + 100;
						SetMemoStateEx(c1, 348, 1, i0);
						if (i0 % 10 != 0)
						{
							SetFlagJournal(c1, 348, 11);
							ShowQuestMark(c1, 348);
						}
						GiveItem1(c1, 4292, 1);
						SoundEffect(c1, "ItemSound.quest_itemget");
						Say(AiUtils.MakeFString(34832, "", "", "", "", ""));
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

		SetCurrentQuestID(348);
		switch (i_quest0)
		{
			case 0:
			{
				Say(AiUtils.MakeFString(34833, "", "", "", "", ""));
				i_quest0 = 1;
				break;
			}
			case 1:
			{
				break;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Creature c0 = null;

		SetCurrentQuestID(348);
		if (timer_id == 34802)
		{
			Despawn();
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;

		SetCurrentQuestID(348);
		AddTimerEx(34804, 1000 * 600);
		Say(AiUtils.MakeFString(34837, "", "", "", "", ""));
		super.onEvtSpawn();
	}

}