package org.mmocore.gameserver.data.client.holder;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.mmocore.commons.data.AbstractHolder;
import org.mmocore.gameserver.templates.client.QuestLine;

import java.util.Optional;

/**
 * @author KilRoy
 */
public class QuestDataHolder extends AbstractHolder {
    private static final QuestDataHolder INSTANCE = new QuestDataHolder();
    private static final TIntObjectMap<QuestLine> quests = new TIntObjectHashMap<>();

    public static QuestDataHolder getInstance() {
        return INSTANCE;
    }

    public void addTestQuest(final int id, final QuestLine questInfo) {
        quests.put(id, questInfo);
    }

    public QuestLine getTestQuestInfo(final int id) {
        final Optional<QuestLine> questLine = Optional.ofNullable(quests.get(id));
        if (questLine.isPresent()) {
            return questLine.get();
        } else {
            error("questLine id - " + id + " map null");
        }
        return null;
    }

    public TIntObjectMap<QuestLine> getTestQuests() {
        return quests;
    }

    @Override
    public void clear() {
        quests.clear();
    }

    @Override
    public int size() {
        return quests.size();
    }
}