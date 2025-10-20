package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_bomb_spawn extends party_leader
{
	public int PhysicalSpecial = 264241153;

	public party_leader_bomb_spawn(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai0 = 0;
		actor._desirePoint = 10;
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if (i_ai0 == 0)
		{
			i_ai0 = 1;
			CreatePrivates(Privates);
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
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (!actor.isAlive())
		{
			return;
		}
		if ((script_event_arg1 == ScriptEvent.SCE_WATERING_QUIZ_NUMBER && p_state != State.ATTACK))
		{
			if (i_ai0 == 0)
			{
				i_ai0 = 1;
				CreatePrivates(Privates);
			}
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!AiUtils.IsNull(c0))
			{
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

}