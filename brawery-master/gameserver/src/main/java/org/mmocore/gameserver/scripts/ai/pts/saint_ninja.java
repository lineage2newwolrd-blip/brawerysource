package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class saint_ninja extends warrior_physicalspecial
{
	public int SelfBuff = 263979009;
	public int IsMainForm = 1;
	public int TeleportEffect = 263979009;
	public int OtherSelf = 1020130;
	public String OtherSelfAI = "saint_ninja";

	public saint_ninja(final NpcInstance actor)
	{
		super(actor);
		PhysicalSpecial = 264241153;
	}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai0 = 0;
		if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
		{
			AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		if (IsMainForm == 0)
		{
			AddTimerEx(2000, 60000 * 5);
		}
		AddTimerEx(2001, 60000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 80 && IsMainForm == 1 && i_ai0 == 0)
		{
			CreateOnePrivateEx(OtherSelf, OtherSelfAI, 0, 0, AiUtils.FloatToInt(actor.getX()) + AiUtils.Rand(20), AiUtils.FloatToInt(actor.getY()) + AiUtils.Rand(20), AiUtils.FloatToInt(actor.getZ()), 32768, 1000, AiUtils.GetIndexFromCreature(attacker), 1);
			i_ai0 = 1;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 2000)
		{
			if (p_state != State.ATTACK)
			{
				Despawn();
				return;
			}
			else
			{
				AddTimerEx(2000, 60000 * 5);
			}
		}
		if (timer_id == 2001)
		{
			if (p_state != State.ATTACK && actor.isAlive())
			{
				InstantRandomTeleportInMyTerritory();
				if (Skill_GetConsumeMP(TeleportEffect) < actor._currentMp && Skill_GetConsumeHP(TeleportEffect) < actor.currentHp && Skill_InReuseDelay(TeleportEffect) == 0)
				{
					AddUseSkillDesire(actor, TeleportEffect, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			AddTimerEx(2001, 60000 * 5);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (!IsInCombatMode(actor) && DistFromMe(attacker) > 300 && actor.isAlive())
		{
			InstantTeleport(actor, AiUtils.FloatToInt(attacker.getX()), AiUtils.FloatToInt(attacker.getY()), AiUtils.FloatToInt(attacker.getZ()));
			if (Skill_GetConsumeMP(TeleportEffect) < actor._currentMp && Skill_GetConsumeHP(TeleportEffect) < actor.currentHp && Skill_InReuseDelay(TeleportEffect) == 0)
			{
				AddUseSkillDesire(actor, TeleportEffect, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
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
		else
		{
			super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
		}
	}

}