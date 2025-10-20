package org.mmocore.gameserver.network.lineage.clientpackets.Moving;

import org.mmocore.gameserver.configuration.config.ExtConfig;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.network.lineage.clientpackets.L2GameClientPacket;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ActionFail;
import org.mmocore.gameserver.object.ObservePoint;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.player.bot_punish.BotPunish.Punish;
import org.mmocore.gameserver.utils.Location;

// cdddddd(d)
public class MoveBackwardToLocation extends L2GameClientPacket {
    private static final int DIFFERENCE_BETWEEN_LOCATIONS = 20;
    private final Location _targetLoc = new Location();
    private final Location _originLoc = new Location();
    private int _moveMovement;

    /**
     * packet type id 0x0f
     */
    @Override
    protected void readImpl() {
        _targetLoc.x = readD();
        _targetLoc.y = readD();
        _targetLoc.z = readD();
        _originLoc.x = readD();
        _originLoc.y = readD();
        _originLoc.z = readD();
        if (_buf.hasRemaining()) {
            _moveMovement = readD(); // 0 клавиатура, 1 мышь.
        }
        if (_moveMovement == 1) {
            // Как щелкните мышью , вы получаете около на 27 ниже позиции , чем когда двигаетесь с помощью клавиатуры
            _targetLoc.z += 27;
        }
    }

    @Override
    protected void runImpl() {
        final Player activeChar = getClient().getActiveChar();
        if (activeChar == null) {
            return;
        }
        activeChar.setLastActiveTime();
        if (ExtConfig.EX_ENABLE_AUTO_HUNTING_REPORT && activeChar.getBotPunishComponent().isBeingPunished()) {
            if (activeChar.getBotPunishComponent().getPlayerPunish().canWalk() && activeChar.getBotPunishComponent().getPlayerPunish().getBotPunishType() == Punish.MOVEBAN)
                activeChar.getBotPunishComponent().endPunishment();
            else {
                activeChar.sendPacket(SystemMsg.YOU_HAVE_BEEN_REPORTED_AS_AN_ILLEGAL_PROGRAM_USER_SO_YOUR_ACTIONS_HAVE_BEEN_RESTRICTED);
                return;
            }
        }
        activeChar.setActive();
        final long now = System.currentTimeMillis();
        final long delta = now - activeChar.getLastMovePacket();

        if (delta < ServerConfig.MOVE_PACKET_DELAY && _moveMovement != 0) {
            activeChar.sendActionFailed();
            return;
        }

        final int dx = _targetLoc.x - activeChar.getX();
        final int dy = _targetLoc.y - activeChar.getY();
        final int diff = dx * dx + dy * dy;

        if (delta < ServerConfig.MOVE_PACKET_DELAY && diff > 10000) {
            activeChar.sendActionFailed();
            return;
        }

        if(_moveMovement != 0 || diff > 10000)
            activeChar.setLastMovePacket();

        if (activeChar.isTeleporting()) {
            activeChar.sendActionFailed();
            return;
        }
        if (activeChar.isFrozen()) {
            activeChar.sendPacket(SystemMsg.YOU_CANNOT_MOVE_WHILE_FROZEN, ActionFail.STATIC);
            return;
        }
        if (activeChar.isInObserverMode()) {
            final ObservePoint observer = activeChar.getObservePoint();
            if (observer != null) {
                observer.moveToLocation(_targetLoc, 0, false);
            }
            return;
        }
        final int dz = _targetLoc.z - activeChar.getZ();
        double dist = Math.sqrt(diff + dz * dz);
        if (dist > 10000) {
            activeChar.sendActionFailed();
            return;
        }
        if (activeChar.getLastMovePacketDestinationDiff() != Double.MIN_VALUE) {
            final double distanceDiff = Math.abs(dist - activeChar.getLastMovePacketDestinationDiff());
            if (distanceDiff < DIFFERENCE_BETWEEN_LOCATIONS) {
                activeChar.sendActionFailed();
                return;
            }
        }
        activeChar.setLastMovePacketDestinationDiff(dist);
        if (activeChar.isOutOfControl()) {
            activeChar.sendActionFailed();
            return;
        }
        if (!activeChar.canMoveAfterInteraction()) {
            activeChar.sendPacket(SystemMsg.YOU_CANNOT_MOVE_WHILE_SPEAKING_TO_AN_NPC, ActionFail.STATIC);
            return;
        }
        if (activeChar.getTeleMode() > 0) {
            if (activeChar.getTeleMode() == 1) {
                activeChar.setTeleMode(0);
            }
            activeChar.sendActionFailed();
            activeChar.teleToLocation(_targetLoc);
            return;
        }
        if (activeChar.isInFlyingTransform()) {
            _targetLoc.z = Math.min(5950, Math.max(50, _targetLoc.z)); // В летающей трансформе нельзя летать ниже, чем 0, и выше, чем 6000
            _targetLoc.x = Math.min(-166168, _targetLoc.x);
        }

        if(activeChar.isActionBlocked(Zone.BLOCKED_ACTION_MOVE))
        {
            activeChar.sendPacket(SystemMsg.YOU_CANNOT_MOVE__YOU_ARE_TOO_ENCUMBERED, ActionFail.STATIC);
            return;
        }
        if(_moveMovement == 0)
            activeChar.moveWithKeyboard(_targetLoc);
        else
            activeChar.moveToLocation(_targetLoc, 0, true);
    }
}