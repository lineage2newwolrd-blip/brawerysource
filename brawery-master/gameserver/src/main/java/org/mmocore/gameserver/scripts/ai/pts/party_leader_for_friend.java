package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;

public class party_leader_for_friend extends party_leader_casting_3skill_approach
{
	public int AttackPointX = 149441;
	public int AttackPointY = 46544;
	public int AttackPointZ = -3408;
	public int AttackRate = 10;
	public int CreatureID = 0;

	public party_leader_for_friend(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		AddTimerEx(4052, 1000);
		i_ai4 = 0;
		actor.flag = CreatureID;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 4050)
		{
			if (i_ai4 == 0)
			{
				i_ai4 = 1;
				AddMoveToDesire(AttackPointX, AttackPointY, AttackPointZ, 1000);
				AddTimerEx(4051, 10000);
			}
			else if (i_ai4 == 1)
			{
				i_ai4 = 0;
				RemoveAllAttackDesire();
				AddMoveToDesire(start_x, start_y, start_z, 100000);
			}
			AddTimerEx(4050, AttackRate * 60 * 1000);
		}
		else if (timer_id == 4051)
		{
			if (i_ai4 == 1)
			{
				LookNeighbor(300);
				AddTimerEx(4051, 1000);
			}
		}
		else if (timer_id == 4052)
		{
			i_ai4 = 1;
			AddTimerEx(4050, AttackRate * 60 * 1000);
			AddTimerEx(4051, 10000);
			AddMoveToDesire(AttackPointX, AttackPointY, AttackPointZ, 10000000);
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
		}
		else
		{
			AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
		}
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (privat != actor)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				f0 = 0;
				if (SetHateGroup >= 0)
				{
					if (IsInCategory(SetHateGroup, attacker.getClassId()))
					{
						f0 = f0 + SetHateGroupRatio;
					}
				}
				if (attacker.getClassId() == SetHateOccupation)
				{
					f0 = f0 + SetHateOccupationRatio;
				}
				if (SetHateRace == attacker.getRaceId())
				{
					f0 = f0 + SetHateRaceRatio;
				}
				f0 = 1.000000f * damage / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * damage / (actor.getLevel() + 7)));
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * damage * privat._desirePoint * 10);
			}
		}
	}

}