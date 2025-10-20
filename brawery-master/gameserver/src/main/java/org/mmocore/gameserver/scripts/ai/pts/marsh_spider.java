package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class marsh_spider extends warrior_aggressive_casting_curse
{
	public marsh_spider(final NpcInstance actor){super(actor);}

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
		always_list.SetInfo(5, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 420) == 1 && AiUtils.OwnItemCount(target, 3824) == 1 && AiUtils.OwnItemCount(target, 3825) < 20)
		{
			random1_list.SetInfo(6, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(7, target);
		}
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(7, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
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
					SetCurrentQuestID(231);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 231)!=0 && AiUtils.GetMemoState(c1, 231) == 4 && AiUtils.OwnItemCount(c1, 2877) < 10 && AiUtils.OwnItemCount(c1, 2875) == 1)
						{
							if (AiUtils.OwnItemCount(c1, 2877) == 9)
							{
								GiveItem1(c1, 2877, 1);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 2877, 1);
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
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 10 && AiUtils.OwnItemCount(c1, 3304) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3302)!=0 && AiUtils.OwnItemCount(c1, 3305)!=0 && AiUtils.OwnItemCount(c1, 3303)!=0)
							{
								GiveItem1(c1, 3304, 1);
								SetMemoState(c1, 224, 11);
								SoundEffect(c1, "Itemsound.quest_middle");
								SetFlagJournal(c1, 224, 11);
								ShowQuestMark(c1, 224);
							}
							else
							{
								GiveItem1(c1, 3304, 1);
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
						if (AiUtils.HaveMemo(c1, 219)!=0 && AiUtils.OwnItemCount(c1, 3177)!=0 && AiUtils.OwnItemCount(c1, 3179) < 10)
						{
							if (AiUtils.Rand(2) <= 1)
							{
								if (AiUtils.OwnItemCount(c1, 3179) == 9)
								{
									GiveItem1(c1, 3179, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3178) >= 10 && AiUtils.OwnItemCount(c1, 3180) >= 10 && AiUtils.OwnItemCount(c1, 3181) >= 10 && AiUtils.OwnItemCount(c1, 3182) >= 10)
									{
										SetFlagJournal(c1, 219, 7);
										ShowQuestMark(c1, 219);
									}
								}
								else
								{
									GiveItem1(c1, 3179, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(232);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 232) == 1 && AiUtils.OwnItemCount(c1, 3391) == 1 && AiUtils.OwnItemCount(c1, 3395) == 1 && AiUtils.OwnItemCount(c1, 3409) == 0)
						{
							if (AiUtils.Rand(100) < 100)
							{
								if (AiUtils.OwnItemCount(c1, 3407) < 10)
								{
									GiveItem1(c1, 3407, 2);
									if (AiUtils.OwnItemCount(c1, 3407) >= 8)
									{
										SoundEffect(c1, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
								}
								else if (AiUtils.OwnItemCount(c1, 3408) < 10)
								{
									GiveItem1(c1, 3408, 2);
									if (AiUtils.OwnItemCount(c1, 3408) >= 8)
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
					}
					break;
				}
				case 4:
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
							if (AiUtils.OwnItemCount(c1, 3275) < 10 && AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3275, 1);
								if (AiUtils.OwnItemCount(c1, 3275) >= 9)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3273) >= 20 && AiUtils.OwnItemCount(c1, 3274) >= 10)
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
				case 5:
				{
					SetCurrentQuestID(218);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 218)!=0 && AiUtils.OwnItemCount(c1, 3144) == 1 && AiUtils.OwnItemCount(c1, 3153) == 1 && AiUtils.OwnItemCount(c1, 3164) < 20)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3164, 4);
								if (AiUtils.OwnItemCount(c1, 3164) >= 16)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3165) >= 20)
									{
										SetFlagJournal(c1, 218, 10);
										ShowQuestMark(c1, 218);
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
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 6:
				{
					SetCurrentQuestID(420);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 50)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 3825, 1);
							if (AiUtils.OwnItemCount(target, 3825) >= 19)
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
				case 7:
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
						else if (i0 < 95)
						{
							GiveItem1(target, 10335, 1);
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