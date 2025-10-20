package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_kill_private extends party_leader
{
	public int DDMagic = 264241164;
	public int SelfBuff = 263979009;
	public int RespawnTime = 2;
	public int KillPrivateHPRatio = 50;

	public party_leader_kill_private(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (privat != actor)
		{
			if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(SelfBuff)) <= 0 && AiUtils.FloatToInt(privat.currentHp / privat.getMaxHp() * 100) <= KillPrivateHPRatio && i_ai0 == 0)
			{
				i_ai0 = 1;
				if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
				{
					AddUseSkillDesire(privat, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		final NpcInstance actor = getActor();

		if (skill_name_id == DDMagic)
		{
			if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
			{
				AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		final NpcInstance actor = getActor();

		if ((privat != actor && privat._respawn_minion != 0))
		{
			if (i_ai0 == 1)
			{
				i_ai0 = 0;
				CreateOnePrivate(privat.getClassId(), privat.getAI().getClass().getName(), privat._desirePoint, RespawnTime);
			}
			else
			{
				CreateOnePrivate(privat.getClassId(), privat.getAI().getClass().getName(), privat._desirePoint, privat._respawn_minion);
			}
		}
	}

}