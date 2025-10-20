package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class oel_mahum_witch_doctor extends wizard_pa_ddmagic2_heal
{
	public oel_mahum_witch_doctor(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		CodeInfoList always_list = null;
		Creature c0 = null;
		Creature c2 = null;
		CodeInfo code_info = null;
		int i0 = 0;
		int i1 = 0;
		int i9 = 0;
		Party lparty = attacker.getPlayer() != null? attacker.getPlayer().getParty() : null;
		Player member = null;
		CodeInfoList random1_list = null;
		Creature target = null;

		always_list = AiUtils.AllocCodeInfoList();
		random1_list = AiUtils.AllocCodeInfoList();
		target = attacker;
		if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 336)!=0 && AiUtils.GetMemoState(target, 336) == 3)
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		random1_list.SetInfo(1, target);
		random1_list.SetInfo(1, target);
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				random1_list.SetInfo(1, target);
			}
		}
		target = attacker;
		always_list.SetInfo(2, target);
		target = attacker;
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (AiUtils.HaveMemo(target, 426) == 1)
		{
			random1_list.SetInfo(3, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 426) == 1)
				{
					random1_list.SetInfo(3, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 2)
			{
				SetCurrentQuestID(712);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					c0 = Pledge_GetLeader(target);
					if (!IsNullCreature(c0))
					{
						if (AiUtils.HaveMemo(c0, 712) == 1 && AiUtils.GetMemoState(c0, 712) == 7 && DistFromMe(c0) <= 1500)
						{
							if (AiUtils.OwnItemCount(c0, 13851) >= 299)
							{
								GiveItem1(c0, 13851, 1);
								SetMemoState(c0, 712, 8);
								SetFlagJournal(c0, 712, 7);
								ShowQuestMark(c0, 712);
								SoundEffect(c0, "ItemSound.quest_middle");
							}
							else
							{
								GiveItem1(c0, 13851, 1);
								SoundEffect(c0, "ItemSound.quest_itemget");
							}
						}
					}
				}
			}
		}
		code_info = random1_list.RandomSelectOne();
		if (!AiUtils.IsNull(code_info))
		{
			switch (code_info.getCode())
			{
				case 0:
				{
					SetCurrentQuestID(336);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(1000) < 200)
						{
							GiveItem1(target, 3490, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 1:
				{
					SetCurrentQuestID(501);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						c2 = Pledge_GetLeader(target);
						if (!IsNullCreature(c2))
						{
							if (AiUtils.HaveMemo(c2, 501)!=0 && (AiUtils.GetMemoState(c2, 501) >= 3 && AiUtils.GetMemoState(c2, 501) < 6))
							{
								if (AiUtils.Rand(10) == 1)
								{
									GiveItem1(target, 3834, 1);
									SoundEffect(target, "ItemSound.quest_itemget");
								}
							}
						}
					}
					break;
				}
				case 3:
				{
					SetCurrentQuestID(426);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						if (AiUtils.Rand(100) < 42)
						{
							GiveItem1(target, 7586, 2);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
						else
						{
							GiveItem1(target, 7586, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

}