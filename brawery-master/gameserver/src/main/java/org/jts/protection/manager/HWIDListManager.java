package org.jts.protection.manager;

import org.jts.protection.database.ProtectionDAO;
import org.jts.protection.manager.hwid.HWIDInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author KilRoy
 * maybe TODO[K] - как то развернутся на базе данного листа О_о
 */
public class HWIDListManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(HWIDListManager.class);

    private Map<Integer, HWIDInfo> listHWID;

    private HWIDListManager() {
        load();

        LOGGER.info("HWIDManager: Loaded " + listHWID.size() + " HWIDs");
    }

    public static HWIDListManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void load() {
        listHWID = ProtectionDAO.getInstance().loadHWIDList();
    }

    private static class LazyHolder {
        private static final HWIDListManager INSTANCE = new HWIDListManager();
    }
}