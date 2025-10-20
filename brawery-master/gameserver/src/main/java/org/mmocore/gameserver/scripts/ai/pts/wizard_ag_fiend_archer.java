package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_ag_fiend_archer extends wizard_fiend_archer
{
	public wizard_ag_fiend_archer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i6 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if (GetLifeTime() > 7 && InMyTerritory(actor) && GetHateInfoCount() == 0)
		{
			if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_FiendArcher) == 0)
				{
					AddUseSkillDesire(creature, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(creature, W_FiendArcher, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
			}
			if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
			{
				return;
			}
			i0 = 0;
			if (SeeCreatureAttackerTime == -1)
			{
				if (SetAggressiveTime == -1)
				{
					if (GetLifeTime() >= AiUtils.Rand(5) + 3 && InMyTerritory(actor))
					{
						i0 = 1;
					}
				}
				else if (SetAggressiveTime == 0)
				{
					if (InMyTerritory(actor))
					{
						i0 = 1;
					}
				}
				else
				{
					if (GetLifeTime() > SetAggressiveTime + AiUtils.Rand(4) && InMyTerritory(actor))
					{
						i0 = 1;
					}
				}
			}
			else
			{
				if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
				{
					i0 = 1;
				}
			}
			if (GetHateInfoCount() == 0 && i0 == 1)
			{
				AddHateInfo(creature, 300, 0, 1, 1);
			}
			else
			{
				AddHateInfo(creature, 100, 0, 1, 1);
			}
		}
		super.onEvtSeeCreature(creature);
	}

}