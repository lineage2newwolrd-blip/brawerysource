package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class langk_lizardman_warrior extends warrior_aggressive
{
	public langk_lizardman_warrior(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(402);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.OwnItemCount(c_quest0, 1174)!=0 && AiUtils.OwnItemCount(c_quest0, 1175) < 20 && i_quest0 == 1 && AiUtils.Rand(10) < 5)
						{
							GiveItem1(c_quest0, 1175, 1);
							if (AiUtils.OwnItemCount(c_quest0, 1175) == 20)
							{
								SoundEffect(c_quest0, "ItemSound.quest_middle");
							}
							else
							{
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(415);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 415) == 1 && AiUtils.OwnItemCount(c_quest0, 1607) == 1 && AiUtils.OwnItemCount(c_quest0, 1611) < 3 && i_quest0 == 1)
						{
							if (AiUtils.OwnItemCount(c_quest0, 1610) + AiUtils.OwnItemCount(c_quest0, 1611) + AiUtils.OwnItemCount(c_quest0, 1612) + AiUtils.OwnItemCount(c_quest0, 1609) >= 11)
							{
								DeleteItem1(c_quest0, 1609, AiUtils.OwnItemCount(c_quest0, 1609));
								DeleteItem1(c_quest0, 1610, AiUtils.OwnItemCount(c_quest0, 1610));
								DeleteItem1(c_quest0, 1611, AiUtils.OwnItemCount(c_quest0, 1611));
								DeleteItem1(c_quest0, 1612, AiUtils.OwnItemCount(c_quest0, 1612));
								DeleteItem1(c_quest0, 1607, 1);
								GiveItem1(c_quest0, 1608, 1);
								SetFlagJournal(c_quest0, 415, 12);
								ShowQuestMark(c_quest0, 415);
								SoundEffect(c_quest0, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c_quest0, 1611, 1);
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(340);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 340)!=0 && AiUtils.GetMemoState(c1, 340) == 3)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								if (AiUtils.OwnItemCount(c1, 4256) == 0)
								{
									if (AiUtils.Rand(100) <= 19)
									{
										GiveItem1(c1, 4256, 1);
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
								}
								else if (AiUtils.OwnItemCount(c1, 4256) >= 1 && AiUtils.OwnItemCount(c1, 4257) == 0)
								{
									if (AiUtils.Rand(100) <= 19)
									{
										GiveItem1(c1, 4257, 1);
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
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
			code_info.getCode();
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(402);
		if (AiUtils.HaveMemo(attacker, 402)!=0)
		{
			switch (i_quest0)
			{
				case 0:
				{
					i_quest0 = 1;
					if (attacker.getPlayer() !=null)
					{
						c_quest0 = attacker.getPlayer();
					}
					else
					{
						c_quest0 = attacker;
					}
					break;
				}
				case 1:
				{
					if (attacker.getPlayer() !=null)
					{
						if (c_quest0 != attacker.getPlayer())
						{
							i_quest0 = 2;
						}
					}
					else if (c_quest0 != attacker)
					{
						i_quest0 = 2;
					}
					break;
				}
				case 2:
				{
					break;
				}
			}
		}
		SetCurrentQuestID(415);
		if (AiUtils.HaveMemo(attacker, 415)!=0)
		{
			switch (i_quest0)
			{
				case 0:
				{
					if (attacker.getWeaponType() != 5 && attacker.getWeaponType() != 9)
					{
						i_quest0 = 2;
					}
					else
					{
						i_quest0 = 1;
					}
					c_quest0 = attacker;
					break;
				}
				case 1:
				{
					if (c_quest0 != attacker || (attacker.getWeaponType() != 5 && attacker.getWeaponType() != 9))
					{
						i_quest0 = 2;
					}
					break;
				}
				case 2:
				{
					break;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}