package org.mmocore.gameserver.model.instances;

import gnu.trove.set.hash.TIntHashSet;
import org.mmocore.gameserver.network.lineage.serverpackets.MagicSkillUse;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.templates.npc.NpcTemplate;
import org.thymeleaf.util.ArrayUtils;

import java.util.Collections;
import java.util.StringTokenizer;

public class OlympiadBufferInstance extends NpcInstance {
    private final TIntHashSet _buffs = new TIntHashSet();
    private static Integer[] _skills = new Integer[]{1086, 1204, 1059, 1085, 1078, 1068, 1240, 1077, 1242, 1062};

    public OlympiadBufferInstance(final int objectId, final NpcTemplate template) {
        super(objectId, template);
    }

    @Override
    public void onBypassFeedback(final Player player, final String command) {
        if (!canBypassCheck(player, this)) {
            return;
        }

        if (_buffs.size() < 5 && command.startsWith("Buff")) {
            final StringTokenizer st = new StringTokenizer(command, " ");
            st.nextToken();
            int id = Integer.parseInt(st.nextToken());
            int lvl = Integer.parseInt(st.nextToken());
            if (!ArrayUtils.contains(_skills, id))
                return;
            final SkillEntry skill = SkillTable.getInstance().getSkillEntry(id, lvl);
            broadcastPacket(new MagicSkillUse(this, player, id, lvl, 0, 0));
            callSkill(skill, Collections.singletonList(player), true);
            _buffs.add(id);
        }
        showChatWindow(player, 0);
    }

    @Override
    public String getHtmlPath(final int npcId, final int val, final Player player) {
        final int size = _buffs.size();
        if (size == 0) {
            return "olympiad/olympiad_master001.htm";
        }
        if (size < 5) {
            return "olympiad/olympiad_master002.htm";
        }

        return "olympiad/olympiad_master003.htm";
    }
}