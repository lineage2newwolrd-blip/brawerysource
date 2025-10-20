package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.ai.ScriptEvent;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_growth_step2 extends wizard_growh_basic
{
	public int SelfBuff = 264110083;

	public wizard_growth_step2(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		final NpcInstance actor = getActor();

		i_ai4 = 1;
		c_ai0 = AiUtils.GetCreatureFromIndex(actor.param1);
		i_ai3 = actor.param2;
		if (IsNullCreature(c_ai0))
		{
			Say("수정. 주인이 없습니다");
		}
		else
		{
			AddHateInfo(c_ai0, 100, 0, 1, 1);
		}
		i_ai2 = 0;
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
			{
				AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		if (!IsNullCreature(c_ai0))
		{
			if (DistFromMe(c_ai0) > 100)
			{
				if (Skill_GetConsumeMP(W_LongRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_LongRangeDDMagic) < actor.currentHp)
				{
					if (Skill_InReuseDelay(W_LongRangeDDMagic) == 0)
					{
						AddUseSkillDesire(c_ai0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(c_ai0, W_LongRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
			else if (Skill_GetConsumeMP(W_ShortRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_ShortRangeDDMagic) < actor.currentHp)
			{
				if (Skill_InReuseDelay(W_ShortRangeDDMagic) == 0)
				{
					AddUseSkillDesire(c_ai0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				else
				{
					AddUseSkillDesire(c_ai0, W_ShortRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			else
			{
				i_ai0 = 1;
				AddAttackDesire(c_ai0, DesireMove.MOVE_TO_TARGET, 1000);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		float f0 = 0;
		int i1 = 0;
		final NpcInstance actor = getActor();

		i0 = 0;
		if (skill_name_id / 65536 == i_ai3)
		{
			if (TakeSocial != 0)
			{
				AddEffectActionDesire(actor, SocialAction.UNKNOW, MoveAroundSocial * 1000 / 30, 200);
			}
			i0 = 1;
		}
		if (i0 == 1)
		{
			if (AiUtils.Rand(100) < 5)
			{
				i0 = AiUtils.Rand(2018 - 2014 + 1);
				i0 = i0 + 2014;
				Say(AiUtils.MakeFString(i0, "", "", "", "", ""));
			}
			if (!IsNullCreature(c_ai0))
			{
				if (c_ai0 == attacker)
				{
					i_ai2 = i_ai2 + 1;
					i1 = 0;
					if ((i_ai4 == 1 && c_ai0 == attacker))
					{
						if (AiUtils.Rand(100) <= i_ai2 * 33)
						{
							AddTimerEx(2001, TakeSocial * 1000 / 30);
							i_ai4 = 3;
						}
					}
					else if (i_ai4 != 3)
					{
						i_ai4 = 2;
						i1 = 1;
					}
				}
			}
			if (i1 == 1)
			{
			}
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
		else
		{
			super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if ((timer_id == 2001 && (i_ai4 == 1 || i_ai4 == 3)) && actor.isAlive())
		{
			i0 = GetDirection(actor);
			if (AiUtils.Rand(100) < 50)
			{
				CreateOnePrivateEx(silhouette1, ai_type2, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), i0 * 182, AiUtils.GetIndexFromCreature(c_ai0), i_ai3, 0);
			}
			else
			{
				CreateOnePrivateEx(silhouette2, ai_type2, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), i0 * 182, AiUtils.GetIndexFromCreature(c_ai0), i_ai3, 0);
			}
			Despawn();
		}
	}

	@Override
	protected void onEvtScriptEvent(int script_event_arg1, int script_event_arg2, int script_event_arg3)
	{
		Creature c0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (script_event_arg1 == ScriptEvent.SCE_HERE_COMES_CRAZY_PET)
		{
			if (AiUtils.Rand(100) < 20)
			{
				i0 = GetDirection(actor);
				CreateOnePrivateEx(silhouette_ex2, ai_type_ex2, 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), i0 * 182, AiUtils.GetIndexFromCreature(c_ai0), i_ai3, 0);
				Despawn();
			}
		}
		super.onEvtScriptEvent(script_event_arg1, script_event_arg2, script_event_arg3);
	}

}