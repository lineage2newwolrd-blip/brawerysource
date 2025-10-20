package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class kind_water_dragon_private extends party_private
{
	public int PhysicalSpecial = 458752001;
	public int selfbuff = 458752001;

	public kind_water_dragon_private(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(selfbuff) < actor._currentMp && Skill_GetConsumeHP(selfbuff) < actor.currentHp && Skill_InReuseDelay(selfbuff) == 0)
		{
			AddUseSkillDesire(actor, selfbuff, DesireType.HEAL, DesireMove.STAND, 10000000);
		}
		AddTimerEx(3001, 30 * 1000 + (AiUtils.Rand(30) * 1000));
		AddTimerEx(3002, 30 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0 && (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(23134209/*@skill_353_1*/)) <= 0 && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(87556097/*@skill_1336_1*/)) <= 0))
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Party party0 = null;
		int i0 = 0;
		int i1 = 0;
		Creature c0 = null;
		Creature c1 = null;
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (timer_id == 3001)
		{
			if (IsMyBossAlive())
			{
				RemoveAllDesire();
				AddAttackDesire(boss, DesireMove.MOVE_TO_TARGET, 10000000);
				i0 = AiUtils.Rand(20);
				if (i0 < 10)
				{
					i0 = i0 + 10;
				}
				AddTimerEx(3001, i0 * 1000);
			}
		}
		else if (timer_id == 3002)
		{
			if (!InMyTerritory(actor))
			{
				RemoveAllDesire();
				InstantTeleport(actor, start_x, start_y, start_z);
			}
			AddTimerEx(3002, 30 * 1000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (privat != boss)
		{
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0 && (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(23134209/*@skill_353_1*/)) <= 0 && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(87556097/*@skill_1336_1*/)) <= 0))
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
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
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * 100);
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		if (SeeCreatureAttackerTime == -1)
		{
			if (SetAggressiveTime == -1)
			{
				if (GetLifeTime() >= AiUtils.Rand(5) + 3 && InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
			else if (SetAggressiveTime == 0)
			{
				if (InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
			else
			{
				if (GetLifeTime() > SetAggressiveTime + AiUtils.Rand(4) && InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
		}
		else
		{
			if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
			{
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		Creature c0 = null;
		Creature c1 = null;
		Party party0 = null;
		int i1 = 0;
		int i2 = 0;
		final NpcInstance actor = getActor();

		DropItem1(actor, 9689, 1);
		if (actor.getClassId() == 1022270)
		{
			if (AiUtils.Rand(10000) <= 1008)
			{
				DropItem1(actor, 9595, 1);
			}
		}
		if (actor.getClassId() == 1022271)
		{
			if (AiUtils.Rand(10000) <= 1008)
			{
				DropItem1(actor, 9595, 1);
			}
		}
	}

}