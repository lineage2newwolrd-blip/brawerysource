package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_corpse_vampire_curse extends wizard_corpse_vampire_basic
{
	public int DeBuff1 = 262209537;
	public int Cancel = 262209537;

	public wizard_corpse_vampire_curse(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai4 == 0)
			{
				if (Skill_GetConsumeMP(DeBuff1) < actor._currentMp && Skill_GetConsumeHP(DeBuff1) < actor.currentHp && Skill_InReuseDelay(DeBuff1) == 0)
				{
					AddUseSkillDesire(attacker, DeBuff1, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
				i_ai4 = 1;
				c_ai0 = attacker;
			}
			if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 10 && i_ai4 == 1)
			{
				if (!IsNullCreature(c_ai0))
				{
					if (DistFromMe(c_ai0) < 100)
					{
						if (Skill_GetConsumeMP(Cancel) < actor._currentMp && Skill_GetConsumeHP(Cancel) < actor.currentHp && Skill_InReuseDelay(Cancel) == 0)
						{
							AddUseSkillDesire(c_ai0, Cancel, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
						i_ai4 = 2;
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}