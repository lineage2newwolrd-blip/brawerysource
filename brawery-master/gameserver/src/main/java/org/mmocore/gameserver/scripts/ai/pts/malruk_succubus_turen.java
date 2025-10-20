package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class malruk_succubus_turen extends warrior_passive_casting_ddmagic
{
	public malruk_succubus_turen(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 353) == 1)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (5 * 10 + 1) && target.isSubClassActive() != 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (5 * 10 + 1) && target.isSubClassActive() != 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (5 * 10 + 1) && target.isSubClassActive() != 0)
				{
					random1_list.SetInfo(2, target);
				}
			}
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
					SetCurrentQuestID(336);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 100)
						{
							GiveItem1(target, 3482, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(353);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 72)
						{
							GiveItem1(target, 5862, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(241);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 120)
						{
							if (AiUtils.OwnItemCount(target, 7597) + 1 >= 10)
							{
								if (AiUtils.OwnItemCount(target, 7597) < 10)
								{
									GiveItem1(target, 7597, 10 - AiUtils.OwnItemCount(target, 7597));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 241, 7);
									ShowQuestMark(target, 241);
									SetMemoState(target, 241, 5 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7597, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}