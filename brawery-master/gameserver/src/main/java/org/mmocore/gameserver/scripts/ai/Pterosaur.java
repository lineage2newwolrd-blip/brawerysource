package org.mmocore.gameserver.scripts.ai;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ai.DefaultAI;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.utils.Location;

public class Pterosaur extends DefaultAI
{
	private static final Location[] points = {
			new Location(3964, -7496, -1500),
			new Location(7093, -6207, -1500),
			new Location(7838, -7407, -1500),
			new Location(7155, -9208, -1500),
			new Location(7667, -10459, -1500),
			new Location(9431, -11590, -1500),
			new Location(8241, -13708, -1500),
			new Location(8417, -15135, -1500),
			new Location(7604, -15878, -1500),
			new Location(7835, -18087, -1500),
			new Location(7880, -20446, -1500),
			new Location(6889, -21556, -1500),
			new Location(5506, -21796, -1500),
			new Location(5350, -20690, -1500),
			new Location(3718, -19280, -1500),
			new Location(2819, -17029, -1500),
			new Location(2394, -14635, -1500),
			new Location(3169, -13397, -1500),
			new Location(2596, -11971, -1500),
			new Location(2040, -9636, -1500),
			new Location(2910, -7033, -1500),
			new Location(5099, -6510, -1500),
			new Location(5895, -8563, -1500),
			new Location(3970, -9894, -1500),
			new Location(5994, -10320, -1500),
			new Location(6468, -11106, -1500),
			new Location(7273, -18036, -1500),
			new Location(5827, -20411, -1500),
			new Location(4708, -18472, -1500),
			new Location(4104, -15834, -1500),
			new Location(5770, -15281, -1500),
			new Location(7596, -19798, -1500),
			new Location(10069, -22629, -1500),
			new Location(10015, -23379, -1500),
			new Location(8079, -22995, -1500),
			new Location(5846, -23514, -1500),
			new Location(5683, -24093, -1500),
			new Location(4663, -24953, -1500),
			new Location(7631, -25726, -1500),
			new Location(9875, -27738, -1500),
			new Location(11293, -27864, -1500),
			new Location(11058, -25030, -1500),
			new Location(11074, -23164, -1500),
			new Location(10370, -22899, -1500),
			new Location(9788, -24086, -1500),
			new Location(11039, -24780, -1500),
			new Location(11341, -23669, -1500),
			new Location(8189, -20399, -1500),
			new Location(6438, -20501, -1500),
			new Location(4972, -17586, -1500),
			new Location(6393, -13759, -1500),
			new Location(8841, -13530, -1500),
			new Location(9567, -12500, -1500),
			new Location(9023, -11165, -1500),
			new Location(7626, -11191, -1500),
			new Location(7341, -12035, -1500),
			new Location(11039, -24780, -1500),
			new Location(8234, -13204, -1500),
			new Location(9316, -12869, -1500),
			new Location(6935, -7852, -1500) };

	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Pterosaur(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		NpcInstance actor = getActor();
		actor.setFlying(true);
		actor.setHasChatWindow(false);
		super.onEvtSpawn();
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		NpcInstance actor = getActor();
		if(actor.isDead())
			return true;

		if(_def_think)
		{
			if(doTask())
				clearTasks();
			return true;
		}

		long now = System.currentTimeMillis();
		if(now > wait_timeout && (current_point > -1 || Rnd.chance(5)))
		{
			if(!wait)
				switch(current_point)
				{
					case 0:
					case 8:
						wait_timeout = now + 10000;
						wait = false;
						return true;
				}

			wait_timeout = 0;
			wait = true;
			current_point++;

			if(current_point >= points.length)
				current_point = 0;

			addTaskMove(points[current_point], false);
			doTask();
			return true;
		}

		if(randomAnimation())
			return true;

		return false;
	}

	@Override
	protected void onEvtAttacked(Creature attacker, SkillEntry skill, int damage)
	{}

	@Override
	protected void onEvtAggression(Creature target, int aggro)
	{}
}