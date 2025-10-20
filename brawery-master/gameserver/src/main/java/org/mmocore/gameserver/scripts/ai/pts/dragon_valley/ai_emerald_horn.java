package org.mmocore.gameserver.scripts.ai.pts.dragon_valley;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.utils.version.Chronicle;
import org.mmocore.gameserver.utils.version.ChronicleCheck;

/**
 * @author KilRoy & iRock
 * TODO[K] - хз как у нас работают рефлект скилы, но данный моб, кидает их на себя и аттакера, болванку сделаю, остальное на ТОДО
 * Скилл рефлекта кладет на себя с шансом в 5%, при наложении рефлекта, включается таймер(15000мс) на восстановление i_ai3 в 0, и так по кругу.
 */
@ChronicleCheck(Chronicle.HIGH_FIVE)
public class ai_emerald_horn extends detect_party_wizard {
    private int i_ai2;
    private int i_ai3;
    private int i_ai5;
    public int damageTimer_15 = 20100504;
    public final static SkillEntry reflectiveSkill = SkillTable.getInstance().getSkillEntry(6823, 1);
    public final static SkillEntry reflectiveAttackLv1 = SkillTable.getInstance().getSkillEntry(6824, 1);
    public final static SkillEntry reflectiveAttackLv2 = SkillTable.getInstance().getSkillEntry(6825, 1);
    public final static SkillEntry reflectiveAttackLv3 = SkillTable.getInstance().getSkillEntry(6825, 2);

    public ai_emerald_horn(NpcInstance actor) {
        super(actor);
        i_ai2 = 0;
        i_ai3 = 0;
        i_ai5 = 0;
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final SkillEntry skill, final int damage) {
        if (getActor().getEffectList().getEffectsBySkillId(6823) != null && i_ai3 == 1)
        {
            i_ai2 += damage;
        }
        if (i_ai2 > 5000) {
            Creature target = getActor().getAggroList().getMostHated();
            if (target != null)
            {
                getActor().altOnMagicUseTimer(target, reflectiveAttackLv3);
            }
            i_ai2 = 0;
            i_ai3 = 0;
            i_ai5 = 1;
        }
        if (i_ai2 > 10000) {
            Creature target = getActor().getAggroList().getMostHated();
            if (target != null)
            {
                getActor().altOnMagicUseTimer(target, reflectiveAttackLv2);
            }
            i_ai2 = 0;
            i_ai3 = 0;
            i_ai5 = 1;
        }
        super.onEvtAttacked(attacker, skill, damage);
    }


    @Override
    protected void onEvtFinishCasting(SkillEntry skill) {
        if (Rnd.get(5) < 1)
        {
            i_ai2 = 0;
            i_ai3 = 1;
            getActor().altOnMagicUseTimer(getActor(), reflectiveSkill);
            AddTimerEx(damageTimer_15, 15 * 1000);
        }
    }

    @Override
    protected void onEvtTimerFiredEx(int timer_id, Object arg1, Object arg2)
    {
        final NpcInstance actor = getActor();
        if (actor == null || actor.isDead()) {
            return;
        }

        if (timer_id == damageTimer_15)
        {
            Creature target = getActor().getAggroList().getMostHated();
            if (i_ai5 == 0 && target != null)
            {
                getActor().altOnMagicUseTimer(target, reflectiveAttackLv1);
            }
            i_ai3 = 0;
        }
        super.onEvtTimerFiredEx(timer_id, arg1, arg2);
    }
}