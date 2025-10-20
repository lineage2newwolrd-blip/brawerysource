package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class royal_rush_party_leader extends party_leader
{
	public int PhysicalSpecial1 = 262209537;
	public int SummonSlave = 1020130;

	public royal_rush_party_leader(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				if (AiUtils.Rand(100) < 33 && i_ai0 == 0)
				{
					CreateOnePrivateEx(SummonSlave, "sacrifice_scarab", 0, 0, AiUtils.FloatToInt(attacker.getX()), AiUtils.FloatToInt(attacker.getY()), AiUtils.FloatToInt(attacker.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(top_desire_target), 0);
					i_ai0 = 1;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}