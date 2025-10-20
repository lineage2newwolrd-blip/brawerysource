package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class q_black_leopard extends warrior_passive
{
	public q_black_leopard(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 416) == 1 && AiUtils.GetMemoState(target, 416) >= 102 && AiUtils.GetMemoState(target, 416) <= 107)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 416) == 1 && AiUtils.GetMemoState(target, 416) >= 102 && AiUtils.GetMemoState(target, 416) <= 107)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 416) == 1 && AiUtils.GetMemoState(target, 416) >= 102 && AiUtils.GetMemoState(target, 416) <= 107)
				{
					random1_list.SetInfo(0, target);
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
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(416);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i1 = AiUtils.Rand(100);
					i0 = AiUtils.GetMemoState(target, 416);
					if (i0 == 102)
					{
						SetMemoState(target, 416, 103);
					}
					else if (i0 == 103)
					{
						SetMemoState(target, 416, 104);
						SetFlagJournal(target, 416, 15);
						ShowQuestMark(target, 416);
						SoundEffect(target, "ItemSound.quest_middle");
						if (target.isPlayer() && AiUtils.Rand(100) < 66)
						{
							Say(AiUtils.MakeFString(41651, target.getName(), "", "", "", ""));
						}
					}
					else if (i0 == 105)
					{
						SetFlagJournal(target, 416, 17);
						ShowQuestMark(target, 416);
						SoundEffect(target, "ItemSound.quest_middle");
						SetMemoState(target, 416, 106);
						if (target.isPlayer() && AiUtils.Rand(100) < 66)
						{
							Say(AiUtils.MakeFString(41652, target.getName(), "", "", "", ""));
						}
					}
					else if (i0 == 107)
					{
						SetFlagJournal(target, 416, 19);
						ShowQuestMark(target, 416);
						SoundEffect(target, "ItemSound.quest_middle");
						SetMemoState(target, 416, 108);
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}