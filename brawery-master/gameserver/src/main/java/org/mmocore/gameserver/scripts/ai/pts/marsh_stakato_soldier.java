package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class marsh_stakato_soldier extends warrior_passive
{
	public marsh_stakato_soldier(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
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
		always_list.SetInfo(3, target);
		target = attacker;
		always_list.SetInfo(4, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 333) == 1 && AiUtils.OwnItemCount(target, 3674) >= 1)
		{
			random1_list.SetInfo(5, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(6, target);
		}
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(6, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
				{
					random1_list.SetInfo(6, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(7, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(7, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(7, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(217);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 217)!=0 && AiUtils.GetMemoState(c1, 217) == 6 && AiUtils.OwnItemCount(c1, 2751) < 5 && AiUtils.OwnItemCount(c1, 2755)!=0 && AiUtils.OwnItemCount(c1, 2754) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 2751) >= 4)
							{
								GiveItem1(c1, 2754, 1);
								DeleteItem1(c1, 2751, AiUtils.OwnItemCount(c1, 2751));
								SoundEffect(c1, "Itemsound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2752) >= 1 && AiUtils.OwnItemCount(c1, 2753) >= 1)
								{
									SetFlagJournal(c1, 217, 7);
									ShowQuestMark(c1, 217);
								}
							}
							else
							{
								GiveItem1(c1, 2751, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
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
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 10 && AiUtils.OwnItemCount(c1, 3303) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3302)!=0 && AiUtils.OwnItemCount(c1, 3304)!=0 && AiUtils.OwnItemCount(c1, 3305)!=0)
							{
								GiveItem1(c1, 3303, 1);
								SetMemoState(c1, 224, 11);
								SoundEffect(c1, "Itemsound.quest_middle");
								SetFlagJournal(c1, 224, 11);
								ShowQuestMark(c1, 224);
							}
							else
							{
								GiveItem1(c1, 3303, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
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
						if (AiUtils.HaveMemo(c1, 219)!=0 && AiUtils.OwnItemCount(c1, 3177)!=0 && AiUtils.OwnItemCount(c1, 3182) < 10)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3182) == 9)
								{
									GiveItem1(c1, 3182, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3178) >= 10 && AiUtils.OwnItemCount(c1, 3179) >= 10 && AiUtils.OwnItemCount(c1, 3180) >= 10 && AiUtils.OwnItemCount(c1, 3181) >= 10)
									{
										SetFlagJournal(c1, 219, 7);
										ShowQuestMark(c1, 219);
									}
								}
								else
								{
									GiveItem1(c1, 3182, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(221);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 221)!=0 && AiUtils.OwnItemCount(c1, 3240) == 1 && AiUtils.OwnItemCount(c1, 3272) == 1 && AiUtils.OwnItemCount(c1, 3270) == 0 && AiUtils.OwnItemCount(c1, 3271) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3273) < 20 && AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3273, 1);
								if (AiUtils.OwnItemCount(c1, 3273) >= 19)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3274) >= 10 && AiUtils.OwnItemCount(c1, 3275) >= 10)
									{
										SetFlagJournal(c1, 221, 8);
										ShowQuestMark(c1, 221);
									}
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(228);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 228)!=0 && AiUtils.OwnItemCount(c1, 2847) == 1 && AiUtils.OwnItemCount(c1, 2862) == 1 && AiUtils.OwnItemCount(c1, 2848) < 20)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2848, 1);
								if (AiUtils.OwnItemCount(c1, 2848) >= 20)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
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
				case 5:
				{
					SetCurrentQuestID(333);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 56)
						{
							GiveItem1(target, 3851, 1);
						}
						if (AiUtils.Rand(100) < 14)
						{
							GiveItem1(target, 3443, 1);
						}
						if (AiUtils.Rand(100) < 2 && AiUtils.HaveMemo(target, 333)!=0 && AiUtils.OwnItemCount(target, 3674)!=0)
						{
							CreateOnePrivate(27152, "marsh_stakato_marquess", 0, 1);
						}
					}
					break;
				}
				case 6:
				{
					SetCurrentQuestID(134);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						i1 = AiUtils.Rand(100);
						if (AiUtils.OwnItemCount(target, 10336) >= 1)
						{
							if (i1 < 100)
							{
								CreateOnePrivateEx(27339, "pagan_of_cruma", 0, 0, AiUtils.FloatToInt(actor.getX()) + 20, AiUtils.FloatToInt(actor.getY()) + 20, AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), actor.getObjectId());
								DeleteItem1(target, 10336, 1);
							}
							else
							{
								DeleteItem1(target, 10336, 1);
							}
						}
						else if (i0 < 81)
						{
							GiveItem1(target, 10335, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 7:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 245)
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