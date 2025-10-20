package org.mmocore.gameserver.scripts.ai.kamaloka;

import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.SimpleSpawner;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

import static org.mmocore.gameserver.utils.Location.coordsRandomize;

/**
 * @author iRock
 */
public class LabyrinthFakeWatcher extends Fighter
{
	NpcInstance actor = getActor();
	Reflection r = actor.getReflection();

	public LabyrinthFakeWatcher(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
        if(r!=null) {
            SimpleSpawner spawner = (SimpleSpawner) actor.getSpawn();
            spawner.setLoc(coordsRandomize(spawner.getLoc(), 50, 120));
        }
		super.onEvtDead(killer);

	}
}