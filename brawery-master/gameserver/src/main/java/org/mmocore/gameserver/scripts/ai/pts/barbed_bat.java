package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class barbed_bat extends warrior_passive
{
	public barbed_bat(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(294);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 294)!=0 && AiUtils.OwnItemCount(c1, 1491) < 100)
					{
						i0 = AiUtils.Rand(10);
						if (i0 > 6)
						{
							if (AiUtils.OwnItemCount(c1, 1491) >= 99)
							{
								GiveItem1(c1, 1491, 1);
								SetFlagJournal(c1, 294, 2);
								ShowQuestMark(c1, 294);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 1491, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
						else if (i0 > 3)
						{
							if (AiUtils.OwnItemCount(c1, 1491) >= 98)
							{
								GiveItem1(c1, 1491, 100 - AiUtils.OwnItemCount(c1, 1491));
								SetFlagJournal(c1, 294, 2);
								ShowQuestMark(c1, 294);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 1491, 2);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
						else if (i0 > 1)
						{
							if (AiUtils.OwnItemCount(c1, 1491) >= 97)
							{
								GiveItem1(c1, 1491, 100 - AiUtils.OwnItemCount(c1, 1491));
								SetFlagJournal(c1, 294, 2);
								ShowQuestMark(c1, 294);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 1491, 3);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
						else if (AiUtils.OwnItemCount(c1, 1491) >= 96)
						{
							GiveItem1(c1, 1491, 100 - AiUtils.OwnItemCount(c1, 1491));
							SetFlagJournal(c1, 294, 2);
							ShowQuestMark(c1, 294);
							SoundEffect(c1, "ItemSound.quest_middle");
						}
						else
						{
							GiveItem1(c1, 1491, 4);
							SoundEffect(c1, "ItemSound.quest_itemget");
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