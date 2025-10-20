package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class tyrant_kingpin extends warrior_aggressive
{
	public tyrant_kingpin(final NpcInstance actor){super(actor);}

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
		target = attacker;
		if (AiUtils.HaveMemo(target, 334)!=0 && AiUtils.OwnItemCount(target, 3680)!=0 && AiUtils.OwnItemCount(target, 3681)!=0 && AiUtils.OwnItemCount(target, 3690) == 0)
		{
			random1_list.SetInfo(3, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 422)!=0 && AiUtils.GetMemoState(target, 422) == 7 && AiUtils.OwnItemCount(target, 4328) < 1)
		{
			random1_list.SetInfo(4, target);
		}
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
						if (AiUtils.HaveMemo(c1, 219)!=0 && AiUtils.OwnItemCount(c1, 3177)!=0 && AiUtils.OwnItemCount(c1, 3181) < 10)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3181) == 9)
								{
									GiveItem1(c1, 3181, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3178) >= 10 && AiUtils.OwnItemCount(c1, 3179) >= 10 && AiUtils.OwnItemCount(c1, 3180) >= 10 && AiUtils.OwnItemCount(c1, 3182) >= 10)
									{
										SetFlagJournal(c1, 219, 7);
										ShowQuestMark(c1, 219);
									}
								}
								else
								{
									GiveItem1(c1, 3181, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(220);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 220)!=0 && AiUtils.OwnItemCount(c1, 3204)!=0 && AiUtils.OwnItemCount(c1, 3206) < 10)
						{
							if (AiUtils.Rand(20) < 20)
							{
								if (AiUtils.OwnItemCount(c1, 3206) == 9)
								{
									GiveItem1(c1, 3206, 1);
									SoundEffect(c1, "Itemsound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3205) >= 10 && AiUtils.OwnItemCount(c1, 3207) >= 10)
									{
										SetFlagJournal(c1, 220, 2);
										ShowQuestMark(c1, 220);
									}
								}
								else
								{
									GiveItem1(c1, 3206, 1);
									SoundEffect(c1, "Itemsound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 2:
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
						if (AiUtils.HaveMemo(c1, 230)!=0 && AiUtils.OwnItemCount(c1, 3352) == 0 && AiUtils.OwnItemCount(c1, 3350)!=0 && AiUtils.OwnItemCount(c1, 3343) < 30)
						{
							if (AiUtils.Rand(10) < 10)
							{
								if (AiUtils.OwnItemCount(c1, 3343) >= 27)
								{
									GiveItem1(c1, 3343, 3);
									SoundEffect(c1, "Itemsound.quest_middle");
								}
								else
								{
									GiveItem1(c1, 3343, 3);
									SoundEffect(c1, "Itemsound.quest_itemget");
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
				case 3:
				{
					SetCurrentQuestID(334);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(10) == 0)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 3690, 1);
							if (AiUtils.OwnItemCount(target, 3684) >= 1 && AiUtils.OwnItemCount(target, 3685) >= 1 && AiUtils.OwnItemCount(target, 3686) >= 1 && AiUtils.OwnItemCount(target, 3687) >= 1 && AiUtils.OwnItemCount(target, 3688) >= 1 && AiUtils.OwnItemCount(target, 3689) >= 1 && AiUtils.OwnItemCount(target, 3689) >= 1 && AiUtils.OwnItemCount(target, 3691) >= 1)
							{
								SetJournal(target, 334, 4);
								ShowQuestMark(target, 334);
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
				case 4:
				{
					SetCurrentQuestID(422);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						GiveItem1(target, 4328, 1);
						SoundEffect(target, "ItemSound.quest_middle");
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}