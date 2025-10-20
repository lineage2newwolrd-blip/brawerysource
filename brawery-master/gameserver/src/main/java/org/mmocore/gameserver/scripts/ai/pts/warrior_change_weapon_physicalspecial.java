package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_change_weapon_physicalspecial extends warrior_physicalspecial
{
	public int EquipWeapon = 129;
	public int SelfBuff = 263979009;

	public warrior_change_weapon_physicalspecial(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		i0 = 50 + AiUtils.Rand(20);
		if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < i0 && i_ai0 == 0)
		{
			EquipItem(EquipWeapon);
			if (Skill_GetConsumeMP(SelfBuff) < actor._currentMp && Skill_GetConsumeHP(SelfBuff) < actor.currentHp && Skill_InReuseDelay(SelfBuff) == 0)
			{
				AddUseSkillDesire(actor, SelfBuff, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
			}
			i_ai0 = 1;
		}
		if (i_ai0 == 1)
		{
			UseSoulShot(SoulShot);
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}