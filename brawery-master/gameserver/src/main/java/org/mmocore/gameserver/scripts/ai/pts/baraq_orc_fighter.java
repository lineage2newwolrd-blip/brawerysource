package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class baraq_orc_fighter extends warrior_aggressive
{
	public baraq_orc_fighter(final NpcInstance actor){super(actor);}

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
				SetCurrentQuestID(163);
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
						if (AiUtils.HaveMemo(c1, 163) == 1)
						{
							if (AiUtils.Rand(10) == 0 && AiUtils.OwnItemCount(c1, 1038) == 0)
							{
								GiveItem1(c1, 1038, 1);
								if (AiUtils.OwnItemCount(c1, 1038) + AiUtils.OwnItemCount(c1, 1039) + AiUtils.OwnItemCount(c1, 1040) + AiUtils.OwnItemCount(c1, 1041) >= 3)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									SetFlagJournal(c1, 163, 2);
									ShowQuestMark(c1, 163);
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							if (AiUtils.Rand(10) > 7 && AiUtils.OwnItemCount(c1, 1039) == 0)
							{
								GiveItem1(c1, 1039, 1);
								if (AiUtils.OwnItemCount(c1, 1038) + AiUtils.OwnItemCount(c1, 1039) + AiUtils.OwnItemCount(c1, 1040) + AiUtils.OwnItemCount(c1, 1041) >= 3)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									SetFlagJournal(c1, 163, 2);
									ShowQuestMark(c1, 163);
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							if (AiUtils.Rand(10) > 7 && AiUtils.OwnItemCount(c1, 1040) == 0)
							{
								GiveItem1(c1, 1040, 1);
								if (AiUtils.OwnItemCount(c1, 1038) + AiUtils.OwnItemCount(c1, 1039) + AiUtils.OwnItemCount(c1, 1040) + AiUtils.OwnItemCount(c1, 1041) >= 3)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									SetFlagJournal(c1, 163, 2);
									ShowQuestMark(c1, 163);
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
								}
							}
							if (AiUtils.Rand(10) > 5 && AiUtils.OwnItemCount(c1, 1041) == 0)
							{
								GiveItem1(c1, 1041, 1);
								if (AiUtils.OwnItemCount(c1, 1038) + AiUtils.OwnItemCount(c1, 1039) + AiUtils.OwnItemCount(c1, 1040) + AiUtils.OwnItemCount(c1, 1041) >= 3)
								{
									SoundEffect(c1, "ItemSound.quest_middle");
									SetFlagJournal(c1, 163, 2);
									ShowQuestMark(c1, 163);
								}
								else
								{
									SoundEffect(c1, "ItemSound.quest_itemget");
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