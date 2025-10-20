package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_saint_transform extends warrior_physicalspecial
{
	public int SelfRangeDDMagic = 263979009;
	public int Dispell = 272039937;

	public warrior_saint_transform(final NpcInstance actor)
	{
		super(actor);
		PhysicalSpecial = 264241153;
	}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Party party0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		party0 = AiUtils.GetParty(attacker);
		if (!IsNullParty(party0))
		{
			if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(SelfRangeDDMagic) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(actor, SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (AiUtils.Rand(100) < 90 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 90 && i_ai0 == 0)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (Skill_GetConsumeMP(Dispell) < actor._currentMp && Skill_GetConsumeHP(Dispell) < actor.currentHp)
					{
						if (Skill_InReuseDelay(Dispell) == 0)
						{
							AddUseSkillDesire(top_desire_target, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(top_desire_target, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(top_desire_target, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(Dispell) < actor._currentMp && Skill_GetConsumeHP(Dispell) < actor.currentHp)
				{
					if (Skill_InReuseDelay(Dispell) == 0)
					{
						AddUseSkillDesire(attacker, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
				i_ai0 = 1;
			}
			else if (AiUtils.Rand(100) < 80 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 40 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 50 && i_ai0 < 1)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (Skill_GetConsumeMP(Dispell) < actor._currentMp && Skill_GetConsumeHP(Dispell) < actor.currentHp)
					{
						if (Skill_InReuseDelay(Dispell) == 0)
						{
							AddUseSkillDesire(top_desire_target, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(top_desire_target, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(top_desire_target, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(Dispell) < actor._currentMp && Skill_GetConsumeHP(Dispell) < actor.currentHp)
				{
					if (Skill_InReuseDelay(Dispell) == 0)
					{
						AddUseSkillDesire(attacker, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
				i_ai0 = 2;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}