package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class roamer_primeval extends party_leader
{
	public int SelfRangeBuff1 = 0;
	public int PhysicalSpecial1 = 0;
	public int PhysicalSpecial2 = 0;
	public int ProbSelfRangeBuff1 = 0;
	public int ProbPhysicalSpecial1 = 0;
	public int ProbPhysicalSpecial2 = 0;
	public int mobile_type = 0;
	public int BroadCastRange = 300;
	public int BroadCastReception = 0;
	public int ag_type = 0;
	public int HpChkRate4 = 50;
	public int HpChkRate5 = 30;
	public int ProbCond5 = 20;
	public int is_dbg = 0;

	public roamer_primeval(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": created";
			Say(s0);
			s0 = "";
		}
		i_ai2 = 0;
		i_ai3 = 1;
		c_ai1 = AiUtils.GetNullCreature();
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": no_desire";
			Say(s0);
			s0 = "";
		}
		if (!IsInCombatMode(actor))
		{
			i_ai2 = 0;
			i_ai3 = 1;
			if (mobile_type == 0)
			{
				AddMoveToDesire(start_x, start_y, start_z, 30);
			}
		}
		super.onEvtNoDesire();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		String s0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.getClassId() == 1022215 || attacker.getClassId() == 1022217 || attacker.getClassId() == 1022216)
		{
			Suicide();
			return;
		}
		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": attacked" + " attacker:" + attacker.getName();
			Say(s0);
			s0 = "";
		}
		if (actor.currentHp / actor.getMaxHp() * 100 <= HpChkRate4)
		{
			i_ai3 = 2;
		}
		else
		{
			i_ai3 = 1;
		}
		if (actor.currentHp / actor.getMaxHp() * 100 <= HpChkRate5)
		{
			if (i_ai2 == 0)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeBuff1) + "]" + "target:" + actor.getName();
					Say(s0);
					s0 = "";
				}
				RemoveAllAttackDesire();
				c_ai1 = top_desire_target;
				if (Skill_GetConsumeMP(SelfRangeBuff1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeBuff1) < actor.currentHp && Skill_InReuseDelay(SelfRangeBuff1) == 0)
				{
					AddUseSkillDesire(actor, SelfRangeBuff1, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 10000000);
				}
			}
		}
		if (AiUtils.Rand(100) <= ProbCond5 * i_ai3)
		{
			BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), BroadCastRange);
		}
		if (!IsNullCreature(top_desire_target))
		{
			if (AiUtils.Rand(100) <= ProbPhysicalSpecial1 * i_ai3)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial1) + "]" + "target:" + top_desire_target.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
				{
					AddUseSkillDesire(top_desire_target, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			if (AiUtils.Rand(100) <= ProbPhysicalSpecial2 * i_ai3)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial2) + "]" + "target:" + top_desire_target.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
				{
					AddUseSkillDesire(top_desire_target, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[L283][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill_finished:" + "[" + AiUtils.IntToStr(skill_name_id) + "]";
			Say(s0);
			s0 = "";
		}
		if ((skill_name_id == SelfRangeBuff1 && success))
		{
			i_ai2 = 1;
			if (!IsNullCreature(c_ai1))
			{
				if (c_ai1.isPlayer() || IsInCategory(Category.summon_npc_group, c_ai1.getClassId()))
				{
					AddAttackDesire(c_ai1, DesireMove.MOVE_TO_TARGET, 1 * 100);
				}
				c_ai1 = AiUtils.GetNullCreature();
			}
		}
		super.onEvtUseSkillFinished(skill_name_id, target, success);
	}

	@Override
	protected void onEvtAbnormalChanged(Creature speller, int skill_name_id, int skill_id, int skill_level)
	{
		String s0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (is_dbg > 1)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": abnormal_status_changed" + " speller:" + speller.getName() + ":skill_level::" + "[" + AiUtils.IntToStr(skill_level) + "]" + ":skill_id::" + "[" + AiUtils.IntToStr(skill_id) + "]";
			Say(s0);
			s0 = "";
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		String s0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": script_event";
			Say(s0);
			s0 = "";
		}
		if (script_event_arg1 == ScriptEvent.SCE_ONE_POINT_ATTACK)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0) && c0.isPlayer() && BroadCastReception == 1)
			{
				if (is_dbg > 2)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": script_event::" + "replace top_desire_target as " + c0.getName();
					Say(s0);
					s0 = "";
				}
				top_desire_target = c0;
			}
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::" + "ag_type=" + "[" + AiUtils.IntToStr(ag_type) + "]";
			Say(s0);
			s0 = "";
		}
		if ((actor.getClassId() == 1022743 || actor.getClassId() == 1022742) && creature.isPlayer())
		{
			if (AiUtils.Rand(100) < 30)
			{
				RemoveAllDesire();
				AddFleeDesireEx(creature, 3000, 500000);
				i_ai4 = 1;
			}
			return;
		}
		if (ag_type == 1 && creature.isPlayer())
		{
			if (AiUtils.Rand(100) <= ProbPhysicalSpecial1 * i_ai3)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial1) + "]" + "target:" + creature.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(PhysicalSpecial1) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial1) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial1) == 0)
				{
					AddUseSkillDesire(creature, PhysicalSpecial1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (AiUtils.Rand(100) <= ProbPhysicalSpecial2 * i_ai3)
			{
				if (is_dbg > 1)
				{
					s0 = "[DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(PhysicalSpecial2) + "]" + "target:" + creature.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(PhysicalSpecial2) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial2) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial2) == 0)
				{
					AddUseSkillDesire(creature, PhysicalSpecial2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
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
			super.onEvtSeeCreature(creature);
		}
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{

		super.onEvtDead(attacker);
		if (AiUtils.Rand(100) < 3 && i_ai4 == 1)
		{
			if (!IsNullCreature(attacker))
			{
				if (GetInventoryInfo(attacker, 0) >= GetInventoryInfo(attacker, 1) * 0.800000f || GetInventoryInfo(attacker, 2) >= GetInventoryInfo(attacker, 3) * 0.800000f)
				{
					ShowSystemMessageStr(attacker, AiUtils.MakeFString(1800879, "", "", "", "", ""));
					return;
				}
				GetItemData(attacker, 14828);
				CreatePet(attacker, 14828, 1016067, 55);
				ShowSystemMessageStr(attacker, AiUtils.MakeFString(1800878, "", "", "", "", ""));
			}
		}
	}

}