package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_primeval extends party_private_physicalspecial
{
	public int BroadCastReception = 0;
	public int ag_type = 1;
	public int mobile_type = 0;
	public int is_dbg = 0;

	public party_private_primeval(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (attacker.getClassId() == 1022215 || attacker.getClassId() == 1022217 || attacker.getClassId() == 1022216)
		{
			Suicide();
			return;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": script_event";
			Say(s0);
			s0 = "";
		}
		if (script_event_arg1 == ScriptEvent.SCE_ONE_POINT_ATTACK)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0) && c0.isPlayer() && BroadCastReception == 1)
			{
				if (is_dbg > 2)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": script_event::" + "replace top_desire_target as " + c0.getName();
					Say(s0);
					s0 = "";
				}
				RemoveAllAttackDesire();
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1 * 100);
				}
			}
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
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
			AddMoveToDesire(start_x, start_y, start_z, 30);
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature" + " creature:" + creature.getName();
			Say(s0);
			s0 = "";
		}
		if (ag_type == 1)
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

}