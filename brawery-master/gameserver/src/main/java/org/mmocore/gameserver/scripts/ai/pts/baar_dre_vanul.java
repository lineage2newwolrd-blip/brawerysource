package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class baar_dre_vanul extends warrior_aggressive
{
	public baar_dre_vanul(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 4 && AiUtils.OwnItemCount(target, 8546) < 1)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 4 && AiUtils.OwnItemCount(target, 8546) < 1)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 415) == 1 && AiUtils.GetMemoState(target, 415) == 4 && AiUtils.OwnItemCount(target, 8546) < 1)
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
				SetCurrentQuestID(415);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (target.getWeaponType() == 5 || target.getWeaponType() == 9)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 90)
						{
							GiveItem1(target, 8546, 1);
							SetFlagJournal(target, 415, 18);
							ShowQuestMark(target, 415);
							SoundEffect(target, "ItemSound.quest_middle");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}