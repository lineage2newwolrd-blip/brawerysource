package org.mmocore.gameserver.scripts.ai.pts.dragon_valley;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.utils.version.Chronicle;
import org.mmocore.gameserver.utils.version.ChronicleCheck;

/**
 * @author KilRoy & iRock
 */
@ChronicleCheck(Chronicle.HIGH_FIVE)
public class ai_blackdagger_wing extends detect_party_wizard {

    private static final int DAMAGE_TIMER = 2010505;
    private static final SkillEntry RANGE_DD_SKILL = SkillTable.getInstance().getSkillEntry(6834, 1);
    private static final int POWER_SKILL =6833;
    private int i_ai2;

    public ai_blackdagger_wing(NpcInstance actor) {
        super(actor);
        i_ai2 = 0;
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final SkillEntry skill, final int damage) {
        final NpcInstance actor = getActor();
        if (actor == null || actor.isDead()) {
            return;
        }
        if (actor.currentHp < actor.getMaxHp() * 0.500000f && i_ai2 == 0)
        {
            i_ai2 = 1;
            AddTimerEx(DAMAGE_TIMER, 10000);
        }
        super.onEvtAttacked(attacker, skill, damage);
    }

    @Override
    protected void onEvtFinishCasting(SkillEntry skill) {
        if (skill.getId() == POWER_SKILL)
        {
            Creature target = getActor().getAggroList().getMostHated();
            if (target != null && !target.isAlikeDead() && getActor().isInRange(target, RANGE_DD_SKILL.getTemplate().getAOECastRange()))
            {
                getActor().doCast(RANGE_DD_SKILL, target, false);
            }
        }
    }

    @Override
    protected void onEvtTimerFiredEx(int timer_id, Object arg1, Object arg2)
    {
        final NpcInstance actor = getActor();
        if (actor == null || actor.isDead()) {
            return;
        }

        if (timer_id == DAMAGE_TIMER)
        {
            Creature target = getActor().getAggroList().getMostHated();
            if (target != null && !target.isAlikeDead() && actor.isInRange(target, 500))
            {
                getActor().doCast(RANGE_DD_SKILL, target, false);
            }
            AddTimerEx(DAMAGE_TIMER, 30 * 1000);
        }
        super.onEvtTimerFiredEx(timer_id, arg1, arg2);
    }

    @Override
    public int getRatePHYS()
    {
        return 0;
    }

    // Should be Mystic type
    @Override
    public int getRateDEBUFF()
    {
        return 5;
    }

    @Override
    public int getRateDAM()
    {
        return 80;
    }
}