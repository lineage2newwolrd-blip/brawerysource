package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class doorman_of_drain_basic extends wizard
{
	public int pass1 = 8064;
	public int pass2 = 8065;
	public int pass3 = 8067;
	public int MagicHeal = 0;
	public int DDMagic = 262209537;
	public int SkillRange = 500;

	public doorman_of_drain_basic(final NpcInstance actor){super(actor);}

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
		int i1 = 0;
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		h0 = GetMaxHateInfo(0);
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(DDMagic) == 0)
						{
							AddUseSkillDesire(top_desire_target, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
						else
						{
							AddUseSkillDesire(top_desire_target, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						if (CanAttack(top_desire_target))
						{
							AddAttackDesire(top_desire_target, DesireMove.STAND, 1000);
						}
					}
				}
				else if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					if (CanAttack(attacker))
					{
						AddAttackDesire(attacker, DesireMove.STAND, 1000);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		int i0 = 0;
		int i1 = 0;
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
		if (GetLifeTime() > 7)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
			{
				if (GetHateInfoCount() == 0)
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
					AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 30 + 300), 0, 1, 1);
				}
				else
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
					AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 30), 0, 1, 1);
				}
			}
		}
		h0 = GetMaxHateInfo(0);
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(DDMagic) == 0)
						{
							AddUseSkillDesire(top_desire_target, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
						else
						{
							AddUseSkillDesire(top_desire_target, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						if (CanAttack(top_desire_target))
						{
							AddAttackDesire(top_desire_target, DesireMove.STAND, 1000);
						}
					}
				}
				else if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					if (CanAttack(attacker))
					{
						AddAttackDesire(attacker, DesireMove.STAND, 1000);
					}
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (!target.isAlive() && !target.isPlayer())
		{
			if (IsInCategory(Category.summon_npc_group, target.getClassId()))
			{
				if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(target.getPlayer(), DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
					else
					{
						AddUseSkillDesire(target.getPlayer(), DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					if (CanAttack(target.getPlayer()))
					{
						AddAttackDesire(target.getPlayer(), DesireMove.STAND, 1000);
					}
				}
				if (target.getPlayer().isPlayer() || IsInCategory(Category.summon_npc_group, target.getPlayer().getClassId()))
				{
					AddHateInfo(target.getPlayer(), 1 * 200, 0, 1, 1);
				}
			}
		}
		else if (DistFromMe(target) < SkillRange)
		{
			h0 = GetMaxHateInfo(0);
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (i_ai0 != 1)
				{
					i0 = 0;
					if (!IsNullHateInfo(h0))
					{
						if (!IsNullCreature(h0.attacker))
						{
							i0 = 1;
						}
					}
					if (i0 == 1)
					{
						if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
						{
							if (Skill_InReuseDelay(DDMagic) == 0)
							{
								AddUseSkillDesire(target, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
							}
							else
							{
								AddUseSkillDesire(target, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							if (CanAttack(target))
							{
								AddAttackDesire(target, DesireMove.STAND, 1000);
							}
						}
					}
				}
			}
		}
		else
		{
			RemoveAllDesire();
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i6 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (creature.isPlayer())
		{
			if (AiUtils.OwnItemCount(creature, pass1) >= 1 || AiUtils.OwnItemCount(creature, pass2) >= 1 || AiUtils.OwnItemCount(creature, pass3) >= 1)
			{
				if (MagicHeal != 0)
				{
					if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
					{
						AddUseSkillDesire(creature, MagicHeal, DesireType.HEAL, DesireMove.STAND, 1000000);
					}
				}
				RemoveAttackDesire(creature.getObjectId());
				RemoveHateInfoByCreature(creature);
			}
			else
			{
				if (GetLifeTime() > 7 && InMyTerritory(actor))
				{
					if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(DDMagic) == 0)
						{
							AddUseSkillDesire(creature, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
						else
						{
							AddUseSkillDesire(creature, DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						if (CanAttack(creature))
						{
							AddAttackDesire(creature, DesireMove.STAND, 1000);
						}
					}
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
			}
		}
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
		int i0 = 0;
		String s0 = null;
		final NpcInstance actor = getActor();

		if (!target.isAlive() && !target.isPlayer())
		{
			if (IsInCategory(Category.summon_npc_group, target.getClassId()))
			{
				if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(DDMagic) == 0)
					{
						AddUseSkillDesire(target.getPlayer(), DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
					else
					{
						AddUseSkillDesire(target.getPlayer(), DDMagic, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					if (CanAttack(target.getPlayer()))
					{
						AddAttackDesire(target.getPlayer(), DesireMove.STAND, 1000);
					}
				}
				if (target.getPlayer().isPlayer() || IsInCategory(Category.summon_npc_group, target.getPlayer().getClassId()))
				{
					AddHateInfo(target.getPlayer(), 1 * 200, 0, 1, 1);
				}
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