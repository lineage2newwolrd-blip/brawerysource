package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_physicalspecial_spoil extends warrior_physicalspecial
{
	public int EffectSkill = 458752001;

	public warrior_physicalspecial_spoil(final NpcInstance actor)
	{
		super(actor);
		PhysicalSpecial = 458752001;
	}

	@Override
	protected void onEvtSpawn()
	{

		i_ai1 = 0;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		if (i_ai1 == 0)
		{
			if (Skill_GetConsumeMP(EffectSkill) < actor._currentMp && Skill_GetConsumeHP(EffectSkill) < actor.currentHp && Skill_InReuseDelay(EffectSkill) == 0)
			{
				AddUseSkillDesire(attacker, EffectSkill, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai1 = 1;
		}
		if (AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(PhysicalSpecial) < actor._currentMp && Skill_GetConsumeHP(PhysicalSpecial) < actor.currentHp && Skill_InReuseDelay(PhysicalSpecial) == 0)
			{
				AddUseSkillDesire(attacker, PhysicalSpecial, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtAtkFinished(Creature target)
	{

		if (!target.isAlive() && target.isPlayer())
		{
			Say(AiUtils.MakeFString(1010584, "", "", "", "", ""));
		}
	}

	@Override
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;

		if ((skill_name_id == EffectSkill && success))
		{
			i0 = AiUtils.Rand(100);
			if (i0 < 30)
			{
				Say(AiUtils.MakeFString(10068, "", "", "", "", ""));
			}
			else if (i0 < 60)
			{
				Say(AiUtils.MakeFString(10069, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(10070, "", "", "", "", ""));
			}
		}
	}

}