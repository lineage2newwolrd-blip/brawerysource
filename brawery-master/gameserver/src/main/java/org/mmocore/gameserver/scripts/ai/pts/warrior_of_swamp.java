package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_of_swamp extends party_private_physicalspecial
{
	public warrior_of_swamp(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(711);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c0 = Pledge_GetLeader(target);
					if (!IsNullCreature(c0))
					{
						if (AiUtils.HaveMemo(c0, 711) == 1 && AiUtils.GetMemoState(c0, 711) / 1000 >= 1 && AiUtils.GetMemoState(c0, 711) / 1000 < 101 && DistFromMe(c0) <= 1500)
						{
							i0 = AiUtils.GetMemoState(c0, 711);
							if (AiUtils.GetMemoState(c0, 711) / 1000 < 100)
							{
								SetMemoState(c0, 711, i0 + 1000);
							}
							else
							{
								SetMemoState(c0, 711, i0 + 1000);
								SetFlagJournal(c0, 711, 6);
								ShowQuestMark(c0, 711);
								SoundEffect(c0, "ItemSound.quest_middle");
							}
						}
					}
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