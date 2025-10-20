package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_ag_casting_debuff2 extends warrior_casting_debuff2
{
	public warrior_ag_casting_debuff2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if (GetLifeTime() > 7 && InMyTerritory(actor) && p_state == State.MOVE_AROUND)
		{
			if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(DeBuff1)) <= 0)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(creature, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) < 33 && AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(DeBuff2)) <= 0)
			{
				if (Skill_GetConsumeMP(DeBuff2) < actor._currentMp && Skill_GetConsumeHP(DeBuff2) < actor.currentHp && Skill_InReuseDelay(DeBuff2) == 0)
				{
					AddUseSkillDesire(creature, DeBuff2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
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
		super.onEvtSeeCreature(creature);
	}

}