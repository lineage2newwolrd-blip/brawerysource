package org.mmocore.gameserver.skills.effects;

import org.mmocore.gameserver.model.Effect;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.skills.SkillEntry;

/**
 * @author iRock
 */
public class Effect_i_dispel_by_slot extends Effect {
    private final String _arg;

    public Effect_i_dispel_by_slot(final Creature creature, final Creature target, final SkillEntry skill, final EffectTemplate template) {
        super(creature, target, skill, template);
        _arg = template.getParam().getString("argument");
    }

    @Override
    public void onStart() {
        final Player player = (Player) getEffected();
        final Effect effect = player.getEffectList().getEffectByStackType(_arg);
        if (effect!=null) {
            effect.exit();
        }
    }

    @Override
    protected boolean onActionTime() {
        return false;
    }
}
