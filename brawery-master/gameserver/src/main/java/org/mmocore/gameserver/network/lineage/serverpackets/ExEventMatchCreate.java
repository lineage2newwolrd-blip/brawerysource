package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

public class ExEventMatchCreate extends GameServerPacket {
    private final int id;

    public ExEventMatchCreate(int id)
    {
        this.id = id;
    }

    @Override
    protected void writeData() {
        writeD(id);
    }
}