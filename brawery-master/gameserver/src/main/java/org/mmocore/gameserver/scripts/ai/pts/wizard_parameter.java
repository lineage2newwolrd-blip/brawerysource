package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_parameter extends monster_parameter
{

	public wizard_parameter(final NpcInstance actor)
	{
		super(actor);
		SpecialSkill = 458752001;
	}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (actor.param1 == 1000)
		{
			c0 = AiUtils.GetCreatureFromIndex(actor.param2);
			if (!IsNullCreature(c0))
			{
				AddHateInfo(c0, 500, 0, 1, 1);
				AddUseSkillDesire(c0, 305594369/*@skill_4663_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		final NpcInstance actor = getActor();

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
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		float f0 = 0;
		int i11 = 0;
		final NpcInstance actor = getActor();

		if (CreviceOfDiminsion != 0)
		{
			if (!InMyTerritory(attacker))
			{
				RemoveAttackDesire(attacker.getObjectId());
				return;
			}
		}
		if (!actor.isInZonePeace())
		{
			if (SSQLoserTeleport != 0)
			{
				if (attacker.getAccessLevel() == 0)
				{
					if ((SSQLoserTeleport != 1 && SSQLoserTeleport != 2))
					{
						Say("버그:SSQLoserTeleport에 잘못된 값이 들어갔습니다. 값 = " + SSQLoserTeleport);
					}
					else if (GetSSQStatus() == 3)
					{
						i0 = GetSSQSealOwner(SSQLoserTeleport);
						if (attacker.isPlayer())
						{
							if (i0 == 0 && AiUtils.GetSSQPart(attacker) == 0)
							{
								RemoveAttackDesire(attacker.getObjectId());
								InstantTeleport(attacker, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 1 && AiUtils.GetSSQPart(attacker) != 1)
							{
								RemoveAttackDesire(attacker.getObjectId());
								InstantTeleport(attacker, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 2 && AiUtils.GetSSQPart(attacker) != 2)
							{
								RemoveAttackDesire(attacker.getObjectId());
								InstantTeleport(attacker, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
						else if (IsInCategory(Category.summon_npc_group, attacker.getClassId()))
						{
							if (i0 == 0 && AiUtils.GetSSQPart(attacker.getPlayer()) == 0)
							{
								RemoveAttackDesire(attacker.getPlayer().getObjectId());
								InstantTeleport(attacker.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 1 && AiUtils.GetSSQPart(attacker.getPlayer()) != 1)
							{
								RemoveAttackDesire(attacker.getPlayer().getObjectId());
								InstantTeleport(attacker.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 2 && AiUtils.GetSSQPart(attacker.getPlayer()) != 2)
							{
								RemoveAttackDesire(attacker.getPlayer().getObjectId());
								InstantTeleport(attacker.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
					}
					else if (GetSSQStatus() == 1)
					{
						i0 = GetSSQSealOwner(SSQLoserTeleport);
						if (attacker.isPlayer())
						{
							if (AiUtils.GetSSQPart(attacker) == 0)
							{
								RemoveAttackDesire(attacker.getObjectId());
								InstantTeleport(attacker, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
						else if (IsInCategory(Category.summon_npc_group, attacker.getClassId()))
						{
							if (AiUtils.GetSSQPart(attacker.getPlayer()) == 0)
							{
								RemoveAttackDesire(attacker.getPlayer().getObjectId());
								InstantTeleport(attacker.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
					}
				}
			}
		}
		if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) <= 20 && i0 == 1)
		{
			if (SetCurse != 0 && AiUtils.Rand(100) < 3 && attacker == h0.attacker)
			{
				Say(AiUtils.MakeFString(1000452, "", "", "", "", ""));
				if (Skill_GetConsumeMP(SetCurse) < actor._currentMp && Skill_GetConsumeHP(SetCurse) < actor.currentHp && Skill_InReuseDelay(SetCurse) == 0)
				{
					AddUseSkillDesire(attacker, SetCurse, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (SoulShot != 0)
		{
			i0 = AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(6553601/*@skill_100_1*/));
			i1 = AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(269811713/*@skill_4117_1*/));
			if ((i0 <= 0 && i1 <= 0))
			{
				if (AiUtils.Rand(100) < SoulShotRate)
				{
					UseSoulShot(SoulShot);
				}
			}
		}
		if (SpiritShot != 0)
		{
			i0 = AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(6553601/*@skill_100_1*/));
			if (i0 <= 0)
			{
				i0 = AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(269811713/*@skill_4117_1*/));
				if (AiUtils.Rand(100) < SpiritShotRate && i0 <= 0)
				{
					UseSpiritShot(SpiritShot, SpeedBonus, HealBonus);
				}
			}
		}
		i11 = AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(330563587/*@skill_5044_3*/));
		if ((LongRangeGuardRate == -1 || skill_id == 28 || skill_id == 680 || skill_id == 51 || skill_id == 511 || skill_id == 15 || skill_id == 254 || skill_id == 1069 || skill_id == 1097 || skill_id == 1042 || skill_id == 1072 || skill_id == 1170 || skill_id == 352 || skill_id == 358 || skill_id == 1394 || skill_id == 695 || skill_id == 115 || skill_id == 1083 || skill_id == 1160 || skill_id == 1164 || skill_id == 1201 || skill_id == 1206 || skill_id == 1222 || skill_id == 1223 || skill_id == 1224 || skill_id == 1092 || skill_id == 65 || skill_id == 106 || skill_id == 122 || skill_id == 127 || skill_id == 1049 || skill_id == 1064 || skill_id == 1071 || skill_id == 1074 || skill_id == 1169 || skill_id == 1263 || skill_id == 1269 || skill_id == 352 || skill_id == 353 || skill_id == 1336 || skill_id == 1337 || skill_id == 1338 || skill_id == 1358 || skill_id == 1359 || skill_id == 402 || skill_id == 403 || skill_id == 412 || skill_id == 1386 || skill_id == 1394 || skill_id == 1396 || skill_id == 485 || skill_id == 501 || skill_id == 1445 || skill_id == 1446 || skill_id == 1447 || skill_id == 522 || skill_id == 531 || skill_id == 1481 || skill_id == 1482 || skill_id == 1483 || skill_id == 1484 || skill_id == 1485 || skill_id == 1486 || skill_id == 695 || skill_id == 696 || skill_id == 716 || skill_id == 775 || skill_id == 1511 || skill_id == 792 || skill_id == 1524 || skill_id == 1529))
		{
		}
		else if (LongRangeGuardRate > 0)
		{
			if (DistFromMe(attacker) > 150)
			{
				if (i11 <= 0 && AiUtils.Rand(100) < LongRangeGuardRate)
				{
					AddUseSkillDesire(actor, 330563587/*@skill_5044_3*/, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 10000000000d);
				}
			}
			else if (i11 <= 0)
			{
			}
			else
			{
				Dispel(actor, Skill_GetAbnormalType(330563587/*@skill_5044_3*/));
			}
		}
		if ((AttackLowLevel == 1 && p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			AddTimerEx(1, 7000);
		}
		if (AttackLowHP == 1 && AiUtils.FloatToInt(attacker.currentHp / attacker.getMaxHp() * 100) < 30 && AiUtils.Rand(100) < 10)
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
				if (h0.attacker != attacker)
				{
					RemoveAllHateInfoIF(0, 0);
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
				}
			}
		}
		if (HelpHeroSilhouette != 0)
		{
			if (AiUtils.FloatToInt(attacker.currentHp / attacker.getMaxHp() * 100) < 20 && AiUtils.Rand(100) < 3 && attacker.isPlayer())
			{
				CreateOnePrivateEx(HelpHeroSilhouette, HelpHeroAI, 0, 0, AiUtils.FloatToInt(actor.getX()) + 80, AiUtils.FloatToInt(actor.getY()) + 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, AiUtils.GetIndexFromCreature(actor));
			}
		}
		if (SpecialSkill == 1)
		{
			if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 30 && AiUtils.Rand(100) < 10)
			{
				if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(SpecialSkill)) <= 0)
				{
					switch (AiUtils.Rand(4))
					{
						case 0:
						{
							Say(AiUtils.MakeFString(1000290, "", "", "", "", ""));
							break;
						}
						case 1:
						{
							Say(AiUtils.MakeFString(1000395, "", "", "", "", ""));
							break;
						}
						case 2:
						{
							Say(AiUtils.MakeFString(1000396, "", "", "", "", ""));
							break;
						}
						case 3:
						{
							Say(AiUtils.MakeFString(1000397, "", "", "", "", ""));
							break;
						}
					}
					if (Skill_GetConsumeMP(SpecialSkill) < actor._currentMp && Skill_GetConsumeHP(SpecialSkill) < actor.currentHp && Skill_InReuseDelay(SpecialSkill) == 0)
					{
						AddUseSkillDesire(actor, SpecialSkill, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
			}
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
		if (AiUtils.Rand(100) < 5 && i0 == 1 && ShoutTarget != 0)
		{
			if (h0.attacker == attacker)
			{
				BroadcastScriptEvent(ScriptEvent.SCE_ONE_POINT_ATTACK, AiUtils.GetIndexFromCreature(attacker), 300);
			}
		}
		if (AiUtils.Rand(100) < 5 && SelfExplosion != 0)
		{
			i0 = AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100);
			i0 = 10 - AiUtils.FloatToInt(i0 / 10);
			if (i0 > AiUtils.Rand(100))
			{
				if (Skill_GetConsumeMP(SelfExplosion) < actor._currentMp && Skill_GetConsumeHP(SelfExplosion) < actor.currentHp && Skill_InReuseDelay(SelfExplosion) == 0)
				{
					AddUseSkillDesire(actor, SelfExplosion, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(SelfExplosion) < actor._currentMp && Skill_GetConsumeHP(SelfExplosion) < actor.currentHp && Skill_InReuseDelay(SelfExplosion) == 0)
				{
					AddUseSkillDesire(actor, SelfExplosion, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				if (Skill_GetConsumeMP(SelfExplosion) < actor._currentMp && Skill_GetConsumeHP(SelfExplosion) < actor.currentHp && Skill_InReuseDelay(SelfExplosion) == 0)
				{
					AddUseSkillDesire(actor, SelfExplosion, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (IsTransform > 0)
		{
			switch (actor.param3)
			{
				case 0:
				{
					if (actor.param3 < IsTransform && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 70 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50 && AiUtils.Rand(100) < 30)
					{
						c_ai0 = attacker;
						switch (AiUtils.Rand(3))
						{
							case 0:
							{
								Say(AiUtils.MakeFString(1000406, "", "", "", "", ""));
								break;
							}
							case 1:
							{
								Say(AiUtils.MakeFString(1000407, "", "", "", "", ""));
								break;
							}
							case 2:
							{
								Say(AiUtils.MakeFString(1000408, "", "", "", "", ""));
								break;
							}
						}
						i0 = GetDirection(actor);
						CreateOnePrivateEx(step1, actor.getAI().getClass().getName(), 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), i0 * 182, 1000, AiUtils.GetIndexFromCreature(c_ai0), 1);
						Despawn();
					}
					break;
				}
				case 1:
				{
					if (actor.param3 < IsTransform && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 50 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 30 && AiUtils.Rand(100) < 20)
					{
						c_ai0 = attacker;
						switch (AiUtils.Rand(3))
						{
							case 0:
							{
								Say(AiUtils.MakeFString(1000409, "", "", "", "", ""));
								break;
							}
							case 1:
							{
								Say(AiUtils.MakeFString(1000410, "", "", "", "", ""));
								break;
							}
							case 2:
							{
								Say(AiUtils.MakeFString(1000411, "", "", "", "", ""));
								break;
							}
						}
						i0 = GetDirection(actor);
						CreateOnePrivateEx(step2, actor.getAI().getClass().getName(), 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), i0 * 182, 1000, AiUtils.GetIndexFromCreature(c_ai0), 2);
						Despawn();
					}
					break;
				}
				case 2:
				{
					if (actor.param3 < IsTransform && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 30 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 5 && AiUtils.Rand(100) < 10)
					{
						c_ai0 = attacker;
						switch (AiUtils.Rand(3))
						{
							case 0:
							{
								Say(AiUtils.MakeFString(1000412, "", "", "", "", ""));
								break;
							}
							case 1:
							{
								Say(AiUtils.MakeFString(1000413, "", "", "", "", ""));
								break;
							}
							case 2:
							{
								Say(AiUtils.MakeFString(1000414, "", "", "", "", ""));
								break;
							}
						}
						i0 = GetDirection(actor);
						CreateOnePrivateEx(step3, actor.getAI().getClass().getName(), 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), i0 * 182, 1000, AiUtils.GetIndexFromCreature(c_ai0), 3);
						Despawn();
					}
					break;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (AttackLowHP == 1 && AiUtils.FloatToInt(attacker.currentHp / attacker.getMaxHp() * 100) < 30 && AiUtils.Rand(100) < 3)
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
				if (h0.attacker != attacker)
				{
					RemoveAllHateInfoIF(0, 0);
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
				}
			}
		}
		if ((AttackLowLevel == 1 && p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			AddTimerEx(1, 7000);
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (!creature.isPlayer() && !IsInCategory(Category.summon_npc_group, creature.getClassId()))
		{
			return;
		}
		if (!actor.isAlive())
		{
			return;
		}
		if (!actor.isInZonePeace())
		{
			if (SSQLoserTeleport != 0)
			{
				if (creature.getAccessLevel() == 0)
				{
					if ((SSQLoserTeleport != 1 && SSQLoserTeleport != 2))
					{
						Say("버그:SSQLoserTeleport에 잘못된 값이 들어갔습니다. 값 = " + SSQLoserTeleport);
					}
					else if (GetSSQStatus() == 3)
					{
						i0 = GetSSQSealOwner(SSQLoserTeleport);
						if (creature.isPlayer())
						{
							if (i0 == 0 && AiUtils.GetSSQPart(creature) == 0)
							{
								RemoveAttackDesire(creature.getObjectId());
								InstantTeleport(creature, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 1 && AiUtils.GetSSQPart(creature) != 1)
							{
								RemoveAttackDesire(creature.getObjectId());
								InstantTeleport(creature, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 2 && AiUtils.GetSSQPart(creature) != 2)
							{
								RemoveAttackDesire(creature.getObjectId());
								InstantTeleport(creature, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
						else if (IsInCategory(Category.summon_npc_group, creature.getClassId()))
						{
							if (i0 == 0 && AiUtils.GetSSQPart(creature.getPlayer()) == 0)
							{
								RemoveAttackDesire(creature.getPlayer().getObjectId());
								InstantTeleport(creature.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 1 && AiUtils.GetSSQPart(creature.getPlayer()) != 1)
							{
								RemoveAttackDesire(creature.getPlayer().getObjectId());
								InstantTeleport(creature.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 2 && AiUtils.GetSSQPart(creature.getPlayer()) != 2)
							{
								RemoveAttackDesire(creature.getPlayer().getObjectId());
								InstantTeleport(creature.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
					}
					else if (GetSSQStatus() == 1)
					{
						i0 = GetSSQSealOwner(SSQLoserTeleport);
						if (creature.isPlayer())
						{
							if (AiUtils.GetSSQPart(creature) == 0)
							{
								RemoveAttackDesire(creature.getObjectId());
								InstantTeleport(creature, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
						else if (IsInCategory(Category.summon_npc_group, creature.getClassId()))
						{
							if (AiUtils.GetSSQPart(creature.getPlayer()) == 0)
							{
								RemoveAttackDesire(creature.getPlayer().getObjectId());
								InstantTeleport(creature.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
					}
				}
			}
		}
		if (AttackLowLevel == 1)
		{
			if ((p_state == State.ATTACK || p_state == State.USE_SKILL) && DistFromMe(creature) < 300)
			{
				if (creature.getLevel() + 15 < actor.getLevel())
				{
					RemoveAllHateInfoIF(0, 0);
					if (creature.isPlayer() || IsInCategory(Category.summon_npc_group, creature.getClassId()))
					{
						AddHateInfo(creature, 7 * 100, 0, 1, 1);
					}
				}
				AddTimerEx(1, 7000);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (!actor.isInZonePeace())
		{
			if (SSQLoserTeleport != 0)
			{
				if (speller.getAccessLevel() == 0)
				{
					if ((SSQLoserTeleport != 1 && SSQLoserTeleport != 2))
					{
						Say("버그:SSQLoserTeleport에 잘못된 값이 들어갔습니다. 값 = " + SSQLoserTeleport);
					}
					else if (GetSSQStatus() == 3)
					{
						i0 = GetSSQSealOwner(SSQLoserTeleport);
						if (speller.isPlayer())
						{
							if (i0 == 0 && AiUtils.GetSSQPart(speller) == 0)
							{
								RemoveAttackDesire(speller.getObjectId());
								InstantTeleport(speller, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 1 && AiUtils.GetSSQPart(speller) != 1)
							{
								RemoveAttackDesire(speller.getObjectId());
								InstantTeleport(speller, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 2 && AiUtils.GetSSQPart(speller) != 2)
							{
								RemoveAttackDesire(speller.getObjectId());
								InstantTeleport(speller, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
						else if (IsInCategory(Category.summon_npc_group, speller.getClassId()))
						{
							if (i0 == 0 && AiUtils.GetSSQPart(speller.getPlayer()) == 0)
							{
								RemoveAttackDesire(speller.getPlayer().getObjectId());
								InstantTeleport(speller.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 1 && AiUtils.GetSSQPart(speller.getPlayer()) != 1)
							{
								RemoveAttackDesire(speller.getPlayer().getObjectId());
								InstantTeleport(speller.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
							else if (i0 == 2 && AiUtils.GetSSQPart(speller.getPlayer()) != 2)
							{
								RemoveAttackDesire(speller.getPlayer().getObjectId());
								InstantTeleport(speller.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
					}
					else if (GetSSQStatus() == 1)
					{
						i0 = GetSSQSealOwner(SSQLoserTeleport);
						if (speller.isPlayer())
						{
							if (AiUtils.GetSSQPart(speller) == 0)
							{
								RemoveAttackDesire(speller.getObjectId());
								InstantTeleport(speller, SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
						else if (IsInCategory(Category.summon_npc_group, speller.getClassId()))
						{
							if (AiUtils.GetSSQPart(speller.getPlayer()) == 0)
							{
								RemoveAttackDesire(speller.getPlayer().getObjectId());
								InstantTeleport(speller.getPlayer(), SSQTelPosX, SSQTelPosY, SSQTelPosZ);
								return;
							}
						}
					}
				}
			}
		}
		if (speller.getLevel() + 15 < actor.getLevel())
		{
			RemoveAllHateInfoIF(0, 0);
			if (Skill_GetEffectPoint(skill_name_id) > 0)
			{
				if ((p_state == State.ATTACK && top_desire_target == speller))
				{
					i0 = Skill_GetEffectPoint(skill_name_id);
					f0 = 0;
					if (SetHateGroup >= 0)
					{
						if (IsInCategory(SetHateGroup, speller.getClassId()))
						{
							f0 = f0 + SetHateGroupRatio;
						}
					}
					if (speller.getClassId() == SetHateOccupation)
					{
						f0 = f0 + SetHateOccupationRatio;
					}
					if (SetHateRace == speller.getRaceId())
					{
						f0 = f0 + SetHateRaceRatio;
					}
					f0 = 1.000000f * i0 / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * i0 / (actor.getLevel() + 7)));
					AddHateInfo(speller, AiUtils.FloatToInt(f0 * 150), 0, 1, 1);
				}
				else
				{
					i0 = Skill_GetEffectPoint(skill_name_id);
					f0 = 0;
					if (SetHateGroup >= 0)
					{
						if (IsInCategory(SetHateGroup, speller.getClassId()))
						{
							f0 = f0 + SetHateGroupRatio;
						}
					}
					if (speller.getClassId() == SetHateOccupation)
					{
						f0 = f0 + SetHateOccupationRatio;
					}
					if (SetHateRace == speller.getRaceId())
					{
						f0 = f0 + SetHateRaceRatio;
					}
					f0 = 1.000000f * i0 / (actor.getLevel() + 7) + (f0 / 100 * (1.000000f * i0 / (actor.getLevel() + 7)));
					AddHateInfo(speller, AiUtils.FloatToInt(f0 * 75), 0, 1, 1);
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;

		if (script_event_arg1 == ScriptEvent.SCE_ONE_POINT_ATTACK && AiUtils.Rand(100) < 50)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				RemoveAllHateInfoIF(0, 0);
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddHateInfo(c0, 2 * 100, 0, 1, 1);
				}
			}
		}
		if (script_event_arg1 == ScriptEvent.SCE_ATTACK_BOSS_TARGET)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				LookNeighbor(500);
			}
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		if (SelfExplosion != 0)
		{
			if (skill_name_id == SelfExplosion)
			{
				Suicide();
			}
		}
	}

}