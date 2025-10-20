package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_ddmagic2_dispell extends wizard_ddmagic2
{
	public int Dispell = 262209537;

	public wizard_ddmagic2_dispell(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
		h0 = GetMaxHateInfo(0);
		if ((attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId())) && i_ai0 == 0)
		{
			i0 = 0;
			if (!IsNullHateInfo(h0))
			{
				if (!IsNullCreature(h0.attacker))
				{
					i0 = 1;
				}
			}
			if (AiUtils.Rand(100) < 33 && i0 == 1)
			{
				if (Skill_GetConsumeMP(Dispell) < actor._currentMp && Skill_GetConsumeHP(Dispell) < actor.currentHp)
				{
					if (Skill_InReuseDelay(Dispell) == 0)
					{
						AddUseSkillDesire(attacker, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					else
					{
						AddUseSkillDesire(attacker, Dispell, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
				}
				else
				{
					i_ai0 = 1;
					AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}