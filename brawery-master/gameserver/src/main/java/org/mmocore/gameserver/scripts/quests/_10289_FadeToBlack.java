package org.mmocore.gameserver.scripts.quests;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.Quest;
import org.mmocore.gameserver.model.quest.QuestState;

import java.util.StringTokenizer;

/**
 * @author pchayka
 */

public class _10289_FadeToBlack extends Quest {
    private static final int Greymore = 32757;
    private static final int Anays = 25701;
    private static final int MarkofSplendor = 15527;
    private static final int MarkofDarkness = 15528;

    public _10289_FadeToBlack() {
        super(PARTY_ALL);
        addStartNpc(Greymore);
        addKillId(Anays);
        addQuestItem(MarkofSplendor, MarkofDarkness);

        addLevelCheck(82);
        addQuestCompletedCheck(10288);
    }

    @Override
    public String onEvent(String event, QuestState st, NpcInstance npc) {
        String htmltext = event;
        if (event.equalsIgnoreCase("greymore_q10289_03.htm")) {
            st.setState(STARTED);
            st.setCond(1);
            st.soundEffect(SOUND_ACCEPT);
        } else if (event.equalsIgnoreCase("showmark")) {
            if (st.getCond() == 2 && st.ownItemCount(MarkofDarkness) > 0) {
                htmltext = "greymore_q10289_06.htm";
            } else if (st.getCond() == 3 && st.ownItemCount(MarkofSplendor) > 0) {
                htmltext = "greymore_q10289_07.htm";
            } else {
                htmltext = "greymore_q10289_08.htm";
            }
        } else if (event.startsWith("exchange")) {
            StringTokenizer str = new StringTokenizer(event);
            str.nextToken();
            int id = Integer.parseInt(str.nextToken());
            switch (id) {
                case 1:
                    st.giveReward(15775, 1, false);
                    st.giveReward(ADENA_ID, 420920, true);
                    break;
                case 2:
                    st.giveReward(15776, 1, false);
                    st.giveReward(ADENA_ID, 420920, true);
                    break;
                case 3:
                    st.giveReward(15777, 1, false);
                    st.giveReward(ADENA_ID, 420920, true);
                    break;
                case 4:
                    st.giveReward(15778, 1, false);
                    break;
                case 5:
                    st.giveReward(15779, 1, false);
                    st.giveReward(ADENA_ID, 168360, true);
                    break;
                case 6:
                    st.giveReward(15780, 1, false);
                    st.giveReward(ADENA_ID, 168360, true);
                    break;
                case 7:
                    st.giveReward(15781, 1, false);
                    st.giveReward(ADENA_ID, 252540, true);
                    break;
                case 8:
                    st.giveReward(15782, 1, false);
                    st.giveReward(ADENA_ID, 357780, true);
                    break;
                case 9:
                    st.giveReward(15783, 1, false);
                    st.giveReward(ADENA_ID, 357780, true);
                    break;
                case 10:
                    st.giveReward(15784, 1, false);
                    st.giveReward(ADENA_ID, 505100, true);
                    break;
                case 11:
                    st.giveReward(15785, 1, false);
                    st.giveReward(ADENA_ID, 505100, true);
                    break;
                case 12:
                    st.giveReward(15786, 1, false);
                    st.giveReward(ADENA_ID, 505100, true);
                    break;
                case 13:
                    st.giveReward(15787, 1, false);
                    st.giveReward(ADENA_ID, 505100, true);
                    break;
                case 14:
                    st.giveReward(15788, 1, false);
                    st.giveReward(ADENA_ID, 505100, true);
                    break;
                case 15:
                    st.giveReward(15789, 1, false);
                    st.giveReward(ADENA_ID, 505100, true);
                    break;
                case 16:
                    st.giveReward(15790, 1, false);
                    st.giveReward(ADENA_ID, 496680, true);
                    break;
                case 17:
                    st.giveReward(15791, 1, false);
                    st.giveReward(ADENA_ID, 496680, true);
                    break;
                case 18:
                    st.giveReward(15812, 1, false);
                    st.giveReward(ADENA_ID, 563860, true);
                    break;
                case 19:
                    st.giveReward(15813, 1, false);
                    st.giveReward(ADENA_ID, 509040, true);
                    break;
                case 20:
                    st.giveReward(15814, 1, false);
                    st.giveReward(ADENA_ID, 454240, true);
                    break;
            }
            htmltext = "greymore_q10289_09.htm";
            st.takeAllItems(MarkofSplendor, MarkofDarkness);
            st.exitQuest(false);
        }
        return htmltext;
    }

    @Override
    public String onTalk(NpcInstance npc, QuestState st) {
        String htmltext = "noquest";
        int cond = st.getCond();
        if (npc.getNpcId() == Greymore) {
            if (cond == 0) {
                switch (isAvailableFor(st.getPlayer())) {
                    case LEVEL:
                    case QUEST:
                        htmltext = "greymore_q10289_00.htm";
                        st.exitQuest(true);
                        break;
                    default:
                        htmltext = "greymore_q10289_01.htm";
                        break;
                }
            } else if (cond == 1) {
                htmltext = "greymore_q10289_04.htm";
            } else if (cond == 2 || cond == 3) {
                htmltext = "greymore_q10289_05.htm";
            }
        }
        return htmltext;
    }

    @Override
    public String onKill(NpcInstance npc, QuestState st) {
        int cond = st.getCond();
        if (cond == 1) {
            if (npc.getNpcId() == Anays) {
                if (Rnd.chance(30)) {
                    st.giveItems(MarkofSplendor, 1, false);
                    st.setCond(3);
                } else {
                    st.giveItems(MarkofDarkness, 1, false);
                    st.setCond(2);
                }
            }
        }
        return null;
    }


}