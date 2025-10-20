package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_heretic_ddmagic2_curse extends wizard_ddmagic2_curse
{
	public wizard_heretic_ddmagic2_curse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			if (i_ai4 == 0)
			{
				i_ai4 = 1;
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;

		if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			if (i_ai4 == 0)
			{
				i_ai4 = 1;
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtOutOfTerritory()
	{

		if (i_ai4 == 0)
		{
			RemoveAllAttackDesire();
			AddMoveToDesire(start_x, start_y, start_z, 100);
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (script_event_arg1 == ScriptEvent.SCE_PROTECT_LEADER)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				i_ai4 = 1;
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
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddHateInfo(c0, 1 * 200, 0, 1, 1);
				}
			}
		}
		else if (script_event_arg1 == ScriptEvent.SCE_STOP_ATTACK)
		{
			i_ai4 = 0;
			RemoveAllAttackDesire();
			AddMoveToDesire(start_x, start_y, start_z, 100);
		}
		else if (script_event_arg1 == ScriptEvent.SCE_VALAKAS_ATTACK)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
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
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddHateInfo(c0, 1 * 200, 0, 1, 1);
				}
			}
		}
	}

}