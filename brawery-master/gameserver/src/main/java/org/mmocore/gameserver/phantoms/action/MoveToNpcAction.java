package org.mmocore.gameserver.phantoms.action;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.configuration.config.clientCustoms.PhantomConfig;
import org.mmocore.gameserver.model.instances.NpcInstance;

import java.util.List;

/**
 * Created by Hack
 * Date: 23.08.2016 20:23
 */
public class MoveToNpcAction extends AbstractPhantomAction {
    @Override
    public long getDelay() {
        return 0;
    }

    @Override
    public long getActionTime() {
        return 15000;
    }
    @Override
    public void run() {
        List<NpcInstance> npcList = actor.getAroundNpc(PhantomConfig.moveToNpcRange, 300);
        if (npcList.size() == 0)
            return;
        NpcInstance npc = npcList.get(Rnd.get(npcList.size()));
        actor.moveToLocation(npc.getLoc(), (int)Math.max(30, actor.getMinDistance(npc)) + 20, true);
        actor.getMemory().setCanDelete(true);
    }
}
