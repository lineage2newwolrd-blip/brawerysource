package org.mmocore.gameserver.scripts.quests;

import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.Quest;
import org.mmocore.gameserver.model.quest.QuestState;
import org.mmocore.gameserver.network.lineage.serverpackets.MagicSkillUse;

public class _651_RunawayYouth extends Quest {
    //Npc
    private static int IVAN = 32014;
    private static int BATIDAE = 31989;
    //Items
    private static int SOE = 736;
    private static boolean despawn;

    public _651_RunawayYouth() {
        super(false);

        addStartNpc(IVAN);
        addTalkId(BATIDAE);
        addLevelCheck(26);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("runaway_boy_ivan_q0651_03.htm")) {
            if (st.ownItemCount(SOE) > 0 && !despawn) {
                st.setCond(1);
                st.setState(STARTED);
                st.soundEffect(SOUND_ACCEPT);
                st.takeItems(SOE, 1);
                htmltext = "runaway_boy_ivan_q0651_04.htm";
                despawn = true;
                npc.broadcastPacket(new MagicSkillUse(npc,npc,2013,1,20000,0));
                //Каст СОЕ и изчезновение НПЦ
                st.startQuestTimer("ivan_timer", 20000, npc);
            }
        } else if (event.equalsIgnoreCase("runaway_boy_ivan_q0651_05.htm")) {
            st.exitQuest(true);
            st.soundEffect(SOUND_GIVEUP);
        } else if (event.equalsIgnoreCase("ivan_timer")) {
            if(npc!=null && npc.getSpawn()!=null)
                npc.decayOrDelete();
            despawn = false;
            htmltext = null;
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        String htmltext = "noquest";
        int npcId = npc.getNpcId();
        int cond = st.getCond();
        if (npcId == IVAN && cond == 0) {
            switch (isAvailableFor(st.getPlayer())) {
                case LEVEL:
                    htmltext = "runaway_boy_ivan_q0651_01a.htm";
                    st.exitQuest(true);
                    break;
                default:
                    htmltext = "runaway_boy_ivan_q0651_01.htm";
                    break;
            }
        } else if (npcId == BATIDAE && cond == 1) {
            htmltext = "fisher_batidae_q0651_01.htm";
            st.giveReward(ADENA_ID, 2883, true);
            st.soundEffect(SOUND_FINISH);
            st.exitQuest(true);
        }
        return htmltext;
    }
}
