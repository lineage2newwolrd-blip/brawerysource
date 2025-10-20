package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class tasaba_lizardman_sham_a extends wizard_pa_ddmagic2
{
	public tasaba_lizardman_sham_a(final NpcInstance actor){super(actor);}

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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(351);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					i0 = AiUtils.Rand(20);
					if (AiUtils.OwnItemCount(c1, 4296)!=0 && AiUtils.HaveMemo(c1, 351)!=0)
					{
						if (i0 < 10)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 4297, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
								if (AiUtils.Rand(20) == 0)
								{
									GiveItem1(c1, 4298, 1);
								}
							}
						}
						else if (i0 < 15)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 4297, 2);
								SoundEffect(c1, "ItemSound.quest_itemget");
								if (AiUtils.Rand(20) == 0)
								{
									GiveItem1(c1, 4298, 1);
								}
							}
						}
						else if (AiUtils.Rand(100) < 3)
						{
							if (GetCurrentTick() - c1.quest_last_reward_time > 1)
							{
								c1.quest_last_reward_time = GetCurrentTick();
								GiveItem1(c1, 4298, 1);
								SoundEffect(c1, "ItemSound.quest_itemget");
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			if (code_info.getCode() == 1)
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
			}
		}
		super.onEvtDead(attacker);
	}

}