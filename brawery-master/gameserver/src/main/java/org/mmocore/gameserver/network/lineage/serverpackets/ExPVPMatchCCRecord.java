package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.model.entity.events.impl.KrateisCubeEvent;
import org.mmocore.gameserver.model.entity.events.objects.KrateisCubePlayerObject;
import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

import java.util.Map;

/**
 * @author VISTALL
 */
public class ExPVPMatchCCRecord extends GameServerPacket {
    private final Map<String, Integer> players;

    public ExPVPMatchCCRecord(final KrateisCubeEvent cube) {
        players = cube.getSortedPlayers();
    }

    public ExPVPMatchCCRecord(Map<String, Integer> scores)
    {
        players = scores;
    }

    @Override
    public void writeData() {
        writeD(0x00); // Open/Dont Open
        writeD(players.size());
        for(Map.Entry<String, Integer> p : players.entrySet())
        {
            writeS(p.getKey());
            writeD(p.getValue().intValue());
        }
    }
}