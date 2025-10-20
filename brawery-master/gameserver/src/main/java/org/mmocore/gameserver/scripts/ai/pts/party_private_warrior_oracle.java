package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_warrior_oracle extends warrior_physicalspecial
{
	public int selfbuff = 458752001;

	public party_private_warrior_oracle(final NpcInstance actor)
	{
		super(actor);
		PhysicalSpecial = 458752001;
	}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		if (Skill_GetConsumeMP(selfbuff) < actor._currentMp && Skill_GetConsumeHP(selfbuff) < actor.currentHp && Skill_InReuseDelay(selfbuff) == 0)
		{
			AddUseSkillDesire(actor, selfbuff, DesireType.HEAL, DesireMove.STAND, 10000000);
		}
		AddTimerEx(1100, 60 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		final NpcInstance boss = getActor().getLeader();

		if (IsMyBossAlive())
		{
			AddFollowDesire(boss, 5);
		}
		else
		{
			AddMoveAroundDesire(5, 5);
		}
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
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
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		if (privat != actor)
		{
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
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

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance boss = getActor().getLeader();

		if (timer_id == 1100)
		{
			if (GetTick() - i_ai1 > 5 * 60 * 1000)
			{
				AiUtils.SendScriptEvent(boss, ScriptEvent.SCE_PRIVATE_DESPAWN, 0);
				Despawn();
			}
			AddTimerEx(1100, 1 * 60 * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		int i0 = 0;

		if (script_event_arg1 == 1000)
		{
			Despawn();
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		Party party0 = null;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		party0 = AiUtils.GetParty(creature);
		if (!IsNullParty(party0))
		{
			if (AiUtils.Rand(100) < 50)
			{
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
			}
		}
		else if (AiUtils.Rand(100) < 20)
		{
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
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{

	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 20 && speller.isPlayer() && IsInCategory(Category.cleric_group, speller.getClassId()))
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(speller, PhysicalSpecial, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
			if (speller.isPlayer() || IsInCategory(Category.summon_npc_group, speller.getClassId()))
			{
				AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, 1 * 1000000);
			}
		}
	}

}