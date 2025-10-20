package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class party_leader_wizard_oracle extends wizard
{
	public int W_LongRangeDDMagic = 458752001;
	public int W_ShortRangeDDMagic = 458752001;
	public int debuff = 458752001;
	public String ai_type = "warrior";
	public int silhouette = 1020130;
	public int bSpawn = 0;

	public party_leader_wizard_oracle(final NpcInstance actor)
	{
		super(actor);
		Attack_DecayRatio = 6.600000f;
	}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		i_ai1 = GetTick();
		i_ai2 = 0;
		i_ai3 = 0;
		i_ai4 = 0;
		AddTimerEx(1100, 60 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Party party0 = null;
		int i0 = 0;
		float f0 = 0;
		final NpcInstance actor = getActor();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		i_ai1 = GetTick();
		if (AiUtils.Rand(100) < 3)
		{
			if (Skill_GetConsumeMP(debuff) < actor._currentMp && Skill_GetConsumeHP(debuff) < actor.currentHp && Skill_InReuseDelay(debuff) == 0)
			{
				AddUseSkillDesire(attacker, debuff, DesireType.ATTACK, DesireMove.STAND, 1000000);
			}
		}
		if (attacker.isPlayer())
		{
			party0 = AiUtils.GetParty(attacker);
		}
		if (!IsNullParty(party0))
		{
			i0 = party0.getMemberCount();
			if (bSpawn == 0)
			{
				if (i0 >= 5)
				{
					if (i_ai3 == 0)
					{
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 80, AiUtils.FloatToInt(actor.getY()) + 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 40, AiUtils.FloatToInt(actor.getY()) + 40, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) - 80, AiUtils.FloatToInt(actor.getY()) - 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) - 40, AiUtils.FloatToInt(actor.getY()) - 40, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						i_ai3 = 1;
					}
				}
				else if (i0 >= 3)
				{
					if (i_ai3 == 0)
					{
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 80, AiUtils.FloatToInt(actor.getY()) + 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						CreateOnePrivateEx(silhouette, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()) + 40, AiUtils.FloatToInt(actor.getY()) + 40, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						i_ai3 = 1;
					}
				}
			}
		}
		if (i_ai0 == 0)
		{
			h0 = GetMaxHateInfo(0);
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
					if (DistFromMe(attacker) > 200)
					{
						if (i0 == 1)
						{
							if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 0))
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
								i_ai4 = 1;
								AddTimerEx(1200, 20 * 1000);
								return;
							}
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
							if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 0))
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
								i_ai4 = 1;
								AddTimerEx(1200, 20 * 1000);
								return;
							}
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

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai0 == 0)
		{
			i_ai1 = GetTick();
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
				if (privat != actor)
				{
					if (DistFromMe(attacker) >= 200)
					{
						if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 0))
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
							i_ai4 = 1;
							AddTimerEx(1200, 20 * 1000);
							return;
						}
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
		super.onEvtPartyAttacked(attacker, privat, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i0 = 0;
		Party party0 = null;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		party0 = AiUtils.GetParty(attacker);
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
			if (victim != actor)
			{
				if (!IsNullParty(party0))
				{
					if (AiUtils.Rand(100) < 50)
					{
						if (DistFromMe(attacker) >= 200)
						{
							if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 0))
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
								i_ai4 = 1;
								AddTimerEx(1200, 20 * 1000);
								return;
							}
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
				else if (AiUtils.Rand(100) < 20)
				{
					if (DistFromMe(attacker) >= 200)
					{
						if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 0))
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
							i_ai4 = 1;
							AddTimerEx(1200, 20 * 1000);
							return;
						}
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
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		Party party0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		i_ai1 = GetTick();
		party0 = AiUtils.GetParty(creature);
		if (!IsNullParty(party0))
		{
			if (AiUtils.Rand(100) < 50)
			{
				if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 0))
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
					i_ai4 = 1;
					AddTimerEx(1200, 20 * 1000);
					return;
				}
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
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == 1100)
		{
			if (GetTick() - i_ai1 > 5 * 60 * 1000)
			{
				i_ai3 = 0;
				BroadcastScriptEvent(1000, 0, 300);
			}
			AddTimerEx(1100, 1 * 60 * 1000);
		}
		else if (timer_id == 1200)
		{
			i_ai4 = 0;
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		int i0 = 0;

		if (script_event_arg1 == ScriptEvent.SCE_PRIVATE_DESPAWN)
		{
			i_ai3 = 0;
			BroadcastScriptEvent(1000, 0, 300);
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai0 == 0)
		{
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
				if (DistFromMe(h0.attacker) > 200)
				{
					if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 0))
					{
						if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
						{
							if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
							{
								AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
						}
						i_ai4 = 1;
						AddTimerEx(1200, 20 * 1000);
						return;
					}
					else if ((W_LongRangeDDMagic == 344915978 && i_ai4 == 1))
					{
						if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
						{
							if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
							{
								AddUseSkillDesire(h0.attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(h0.attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
						}
					}
					else if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
					{
						if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
						{
							AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						else
						{
							AddUseSkillDesire(h0.attacker, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
					else
					{
						i_ai0 = 1;
						AddAttackDesire(h0.attacker, DesireMove.MOVE_TO_TARGET, 1000);
					}
				}
				else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
					{
						AddUseSkillDesire(h0.attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(h0.attacker, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
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

	@Override
	protected void onEvtDead(Creature attacker)
	{
		Creature c0 = null;
		Creature c1 = null;
		Party party0 = null;
		int i1 = 0;
		int i2 = 0;
		final NpcInstance actor = getActor();

		if (actor.getClassId() == 1022260)
		{
			if (AiUtils.Rand(10000) <= 2292)
			{
				DropItem1(actor, 9593, 1);
			}
		}
		else if (actor.getClassId() == 1022262)
		{
			if (AiUtils.Rand(10000) <= 1173)
			{
				DropItem1(actor, 9594, 1);
			}
		}
		else if (actor.getClassId() == 1022264)
		{
			if (AiUtils.Rand(10000) <= 1207)
			{
				DropItem1(actor, 9594, 1);
			}
		}
		else if (actor.getClassId() == 1022266)
		{
			if (AiUtils.Rand(10000) <= 493)
			{
				DropItem1(actor, 9596, 1);
			}
		}
		else if (actor.getClassId() == 1022257)
		{
			if (AiUtils.Rand(10000) <= 2087)
			{
				DropItem1(actor, 9593, 1);
			}
		}
		else if (actor.getClassId() == 1022258)
		{
			if (AiUtils.Rand(10000) <= 2147)
			{
				DropItem1(actor, 9593, 1);
			}
		}
	}

}