package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class baraham extends party_leader
{
	public baraham(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c1 = null;
		CodeInfo code_info = null;
		int i4 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;
		final NpcInstance actor = getActor();

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (3 * 10 + 1) && target.isSubClassActive() != 0)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (3 * 10 + 1) && target.isSubClassActive() != 0)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 241) == 1 && AiUtils.GetMemoState(target, 241) == (3 * 10 + 1) && target.isSubClassActive() != 0)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(1, target);
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 1)
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
					if (AiUtils.HaveMemo(c1, 211)!=0)
					{
						if (AiUtils.GetMemoState(c1, 211) == 5 && AiUtils.OwnItemCount(c1, 2630) == 0)
						{
							GiveItem1(c1, 2630, 1);
							SoundEffect(c1, "ItemSound.quest_middle");
							SetMemoState(c1, 211, 6);
							SetFlagJournal(c1, 211, 7);
							ShowQuestMark(c1, 211);
						}
						if (Maker_GetNpcCount() < 10)
						{
							CreateOnePrivateEx(30646, "raldo", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, AiUtils.GetIndexFromCreature(attacker), 0, 0);
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(241);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 1000)
					{
						if (AiUtils.OwnItemCount(target, 7587) + 1 >= 1)
						{
							if (AiUtils.OwnItemCount(target, 7587) < 1)
							{
								GiveItem1(target, 7587, 1 - AiUtils.OwnItemCount(target, 7587));
								SoundEffect(target, "ItemSound.quest_middle");
								SetFlagJournal(target, 241, 4);
								ShowQuestMark(target, 241);
								SetMemoState(target, 241, 3 * 10 + 2);
							}
						}
						else
						{
							GiveItem1(target, 7587, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

}