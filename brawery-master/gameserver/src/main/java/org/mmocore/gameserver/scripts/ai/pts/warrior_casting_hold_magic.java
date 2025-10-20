package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_hold_magic extends warrior
{
	public int HoldMagic = 265224193;

	public warrior_casting_hold_magic(final NpcInstance actor){super(actor);}

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
		Creature c0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			AddTimerEx(2001, 12000);
			i6 = AiUtils.Rand(100);
			if (DistFromMe(attacker) > 300 && i_ai0 == 0)
			{
				if (i6 < 50)
				{
					if (Skill_GetConsumeMP(HoldMagic) < actor._currentMp && Skill_GetConsumeHP(HoldMagic) < actor.currentHp && Skill_InReuseDelay(HoldMagic) == 0)
					{
						AddUseSkillDesire(attacker, HoldMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
			else if (DistFromMe(attacker) > 100 && i_ai0 == 0)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if ((top_desire_target == attacker && i6 < 50) || i6 < 10)
					{
						if (Skill_GetConsumeMP(HoldMagic) < actor._currentMp && Skill_GetConsumeHP(HoldMagic) < actor.currentHp && Skill_InReuseDelay(HoldMagic) == 0)
						{
							AddUseSkillDesire(attacker, HoldMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						i_ai0 = 1;
					}
				}
			}
			if (attacker.getWeaponType() == 6 && i_ai0 == 0)
			{
				if (AiUtils.Rand(100) < 50)
				{
					if (Skill_GetConsumeMP(HoldMagic) < actor._currentMp && Skill_GetConsumeHP(HoldMagic) < actor.currentHp && Skill_InReuseDelay(HoldMagic) == 0)
					{
						AddUseSkillDesire(attacker, HoldMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i6 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (timer_id == 2001)
		{
			c0 = GetLastAttacker();
			if (!IsNullCreature(c0))
			{
				i6 = AiUtils.Rand(100);
				if ((i6 < 80 && i_ai0 == 0))
				{
					if (Skill_GetConsumeMP(HoldMagic) < actor._currentMp && Skill_GetConsumeHP(HoldMagic) < actor.currentHp && Skill_InReuseDelay(HoldMagic) == 0)
					{
						AddUseSkillDesire(c0, HoldMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					i_ai0 = 1;
				}
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

}