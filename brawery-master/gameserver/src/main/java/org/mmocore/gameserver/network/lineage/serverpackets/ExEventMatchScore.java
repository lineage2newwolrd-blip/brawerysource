package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

public class ExEventMatchScore extends GameServerPacket {
    private final int score1;
    private final int score2;

    public ExEventMatchScore(int score1, int score2)
    {
        this.score1=score1;
        this.score2=score2;
    }

    @Override
    protected void writeData() {
        writeD(0x00);// Race id
        writeD(score1);
        writeD(score2);
    }
}