package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class harpy extends warrior_aggressive_casting_curse
{
	public harpy(final NpcInstance actor){super(actor);}

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
							if (i0 < 59)
							{
								GiveItem1(c1, 1452, 1);
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
						if (AiUtils.HaveMemo(c1, 223)!=0 && AiUtils.OwnItemCount(c1, 3281)!=0 && AiUtils.OwnItemCount(c1, 3287) < 30)
						{
							if (AiUtils.Rand(2) <= 1)
							{
								if (AiUtils.OwnItemCount(c1, 3287) >= 28)
								{
									GiveItem1(c1, 3287, 2);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3288) >= 30 && AiUtils.OwnItemCount(c1, 3289) >= 30)
									{
										SetFlagJournal(c1, 223, 7);
										ShowQuestMark(c1, 223);
									}
								}
								else
								{
									GiveItem1(c1, 3287, 2);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
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
						if (AiUtils.HaveMemo(c1, 218)!=0 && AiUtils.OwnItemCount(c1, 3144) == 1 && AiUtils.OwnItemCount(c1, 3153) == 1 && AiUtils.OwnItemCount(c1, 3165) < 20)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3165, 4);
								if (AiUtils.OwnItemCount(c1, 3165) >= 16)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3164) >= 20)
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
				case 3:
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
						if (AiUtils.HaveMemo(c1, 228)!=0 && AiUtils.OwnItemCount(c1, 2847) == 1 && AiUtils.OwnItemCount(c1, 2861) == 1 && AiUtils.OwnItemCount(c1, 2850) < 20)
						{
							c1.quest_last_reward_time = GetCurrentTick();
							GiveItem1(c1, 2850, 1);
							if (AiUtils.OwnItemCount(c1, 2850) >= 20)
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

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(223);
		switch (i_quest0)
		{
			case 0:
			{
				c_quest0 = attacker;
				if (AiUtils.HaveMemo(c_quest0, 223)!=0 && AiUtils.OwnItemCount(c_quest0, 3281)!=0 && AiUtils.OwnItemCount(c_quest0, 3287) < 30)
				{
					if (AiUtils.Rand(2) == 1)
					{
						if (AiUtils.Rand(10) < 7)
						{
							CreateOnePrivate(27088, "harpy_martriarch", 0, 1);
						}
						else
						{
							CreateOnePrivate(27088, "harpy_martriarch", 0, 1);
							CreateOnePrivate(27088, "harpy_martriarch", 0, 1);
						}
					}
				}
				i_quest0 = 1;
				break;
			}
			case 1:
			{
				i_quest0 = 2;
				break;
			}
			case 2:
			{
				break;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}