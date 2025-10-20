package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior extends warrior_parameter
{

	public warrior(final NpcInstance actor)
	{
		super(actor);
		Attack_BoostValue = 300.000000f;
		Attack_DecayRatio = 6.600000f;
		UseSkill_BoostValue = 100000.000000f;
		UseSkill_DecayRatio = 66000.000000f;
	}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundDesire(5, 5);
	}

	@Override
	protected void onEvtSpawn()
	{

		if (ShoutMsg1 > 0)
		{
			if (IsSay == 0)
			{
				Shout(AiUtils.MakeFString(ShoutMsg1, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(ShoutMsg1, "", "", "", "", ""));
			}
		}
		if ((MoveAroundSocial > 0 || ShoutMsg2 > 0 || ShoutMsg3 > 0))
		{
			AddTimerEx(1001, 10000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 1001)
		{
			if ((p_state == State.MOVE_AROUND || p_state == State.DO_NOTHING || p_state == State.MOVE_TO_WAY_POINT) && (actor.currentHp > actor.getMaxHp() * 0.400000f && actor.isAlive()))
			{
				if ((MoveAroundSocial > 0 || MoveAroundSocial1 > 0 || MoveAroundSocial2 > 0))
				{
					if (MoveAroundSocial2 > 0 && AiUtils.Rand(100) < 20)
					{
						AddEffectActionDesire(actor, SocialAction.VICTORY, MoveAroundSocial2 * 1000 / 30, 50);
					}
					else if (MoveAroundSocial1 > 0 && AiUtils.Rand(100) < 20)
					{
						AddEffectActionDesire(actor, SocialAction.GREETING, MoveAroundSocial1 * 1000 / 30, 50);
					}
					else if (MoveAroundSocial > 0 && AiUtils.Rand(100) < 20)
					{
						AddEffectActionDesire(actor, SocialAction.UNKNOW, MoveAroundSocial * 1000 / 30, 50);
					}
				}
				if (ShoutMsg2 > 0 && AiUtils.Rand(1000) < 17)
				{
					if (IsSay == 0)
					{
						Shout(AiUtils.MakeFString(ShoutMsg2, "", "", "", "", ""));
					}
					else
					{
						Say(AiUtils.MakeFString(ShoutMsg2, "", "", "", "", ""));
					}
				}
			}
			else if (p_state == State.ATTACK)
			{
				if (ShoutMsg3 > 0 && AiUtils.Rand(100) < 10)
				{
					if (IsSay == 0)
					{
						Shout(AiUtils.MakeFString(ShoutMsg3, "", "", "", "", ""));
					}
					else
					{
						Say(AiUtils.MakeFString(ShoutMsg3, "", "", "", "", ""));
					}
				}
			}
			AddTimerEx(1001, 10000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

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
			AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * 100);
		}
		if (!IsNullCreature(top_desire_target))
		{
			if (GetPathfindFailCount() > 10 && attacker == top_desire_target && AiUtils.FloatToInt(actor.currentHp) != AiUtils.FloatToInt(actor.getMaxHp()))
			{
				InstantTeleport(actor, AiUtils.FloatToInt(attacker.getX()), AiUtils.FloatToInt(attacker.getY()), AiUtils.FloatToInt(attacker.getZ()));
			}
			if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(78708737/*@skill_1201_1*/)) >= 0 && DistFromMe(top_desire_target) > 40)
			{
				if (CanAttack(top_desire_target))
				{
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
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * 100);
					}
				}
				else
				{
					RemoveAttackDesire(top_desire_target.getObjectId());
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
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * 100);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7)
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (GetPathfindFailCount() > 10 && attacker == top_desire_target && AiUtils.FloatToInt(actor.currentHp) != AiUtils.FloatToInt(actor.getMaxHp()))
				{
					InstantTeleport(actor, AiUtils.FloatToInt(attacker.getX()), AiUtils.FloatToInt(attacker.getY()), AiUtils.FloatToInt(attacker.getZ()));
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
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, f0 * 30);
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (Skill_GetEffectPoint(skill_name_id) > 0)
		{
			if ((p_state == State.ATTACK && top_desire_target == target))
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
				f0 = 1.000000f * i0 / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * i0 / (actor.getLevel() + 7)));
				AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, f0 * 150);
			}
		}
		if (GetPathfindFailCount() > 10 && speller == top_desire_target && AiUtils.FloatToInt(actor.currentHp) != AiUtils.FloatToInt(actor.getMaxHp()))
		{
			InstantTeleport(actor, AiUtils.FloatToInt(speller.getX()), AiUtils.FloatToInt(speller.getY()), AiUtils.FloatToInt(speller.getZ()));
		}
	}

	@Override
	protected void onEvtDesireManipulation(Creature speller, int desire)
	{

		MakeAttackEvent(speller, desire, 0);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{

		if (ShoutMsg4 > 0 && AiUtils.Rand(100) < 30)
		{
			if (IsSay == 0)
			{
				Shout(AiUtils.MakeFString(ShoutMsg4, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(ShoutMsg4, "", "", "", "", ""));
			}
		}
	}

}