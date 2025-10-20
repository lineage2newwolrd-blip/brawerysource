package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_private_wizard_oracle extends wizard
{
	public int selfbuff = 458752001;
	public int W_LongRangeDDMagic = 458752001;
	public int W_ShortRangeDDMagic = 458752001;

	public party_private_wizard_oracle(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		i_ai2 = 0;
		c_ai0 = AiUtils.GetNullCreature();
		if (Skill_GetConsumeMP(selfbuff) < actor._currentMp && Skill_GetConsumeHP(selfbuff) < actor.currentHp && Skill_InReuseDelay(selfbuff) == 0)
		{
			AddUseSkillDesire(actor, selfbuff, DesireType.HEAL, DesireMove.STAND, 10000000);
		}
		AddTimerEx(1100, 60 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		final NpcInstance boss = getActor().getLeader();

		if (IsMyBossAlive())
		{
			AddFollowDesire(boss, 5);
		}
		else
		{
			AddMoveAroundDesire(5, 5);
		}
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		h0 = GetMaxHateInfo(0);
		c_ai0 = attacker;
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai2 == 0)
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
				if (DistFromMe(attacker) > 100)
				{
					if (i0 == 1)
					{
						if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
						{
							if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
							{
								AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
						}
					}
					else if (AiUtils.Rand(100) < 2)
					{
						if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
						{
							if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
							{
								AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
						}
					}
				}
				else if (i0 == 1)
				{
					if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
						{
							AddUseSkillDesire(attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (AiUtils.Rand(100) < 2)
				{
					if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
						{
							AddUseSkillDesire(attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
			}
			else if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
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

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		if (privat != actor)
		{
			c_ai0 = attacker;
			if (DistFromMe(attacker) >= 100)
			{
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (DistFromMe(attacker) <= 100)
			{
				if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
					{
						AddUseSkillDesire(attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{

	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance boss = getActor().getLeader();

		if (timer_id == 1100)
		{
			if (GetTick() - i_ai1 > 5 * 60 * 1000)
			{
				AiUtils.SendScriptEvent(boss, ScriptEvent.SCE_PRIVATE_DESPAWN, 0);
				Despawn();
			}
			AddTimerEx(1100, 1 * 60 * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		int i0 = 0;

		if (script_event_arg1 == 1000)
		{
			Despawn();
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		Party party0 = null;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		party0 = AiUtils.GetParty(creature);
		if (!IsNullParty(party0))
		{
			if (AiUtils.Rand(100) < 50)
			{
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		else if (AiUtils.Rand(100) < 20)
		{
			if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
				{
					AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(creature, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 20 && speller.isPlayer() && IsInCategory(Category.cleric_group, speller.getClassId()))
		{
			if (DistFromMe(speller) > 100)
			{
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(speller, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(speller, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (DistFromMe(speller) < 100)
			{
				if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
					{
						AddUseSkillDesire(speller, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(speller, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai2 != 1)
		{
			if (!IsNullCreature(c_ai0))
			{
				if (DistFromMe(c_ai0) > 100)
				{
					if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
						{
							AddUseSkillDesire(c_ai0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(c_ai0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
					{
						AddUseSkillDesire(c_ai0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(c_ai0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
	}

}