package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class party_private_berserker extends party_private
{
	public int Fury = 264568833;
	public int MaxRoarCount = 1;

	public party_private_berserker(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if ((i_ai1 < MaxRoarCount && privat != actor))
		{
			i0 = Fury + i_ai1;
			if (Skill_GetConsumeMP(i0) < actor._currentMp && Skill_GetConsumeHP(i0) < actor.currentHp && Skill_InReuseDelay(i0) == 0)
			{
				AddUseSkillDesire(actor, i0, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai1 = i_ai1 + 1;
		}
		super.onEvtPartyDead(attacker, privat);
	}

}