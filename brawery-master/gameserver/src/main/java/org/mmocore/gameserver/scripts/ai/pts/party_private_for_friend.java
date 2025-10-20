package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class party_private_for_friend extends party_private_physicalspecial
{
	public int CreatureID = 0;

	public party_private_for_friend(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai4 = 0;
		actor.flag = CreatureID;
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 4051)
		{
			if (i_ai4 == 1)
			{
				LookNeighbor(300);
				AddTimerEx(4051, 3 * 60 * 1000);
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (creature.flag == actor.flag)
		{
			i_ai4 = 0;
		}
		else
		{
			AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
			super.onEvtSeeCreature(creature);
		}
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (privat != actor)
		{
			i_ai4 = 1;
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
			super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
		}
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.flag != actor.flag)
		{
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
			super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		}
	}

}