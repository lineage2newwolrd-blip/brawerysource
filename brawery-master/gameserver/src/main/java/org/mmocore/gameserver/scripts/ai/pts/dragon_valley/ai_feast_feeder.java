package org.mmocore.gameserver.scripts.ai.pts.dragon_valley;

import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.utils.version.Chronicle;
import org.mmocore.gameserver.utils.version.ChronicleCheck;

/**
 * @author iRock
 */
@ChronicleCheck(Chronicle.HIGH_FIVE)
public class ai_feast_feeder extends Fighter {

    private static final int suicideTimer = 2010508;
    private static final int lifeTimer = 2010509;

    public ai_feast_feeder(NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtSpawn()
    {
        AddTimerEx(lifeTimer, 1000);
        super.onEvtSpawn();
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final SkillEntry skill, final int damage)
    {
        if (attacker.getNpcId() == 25722)
        {
            attacker.setCurrentHp(attacker.getCurrentHp() + 30000, false);
            AddTimerEx(suicideTimer, 1000);
        }
        super.onEvtAttacked(attacker, skill, damage);
    }

    @Override
    protected void onEvtTimerFiredEx(int timer_id, Object arg1, Object arg2)
    {
        final NpcInstance actor = getActor();

        if (timer_id == lifeTimer)
        {
            for(NpcInstance npc : actor.getAroundNpc(2000, 200))
                if(npc.getNpcId() == 25722 && !npc.isDead()) {
                    AddTimerEx(lifeTimer, 5 * 1000);
                    return;
                }
            actor.deleteMe();
        }
        if (timer_id == suicideTimer)
        {
            actor.doDie(null);
        }
    }
}