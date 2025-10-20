package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;
import org.mmocore.gameserver.ai.NpcAI;

public class default_npc extends NpcAI
{
	public int DesirePqSize = 50;
	public int FavorListSize = 30;
	public float IdleDesire_DecayRatio = 0.000000f;
	public float MoveAround_DecayRatio = 0.000000f;
	public float DoNothing_DecayRatio = 0.000000f;
	public float Attack_DecayRatio = 0.000000f;
	public float Chase_DecayRatio = 0.000000f;
	public float Flee_DecayRatio = 0.000000f;
	public float GetItem_DecayRatio = 0.000000f;
	public float Follow_DecayRatio = 0.000000f;
	public float Decaying_DecayRatio = 0.000000f;
	public float MoveToWayPoint_DecayRatio = 0.000000f;
	public float UseSkill_DecayRatio = 0.000000f;
	public float MoveTo_DecayRatio = 0.000000f;
	public float EffectAction_DecayRatio = 0.000000f;
	public float MoveToTarget_DecayRatio = 0.000000f;
	public float IdleDesire_BoostValue = 0.000000f;
	public float MoveAround_BoostValue = 0.000000f;
	public float DoNothing_BoostValue = 0.000000f;
	public float Attack_BoostValue = 0.000000f;
	public float Chase_BoostValue = 0.000000f;
	public float Flee_BoostValue = 0.000000f;
	public float GetItem_BoostValue = 0.000000f;
	public float Follow_BoostValue = 0.000000f;
	public float Decaying_BoostValue = 0.000000f;
	public float MoveToWayPoint_BoostValue = 0.000000f;
	public float UseSkill_BoostValue = 0.000000f;
	public float MoveTo_BoostValue = 0.000000f;
	public float EffectAction_BoostValue = 0.000000f;
	public float MoveToTarget_BoostValue = 0.000000f;
	public int Dispel_Debuff = 0;
	public int Dispel_Debuff_Prob = 0;

	public default_npc(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtNoDesire()
	{

	}

	@Override
	protected void onEvtTalkSelected(Player talker)
	{

		ShowPage(talker, "noquest.htm");
	}

	/*
	@Override
	protected void DEBUG_AI()
	{
		Creature creature = null;
		int reply = 0;

		if (reply == 101)
		{
			Whisper(creature, "** Direction : " + (GetDirectionToTarget(creature) * 182) + "  ");
		}
	}

	*/
	@Override
	protected void onEvtAbnormalChanged(Creature speller, int skill_name_id, int skill_id, int skill_level)
	{
		String s0 = null;
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (Dispel_Debuff == 1)
		{
			if (skill_level > 0)
			{
				if (Skill_GetAbnormalType(6029313/*@skill_92_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(6029313/*@skill_92_1*/)) > 0)
				{
					Dispel(actor, Skill_GetAbnormalType(6029313/*@skill_92_1*/));
				}
				else if (Skill_GetAbnormalType(91357185/*@skill_1394_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(91357185/*@skill_1394_1*/)) > 0)
				{
					Dispel(actor, Skill_GetAbnormalType(91357185/*@skill_1394_1*/));
				}
				else if (Skill_GetAbnormalType(18284545/*@skill_279_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(18284545/*@skill_279_1*/)) > 0)
				{
					Dispel(actor, Skill_GetAbnormalType(18284545/*@skill_279_1*/));
				}
				else if (Skill_GetAbnormalType(24051713/*@skill_367_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(24051713/*@skill_367_1*/)) > 0)
				{
					Dispel(actor, Skill_GetAbnormalType(24051713/*@skill_367_1*/));
				}
				else if (Skill_GetAbnormalType(76611585/*@skill_1169_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(76611585/*@skill_1169_1*/)) > 0)
				{
					Dispel(actor, Skill_GetAbnormalType(76611585/*@skill_1169_1*/));
				}
				else if (Skill_GetAbnormalType(78708737/*@skill_1201_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(78708737/*@skill_1201_1*/)) > 0)
				{
					Dispel(actor, Skill_GetAbnormalType(78708737/*@skill_1201_1*/));
				}
				else if (Skill_GetAbnormalType(26411009/*@skill_403_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(26411009/*@skill_403_1*/)) > 0)
				{
					Dispel(actor, Skill_GetAbnormalType(26411009/*@skill_403_1*/));
				}
			}
		}
		else if (Dispel_Debuff == 2)
		{
			if (skill_level > 0)
			{
				if (Skill_GetAbnormalType(6029313/*@skill_92_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(6029313/*@skill_92_1*/)) > 0)
				{
					if (AiUtils.Rand(10000) < Dispel_Debuff_Prob)
					{
						Dispel(actor, Skill_GetAbnormalType(6029313/*@skill_92_1*/));
					}
				}
				else if (Skill_GetAbnormalType(91357185/*@skill_1394_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(91357185/*@skill_1394_1*/)) > 0)
				{
					if (AiUtils.Rand(10000) < Dispel_Debuff_Prob)
					{
						Dispel(actor, Skill_GetAbnormalType(91357185/*@skill_1394_1*/));
					}
				}
				else if (Skill_GetAbnormalType(18284545/*@skill_279_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(18284545/*@skill_279_1*/)) > 0)
				{
					if (AiUtils.Rand(10000) < Dispel_Debuff_Prob)
					{
						Dispel(actor, Skill_GetAbnormalType(18284545/*@skill_279_1*/));
					}
				}
				else if (Skill_GetAbnormalType(24051713/*@skill_367_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(24051713/*@skill_367_1*/)) > 0)
				{
					if (AiUtils.Rand(10000) < Dispel_Debuff_Prob)
					{
						Dispel(actor, Skill_GetAbnormalType(24051713/*@skill_367_1*/));
					}
				}
				else if (Skill_GetAbnormalType(76611585/*@skill_1169_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(76611585/*@skill_1169_1*/)) > 0)
				{
					if (AiUtils.Rand(10000) < Dispel_Debuff_Prob)
					{
						Dispel(actor, Skill_GetAbnormalType(76611585/*@skill_1169_1*/));
					}
				}
				else if (Skill_GetAbnormalType(78708737/*@skill_1201_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(78708737/*@skill_1201_1*/)) > 0)
				{
					if (AiUtils.Rand(10000) < Dispel_Debuff_Prob)
					{
						Dispel(actor, Skill_GetAbnormalType(78708737/*@skill_1201_1*/));
					}
				}
				else if (Skill_GetAbnormalType(26411009/*@skill_403_1*/) == Skill_GetAbnormalType(skill_name_id) && AiUtils.GetAbnormalLevel(actor, Skill_GetAbnormalType(26411009/*@skill_403_1*/)) > 0)
				{
					if (AiUtils.Rand(10000) < Dispel_Debuff_Prob)
					{
						Dispel(actor, Skill_GetAbnormalType(26411009/*@skill_403_1*/));
					}
				}
			}
		}
	}

}