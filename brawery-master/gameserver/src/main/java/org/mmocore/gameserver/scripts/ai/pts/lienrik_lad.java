package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class lienrik_lad extends warrior_aggressive_casting_curse
{
	public lienrik_lad(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 352) == 1)
		{
			random1_list.SetInfo(0, target);
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 431) == 1 && AiUtils.GetMemoState(target, 431) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 431) == 1 && AiUtils.GetMemoState(target, 431) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 431) == 1 && AiUtils.GetMemoState(target, 431) == (1 * 10 + 1))
				{
					random1_list.SetInfo(1, target);
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
					SetCurrentQuestID(352);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i0 = AiUtils.Rand(100);
						if (i0 < 69)
						{
							GiveItem1(target, 5860, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else if (i0 < 71)
						{
							GiveItem1(target, 5861, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(431);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 500)
						{
							if (AiUtils.OwnItemCount(target, 7540) + 1 >= 50)
							{
								if (AiUtils.OwnItemCount(target, 7540) < 50)
								{
									GiveItem1(target, 7540, 50 - AiUtils.OwnItemCount(target, 7540));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 431, 2);
									ShowQuestMark(target, 431);
									SetMemoState(target, 431, 1 * 10 + 2);
								}
							}
							else
							{
								GiveItem1(target, 7540, 1);
								SoundEffect(target, "ItemSound.quest_itemget");
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