package org.mmocore.gameserver.scripts.ai;

import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.MagicSkillUse;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.utils.Location;

import java.util.concurrent.ScheduledFuture;

public class Lematan extends Fighter
{
	private int phase = 0;
	private long _skillReuseTimer = 0;
	private ScheduledFuture _spawnTask;

	public Lematan(NpcInstance actor)
	{
		super(actor);
	}

	@Override
	protected void thinkAttack()
	{
		if(phase == 0 && _actor.getCurrentHpPercents() <= 50)
		{
			phase = 1;
			getActor().getAggroList().clear();
			addTaskMove(new Location(86116, -209117, -3774), false);
			ThreadPoolManager.getInstance().schedule(new Runnable()
			{
				@Override
				public void run()
				{
					if(phase == 1)
					{
						phase = 2;
						_actor.broadcastPacket(new MagicSkillUse(_actor, 4671, 1, 500, 0));
						_actor.teleToLocation(new Location(85000, -208699, -3336));
						getActor().setSpawnedLoc(new Location(85000, -208699, -3336));
						_spawnTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
							@Override
							public void run() {
								if(phase == 2) {
									phase = 3;
									_actor.getReflection().spawnByGroup("lematan_privates", true);
								}
							}
						}, 15000L);
					}
				}
			}, 8000L);
		}
		else if(phase == 3)
		{
			if(_skillReuseTimer + 15000 < System.currentTimeMillis())
			{
				_skillReuseTimer = System.currentTimeMillis();
				for(NpcInstance n : _actor.getAroundNpc(1000, 200))
					if(n.getNpcId() == 18634)
					{
						n.doCast(SkillTable.getInstance().getSkillEntry(5712, 1), _actor, false);
					}
			}
		}
		super.thinkAttack();
	}

	@Override
	protected void onEvtAttacked(Creature attacker, SkillEntry skill, int damage)
	{
		if(phase == 1)
			return;
		super.onEvtAttacked(attacker, skill, damage);
	}

	@Override
	protected void onEvtDead(Creature killer)
	{
		if(_spawnTask!=null)
			_spawnTask.cancel(false);
		if(phase == 3) {
			_actor.getReflection().despawnByGroup("lematan_privates");
		}
		_actor.getReflection().addSpawnWithoutRespawn(32511, new Location(84983, -208736, -3328, 49915), 0);
		super.onEvtDead(killer);
	}


}