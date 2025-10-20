package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.model.team.Party;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_corpse_vampire_range extends wizard_corpse_vampire_basic
{
	public int W_SelfRangeDDMagic = 274071553;

	public wizard_corpse_vampire_range(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		Party party0 = null;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				party0 = AiUtils.GetParty(attacker);
				if (!IsNullParty(party0))
				{
					if (AiUtils.Rand(100) < 33)
					{
						if (Skill_GetConsumeMP(W_SelfRangeDDMagic) < actor._currentMp && Skill_GetConsumeHP(W_SelfRangeDDMagic) < actor.currentHp)
						{
							if (Skill_InReuseDelay(W_SelfRangeDDMagic) == 0)
							{
								AddUseSkillDesire(attacker, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
							else
							{
								AddUseSkillDesire(attacker, W_SelfRangeDDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
							}
						}
						else
						{
							i_ai0 = 1;
							AddAttackDesire(attacker, DesireMove.MOVE_TO_TARGET, 1000);
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}