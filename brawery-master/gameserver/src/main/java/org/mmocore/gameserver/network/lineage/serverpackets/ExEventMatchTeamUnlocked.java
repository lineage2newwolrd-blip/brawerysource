package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

public class ExEventMatchTeamUnlocked extends GameServerPacket {
    private final int team;
    public ExEventMatchTeamUnlocked(int team)
    {
        this.team = team;
    }

    @Override
    protected void writeData() {
        writeD(0x01);//race id
        writeC(team);//team id
    }
}