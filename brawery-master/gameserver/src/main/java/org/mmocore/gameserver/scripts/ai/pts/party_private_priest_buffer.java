package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_priest_buffer extends party_private
{
	public int Buff1 = 458752001;
	public int Buff2 = 458752001;
	public int Buff3 = 458752001;
	public int Buff4 = 458752001;
	public int Buff5 = 458752001;
	public int Buff6 = 458752001;

	public party_private_priest_buffer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(5001, 20000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance boss = getActor().getLeader();
		final NpcInstance actor = getActor();

		if ((!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()) && creature.getLeader() == actor.getLeader()) || creature == boss)
		{
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(Buff1)) <= 0 && p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(Buff1) < actor._currentMp && Skill_GetConsumeHP(Buff1) < actor.currentHp && Skill_InReuseDelay(Buff1) == 0)
				{
					AddUseSkillDesire(creature, Buff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(Buff2)) <= 0 && p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(Buff2) < actor._currentMp && Skill_GetConsumeHP(Buff2) < actor.currentHp && Skill_InReuseDelay(Buff2) == 0)
				{
					AddUseSkillDesire(creature, Buff2, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(Buff3)) <= 0 && p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(Buff3) < actor._currentMp && Skill_GetConsumeHP(Buff3) < actor.currentHp && Skill_InReuseDelay(Buff3) == 0)
				{
					AddUseSkillDesire(creature, Buff3, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(Buff4)) <= 0 && p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(Buff4) < actor._currentMp && Skill_GetConsumeHP(Buff4) < actor.currentHp && Skill_InReuseDelay(Buff4) == 0)
				{
					AddUseSkillDesire(creature, Buff4, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(Buff5)) <= 0 && p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(Buff5) < actor._currentMp && Skill_GetConsumeHP(Buff5) < actor.currentHp && Skill_InReuseDelay(Buff5) == 0)
				{
					AddUseSkillDesire(creature, Buff5, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(Buff6)) <= 0 && p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(Buff6) < actor._currentMp && Skill_GetConsumeHP(Buff6) < actor.currentHp && Skill_InReuseDelay(Buff6) == 0)
				{
					AddUseSkillDesire(creature, Buff6, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 5001)
		{
			LookNeighbor(300);
			AddTimerEx(5001, 20000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}