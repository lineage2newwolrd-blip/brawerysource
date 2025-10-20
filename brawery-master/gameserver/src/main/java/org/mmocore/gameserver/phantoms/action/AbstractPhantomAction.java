package org.mmocore.gameserver.phantoms.action;

import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.phantoms.PhantomUtils;
import org.mmocore.gameserver.phantoms.model.Phantom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

/**
 * Created by Hack
 * Date: 23.08.2016 7:38
 */
public abstract class AbstractPhantomAction implements Runnable {
    protected static final Logger log = LoggerFactory.getLogger(AbstractPhantomAction.class);
    protected Phantom actor;
    protected long end_action;

    public abstract long getDelay();

    public abstract long getActionTime();

    public Future<?> schedule() {
        long start = PhantomUtils.getRndDelay(getDelay());
        end_action = System.currentTimeMillis() + start + PhantomUtils.getRndDelay(getActionTime());
        return ThreadPoolManager.getInstance().schedule(this, start);
    }

    public void setActor(Phantom phantom) {
        actor = phantom;
    }

    public boolean isDone() {
        return end_action < System.currentTimeMillis();
    }

    public boolean isSuperPoint() {
        return false;
    }
}
