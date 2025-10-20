package org.mmocore.gameserver.scripts.ai.freya;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ai.CtrlEvent;
import org.mmocore.gameserver.ai.CtrlIntention;
import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.configuration.config.AiConfig;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.utils.Location;


/**
 * @author iRock
 */

public class IceKnightNormal extends Fighter {
    private boolean iced = false;
    private boolean _static = false;
    /**
     * The flag used to indicate that a thinking action is in progress
     */
    private boolean _thinking = false;
    /** The flag used to indicate that a thinking action is in progress */
    private static Location[] _points = new Location[]{
            new Location(113845, -113515, -11168, 57343),
            new Location(113380, -113984, -11176, 57343),
            new Location(113380, -115617, -11168, 8191),
            new Location(113848, -116082, -11168, 8191),
            new Location(115593, -116083, -11168, 24575),
            new Location(116059, -115615, -11168, 24575),
            new Location(116059, -113982, -11176, 40959),
            new Location(115593, -113516, -11168, 40959),
            new Location(112940, -114121, -10960, 0),
            new Location(112940, -114450, -10960, 0),
            new Location(112940, -115142, -10960, 0),
            new Location(112940, -115473, -10960, 0),
            new Location(116500, -115473, -10960, 32767),
            new Location(116500, -115140, -10960, 32767),
            new Location(116500, -114447, -10960, 32767),
            new Location(116500, -114118, -10960, 32767)

    };

    public IceKnightNormal(NpcInstance actor)
    {
        super(actor);
    }

    @Override
    protected void onEvtSpawn()
    {
        super.onEvtSpawn();
        NpcInstance actor = getActor();
        iced = true;
        actor.setNpcState(1);
        actor.block();
        Reflection r = actor.getReflection();
        if(r != null && actor.getSpawnedLoc().distance(Location.findNearest(actor, _points))<50)
            _static = true;

    }

    public synchronized boolean DropIce(){
        if(iced)
        {
            iced = false;
            NpcInstance actor = getActor();
            actor.setNpcState(2);
            actor.unblock();
            Reflection r = actor.getReflection();
            if(_static && r.getPlayers()!=null) {
                actor.setRunning();
                for (Player p : r.getPlayers())
                    actor.getAggroList().addDamageHate(p, 0, Rnd.get(200, 300));
            }

            return true;
        }

        return false;
    }

    @Override
    protected void teleportHome()
    {
    }

    @Override
    protected void onEvtThink()
    {
        NpcInstance actor = getActor();
        if(_thinking || actor == null || isActionsDisabled() || actor.isAfraid())
            return;
        if(iced && _static)
            return;

        if(_randomAnimationEnd > System.currentTimeMillis())
            return;

        _thinking = true;
        try {
            if (!AiConfig.BLOCK_ACTIVE_TASKS && getIntention() == CtrlIntention.AI_INTENTION_ACTIVE) {
                thinkActive();
            } else if (getIntention() == CtrlIntention.AI_INTENTION_ATTACK) {
                if (iced && !_static)
                    DropIce();
                if(actor.isBlocked())
                    return;
                thinkAttack();
            }
        } finally {
            _thinking = false;
        }
    }

    @Override
    public boolean isActionsDisabled() {
        final NpcInstance actor = getActor();
        return actor.isAlikeDead() || actor.isFlashed() || actor.isStunned() || actor.isSleeping() || actor.isParalyzed() || actor.isAttackingNow() || actor.isCastingNow() || actor.isFrozen();}

    @Override
    protected void onEvtAggression(Creature attacker, int aggro) {
        if(iced && !_static) {
            DropIce();
            for(NpcInstance npc : getActor().getAroundNpc(1400, 400))
                npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, 2);
        }
        super.onEvtAggression(attacker,aggro);
    }

    @Override
    protected void onEvtAttacked(Creature attacker, SkillEntry skill, int damage)
    {
        if(iced) {
            DropIce();
            if(!_static) {
                for(NpcInstance npc : getActor().getAroundNpc(1400, 400))
                    npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, 2);
            }
        }

        super.onEvtAttacked(attacker, skill, damage);
    }

    @Override
    public boolean isGlobalAI() { return true; }
}