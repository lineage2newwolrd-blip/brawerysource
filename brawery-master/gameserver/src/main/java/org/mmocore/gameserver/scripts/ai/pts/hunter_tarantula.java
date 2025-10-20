package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class hunter_tarantula extends warrior_passive
{
	public hunter_tarantula(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i5 = 0;
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
		if (AiUtils.HaveMemo(target, 296)!=0)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(417);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (AiUtils.HaveMemo(c_quest0, 417) == 1 && AiUtils.OwnItemCount(c_quest0, 1654) == 1 && AiUtils.OwnItemCount(c_quest0, 1656) < 20 && i_quest0 == 2)
						{
							if (AiUtils.Rand(2) < 2)
							{
								if (AiUtils.OwnItemCount(c_quest0, 1656) == 19)
								{
									GiveItem1(c_quest0, 1656, 1);
									SetFlagJournal(c_quest0, 417, 8);
									ShowQuestMark(c_quest0, 417);
									SoundEffect(c_quest0, "ItemSound.quest_middle");
								}
								else
								{
									GiveItem1(c_quest0, 1656, 1);
									SoundEffect(c_quest0, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(419);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						c1.quest_last_reward_time = 0;
						if (AiUtils.HaveMemo(c1, 419) == 1 && AiUtils.OwnItemCount(c1, 3422) == 1)
						{
							if (AiUtils.OwnItemCount(c1, 3427) < 50 && AiUtils.Rand(100) < 75)
							{
								GiveItem1(c1, 3427, 1);
								if (AiUtils.OwnItemCount(c1, 3427) >= 50)
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
				case 1:
				{
					SetCurrentQuestID(296);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i5 = AiUtils.Rand(100);
						if (i5 > 95)
						{
							GiveItem1(target, 1494, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else if (i5 > 45)
						{
							GiveItem1(target, 1493, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 11)
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

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		SetCurrentQuestID(417);
		switch (i_quest0)
		{
			case 0:
			{
				i_quest0 = 1;
				c_quest0 = attacker;
				if (skill_name_id / 65536 == 16646145 / 65536)
				{
					i_quest0 = 2;
				}
				break;
			}
			case 1:
			{
				if (skill_name_id / 65536 == 16646145 / 65536)
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
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}