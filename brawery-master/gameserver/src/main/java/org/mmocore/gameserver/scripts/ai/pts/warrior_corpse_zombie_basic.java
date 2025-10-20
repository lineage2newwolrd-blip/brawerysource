package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_corpse_zombie_basic extends warrior
{
	public int SelfRangeDeBuff = 274464769;
	public int IsTeleport = 0;
	public int IsPrivate = 0;

	public warrior_corpse_zombie_basic(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		if (IsPrivate == 1)
		{
			AddTimerEx(1004, 20000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		final NpcInstance boss = getActor().getLeader();

		if (IsPrivate == 1)
		{
			if (IsMyBossAlive())
			{
				AddFollowDesire(boss, 5);
			}
			else
			{
				AddMoveAroundDesire(5, 5);
			}
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 1004)
		{
			if (IsPrivate == 1 && !IsMyBossAlive())
			{
				if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
				{
					Despawn();
					return;
				}
			}
			AddTimerEx(1004, 20000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 10)
		{
			if (Skill_GetConsumeMP(SelfRangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDeBuff) < actor.currentHp)
			{
				if (Skill_InReuseDelay(SelfRangeDeBuff) == 0)
				{
					AddUseSkillDesire(actor, SelfRangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(actor, SelfRangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtDead(attacker);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (IsTeleport != 0)
		{
			if (DistFromMe(attacker) > 100 && actor.currentHp > 0)
			{
				if (top_desire_target == attacker && AiUtils.Rand(100) < 10)
				{
					InstantTeleport(actor, AiUtils.FloatToInt(attacker.getX()), AiUtils.FloatToInt(attacker.getY()), AiUtils.FloatToInt(attacker.getZ()));
					if (Skill_GetConsumeMP(IsTeleport) < actor._currentMp && Skill_GetConsumeHP(IsTeleport) < actor.currentHp && Skill_InReuseDelay(IsTeleport) == 0)
					{
						AddUseSkillDesire(actor, IsTeleport, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
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
		}
		if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 10 && i_ai0 == 0)
		{
			if (AiUtils.Rand(100) < 10)
			{
				if (Skill_GetConsumeMP(SelfRangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDeBuff) < actor.currentHp)
				{
					if (Skill_InReuseDelay(SelfRangeDeBuff) == 0)
					{
						AddUseSkillDesire(actor, SelfRangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(actor, SelfRangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(actor, DesireMove.MOVE_TO_TARGET, 1000);
				}
				i_ai0 = 1;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (IsTeleport != 0)
		{
			if (DistFromMe(attacker) > 100 && p_state != State.ATTACK && AiUtils.Rand(100) < 10 && actor.currentHp > 0)
			{
				InstantTeleport(actor, AiUtils.FloatToInt(attacker.getX()), AiUtils.FloatToInt(attacker.getY()), AiUtils.FloatToInt(attacker.getZ()));
				if (Skill_GetConsumeMP(IsTeleport) < actor._currentMp && Skill_GetConsumeHP(IsTeleport) < actor.currentHp && Skill_InReuseDelay(IsTeleport) == 0)
				{
					AddUseSkillDesire(actor, IsTeleport, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
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
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}