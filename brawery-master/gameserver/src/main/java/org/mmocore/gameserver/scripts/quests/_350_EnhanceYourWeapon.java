package org.mmocore.gameserver.scripts.quests;

import org.mmocore.gameserver.data.xml.holder.NpcHolder;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.Quest;
import org.mmocore.gameserver.model.quest.QuestState;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.templates.npc.NpcTemplate;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.SoulCrystalUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _350_EnhanceYourWeapon extends Quest {
    private static final int RED_SOUL_CRYSTAL0_ID = 4629;
    private static final int GREEN_SOUL_CRYSTAL0_ID = 4640;
    private static final int BLUE_SOUL_CRYSTAL0_ID = 4651;
    private static final int Jurek = 30115;
    private static final int Gideon = 30194;
    private static final int Winonin = 30856;
    public _350_EnhanceYourWeapon() {
        super(false);
        addStartNpc(Jurek);
        addStartNpc(Gideon);
        addStartNpc(Winonin);

        for (NpcTemplate template : NpcHolder.getInstance().getAll()) {
            if (template != null && !template.getAbsorbInfo().isEmpty()) {
                addKillId(template.npcId);
            }
        }
        addLevelCheck(40, 80);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        if (event.equalsIgnoreCase(Jurek + "-04.htm") || event.equalsIgnoreCase(Gideon + "-04.htm") || event.equalsIgnoreCase(Winonin + "-04.htm")) {
            st.setCond(1);
            st.setState(STARTED);
            st.soundEffect(SOUND_ACCEPT);
        }
        if (event.equalsIgnoreCase(Jurek + "-09.htm") || event.equalsIgnoreCase(Gideon + "-09.htm") || event.equalsIgnoreCase(Winonin + "-09.htm")) {
            st.giveItems(RED_SOUL_CRYSTAL0_ID, 1);
        }
        if (event.equalsIgnoreCase(Jurek + "-10.htm") || event.equalsIgnoreCase(Gideon + "-10.htm") || event.equalsIgnoreCase(Winonin + "-10.htm")) {
            st.giveItems(GREEN_SOUL_CRYSTAL0_ID, 1);
        }
        if (event.equalsIgnoreCase(Jurek + "-11.htm") || event.equalsIgnoreCase(Gideon + "-11.htm") || event.equalsIgnoreCase(Winonin + "-11.htm")) {
            st.giveItems(BLUE_SOUL_CRYSTAL0_ID, 1);
        }
        if (event.equalsIgnoreCase("exit.htm")) {
            st.exitQuest(true);
        }
        return event;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        String npcId = str(npc.getNpcId());
        String htmltext = "noquest";
        int id = st.getState();
        if (st.ownItemCount(RED_SOUL_CRYSTAL0_ID) == 0 && st.ownItemCount(GREEN_SOUL_CRYSTAL0_ID) == 0 && st.ownItemCount(BLUE_SOUL_CRYSTAL0_ID) == 0) {
            if (id == CREATED) {
                switch (isAvailableFor(st.getPlayer())) {
                    case LEVEL:
                        htmltext = st.getPlayer().getLanguage() == Language.ENGLISH ? "This quest available for 40 and from 80 level." : "Данный квест доступен для игроков от 40 до 80 уровня.";
                        break;
                    default:
                        htmltext = npcId + "-01.htm";
                        break;
                }
            } else {
                htmltext = npcId + "-21.htm";
            }
        } else {
            if (id == CREATED) {
                st.setCond(1);
                st.setState(STARTED);
            }
            htmltext = npcId + "-03.htm";
        }
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState qs) {
        Player player = qs.getPlayer();
        if (player == null || !npc.isMonster()) {
            return null;
        }

        SoulCrystalUtils.calcAbsorb(player, npc, true);

        return null;
    }
}