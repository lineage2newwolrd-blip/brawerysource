package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class royal_rush_warrior_physicalspecial extends warrior_physicalspecial
{
	public int SelfBuff = 0;
	public int WeaponID = 0;

	public royal_rush_warrior_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		if (SelfBuff != 0)
		{
			if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
			{
				AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			AddTimerEx(3000, 1000 * 60 * 2);
		}
		i_ai0 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 50 && WeaponID !=0)
		{
			EquipItem(WeaponID);
			SetEnchantOfWeapon(15);
			i_ai0 = 1;
		}
		if ((attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())) && i_ai0 == 0)
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.Rand(100) < 33 && top_desire_target == attacker && DistFromMe(attacker) < 100)
				{
					if (i_ai0 == 0)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
						{
							AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						UseSoulShot(20);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state != State.ATTACK)
		{
			if (AiUtils.Rand(100) < 33 && DistFromMe(attacker) > 100)
			{
				if (i_ai0 == 0)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					UseSoulShot(20);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 3000)
		{
			if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(SelfBuff)) <= 0)
			{
				if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
				{
					AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			AddTimerEx(3000, 1000 * 60 * 2);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;

		if (script_event_arg1 == 1234)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				if (AiUtils.Rand(100) < 80)
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 300);
				}
				else
				{
					RemoveAllAttackDesire();
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

}