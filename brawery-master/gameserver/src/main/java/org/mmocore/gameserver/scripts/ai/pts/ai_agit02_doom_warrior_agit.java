package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_agit02_doom_warrior_agit extends warrior_aggressive_casting_enchant_clan
{
	public int Unit = 0;

	public ai_agit02_doom_warrior_agit(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(5, 5);
	}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		AddTimerEx(4001, 1000);
		actor.flag = Unit;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 4001)
		{
			if (!InMyTerritory(actor) && AiUtils.Rand(3) < 1)
			{
				InstantTeleport(actor, start_x, start_y, start_z);
				RemoveAllAttackDesire();
			}
			if (AiUtils.Rand(5) < 1)
			{
				RandomizeAttackDesire();
			}
			AddTimerEx(4001, 60 * 1000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (actor.flag == victim.flag)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, damage / actor.getMaxHp() / 0.050000f * 100);
			}
		}
	}

}