package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_physicalspecial_potion extends warrior_physicalspecial
{
	public int EffectSkill = 458752001;
	public int MagicHeal = 458752001;

	public warrior_physicalspecial_potion(final NpcInstance actor)
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
		final NpcInstance actor = getActor();

		if (actor.currentHp < actor.getMaxHp() * 0.800000f && i_ai1 != 3 && AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai1 = 1;
		}
		else if (actor.currentHp < actor.getMaxHp() / 2 && i_ai1 != 3 && AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai1 = 2;
		}
		else if (actor.currentHp < actor.getMaxHp() / 3 && i_ai1 != 3 && AiUtils.Rand(100) < 33)
		{
			if (Skill_GetConsumeMP(MagicHeal) < actor._currentMp && Skill_GetConsumeHP(MagicHeal) < actor.currentHp && Skill_InReuseDelay(MagicHeal) == 0)
			{
				AddUseSkillDesire(actor, MagicHeal, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai1 = 3;
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
	protected void onEvtUseSkillFinished(int skill_name_id, Creature target, boolean success)
	{
		int i0 = 0;

		i0 = AiUtils.Rand(100);
		if (skill_name_id == MagicHeal)
		{
			if (i0 < 30)
			{
				Say(AiUtils.MakeFString(10071, "", "", "", "", ""));
			}
			else if (i0 < 60)
			{
				Say(AiUtils.MakeFString(10072, "", "", "", "", ""));
			}
			else
			{
				Say(AiUtils.MakeFString(10073, "", "", "", "", ""));
			}
		}
	}

}