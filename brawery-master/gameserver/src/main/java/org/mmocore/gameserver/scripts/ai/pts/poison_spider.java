package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class poison_spider extends warrior_passive_casting_curse
{
	public poison_spider(final NpcInstance actor){super(actor);}

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

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 324) == 1 && AiUtils.OwnItemCount(target, 1077) < 10)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		always_list.SetInfo(3, target);
		target = attacker;
		always_list.SetInfo(4, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 62) == 1 && AiUtils.GetMemoState(target, 62) == 3 && AiUtils.OwnItemCount(target, 9750) < 10)
		{
			random1_list.SetInfo(5, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 1:
				{
					SetCurrentQuestID(410);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 410) == 1 && AiUtils.OwnItemCount(c_quest0, 1240) == 1 && i_quest0 == 1 && AiUtils.OwnItemCount(c_quest0, 1241) < 1)
						{
							GiveItem1(c_quest0, 1241, 1);
							if (AiUtils.OwnItemCount(c_quest0, 1242) >= 5)
							{
								SetFlagJournal(c_quest0, 410, 5);
								ShowQuestMark(c_quest0, 410);
							}
							SoundEffect(c_quest0, "ItemSound.quest_middle");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(401);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest2, 401)!=0 && AiUtils.OwnItemCount(c_quest2, 1144) < 20 && i_quest2 == 1)
						{
							GiveItem1(c_quest2, 1144, 1);
							if (AiUtils.OwnItemCount(c_quest2, 1144) >= 19)
							{
								SoundEffect(c_quest2, "ItemSound.quest_middle");
								SetFlagJournal(c_quest2, 401, 6);
								ShowQuestMark(c_quest2, 401);
							}
							else
							{
								SoundEffect(c_quest2, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(402);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 402)!=0 && AiUtils.OwnItemCount(c_quest0, 1172) > 0 && AiUtils.OwnItemCount(c_quest0, 1173) < 20 && i_quest0 == 1)
						{
							GiveItem1(c_quest0, 1173, 1);
							if (AiUtils.OwnItemCount(c_quest0, 1173) == 20)
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
				case 4:
				{
					SetCurrentQuestID(416);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 416) == 1 && AiUtils.OwnItemCount(c_quest0, 1627) == 1 && AiUtils.OwnItemCount(c_quest0, 1628) == 0 && AiUtils.OwnItemCount(c_quest0, 1629) <= 8 && i_quest0 == 1)
						{
							i0 = AiUtils.Rand(10);
							if (AiUtils.OwnItemCount(c_quest0, 1629) == 5 && i0 < 1)
							{
								DeleteItem1(c_quest0, 1629, AiUtils.OwnItemCount(c_quest0, 1629));
								CreateOnePrivate(27056, "durka_spirit", 0, 1);
								SoundEffect(c_quest0, "Itemsound.quest_before_battle");
							}
							else if (AiUtils.OwnItemCount(c_quest0, 1629) == 6 && i0 < 2)
							{
								DeleteItem1(c_quest0, 1629, AiUtils.OwnItemCount(c_quest0, 1629));
								SoundEffect(c_quest0, "Itemsound.quest_before_battle");
								CreateOnePrivate(27056, "durka_spirit", 0, 1);
							}
							else if (AiUtils.OwnItemCount(c_quest0, 1629) == 7 && i0 < 2)
							{
								DeleteItem1(c_quest0, 1629, AiUtils.OwnItemCount(c_quest0, 1629));
								SoundEffect(c_quest0, "Itemsound.quest_before_battle");
								CreateOnePrivate(27056, "durka_spirit", 0, 1);
							}
							else if (AiUtils.OwnItemCount(c_quest0, 1629) >= 8)
							{
								CreateOnePrivate(27056, "durka_spirit", 0, 1);
								SoundEffect(c_quest0, "Itemsound.quest_before_battle");
								DeleteItem1(c_quest0, 1629, AiUtils.OwnItemCount(c_quest0, 1629));
							}
							else
							{
								GiveItem1(c_quest0, 1629, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
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
				case 0:
				{
					SetCurrentQuestID(324);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 29)
						{
							GiveItem1(target, 1077, 1);
							if (AiUtils.OwnItemCount(target, 1077) >= 9)
							{
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 324, 2);
								ShowQuestMark(target, 324);
							}
							else
							{
								SoundEffect(target, "ItemSound.quest_itemget");
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(62);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (AiUtils.OwnItemCount(target, 9750) < 10)
						{
							SoundEffect(target, "ItemSound.quest_itemget");
							GiveItem1(target, 9750, 1);
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int weapon_class_id = 0;

		SetCurrentQuestID(410);
		if (AiUtils.HaveMemo(attacker, 410)!=0)
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
		SetCurrentQuestID(401);
		if (AiUtils.HaveMemo(attacker, 401)!=0)
		{
			switch (i_quest2)
			{
				case 0:
				{
					c_quest2 = attacker;
					if (c_quest2.getWeaponClass() != 1142)
					{
						i_quest2 = 2;
					}
					else
					{
						i_quest2 = 1;
					}
					break;
				}
				case 1:
				{
					if (c_quest2.getWeaponClass() != 1142)
					{
						i_quest2 = 2;
					}
					if (c_quest2 != attacker)
					{
						i_quest2 = 2;
					}
					break;
				}
				case 2:
				{
					break;
				}
			}
		}
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