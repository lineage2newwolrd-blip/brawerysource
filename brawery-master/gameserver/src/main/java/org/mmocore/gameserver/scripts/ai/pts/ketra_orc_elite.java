package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.utils.ai.CodeInfoList;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.CodeInfo;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.utils.AiUtils;

public class ketra_orc_elite extends warrior_ag_casting_3skill_approach_stone
{
	public ketra_orc_elite(final NpcInstance actor){super(actor);}

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
		if (AiUtils.HaveMemo(target, 611) == 1 && ((((AiUtils.OwnItemCount(target, 7221) >= 1 && AiUtils.OwnItemCount(target, 7227) < 100) || (AiUtils.OwnItemCount(target, 7222) >= 1 && AiUtils.OwnItemCount(target, 7227) < 200)) || (AiUtils.OwnItemCount(target, 7223) >= 1 && AiUtils.OwnItemCount(target, 7227) < 300)) || (AiUtils.OwnItemCount(target, 7224) >= 1 && AiUtils.OwnItemCount(target, 7227) < 400)))
		{
			random1_list.SetInfo(0, target);
		}
		if (AiUtils.HaveMemo(target, 611) == 1 && ((((AiUtils.OwnItemCount(target, 7221) >= 1 && AiUtils.OwnItemCount(target, 7227) < 100) || (AiUtils.OwnItemCount(target, 7222) >= 1 && AiUtils.OwnItemCount(target, 7227) < 200)) || (AiUtils.OwnItemCount(target, 7223) >= 1 && AiUtils.OwnItemCount(target, 7227) < 300)) || (AiUtils.OwnItemCount(target, 7224) >= 1 && AiUtils.OwnItemCount(target, 7227) < 400)))
		{
			random1_list.SetInfo(0, target);
		}
		if (!AiUtils.IsNull(lparty))
		{
			for (i9 = 0; i9 < lparty.getMemberCount(); i9++)
			{
				target = GetMemberOfParty(lparty, i9);
				if (AiUtils.HaveMemo(target, 611) == 1 && ((((AiUtils.OwnItemCount(target, 7221) >= 1 && AiUtils.OwnItemCount(target, 7227) < 100) || (AiUtils.OwnItemCount(target, 7222) >= 1 && AiUtils.OwnItemCount(target, 7227) < 200)) || (AiUtils.OwnItemCount(target, 7223) >= 1 && AiUtils.OwnItemCount(target, 7227) < 300)) || (AiUtils.OwnItemCount(target, 7224) >= 1 && AiUtils.OwnItemCount(target, 7227) < 400)))
				{
					random1_list.SetInfo(0, target);
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
			if (code_info.getCode() == 0)
			{
				SetCurrentQuestID(611);
				target = code_info.RandomSelectOne();
				if (!AiUtils.IsNull(target) && DistFromMe(target) <= 1500)
				{
					i4 = AiUtils.Rand(1000);
					if (i4 < 518)
					{
						GiveItem1(target, 7227, 1);
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