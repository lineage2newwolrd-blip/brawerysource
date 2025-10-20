package org.mmocore.gameserver.network.lineage.components;

import org.mmocore.gameserver.network.lineage.serverpackets.L2GameServerPacket;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;

/**
 * @author VISTALL
 * @date 13:28/01.12.2010
 */
@FunctionalInterface
public interface IBroadcastPacket {
    L2GameServerPacket packet(Player player);
    default boolean isInPacketRange(final Creature sender, final Player recipient) {
        return true;
    }
}
