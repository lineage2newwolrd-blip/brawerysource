package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_ag_corpse_zombie_basic extends warrior_corpse_zombie_basic
{
	public warrior_ag_corpse_zombie_basic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if (IsTeleport != 0 && GetLifeTime() > 7 && DistFromMe(creature) > 100 && p_state != State.ATTACK && AiUtils.Rand(100) < 10 && actor.currentHp > 0)
		{
			InstantTeleport(actor, AiUtils.FloatToInt(creature.getX()), AiUtils.FloatToInt(creature.getY()), AiUtils.FloatToInt(creature.getZ()));
			if (Skill_GetConsumeMP(IsTeleport) < actor._currentMp && Skill_GetConsumeHP(IsTeleport) < actor.currentHp && Skill_InReuseDelay(IsTeleport) == 0)
			{
				AddUseSkillDesire(actor, IsTeleport, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
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