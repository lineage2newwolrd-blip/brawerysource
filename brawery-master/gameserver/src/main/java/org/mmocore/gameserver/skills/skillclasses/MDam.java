package org.mmocore.gameserver.skills.skillclasses;


import org.mmocore.gameserver.model.Skill;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.stats.Formulas;
import org.mmocore.gameserver.templates.StatsSet;

import java.util.List;

public class MDam extends Skill {
    public MDam(final StatsSet set) {
        super(set);
    }

    @Override
    public void useSkill(final SkillEntry skillEntry, final Creature activeChar, final List<Creature> targets) {
        final int sps = isSSPossible() ? (isMagic() ? activeChar.getChargedSpiritShot() : activeChar.getChargedSoulShot() ? 2 : 0) : 0;
        final boolean corpseSkill = _targetType == SkillTargetType.TARGET_CORPSE || _targetType == SkillTargetType.TARGET_AREA_AIM_CORPSE;
        Creature castingTarget = activeChar.getCastingTarget();

        Creature realTarget;
        boolean reflected;

        for (final Creature target : targets) {
            if (target != null) {
                if (target.isDead()) {
                    continue;
                }

                reflected = target.checkReflectSkill(activeChar, skillEntry);
                realTarget = reflected ? activeChar : target;

                final Formulas.AttackInfo attackInfo = Formulas.calcMagicDam(activeChar, realTarget, skillEntry, sps);

                if (attackInfo.lethal_dmg > 0) {
                    realTarget.reduceCurrentHp(attackInfo.lethal_dmg, activeChar, skillEntry, true, true, false, false, false, false, false, true);
                }

                if (attackInfo.damage >= 1) {
                    realTarget.reduceCurrentHp(attackInfo.damage, activeChar, skillEntry, true, true, false, true, false, false, true);
                }

                getEffects(skillEntry, activeChar, target, getActivateRate() > 0, false, reflected);
            }
        }

        if(corpseSkill && castingTarget!=null && castingTarget.isDead() && castingTarget.isNpc()){
            activeChar.getAI().setAttackTarget(null);
            ((NpcInstance) castingTarget).endDecayTask();
            castingTarget.deleteMe();
        }

        if (isSuicideAttack()) {
            activeChar.doDie(null);
        } else if (isSSPossible()) {
            activeChar.unChargeShots(isMagic());
        }
    }
}