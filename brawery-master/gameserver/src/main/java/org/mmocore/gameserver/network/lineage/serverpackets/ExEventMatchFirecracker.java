package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

public class ExEventMatchFirecracker extends GameServerPacket {
    private final int objectId;

    public ExEventMatchFirecracker(int objectId)
    {
        this.objectId=objectId;
    }

    @Override
    protected void writeData() {
        writeD(objectId);
    }
}