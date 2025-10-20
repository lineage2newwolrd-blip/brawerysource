package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class picat_araneid extends warrior_passive
{
	public picat_araneid(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		CodeInfo code_info = null;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 123) == 1 && AiUtils.GetMemoState(target, 123) == 4 && AiUtils.OwnItemCount(target, 8550) < 8)
		{
			always_list.SetInfo(0, target);
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(123);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					if (AiUtils.Rand(10) < 7)
					{
						if (HasAcademyMaster(target) == 1)
						{
							c0 = GetAcademyMaster(target);
							if (!IsNullCreature(c0) && DistFromMe(c0) <= 1500)
							{
								GiveItem1(target, 8550, 1);
								if (AiUtils.OwnItemCount(target, 8550) >= 7)
								{
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 123, 8);
									ShowQuestMark(target, 123);
								}
								else
								{
									SoundEffect(target, "ItemSound.quest_itemget");
								}
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