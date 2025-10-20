package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

public class ExEventMatchObserver extends GameServerPacket {

    private final int type;
    private final String group1;
    private final String group2;

    public ExEventMatchObserver(int type)
    {
        this(type, "", "");
    }

    public ExEventMatchObserver(int type, String group1, String group2)
    {
        this.type = type;
        this.group1 = group1;
        this.group2 = group2;
    }

    @Override
    protected void writeData() {
        writeD(0x00);// Race id
        writeC(type);//0-exit, 1-enter, 2-enter with return
        writeC(0x00);//unknown
        writeS(group1);
        writeS(group2);
    }
}