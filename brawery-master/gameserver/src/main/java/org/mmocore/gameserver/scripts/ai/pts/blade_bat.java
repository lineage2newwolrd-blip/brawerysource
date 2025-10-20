package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class blade_bat extends warrior_passive
{
	public blade_bat(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
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
		if (AiUtils.HaveMemo(target, 108) == 1 && AiUtils.OwnItemCount(target, 1570)!=0 && AiUtils.OwnItemCount(target, 1571) == 0)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 294)!=0 && AiUtils.OwnItemCount(target, 1491) < 100)
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
					SetCurrentQuestID(108);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) < 2)
						{
							GiveItem1(target, 1571, 1);
							DeleteItem1(target, 1570, 1);
							SetFlagJournal(target, 108, 12);
							ShowQuestMark(target, 108);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(294);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(10);
						if (i0 > 5)
						{
							if (AiUtils.OwnItemCount(target, 1491) >= 99)
							{
								GiveItem1(target, 1491, 1);
								SetFlagJournal(target, 294, 2);
								ShowQuestMark(target, 294);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 1491, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
						else if (i0 > 2)
						{
							if (AiUtils.OwnItemCount(target, 1491) >= 98)
							{
								GiveItem1(target, 1491, 100 - AiUtils.OwnItemCount(target, 1491));
								SetFlagJournal(target, 294, 2);
								ShowQuestMark(target, 294);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(target, 1491, 2);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
						else if (AiUtils.OwnItemCount(target, 1491) >= 97)
						{
							GiveItem1(target, 1491, 100 - AiUtils.OwnItemCount(target, 1491));
							SetFlagJournal(target, 294, 2);
							ShowQuestMark(target, 294);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							GiveItem1(target, 1491, 3);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}