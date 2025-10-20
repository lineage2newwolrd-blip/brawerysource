package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.ai.SkillInfo;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_tantaar_lizard_archer extends warrior_passive_use_bow
{
	public int Max_Desire = 10000000;
	@SkillInfo(id = 7000, level = 1)
	public int Skill01_ID;
	@SkillInfo(id = 7000, level = 1)
	public int Skill02_ID;
	public int TID_SKILL_COOLTIME = 780001;
	public int TIME_SKILL_COOLTIME = 2;
	public int babble_mode = 0;

	public ai_tantaar_lizard_archer(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		AddTimerEx(TID_SKILL_COOLTIME, TIME_SKILL_COOLTIME * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		if (!IsNullCreature(attacker))
		{
			c_ai0 = attacker;
		}
	}

	@Override
	protected void onEvtSpelled(int skill_name_id, Creature speller)
	{
		final NpcInstance actor = getActor();

		super.onEvtSpelled(skill_name_id, speller);
		if (skill_name_id == 421199873)
		{
			if (babble_mode !=0)
			{
				Say("s_lizard_grasslands_fungus1 hit");
			}
			AddUseSkillDesire(actor, 433979393/*@skill_6622_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, Max_Desire);
		}
		if (babble_mode !=0)
		{
			Say("SPELLED:" + (skill_name_id / (256 * 256)));
		}
	}

	@Override
	protected void onEvtTimerFiredEx(int timer_id)
	{

		if (timer_id == TID_SKILL_COOLTIME)
		{
			if (!IsNullCreature(c_ai0))
			{
				if (AiUtils.GetAbnormalLevel(c_ai0, Skill_GetAbnormalType(6619137/*@skill_101_1*/)) == 0 && AiUtils.Rand(100) >= 85)
				{
					AddUseSkillDesire(c_ai0, Skill01_ID, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, Max_Desire);
				}
				else
				{
					AddUseSkillDesire(c_ai0, Skill02_ID, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, Max_Desire);
				}
			}
			AddTimerEx(TID_SKILL_COOLTIME, TIME_SKILL_COOLTIME * 1000);
		}
		super.onEvtTimerFiredEx(timer_id);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		final NpcInstance actor = getActor();

		super.onEvtDead(attacker);
		if (!IsNullCreature(c_ai0))
		{
			CreateOnePrivateEx(18919, "ai_auragrafter", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, c_ai0.getObjectId(), 0, 0);
		}
		if (AiUtils.Rand(1000) == 0 && actor.getClassId() != 1018862)
		{
			CreateOnePrivateEx(18862, "tantaar_lizard_protecter", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()), 0, c_ai0.getObjectId(), 0, 0);
		}
	}

}