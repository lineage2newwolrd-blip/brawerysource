package org.mmocore.gameserver.scripts.ai.test;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_parameter extends monster_parameter
{
	public warrior_parameter(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (IsVs == 1)
		{
			c_ai0 = actor;
		}
		if (actor.param1 == 1000)
		{
			c0 = AiUtils.GetCreatureFromIndex(actor.param2);
			if (!IsNullCreature(c0))
			{
				AddUseSkillDesire(c0, 305594369/*@skill_4663_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 10000);
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 500);
			}
		}
		super.onEvtSpawn();
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
		int i10 = 0;
		int i11 = 0;
		top_desire_target = getTopHated();
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
		if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) <= 20)
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (SetCurse != 0 && AiUtils.Rand(100) < 3 && top_desire_target == attacker)
				{
					Say(AiUtils.MakeFString(1000452, "", "", "", "", ""));
					if (Skill_GetConsumeMP(SetCurse) < actor._currentMp && Skill_GetConsumeHP(SetCurse) < actor.currentHp && Skill_InReuseDelay(SetCurse) == 0)
					{
						AddUseSkillDesire(attacker, SetCurse, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
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
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target != attacker)
				{
					RemoveAllAttackDesire();
					if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
					{
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1 * 100);
					}
					switch (AiUtils.Rand(3))
					{
						case 0:
						{
							Say(AiUtils.MakeFString(1000307, "", "", "", "", ""));
							break;
						}
						case 1:
						{
							Say(AiUtils.MakeFString(1000427, "", "", "", "", ""));
							break;
						}
						case 2:
						{
							Say(AiUtils.MakeFString(1000428, "", "", "", "", ""));
							break;
						}
					}
				}
			}
		}
		if (IsVs == 1)
		{
			if (!IsNullCreature(top_desire_target) && c_ai0 == actor)
			{
				if (attacker != top_desire_target && attacker.isPlayer())
				{
					switch (AiUtils.Rand(5))
					{
						case 0:
						{
							Say(AiUtils.MakeFString(1000288, attacker.getName(), "", "", "", ""));
							break;
						}
						case 1:
						{
							Say(AiUtils.MakeFString(1000388, attacker.getName(), "", "", "", ""));
							break;
						}
						case 2:
						{
							Say(AiUtils.MakeFString(1000389, "", "", "", "", ""));
							break;
						}
						case 3:
						{
							Say(AiUtils.MakeFString(1000390, "", "", "", "", ""));
							break;
						}
						case 4:
						{
							Say(AiUtils.MakeFString(1000391, "", "", "", "", ""));
							break;
						}
					}
					c_ai0 = attacker;
					AddTimerEx(2, 20000);
					BroadcastScriptEvent(ScriptEvent.SCE_VALAKAS_STAMP, AiUtils.GetIndexFromCreature(attacker), 600);
				}
			}
		}
		if ((SpecialSkill != 0 && SpecialSkill != 458752001))
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
		if (HelpHeroSilhouette != 0)
		{
			if (AiUtils.FloatToInt(attacker.currentHp / attacker.getMaxHp() * 100) < 20 && AiUtils.Rand(100) < 3 && attacker.isPlayer())
			{
				CreateOnePrivateEx(HelpHeroSilhouette, HelpHeroAI, 0, 0, AiUtils.FloatToInt(actor.getX()) + 80, AiUtils.FloatToInt(actor.getY()) + 80, AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, AiUtils.GetIndexFromCreature(actor));
			}
		}
		if (DungeonType != 0)
		{
			for (i1 = 0; i1 < DungeonType; i1++)
			{
				CreateOnePrivateEx(DungeonTypeAI, DungeonTypePrivate, 0, 0, AiUtils.FloatToInt(actor.getX() + (i1 * 20)), AiUtils.FloatToInt(actor.getY() + (i1 * 20)), AiUtils.FloatToInt(actor.getZ()), 0, 1000, AiUtils.GetIndexFromCreature(attacker), 0);
			}
			Despawn();
		}
		if (AiUtils.Rand(100) < 5 && !IsNullCreature(top_desire_target) && ShoutTarget != 0)
		{
			if (top_desire_target == attacker)
			{
				BroadcastScriptEvent(ScriptEvent.SCE_ONE_POINT_ATTACK, AiUtils.GetIndexFromCreature(attacker), 300);
			}
		}
		if (AiUtils.Rand(100) < 5 && SelfExplosion != 0)
		{
			i0 = AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100);
			if (i0 < 50)
			{
				i0 = 10 - AiUtils.FloatToInt(i0 / 10);
				if (i0 > AiUtils.Rand(100))
				{
					if (Skill_GetConsumeMP(SelfExplosion) < actor._currentMp && Skill_GetConsumeHP(SelfExplosion) < actor.currentHp && Skill_InReuseDelay(SelfExplosion) == 0)
					{
						AddUseSkillDesire(actor, SelfExplosion, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
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
		top_desire_target = getTopHated();

		if (AttackLowHP == 1 && AiUtils.FloatToInt(attacker.currentHp / attacker.getMaxHp() * 100) < 30 && AiUtils.Rand(100) < 3)
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (top_desire_target != attacker)
				{
					RemoveAllAttackDesire();
					if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
					{
						AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1 * 100);
					}
				}
			}
		}
		if ((AttackLowLevel == 1 && p_state != State.ATTACK && p_state != State.USE_SKILL))
		{
			AddTimerEx(1, 7000);
		}
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;
		float f0 = 0;
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
		if (HalfAggressive == 1)
		{
			if (GetTimeHour() >= 5)
			{
				if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
				{
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
			}
			return;
		}
		else if (HalfAggressive == 2)
		{
			if (GetTimeHour() < 5)
			{
				if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
				{
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
			}
			return;
		}
		else if (RandomAggressive > 0)
		{
			if (AiUtils.Rand(100) < RandomAggressive && creature.isPlayer())
			{
				if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
				{
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
				return;
			}
			else if ((p_state != State.ATTACK && p_state != State.USE_SKILL))
			{
				RemoveAllAttackDesire();
			}
		}
		if (AttackLowLevel == 1)
		{
			if ((p_state == State.ATTACK || p_state == State.USE_SKILL) && DistFromMe(creature) < 300)
			{
				if (creature.getLevel() + 15 < actor.getLevel())
				{
					RemoveAllAttackDesire();
					if (creature.isPlayer() || IsInCategory(Category.summon_npc_group, creature.getClassId()))
					{
						AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 7 * 100);
					}
				}
				AddTimerEx(1, 7000);
			}
		}
		if (IsVs == 1)
		{
			if (creature.isPlayer())
			{
				if (creature.getLevel() > actor.getLevel() - 2 && creature.getLevel() < actor.getLevel() + 2 && p_state != State.ATTACK && p_state != State.USE_SKILL)
				{
					switch (AiUtils.Rand(5))
					{
						case 0:
						{
							Say(AiUtils.MakeFString(1000287, creature.getName(), "", "", "", ""));
							break;
						}
						case 1:
						{
							Say(AiUtils.MakeFString(1000384, creature.getName(), "", "", "", ""));
							break;
						}
						case 2:
						{
							Say(AiUtils.MakeFString(1000385, creature.getName(), "", "", "", ""));
							break;
						}
						case 3:
						{
							Say(AiUtils.MakeFString(1000386, creature.getName(), "", "", "", ""));
							break;
						}
						case 4:
						{
							Say(AiUtils.MakeFString(1000387, creature.getName(), "", "", "", ""));
							break;
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
				}
			}
		}
		if (DaggerBackAttack == 1)
		{
			if (creature.isPlayer() && AiUtils.Rand(100) < 50 && p_state != State.ATTACK && p_state != State.USE_SKILL)
			{
				i0 = GetAngleFromTarget(creature);
				if (DistFromMe(creature) < 100 && i0 > 36864 && i0 < 61440)
				{
					switch (AiUtils.Rand(4))
					{
						case 0:
						{
							Say(AiUtils.MakeFString(1000286, creature.getName(), "", "", "", ""));
							break;
						}
						case 1:
						{
							Say(AiUtils.MakeFString(1000381, creature.getName(), "", "", "", ""));
							break;
						}
						case 2:
						{
							Say(AiUtils.MakeFString(1000382, "", "", "", "", ""));
							break;
						}
						case 3:
						{
							Say(AiUtils.MakeFString(1000383, "", "", "", "", ""));
							break;
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
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		int x = 0;
		int y = 0;
		int z = 0;
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
			RemoveAllAttackDesire();
			if (Skill_GetEffectPoint(skill_name_id) > 0)
			{
				if ((p_state == State.ATTACK && top_desire_target == target))
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
					AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, f0 * 150);
				}
			}
			if (GetPathfindFailCount() > 10 && speller == top_desire_target && AiUtils.FloatToInt(actor.currentHp) != AiUtils.FloatToInt(actor.getMaxHp()))
			{
				InstantTeleport(actor, AiUtils.FloatToInt(speller.getX()), AiUtils.FloatToInt(speller.getY()), AiUtils.FloatToInt(speller.getZ()));
			}
		}
		if (SwapPosition != 0)
		{
			if (p_state == State.ATTACK && AiUtils.Rand(100) < SwapPosition)
			{
				if (!IsNullCreature(top_desire_target))
				{
					if (IsInCategory(Category.fighter_group, top_desire_target.getClassId()))
					{
						if (IsInCategory(Category.fighter_group, top_desire_target.getClassId()) || IsInCategory(Category.cleric_group, top_desire_target.getClassId()))
						{
							if (DistFromMe(top_desire_target) < DistFromMe(speller))
							{
								if (DistFromMe(speller) < 900)
								{
									x = AiUtils.FloatToInt(speller.getX());
									y = AiUtils.FloatToInt(speller.getY());
									z = AiUtils.FloatToInt(speller.getZ());
									InstantTeleport(speller, AiUtils.FloatToInt(top_desire_target.getX()), AiUtils.FloatToInt(top_desire_target.getY()), AiUtils.FloatToInt(top_desire_target.getZ()));
									InstantTeleport(top_desire_target, x, y, z);
									AddAttackDesire(speller, DesireMove.MOVE_TO_TARGET, 1000);
								}
							}
						}
					}
				}
			}
		}
		super.onEvtSeeSpell(skill_name_id, speller, target);
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if ((script_event_arg1 == ScriptEvent.SCE_VALAKAS_STAMP && p_state != State.ATTACK))
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				if (!actor.isAlive())
				{
					return;
				}
				if (c_ai0 != c0)
				{
					switch (AiUtils.Rand(3))
					{
						case 0:
						{
							Say(AiUtils.MakeFString(1000392, "", "", "", "", ""));
							break;
						}
						case 1:
						{
							Say(AiUtils.MakeFString(1000393, "", "", "", "", ""));
							break;
						}
						case 2:
						{
							Say(AiUtils.MakeFString(1000394, "", "", "", "", ""));
							break;
						}
					}
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		if (script_event_arg1 == ScriptEvent.SCE_ONE_POINT_ATTACK && AiUtils.Rand(100) < 50)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				if (!actor.isAlive())
				{
					return;
				}
				RemoveAllAttackDesire();
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1 * 100);
				}
			}
		}
		if (script_event_arg1 == ScriptEvent.SCE_ATTACK_BOSS_TARGET)
		{
			c0 = AiUtils.GetCreatureFromIndex(script_event_arg2);
			if (!IsNullCreature(c0))
			{
				if (!actor.isAlive())
				{
					return;
				}
				if (c0.isPlayer() || IsInCategory(Category.summon_npc_group, c0.getClassId()))
				{
					AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 1 * 100);
				}
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