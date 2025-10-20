package org.mmocore.gameserver.phantoms.action;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.clientCustoms.PhantomConfig;
import org.mmocore.gameserver.manager.ReflectionManager;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoinCoordinate;
import org.mmocore.gameserver.phantoms.PhantomUtils;
import org.mmocore.gameserver.utils.Location;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Hack
 * Date: 23.08.2016 18:39
 */
public class RandomSuperPoint extends AbstractPhantomAction {

    // superpoint
    private int currentPointCounter = 0;
    private long superpointTimeout = 0;

    @Override
    public long getDelay() {
        return 0;
    }

    @Override
    public long getActionTime() {
        return 20000;
    }

    @Override
    public boolean isSuperPoint() {
        return true;
    }

    @Override
    public boolean isDone() {
        return actor.getMemory().canDeleted() && super.isDone();
    }

    @Override
    public Future<?> schedule() {
        return ThreadPoolManager.getInstance().schedule(this, 0);
    }

    @Override
    public void run() {
        if(actor.getSuperPoint() == null)
            actor.getMemory().setCanDelete(true);

        if (!actor.isBlocked() && !actor.isAttackingNow() && !actor.isCastingNow() && !actor.isMoving()) {
            if(superpointTimeout > System.currentTimeMillis()) {
                return;
            } else {
                superpointTimeout = 0;
            }
            final List<SuperPoinCoordinate> coords = actor.getSuperPoint().getCoordinats();
            if (currentPointCounter > coords.size() - 1)
                return;
            final SuperPoinCoordinate currentPointCoordinate = coords.get(currentPointCounter);
            if (currentPointCoordinate.getDelayInSec() > 0) {
                superpointTimeout = System.currentTimeMillis() + (PhantomUtils.getRndDelay(currentPointCoordinate.getDelayInSec()* 1000));
            }

            Location loc = Location.findPointToStay(currentPointCoordinate, 100, ReflectionManager.DEFAULT.getGeoIndex());
            actor.moveToLocation(loc, 0, true);

            currentPointCounter++;
            if (currentPointCounter > coords.size() - 1) {
                actor.getMemory().setCanDelete(true);
                end_action = superpointTimeout;
            }
        }
    }
}
