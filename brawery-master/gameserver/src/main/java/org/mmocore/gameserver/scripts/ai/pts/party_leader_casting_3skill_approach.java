package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_casting_3skill_approach extends party_leader
{
	public int SelfBuff = 263979009;
	public int SelfRangeDDMagic = 264306689;
	public int PhysicalSpecial = 264241153;

	public party_leader_casting_3skill_approach(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		AddTimerEx(2001, 10000);
		if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
		{
			AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 2001)
		{
			if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(SelfBuff)) <= 0 && p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
				{
					AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			AddTimerEx(2001, 10000);
		}
		if (timer_id == 2002)
		{
			if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
			{
				AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!IsNullCreature(top_desire_target))
		{
			if ((attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())) && top_desire_target == attacker)
			{
				if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 33)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (AiUtils.Rand(100) < 33)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
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
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

}