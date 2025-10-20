package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.object.Player;

public class RequestExEventMatchObserverEnd extends L2GameClientPacket {
    private int unk, unk2;

    /**
     * format: dd
     */
    @Override
    protected void readImpl() {
        unk = readD();
        unk2 = readD();
    }

    @Override
    protected void runImpl() {
        Player activeChar = getClient().getActiveChar();
        if(activeChar == null)
            return;
        if(activeChar.isInObserverMode())
            activeChar.leaveMatchObserverMode();
    }
}