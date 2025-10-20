package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_ag_private_spawn extends party_private_physicalspecial
{
	public party_private_ag_private_spawn(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{

		if (script_event_arg1 == ScriptEvent.SCE_SUICIDE)
		{
			Despawn();
		}
		if (script_event_arg1 == ScriptEvent.SCE_PRIVATE_DESPAWN)
		{
			Despawn();
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

}