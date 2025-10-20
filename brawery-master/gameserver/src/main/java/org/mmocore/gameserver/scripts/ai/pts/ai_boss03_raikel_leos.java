package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_boss03_raikel_leos extends party_private
{
	public int different_level_9_attacked = 295895041;
	public int different_level_9_see_spelled = 276234241;

	public ai_boss03_raikel_leos(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance boss = getActor().getLeader();

		AddTimerEx(2001, 10000);
		i_ai0 = boss.flag;
		i_ai1 = 1;
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (timer_id == 2001)
		{
			if (((!IsNullCreature(boss) && (boss.flag != i_ai0 || DistFromMe(boss) > 3000)) && i_ai1 == 1) && IsMyBossAlive())
			{
				i_ai0 = boss.flag;
				InstantTeleport(actor, AiUtils.FloatToInt(boss.getX()), AiUtils.FloatToInt(boss.getY()), AiUtils.FloatToInt(boss.getZ()));
				RemoveAllAttackDesire();
			}
			AddTimerEx(2001, 10000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if (attacker.getLevel() <= boss.getLevel() + 8)
		{
			if (AiUtils.Rand(100) < 5)
			{
				if (Skill_GetConsumeMP(266534916/*@skill_4067_4*/) < actor._currentMp && Skill_GetConsumeHP(266534916/*@skill_4067_4*/) < actor.currentHp && Skill_InReuseDelay(266534916/*@skill_4067_4*/) == 0)
				{
					AddUseSkillDesire(attacker, 266534916/*@skill_4067_4*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		final NpcInstance boss = getActor().getLeader();

		if (privat == boss)
		{
			i_ai1 = 0;
		}
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.getLevel() > actor.getLevel() + 8)
		{
			if (AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(different_level_9_attacked)) == -1)
			{
				if (different_level_9_attacked == 295895041)
				{
					CastBuffForQuestReward(attacker, different_level_9_attacked);
					RemoveAttackDesire(attacker.getObjectId());
					return;
				}
				else
				{
					CastBuffForQuestReward(attacker, different_level_9_attacked);
				}
			}
		}
		else if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (damage == 0)
			{
				damage = 1;
			}
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 20000);
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		final NpcInstance actor = getActor();

		if (speller.getLevel() > actor.getLevel() + 8)
		{
			if (AiUtils.GetAbnormalLevel(speller, Skill_GetAbnormalType(different_level_9_see_spelled)) == -1)
			{
				if (different_level_9_see_spelled == 295895041)
				{
					CastBuffForQuestReward(speller, different_level_9_see_spelled);
					RemoveAttackDesire(speller.getObjectId());
					return;
				}
				else
				{
					CastBuffForQuestReward(speller, different_level_9_see_spelled);
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (creature.getLevel() > actor.getLevel() + 8)
		{
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(different_level_9_attacked)) == -1)
			{
				if (different_level_9_attacked == 295895041)
				{
					CastBuffForQuestReward(creature, different_level_9_attacked);
					RemoveAttackDesire(creature.getObjectId());
					return;
				}
				else
				{
					CastBuffForQuestReward(creature, different_level_9_attacked);
				}
			}
		}
		else
		{
			super.onEvtSeeCreature(creature);
		}
	}

}