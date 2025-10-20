package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_corpse_vampire_mobhate extends warrior_corpse_vampire
{
	public int DDMagic2 = 272039937;

	public warrior_corpse_vampire_mobhate(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		final NpcInstance actor = getActor();

		if ((GetLifeTime() > 7 && (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))) && p_state == State.ATTACK)
		{
			if (DistFromMe(attacker) < 100 && AiUtils.Rand(100) < 33)
			{
				if (Skill_GetConsumeMP(DDMagic2) < actor._currentMp && Skill_GetConsumeHP(DDMagic2) < actor.currentHp && Skill_InReuseDelay(DDMagic2) == 0)
				{
					AddUseSkillDesire(attacker, DDMagic2, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}