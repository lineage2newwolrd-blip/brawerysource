package org.mmocore.gameserver;

import org.mmocore.commons.lang.reference.HardReference;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.network.lineage.components.IBroadcastPacket;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class Broadcaster {
    private static final Logger log = LoggerFactory.getLogger(Broadcaster.class);

    public static void broadcast(Creature actor, IBroadcastPacket... packets) {
        if (!actor.isVisible() || actor.isInvisible() || packets.length == 0)
            return;

        execute(new BroadcastPacketArray(actor, packets));
    }

    public static void broadcast(Creature actor, List<IBroadcastPacket> packets) {
        if (!actor.isVisible() || actor.isInvisible()  || packets.isEmpty())
            return;

        execute(new BroadcastPacketsList(actor, packets));
    }

    private static void execute(Runnable task) {
        ThreadPoolManager.getInstance().executeBroadcast(task);
    }

    private static abstract class BroadcastPackets extends RunnableImpl {
        private final HardReference<? extends Creature> actorRef;

        protected BroadcastPackets(Creature actor) {
            this.actorRef = actor.getRef();
        }

        @Override
        protected final void runImpl() throws Exception {
            Creature actor = actorRef.get();
            if (actor == null) {
                return;
            }

            final List<Player> players = World.getAroundObservers(actor);
            for (final Player target : players) {
                if (target.isInObserverMode()) {
                    sendPackets(target);
                    continue;
                }
                forEachPacket(packet -> {
                    if (packet.isInPacketRange(actor, target))
                        target.sendPacket(packet);
                });
            }
        }

        abstract void sendPackets(Creature target);
        abstract void forEachPacket(Consumer<IBroadcastPacket> consumer);

        @Override
        protected final void exceptionWrapper(Exception e) {
            log.error("Failed broadcast packets from {}", actorRef.get(), e);
        }
    }

    private static class BroadcastPacketArray extends BroadcastPackets {
        private final IBroadcastPacket[] packets;

        BroadcastPacketArray(Creature actor, IBroadcastPacket[] packets) {
            super(actor);
            this.packets = packets;
        }

        @Override
        void sendPackets(Creature target) {
            target.sendPacket(packets);
        }

        @Override
        void forEachPacket(Consumer<IBroadcastPacket> consumer) {
            for (IBroadcastPacket packet : packets) {
                consumer.accept(packet);
            }
        }
    }

    private static class BroadcastPacketsList extends BroadcastPackets {
        private final List<IBroadcastPacket> packets;

        BroadcastPacketsList(Creature actor, List<IBroadcastPacket> packets) {
            super(actor);
            this.packets = packets;
        }

        @Override
        void sendPackets(Creature target) {
            target.sendPacket(packets);
        }

        @Override
        void forEachPacket(Consumer<IBroadcastPacket> consumer) {
            packets.forEach(consumer);
        }
    }
}
