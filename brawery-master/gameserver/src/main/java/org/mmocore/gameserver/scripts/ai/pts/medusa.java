package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class medusa extends warrior_aggressive
{
	public medusa(final NpcInstance actor){super(actor);}

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
		always_list.SetInfo(1, target);
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		always_list.SetInfo(3, target);
		target = attacker;
		always_list.SetInfo(4, target);
		target = attacker;
		always_list.SetInfo(5, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(331);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 331)!=0)
						{
							i0 = AiUtils.Rand(100);
							if (i0 < 61)
							{
								GiveItem1(c1, 1453, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
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
						if (AiUtils.HaveMemo(c1, 223)!=0 && AiUtils.OwnItemCount(c1, 3281)!=0 && AiUtils.OwnItemCount(c1, 3288) < 30)
						{
							if (AiUtils.Rand(2) <= 1)
							{
								if (AiUtils.OwnItemCount(c1, 3288) >= 27)
								{
									GiveItem1(c1, 3288, 3);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3287) >= 30 && AiUtils.OwnItemCount(c1, 3289) >= 30)
									{
										SetFlagJournal(c1, 223, 7);
										ShowQuestMark(c1, 223);
									}
								}
								else
								{
									GiveItem1(c1, 3288, 3);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
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
						if (AiUtils.HaveMemo(c1, 219)!=0 && AiUtils.OwnItemCount(c1, 3177)!=0 && AiUtils.OwnItemCount(c1, 3178) < 10)
						{
							if (AiUtils.Rand(2) <= 1)
							{
								if (AiUtils.OwnItemCount(c1, 3178) == 9)
								{
									GiveItem1(c1, 3178, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3179) >= 10 && AiUtils.OwnItemCount(c1, 3180) >= 10 && AiUtils.OwnItemCount(c1, 3181) >= 10 && AiUtils.OwnItemCount(c1, 3182) >= 10)
									{
										SetFlagJournal(c1, 219, 7);
										ShowQuestMark(c1, 219);
									}
								}
								else
								{
									GiveItem1(c1, 3178, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 3:
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
						if (AiUtils.HaveMemo(c1, 233)!=0 && AiUtils.OwnItemCount(c1, 2895) == 1)
						{
							i0 = AiUtils.Rand(100);
							if (i0 > 0)
							{
								if (AiUtils.OwnItemCount(c1, 2897) == 0)
								{
									GiveItem1(c1, 2897, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else if (AiUtils.OwnItemCount(c1, 2898) == 0)
								{
									GiveItem1(c1, 2898, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else if (AiUtils.OwnItemCount(c1, 2899) == 0)
								{
									GiveItem1(c1, 2899, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
								else if (AiUtils.OwnItemCount(c1, 2900) == 0)
								{
									GiveItem1(c1, 2900, 1);
									SoundEffect(c1, "ItemSound.quest_middle");
								}
							}
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(213);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 213)!=0 && AiUtils.OwnItemCount(c1, 2659) == 1 && AiUtils.OwnItemCount(c1, 2660) < 10)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2660, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2660) >= 9)
								{
									SetFlagJournal(c1, 213, 10);
									ShowQuestMark(c1, 213);
								}
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(214);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 214)!=0 && AiUtils.OwnItemCount(c1, 2705) == 1 && AiUtils.OwnItemCount(c1, 2711) == 1 && AiUtils.OwnItemCount(c1, 2715) == 1 && AiUtils.OwnItemCount(c1, 2717) < 12)
						{
							GiveItem1(c1, 2717, 1);
							if (AiUtils.OwnItemCount(c1, 2717) >= 12)
							{
								SoundEffect(c1, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(c1, "ItemSound.quest_itemget");
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
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

}