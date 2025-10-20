package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.ai.maker.default_maker;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class beres_private extends warrior_aggressive
{
	public int PhysicalSpecial = 458752001;
	public String aareadata = "beres_private_area_default";
	public int silhouette1 = 1020130;
	public int silhouette2 = 1020130;
	public String ai_type = "beres_private_private";
	public String areadata_name = "24_22_water_";

	public beres_private(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
		i_ai2 = 0;
		i_ai3 = 0;
		i_ai4 = 0;
		i_quest0 = 0;
		c_ai0 = AiUtils.GetNullCreature();
		AddTimerEx(1102, 30 * 1000);
		AddTimerEx(1103, 5000);
	}

	@Override
	protected void onEvtNoDesire()
	{

	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		Creature c1 = null;
		int i0 = 0;
		default_maker maker0 = null;
		String s0 = null;
		String s1 = null;

		if (script_event_arg1 == ScriptEvent.SCE_DARK_WATERDRAGON_AREA_INDEX)
		{
			i_ai2 = script_event_arg2;
			if (AiUtils.Rand(100) < 50)
			{
				i_ai3 = 4;
			}
			else
			{
				i_ai3 = 5;
			}
			if (i_ai2 < 10)
			{
				s1 = "00";
			}
			else
			{
				s1 = "0";
			}
			if (i_ai2 != 0)
			{
				s0 = areadata_name + s1 + i_ai2 + "_04";
				AiUtils.Area_SetOnOff(s0, false);
				s0 = areadata_name + s1 + i_ai2 + "_05";
				AiUtils.Area_SetOnOff(s0, false);
				s0 = areadata_name + s1 + i_ai2 + "_03";
				AiUtils.Area_SetOnOff(s0, false);
				s0 = areadata_name + s1 + i_ai2 + "_06";
				AiUtils.Area_SetOnOff(s0, false);
				s0 = areadata_name + s1 + i_ai2 + "_01";
				AiUtils.Area_SetOnOff(s0, false);
				s0 = areadata_name + s1 + i_ai2 + "_02";
				AiUtils.Area_SetOnOff(s0, false);
				s0 = areadata_name + s1 + i_ai2 + "_0" + i_ai3;
				AiUtils.Area_SetOnOff(s0, true);
			}
		}
	}

	@Override
	protected void onEvtPartyAttacked(Creature attacker, NpcInstance privat, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(100) < 30)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0 && (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(23134209/*@skill_353_1*/)) <= 0 && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(87556097/*@skill_1336_1*/)) <= 0))
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (privat != actor)
		{
			if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
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
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		int i4 = 0;
		int i5 = 0;
		int i6 = 0;
		int i7 = 0;
		int i8 = 0;
		int i9 = 0;
		Creature c0 = null;
		Creature c1 = null;
		Creature c2 = null;
		Creature c3 = null;
		Creature c4 = null;
		Creature c5 = null;
		Party party0 = null;
		final NpcInstance actor = getActor();

		c_ai0 = attacker;
		if (i_ai1 == 0)
		{
			party0 = AiUtils.GetParty(attacker);
			if (!IsNullParty(party0))
			{
				i0 = party0.getMemberCount();
				i1 = AiUtils.Rand(i0);
				i2 = AiUtils.Rand(i0);
				i3 = AiUtils.Rand(i0);
				i4 = AiUtils.Rand(i0);
				i5 = AiUtils.Rand(i0);
				c0 = GetMemberOfParty(party0, i1);
				c1 = GetMemberOfParty(party0, i2);
				c2 = GetMemberOfParty(party0, i3);
				c3 = GetMemberOfParty(party0, i4);
				c4 = GetMemberOfParty(party0, i5);
				if (i0 >= 6)
				{
					if (!IsNullCreature(c0))
					{
						if (DistFromMe(c0) < 500)
						{
							CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(c0.getX() + 110), AiUtils.FloatToInt(c0.getY()), AiUtils.FloatToInt(c0.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c0), 0);
						}
						else
						{
							CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() - 150), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						}
					}
					else
					{
						CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() - 150), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
					}
					if (!IsNullCreature(c1))
					{
						if (DistFromMe(c1) < 500)
						{
							CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(c1.getX() + 10), AiUtils.FloatToInt(c1.getY()), AiUtils.FloatToInt(c1.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c1), 0);
						}
						else
						{
							CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() - 150), AiUtils.FloatToInt(actor.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c1), 0);
						}
					}
					else
					{
						CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() - 150), AiUtils.FloatToInt(actor.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c1), 0);
					}
				}
			}
			if (!IsNullCreature(c0))
			{
				if (DistFromMe(c0) < 500)
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(c0.getX() + 10), AiUtils.FloatToInt(c0.getY()), AiUtils.FloatToInt(c0.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c0), 0);
				}
				else
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 150), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
				}
			}
			else
			{
				CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 150), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
			}
			if (!IsNullCreature(c1))
			{
				if (DistFromMe(c1) < 500)
				{
					CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(c1.getX() + 10), AiUtils.FloatToInt(c1.getY()), AiUtils.FloatToInt(c1.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c1), 0);
				}
				else
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 150), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
				}
			}
			else
			{
				CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 150), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
			}
			if (!IsNullCreature(c2))
			{
				if (DistFromMe(c2) < 500)
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(c2.getX() + 10), AiUtils.FloatToInt(c2.getY()), AiUtils.FloatToInt(c2.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c2), 0);
				}
				else
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() - 30), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
				}
			}
			else
			{
				CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() - 30), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
			}
			if (!IsNullCreature(c3))
			{
				if (DistFromMe(c3) < 500)
				{
					CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(c3.getX() + 10), AiUtils.FloatToInt(c3.getY()), AiUtils.FloatToInt(c3.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c3), 0);
				}
				else
				{
					CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() - 30), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
				}
			}
			else
			{
				CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() - 30), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
			}
			if (!IsNullCreature(c4))
			{
				if (DistFromMe(c4) < 500)
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(c4.getX() + 10), AiUtils.FloatToInt(c4.getY()), AiUtils.FloatToInt(c4.getZ()), 0, 0, AiUtils.GetIndexFromCreature(c4), 0);
				}
				else
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 80), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
				}
			}
			else
			{
				CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 80), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
			}
			i_ai1 = 1;
		}
		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (AiUtils.Rand(100) < 33 && DistFromMe(attacker) > 100)
			{
				if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
				{
					AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.STAND, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		Party party0 = null;
		int i0 = 0;
		int i1 = 0;
		Creature c0 = null;
		Creature c1 = null;
		final NpcInstance actor = getActor();

		if (timer_id == 1100)
		{
			i_ai1 = 0;
		}
		else if (timer_id == 1101)
		{
			if (i_quest0 == 0)
			{
				if (!IsNullCreature(c_ai0))
				{
					party0 = AiUtils.GetParty(c_ai0);
					if (!IsNullParty(party0))
					{
						i0 = party0.getMemberCount();
						i1 = AiUtils.Rand(i0);
						if (i1 == 0)
						{
							i1 = 1;
						}
						c0 = GetMemberOfParty(party0, i1);
						if (!IsNullCreature(c0))
						{
							if (DistFromMe(c0) < 500)
							{
								CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(c0.getX() + 10), AiUtils.FloatToInt(c0.getY()), AiUtils.FloatToInt(c0.getZ()), 0, 0, 0, 0);
							}
							else if (AiUtils.Rand(100) < 50)
							{
								CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() - 10), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
							}
							else
							{
								CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() + 10), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
							}
						}
						else
						{
							CreateOnePrivateEx(silhouette2, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY() + 50), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
						}
					}
					else
					{
						CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() - 120), AiUtils.FloatToInt(actor.getY() + 130), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
					}
				}
				else
				{
					CreateOnePrivateEx(silhouette1, ai_type, 0, 0, AiUtils.FloatToInt(actor.getX() - 10), AiUtils.FloatToInt(actor.getY() + 150), AiUtils.FloatToInt(actor.getZ()), 0, 0, 0, 0);
				}
			}
		}
		else if (timer_id == 1102)
		{
			if (!InMyTerritory(actor))
			{
				RemoveAllDesire();
				InstantTeleport(actor, start_x, start_y, start_z);
			}
			AddTimerEx(1102, 30 * 1000);
		}
		else if (timer_id == 1102)
		{
			AiUtils.Area_SetOnOffEx(areadata_name + "00" + i_ai2 + "_0" + i0, true, 0);
		}
	}

	@Override
	protected void onEvtPartyDead(Creature attacker, NpcInstance privat)
	{
		Party party0 = null;
		int i1 = 0;
		Creature c0 = null;
		Creature c1 = null;
		String s0 = null;
		String s1 = null;
		final NpcInstance actor = getActor();

		if (privat != actor)
		{
			if (i_ai4 < 5)
			{
				AddTimerEx(1101, 5 * 1000 + (AiUtils.Rand(10) * 1000));
			}
			i_ai4 = i_ai4 + 1;
			if (i_ai4 >= 10)
			{
				if (i_ai2 < 10)
				{
					s1 = "00";
				}
				else
				{
					s1 = "0";
				}
				if (i_ai2 != 0)
				{
					s0 = areadata_name + s1 + i_ai2 + "_03";
					AiUtils.Area_SetOnOff(s0, true);
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
		String s0 = null;
		String s1 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		i_quest0 = 1;
		if (i_ai2 < 10)
		{
			s1 = "00";
		}
		else
		{
			s1 = "0";
		}
		if (i_ai2 != 0)
		{
			s0 = areadata_name + s1 + i_ai2 + "_0" + i_ai3;
			AiUtils.Area_SetOnOff(s0, false);
			s0 = areadata_name + s1 + i_ai2 + "_03";
			AiUtils.Area_SetOnOff(s0, false);
		}
		CreateOnePrivateEx(18482, "kind_water_dragon", 0, 0, AiUtils.FloatToInt(start_x), AiUtils.FloatToInt(start_y), AiUtils.FloatToInt(start_z), 0, i_ai2, 0, 0);
		if (AiUtils.Rand(100) <= 77)
		{
			DropItem1(actor, 9596, 1);
		}
	}

}