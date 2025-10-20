package org.mmocore.gameserver.network.lineage.clientpackets.Moving;


import org.mmocore.gameserver.configuration.config.GeodataConfig;
import org.mmocore.gameserver.geoengine.GeoEngine;
import org.mmocore.gameserver.network.lineage.clientpackets.L2GameClientPacket;
import org.mmocore.gameserver.network.lineage.serverpackets.Moving.CharMoveToLocation;
import org.mmocore.gameserver.network.lineage.serverpackets.Moving.FlyToLocation;
import org.mmocore.gameserver.object.Boat;
import org.mmocore.gameserver.object.ClanAirShip;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.utils.Location;

    public class ValidatePosition extends L2GameClientPacket {
    private final Location _loc = new Location();

    private int _boatObjectId;
    private Location _lastClientPosition;
    private Location _lastServerPosition;

    public static void correctPositionWithMovePacket(Player player, boolean broadcastToAll) {
        if (player == null)
            return;
        CharMoveToLocation movePacket = new CharMoveToLocation(player.getObjectId(), player.getLastClientPosition(),
                player.getLastServerPosition());
        if (broadcastToAll)
            player.broadcastPacket(movePacket);
        else
            player.sendPacket(movePacket);
    }

    public static void correctPosition(Player player, CorrectType type) {
        if (player == null)
            return;
        if (player.getLastServerPosition() != null && player.getLastClientPosition() != null)
            if (player.getLastServerPosition().distance3D(player.getLastClientPosition()) > GeodataConfig.maxAsyncCoordDiffBeforeAttack) {
                switch (type) {
                    case FlyCorrect:
                        player.sendPacket(new FlyToLocation(player, player.getLastServerPosition(), FlyToLocation.FlyType.DUMMY));
                        break;
                    case ValidateCorrect:
                        player.validateLocation(1);
                        break;
                    case MoveCorrect:
                        correctPositionWithMovePacket(player, true);
                        break;
                }
            }
    }

    public static boolean isNotCastingFlySkillAndNotFalling(Player player) {
        if (player.isFalling())
            return false;

        SkillEntry castingSkill = player.getCastingSkill();
        if (castingSkill != null && castingSkill.getTemplate().getFlyType() != FlyToLocation.FlyType.NONE)
            return false;

        return true;
    }

    /**
     * packet type id 0x48
     * format:		cddddd
     */
    @Override
    protected void readImpl() {
        _loc.x = readD();
        _loc.y = readD();
        _loc.z = readD();
        _loc.h = readD();
        _boatObjectId = readD();
    }

    @Override
    protected void runImpl() {
        final Player activeChar = getClient().getActiveChar();
        if (activeChar == null) {
            return;
        }

        if (activeChar.isTeleporting() || activeChar.isInObserverMode()) {
            return;
        }

        _lastClientPosition = activeChar.getLastClientPosition();
        _lastServerPosition = activeChar.getLastServerPosition();

        if (_lastClientPosition == null) {
            _lastClientPosition = activeChar.getLoc();
        }
        if (_lastServerPosition == null) {
            _lastServerPosition = activeChar.getLoc();
        }

        if (activeChar.isAirshipCaptain()) {
            return;
        }

        if (!activeChar.isInBoat() && activeChar.getX() == 0 && activeChar.getY() == 0 && activeChar.getZ() == 0) {
            correctPosition(activeChar);
            _loc.set(activeChar.getLoc());
        }

        if (activeChar.isInFlyingTransform()) {
            // В летающей трансформе нельзя находиться на территории Aden
            if (activeChar.getX() > -166168) {
                activeChar.stopTransformation();
                return;
            }
            // В летающей трансформе нельзя летать ниже, чем 0, и выше, чем 6000
            if (activeChar.getZ() <= 0 || activeChar.getZ() >= 6000) {
                activeChar.teleToLocation(activeChar.getLoc().setZ(Math.min(5950, Math.max(50, activeChar.getZ()))));
                return;
            }
        }
        final Boat boat = activeChar.getBoat();
        if (boat != null) {
            if (boat.isClanAirShip() && ((ClanAirShip) boat).getDriver() == activeChar) // у драйвера _boatObjectId = 0
            {
                return;
            }
            if (boat.getObjectId() == _boatObjectId) // DS: перемещение на корабле не реализовано, сразу задается конечная точка, поэтому рассылаем валидейт только при очень грубых ошибках
            {
                Location boatLoc = activeChar.getInBoatPosition();
                if (boatLoc != null && (boatLoc.distance(_loc) > 1024 || Math.abs(_loc.z - boatLoc.z) > 256)) {
                    activeChar.sendPacket(boat.validateLocationPacket(activeChar));
                }
            }
            return;
        }

        final double diff = _loc.distance3D(activeChar.getLoc()); // activeChar.getDistance(_loc.x, _loc.y)
        final int h = _lastServerPosition.z - activeChar.getZ();
        if (h >= activeChar.getPlayerTemplateComponent().getPcSafeFallHeight())// Пока падаем, высоту не корректируем
        {
            activeChar.falling(h);
        }

	    if (diff > GeodataConfig.maxAsyncCoordDiff && isNotCastingFlySkillAndNotFalling(activeChar)) {
		    //Если валидация вызвана скиллом телепорта, до конца каста не обрабатываем
            if(activeChar.isInWater()) {
                activeChar.waterFall();
                activeChar.validateLocation(0);
            } else {
                activeChar.validateLocation(1);
            }
	    }

        if (activeChar.getServitor() != null && !activeChar.getServitor().isInRange()) {
            activeChar.getServitor().stopMove();
        }

        activeChar.setLastClientPosition(_loc.setH(activeChar.getHeading()));
        activeChar.setLastServerPosition(activeChar.getLoc());
    }

    @Deprecated
    private void correctPosition(final Player activeChar) {
        if (activeChar.isGM()) {
            activeChar.sendAdminMessage("Server loc: " + activeChar.getLoc());
            activeChar.sendAdminMessage("Correcting position...");
        }
        if (_lastServerPosition.x != 0 && _lastServerPosition.y != 0 && _lastServerPosition.z != 0) {
            if (GeoEngine.getNSWE(_lastServerPosition.x, _lastServerPosition.y, _lastServerPosition.z, activeChar.getGeoIndex()) == GeoEngine.NSWE_ALL) {
                activeChar.teleToLocation(_lastServerPosition);
            } else {
                activeChar.teleToClosestTown();
            }
        } else if (_lastClientPosition.x != 0 && _lastClientPosition.y != 0 && _lastClientPosition.z != 0) {
            if (GeoEngine.getNSWE(_lastClientPosition.x, _lastClientPosition.y, _lastClientPosition.z, activeChar.getGeoIndex()) == GeoEngine.NSWE_ALL) {
                activeChar.teleToLocation(_lastClientPosition);
            } else {
                activeChar.teleToClosestTown();
            }
        } else {
            activeChar.teleToClosestTown();
        }
    }

    public enum CorrectType {
        FlyCorrect, ValidateCorrect, MoveCorrect
    }
}