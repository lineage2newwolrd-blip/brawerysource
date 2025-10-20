package org.mmocore.gameserver.object.components.player.packet;

import org.mmocore.commons.limiter.RateLimiter;
import org.mmocore.gameserver.object.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mmocore.gameserver.configuration.config.ExtConfig.FULL_RANGE_PACKET_LIMIT;
import static org.mmocore.gameserver.configuration.config.ExtConfig.SHORT_RANGE_PACKET_LIMIT;

public class PacketThrottler {
    private static final Logger log = LoggerFactory.getLogger(PacketThrottler.class);
    private final Player playerRef;

    public static final int MAX_PACKET_RANGE = 3000;
    private static final int SHORT_PACKET_RANGE = 1500;
    private RateLimiter fullRangePacketLimiter;
    private RateLimiter shortRangePacketLimiter;

    public PacketThrottler(Player player) {
        playerRef = player;
        fullRangePacketLimiter = new RateLimiter(FULL_RANGE_PACKET_LIMIT, 1000);
        shortRangePacketLimiter = new RateLimiter(SHORT_RANGE_PACKET_LIMIT, 1000);
    }

    public Player getPlayer() {
        return playerRef;
    }

    public PacketRange getPacketRange() {
        if (fullRangePacketLimiter.getRest() > 0)
            return PacketRange.FULL;
        if (shortRangePacketLimiter.getRest() > 0)
            return PacketRange.SHORT;

        return PacketRange.NONE;
    }

    public void onSendPacket() {
        if (!fullRangePacketLimiter.passWithoutStat())
            shortRangePacketLimiter.passWithoutStat();
    }

    public enum PacketRange {
        FULL(MAX_PACKET_RANGE),
        SHORT(SHORT_PACKET_RANGE),
        NONE(0);

        private int range;

        PacketRange(int range) {
            this.range = range;
        }

        public int range() {
            return range;
        }
    }
}
