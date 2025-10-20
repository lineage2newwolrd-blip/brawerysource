package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ashen_wolf extends warrior_passive
{
	public ashen_wolf(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 165) == 1 && AiUtils.OwnItemCount(target, 1160) < 13)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 1)
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
						i0 = AiUtils.Rand(5);
						if (i0 < 4)
						{
							if (AiUtils.OwnItemCount(c1, 1367) >= 49)
							{
								GiveItem1(c1, 1367, 1);
								SetFlagJournal(c1, 264, 2);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 1367, 1);
								ShowQuestMark(c1, 264);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
						else if (AiUtils.OwnItemCount(c1, 1367) >= 49)
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
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(165);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(3) < 3)
						{
							GiveItem1(target, 1160, 1);
							if (AiUtils.OwnItemCount(target, 1160) >= 12)
							{
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 165, 2);
								ShowQuestMark(target, 165);
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 2)
						{
							GiveItem1(target, 7586, 1);
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