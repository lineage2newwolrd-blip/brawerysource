package org.mmocore.gameserver.scripts.ai;

import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.ai.DefaultAI;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.MagicSkillUse;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mangol
 * @version Freya
 */
public class GunPowder extends DefaultAI {
    // skill use
    private static final SkillEntry s_pailaka_boomup1 = SkillTable.getInstance().getSkillEntry(5714, 1);

    public GunPowder(NpcInstance actor) {
        super(actor);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final SkillEntry skill, final int damage) {
        NpcInstance actor = getActor();
        if (actor != null && !actor.isDead()) {
            List<Creature> targets = new ArrayList<>(_actor.getAroundNpc(600, 200));
            _actor.altUseSkill(_actor, targets, s_pailaka_boomup1);
            //_actor.callSkill(s_pailaka_boomup1, targets, true);
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    actor.deleteMe();
                }
            }, 2000L);
        }
        super.onEvtAttacked(attacker, skill, damage);
    }
}