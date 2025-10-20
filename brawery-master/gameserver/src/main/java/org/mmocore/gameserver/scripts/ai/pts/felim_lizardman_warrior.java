package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class felim_lizardman_warrior extends warrior_passive
{
	public felim_lizardman_warrior(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
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
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 62) == 1 && AiUtils.GetMemoState(target, 62) == 2 && AiUtils.OwnItemCount(target, 9749) < 5)
		{
			random1_list.SetInfo(2, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(415);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 415) == 1 && AiUtils.OwnItemCount(c_quest0, 1607) == 1 && AiUtils.OwnItemCount(c_quest0, 1612) < 3 && i_quest0 == 1)
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
								GiveItem1(c_quest0, 1612, 1);
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
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
						if (AiUtils.HaveMemo(c1, 340)!=0 && AiUtils.GetMemoState(c1, 340) == 1 && AiUtils.OwnItemCount(c1, 4255) < 30)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								if (AiUtils.Rand(100) < 68)
								{
									GiveItem1(c1, 4255, 1);
									if (AiUtils.OwnItemCount(c1, 4255) >= 30)
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
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			if (code_info.getCode() == 2)
			{
				SetCurrentQuestID(62);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (AiUtils.OwnItemCount(target, 9749) < 5)
					{
						SoundEffect(target, "ItemSound.quest_itemget");
						GiveItem1(target, 9749, 1);
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

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