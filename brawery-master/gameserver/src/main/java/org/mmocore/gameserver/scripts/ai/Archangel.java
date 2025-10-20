package org.mmocore.gameserver.scripts.ai;

import org.mmocore.gameserver.ai.CtrlIntention;
import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.geoengine.GeoEngine;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.network.lineage.serverpackets.MagicSkillUse;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.ReflectionUtils;

/**
 * @author iRock
 * @version Freya
 */
public class Archangel extends Fighter {
    private final Zone _zone = ReflectionUtils.getZone("[baium_epic]");
    private long _new_target = System.currentTimeMillis() + 10000;

    public Archangel(NpcInstance actor) {
        super(actor);
    }

    @Override
    public boolean isGlobalAI() {
        return true;
    }

    @Override
    protected boolean thinkActive() {
        return super.thinkActive() || hitBaium();
    }

    protected boolean hitBaium()
    {
        NpcInstance actor = getActor();
        if (actor == null || actor.isDead())
            return false;

        if(_new_target < System.currentTimeMillis())
        {
            for(Creature target : actor.getAroundNpc(2000, 150))
            {
                if( !target.isDead())
                {
                    if(target.getNpcId() == 29020)
                    {
                        setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
                        actor.getAggroList().addDamageHate(target, 100, 10);
                        return true;
                    }
                }
            }
            _new_target = (System.currentTimeMillis() + 20000);
        }
        return false;

    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final SkillEntry skill, final int damage) {
        NpcInstance actor = getActor();
        if (actor != null && !actor.isDead()) {
            if (attacker != null) {
                if (attacker.getNpcId() == 29020) {
                    actor.getAggroList().addDamageHate(attacker, damage, 10);
                    setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
                }
            }
        }
        super.onEvtAttacked(attacker, skill, damage);
    }

    @Override
    protected boolean maybeMoveToHome() {
        NpcInstance actor = getActor();
        if (actor != null && !_zone.checkIfInZone(actor)) {
            returnHome();
        }
        return false;
    }

    @Override
    protected void returnHome() {
        NpcInstance actor = getActor();
        Location sloc = actor.getSpawnedLoc();
        clearTasks();
        actor.stopMove();
        actor.getAggroList().clear(true);
        setAttackTimeout(Long.MAX_VALUE);
        setAttackTarget(null);
        changeIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
        actor.broadcastPacketToOthers(new MagicSkillUse(actor, actor, 2036, 1, 500, 0));
        actor.teleToLocation(sloc.x, sloc.y, GeoEngine.getHeight(sloc, actor.getGeoIndex()));
    }
}