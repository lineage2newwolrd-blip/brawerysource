package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class royal_rush_warrior_stone extends warrior_physicalspecial
{
	public int DeBuff = 277348353;
	public int SelfRangeDDMagic = 277348353;
	public int SelfBuff1 = 277348353;
	public int SelfBuff2 = 277348353;

	public royal_rush_warrior_stone(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(SelfBuff1) < actor._currentMp && Skill_GetConsumeHP(SelfBuff1) < actor.currentHp && Skill_InReuseDelay(SelfBuff1) == 0)
		{
			AddUseSkillDesire(actor, SelfBuff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && p_state != State.ATTACK)
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

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(SelfBuff2) < actor._currentMp && Skill_GetConsumeHP(SelfBuff2) < actor.currentHp && Skill_InReuseDelay(SelfBuff2) == 0)
					{
						AddUseSkillDesire(actor, SelfBuff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
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
				if (Skill_GetConsumeMP(SelfBuff2) < actor._currentMp && Skill_GetConsumeHP(SelfBuff2) < actor.currentHp && Skill_InReuseDelay(SelfBuff2) == 0)
				{
					AddUseSkillDesire(actor, SelfBuff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}