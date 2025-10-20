package org.mmocore.gameserver.handler.admincommands.impl;

import org.mmocore.gameserver.geoengine.GeoEngine;
import org.mmocore.gameserver.handler.admincommands.IAdminCommandHandler;
import org.mmocore.gameserver.network.lineage.serverpackets.Moving.FlyToLocation;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.GameObject;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.PositionUtils;

public class AdminPush implements IAdminCommandHandler {

	public void push(Player activeChar, Creature target) {
		double radian = PositionUtils.convertHeadingToRadian(PositionUtils.calculateHeadingFrom(activeChar, target));
		int x1 = -(int) (Math.sin(radian) * 300);
		int y1 = (int) (Math.cos(radian) * 300);
		Location flyLoc;
		if (target.isFlying())
			flyLoc = GeoEngine.moveCheckInAir(target.getX(), target.getY(), target.getZ(), target.getX() + x1, target.getY() + y1, target.getZ(), target.getColRadius(), target.getGeoIndex());
		else
			flyLoc = GeoEngine.moveCheck(target.getX(), target.getY(), target.getZ(), target.getX() + x1, target.getY() + y1, true, target.getGeoIndex());

		target.setLoc(flyLoc);
		target.broadcastPacket(new FlyToLocation(target, flyLoc, FlyToLocation.FlyType.CHARGE));
	}

	@Override
	public boolean useAdminCommand(Enum<?> comm, String[] wordList, String fullString, Player activeChar) throws InstantiationException, IllegalAccessException {
		if (!activeChar.getPlayerAccess().CanTeleport)
			return false;

		GameObject obj = activeChar.getTarget();
		if (obj == null || !obj.isCreature() || activeChar.equals(obj)) {
			activeChar.sendMessage("Wrong target type.");
			return false;
		}

		Creature target = (Creature) obj;
		push(activeChar, target);

		return true;
	}

	@Override
	public Enum[] getAdminCommandEnum() {
		return Commands.values();
	}

	@Override
	public String[] getAdminCommandString() {
		return new String[0];
	}

	enum Commands {
		admin_push
	}
}
