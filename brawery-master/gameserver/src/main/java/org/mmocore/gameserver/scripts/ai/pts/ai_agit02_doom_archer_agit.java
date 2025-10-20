package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_agit02_doom_archer_agit extends warrior_passive_casting_enchant_clan
{
	public int Unit = 0;

	public ai_agit02_doom_archer_agit(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(5, 5);
	}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		actor.flag = Unit;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai1 == 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			i_ai1 = 1;
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			AddAttackDesire(attacker, DesireMove.STAND, damage / actor.getMaxHp() / 0.050000f * 100);
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK & i_ai1 == 0) && actor.flag == victim.flag)
		{
			if (AiUtils.Rand(100) < 50 && AiUtils.GetAbnormalLevel(victim, Skill_GetAbnormalType(Buff)) <= 0 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(victim, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		i_ai1 = 1;
		if (actor.flag == victim.flag)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				AddAttackDesire(attacker, DesireMove.STAND, damage / actor.getMaxHp() / 0.050000f * 100);
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		final NpcInstance actor = getActor();

		if (Skill_GetEffectPoint(skill_name_id) > 0)
		{
			AddAttackDesire(speller, DesireMove.STAND, Skill_GetEffectPoint(skill_name_id) / actor.getMaxHp() / 0.050000f * 150);
		}
	}

	@Override
	protected void onEvtClanObjectAttacked(Creature attacker, Creature victim, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.getClassId() == 1014737)
		{
			AddAttackDesire(attacker.getPlayer(), DesireMove.STAND, 5000.000000f);
			AddAttackDesire(attacker, DesireMove.STAND, 1000.000000f);
		}
		else if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			AddAttackDesire(attacker, DesireMove.STAND, damage / actor.getMaxHp() / 0.050000f * 50);
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		AddAttackDesire(creature, DesireMove.STAND, 200);
	}

}