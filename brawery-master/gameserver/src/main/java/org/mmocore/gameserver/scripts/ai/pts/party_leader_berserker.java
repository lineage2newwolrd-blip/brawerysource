package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class party_leader_berserker extends party_leader
{
	public int Fury = 264568833;
	public int MaxRoarCount = 1;

	public party_leader_berserker(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		final NpcInstance actor = getActor();

		if ((i_ai1 < MaxRoarCount && privat != actor))
		{
			if (Skill_GetConsumeMP(i_ai1) < actor._currentMp && Skill_GetConsumeHP(i_ai1) < actor.currentHp && Skill_InReuseDelay(i_ai1) == 0)
			{
				AddUseSkillDesire(actor, i_ai1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai1 = i_ai1 + 1;
		}
		super.onEvtPartyDead(attacker, privat);
	}

}