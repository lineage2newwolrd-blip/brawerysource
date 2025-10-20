package org.mmocore.gameserver.phantoms.ai;

import org.mmocore.gameserver.ai.PlayerAI;
import org.mmocore.gameserver.phantoms.action.AbstractPhantomAction;
import org.mmocore.gameserver.phantoms.model.Phantom;

/**
 * Created by Hack
 * Date: 21.08.2016 21:13
 */
public abstract class AbstractPhantomAi extends PlayerAI {
    protected Phantom actor;
    protected AbstractPhantomAction currentAction;

    public AbstractPhantomAi(Phantom actor) {
        super(actor);
        this.actor = actor;
    }

    @Override
    public abstract void runImpl();

    public abstract PhantomAiType getType();

    @Override
    public Phantom getActor() {
        return (Phantom) super.getActor();
    }

    protected boolean isInAction(){
        return currentAction != null && !currentAction.isDone();
    }

    protected void action(AbstractPhantomAction action){
        currentAction = action;
        actor.doAction(action);
    }
}
