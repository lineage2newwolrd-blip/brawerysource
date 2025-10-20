package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_physicalspecial_hold extends warrior_physicalspecial
{
	public int HelpCastRange = 400;
	public int DDMagic = 262209537;

	public warrior_physicalspecial_hold(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveToDesire(start_x, start_y, start_z, 30);
	}

	@Override
	protected void onEvtSpawn()
	{

		AddMoveToDesire(start_x, start_y, start_z, 30);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if ((p_state != State.ATTACK || p_state != State.USE_SKILL))
		{
			BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_STAMP, AiUtils.GetIndexFromCreature(attacker), HelpCastRange);
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (DistFromMe(attacker) < 80)
			{
				if (AiUtils.Rand(100) < 33)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
				}
			}
			else if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
			{
				AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
		}
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
			AddAttackDesire(attacker, DesireMove.STAND, f0 * 100);
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (DistFromMe(attacker) < 80)
			{
				if (AiUtils.Rand(100) < 33)
				{
					if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
					{
						AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
				}
			}
			else if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
			{
				AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
		}
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
			AddAttackDesire(attacker, DesireMove.STAND, f0 * 100);
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{

	}

	@Override
	protected void onEvtMoveFinished(int x, int y, int z)
	{

		if ((x == start_x && y == start_y && z == start_z))
		{
			AddDoNothingDesire(40, 30);
		}
		else
		{
			AddMoveToDesire(start_x, start_y, start_z, 30);
		}
	}

	@Override
	protected void onEvtAtkFinished(Creature target)
	{
		final NpcInstance actor = getActor();

		if (!target.isAlive() && !target.isPlayer())
		{
			if (IsInCategory(Category.summon_npc_group, target.getClassId()))
			{
				if (!IsNullCreature(target.getPlayer()) && DistFromMe(target.getPlayer()) < 80)
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
						{
							AddUseSkillDesire(target.getPlayer(), PhysicalSpecial, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
					}
				}
				else if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
				{
					AddUseSkillDesire(target.getPlayer(), DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
				}
			}
			if (target.getPlayer().isPlayer() || IsInCategory(Category.summon_npc_group, target.getPlayer().getClassId()))
			{
				AddAttackDesire(target.getPlayer(), DesireMove.STAND, 1 * 50);
			}
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		final NpcInstance actor = getActor();

		if (!target.isAlive() && !target.isPlayer())
		{
			if (IsInCategory(Category.summon_npc_group, target.getClassId()))
			{
				if (!IsNullCreature(target.getPlayer()) && DistFromMe(target.getPlayer()) < 80)
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
						{
							AddUseSkillDesire(target.getPlayer(), PhysicalSpecial, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
					}
				}
				else if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
				{
					AddUseSkillDesire(target.getPlayer(), DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
				}
			}
			if (target.getPlayer().isPlayer() || IsInCategory(Category.summon_npc_group, target.getPlayer().getClassId()))
			{
				AddAttackDesire(target.getPlayer(), DesireMove.STAND, 1 * 50);
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (Skill_GetEffectPoint(skill_name_id) > 0)
		{
			i0 = Skill_GetEffectPoint(skill_name_id);
			f0 = 0;
			if (SetHateGroup >= 0)
			{
				if (IsInCategory(SetHateGroup, speller.getClassId()))
				{
					f0 = f0 + SetHateGroupRatio;
				}
			}
			if (speller.getClassId() == SetHateOccupation)
			{
				f0 = f0 + SetHateOccupationRatio;
			}
			if (SetHateRace == speller.getRaceId())
			{
				f0 = f0 + SetHateRaceRatio;
			}
			f0 = 1.000000f * 1 / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * 1 / (actor.getLevel() + 7)));
			AddAttackDesire(speller, DesireMove.STAND, f0 * 150);
		}
	}

}