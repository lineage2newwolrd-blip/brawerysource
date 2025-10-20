package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ketra_high_shaman extends party_leader_ag_casting_ddmagic_stone
{
	public ketra_high_shaman(final NpcInstance actor){super(actor);}

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
		always_list.SetInfo(0, target);
		always_list.SetInfo(0, target);
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				always_list.SetInfo(0, target);
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 611) == 1 && (((AiUtils.OwnItemCount(target, 7222) >= 1 && AiUtils.OwnItemCount(target, 7228) < 100) || (AiUtils.OwnItemCount(target, 7223) >= 1 && AiUtils.OwnItemCount(target, 7228) < 200)) || (AiUtils.OwnItemCount(target, 7224) >= 1 && AiUtils.OwnItemCount(target, 7228) < 200)))
		{
			random1_list.SetInfo(1, target);
		}
		if (AiUtils.HaveMemo(target, 611) == 1 && (((AiUtils.OwnItemCount(target, 7222) >= 1 && AiUtils.OwnItemCount(target, 7228) < 100) || (AiUtils.OwnItemCount(target, 7223) >= 1 && AiUtils.OwnItemCount(target, 7228) < 200)) || (AiUtils.OwnItemCount(target, 7224) >= 1 && AiUtils.OwnItemCount(target, 7228) < 200)))
		{
			random1_list.SetInfo(1, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 611) == 1 && (((AiUtils.OwnItemCount(target, 7222) >= 1 && AiUtils.OwnItemCount(target, 7228) < 100) || (AiUtils.OwnItemCount(target, 7223) >= 1 && AiUtils.OwnItemCount(target, 7228) < 200)) || (AiUtils.OwnItemCount(target, 7224) >= 1 && AiUtils.OwnItemCount(target, 7228) < 200)))
				{
					random1_list.SetInfo(1, target);
				}
			}
		}
		target = attacker;
		if (AiUtils.HaveMemo(target, 612) == 1 && AiUtils.GetMemoState(target, 612) == (1 * 10 + 1))
		{
			random1_list.SetInfo(2, target);
		}
		if (AiUtils.HaveMemo(target, 612) == 1 && AiUtils.GetMemoState(target, 612) == (1 * 10 + 1))
		{
			random1_list.SetInfo(2, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 612) == 1 && AiUtils.GetMemoState(target, 612) == (1 * 10 + 1))
				{
					random1_list.SetInfo(2, target);
				}
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(605);
				while (!AiUtils.IsNull(target = code_info.Next()))
				{
					if (DistFromMe(target) <= 1500)
					{
						SetCurrentQuestID(605);
						if (AiUtils.OwnItemCount(target, 7215) >= 1)
						{
							DeleteItem1(target, 7215, AiUtils.OwnItemCount(target, 7215));
							GiveItem1(target, 7214, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7214) >= 1)
						{
							DeleteItem1(target, 7214, AiUtils.OwnItemCount(target, 7214));
							GiveItem1(target, 7213, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7213) >= 1)
						{
							DeleteItem1(target, 7213, AiUtils.OwnItemCount(target, 7213));
							GiveItem1(target, 7212, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7212) >= 1)
						{
							DeleteItem1(target, 7212, AiUtils.OwnItemCount(target, 7212));
							GiveItem1(target, 7211, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7211) >= 1)
						{
							DeleteItem1(target, 7211, AiUtils.OwnItemCount(target, 7211));
						}
						if (AiUtils.HaveMemo(target, 605)!=0)
						{
							RemoveMemo(target, 605);
							AiUtils.AddLog(2, target, 605);
							DeleteItem1(target, 7216, AiUtils.OwnItemCount(target, 7216));
							DeleteItem1(target, 7217, AiUtils.OwnItemCount(target, 7217));
							DeleteItem1(target, 7218, AiUtils.OwnItemCount(target, 7218));
						}
						if (AiUtils.HaveMemo(target, 606)!=0)
						{
							RemoveMemo(target, 606);
							AiUtils.AddLog(2, target, 606);
							DeleteItem1(target, 7233, AiUtils.OwnItemCount(target, 7233));
						}
						if (AiUtils.HaveMemo(target, 607)!=0)
						{
							RemoveMemo(target, 607);
							AiUtils.AddLog(2, target, 607);
							DeleteItem1(target, 7235, AiUtils.OwnItemCount(target, 7235));
						}
						if (AiUtils.HaveMemo(target, 608)!=0)
						{
							RemoveMemo(target, 608);
							AiUtils.AddLog(2, target, 608);
							DeleteItem1(target, 7236, AiUtils.OwnItemCount(target, 7236));
						}
						if (AiUtils.HaveMemo(target, 609)!=0)
						{
							RemoveMemo(target, 609);
							AiUtils.AddLog(2, target, 609);
							DeleteItem1(target, 7237, AiUtils.OwnItemCount(target, 7237));
						}
						if (AiUtils.HaveMemo(target, 610)!=0)
						{
							RemoveMemo(target, 610);
							AiUtils.AddLog(2, target, 610);
							DeleteItem1(target, 7239, AiUtils.OwnItemCount(target, 7239));
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
				case 1:
				{
					SetCurrentQuestID(611);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 627)
						{
							GiveItem1(target, 7228, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
				case 2:
				{
					SetCurrentQuestID(612);
					target = code_info.RandomSelectOne();
					if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
					{
						i4 = AiUtils.Rand(1000);
						if (i4 < 713)
						{
							GiveItem1(target, 7234, 1);
							SoundEffect(target, "ItemSound.quest_itemget");
						}
					}
					break;
				}
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		SetCurrentQuestID(615);
		if (AiUtils.HaveMemo(attacker, 615)!=0 && AiUtils.GetMemoState(attacker, 615) == 2)
		{
			SetMemoState(attacker, 615, 3);
			CreateOnePrivateEx(31685, "asefas_eye", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
			if (Skill_GetConsumeMP(297992193/*@skill_4547_1*/) < actor._currentMp && Skill_GetConsumeHP(297992193/*@skill_4547_1*/) < actor.currentHp && Skill_InReuseDelay(297992193/*@skill_4547_1*/) == 0)
			{
				AddUseSkillDesire(attacker, 297992193/*@skill_4547_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 10000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}