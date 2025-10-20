package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class karul_bugbear extends warrior_passive_physicalspecial
{
	public karul_bugbear(final NpcInstance actor){super(actor);}

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
					SetCurrentQuestID(219);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 219)!=0 && AiUtils.OwnItemCount(c1, 3190)!=0 && AiUtils.OwnItemCount(c1, 3192)!=0 && AiUtils.OwnItemCount(c1, 3198) == 0 && AiUtils.OwnItemCount(c1, 3193)!=0 && AiUtils.OwnItemCount(c1, 3195) < 1)
						{
							if (AiUtils.OwnItemCount(c1, 3195) >= 0)
							{
								GiveItem1(c1, 3195, 1);
								SoundEffect(c1, "Itemsound.quest_middle");
							}
							else
							{
								GiveItem1(c1, 3195, 1);
								SoundEffect(c1, "Itemsound.quest_itemget");
							}
						}
					}
					break;
				}
				case 1:
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
						if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3352) == 0 && AiUtils.OwnItemCount(c1, 3348)!=0 && AiUtils.OwnItemCount(c1, 3339) < 30)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3339) >= 28)
								{
									GiveItem1(c1, 3339, 2);
									SoundEffect(c1, "Itemsound.quest_middle");
								}
								else
								{
									GiveItem1(c1, 3339, 2);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
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
							if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3737)!=0 && AiUtils.OwnItemCount(c1, 3779) < 30)
							{
								if (AiUtils.Rand(100) < 60)
								{
									GiveItem1(c1, 3779, 1);
									if (AiUtils.OwnItemCount(c1, 3779) >= 30)
									{
										SoundEffect(c1, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
								}
							}
							if (AiUtils.HaveMemo(c1, 335)!=0 && AiUtils.OwnItemCount(c1, 3747)!=0 && AiUtils.OwnItemCount(c1, 3789) < 1)
							{
								if (AiUtils.Rand(10) < 2)
								{
									CreateOnePrivate(27164, "karul_chief_orooto", 0, 1);
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