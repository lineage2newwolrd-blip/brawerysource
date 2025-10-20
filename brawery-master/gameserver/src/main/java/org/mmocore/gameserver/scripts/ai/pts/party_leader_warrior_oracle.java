package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_warrior_oracle extends warrior
{
	public int debuff = 458752001;
	public int PhysicalSpecial = 458752001;
	public String ai_type = "party_private_warrior_oracle";
	public int silhouette = 1020116;
	public int bSpawn = 0;

	public party_leader_warrior_oracle(final NpcInstance actor)
	{
		super(actor);
		Attack_DecayRatio = 6.600000f;
	}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = GetTick();
		AddTimerEx(1100, 60 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Party party0 = null;
		int i0 = 0;
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
		if (AiUtils.Rand(100) < 3)
		{
			if (Skill_GetConsumeMP(debuff) < actor._currentMp && Skill_GetConsumeHP(debuff) < actor.currentHp && Skill_InReuseDelay(debuff) == 0)
			{
				AddUseSkillDesire(attacker, debuff, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
		}
		if (attacker.isPlayer())
		{
			party0 = AiUtils.GetParty(attacker);
		}
		if (!IsNullParty(party0))
		{
			i0 = party0.getMemberCount();
			if (bSpawn == 0)
			{
				if (i0 >= 5)
				{
					if (AiUtils.Rand(100) < 70)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
						{
							AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (i_ai0 == 0)
					{
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 80, AiUtils.FloatToInt(actor.getY()) + 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 40, AiUtils.FloatToInt(actor.getY()) + 40, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) - 80, AiUtils.FloatToInt(actor.getY()) - 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) - 40, AiUtils.FloatToInt(actor.getY()) - 40, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						i_ai0 = 1;
					}
				}
				else if (i0 >= 3)
				{
					if (AiUtils.Rand(100) < 50)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
						{
							AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (i_ai0 == 0)
					{
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 80, AiUtils.FloatToInt(actor.getY()) + 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 40, AiUtils.FloatToInt(actor.getY()) + 40, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						i_ai0 = 1;
					}
				}
				else if (AiUtils.Rand(100) < 30)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		else if (AiUtils.Rand(100) < 30)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
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
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
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
		Party party0 = null;
		float f0 = 0;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		party0 = AiUtils.GetParty(attacker);
		if (!IsNullParty(party0))
		{
			if (AiUtils.Rand(100) < 50)
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
		else if (AiUtils.Rand(100) < 20)
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

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 1100)
		{
			if (GetTick() - i_ai1 > 5 * 60 * 1000 && i_ai0 == 1)
			{
				i_ai0 = 0;
				BroadcastScriptEvent(1000, 0, 300);
			}
			AddTimerEx(1100, 1 * 60 * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		int i0 = 0;

		if (script_event_arg1 == ScriptEvent.SCE_PRIVATE_DESPAWN)
		{
			i_ai0 = 0;
			BroadcastScriptEvent(1000, 0, 300);
		}
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

		if (actor.getClassId() == 1022259)
		{
			if (AiUtils.Rand(10000) <= 2642)
			{
				DropItem1(actor, 9593, 1);
			}
		}
		else if (actor.getClassId() == 1022263)
		{
			if (AiUtils.Rand(10000) <= 1403)
			{
				DropItem1(actor, 9594, 1);
			}
		}
	}

}