package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class harit_lizardman_warrior extends warrior_passive_casting_enchant_clan
{
	public harit_lizardman_warrior(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		int weapon_class_id = 0;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3751)!=0 && AiUtils.OwnItemCount(target, 3793) < 40)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 335)!=0 && AiUtils.OwnItemCount(target, 3751)!=0 && AiUtils.OwnItemCount(target, 3793) < 40)
		{
			random1_list.SetInfo(1, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			code_info.getCode();
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 100)
						{
							GiveItem1(target, 3793, 1);
							if (AiUtils.OwnItemCount(target, 3793) >= 40)
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
				case 1:
				{
					SetCurrentQuestID(335);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 60)
						{
							if (AiUtils.OwnItemCount(target, 3697)!=0)
							{
								if (AiUtils.OwnItemCount(target, 3698)!=0)
								{
									GiveItem1(target, 3699, 1);
									DeleteItem1(target, 3698, AiUtils.OwnItemCount(target, 3698));
								}
								else if (AiUtils.OwnItemCount(target, 3699)!=0)
								{
									GiveItem1(target, 3700, 1);
									DeleteItem1(target, 3699, AiUtils.OwnItemCount(target, 3699));
								}
								else if (AiUtils.OwnItemCount(target, 3700)!=0)
								{
									GiveItem1(target, 3701, 1);
									DeleteItem1(target, 3700, AiUtils.OwnItemCount(target, 3700));
								}
								else if (AiUtils.OwnItemCount(target, 3701)!=0)
								{
									GiveItem1(target, 3702, 1);
									DeleteItem1(target, 3701, AiUtils.OwnItemCount(target, 3701));
								}
								else if (AiUtils.OwnItemCount(target, 3702)!=0)
								{
									GiveItem1(target, 3703, 1);
									DeleteItem1(target, 3702, AiUtils.OwnItemCount(target, 3702));
								}
								else if (AiUtils.OwnItemCount(target, 3703)!=0)
								{
									GiveItem1(target, 3704, 1);
									SoundEffect(target, "ItemSound.quest_jackpot");
									DeleteItem1(target, 3703, AiUtils.OwnItemCount(target, 3703));
								}
								else if (AiUtils.OwnItemCount(target, 3704)!=0)
								{
									GiveItem1(target, 3705, 1);
									SoundEffect(target, "ItemSound.quest_jackpot");
									DeleteItem1(target, 3704, AiUtils.OwnItemCount(target, 3704));
								}
								else if (AiUtils.OwnItemCount(target, 3705)!=0)
								{
									GiveItem1(target, 3706, 1);
									SoundEffect(target, "ItemSound.quest_jackpot");
									DeleteItem1(target, 3705, AiUtils.OwnItemCount(target, 3705));
								}
								else if (AiUtils.OwnItemCount(target, 3706)!=0)
								{
									GiveItem1(target, 3707, 1);
									SoundEffect(target, "ItemSound.quest_jackpot");
									DeleteItem1(target, 3706, AiUtils.OwnItemCount(target, 3706));
								}
							}
						}
						else if (AiUtils.OwnItemCount(target, 3697)!=0 && (AiUtils.OwnItemCount(target, 3698) >= 1 || AiUtils.OwnItemCount(target, 3699) >= 1 || AiUtils.OwnItemCount(target, 3700) >= 1 || AiUtils.OwnItemCount(target, 3701) >= 1 || AiUtils.OwnItemCount(target, 3702) >= 1 || AiUtils.OwnItemCount(target, 3703) >= 1 || AiUtils.OwnItemCount(target, 3704) >= 1 || AiUtils.OwnItemCount(target, 3705) >= 1 || AiUtils.OwnItemCount(target, 3706) >= 1))
						{
							DeleteItem1(target, 3698, AiUtils.OwnItemCount(target, 3698));
							DeleteItem1(target, 3699, AiUtils.OwnItemCount(target, 3699));
							DeleteItem1(target, 3700, AiUtils.OwnItemCount(target, 3700));
							DeleteItem1(target, 3701, AiUtils.OwnItemCount(target, 3701));
							DeleteItem1(target, 3702, AiUtils.OwnItemCount(target, 3702));
							DeleteItem1(target, 3703, AiUtils.OwnItemCount(target, 3703));
							DeleteItem1(target, 3704, AiUtils.OwnItemCount(target, 3704));
							DeleteItem1(target, 3705, AiUtils.OwnItemCount(target, 3705));
							DeleteItem1(target, 3706, AiUtils.OwnItemCount(target, 3706));
							GiveItem1(target, 3708, 1);
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 66)
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