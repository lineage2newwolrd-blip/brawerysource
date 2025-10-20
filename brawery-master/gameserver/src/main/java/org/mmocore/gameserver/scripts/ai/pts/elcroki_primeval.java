package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class elcroki_primeval extends party_leader_wizard
{
	public String ai_type1 = "orc";
	public int silhouette1 = 1020130;
	public String ai_type2 = "orc";
	public int silhouette2 = 1020130;
	public int max_spawn_privates = 5;
	public int max_concurrent_privates = 2;
	public int SelfRangeBuff1 = 0;
	public int SelfRangeDeBuff1 = 0;
	public int LongRangeDDMagic1 = 0;
	public int SelfRangeDDMagic1 = 0;
	public int ProbSelfRangeBuff1 = 0;
	public int ProbSelfRangeDeBuff1 = 30;
	public int ProbLongRangeDDMagic1 = 0;
	public int ProbSelfRangeDDMagic1 = 0;
	public int LongRangeSkillDist = 100;
	public int BroadCastReception = 0;
	public int BroadCastRange = 300;
	public int ag_type = 0;
	public int HpChkRate2 = 80;
	public int HpChkRate3 = 60;
	public int HpChkRate4 = 50;
	public int HpChkRate5 = 30;
	public int ProbCond4 = 30;
	public int is_dbg = 0;

	public elcroki_primeval(final NpcInstance actor)
	{
		super(actor);
		Privates = "";
	}

	@Override
	protected void onEvtSpawn()
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[L063][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": created";
			Say(s0);
			s0 = "";
		}
		i_quest0 = 0;
		i_ai3 = 0;
		i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[L078][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": no_desire";
			Say(s0);
			s0 = "";
		}
		if (!IsInCombatMode(actor))
		{
			if (actor.currentHp / actor.getMaxHp() * 100 >= HpChkRate2)
			{
				if (is_dbg > 2)
				{
					s0 = "[L089][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": no_desire - reset innver variables";
					Say(s0);
					s0 = "";
				}
				if (i_ai3 > 0)
				{
					if (is_dbg > 1)
					{
						s0 = "[L098][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": broadcast - @sce_private_despawn : range" + "[" + AiUtils.IntToStr(BroadCastRange) + "]";
						Say(s0);
						s0 = "";
					}
					BroadcastScriptEvent(ScriptEvent.SCE_PRIVATE_DESPAWN, 0, BroadCastRange);
					i_ai3 = 0;
					i_ai4 = 0;
				}
				i_quest0 = 0;
			}
			AddMoveToDesire(start_x, start_y, start_z, 30);
		}
		super.onEvtNoDesire();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		String s0 = null;
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[L117][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": attacked" + " attacker:" + attacker.getName();
			Say(s0);
			s0 = "";
		}
		if (actor.currentHp / actor.getMaxHp() * 100 <= HpChkRate3)
		{
			if (i_quest0 == 0)
			{
				if (is_dbg > 1)
				{
					s0 = "[L130][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeBuff1) + "]" + "target:" + actor.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(SelfRangeBuff1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeBuff1) < actor.currentHp && Skill_InReuseDelay(SelfRangeBuff1) == 0)
				{
					AddUseSkillDesire(actor, SelfRangeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (actor.currentHp / actor.getMaxHp() * 100 <= HpChkRate5 && AiUtils.Rand(100) < ProbSelfRangeDeBuff1)
		{
			if (is_dbg > 1)
			{
				s0 = "[L145][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeDeBuff1) + "]" + "target:" + actor.getName();
				Say(s0);
				s0 = "";
			}
			if (Skill_GetConsumeMP(SelfRangeDeBuff1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDeBuff1) < actor.currentHp && Skill_InReuseDelay(SelfRangeDeBuff1) == 0)
			{
				AddUseSkillDesire(attacker, SelfRangeDeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (AiUtils.Rand(100) <= ProbCond4)
		{
			BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_ATTACK, AiUtils.GetIndexFromCreature(actor), BroadCastRange);
		}
		if (is_dbg > 1)
		{
			s0 = "[L163][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": attacked-GetHateInfo" + " attacker:" + attacker.getName();
			Say(s0);
			s0 = "";
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		h0 = GetMaxHateInfo(0);
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai0 == 0)
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
				if (DistFromMe(attacker) > LongRangeSkillDist)
				{
					if (i0 == 1)
					{
						if (is_dbg > 1)
						{
							s0 = "[L187][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(LongRangeDDMagic1) + "]" + "target:" + attacker.getName();
							Say(s0);
							s0 = "";
						}
						if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp)
						{
							if (Skill_InReuseDelay(LongRangeDDMagic1) == 0)
							{
								AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
						if (is_dbg > 1)
						{
							s0 = "[L197][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(LongRangeDDMagic1) + "]" + "target:" + attacker.getName();
							Say(s0);
							s0 = "";
						}
						if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp)
						{
							if (Skill_InReuseDelay(LongRangeDDMagic1) == 0)
							{
								AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
					if (is_dbg > 1)
					{
						s0 = "[L210][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeDDMagic1) + "]" + "target:" + actor.getName() + "i0:" + "[" + AiUtils.IntToStr(i0) + "]";
						Say(s0);
						s0 = "";
					}
					if (Skill_GetConsumeMP(SelfRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic1) == 0)
					{
						AddUseSkillDesire(attacker, SelfRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else if (AiUtils.Rand(100) < 2)
				{
					if (is_dbg > 1)
					{
						s0 = "[L221][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeDDMagic1) + "]" + "target:" + actor.getName();
						Say(s0);
						s0 = "";
					}
					if (Skill_GetConsumeMP(SelfRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic1) < actor.currentHp)
					{
						if (Skill_InReuseDelay(SelfRangeDDMagic1) == 0)
						{
							AddUseSkillDesire(attacker, SelfRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(attacker, SelfRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
			if (DistFromMe(attacker) > LongRangeSkillDist)
			{
				if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp)
				{
					if (Skill_InReuseDelay(LongRangeDDMagic1) == 0)
					{
						AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (Skill_GetConsumeMP(SelfRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic1) == 0)
			{
				AddUseSkillDesire(attacker, SelfRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		RemoveAllHateInfoIF(1, 0);
		if ((attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())) && GetHateInfoCount() == 0)
		{
			if (DistFromMe(attacker) > LongRangeSkillDist)
			{
				if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp)
				{
					if (Skill_InReuseDelay(LongRangeDDMagic1) == 0)
					{
						AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (Skill_GetConsumeMP(SelfRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic1) == 0)
			{
				AddUseSkillDesire(attacker, SelfRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
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
			i_quest0 = 1;
		}
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
				if (DistFromMe(h0.attacker) > LongRangeSkillDist)
				{
					if (is_dbg > 1)
					{
						s0 = "[L298][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(LongRangeDDMagic1) + "]" + "target:" + h0.attacker.getName();
						Say(s0);
						s0 = "";
					}
					if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp)
					{
						if (Skill_InReuseDelay(LongRangeDDMagic1) == 0)
						{
							AddUseSkillDesire(h0.attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(h0.attacker, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (is_dbg > 1)
				{
					s0 = "[L310][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeDDMagic1) + "]" + "target:" + h0.attacker.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(SelfRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(h0.attacker, SelfRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		int i0 = 0;
		Creature c0 = null;
		String s0 = null;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[L325][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": script_event";
			Say(s0);
			s0 = "";
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		String s0 = null;
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[L337][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": PARTY_DIED::private_name" + "[" + privat.getName() + "]";
			Say(s0);
			s0 = "";
		}
		if (privat != actor)
		{
			if (i_ai3 > 0)
			{
				i_ai3 = i_ai3 - 1;
			}
			if (IsInCombatMode(actor) && i_ai3 < 2 && i_ai4 < max_spawn_privates)
			{
				if (AiUtils.Rand(2) == 0)
				{
					if (is_dbg > 1)
					{
						s0 = "[L355][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": party_died::create private:" + ai_type1 + ":silhouette=" + "[" + AiUtils.IntToStr(silhouette1) + "]";
						Say(s0);
						s0 = "";
					}
					if (!IsNullCreature(top_desire_target))
					{
					}
					i_ai3 = i_ai3 + 1;
					i_ai4 = i_ai4 + 1;
				}
				else if (is_dbg > 1)
				{
					s0 = "[L367][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": party_died::create private:" + ai_type2 + ":silhouette=" + "[" + AiUtils.IntToStr(silhouette2) + "]";
					Say(s0);
					s0 = "";
				}
				if (!IsNullCreature(top_desire_target))
				{
				}
				i_ai3 = i_ai3 + 1;
				i_ai4 = i_ai4 + 1;
			}
		}
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
	protected void onEvtSeeCreature(Creature creature)
	{
		String s0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (is_dbg > 2)
		{
			s0 = "[L392][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::" + "ag_type=" + "[" + AiUtils.IntToStr(ag_type) + "]";
			Say(s0);
			s0 = "";
		}
		if (ag_type == 1 && creature.isPlayer())
		{
			if (InMyTerritory(actor) && GetHateInfoCount() == 0)
			{
				if (AiUtils.Rand(2) == 0 && i_ai3 < 2 && i_ai4 < max_spawn_privates)
				{
					if (is_dbg > 1)
					{
						s0 = "[L407][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::create private:" + ai_type1 + ":silhouette=" + "[" + AiUtils.IntToStr(silhouette1) + "]";
						Say(s0);
						s0 = "";
					}
					i_ai3 = i_ai3 + 1;
					i_ai4 = i_ai4 + 1;
				}
				else if ((i_ai3 < 2 && i_ai4 < max_spawn_privates))
				{
					if (is_dbg > 1)
					{
						s0 = "[L419][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::create private:" + ai_type2 + ":silhouette=" + "[" + AiUtils.IntToStr(silhouette2) + "]";
						Say(s0);
						s0 = "";
					}
					i_ai3 = i_ai3 + 1;
					i_ai4 = i_ai4 + 1;
				}
				else if (is_dbg > 1)
				{
					s0 = "[L431][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::more than 2 current privates ";
					Say(s0);
					s0 = "";
				}
				if (AiUtils.Rand(2) == 0 && i_ai3 < 2 && i_ai4 < max_spawn_privates)
				{
					if (is_dbg > 1)
					{
						s0 = "[L442][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::create private:" + ai_type1 + ":silhouette=" + "[" + AiUtils.IntToStr(silhouette1) + "]";
						Say(s0);
						s0 = "";
					}
					i_ai3 = i_ai3 + 1;
					i_ai4 = i_ai4 + 1;
				}
				else if ((i_ai3 < 2 && i_ai4 < max_spawn_privates))
				{
					if (is_dbg > 1)
					{
						s0 = "[L454][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::create private:" + ai_type2 + ":silhouette=" + "[" + AiUtils.IntToStr(silhouette2) + "]";
						Say(s0);
						s0 = "";
					}
					i_ai3 = i_ai3 + 1;
					i_ai4 = i_ai4 + 1;
				}
				else if (is_dbg > 1)
				{
					s0 = "[L466][DBG3]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": see_creature::more than 2 current privates ";
					Say(s0);
					s0 = "";
				}
				if (DistFromMe(creature) > LongRangeSkillDist)
				{
					if (is_dbg > 1)
					{
						s0 = "[L477][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(LongRangeDDMagic1) + "]" + "target:" + creature.getName();
						Say(s0);
						s0 = "";
					}
					if (Skill_GetConsumeMP(LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(LongRangeDDMagic1) < actor.currentHp)
					{
						if (Skill_InReuseDelay(LongRangeDDMagic1) == 0)
						{
							AddUseSkillDesire(creature, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(creature, LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (is_dbg > 1)
				{
					s0 = "[L487][DBG2]" + "[" + AiUtils.IntToStr(actor.getClassId()) + "]" + actor.getName() + ": use_skill::" + "skill_id" + "[" + AiUtils.IntToStr(SelfRangeDDMagic1) + "]" + "target:" + creature.getName();
					Say(s0);
					s0 = "";
				}
				if (Skill_GetConsumeMP(SelfRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(SelfRangeDDMagic1) < actor.currentHp && Skill_InReuseDelay(SelfRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(creature, SelfRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
				super.onEvtSeeCreature(creature);
			}
		}
	}

}