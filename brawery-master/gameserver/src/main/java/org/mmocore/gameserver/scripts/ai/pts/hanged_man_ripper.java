package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class hanged_man_ripper extends warrior_aggressive_casting_hold_magic
{
	public hanged_man_ripper(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
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
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 66) == 1 && AiUtils.GetMemoState(target, 66) == 8 && AiUtils.OwnItemCount(target, 9775) < 30)
		{
			always_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 137) == 1 && AiUtils.GetMemoState(target, 137) == 5 && AiUtils.OwnItemCount(target, 10340) < 30)
		{
			random1_list.SetInfo(4, target);
		}
		if (AiUtils.HaveMemo(target, 137) == 1 && AiUtils.GetMemoState(target, 137) == 5 && AiUtils.OwnItemCount(target, 10340) < 30)
		{
			random1_list.SetInfo(4, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 137) == 1 && AiUtils.GetMemoState(target, 137) == 5 && AiUtils.OwnItemCount(target, 10340) < 30)
				{
					random1_list.SetInfo(4, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(212);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 212)!=0 && AiUtils.GetMemoState(c1, 212) == 6)
						{
							if (AiUtils.Rand(100) < (c1.flag - 3) * 33)
							{
								CreateOnePrivateEx(30656, "spirit_of_sir_talianus", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
								SoundEffect(c1, "ItemSound.quest_middle");
								c1.flag = 0;
								SetFlagJournal(c1, 212, 8);
								ShowQuestMark(c1, 212);
							}
							else
							{
								c1.flag = c1.flag + 1;
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(219);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 219)!=0 && AiUtils.OwnItemCount(c1, 3174)!=0 && AiUtils.OwnItemCount(c1, 3175) == 0)
						{
							GiveItem1(c1, 3175, 1);
							DeleteItem1(c1, 3174, 1);
							SoundEffect(c1, "Itemsound.quest_middle");
							SetFlagJournal(c1, 219, 3);
							ShowQuestMark(c1, 219);
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(225);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 225) == 1 && AiUtils.OwnItemCount(c1, 2800) == 1 && AiUtils.OwnItemCount(c1, 2802) < 4 && AiUtils.OwnItemCount(c1, 2804) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 2802) < 3)
							{
								if (AiUtils.Rand(100) < 50)
								{
									c1.quest_last_reward_time = GetCurrentTick();
									GiveItem1(c1, 2802, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
							}
							else if (AiUtils.Rand(100) < 50)
							{
								DeleteItem1(c1, 2802, AiUtils.OwnItemCount(c1, 2802));
								GiveItem1(c1, 2804, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2803) >= 1)
								{
									SetFlagJournal(c1, 225, 15);
									ShowQuestMark(c1, 225);
								}
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(66);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (AiUtils.OwnItemCount(target, 9775) < 30)
						{
							if (AiUtils.OwnItemCount(target, 9775) >= 29)
							{
								SetFlagJournal(target, 66, 8);
								ShowQuestMark(target, 66);
								SoundEffect(target, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
							GiveItem1(target, 9775, 1);
							if (i4 < 100 && AiUtils.OwnItemCount(target, 9775) < 29)
							{
								GiveItem1(target, 9775, 1);
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
			if (code_info.getCode() == 4)
			{
				SetCurrentQuestID(137);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i0 = AiUtils.Rand(100);
					if (i0 < 100)
					{
						if (AiUtils.OwnItemCount(target, 10340) >= 29)
						{
							GiveItem1(target, 10340, 1);
							SetFlagJournal(target, 137, 3);
							ShowQuestMark(target, 137);
							SoundEffect(target, "ItemSound.quest_middle");
						}
						else
						{
							GiveItem1(target, 10340, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}