package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_corpse_vampire_teleport extends wizard_corpse_vampire_basic
{
	public int TeleportEffect = 263979009;

	public wizard_corpse_vampire_teleport(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(3000, 10000);
		super.onEvtSpawn();
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
		if (p_state != State.ATTACK && GetLifeTime() > 7 && actor.isAlive())
		{
			if (DistFromMe(creature) > 200)
			{
				InstantTeleport(actor, AiUtils.FloatToInt(creature.getX()), AiUtils.FloatToInt(creature.getY()), AiUtils.FloatToInt(creature.getZ()));
				if (Skill_GetConsumeMP(TeleportEffect) < actor._currentMp && Skill_GetConsumeHP(TeleportEffect) < actor.currentHp && Skill_InReuseDelay(TeleportEffect) == 0)
				{
					AddUseSkillDesire(actor, TeleportEffect, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
			{
				return;
			}
			if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
			{
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
			}
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 3000)
		{
			LookNeighbor(500);
			AddTimerEx(3000, 10000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}