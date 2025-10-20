package org.mmocore.gameserver.scripts.ai;

import org.mmocore.gameserver.ai.Mystic;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;

public class Latana extends Mystic {
    public Latana(NpcInstance actor) {
        super(actor);
        actor.startImmobilized();
    }

    @Override
    protected boolean checkTarget(Creature target, int range) {
        NpcInstance actor = getActor();
        if (target != null && !actor.isInRange(target, 900)) {
            actor.getAggroList().remove(target, true);
            return false;
        }
        return super.checkTarget(target, range);
    }

    @Override
    protected boolean randomWalk() {
        return false;
    }
}