package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_physicalspecial extends party_leader
{
	public int PhysicalSpecial = 264241153;

	public party_leader_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			i6 = AiUtils.Rand(100);
			if (!IsNullCreature(top_desire_target))
			{
				if (actor.currentHp < actor.getMaxHp() * 0.200000f && i6 < 33 && i_ai0 == 0 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())))
		{
			i6 = AiUtils.Rand(100);
			if ((i6 < 33 && i_ai0 == 0))
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				i_ai0 = 1;
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}