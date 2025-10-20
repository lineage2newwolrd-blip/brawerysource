package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class baranka_messenger extends party_leader_patrol
{
	public baranka_messenger(final NpcInstance actor){super(actor);}

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
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(107);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 107) == 1)
					{
						if (AiUtils.OwnItemCount(c1, 1553)!=0 && AiUtils.OwnItemCount(c1, 1557) == 0)
						{
							GiveItem1(c1, 1557, 1);
							SetFlagJournal(c1, 107, 3);
							ShowQuestMark(c1, 107);
							SoundEffect(c1, "ItemSound.quest_itemget");
						}
						if (AiUtils.OwnItemCount(c1, 1554)!=0 && AiUtils.OwnItemCount(c1, 1556) == 0)
						{
							GiveItem1(c1, 1556, 1);
							SetFlagJournal(c1, 107, 5);
							ShowQuestMark(c1, 107);
							SoundEffect(c1, "ItemSound.quest_itemget");
						}
						if (AiUtils.OwnItemCount(c1, 1555)!=0 && AiUtils.OwnItemCount(c1, 1558) == 0)
						{
							GiveItem1(c1, 1558, 1);
							SetFlagJournal(c1, 107, 7);
							ShowQuestMark(c1, 107);
							SoundEffect(c1, "ItemSound.quest_itemget");
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