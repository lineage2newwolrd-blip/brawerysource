package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.jts.dataparser.data.holder.categorydata.Category;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_casting_cancel extends warrior
{
	public int CancelMagic = 268304385;
	public int CheckMagic = 458752001;
	public int CheckMagic1 = 458752001;
	public int CheckMagic2 = 458752001;

	public warrior_casting_cancel(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{

		i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, int skill_name_id, int skill_id, int damage)
	{
		int i6 = 0;
		top_desire_target = getTopHated();
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (AiUtils.Rand(10000) < 1 && i_ai0 == 0 && actor.currentHp > actor.getMaxHp() * 0.400000f)
			{
				if (!IsNullCreature(top_desire_target))
				{
					i_ai0 = 1;
				}
			}
		}
		super.onEvtAttacked(attacker, skill_name_id, skill_id, damage);
	}

	@Override
	protected void onEvtClanAttacked(Creature attacker, Creature victim, int damage, int skill_name_id, int skill_id)
	{
		int i6 = 0;
		final NpcInstance actor = getActor();

		if (attacker.isPlayer() || IsInCategory(Category.summon_npc_group, attacker.getClassId()))
		{
			if (AiUtils.Rand(10000) < 1 && i_ai0 == 0 && actor.currentHp > actor.getMaxHp() * 0.400000f)
			{
				i_ai0 = 1;
			}
		}
		super.onEvtClanAttacked(attacker, victim, damage, skill_name_id, skill_id);
	}

}