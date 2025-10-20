package org.mmocore.gameserver.scripts.ai.freya;

import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.instances.NpcInstance;

/**
 * @author iRock
 */

public class IceCastleBreath extends Fighter {
    public IceCastleBreath(NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void teleportHome() {}

    @Override
    protected boolean isGlobalAggro(){return true;}
}