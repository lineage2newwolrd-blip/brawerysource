package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class honey_bear extends warrior_passive
{
	public honey_bear(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		Creature c2 = null;
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
				SetCurrentQuestID(417);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					c2 = AiUtils.GetCreatureFromIndex(i_quest1);
					if (!IsNullCreature(c2))
					{
						if (AiUtils.HaveMemo(c2, 417) == 1 && AiUtils.OwnItemCount(c2, 1653) == 1 && AiUtils.OwnItemCount(c2, 1655) < 5 && i_quest0 == 2)
						{
							if (AiUtils.OwnItemCount(c2, 1655) == 4)
							{
								GiveItem1(c2, 1655, 1);
								SetFlagJournal(c2, 417, 6);
								ShowQuestMark(c2, 417);
								SoundEffect(c2, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c2, 1655, 1);
								SoundEffect(c2, "ItemSound.quest_itemget");
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
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(417);
		switch (i_quest0)
		{
			case 0:
			{
				i_quest0 = 1;
				i_quest1 = AiUtils.GetIndexFromCreature(attacker);
				if (skill_name_id / 65536 == 16646145 / 65536)
				{
					i_quest0 = 2;
				}
				break;
			}
			case 1:
			{
				if (skill_name_id / 65536 == 16646145 / 65536)
				{
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

}