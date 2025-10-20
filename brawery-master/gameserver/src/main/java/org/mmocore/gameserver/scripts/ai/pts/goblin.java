package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class goblin extends warrior_passive
{
	public goblin(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(264);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.OwnItemCount(c1, 1367) < 50 && AiUtils.HaveMemo(c1, 264)!=0)
					{
						i0 = AiUtils.Rand(20);
						if (i0 < 5)
						{
							if (AiUtils.OwnItemCount(c1, 1367) >= 49)
							{
								GiveItem1(c1, 1367, 1);
								SetFlagJournal(c1, 264, 2);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 1367) == 48)
							{
								GiveItem1(c1, 1367, 2);
								SetFlagJournal(c1, 264, 2);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 1367, 2);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
						else if (i0 < 10)
						{
							if (AiUtils.OwnItemCount(c1, 1367) == 46)
							{
								GiveItem1(c1, 1367, 4);
								SetFlagJournal(c1, 264, 2);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 1367) == 47)
							{
								GiveItem1(c1, 1367, 3);
								SetFlagJournal(c1, 264, 2);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 1367) == 48)
							{
								GiveItem1(c1, 1367, 2);
								SetFlagJournal(c1, 264, 2);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 1367) == 49)
							{
								GiveItem1(c1, 1367, 1);
								SetFlagJournal(c1, 264, 2);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 1367, 8);
								SoundEffect(c1, "ItemSound.quest_itemget");
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