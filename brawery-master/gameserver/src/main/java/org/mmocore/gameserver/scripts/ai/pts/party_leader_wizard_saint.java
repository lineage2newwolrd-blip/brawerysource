package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_wizard_saint extends party_leader_wizard
{
	public int W_LongRangeDDMagic1 = 272039937;
	public int W_LongRangeDDMagic2 = 272039937;
	public int SelfRangeBuff = 263979009;
	public int RangeDeBuff = 272039937;

	public party_leader_wizard_saint(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(1005, 5000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

		if (timer_id == 1005)
		{
			if (p_state != State.ATTACK)
			{
				if (Skill_GetConsumeMP(SelfRangeBuff) < actor._currentMp && Skill_GetConsumeHP(SelfRangeBuff) < actor.currentHp && Skill_InReuseDelay(SelfRangeBuff) == 0)
				{
					AddUseSkillDesire(actor, SelfRangeBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else if (AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(SelfRangeBuff) < actor._currentMp && Skill_GetConsumeHP(SelfRangeBuff) < actor.currentHp && Skill_InReuseDelay(SelfRangeBuff) == 0)
				{
					AddUseSkillDesire(actor, SelfRangeBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			AddTimerEx(1005, 1000 * 60 * 2);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		float f0 = 0;
		Party party0 = null;
		top_desire_target = getTopHated();
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
		if (i_ai0 == 0)
		{
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
					if (AiUtils.Rand(100) < 80)
					{
						if (Skill_GetConsumeMP(W_LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic1) < actor.currentHp)
						{
							if (Skill_InReuseDelay(W_LongRangeDDMagic1) == 0)
							{
								AddUseSkillDesire(attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
						}
					}
					else
					{
						party0 = AiUtils.GetParty(attacker);
						if (!IsNullParty(party0) || top_desire_target != attacker)
						{
							if (AiUtils.Rand(100) < 33)
							{
								if (Skill_GetConsumeMP(W_LongRangeDDMagic2) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic2) < actor.currentHp)
								{
									if (Skill_InReuseDelay(W_LongRangeDDMagic2) == 0)
									{
										AddUseSkillDesire(attacker, W_LongRangeDDMagic2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
									}
									else
									{
										AddUseSkillDesire(attacker, W_LongRangeDDMagic2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
									}
								}
								else
								{
									i_ai0 = 1;
									AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
								}
							}
							else if (Skill_GetConsumeMP(RangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(RangeDeBuff) < actor.currentHp)
							{
								if (Skill_InReuseDelay(RangeDeBuff) == 0)
								{
									AddUseSkillDesire(attacker, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
								}
								else
								{
									AddUseSkillDesire(attacker, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
				else if (AiUtils.Rand(100) < 10)
				{
					if (Skill_GetConsumeMP(W_LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic1) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_LongRangeDDMagic1) == 0)
						{
							AddUseSkillDesire(attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 80)
		{
			if (Skill_GetConsumeMP(W_LongRangeDDMagic2) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic2) < actor.currentHp && Skill_InReuseDelay(W_LongRangeDDMagic2) == 0)
			{
				AddUseSkillDesire(attacker, W_LongRangeDDMagic2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		else if (Skill_GetConsumeMP(RangeDeBuff) < actor._currentMp && Skill_GetConsumeHP(RangeDeBuff) < actor.currentHp && Skill_InReuseDelay(RangeDeBuff) == 0)
		{
			AddUseSkillDesire(attacker, RangeDeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		Party party0 = null;
		final NpcInstance actor = getActor();

		RemoveAllHateInfoIF(1, 0);
		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && GetHateInfoCount() == 0)
		{
			if (Skill_GetConsumeMP(W_LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic1) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_LongRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtMoveFinished(int x, int y, int z)
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
			if (Skill_GetConsumeMP(W_LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic1) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_LongRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
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
			if (DistFromMe(h0.attacker) < 100)
			{
				AddFleeDesire(h0.attacker, 1000000);
			}
			else if (Skill_GetConsumeMP(W_LongRangeDDMagic1) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic1) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_LongRangeDDMagic1) == 0)
				{
					AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
	}

}