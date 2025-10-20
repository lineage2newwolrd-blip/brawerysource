package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class wizard_corpse_necro extends wizard_ddmagic2
{
	public int DeBuff = 262209537;
	public int Cancel = 262209537;
	public int DDMagic = 262209537;

	public wizard_corpse_necro(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (!IsNullCreature(top_desire_target))
			{
				if (AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 90 && p_state != State.ATTACK)
				{
					if (Skill_GetConsumeMP(DeBuff) < actor._currentMp && Skill_GetConsumeHP(DeBuff) < actor.currentHp && Skill_InReuseDelay(DeBuff) == 0)
					{
						AddUseSkillDesire(attacker, DeBuff, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
					}
					c_ai0 = attacker;
				}
				if (top_desire_target == attacker)
				{
					if (AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) < 40)
					{
						if (Skill_GetConsumeMP(DDMagic) < actor._currentMp && Skill_GetConsumeHP(DDMagic) < actor.currentHp && Skill_InReuseDelay(DDMagic) == 0)
						{
							AddUseSkillDesire(attacker, DDMagic, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
						}
					}
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtDead(Creature attacker)
	{
		final NpcInstance actor = getActor();

		if (!IsNullCreature(c_ai0))
		{
			if (DistFromMe(c_ai0) < 100)
			{
				if (Skill_GetConsumeMP(Cancel) < actor._currentMp && Skill_GetConsumeHP(Cancel) < actor.currentHp && Skill_InReuseDelay(Cancel) == 0)
				{
					AddUseSkillDesire(c_ai0, Cancel, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
		}
	}

}