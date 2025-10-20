package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class trisalim_tote extends warrior_aggressive_casting_curse
{
	public trisalim_tote(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3696)!=0 && AiUtils.OwnItemCount(target, 3718) < 20)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3749)!=0 && AiUtils.OwnItemCount(target, 3791) < 40)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 422)!=0 && AiUtils.GetMemoState(target, 422) == 8 && AiUtils.OwnItemCount(target, 4329) < 3)
		{
			random1_list.SetInfo(2, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 34) == 1 && GetOneTimeQuestFlag(target, 34) == 0 && AiUtils.GetMemoState(target, 34) == (4 * 10 + 1))
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 34) == 1 && GetOneTimeQuestFlag(target, 34) == 0 && AiUtils.GetMemoState(target, 34) == (4 * 10 + 1))
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 34) == 1 && GetOneTimeQuestFlag(target, 34) == 0 && AiUtils.GetMemoState(target, 34) == (4 * 10 + 1))
				{
					random1_list.SetInfo(3, target);
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
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 60)
						{
							GiveItem1(target, 3718, 1);
							if (AiUtils.OwnItemCount(target, 3718) >= 20)
							{
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 85)
						{
							GiveItem1(target, 3791, 1);
							if (AiUtils.OwnItemCount(target, 3791) >= 40)
							{
								SoundEffect(target, "ItemSound.quest_middle");
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
					SetCurrentQuestID(422);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 4329) == 2)
						{
							GiveItem1(target, 4329, 1);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							GiveItem1(target, 4329, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(34);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 500)
						{
							if (AiUtils.OwnItemCount(target, 7528) + 1 >= 10)
							{
								if (AiUtils.OwnItemCount(target, 7528) <= 10)
								{
									GiveItem1(target, 7528, 10 - AiUtils.OwnItemCount(target, 7528));
									SoundEffect(target, "ItemSound.quest_middle");
								}
								SetFlagJournal(target, 34, 5);
								ShowQuestMark(target, 34);
								SetMemoState(target, 34, 4 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7528, 1);
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