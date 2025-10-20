package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class varka_high_guard extends party_private_pa_physicalspecial_stone
{
	public varka_high_guard(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 605) == 1 && (((AiUtils.OwnItemCount(target, 7212) >= 1 && AiUtils.OwnItemCount(target, 7218) < 100) || (AiUtils.OwnItemCount(target, 7213) >= 1 && AiUtils.OwnItemCount(target, 7218) < 200)) || (AiUtils.OwnItemCount(target, 7214) >= 1 && AiUtils.OwnItemCount(target, 7218) < 200)))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 605) == 1 && (((AiUtils.OwnItemCount(target, 7212) >= 1 && AiUtils.OwnItemCount(target, 7218) < 100) || (AiUtils.OwnItemCount(target, 7213) >= 1 && AiUtils.OwnItemCount(target, 7218) < 200)) || (AiUtils.OwnItemCount(target, 7214) >= 1 && AiUtils.OwnItemCount(target, 7218) < 200)))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 605) == 1 && (((AiUtils.OwnItemCount(target, 7212) >= 1 && AiUtils.OwnItemCount(target, 7218) < 100) || (AiUtils.OwnItemCount(target, 7213) >= 1 && AiUtils.OwnItemCount(target, 7218) < 200)) || (AiUtils.OwnItemCount(target, 7214) >= 1 && AiUtils.OwnItemCount(target, 7218) < 200)))
				{
					random1_list.SetInfo(0, target);
				}
			}
		}
		target = attacker;
		always_list.SetInfo(1, target);
		always_list.SetInfo(1, target);
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				always_list.SetInfo(1, target);
			}
		}
		while (!AiUtils.IsNull(code_info = always_list.Next()))
		{
			if (code_info.getCode() == 1)
			{
				SetCurrentQuestID(611);
				while (!AiUtils.IsNull(target = code_info.Next()))
				{
					if (DistFromMe(target) <= 1500)
					{
						SetCurrentQuestID(611);
						if (AiUtils.OwnItemCount(target, 7225) >= 1)
						{
							DeleteItem1(target, 7225, AiUtils.OwnItemCount(target, 7225));
							GiveItem1(target, 7224, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7224) >= 1)
						{
							DeleteItem1(target, 7224, AiUtils.OwnItemCount(target, 7224));
							GiveItem1(target, 7223, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7223) >= 1)
						{
							DeleteItem1(target, 7223, AiUtils.OwnItemCount(target, 7223));
							GiveItem1(target, 7222, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7222) >= 1)
						{
							DeleteItem1(target, 7222, AiUtils.OwnItemCount(target, 7222));
							GiveItem1(target, 7221, 1);
						}
						else if (AiUtils.OwnItemCount(target, 7221) >= 1)
						{
							DeleteItem1(target, 7221, AiUtils.OwnItemCount(target, 7221));
						}
						if (AiUtils.HaveMemo(target, 611)!=0)
						{
							RemoveMemo(target, 611);
							AiUtils.AddLog(2, target, 611);
							DeleteItem1(target, 7226, AiUtils.OwnItemCount(target, 7226));
							DeleteItem1(target, 7227, AiUtils.OwnItemCount(target, 7227));
							DeleteItem1(target, 7228, AiUtils.OwnItemCount(target, 7228));
						}
						if (AiUtils.HaveMemo(target, 612)!=0)
						{
							RemoveMemo(target, 612);
							AiUtils.AddLog(2, target, 612);
							DeleteItem1(target, 7234, AiUtils.OwnItemCount(target, 7234));
						}
						if (AiUtils.HaveMemo(target, 613)!=0)
						{
							RemoveMemo(target, 613);
							AiUtils.AddLog(2, target, 613);
							DeleteItem1(target, 7240, AiUtils.OwnItemCount(target, 7240));
						}
						if (AiUtils.HaveMemo(target, 614)!=0)
						{
							RemoveMemo(target, 614);
							AiUtils.AddLog(2, target, 614);
							DeleteItem1(target, 7241, AiUtils.OwnItemCount(target, 7241));
						}
						if (AiUtils.HaveMemo(target, 615)!=0)
						{
							RemoveMemo(target, 615);
							AiUtils.AddLog(2, target, 615);
							DeleteItem1(target, 7242, AiUtils.OwnItemCount(target, 7242));
						}
						if (AiUtils.HaveMemo(target, 616)!=0)
						{
							RemoveMemo(target, 616);
							AiUtils.AddLog(2, target, 616);
							DeleteItem1(target, 7244, AiUtils.OwnItemCount(target, 7244));
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
				SetCurrentQuestID(605);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 604)
					{
						GiveItem1(target, 7218, 1);
						SoundEffect(target, "ItemSound.quest_itemget");
					}
				}
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		SetCurrentQuestID(609);
		if (AiUtils.HaveMemo(attacker, 609)!=0 && AiUtils.GetMemoState(attacker, 609) == 2)
		{
			SetMemoState(attacker, 609, 3);
			CreateOnePrivateEx(31684, "udans_eye", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
			if (Skill_GetConsumeMP(297992193/*@skill_4547_1*/) < actor._currentMp && Skill_GetConsumeHP(297992193/*@skill_4547_1*/) < actor.currentHp && Skill_InReuseDelay(297992193/*@skill_4547_1*/) == 0)
			{
				AddUseSkillDesire(attacker, 297992193/*@skill_4547_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 10000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}