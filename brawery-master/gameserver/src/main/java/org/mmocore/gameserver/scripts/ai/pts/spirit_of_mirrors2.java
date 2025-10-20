package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class spirit_of_mirrors2 extends warrior_passive
{
	public spirit_of_mirrors2(final NpcInstance actor){super(actor);}

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
		int weapon_class_id = 0;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		always_list.SetInfo(0, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(104);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c1 = GetLastAttacker();
					if (c1.getPlayer() !=null)
					{
						c1 = c1.getPlayer();
					}
					if (!IsNullCreature(c1))
					{
						if (AiUtils.HaveMemo(c1, 104) == 1 && actor.last_blow_weapon_class_id == 748 && AiUtils.OwnItemCount(c1, 1136) == 0 && AiUtils.OwnItemCount(c1, 748) > 0)
						{
							DeleteItem1(c1, 748, 1);
							GiveItem1(c1, 1136, 1);
							if (AiUtils.OwnItemCount(c1, 1135) + AiUtils.OwnItemCount(c1, 1136) + AiUtils.OwnItemCount(c1, 1137) >= 2)
							{
								SetFlagJournal(c1, 104, 3);
								ShowQuestMark(c1, 104);
							}
							SoundEffect(c1, "ItemSound.quest_middle");
							SetMemoState(c1, 104, 2);
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