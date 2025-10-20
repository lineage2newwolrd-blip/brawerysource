package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class darkwing_bat extends warrior_passive
{
	public darkwing_bat(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
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
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(275);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (AiUtils.HaveMemo(c1, 275)!=0 && AiUtils.OwnItemCount(c1, 1478) < 70)
					{
						if (AiUtils.OwnItemCount(c1, 1478) < 69)
						{
							SoundEffect(c1, "ItemSound.quest_itemget");
						}
						else
						{
							SoundEffect(c1, "ItemSound.quest_middle");
							SetFlagJournal(c1, 275, 2);
							ShowQuestMark(c1, 275);
						}
						GiveItem1(c1, 1478, 1);
						i0 = AiUtils.Rand(100);
						if (AiUtils.OwnItemCount(c1, 1478) > 10 && AiUtils.OwnItemCount(c1, 1478) < 66 && i0 < 10)
						{
							CreateOnePrivate(27043, "varangkas_tracker", 0, 1);
							GiveItem1(c1, 1479, 1);
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