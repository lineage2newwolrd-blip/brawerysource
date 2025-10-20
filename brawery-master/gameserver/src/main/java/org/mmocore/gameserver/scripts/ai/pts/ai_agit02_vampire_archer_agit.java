package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_agit02_vampire_archer_agit extends wizard
{
	public int W_FiendArcher = 272039937;

	public ai_agit02_vampire_archer_agit(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddDoNothingDesire(5, 5);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(5, 5);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
		{
			if (Skill_InReuseDelay(W_FiendArcher) == 0)
			{
				AddUseSkillDesire(creature, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
			else
			{
				AddUseSkillDesire(creature, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
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

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

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
				AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 100 + 300), 0, 1, 1);
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
				AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 100), 0, 1, 1);
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
				if (h0.attacker == attacker)
				{
					i0 = 1;
				}
			}
			if (i0 == 1)
			{
				if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_FiendArcher) == 0)
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
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
			else if (AiUtils.Rand(100) < 10)
			{
				if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_FiendArcher) == 0)
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
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
		final NpcInstance actor = getActor();

		RemoveAllHateInfoIF(1, 0);
		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && GetHateInfoCount() == 0)
		{
			if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_FiendArcher) == 0)
				{
					AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
				}
				else
				{
					AddUseSkillDesire(attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
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
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtClanObjectAttacked(Creature attacker, Creature victim, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.getClassId() == 1014737)
		{
			AddAttackDesire(attacker.getPlayer(), DesireMove.STAND, 5000.000000f);
			AddAttackDesire(attacker, DesireMove.STAND, 1000.000000f);
		}
		else if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			AddAttackDesire(attacker, DesireMove.STAND, damage / actor.getMaxHp() / 0.050000f * 50);
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

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
			if (Skill_GetConsumeMP(W_FiendArcher) < actor._currentMp && Skill_GetConsumeHP(W_FiendArcher) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_FiendArcher) == 0)
				{
					AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
				}
				else
				{
					AddUseSkillDesire(h0.attacker, W_FiendArcher, DesireType.ATTACK, DesireMove.STAND, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				if (CanAttack(h0.attacker))
				{
					AddAttackDesire(h0.attacker, DesireMove.STAND, 1000);
				}
			}
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

}