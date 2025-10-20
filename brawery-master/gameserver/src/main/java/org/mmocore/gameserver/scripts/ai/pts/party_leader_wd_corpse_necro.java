package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_wd_corpse_necro extends party_leader_wizard_dd2
{
	public int SummonPrivate = 1020130;
	public String SummonPrivateAI = "warrior";
	public int ClearCorpse = 262209537;
	public int DDMagic1 = 262209537;
	public int DDmagic2 = 262209537;

	public party_leader_wd_corpse_necro(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(3456, 5000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if (!creature.isAlive())
		{
			if (p_state == State.ATTACK && AiUtils.Rand(100) < 50 && DistFromMe(creature) < 100)
			{
				if (!IsNullCreature(top_desire_target))
				{
					CreateOnePrivateEx(SummonPrivate, SummonPrivateAI, 0, 0, AiUtils.FloatToInt(creature.getX()), AiUtils.FloatToInt(creature.getY()), AiUtils.FloatToInt(creature.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 0);
					if (Skill_GetConsumeMP(ClearCorpse) < actor._currentMp && Skill_GetConsumeHP(ClearCorpse) < actor.currentHp && Skill_InReuseDelay(ClearCorpse) == 0)
					{
						AddUseSkillDesire(creature, ClearCorpse, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtSeeCreature(creature);
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

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
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
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				if (i_ai0 == 0)
				{
					if (top_desire_target == attacker)
					{
						if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 40)
						{
							if (Skill_GetConsumeMP(DDMagic1) < actor._currentMp && Skill_GetConsumeHP(DDMagic1) < actor.currentHp && Skill_InReuseDelay(DDMagic1) == 0)
							{
								AddUseSkillDesire(attacker, DDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}