package org.mmocore.gameserver.phantoms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 23.08.2016 8:14
 */
public class PhantomMemory {
    private List<String> ignoredChatNicks = new ArrayList<>();
    private boolean canDelete = false;

    public void addIgnoredChatNick(String nick) {
        ignoredChatNicks.add(nick);
    }

    public boolean isIgnoredChatNick(String nick) {
        return ignoredChatNicks.contains(nick);
    }

    public void setCanDelete(boolean value) {
        canDelete = value;
    }

    public boolean canDeleted() {
        return canDelete;
    }
}
