package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class leto_lizardman_shaman extends wizard_pa_ddmagic2_curse
{
	public leto_lizardman_shaman(final NpcInstance actor){super(actor);}

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
		int weapon_class_id = 0;
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
		always_list.SetInfo(6, target);
		target = attacker;
		always_list.SetInfo(7, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 186) == 1 && AiUtils.GetMemoState(target, 186) == 2 && GetOneTimeQuestFlag(target, 186) == 0)
		{
			random1_list.SetInfo(8, target);
		}
		if (AiUtils.HaveMemo(target, 186) == 1 && AiUtils.GetMemoState(target, 186) == 2 && GetOneTimeQuestFlag(target, 186) == 0)
		{
			random1_list.SetInfo(8, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 186) == 1 && AiUtils.GetMemoState(target, 186) == 2 && GetOneTimeQuestFlag(target, 186) == 0)
				{
					random1_list.SetInfo(8, target);
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
						if (AiUtils.HaveMemo(c1, 212)!=0 && AiUtils.GetMemoState(c1, 212) == 9 && AiUtils.OwnItemCount(c1, 2641) < 20)
						{
							if (AiUtils.OwnItemCount(c1, 2641) == 19)
							{
								GiveItem1(c1, 2641, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								SetFlagJournal(c1, 212, 12);
								ShowQuestMark(c1, 212);
							}
							else
							{
								GiveItem1(c1, 2641, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
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
						if (AiUtils.HaveMemo(c1, 224)!=0 && AiUtils.GetMemoState(c1, 224) == 13 && AiUtils.OwnItemCount(c1, 3306) < 140)
						{
							if ((AiUtils.OwnItemCount(c1, 3306) - 10) * 5 > AiUtils.Rand(100))
							{
								CreateOnePrivateEx(27090, "serpent_demon_kadesh", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
								DeleteItem1(c1, 3306, AiUtils.OwnItemCount(c1, 3306));
								SoundEffect(c1, "Itemsound.quest_before_battle");
							}
							else
							{
								GiveItem1(c1, 3306, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(223);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 223)!=0 && AiUtils.OwnItemCount(c1, 3285)!=0 && AiUtils.OwnItemCount(c1, 3292) < 10)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3292) >= 9)
								{
									GiveItem1(c1, 3292, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									SetFlagJournal(c1, 223, 13);
									ShowQuestMark(c1, 223);
								}
								else
								{
									GiveItem1(c1, 3292, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(230);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3352) == 0 && AiUtils.OwnItemCount(c1, 3347)!=0 && AiUtils.OwnItemCount(c1, 3337) < 30)
						{
							if (AiUtils.OwnItemCount(c1, 3337) >= 28)
							{
								GiveItem1(c1, 3337, 2);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 3337, 2);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 4:
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
						if (AiUtils.HaveMemo(c1, 218)!=0 && AiUtils.OwnItemCount(c1, 3156) == 1 && AiUtils.Rand(100) < 100)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							if (AiUtils.OwnItemCount(c1, 3166) == 0)
							{
								GiveItem1(c1, 3166, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 3167) == 0)
							{
								GiveItem1(c1, 3167, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 3168) == 0)
							{
								GiveItem1(c1, 3168, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 3169) == 0)
							{
								GiveItem1(c1, 3169, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 3170) == 0)
							{
								GiveItem1(c1, 3170, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else if (AiUtils.OwnItemCount(c1, 3171) == 0)
							{
								GiveItem1(c1, 3171, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(229);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 229) == 1 && AiUtils.OwnItemCount(c1, 3309) == 1 && AiUtils.OwnItemCount(c1, 3310) == 1)
						{
							if (AiUtils.Rand(100) <= 100 && AiUtils.OwnItemCount(c1, 3312) < 20)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3312, 1);
								if (AiUtils.OwnItemCount(c1, 3312) >= 20)
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
				case 6:
				{
					SetCurrentQuestID(233);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 233)!=0 && AiUtils.OwnItemCount(c1, 2888) == 1)
						{
							i0 = AiUtils.Rand(100);
							if (i0 > 0)
							{
								if (AiUtils.OwnItemCount(c1, 2889) == 0)
								{
									GiveItem1(c1, 2889, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else if (AiUtils.OwnItemCount(c1, 2890) == 0)
								{
									GiveItem1(c1, 2890, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else if (AiUtils.OwnItemCount(c1, 2891) == 0)
								{
									GiveItem1(c1, 2891, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else if (AiUtils.OwnItemCount(c1, 2892) == 0)
								{
									GiveItem1(c1, 2892, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else if (AiUtils.OwnItemCount(c1, 2893) == 0)
								{
									GiveItem1(c1, 2893, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
							}
						}
					}
					break;
				}
				case 7:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (GetCurrentTick() - c1.quest_last_reward_time > 1)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3695)!=0 && AiUtils.OwnItemCount(c1, 3710) < 20)
							{
								if (AiUtils.Rand(100) < 60)
								{
									GiveItem1(c1, 3710, 1);
									if (AiUtils.OwnItemCount(c1, 3710) >= 20)
									{
										SoundEffect(c1, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
								}
							}
							if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3742)!=0 && AiUtils.OwnItemCount(c1, 3784) < 1)
							{
								if (AiUtils.Rand(10) < 2)
								{
									CreateOnePrivate(27156, "leto_shaman_ketz", 0, 1);
								}
							}
							if (AiUtils.HaveMemo(c1, 335)!=0 && actor.last_blow_weapon_class_id == 3471 && (AiUtils.OwnItemCount(c1, 3692)!=0 || AiUtils.OwnItemCount(c1, 3693)!=0))
							{
								if (AiUtils.Rand(100) < 60)
								{
									if (AiUtils.OwnItemCount(c1, 3697)!=0)
									{
										if (AiUtils.OwnItemCount(c1, 3698)!=0)
										{
											GiveItem1(c1, 3699, 1);
											DeleteItem1(c1, 3698, AiUtils.OwnItemCount(c1, 3698));
										}
										else if (AiUtils.OwnItemCount(c1, 3699)!=0)
										{
											GiveItem1(c1, 3700, 1);
											DeleteItem1(c1, 3699, AiUtils.OwnItemCount(c1, 3699));
										}
										else if (AiUtils.OwnItemCount(c1, 3700)!=0)
										{
											GiveItem1(c1, 3701, 1);
											DeleteItem1(c1, 3700, AiUtils.OwnItemCount(c1, 3700));
										}
										else if (AiUtils.OwnItemCount(c1, 3701)!=0)
										{
											GiveItem1(c1, 3702, 1);
											DeleteItem1(c1, 3701, AiUtils.OwnItemCount(c1, 3701));
										}
										else if (AiUtils.OwnItemCount(c1, 3702)!=0)
										{
											GiveItem1(c1, 3703, 1);
											DeleteItem1(c1, 3702, AiUtils.OwnItemCount(c1, 3702));
										}
										else if (AiUtils.OwnItemCount(c1, 3703)!=0)
										{
											GiveItem1(c1, 3704, 1);
											SoundEffect(c1, "ItemSound.quest_jackpot");
											DeleteItem1(c1, 3703, AiUtils.OwnItemCount(c1, 3703));
										}
										else if (AiUtils.OwnItemCount(c1, 3704)!=0)
										{
											GiveItem1(c1, 3705, 1);
											SoundEffect(c1, "ItemSound.quest_jackpot");
											DeleteItem1(c1, 3704, AiUtils.OwnItemCount(c1, 3704));
										}
										else if (AiUtils.OwnItemCount(c1, 3705)!=0)
										{
											GiveItem1(c1, 3706, 1);
											SoundEffect(c1, "ItemSound.quest_jackpot");
											DeleteItem1(c1, 3705, AiUtils.OwnItemCount(c1, 3705));
										}
										else if (AiUtils.OwnItemCount(c1, 3706)!=0)
										{
											GiveItem1(c1, 3707, 1);
											SoundEffect(c1, "ItemSound.quest_jackpot");
											DeleteItem1(c1, 3706, AiUtils.OwnItemCount(c1, 3706));
										}
									}
								}
								else if (AiUtils.OwnItemCount(c1, 3697)!=0 && (AiUtils.OwnItemCount(c1, 3698) >= 1 || AiUtils.OwnItemCount(c1, 3699) >= 1 || AiUtils.OwnItemCount(c1, 3700) >= 1 || AiUtils.OwnItemCount(c1, 3701) >= 1 || AiUtils.OwnItemCount(c1, 3702) >= 1 || AiUtils.OwnItemCount(c1, 3703) >= 1 || AiUtils.OwnItemCount(c1, 3704) >= 1 || AiUtils.OwnItemCount(c1, 3705) >= 1 || AiUtils.OwnItemCount(c1, 3706) >= 1))
								{
									DeleteItem1(c1, 3698, AiUtils.OwnItemCount(c1, 3698));
									DeleteItem1(c1, 3699, AiUtils.OwnItemCount(c1, 3699));
									DeleteItem1(c1, 3700, AiUtils.OwnItemCount(c1, 3700));
									DeleteItem1(c1, 3701, AiUtils.OwnItemCount(c1, 3701));
									DeleteItem1(c1, 3702, AiUtils.OwnItemCount(c1, 3702));
									DeleteItem1(c1, 3703, AiUtils.OwnItemCount(c1, 3703));
									DeleteItem1(c1, 3704, AiUtils.OwnItemCount(c1, 3704));
									DeleteItem1(c1, 3705, AiUtils.OwnItemCount(c1, 3705));
									DeleteItem1(c1, 3706, AiUtils.OwnItemCount(c1, 3706));
									GiveItem1(c1, 3708, 1);
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
			if (code_info.getCode() == 8)
			{
				SetCurrentQuestID(186);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(100) < 50 && AiUtils.OwnItemCount(target, 10367) < 1)
					{
						GiveItem1(target, 10367, 1);
						SoundEffect(target, "ItemSound.quest_middle");
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}