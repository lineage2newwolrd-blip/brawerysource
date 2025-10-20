package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_wizard_ag_saint extends party_leader_wizard_saint
{
	public party_leader_wizard_ag_saint(final NpcInstance actor){super(actor);}

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
			if (AiUtils.Rand(100) < 33 && DistFromMe(creature) < 40)
			{
				if (Skill_GetConsumeMP(RangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(RangeDeBuff) < actor.currentHp)
				{
					if (Skill_InReuseDelay(RangeDeBuff) == 0)
					{
						AddUseSkillDesire(actor, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(actor, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (Skill_GetConsumeMP(W_LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic1) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_LongRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(creature, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(creature, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
			super.onEvtSeeCreature(creature);
		}
	}

}