package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_corpse_necro_summon extends wizard_corpse_necro
{
	public int ClearCorpse = 272039937;
	public int SummonPrivate = 1020130;
	public String SummonPrivateAI = "warrior";

	public wizard_corpse_necro_summon(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(3456, 5000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;

		if (!creature.isAlive() && !creature.isPlayer() && AiUtils.Rand(100) < 30 && DistFromMe(creature) < 200)
		{
			h0 = GetMaxHateInfo(0);
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (!IsNullCreature(h0.attacker))
				{
					UseSkill(creature, ClearCorpse);
					CreateOnePrivateEx(SummonPrivate, SummonPrivateAI, 0, 0, AiUtils.FloatToInt(creature.getX()), AiUtils.FloatToInt(creature.getY()), AiUtils.FloatToInt(creature.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(h0.attacker), 0);
				}
			}
			return;
		}
		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		else
		{
			super.onEvtSeeCreature(creature);
		}
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
		int i0 = 0;
		int i1 = 0;
		final NpcInstance actor = getActor();

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
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 3456)
		{
			LookNeighbor(200);
			AddTimerEx(3456, 5000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}