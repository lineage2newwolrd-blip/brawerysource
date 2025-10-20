package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_enchant_1of4 extends warrior
{
	public int SelfBuff1 = 263979009;
	public int SelfBuff2 = 263979009;
	public int SelfBuff3 = 263979009;
	public int SelfBuff4 = 263979009;

	public warrior_casting_enchant_1of4(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = 0;
		final NpcInstance actor = getActor();

		i0 = AiUtils.Rand(4);
		if (i0 == 0)
		{
			i_ai0 = SelfBuff1;
		}
		else if (i0 == 1)
		{
			i_ai0 = SelfBuff2;
		}
		else if (i0 == 2)
		{
			i_ai0 = SelfBuff3;
		}
		else
		{
			i_ai0 = SelfBuff4;
		}
		i_ai1 = 0;
		if (Skill_GetConsumeMP(i_ai0) < actor._currentMp && Skill_GetConsumeHP(i_ai0) < actor.currentHp && Skill_InReuseDelay(i_ai0) == 0)
		{
			AddUseSkillDesire(actor, i_ai0, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (i_ai1 == 0 && AiUtils.Rand(100) < 33 && AiUtils.FloatToInt(actor.currentHp / actor.getMaxHp() * 100) > 50)
			{
				if (Skill_GetConsumeMP(i_ai0) < actor._currentMp && Skill_GetConsumeHP(i_ai0) < actor.currentHp && Skill_InReuseDelay(i_ai0) == 0)
				{
					AddUseSkillDesire(actor, i_ai0, DesireType.HEAL, DesireMove.MOVE_TO_TARGET, 1000000);
				}
			}
			i_ai1 = 1;
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

}