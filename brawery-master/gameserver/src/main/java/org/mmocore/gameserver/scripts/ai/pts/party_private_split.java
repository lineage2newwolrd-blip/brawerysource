package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_split extends party_private
{
	public int PhysicalSpecial1 = 458752001;
	public int PhysicalSpecial2 = 458752001;

	public party_private_split(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		c_ai0 = AiUtils.GetCreatureFromIndex(actor.param2);
		if (!IsNullCreature(c_ai0))
		{
			top_desire_target = c_ai0;
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (creature == top_desire_target)
		{
			if (AiUtils.Rand(100) < 50)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
				{
					AddUseSkillDesire(creature, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
			{
				AddUseSkillDesire(creature, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
			{
				return;
			}
			if (SeeCreatureAttackerTime == -1)
			{
				if (SetAggressiveTime == -1)
				{
					if (GetLifeTime() >= AiUtils.Rand(5) + 3 && InMyTerritory(actor))
					{
						AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
					}
				}
				else if (SetAggressiveTime == 0)
				{
					if (InMyTerritory(actor))
					{
						AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
					}
				}
				else
				{
					if (GetLifeTime() > SetAggressiveTime + AiUtils.Rand(4) && InMyTerritory(actor))
					{
						AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
					}
				}
			}
			else
			{
				if (GetLifeTime() > SeeCreatureAttackerTime && InMyTerritory(actor))
				{
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 200);
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 1006)
		{
			if (!IsMyBossAlive())
			{
				if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
				{
					Despawn();
					return;
				}
				else
				{
					AddTimerEx(1006, 3 * 60 * 1000);
				}
			}
		}
		if (timer_id == 1004)
		{
			AddTimerEx(1006, 3 * 60 * 1000);
		}
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
		if (timer_id == 1)
		{
			if (AttackLowLevel == 1)
			{
				LookNeighbor(300);
			}
		}
		if (timer_id == 2)
		{
			if (IsVs == 1)
			{
				c_ai0 = actor;
			}
		}
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		final NpcInstance boss = getActor().getLeader();

		if (privat == boss)
		{
			AddTimerEx(1004, 3 * 60 * 1000);
		}
	}

}