package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class kaboo_orc_archer extends warrior_passive_use_bow
{
	public kaboo_orc_archer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 260)!=0)
		{
			random1_list.SetInfo(0, target);
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
				SetCurrentQuestID(260);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(10) > 4)
					{
						GiveItem1(target, 1114, 1);
						SoundEffect(target, "ItemSound.quest_itemget");
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}