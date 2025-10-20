package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_selfrangeddmagic_buff extends warrior
{
	public int SelfRangeDDMagic = 264306689;
	public int Buff = 264110083;

	public warrior_casting_selfrangeddmagic_buff(final NpcInstance actor){super(actor);}

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
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
			{
				if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(Buff)) <= 0)
				{
					if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
					{
						AddUseSkillDesire(actor, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && top_desire_target != attacker && DistFromMe(attacker) < 200)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
				if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 33 && top_desire_target != attacker)
				{
					if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
			if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
			{
				if (AiUtils.GetAbnormalLevel(victim, Skill_GetAbnormalType(Buff)) <= 0)
				{
					if (Skill_GetConsumeMP(Buff) < actor._currentMp && Skill_GetConsumeHP(Buff) < actor.currentHp && Skill_InReuseDelay(Buff) == 0)
					{
						AddUseSkillDesire(victim, Buff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}