package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class crimson_drake extends warrior_aggressive_casting_ddmagic
{
	public crimson_drake(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		CodeInfo code_info = null;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 386)!=0)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 386)!=0)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 386)!=0)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 53) == 1 && GetOneTimeQuestFlag(target, 53) == 0 && AiUtils.GetMemoState(target, 53) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 53) == 1 && GetOneTimeQuestFlag(target, 53) == 0 && AiUtils.GetMemoState(target, 53) == (1 * 10 + 1))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 53) == 1 && GetOneTimeQuestFlag(target, 53) == 0 && AiUtils.GetMemoState(target, 53) == (1 * 10 + 1))
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
					SetCurrentQuestID(386);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 20.200001f)
						{
							GiveItem1(target, 6363, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(53);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if ((i4 < 500 && 500 != 0))
						{
							if (AiUtils.OwnItemCount(target, 7624) + 1 >= 100)
							{
								if (AiUtils.OwnItemCount(target, 7624) < 100)
								{
									GiveItem1(target, 7624, 100 - AiUtils.OwnItemCount(target, 7624));
									SoundEffect(target, "ItemSound.quest_middle");
									SetFlagJournal(target, 53, 2);
									ShowQuestMark(target, 53);
								}
								SetMemoState(target, 53, 1 * 10 + 2);
							}
							else
							{
								GiveItem1(target, 7624, 1);
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