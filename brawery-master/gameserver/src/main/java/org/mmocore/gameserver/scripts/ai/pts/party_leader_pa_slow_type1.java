package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_pa_slow_type1 extends party_leader_slow_type_bagic
{
	public party_leader_pa_slow_type1(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!IsNullCreature(top_desire_target))
		{
			if ((p_state == State.ATTACK && attacker != top_desire_target))
			{
				if (DistFromMe(top_desire_target) > 100 && DistFromMe(attacker) < 100)
				{
					if (AiUtils.Rand(100) < 80)
					{
						RemoveAllAttackDesire();
						if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
						{
							f0 = 0;
							if (SetHateGroup >= 0)
							{
								if (IsInCategory(SetHateGroup, attacker.getClassId()))
								{
									f0 = f0 + SetHateGroupRatio;
								}
							}
							if (attacker.getClassId() == SetHateOccupation)
							{
								f0 = f0 + SetHateOccupationRatio;
							}
							if (SetHateRace == attacker.getRaceId())
							{
								f0 = f0 + SetHateRaceRatio;
							}
							f0 = 1.000000f * damage / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * damage / (actor.getLevel() + 7)));
							AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * 30);
						}
					}
				}
			}
		}
		if (DistFromMe(attacker) < 200 && AiUtils.Rand(100) < 10)
		{
			if (Skill_GetConsumeMP(DDMagicSlow) < actor._currentMp && Skill_GetConsumeHP(DDMagicSlow) < actor.currentHp && Skill_InReuseDelay(DDMagicSlow) == 0)
			{
				AddUseSkillDesire(attacker, DDMagicSlow, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}