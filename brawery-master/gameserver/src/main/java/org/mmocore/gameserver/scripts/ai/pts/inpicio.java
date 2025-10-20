package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class inpicio extends warrior_passive_casting_curse
{
	public inpicio(final NpcInstance actor){super(actor);}

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
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		target = attacker;
		always_list.SetInfo(1, target);
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 134) >= 1 && AiUtils.GetMemoState(target, 134) == 3 && AiUtils.OwnItemCount(target, 10337) < 3)
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
					SetCurrentQuestID(221);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 221)!=0 && AiUtils.OwnItemCount(c1, 3240) == 1 && AiUtils.OwnItemCount(c1, 3272) == 1 && AiUtils.OwnItemCount(c1, 3270) == 0 && AiUtils.OwnItemCount(c1, 3271) == 0)
						{
							if (AiUtils.OwnItemCount(c1, 3274) < 10 && AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 3274, 1);
								if (AiUtils.OwnItemCount(c1, 3274) >= 9)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									if (AiUtils.OwnItemCount(c1, 3273) >= 20 && AiUtils.OwnItemCount(c1, 3275) >= 10)
									{
										SetFlagJournal(c1, 221, 8);
										ShowQuestMark(c1, 221);
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
				case 1:
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
						if (AiUtils.HaveMemo(c1, 228)!=0 && AiUtils.OwnItemCount(c1, 2847) == 1 && AiUtils.OwnItemCount(c1, 2862) == 1 && AiUtils.OwnItemCount(c1, 2848) < 20)
						{
							if (AiUtils.Rand(100) < 100)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 2848, 1);
								if (AiUtils.OwnItemCount(c1, 2848) >= 20)
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
				case 2:
				{
					SetCurrentQuestID(420);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c1 = GetLastAttacker();
						if (c1.getPlayer() !=null)
						{
							c1 = c1.getPlayer();
						}
						if (AiUtils.HaveMemo(c1, 420) == 1 && ((AiUtils.OwnItemCount(c1, 3818) == 1 && AiUtils.OwnItemCount(c1, 3820) < 10) || (AiUtils.OwnItemCount(c1, 3819) == 1 && AiUtils.OwnItemCount(c1, 3820) < 20)))
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								if (AiUtils.Rand(100) < 30)
								{
									i0 = 19;
									if (AiUtils.OwnItemCount(c1, 3818) == 1)
									{
										i0 = 9;
									}
									c1.quest_last_reward_time = GetCurrentTick();
									if (AiUtils.OwnItemCount(c1, 3820) >= i0)
									{
										SoundEffect(c1, "ItemSound.quest_middle");
									}
									else
									{
										SoundEffect(c1, "ItemSound.quest_itemget");
									}
									GiveItem1(c1, 3820, 1);
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
			if (code_info.getCode() == 3)
			{
				SetCurrentQuestID(134);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i0 = AiUtils.Rand(100);
					i1 = AiUtils.Rand(100);
					if (AiUtils.OwnItemCount(target, 10336) >= 1)
					{
						if (i1 < 100)
						{
							CreateOnePrivateEx(27339, "pagan_of_cruma", 0, 0, AiUtils.FloatToInt(actor.getX()) + 20, AiUtils.FloatToInt(actor.getY()) + 20, AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), actor.getObjectId());
							DeleteItem1(target, 10336, 1);
						}
						else
						{
							DeleteItem1(target, 10336, 1);
						}
					}
					else if (i0 < 83)
					{
						GiveItem1(target, 10335, 1);
						SoundEffect(target, "ItemSound.quest_itemget");
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}