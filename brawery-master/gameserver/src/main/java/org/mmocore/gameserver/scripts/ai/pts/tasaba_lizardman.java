package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class tasaba_lizardman extends warrior_passive_use_bow
{
	public tasaba_lizardman(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
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
		if (AiUtils.OwnItemCount(target, 4296)!=0 && AiUtils.HaveMemo(target, 351)!=0)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 139) == 1 && AiUtils.GetMemoState(target, 139) == 3 && GetOneTimeQuestFlag(target, 139) == 0)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 139) == 1 && AiUtils.GetMemoState(target, 139) == 3 && GetOneTimeQuestFlag(target, 139) == 0)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 139) == 1 && AiUtils.GetMemoState(target, 139) == 3 && GetOneTimeQuestFlag(target, 139) == 0)
				{
					random1_list.SetInfo(1, target);
				}
			}
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
					SetCurrentQuestID(351);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (i0 < 10)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 4297, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
							if (AiUtils.Rand(20) == 0)
							{
								GiveItem1(target, 4298, 1);
							}
						}
						else if (i0 < 15)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 4297, 2);
							SoundEffect(target, "ItemSound.quest_itemget");
							if (AiUtils.Rand(20) == 0)
							{
								GiveItem1(target, 4298, 1);
							}
						}
						else if (AiUtils.Rand(100) < 4)
						{
							target.quest_last_reward_time = GetCurrentTick();
							GiveItem1(target, 4298, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(139);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						i1 = AiUtils.Rand(11);
						if (i0 < 68)
						{
							if (i1 < 10)
							{
								GiveItem1(target, 10345, 1);
							}
							else
							{
								GiveItem1(target, 10346, 1);
							}
							SoundEffect(target, "ItemSound.quest_itemget");
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
						if (AiUtils.Rand(1000) < 155)
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