package org.mmocore.gameserver.phantoms.ai;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.configuration.config.clientCustoms.PhantomConfig;
import org.mmocore.gameserver.phantoms.action.MoveToNpcAction;
import org.mmocore.gameserver.phantoms.action.RandomMoveAction;
import org.mmocore.gameserver.phantoms.action.RandomSuperPoint;
import org.mmocore.gameserver.phantoms.action.RandomUserAction;
import org.mmocore.gameserver.phantoms.model.Phantom;

/**
 * Created by Hack
 * Date: 23.08.2016 6:32
 */
public class PhantomTownAi extends AbstractPhantomAi {
    public PhantomTownAi(Phantom actor) {
        super(actor);
    }
    private double rndmove = PhantomConfig.randomMoveChance;
    private double npcmove = PhantomConfig.moveToNpcChance;
    private double usraction = PhantomConfig.userActionChance;
    private double supermove = PhantomConfig.superpointChance;

    @Override
    public void runImpl() {
        if(isInAction()) {
            if(currentAction.isSuperPoint())
                currentAction.schedule();
            return;
        }
	    if(actor.getMemory().canDeleted()) {
		    if (Rnd.chance(PhantomConfig.finishDespawnChance)) {
			    actor.despawnPhantom();
			    return;
		    } else
			    actor.getMemory().setCanDelete(false);
	    }
        if (Rnd.chance(rndmove)) {
            rndmove = Math.min(80, rndmove * 3);
            npcmove = Math.min(80, npcmove * 2);
            supermove = Math.min(80, supermove * 3);
            usraction=PhantomConfig.userActionChance;
            action(new RandomMoveAction());
        } else if (Rnd.chance(npcmove)) {
            rndmove = PhantomConfig.randomMoveChance;
            npcmove = PhantomConfig.moveToNpcChance;
            supermove = Math.min(80, supermove * 3);
            usraction=PhantomConfig.userActionChance;
            action(new MoveToNpcAction());
        } else if (Rnd.chance(usraction)) {
            rndmove = PhantomConfig.randomMoveChance;
            npcmove = PhantomConfig.moveToNpcChance;
            supermove = PhantomConfig.superpointChance;
            usraction = Math.min(80, usraction * 3);
            action(new RandomUserAction());
        } else if (Rnd.chance(supermove)) {
            rndmove = PhantomConfig.randomMoveChance;
            npcmove = PhantomConfig.moveToNpcChance;
            supermove = PhantomConfig.superpointChance;
            usraction = PhantomConfig.userActionChance;
            action(new RandomSuperPoint());
        }
    }

    @Override
    protected void onEvtArrived() {
        if(isInAction()) {
            if(currentAction.isSuperPoint())
                currentAction.schedule();
        }
    }

    @Override
    public PhantomAiType getType() {
        return PhantomAiType.TOWN;
    }
}
