package org.mmocore.gameserver.scripts.ai.pts.lair_of_antaras;

import org.mmocore.gameserver.ai.DefaultAI;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;

/**
 * @author Mangol
 */
public class ai_party_vitality_herb extends DefaultAI {
    private final int TID_LIFETIME = 787878;
    private final int TIME_LIFETIME = 3;
    private final SkillEntry herb_skill = SkillTable.getInstance().getSkillEntry(6883, 1);

    public ai_party_vitality_herb(final NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtSpawn() {
        super.onEvtSpawn();
        final NpcInstance actor = getActor();
        final Creature arg = actor.getParam4();
        if (arg != null && !arg.isDead() && arg.isPlayer() && arg.getPlayer().getParty() != null) {
            arg.getPlayer().getParty().getPartyMembers().stream().filter(player -> actor.isInRange(player, 1200)).forEach(player -> actor.altOnMagicUseTimer(player, herb_skill));
        }
        AddTimerEx(TID_LIFETIME, TIME_LIFETIME * 1000);
    }

    @Override
    protected void onEvtTimerFiredEx(final int timer_id, final Object arg1, final Object arg2) {
        final NpcInstance actor = getActor();
        if (actor == null) {
            return;
        }
        if (timer_id == TID_LIFETIME) {
            actor.deleteMe();
        }
    }
}
