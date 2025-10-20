package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_demonic_swordman_oracle extends party_leader_warrior_oracle
{
	public int W_LongRangeDDMagic = 458752001;

	public party_leader_demonic_swordman_oracle(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Party party0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 3)
		{
			if (Skill_GetConsumeMP(debuff) < actor._currentMp && Skill_GetConsumeHP(debuff) < actor.currentHp && Skill_InReuseDelay(debuff) == 0)
			{
				AddUseSkillDesire(attacker, debuff, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
		}
		if (DistFromMe(attacker) >= 100)
		{
			if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
				{
					AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
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
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
				}
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
			if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
				{
					AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
			}
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
	protected void onEvtDead(Creature attacker)
	{
		Creature c0 = null;
		Creature c1 = null;
		Party party0 = null;
		int i1 = 0;
		int i2 = 0;
		final NpcInstance actor = getActor();

		if (actor.getClassId() == 1022261)
		{
			if (AiUtils.Rand(10000) <= 1171)
			{
				DropItem1(actor, 9594, 1);
			}
		}
		else if (actor.getClassId() == 1022265)
		{
			if (AiUtils.Rand(10000) <= 575)
			{
				DropItem1(actor, 9596, 1);
			}
		}
	}

}