package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ant_overseer extends warrior_passive_casting_enchant_clan
{
	public ant_overseer(final NpcInstance actor){super(actor);}

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
		target = attacker;
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3135) == 1 && AiUtils.OwnItemCount(target, 3136) < 70)
		{
			always_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3135) == 1 && AiUtils.OwnItemCount(target, 3136) < 70)
		{
			always_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 216) == 1 && AiUtils.OwnItemCount(target, 3122) == 1 && AiUtils.OwnItemCount(target, 3135) == 1 && AiUtils.OwnItemCount(target, 3136) < 70)
				{
					always_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 142) == 1 && AiUtils.GetMemoState(target, 142) == 7 && GetOneTimeQuestFlag(target, 142) == 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 142) == 1 && AiUtils.GetMemoState(target, 142) == 7 && GetOneTimeQuestFlag(target, 142) == 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 142) == 1 && AiUtils.GetMemoState(target, 142) == 7 && GetOneTimeQuestFlag(target, 142) == 0)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(224);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 3 && AiUtils.OwnItemCount(c1, 3298) < 10)
						{
							if (AiUtils.Rand(2) <= 1)
							{
								if (AiUtils.OwnItemCount(c1, 3298) == 9)
								{
									GiveItem1(c1, 3298, 1);
									SetMemoState(c1, 224, 4);
									SoundEffect(c1, "Itemsound.quest_middle");
									SetFlagJournal(c1, 224, 4);
									ShowQuestMark(c1, 224);
								}
								else
								{
									GiveItem1(c1, 3298, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(216);
					while (!AiUtils.IsNull(target = code_info.Next()))
					{
						if (DistFromMe(target) <= 1500)
						{
							SetCurrentQuestID(216);
							i0 = 0;
							if (AiUtils.Rand(100) < 100 && target.getClassId() == 54 && IsSpoiled())
							{
								i0 = i0 + 5;
							}
							if (AiUtils.Rand(100) < 50 && target.getClassId() == 56)
							{
								target.quest_last_reward_time = GetCurrentTick();
								GiveItem1(target, 3137, 1);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							if (AiUtils.Rand(100) < 100 && AiUtils.OwnItemCount(target, 3136) + i0 < 70)
							{
								i0 = i0 + 5;
							}
							if (i0 >= 1)
							{
								target.quest_last_reward_time = GetCurrentTick();
								GiveItem1(target, 3136, i0);
								if (AiUtils.OwnItemCount(target, 3136) + i0 >= 70)
								{
									SoundEffect(target, "ItemSound.quest_middle");
								}
								else
								{
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 2:
				{
					SetCurrentQuestID(142);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(1000);
						if (i0 < 611)
						{
							if (AiUtils.OwnItemCount(target, 10352) >= 29)
							{
								DeleteItem1(target, 10352, AiUtils.OwnItemCount(target, 10352));
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 142, 5);
								ShowQuestMark(target, 142);
								SetMemoState(target, 142, 8);
							}
							else
							{
								GiveItem1(target, 10352, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 37)
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