package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_summon_pc extends warrior
{
	public int SummonMagic = 458752001;
	public int PhysicalSpecial = 458752001;
	public int SkillCastingTime = 2000;

	public warrior_casting_summon_pc(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		Creature c0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			i6 = AiUtils.Rand(100);
			c_ai0 = attacker;
			if (DistFromMe(attacker) > 300 && i_ai0 == 0)
			{
				if (i6 < 50)
				{
					if (Skill_GetConsumeMP(SummonMagic) < actor._currentMp && Skill_GetConsumeHP(SummonMagic) < actor.currentHp && Skill_InReuseDelay(SummonMagic) == 0)
					{
						AddUseSkillDesire(attacker, SummonMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					if (Skill_GetConsumeMP(SummonMagic) < actor._currentMp && Skill_GetConsumeHP(SummonMagic) < actor.currentHp && Skill_InReuseDelay(SummonMagic) == 0)
					{
						AddUseSkillDesire(attacker, SummonMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						i_ai0 = 1;
						i_ai1 = 1;
					}
				}
			}
			else if (DistFromMe(attacker) > 100 && i_ai0 == 0)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if ((top_desire_target == attacker && i6 < 50) || i6 < 10)
					{
						if (Skill_GetConsumeMP(SummonMagic) < actor._currentMp && Skill_GetConsumeHP(SummonMagic) < actor.currentHp && Skill_InReuseDelay(SummonMagic) == 0)
						{
							AddUseSkillDesire(attacker, SummonMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							i_ai0 = 1;
							i_ai1 = 1;
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if ((success && i_ai1 == 1))
		{
			InstantTeleport(c_ai0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()));
			i_ai1 = 0;
		}
		if (!IsNullCreature(top_desire_target))
		{
			if (AiUtils.Rand(100) < 33 && top_desire_target == c_ai0)
			{
				if (PhysicalSpecial != 0)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(c_ai0, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
		}
	}

}