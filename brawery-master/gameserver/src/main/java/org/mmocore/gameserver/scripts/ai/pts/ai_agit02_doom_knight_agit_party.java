package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_agit02_doom_knight_agit_party extends warrior_passive_casting_curse
{
	public ai_agit02_doom_knight_agit_party(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(5, 5);
	}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(3001, 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (timer_id == 3001)
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
			AddTimerEx(3001, 60 * 1000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			if (AiUtils.Rand(100) < 10 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(DeBuff)) <= 0)
			{
				if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
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