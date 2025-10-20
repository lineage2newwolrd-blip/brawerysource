package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class scarlet_salamander extends warrior_pa_casting_3skill_magical2
{
	public scarlet_salamander(final NpcInstance actor){super(actor);}

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
						if (AiUtils.HaveMemo(c_quest0, 415) == 1 && AiUtils.OwnItemCount(c_quest0, 1596) == 1 && i_quest0 == 1)
						{
							if (AiUtils.OwnItemCount(c_quest0, 1602) == 4)
							{
								DeleteItem1(c_quest0, 1602, AiUtils.OwnItemCount(c_quest0, 1602));
								DeleteItem1(c_quest0, 1596, AiUtils.OwnItemCount(c_quest0, 1596));
								GiveItem1(c_quest0, 1599, 1);
								SetFlagJournal(c_quest0, 415, 7);
								ShowQuestMark(c_quest0, 415);
								SoundEffect(c_quest0, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c_quest0, 1602, 1);
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(416);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 416) == 1 && AiUtils.OwnItemCount(c_quest0, 1616) == 1 && AiUtils.OwnItemCount(c_quest0, 1619) < 1 && i_quest0 == 1)
						{
							if (AiUtils.OwnItemCount(c_quest0, 1617) >= 1 && AiUtils.OwnItemCount(c_quest0, 1618) >= 1 && AiUtils.OwnItemCount(c_quest0, 1619) < 1)
							{
								GiveItem1(c_quest0, 1619, 1);
								SetFlagJournal(c_quest0, 416, 2);
								ShowQuestMark(c_quest0, 416);
								SoundEffect(c_quest0, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c_quest0, 1619, 1);
								SoundEffect(c_quest0, "ItemSound.quest_itemget");
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
		SetCurrentQuestID(416);
		if (AiUtils.HaveMemo(attacker, 416)!=0)
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
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}