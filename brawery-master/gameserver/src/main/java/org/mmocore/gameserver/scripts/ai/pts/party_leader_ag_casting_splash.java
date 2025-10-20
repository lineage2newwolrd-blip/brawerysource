package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_ag_casting_splash extends party_leader_casting_splash
{
	public party_leader_ag_casting_splash(final NpcInstance actor){super(actor);}

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

}