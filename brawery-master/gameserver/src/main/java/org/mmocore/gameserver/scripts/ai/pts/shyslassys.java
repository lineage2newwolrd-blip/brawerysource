package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class shyslassys extends party_leader
{
	public shyslassys(final NpcInstance actor){super(actor);}

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
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(211);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 211)!=0 && AiUtils.GetMemoState(c1, 211) == 1 && AiUtils.OwnItemCount(c1, 2631) == 0 && AiUtils.OwnItemCount(c1, 2632) == 0)
					{
						GiveItem1(c1, 2631, 1);
						GiveItem1(c1, 2632, 1);
						if (Maker_GetNpcCount() < 10)
						{
							CreateOnePrivateEx(30647, "chest_of_shyslassys", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						}
						SoundEffect(c1, "ItemSound.quest_middle");
						SetMemoState(c1, 211, 2);
						SetFlagJournal(c1, 211, 2);
						ShowQuestMark(c1, 211);
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