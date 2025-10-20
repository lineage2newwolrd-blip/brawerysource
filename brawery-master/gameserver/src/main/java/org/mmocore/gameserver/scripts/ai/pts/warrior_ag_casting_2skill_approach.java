package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_ag_casting_2skill_approach extends warrior
{
	public int SelfBuff = 458752001;
	public int PhysicalSpecial = 458752001;

	public warrior_ag_casting_2skill_approach(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		i_ai3 = 0;
		i_ai4 = 0;
		c_ai0 = AiUtils.GetNullCreature();
		c_ai1 = AiUtils.GetNullCreature();
	}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(5, 5);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
		{
			AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
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

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;

		if (AiUtils.Rand(100) < 20 && speller.isPlayer() && IsInCategory(Category.cleric_group, speller.getClassId()))
		{
			RemoveAllAttackDesire();
			if (speller.isPlayer() || IsInCategory(Category.summon_npc_group, speller.getClassId()))
			{
				AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, 1 * 200000);
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (script_event_arg2 == 10037 && actor.getClassId() == 1018329)
		{
			i_ai4 = i_ai4 + 1;
		}
		if (script_event_arg2 == 10038 && actor.getClassId() == 1018331)
		{
			i_ai3 = i_ai3 + 1;
		}
		if (script_event_arg1 == ScriptEvent.SCE_ATTACK_CENTER)
		{
			AddMoveToDesire(script_event_arg2, script_event_arg3, AiUtils.FloatToInt(actor.getZ()), 1000);
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (damage == 0)
			{
				damage = 1;
			}
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 1000);
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
		}
		if (!IsNullCreature(top_desire_target))
		{
			c_ai1 = top_desire_target;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		int i0 = 0;
		default_maker maker0 = null;
		final NpcInstance actor = getActor();

		i0 = AiUtils.Rand(100);
		if (i0 < 5 && actor.getClassId() == 1018329)
		{
			DropItem1(actor, 8556, 1);
		}
	}

}