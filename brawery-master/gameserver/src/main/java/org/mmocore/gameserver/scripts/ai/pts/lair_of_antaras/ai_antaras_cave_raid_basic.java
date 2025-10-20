package org.mmocore.gameserver.scripts.ai.pts.lair_of_antaras;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ai.CtrlIntention;
import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.NpcUtils;
import org.mmocore.gameserver.utils.version.Chronicle;
import org.mmocore.gameserver.utils.version.ChronicleCheck;
import org.mmocore.gameserver.world.GameObjectsStorage;

/**
 * @author KilRoy & iRock
 */
@ChronicleCheck(Chronicle.HIGH_FIVE)
public class ai_antaras_cave_raid_basic extends Fighter {
    private static final int SPAWN_HOLD_MON = 1001;
    private static final int R_U_IN_BATTLE = 1004;
    private static final int SPAWN_HOLD_MON_TIME = 50;
    private static final int underling = 25728;
    private static final int underling1 = 25729;
    private int i_ai0;
    private int i_ai1;
    private int i_ai2;
    private static final SkillEntry selfheal_skill = SkillTable.getInstance().getSkillEntry(6886, 1);

    public ai_antaras_cave_raid_basic(NpcInstance actor) {
        super(actor);
        i_ai0 = 0;
        i_ai1 = 0;
    }

    @Override
    protected void onEvtTimerFiredEx(int timer_id, Object arg1, Object arg2) {
        if (getActor() == null || getActor().isDead()) {
            return;
        }

        if (timer_id == R_U_IN_BATTLE) {
            if (i_ai0 == 1) {
                NpcInstance actor = getActor();
                if(getIntention() == CtrlIntention.AI_INTENTION_ACTIVE){
                    i_ai0 = 0;
                    i_ai1 = 0;
                    actor.altOnMagicUseTimer(actor, selfheal_skill);
                    actor.setRunning();
                    final Location pos = Location.findPointToStay(actor, actor.getSpawnedLoc(), 0, 100);
                    if (!actor.moveToLocation(pos.x, pos.y, pos.z, 0, true)) {
                        teleportHome();
                    }
                    return;
                }
                AddTimerEx(R_U_IN_BATTLE, 30000L);
            }
        } else if (timer_id == SPAWN_HOLD_MON) {
            if (i_ai0 == 1) {
                AddTimerEx(SPAWN_HOLD_MON, ((SPAWN_HOLD_MON_TIME + Rnd.get(20)) * 1000));
                i_ai2 = 0;
                for (final NpcInstance npcs : GameObjectsStorage.getAllByNpcId(underling, true)) {
                    if (npcs.isInRange(getActor(), 4000)) {
                        i_ai2++;
                    } else {
                        npcs.deleteMe();
                    }
                }

                for (final NpcInstance npcs : GameObjectsStorage.getAllByNpcId(underling1, true)) {
                    if (npcs.isInRange(getActor(), 4000)) {
                        i_ai2++;
                    } else {
                        npcs.deleteMe();
                    }
                }
                if (i_ai2 <= 40) {
                    for (int i2 = 0; i2 < 5; i2++) {
                        if (getActor().getAggroList() != null && !getActor().getAggroList().isEmpty()) {
                            final Creature target = getActor().getAggroList().getRandomHated();
                            if (target != null) {
                                final NpcInstance npc = NpcUtils.spawnSingle(underling, Location.findAroundPosition(target, Rnd.get(100)), 1800000L);
                                npc.getAggroList().addDamageHate(target, 0, 1);
                                npc.setRunning();
                                npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
                            }
                        }
                    }
                    for (int i3 = 0; i3 < 3; i3++) {
                        if (getActor().getAggroList() != null && !getActor().getAggroList().isEmpty()) {
                            final Creature target = getActor().getAggroList().getRandomHated();
                            if (target != null) {
                                final NpcInstance npc = NpcUtils.spawnSingleStablePoint(underling1, Location.findAroundPosition(target, Rnd.get(50)), 1800000L);
                                npc.getAggroList().addDamageHate(target, 0, 1);
                                npc.setRunning();
                                npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onEvtSeeSpell(SkillEntry skill, Creature caster) {
        super.onEvtSeeSpell(skill, caster);

        NpcInstance actor = getActor();
        if (actor.isDead() || skill == null || caster == null) {
            return;
        }
        if(getIntention() == CtrlIntention.AI_INTENTION_ATTACK && (actor.getAggroList().get(caster) == null || actor.getAggroList().get(caster).hate == 0))
            actor.getAggroList().addDamageHate(caster, 0, 1);

    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final SkillEntry skill, final int damage) {
        if (i_ai0 == 0) {
            i_ai0 = 1;

            AddTimerEx(R_U_IN_BATTLE, 30000L);
            if (i_ai1 == 0)
            {
                i_ai1 = 1;
                AddTimerEx(SPAWN_HOLD_MON, ((SPAWN_HOLD_MON_TIME + Rnd.get(20)) * 1000));
            }
        }
        super.onEvtAttacked(attacker, skill, damage);
    }

    @Override
    public int getRateBUFF()
    {
        return _buffSkills.length == 0? 0 : _castReady > System.currentTimeMillis()? 1 : 0;
    }

    @Override
    protected boolean defaultThinkBuff(int rateSelf, int rateFriends)
    {
        return super.defaultThinkBuff(0,0);
    }

    @Override
    protected boolean isInSpawnAggro(Creature target) {
        return target.isInRangeZ(getActor().getSpawnedLoc(), 4000);
    }

    @Override
    protected boolean maybeMoveToHome(){
        if(super.maybeMoveToHome()) {
            getActor().setRunning();
            return true;
        }
        else return false;
    }
}