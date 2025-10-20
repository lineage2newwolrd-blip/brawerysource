package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_corpse_necro extends warrior
{
	public int SummonPrivate = 1020130;
	public String SummonPrivateAI = "warrior";
	public int ClearCorpse = 262209537;
	public int DDMagic = 262209537;
	public int PhysicalSpecial = 264241153;

	public warrior_corpse_necro(final NpcInstance actor){super(actor);}

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
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
						{
							AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 40)
					{
						if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
						{
							AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK)
		{
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
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