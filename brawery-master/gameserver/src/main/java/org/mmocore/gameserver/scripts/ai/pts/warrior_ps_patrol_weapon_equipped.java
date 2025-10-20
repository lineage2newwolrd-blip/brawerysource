package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_ps_patrol_weapon_equipped extends warrior_physicalspecial
{
	public int SelfRangePhysicalSpecial = 0;
	public int DeBuff = 458752001;
	public int DeBuff2 = 458752001;

	public warrior_ps_patrol_weapon_equipped(final NpcInstance actor)
	{
		super(actor);
		MoveAroundSocial = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		AddMoveAroundDesire(5, 5);
		c_ai0 = actor;
		i_ai3 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{

		i_ai3 = 0;
		super.onEvtNoDesire();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (creature.isPlayer() && IsWeaponEquippedInHand(creature))
		{
			if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
			{
				AddUseSkillDesire(creature, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			c_ai0 = creature;
		}
		super.onEvtSeeCreature(creature);
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
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(SelfRangePhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(SelfRangePhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(SelfRangePhysicalSpecial) == 0)
						{
							AddUseSkillDesire(attacker, SelfRangePhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(DeBuff2) < actor._currentMp && Skill_GetConsumeHP(DeBuff2) < actor.currentHp && Skill_InReuseDelay(DeBuff2) == 0)
						{
							AddUseSkillDesire(attacker, DeBuff2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
				}
				if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
				{
					if (damage == 0)
					{
						damage = 1;
					}
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, (1.000000f * damage / (actor.getLevel() + 7)) * 10000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		if (skill_name_id == DeBuff)
		{
			if (i_ai3 == 0)
			{
				Say(AiUtils.MakeFString(1121006, "", "", "", "", ""));
				i_ai3 = 1;
			}
			AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 10000);
		}
	}

}