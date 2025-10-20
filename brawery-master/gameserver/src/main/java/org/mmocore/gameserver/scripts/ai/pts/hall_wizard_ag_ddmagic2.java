package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class hall_wizard_ag_ddmagic2 extends wizard_ag_ddmagic2
{
	public hall_wizard_ag_ddmagic2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddDoNothingDesire(5, 5);
		c_ai0 = AiUtils.GetNullCreature();
	}

	@Override
	protected void onEvtNoDesire()
	{

		AddDoNothingDesire(5, 5);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (DistFromMe(attacker) > 100)
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
		else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
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

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 50 && speller.isPlayer() && IsInCategory(Category.cleric_group, speller.getClassId()))
		{
			RemoveAllAttackDesire();
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
			else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
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

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (script_event_arg2 == 10033)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg3);
			if (!IsNullCreature(c0))
			{
				RemoveAllAttackDesire();
				if (DistFromMe(c0) > 100)
				{
					if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
						{
							AddUseSkillDesire(c0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(c0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
					{
						AddUseSkillDesire(c0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(c0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		if (script_event_arg2 == 10042)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg3);
			if (!IsNullCreature(c0))
			{
				AddMoveToDesire(AiUtils.FloatToInt(c0.getX()), AiUtils.FloatToInt(c0.getY()), AiUtils.FloatToInt(c0.getZ()), 10000000);
			}
		}
		if (script_event_arg1 == ScriptEvent.SCE_ATTACK_CENTER)
		{
			AddMoveToDesire(script_event_arg2, script_event_arg3, AiUtils.FloatToInt(actor.getZ()), 1000);
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		final NpcInstance actor = getActor();

		RemoveDesire(11);
		if (DistFromMe(creature) > 100)
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
		else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
		{
			if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
			{
				AddUseSkillDesire(creature, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			else
			{
				AddUseSkillDesire(creature, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else
		{
			i_ai0 = 1;
			AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
		}
		if (creature.isPlayer() || IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			AddHateInfo(creature, 1 * 200, 0, 1, 1);
		}
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (DistFromMe(attacker) > 100)
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
		else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
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