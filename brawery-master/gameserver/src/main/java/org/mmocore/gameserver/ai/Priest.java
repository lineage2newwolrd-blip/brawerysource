package org.mmocore.gameserver.ai;

import org.mmocore.commons.math.random.RndSelector;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.utils.Location;

import java.util.Arrays;

public class Priest extends DefaultAI {
    public Priest(NpcInstance actor)
    {
        super(actor);
    }

    @Override
    protected boolean thinkActive()
    {
        return super.thinkActive() || _castReady > System.currentTimeMillis() && defaultThinkBuff(5, 5);
    }

    @Override
    protected boolean createNewTask()
    {
        return defaultFightTask();
    }

    @Override
    protected void onEvtAttacked(Creature attacker, SkillEntry skill, int damage)
    {
        super.onEvtAttacked(attacker, skill, damage);
        NpcInstance actor = getActor();
        if(actor.isDead() || attacker == null)
            return;

        if(actor.isMoving)
            return;

        if(actor.isMinion() && Rnd.chance(20))
        {
            addTaskMove(Location.findAroundPosition(actor, 100, 200), false);
        }
    }

    @Override
    public int getRatePHYS()
    {
        return 0;
    }

    @Override
    public int getRateDOT()
    {
        return _dotSkills.length == 0 ? 0 : 15;
    }

    @Override
    public int getRateDEBUFF()
    {
        return _debuffSkills.length == 0 ? 0 : 40;
    }

    @Override
    public int getRateDAM()
    {
        return _damSkills.length == 0 ? 0 : 30;
    }

    @Override
    public int getRateSTUN()
    {
        return _stunSkills.length == 0 ? 0 : 15;
    }

    @Override
    public int getRateBUFF()
    {
        return 15;
    }

    @Override
    public int getRateHEAL()
    {
        return 40;
    }

    @Override
    protected boolean defaultFightTask()
    {
        clearTasks();

        NpcInstance actor = getActor();
        if(actor.isDead())
            return false;

        // TODO сделать лечение и баф дружественных целей
        if(defaultThinkBuff(30, 70))
            return true;

        Creature target;
        if((target = prepareTarget()) == null)
            return false;

        double distance = actor.getDistance(target);
        double targetHp = target.getCurrentHpPercents();
        double actorHp = actor.getCurrentHpPercents();

        final SkillEntry[] dam = Rnd.chance(getRateDAM()) ? selectUsableSkills(target, distance, _damSkills) : null;
        final SkillEntry[] dot = Rnd.chance(getRateDOT()) ? selectUsableSkills(target, distance, _dotSkills) : null;
        final SkillEntry[] debuff = targetHp > 10 ? Rnd.chance(getRateDEBUFF()) ? selectUsableSkills(target, distance, _debuffSkills) : null : null;
        final SkillEntry[] stun = Rnd.chance(getRateSTUN()) ? selectUsableSkills(target, distance, _stunSkills) : null;

        final RndSelector<SkillEntry[]> rndSelector = actor.isAMuted()
                ? RndSelector.of(
                RndSelector.RndNode.of(dam, getRateDAM()),
                RndSelector.RndNode.of(dot, getRateDOT()),
                RndSelector.RndNode.of(debuff, getRateDEBUFF()),
                RndSelector.RndNode.of(stun, getRateSTUN())
                )
                :
                RndSelector.of(
                        RndSelector.RndNode.of(null, getRatePHYS()),
                        RndSelector.RndNode.of(dam, getRateDAM()),
                        RndSelector.RndNode.of(dot, getRateDOT()),
                    RndSelector.RndNode.of(debuff, getRateDEBUFF()),
                    RndSelector.RndNode.of(stun, getRateSTUN())
                );

        final SkillEntry[] selected = rndSelector.select();
        if (selected != null) {
            if (Arrays.equals(selected, dam) || Arrays.equals(selected, dot)) {
                return chooseTaskAndTargets(selectTopSkillByDamage(actor, target, distance, selected), target, distance);
            }

            if (Arrays.equals(selected, debuff) || Arrays.equals(selected, stun)) {
                return chooseTaskAndTargets(selectTopSkillByDebuff(actor, target, distance, selected), target, distance);
            }
        }

        if(actor.isMinion()) {
            NpcInstance leader = actor.getLeader();
            if (leader != null) {
                distance = actor.getDistance(leader.getX(), leader.getY());
                if (distance > 2000) {
                    actor.teleToLocation(leader.getMinionPosition());
                    return true;
                }
                else if (distance > 200) {
                    if (!actor.isRunning() && !leader.getAggroList().isEmpty())
                        actor.setRunning();
                    addTaskMove(leader.getMinionPosition(), false);
                    return true;
                }
                else if (actor.isRunning() && leader.getAggroList().isEmpty()) {
                    actor.setWalking();
                }
            }
            return false;
        }
        return chooseTaskAndTargets(null, target, distance);
    }
}