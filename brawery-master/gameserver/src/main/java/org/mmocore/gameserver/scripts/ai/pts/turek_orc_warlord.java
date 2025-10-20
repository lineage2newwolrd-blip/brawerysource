package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class turek_orc_warlord extends warrior_aggressive_run_away_to_clan
{
	public turek_orc_warlord(final NpcInstance actor){super(actor);}

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
					SetCurrentQuestID(213);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 213)!=0 && AiUtils.OwnItemCount(c1, 2649) == 1 && AiUtils.OwnItemCount(c1, 2655) == 0)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2655, 1);
								SoundEffect(c1, "ItemSound.quest_middle");
								if (AiUtils.OwnItemCount(c1, 2654) >= 1 && AiUtils.OwnItemCount(c1, 2656) >= 1 && AiUtils.OwnItemCount(c1, 2657) >= 1)
								{
									SetFlagJournal(c1, 213, 5);
									ShowQuestMark(c1, 213);
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(327);
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
							c1 = GetLastAttacker();
							if (c1.getPlayer() !=null)
							{
								c1 = c1.getPlayer();
							}
							if (AiUtils.HaveMemo(c1, 327) == 1)
							{
								GiveItem1(c1, 1847, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
								i0 = AiUtils.Rand(100);
								if (i0 < 26)
								{
									i1 = AiUtils.Rand(100);
									if (i1 < 25)
									{
										GiveItem1(c1, 1848, 1);
									}
									else if (i1 < 50)
									{
										GiveItem1(c1, 1849, 1);
									}
									else if (i1 < 75)
									{
										GiveItem1(c1, 1850, 1);
									}
									else
									{
										GiveItem1(c1, 1851, 1);
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

}