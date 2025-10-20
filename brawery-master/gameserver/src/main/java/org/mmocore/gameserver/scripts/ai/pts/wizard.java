package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard extends wizard_parameter
{

	public wizard(final NpcInstance actor)
	{
		super(actor);
		Attack_BoostValue = 300.000000f;
		Attack_DecayRatio = 6.600000f;
		UseSkill_BoostValue = 100000.000000f;
		UseSkill_DecayRatio = 66000.000000f;
	}

	@Override
	protected void onEvtNoDesire()
	{

		AddMoveAroundDesire(5, 5);
	}

	@Override
	protected void onEvtSpawn()
	{

		if (ShoutMsg1 > 0)
		{
			if (IsSay == 0)
			{
				Shout(AiUtils.MakeFString(ShoutMsg1, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(ShoutMsg1, "", "", "", "", ""));
			}
		}
		if ((MoveAroundSocial > 0 || ShoutMsg2 > 0 || ShoutMsg3 > 0))
		{
			AddTimerEx(1001, 10000);
		}
		AddTimerEx(1002, 10000);
		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (timer_id == 1001)
		{
			if ((p_state == State.MOVE_AROUND || p_state == State.DO_NOTHING || p_state == State.MOVE_TO_WAY_POINT))
			{
				if ((MoveAroundSocial > 0 || MoveAroundSocial1 > 0 || MoveAroundSocial2 > 0))
				{
					if (MoveAroundSocial2 > 0 && AiUtils.Rand(100) < 20)
					{
						AddEffectActionDesire(actor, SocialAction.VICTORY, MoveAroundSocial2 * 1000 / 30, 50);
					}
					else if (MoveAroundSocial1 > 0 && AiUtils.Rand(100) < 20)
					{
						AddEffectActionDesire(actor, SocialAction.GREETING, MoveAroundSocial1 * 1000 / 30, 50);
					}
					else if (MoveAroundSocial > 0 && AiUtils.Rand(100) < 20)
					{
						AddEffectActionDesire(actor, SocialAction.UNKNOW, MoveAroundSocial * 1000 / 30, 50);
					}
				}
				if (ShoutMsg2 > 0 && AiUtils.Rand(1000) < 17)
				{
					if (IsSay == 0)
					{
						Shout(AiUtils.MakeFString(ShoutMsg2, "", "", "", "", ""));
					}
					else
					{
						Say(AiUtils.MakeFString(ShoutMsg2, "", "", "", "", ""));
					}
				}
			}
			else if (p_state == State.ATTACK)
			{
				if (ShoutMsg3 > 0 && AiUtils.Rand(100) < 10)
				{
					if (IsSay == 0)
					{
						Shout(AiUtils.MakeFString(ShoutMsg3, "", "", "", "", ""));
					}
					else
					{
						Say(AiUtils.MakeFString(ShoutMsg3, "", "", "", "", ""));
					}
				}
			}
			AddTimerEx(1001, 10000);
		}
		if (timer_id == 1002)
		{
			AddTimerEx(1002, 10000);
			RemoveAllHateInfoIF(1, 0);
			RemoveAllHateInfoIF(3, 1000);
		}
		if (timer_id == 1003)
		{
			if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(69730305/*@skill_1064_1*/)) > 0 || AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(87556097/*@skill_1336_1*/)) > 0)
			{
				AddTimerEx(1003, 10000);
			}
			else
			{
				RemoveAllAttackDesire();
				i_ai0 = 0;
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
					MakeAttackEvent(h0.attacker, 100, 0);
				}
			}
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		float f0 = 0;
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
		if (AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(69730305/*@skill_1064_1*/)) > 0 || AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(87556097/*@skill_1336_1*/)) > 0)
		{
			i_ai0 = 1;
			AddTimerEx(1003, 10000);
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		float f0 = 0;
		final NpcInstance actor = getActor();

		if (GetLifeTime() > 7)
		{
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
					AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 30 + 300), 0, 1, 1);
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
					AddHateInfo(attacker, AiUtils.FloatToInt(f0 * 30), 0, 1, 1);
				}
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(int skill_name_id, Creature speller, Creature target)
	{
		float f0 = 0;
		int i0 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

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

	@Override
	protected void onEvtDesireManipulation(Creature speller, int desire)
	{

		MakeAttackEvent(speller, desire, 0);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{

		if (ShoutMsg4 > 0 && AiUtils.Rand(100) < 30)
		{
			if (IsSay == 0)
			{
				Shout(AiUtils.MakeFString(ShoutMsg4, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(ShoutMsg4, "", "", "", "", ""));
			}
		}
	}

}