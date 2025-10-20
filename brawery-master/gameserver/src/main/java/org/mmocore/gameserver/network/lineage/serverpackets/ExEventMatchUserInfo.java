package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;
import org.mmocore.gameserver.object.Player;

public class ExEventMatchUserInfo extends GameServerPacket {
    private final EventMatchUserInfo player;
    // format dSdddddddd
    public ExEventMatchUserInfo(Player player)
    {
        this.player = new EventMatchUserInfo(player);
    }

    @Override
    protected void writeData() {
        writeD(player.id);
        writeS(player.name);
        writeD(player.curHp);
        writeD(player.maxHp);
        writeD(player.curMp);
        writeD(player.maxMp);
        writeD(player.curCp);
        writeD(player.maxCp);
        writeD(player.level);
        writeD(player.class_id);
    }

    public static class EventMatchUserInfo {
        public final String name;
        public final int id;
        public final int curCp;
        public final int maxCp;
        public final int curHp;
        public final int maxHp;
        public final int curMp;
        public final int maxMp;
        public final int level;
        public final int class_id;

        public EventMatchUserInfo(final Player player) {
            name = player.getName();
            id = player.getObjectId();
            curCp = (int) player.getCurrentCp();
            maxCp = player.getMaxCp();
            curHp = (int) player.getCurrentHp();
            maxHp = (int) player.getMaxHp();
            curMp = (int) player.getCurrentMp();
            maxMp = player.getMaxMp();
            level = player.getLevel();
            class_id = player.getPlayerClassComponent().getClassId().getId();
        }
    }
}