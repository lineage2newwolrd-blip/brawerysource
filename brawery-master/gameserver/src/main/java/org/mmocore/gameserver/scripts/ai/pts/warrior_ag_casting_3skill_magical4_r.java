package org.mmocore.gameserver.scripts.ai.pts;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.utils.AiUtils;

public class warrior_ag_casting_3skill_magical4_r extends warrior_ag_casting_3skill_magical4
{
	public warrior_ag_casting_3skill_magical4_r(final NpcInstance actor){super(actor);}

	@Override
	protected void onEvtSpawn()
	{
		Creature c0 = null;
		final NpcInstance actor = getActor();

		if (actor.param1 == 1000)
		{
			c0 = AiUtils.GetCreatureFromIndex(actor.param2);
			if (!IsNullCreature(c0))
			{
				AddUseSkillDesire(c0, 305594369/*@skill_4663_1*/, DesireType.ATTACK, DesireMove.MOVE_TO_TARGET, 10000);
				AddAttackDesire(c0, DesireMove.MOVE_TO_TARGET, 500);
			}
		}
		super.onEvtSpawn();
		super.onEvtSpawn();
	}

}