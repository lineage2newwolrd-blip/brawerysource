package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_agit02_doom_archer_agit_party extends warrior_passive_casting_enchant_clan
{
	public ai_agit02_doom_archer_agit_party(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(5, 5);
	}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(4001, 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (timer_id == 4001)
		{
			if (!InMyTerritory(actor) && AiUtils.Rand(3) < 1)
			{
				InstantTeleport(actor, AiUtils.FloatToInt(boss.getX()), AiUtils.FloatToInt(boss.getY()), AiUtils.FloatToInt(boss.getZ()));
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
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK) && i_ai1 == 0)
		{
			if (AiUtils.Rand(100) < 50 && AiUtils.GetAbnormalLevel(privat, Skill_GetAbnormalType(Buff)) <= 0 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(privat, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		i_ai1 = 1;
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, damage / actor.getMaxHp() / 0.050000f * damage * privat._desirePoint / 1000000);
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (Skill_GetEffectPoint(skill_name_id) > 0)
		{
			if ((p_state == State.ATTACK && top_desire_target == target))
			{
				AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, Skill_GetEffectPoint(skill_name_id) / actor.getMaxHp() / 0.050000f * 150);
			}
		}
		if (GetPathfindFailCount() > 10 && speller == top_desire_target && AiUtils.FloatToInt(actor.currentHp) != AiUtils.FloatToInt(actor.getMaxHp()))
		{
			InstantTeleport(actor, AiUtils.FloatToInt(speller.getX()), AiUtils.FloatToInt(speller.getY()), AiUtils.FloatToInt(speller.getZ()));
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{

	}

}