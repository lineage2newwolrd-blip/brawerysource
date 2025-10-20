package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_ag_physicalspecial extends party_leader_physicalspecial
{
	public party_leader_ag_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if (GetLifeTime() > 7 && InMyTerritory(actor) && p_state != State.ATTACK)
		{
			if (DistFromMe(creature) > 100 && i_ai0 == 0)
			{
				if (AiUtils.Rand(100) < 33)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(creature, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
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
		super.onEvtSeeCreature(creature);
	}

}