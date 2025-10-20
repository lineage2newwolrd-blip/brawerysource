package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

public class ExEventMatchMessage extends GameServerPacket {

    private final int type;
    private final String msg;

    public ExEventMatchMessage(int type, String msg)
    {
        this.type = type;
        this.msg = msg;
    }

    public ExEventMatchMessage(int type)
    {
        this.type = type;
        msg = "";
    }

    @Override
    protected void writeData() {
        writeC(type);//0 - msg, 1-finish, 2-start, 3 - game over, 4 - "1", 5 - "2", 6 - "3", 7 - "4", 8 - "5"
        writeS(msg);
    }
}