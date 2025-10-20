package org.mmocore.gameserver.model.entity.olympiad;

import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.manager.OlympiadHistoryManager;
import org.mmocore.gameserver.model.entity.Hero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationTask extends RunnableImpl {
    private static final Logger _log = LoggerFactory.getLogger(ValidationTask.class);

    @Override
    public void runImpl() throws Exception {
        OlympiadHistoryManager.getInstance().switchData();

        Olympiad._period = 0;
        Olympiad._currentCycle++;

        OlympiadDatabase.setNewOlympiadEnd();

        Olympiad.init();
        OlympiadDatabase.save();
    }
}