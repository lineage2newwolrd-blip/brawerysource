package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class shrine_guard extends warrior_casting_splash
{
	public shrine_guard(final NpcInstance actor){super(actor);}

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
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 70) == 1 && target.getClassId() == 5 && AiUtils.GetMemoState(target, 70) == 12 && AiUtils.OwnItemCount(target, 7392) == 0)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 70) == 1 && target.getClassId() == 5 && AiUtils.GetMemoState(target, 70) == 12 && AiUtils.OwnItemCount(target, 7392) == 0)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 70) == 1 && target.getClassId() == 5 && AiUtils.GetMemoState(target, 70) == 12 && AiUtils.OwnItemCount(target, 7392) == 0)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 71) == 1 && target.getClassId() == 20 && AiUtils.GetMemoState(target, 71) == 12 && AiUtils.OwnItemCount(target, 7393) == 0)
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 71) == 1 && target.getClassId() == 20 && AiUtils.GetMemoState(target, 71) == 12 && AiUtils.OwnItemCount(target, 7393) == 0)
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 71) == 1 && target.getClassId() == 20 && AiUtils.GetMemoState(target, 71) == 12 && AiUtils.OwnItemCount(target, 7393) == 0)
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 72) == 1 && target.getClassId() == 21 && AiUtils.GetMemoState(target, 72) == 12 && AiUtils.OwnItemCount(target, 7394) == 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 72) == 1 && target.getClassId() == 21 && AiUtils.GetMemoState(target, 72) == 12 && AiUtils.OwnItemCount(target, 7394) == 0)
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 72) == 1 && target.getClassId() == 21 && AiUtils.GetMemoState(target, 72) == 12 && AiUtils.OwnItemCount(target, 7394) == 0)
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 73) == 1 && target.getClassId() == 2 && AiUtils.GetMemoState(target, 73) == 12 && AiUtils.OwnItemCount(target, 7395) == 0)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 73) == 1 && target.getClassId() == 2 && AiUtils.GetMemoState(target, 73) == 12 && AiUtils.OwnItemCount(target, 7395) == 0)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 73) == 1 && target.getClassId() == 2 && AiUtils.GetMemoState(target, 73) == 12 && AiUtils.OwnItemCount(target, 7395) == 0)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 74) == 1 && target.getClassId() == 3 && AiUtils.GetMemoState(target, 74) == 12 && AiUtils.OwnItemCount(target, 7396) == 0)
		{
			random1_list.SetInfo(4, target);
		}
		if (AiUtils.HaveMemo(target, 74) == 1 && target.getClassId() == 3 && AiUtils.GetMemoState(target, 74) == 12 && AiUtils.OwnItemCount(target, 7396) == 0)
		{
			random1_list.SetInfo(4, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 74) == 1 && target.getClassId() == 3 && AiUtils.GetMemoState(target, 74) == 12 && AiUtils.OwnItemCount(target, 7396) == 0)
				{
					random1_list.SetInfo(4, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 75) == 1 && target.getClassId() == 46 && AiUtils.GetMemoState(target, 75) == 12 && AiUtils.OwnItemCount(target, 7397) == 0)
		{
			random1_list.SetInfo(5, target);
		}
		if (AiUtils.HaveMemo(target, 75) == 1 && target.getClassId() == 46 && AiUtils.GetMemoState(target, 75) == 12 && AiUtils.OwnItemCount(target, 7397) == 0)
		{
			random1_list.SetInfo(5, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 75) == 1 && target.getClassId() == 46 && AiUtils.GetMemoState(target, 75) == 12 && AiUtils.OwnItemCount(target, 7397) == 0)
				{
					random1_list.SetInfo(5, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 76) == 1 && target.getClassId() == 48 && AiUtils.GetMemoState(target, 76) == 12 && AiUtils.OwnItemCount(target, 7398) == 0)
		{
			random1_list.SetInfo(6, target);
		}
		if (AiUtils.HaveMemo(target, 76) == 1 && target.getClassId() == 48 && AiUtils.GetMemoState(target, 76) == 12 && AiUtils.OwnItemCount(target, 7398) == 0)
		{
			random1_list.SetInfo(6, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 76) == 1 && target.getClassId() == 48 && AiUtils.GetMemoState(target, 76) == 12 && AiUtils.OwnItemCount(target, 7398) == 0)
				{
					random1_list.SetInfo(6, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 77) == 1 && target.getClassId() == 51 && AiUtils.GetMemoState(target, 77) == 12 && AiUtils.OwnItemCount(target, 7399) == 0)
		{
			random1_list.SetInfo(7, target);
		}
		if (AiUtils.HaveMemo(target, 77) == 1 && target.getClassId() == 51 && AiUtils.GetMemoState(target, 77) == 12 && AiUtils.OwnItemCount(target, 7399) == 0)
		{
			random1_list.SetInfo(7, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 77) == 1 && target.getClassId() == 51 && AiUtils.GetMemoState(target, 77) == 12 && AiUtils.OwnItemCount(target, 7399) == 0)
				{
					random1_list.SetInfo(7, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 78) == 1 && target.getClassId() == 52 && AiUtils.GetMemoState(target, 78) == 12 && AiUtils.OwnItemCount(target, 7400) == 0)
		{
			random1_list.SetInfo(8, target);
		}
		if (AiUtils.HaveMemo(target, 78) == 1 && target.getClassId() == 52 && AiUtils.GetMemoState(target, 78) == 12 && AiUtils.OwnItemCount(target, 7400) == 0)
		{
			random1_list.SetInfo(8, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 78) == 1 && target.getClassId() == 52 && AiUtils.GetMemoState(target, 78) == 12 && AiUtils.OwnItemCount(target, 7400) == 0)
				{
					random1_list.SetInfo(8, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 79) == 1 && target.getClassId() == 8 && AiUtils.GetMemoState(target, 79) == 12 && AiUtils.OwnItemCount(target, 7401) == 0)
		{
			random1_list.SetInfo(9, target);
		}
		if (AiUtils.HaveMemo(target, 79) == 1 && target.getClassId() == 8 && AiUtils.GetMemoState(target, 79) == 12 && AiUtils.OwnItemCount(target, 7401) == 0)
		{
			random1_list.SetInfo(9, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 79) == 1 && target.getClassId() == 8 && AiUtils.GetMemoState(target, 79) == 12 && AiUtils.OwnItemCount(target, 7401) == 0)
				{
					random1_list.SetInfo(9, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 80) == 1 && target.getClassId() == 23 && AiUtils.GetMemoState(target, 80) == 12 && AiUtils.OwnItemCount(target, 7402) == 0)
		{
			random1_list.SetInfo(10, target);
		}
		if (AiUtils.HaveMemo(target, 80) == 1 && target.getClassId() == 23 && AiUtils.GetMemoState(target, 80) == 12 && AiUtils.OwnItemCount(target, 7402) == 0)
		{
			random1_list.SetInfo(10, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 80) == 1 && target.getClassId() == 23 && AiUtils.GetMemoState(target, 80) == 12 && AiUtils.OwnItemCount(target, 7402) == 0)
				{
					random1_list.SetInfo(10, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 81) == 1 && target.getClassId() == 36 && AiUtils.GetMemoState(target, 81) == 12 && AiUtils.OwnItemCount(target, 7403) == 0)
		{
			random1_list.SetInfo(11, target);
		}
		if (AiUtils.HaveMemo(target, 81) == 1 && target.getClassId() == 36 && AiUtils.GetMemoState(target, 81) == 12 && AiUtils.OwnItemCount(target, 7403) == 0)
		{
			random1_list.SetInfo(11, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 81) == 1 && target.getClassId() == 36 && AiUtils.GetMemoState(target, 81) == 12 && AiUtils.OwnItemCount(target, 7403) == 0)
				{
					random1_list.SetInfo(11, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 82) == 1 && target.getClassId() == 9 && AiUtils.GetMemoState(target, 82) == 12 && AiUtils.OwnItemCount(target, 7404) == 0)
		{
			random1_list.SetInfo(12, target);
		}
		if (AiUtils.HaveMemo(target, 82) == 1 && target.getClassId() == 9 && AiUtils.GetMemoState(target, 82) == 12 && AiUtils.OwnItemCount(target, 7404) == 0)
		{
			random1_list.SetInfo(12, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 82) == 1 && target.getClassId() == 9 && AiUtils.GetMemoState(target, 82) == 12 && AiUtils.OwnItemCount(target, 7404) == 0)
				{
					random1_list.SetInfo(12, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 83) == 1 && target.getClassId() == 24 && AiUtils.GetMemoState(target, 83) == 12 && AiUtils.OwnItemCount(target, 7405) == 0)
		{
			random1_list.SetInfo(13, target);
		}
		if (AiUtils.HaveMemo(target, 83) == 1 && target.getClassId() == 24 && AiUtils.GetMemoState(target, 83) == 12 && AiUtils.OwnItemCount(target, 7405) == 0)
		{
			random1_list.SetInfo(13, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 83) == 1 && target.getClassId() == 24 && AiUtils.GetMemoState(target, 83) == 12 && AiUtils.OwnItemCount(target, 7405) == 0)
				{
					random1_list.SetInfo(13, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 84) == 1 && target.getClassId() == 37 && AiUtils.GetMemoState(target, 84) == 12 && AiUtils.OwnItemCount(target, 7406) == 0)
		{
			random1_list.SetInfo(14, target);
		}
		if (AiUtils.HaveMemo(target, 84) == 1 && target.getClassId() == 37 && AiUtils.GetMemoState(target, 84) == 12 && AiUtils.OwnItemCount(target, 7406) == 0)
		{
			random1_list.SetInfo(14, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 84) == 1 && target.getClassId() == 37 && AiUtils.GetMemoState(target, 84) == 12 && AiUtils.OwnItemCount(target, 7406) == 0)
				{
					random1_list.SetInfo(14, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 85) == 1 && target.getClassId() == 16 && AiUtils.GetMemoState(target, 85) == 12 && AiUtils.OwnItemCount(target, 7407) == 0)
		{
			random1_list.SetInfo(15, target);
		}
		if (AiUtils.HaveMemo(target, 85) == 1 && target.getClassId() == 16 && AiUtils.GetMemoState(target, 85) == 12 && AiUtils.OwnItemCount(target, 7407) == 0)
		{
			random1_list.SetInfo(15, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 85) == 1 && target.getClassId() == 16 && AiUtils.GetMemoState(target, 85) == 12 && AiUtils.OwnItemCount(target, 7407) == 0)
				{
					random1_list.SetInfo(15, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 86) == 1 && target.getClassId() == 17 && AiUtils.GetMemoState(target, 86) == 12 && AiUtils.OwnItemCount(target, 7408) == 0)
		{
			random1_list.SetInfo(16, target);
		}
		if (AiUtils.HaveMemo(target, 86) == 1 && target.getClassId() == 17 && AiUtils.GetMemoState(target, 86) == 12 && AiUtils.OwnItemCount(target, 7408) == 0)
		{
			random1_list.SetInfo(16, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 86) == 1 && target.getClassId() == 17 && AiUtils.GetMemoState(target, 86) == 12 && AiUtils.OwnItemCount(target, 7408) == 0)
				{
					random1_list.SetInfo(16, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 87) == 1 && target.getClassId() == 30 && AiUtils.GetMemoState(target, 87) == 12 && AiUtils.OwnItemCount(target, 7409) == 0)
		{
			random1_list.SetInfo(17, target);
		}
		if (AiUtils.HaveMemo(target, 87) == 1 && target.getClassId() == 30 && AiUtils.GetMemoState(target, 87) == 12 && AiUtils.OwnItemCount(target, 7409) == 0)
		{
			random1_list.SetInfo(17, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 87) == 1 && target.getClassId() == 30 && AiUtils.GetMemoState(target, 87) == 12 && AiUtils.OwnItemCount(target, 7409) == 0)
				{
					random1_list.SetInfo(17, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 88) == 1 && target.getClassId() == 12 && AiUtils.GetMemoState(target, 88) == 12 && AiUtils.OwnItemCount(target, 7410) == 0)
		{
			random1_list.SetInfo(18, target);
		}
		if (AiUtils.HaveMemo(target, 88) == 1 && target.getClassId() == 12 && AiUtils.GetMemoState(target, 88) == 12 && AiUtils.OwnItemCount(target, 7410) == 0)
		{
			random1_list.SetInfo(18, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 88) == 1 && target.getClassId() == 12 && AiUtils.GetMemoState(target, 88) == 12 && AiUtils.OwnItemCount(target, 7410) == 0)
				{
					random1_list.SetInfo(18, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 89) == 1 && target.getClassId() == 27 && AiUtils.GetMemoState(target, 89) == 12 && AiUtils.OwnItemCount(target, 7411) == 0)
		{
			random1_list.SetInfo(19, target);
		}
		if (AiUtils.HaveMemo(target, 89) == 1 && target.getClassId() == 27 && AiUtils.GetMemoState(target, 89) == 12 && AiUtils.OwnItemCount(target, 7411) == 0)
		{
			random1_list.SetInfo(19, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 89) == 1 && target.getClassId() == 27 && AiUtils.GetMemoState(target, 89) == 12 && AiUtils.OwnItemCount(target, 7411) == 0)
				{
					random1_list.SetInfo(19, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 90) == 1 && target.getClassId() == 40 && AiUtils.GetMemoState(target, 90) == 12 && AiUtils.OwnItemCount(target, 7412) == 0)
		{
			random1_list.SetInfo(20, target);
		}
		if (AiUtils.HaveMemo(target, 90) == 1 && target.getClassId() == 40 && AiUtils.GetMemoState(target, 90) == 12 && AiUtils.OwnItemCount(target, 7412) == 0)
		{
			random1_list.SetInfo(20, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 90) == 1 && target.getClassId() == 40 && AiUtils.GetMemoState(target, 90) == 12 && AiUtils.OwnItemCount(target, 7412) == 0)
				{
					random1_list.SetInfo(20, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 91) == 1 && target.getClassId() == 14 && AiUtils.GetMemoState(target, 91) == 12 && AiUtils.OwnItemCount(target, 7413) == 0)
		{
			random1_list.SetInfo(21, target);
		}
		if (AiUtils.HaveMemo(target, 91) == 1 && target.getClassId() == 14 && AiUtils.GetMemoState(target, 91) == 12 && AiUtils.OwnItemCount(target, 7413) == 0)
		{
			random1_list.SetInfo(21, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 91) == 1 && target.getClassId() == 14 && AiUtils.GetMemoState(target, 91) == 12 && AiUtils.OwnItemCount(target, 7413) == 0)
				{
					random1_list.SetInfo(21, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 92) == 1 && target.getClassId() == 28 && AiUtils.GetMemoState(target, 92) == 12 && AiUtils.OwnItemCount(target, 7414) == 0)
		{
			random1_list.SetInfo(22, target);
		}
		if (AiUtils.HaveMemo(target, 92) == 1 && target.getClassId() == 28 && AiUtils.GetMemoState(target, 92) == 12 && AiUtils.OwnItemCount(target, 7414) == 0)
		{
			random1_list.SetInfo(22, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 92) == 1 && target.getClassId() == 28 && AiUtils.GetMemoState(target, 92) == 12 && AiUtils.OwnItemCount(target, 7414) == 0)
				{
					random1_list.SetInfo(22, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 93) == 1 && target.getClassId() == 41 && AiUtils.GetMemoState(target, 93) == 12 && AiUtils.OwnItemCount(target, 7415) == 0)
		{
			random1_list.SetInfo(23, target);
		}
		if (AiUtils.HaveMemo(target, 93) == 1 && target.getClassId() == 41 && AiUtils.GetMemoState(target, 93) == 12 && AiUtils.OwnItemCount(target, 7415) == 0)
		{
			random1_list.SetInfo(23, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 93) == 1 && target.getClassId() == 41 && AiUtils.GetMemoState(target, 93) == 12 && AiUtils.OwnItemCount(target, 7415) == 0)
				{
					random1_list.SetInfo(23, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 94) == 1 && target.getClassId() == 13 && AiUtils.GetMemoState(target, 94) == 12 && AiUtils.OwnItemCount(target, 7416) == 0)
		{
			random1_list.SetInfo(24, target);
		}
		if (AiUtils.HaveMemo(target, 94) == 1 && target.getClassId() == 13 && AiUtils.GetMemoState(target, 94) == 12 && AiUtils.OwnItemCount(target, 7416) == 0)
		{
			random1_list.SetInfo(24, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 94) == 1 && target.getClassId() == 13 && AiUtils.GetMemoState(target, 94) == 12 && AiUtils.OwnItemCount(target, 7416) == 0)
				{
					random1_list.SetInfo(24, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 95) == 1 && target.getClassId() == 6 && AiUtils.GetMemoState(target, 95) == 12 && AiUtils.OwnItemCount(target, 7417) == 0)
		{
			random1_list.SetInfo(25, target);
		}
		if (AiUtils.HaveMemo(target, 95) == 1 && target.getClassId() == 6 && AiUtils.GetMemoState(target, 95) == 12 && AiUtils.OwnItemCount(target, 7417) == 0)
		{
			random1_list.SetInfo(25, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 95) == 1 && target.getClassId() == 6 && AiUtils.GetMemoState(target, 95) == 12 && AiUtils.OwnItemCount(target, 7417) == 0)
				{
					random1_list.SetInfo(25, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 96) == 1 && target.getClassId() == 34 && AiUtils.GetMemoState(target, 96) == 12 && AiUtils.OwnItemCount(target, 7418) == 0)
		{
			random1_list.SetInfo(26, target);
		}
		if (AiUtils.HaveMemo(target, 96) == 1 && target.getClassId() == 34 && AiUtils.GetMemoState(target, 96) == 12 && AiUtils.OwnItemCount(target, 7418) == 0)
		{
			random1_list.SetInfo(26, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 96) == 1 && target.getClassId() == 34 && AiUtils.GetMemoState(target, 96) == 12 && AiUtils.OwnItemCount(target, 7418) == 0)
				{
					random1_list.SetInfo(26, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 97) == 1 && target.getClassId() == 33 && AiUtils.GetMemoState(target, 97) == 12 && AiUtils.OwnItemCount(target, 7419) == 0)
		{
			random1_list.SetInfo(27, target);
		}
		if (AiUtils.HaveMemo(target, 97) == 1 && target.getClassId() == 33 && AiUtils.GetMemoState(target, 97) == 12 && AiUtils.OwnItemCount(target, 7419) == 0)
		{
			random1_list.SetInfo(27, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 97) == 1 && target.getClassId() == 33 && AiUtils.GetMemoState(target, 97) == 12 && AiUtils.OwnItemCount(target, 7419) == 0)
				{
					random1_list.SetInfo(27, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 98) == 1 && target.getClassId() == 43 && AiUtils.GetMemoState(target, 98) == 12 && AiUtils.OwnItemCount(target, 7420) == 0)
		{
			random1_list.SetInfo(28, target);
		}
		if (AiUtils.HaveMemo(target, 98) == 1 && target.getClassId() == 43 && AiUtils.GetMemoState(target, 98) == 12 && AiUtils.OwnItemCount(target, 7420) == 0)
		{
			random1_list.SetInfo(28, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 98) == 1 && target.getClassId() == 43 && AiUtils.GetMemoState(target, 98) == 12 && AiUtils.OwnItemCount(target, 7420) == 0)
				{
					random1_list.SetInfo(28, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 99) == 1 && target.getClassId() == 55 && AiUtils.GetMemoState(target, 99) == 12 && AiUtils.OwnItemCount(target, 7421) == 0)
		{
			random1_list.SetInfo(29, target);
		}
		if (AiUtils.HaveMemo(target, 99) == 1 && target.getClassId() == 55 && AiUtils.GetMemoState(target, 99) == 12 && AiUtils.OwnItemCount(target, 7421) == 0)
		{
			random1_list.SetInfo(29, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 99) == 1 && target.getClassId() == 55 && AiUtils.GetMemoState(target, 99) == 12 && AiUtils.OwnItemCount(target, 7421) == 0)
				{
					random1_list.SetInfo(29, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 100) == 1 && target.getClassId() == 57 && AiUtils.GetMemoState(target, 100) == 12 && AiUtils.OwnItemCount(target, 7422) == 0)
		{
			random1_list.SetInfo(30, target);
		}
		if (AiUtils.HaveMemo(target, 100) == 1 && target.getClassId() == 57 && AiUtils.GetMemoState(target, 100) == 12 && AiUtils.OwnItemCount(target, 7422) == 0)
		{
			random1_list.SetInfo(30, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 100) == 1 && target.getClassId() == 57 && AiUtils.GetMemoState(target, 100) == 12 && AiUtils.OwnItemCount(target, 7422) == 0)
				{
					random1_list.SetInfo(30, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 67) == 1 && target.getClassId() == 127 && AiUtils.GetMemoState(target, 67) == 12 && AiUtils.OwnItemCount(target, 9734) == 0)
		{
			random1_list.SetInfo(31, target);
		}
		if (AiUtils.HaveMemo(target, 67) == 1 && target.getClassId() == 127 && AiUtils.GetMemoState(target, 67) == 12 && AiUtils.OwnItemCount(target, 9734) == 0)
		{
			random1_list.SetInfo(31, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 67) == 1 && target.getClassId() == 127 && AiUtils.GetMemoState(target, 67) == 12 && AiUtils.OwnItemCount(target, 9734) == 0)
				{
					random1_list.SetInfo(31, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 69) == 1 && target.getClassId() == 130 && AiUtils.GetMemoState(target, 69) == 12 && AiUtils.OwnItemCount(target, 9736) == 0)
		{
			random1_list.SetInfo(32, target);
		}
		if (AiUtils.HaveMemo(target, 69) == 1 && target.getClassId() == 130 && AiUtils.GetMemoState(target, 69) == 12 && AiUtils.OwnItemCount(target, 9736) == 0)
		{
			random1_list.SetInfo(32, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 69) == 1 && target.getClassId() == 130 && AiUtils.GetMemoState(target, 69) == 12 && AiUtils.OwnItemCount(target, 9736) == 0)
				{
					random1_list.SetInfo(32, target);
				}
			}
		}
		target = attacker;
		if (((AiUtils.HaveMemo(target, 68) == 1 && (target.getClassId() == 128 || target.getClassId() == 129)) && AiUtils.GetMemoState(target, 68) == 12) && AiUtils.OwnItemCount(target, 9735) == 0)
		{
			random1_list.SetInfo(33, target);
		}
		if (((AiUtils.HaveMemo(target, 68) == 1 && (target.getClassId() == 128 || target.getClassId() == 129)) && AiUtils.GetMemoState(target, 68) == 12) && AiUtils.OwnItemCount(target, 9735) == 0)
		{
			random1_list.SetInfo(33, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (((AiUtils.HaveMemo(target, 68) == 1 && (target.getClassId() == 128 || target.getClassId() == 129)) && AiUtils.GetMemoState(target, 68) == 12) && AiUtils.OwnItemCount(target, 9735) == 0)
				{
					random1_list.SetInfo(33, target);
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
					SetCurrentQuestID(70);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7485) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7485) >= 20)
							{
								DeleteItem1(target, 7485, 20);
							}
							CreateOnePrivateEx(27219, "argos_boss_01", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7485);
							if (i0 + i1 < 700)
							{
								GiveItem1(target, 7485, i0);
							}
							else
							{
								GiveItem1(target, 7485, 700 - i1);
							}
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(71);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7486) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7486) >= 20)
							{
								DeleteItem1(target, 7486, 20);
							}
							CreateOnePrivateEx(27220, "argos_boss_02", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7486);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7486, i0);
							}
							else
							{
								GiveItem1(target, 7486, 700 - i1);
							}
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(72);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7487) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7487) >= 20)
							{
								DeleteItem1(target, 7487, 20);
							}
							CreateOnePrivateEx(27221, "argos_boss_03", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7487);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7487, i0);
							}
							else
							{
								GiveItem1(target, 7487, 700 - i1);
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(73);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7488) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7488) >= 20)
							{
								DeleteItem1(target, 7488, 20);
							}
							CreateOnePrivateEx(27222, "argos_boss_04", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7488);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7488, i0);
							}
							else
							{
								GiveItem1(target, 7488, 700 - i1);
							}
						}
					}
					break;
				}
				case 4:
				{
					SetCurrentQuestID(74);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7489) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7489) >= 20)
							{
								DeleteItem1(target, 7489, 20);
							}
							CreateOnePrivateEx(27223, "argos_boss_05", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7489);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7489, i0);
							}
							else
							{
								GiveItem1(target, 7489, 700 - i1);
							}
						}
					}
					break;
				}
				case 5:
				{
					SetCurrentQuestID(75);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7490) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7490) >= 20)
							{
								DeleteItem1(target, 7490, 20);
							}
							CreateOnePrivateEx(27224, "argos_boss_06", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7490);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7490, i0);
							}
							else
							{
								GiveItem1(target, 7490, 700 - i1);
							}
						}
					}
					break;
				}
				case 6:
				{
					SetCurrentQuestID(76);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7491) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7491) >= 20)
							{
								DeleteItem1(target, 7491, 20);
							}
							CreateOnePrivateEx(27225, "argos_boss_07", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7491);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7491, i0);
							}
							else
							{
								GiveItem1(target, 7491, 700 - i1);
							}
						}
					}
					break;
				}
				case 7:
				{
					SetCurrentQuestID(77);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7492) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7492) >= 20)
							{
								DeleteItem1(target, 7492, 20);
							}
							CreateOnePrivateEx(27226, "argos_boss_08", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7492);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7492, i0);
							}
							else
							{
								GiveItem1(target, 7492, 700 - i1);
							}
						}
					}
					break;
				}
				case 8:
				{
					SetCurrentQuestID(78);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7493) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7493) >= 20)
							{
								DeleteItem1(target, 7493, 20);
							}
							CreateOnePrivateEx(27227, "argos_boss_09", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7493);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7493, i0);
							}
							else
							{
								GiveItem1(target, 7493, 700 - i1);
							}
						}
					}
					break;
				}
				case 9:
				{
					SetCurrentQuestID(79);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7494) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7494) >= 20)
							{
								DeleteItem1(target, 7494, 20);
							}
							CreateOnePrivateEx(27228, "argos_boss_10", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7494);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7494, i0);
							}
							else
							{
								GiveItem1(target, 7494, 700 - i1);
							}
						}
					}
					break;
				}
				case 10:
				{
					SetCurrentQuestID(80);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7495) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7495) >= 20)
							{
								DeleteItem1(target, 7495, 20);
							}
							CreateOnePrivateEx(27229, "argos_boss_11", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7495);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7495, i0);
							}
							else
							{
								GiveItem1(target, 7495, 700 - i1);
							}
						}
					}
					break;
				}
				case 11:
				{
					SetCurrentQuestID(81);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7496) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7496) >= 20)
							{
								DeleteItem1(target, 7496, 20);
							}
							CreateOnePrivateEx(27230, "argos_boss_12", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7496);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7496, i0);
							}
							else
							{
								GiveItem1(target, 7496, 700 - i1);
							}
						}
					}
					break;
				}
				case 12:
				{
					SetCurrentQuestID(82);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7497) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7497) >= 20)
							{
								DeleteItem1(target, 7497, 20);
							}
							CreateOnePrivateEx(27231, "argos_boss_13", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7497);
							if (i0 + i1 < 700)
							{
								GiveItem1(target, 7497, i0);
							}
							else
							{
								GiveItem1(target, 7497, 700 - i1);
							}
						}
					}
					break;
				}
				case 13:
				{
					SetCurrentQuestID(83);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7498) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7498) >= 20)
							{
								DeleteItem1(target, 7498, 20);
							}
							CreateOnePrivateEx(27232, "argos_boss_14", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7498);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7498, i0);
							}
							else
							{
								GiveItem1(target, 7498, 700 - i1);
							}
						}
					}
					break;
				}
				case 14:
				{
					SetCurrentQuestID(84);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7499) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7499) >= 20)
							{
								DeleteItem1(target, 7499, 20);
							}
							CreateOnePrivateEx(27233, "argos_boss_15", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7499);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7499, i0);
							}
							else
							{
								GiveItem1(target, 7499, 700 - i1);
							}
						}
					}
					break;
				}
				case 15:
				{
					SetCurrentQuestID(85);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7500) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7500) >= 20)
							{
								DeleteItem1(target, 7500, 20);
							}
							CreateOnePrivateEx(27234, "argos_boss_16", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7500);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7500, i0);
							}
							else
							{
								GiveItem1(target, 7500, 700 - i1);
							}
						}
					}
					break;
				}
				case 16:
				{
					SetCurrentQuestID(86);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7501) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7501) >= 20)
							{
								DeleteItem1(target, 7501, 20);
							}
							CreateOnePrivateEx(27235, "argos_boss_17", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7501);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7501, i0);
							}
							else
							{
								GiveItem1(target, 7501, 700 - i1);
							}
						}
					}
					break;
				}
				case 17:
				{
					SetCurrentQuestID(87);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7502) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7502) >= 20)
							{
								DeleteItem1(target, 7502, 20);
							}
							CreateOnePrivateEx(27236, "argos_boss_18", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7502);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7502, i0);
							}
							else
							{
								GiveItem1(target, 7502, 700 - i1);
							}
						}
					}
					break;
				}
				case 18:
				{
					SetCurrentQuestID(88);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7503) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7503) >= 20)
							{
								DeleteItem1(target, 7503, 20);
							}
							CreateOnePrivateEx(27237, "argos_boss_19", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7503);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7503, i0);
							}
							else
							{
								GiveItem1(target, 7503, 700 - i1);
							}
						}
					}
					break;
				}
				case 19:
				{
					SetCurrentQuestID(89);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7504) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7504) >= 20)
							{
								DeleteItem1(target, 7504, 20);
							}
							CreateOnePrivateEx(27238, "argos_boss_20", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7504);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7504, i0);
							}
							else
							{
								GiveItem1(target, 7504, 700 - i1);
							}
						}
					}
					break;
				}
				case 20:
				{
					SetCurrentQuestID(90);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7505) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7505) >= 20)
							{
								DeleteItem1(target, 7505, 20);
							}
							CreateOnePrivateEx(27239, "argos_boss_21", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7505);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7505, i0);
							}
							else
							{
								GiveItem1(target, 7505, 700 - i1);
							}
						}
					}
					break;
				}
				case 21:
				{
					SetCurrentQuestID(91);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7506) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7506) >= 20)
							{
								DeleteItem1(target, 7506, 20);
							}
							CreateOnePrivateEx(27240, "argos_boss_22", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7506);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7506, i0);
							}
							else
							{
								GiveItem1(target, 7506, 700 - i1);
							}
						}
					}
					break;
				}
				case 22:
				{
					SetCurrentQuestID(92);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7507) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7507) >= 20)
							{
								DeleteItem1(target, 7507, 20);
							}
							CreateOnePrivateEx(27241, "argos_boss_23", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7507);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7507, i0);
							}
							else
							{
								GiveItem1(target, 7507, 700 - i1);
							}
						}
					}
					break;
				}
				case 23:
				{
					SetCurrentQuestID(93);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7508) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7508) >= 20)
							{
								DeleteItem1(target, 7508, 20);
							}
							CreateOnePrivateEx(27242, "argos_boss_24", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7508);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7508, i0);
							}
							else
							{
								GiveItem1(target, 7508, 700 - i1);
							}
						}
					}
					break;
				}
				case 24:
				{
					SetCurrentQuestID(94);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7509) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7509) >= 20)
							{
								DeleteItem1(target, 7509, 20);
							}
							CreateOnePrivateEx(27243, "argos_boss_25", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7509);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7509, i0);
							}
							else
							{
								GiveItem1(target, 7509, 700 - i1);
							}
						}
					}
					break;
				}
				case 25:
				{
					SetCurrentQuestID(95);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7510) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7510) >= 20)
							{
								DeleteItem1(target, 7510, 20);
							}
							CreateOnePrivateEx(27244, "argos_boss_26", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7510);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7510, i0);
							}
							else
							{
								GiveItem1(target, 7510, 700 - i1);
							}
						}
					}
					break;
				}
				case 26:
				{
					SetCurrentQuestID(96);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7511) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7511) >= 20)
							{
								DeleteItem1(target, 7511, 20);
							}
							CreateOnePrivateEx(27245, "argos_boss_27", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7511);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7511, i0);
							}
							else
							{
								GiveItem1(target, 7511, 700 - i1);
							}
						}
					}
					break;
				}
				case 27:
				{
					SetCurrentQuestID(97);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7512) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7512) >= 20)
							{
								DeleteItem1(target, 7512, 20);
							}
							CreateOnePrivateEx(27246, "argos_boss_28", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7512);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7512, i0);
							}
							else
							{
								GiveItem1(target, 7512, 700 - i1);
							}
						}
					}
					break;
				}
				case 28:
				{
					SetCurrentQuestID(98);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7513) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7513) >= 20)
							{
								DeleteItem1(target, 7513, 20);
							}
							CreateOnePrivateEx(27247, "argos_boss_29", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7513);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7513, i0);
							}
							else
							{
								GiveItem1(target, 7513, 700 - i1);
							}
						}
					}
					break;
				}
				case 29:
				{
					SetCurrentQuestID(99);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7514) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7514) >= 20)
							{
								DeleteItem1(target, 7514, 20);
							}
							CreateOnePrivateEx(27248, "argos_boss_30", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7514);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7514, i0);
							}
							else
							{
								GiveItem1(target, 7514, 700 - i1);
							}
						}
					}
					break;
				}
				case 30:
				{
					SetCurrentQuestID(100);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 7515) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 7515) >= 20)
							{
								DeleteItem1(target, 7515, 20);
							}
							CreateOnePrivateEx(27249, "argos_boss_31", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 7515);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 7515, i0);
							}
							else
							{
								GiveItem1(target, 7515, 700 - i1);
							}
						}
					}
					break;
				}
				case 31:
				{
					SetCurrentQuestID(67);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 9740) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 9740) >= 20)
							{
								DeleteItem1(target, 9740, 20);
							}
							CreateOnePrivateEx(27325, "argos_boss_32", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 9740);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 9740, i0);
							}
							else
							{
								GiveItem1(target, 9740, 700 - i1);
							}
						}
					}
					break;
				}
				case 32:
				{
					SetCurrentQuestID(69);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 9742) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 9742) >= 20)
							{
								DeleteItem1(target, 9742, 20);
							}
							CreateOnePrivateEx(27334, "argos_boss_34", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 9742);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 9742, i0);
							}
							else
							{
								GiveItem1(target, 9742, 700 - i1);
							}
						}
					}
					break;
				}
				case 33:
				{
					SetCurrentQuestID(68);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.OwnItemCount(target, 9741) >= 700 && target.isPlayer())
						{
							if (AiUtils.OwnItemCount(target, 9741) >= 20)
							{
								DeleteItem1(target, 9741, 20);
							}
							CreateOnePrivateEx(27329, "argos_boss_33", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(target), target.getObjectId(), 0);
						}
						else
						{
							i0 = AiUtils.Rand(4) + 1;
							i1 = AiUtils.OwnItemCount(target, 9741);
							if (i0 + i1 <= 700)
							{
								GiveItem1(target, 9741, i0);
							}
							else
							{
								GiveItem1(target, 9741, 700 - i1);
							}
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}