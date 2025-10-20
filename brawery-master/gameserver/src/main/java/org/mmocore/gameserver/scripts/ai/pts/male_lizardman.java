package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class male_lizardman extends warrior_passive
{
	public male_lizardman(final NpcInstance actor){super(actor);}

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
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 39) == 1 && AiUtils.GetMemoState(target, 39) == (2 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 39) == 1 && AiUtils.GetMemoState(target, 39) == (2 * 10 + 1))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 39) == 1 && AiUtils.GetMemoState(target, 39) == (2 * 10 + 1))
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 44) == 1 && AiUtils.GetMemoState(target, 44) == (2 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 44) == 1 && AiUtils.GetMemoState(target, 44) == (2 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 44) == 1 && AiUtils.GetMemoState(target, 44) == (2 * 10 + 1))
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 118) == 1 && AiUtils.GetMemoState(target, 118) == 1 && AiUtils.OwnItemCount(target, 8062) < 10)
		{
			always_list.SetInfo(2, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 63) == 1 && AiUtils.GetMemoState(target, 63) == 16 && AiUtils.OwnItemCount(target, 9771) < 1)
		{
			random1_list.SetInfo(3, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 2)
			{
				SetCurrentQuestID(118);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(10) < 7)
					{
						GiveItem1(target, 8062, 1);
						if (AiUtils.OwnItemCount(target, 8062) >= 9)
						{
							SoundEffect(target, "ItemSound.quest_middle");
							SetFlagJournal(target, 118, 2);
							ShowQuestMark(target, 118);
						}
						else
						{
							SoundEffect(target, "ItemSound.quest_itemget");
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
					SetCurrentQuestID(39);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 500)
						{
							if (AiUtils.OwnItemCount(target, 7178) + 1 >= 100)
							{
								if (AiUtils.OwnItemCount(target, 7178) < 100)
								{
									GiveItem1(target, 7178, 100 - AiUtils.OwnItemCount(target, 7178));
									SoundEffect(target, "ItemSound.quest_middle");
								}
								if (AiUtils.OwnItemCount(target, 7179) >= 100)
								{
									SetFlagJournal(target, 39, 3);
									ShowQuestMark(target, 39);
									SetMemoState(target, 39, 2 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7178, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(44);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if ((i4 < 1000 && 1000 != 0))
						{
							if (AiUtils.OwnItemCount(target, 7552) + 1 >= 30)
							{
								if (AiUtils.OwnItemCount(target, 7552) < 30)
								{
									GiveItem1(target, 7552, 30 - AiUtils.OwnItemCount(target, 7552));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 44, 3);
									ShowQuestMark(target, 44);
								}
								SetMemoState(target, 44, 2 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7552, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(63);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.GetMemoStateEx(target, 63, 1);
						if (i4 < 4)
						{
							SetMemoStateEx(target, 63, 1, i4 + 1);
						}
						else
						{
							SetMemoStateEx(target, 63, 1, 0);
							CreateOnePrivateEx(27337, "olmahum_takin", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}