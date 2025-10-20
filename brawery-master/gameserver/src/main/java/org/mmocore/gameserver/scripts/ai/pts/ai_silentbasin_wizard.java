package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class ai_silentbasin_wizard extends wizard_ddmagic2_heal
{
	public int SkillStigma = 395378689;

	public ai_silentbasin_wizard(final NpcInstance actor)
	{
		super(actor);
		HealBonus = 0f;
		SpeedBonus = 0f;
	}

	@Override
	protected void onEvtSeeCreature(Creature creature)
	{
		int i0 = 0;
		int i1 = 0;

		if (AiUtils.GetAbnormalLevel(creature, Skill_GetAbnormalType(SkillStigma)) > 0)
		{
			AddAttackDesire(creature, DesireMove.MOVE_TO_TARGET, 2000);
			i_ai0 = 1;
		}
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		Creature creature = null;
		final NpcInstance actor = getActor();

		if (AiUtils.Rand(1000) < 2)
		{
			CreateOnePrivateEx(18695, "ai_silentbasin_trguard_b", 0, 0, AiUtils.FloatToInt(actor.getX()) + 100, AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()) + 100, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()));
			CreateOnePrivateEx(18694, "ai_silentbasin_trguard_a", 0, 0, AiUtils.FloatToInt(actor.getX()) - 100, AiUtils.FloatToInt(actor.getY()), AiUtils.FloatToInt(actor.getZ()) + 100, 0, 0, 0, 0);
			CreateOnePrivateEx(18694, "ai_silentbasin_trguard_a", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()) + 100, AiUtils.FloatToInt(actor.getZ()) + 100, 0, 0, 0, 0);
			CreateOnePrivateEx(18694, "ai_silentbasin_trguard_a", 0, 0, AiUtils.FloatToInt(actor.getX()), AiUtils.FloatToInt(actor.getY()) - 100, AiUtils.FloatToInt(actor.getZ()) + 100, 0, 0, 0, 0);
		}
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{

		if (i_ai0 == 1)
		{
			UseSpiritShot(20, SpeedBonus, HealBonus);
		}
		if (attacker.getSummonType() == 1 || attacker.getSummonType() == 2)
		{
			if (DistFromMe(attacker.getPlayer()) < 900)
			{
				AddAttackDesire(attacker.getPlayer(), DesireMove.MOVE_TO_TARGET, 2000);
			}
			else
			{
				TeleportTo(attacker, attacker.getPlayer());
			}
			return;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}