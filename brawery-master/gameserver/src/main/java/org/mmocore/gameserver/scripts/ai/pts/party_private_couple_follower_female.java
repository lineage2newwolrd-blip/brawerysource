package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_couple_follower_female extends party_private_couple_follower
{
	public int PhysicalSpecial = 458752001;
	public int Buff = 458752001;
	public int Debuff = 458752001;
	public int Private = 0;

	public party_private_couple_follower_female(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
		{
			AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(Debuff)) <= 0)
					{
						if (Skill_GetConsumeMP(Debuff) < actor._currentMp && Skill_GetConsumeHP(Debuff) < actor.currentHp && Skill_InReuseDelay(Debuff) == 0)
						{
							AddUseSkillDesire(attacker, Debuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
						{
							AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(Buff)) <= 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
					{
						if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
						{
							AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
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
			if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(Debuff)) <= 0)
			{
				if (Skill_GetConsumeMP(Debuff) < actor._currentMp && Skill_GetConsumeHP(Debuff) < actor.currentHp && Skill_InReuseDelay(Debuff) == 0)
				{
					AddUseSkillDesire(attacker, Debuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
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
			if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(Debuff)) <= 0)
			{
				if (Skill_GetConsumeMP(Debuff) < actor._currentMp && Skill_GetConsumeHP(Debuff) < actor.currentHp && Skill_InReuseDelay(Debuff) == 0)
				{
					AddUseSkillDesire(attacker, Debuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.GetAbnormalLevel(attacker, Skill_GetAbnormalType(Buff)) <= 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
				{
					AddUseSkillDesire(privat, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

}