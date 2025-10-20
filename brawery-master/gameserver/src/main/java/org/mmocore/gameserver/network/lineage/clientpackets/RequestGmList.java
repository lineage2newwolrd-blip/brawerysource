package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.ProtectType;
import org.mmocore.gameserver.configuration.config.protection.BaseProtectSetting;
import org.mmocore.gameserver.manager.GmManager;
import org.mmocore.gameserver.object.Player;
import smartguard.api.ISmartGuardService;
import smartguard.core.properties.GuardProperties;
import smartguard.integration.SmartClient;
import smartguard.spi.SmartGuardSPI;

public class RequestGmList extends L2GameClientPacket {
	private byte[] smartData = null;

	@Override
	protected void readImpl() {
		if (BaseProtectSetting.protectType == ProtectType.SMART && GuardProperties.ProtectionEnabled) {
			if (_buf.remaining() > 2) {
				int dataLen = readH();

				if (_buf.remaining() >= dataLen) {
					smartData = new byte[dataLen];
					readB(smartData);
				}
			}
		}
	}

	@Override
	protected void runImpl() {
		if (smartData != null) {
			ISmartGuardService svc = SmartGuardSPI.getSmartGuardService();
			svc.getSmartGuardBus().onClientData(new SmartClient(getClient()), smartData);
			return;
		}
		final Player activeChar = getClient().getActiveChar();
		if (activeChar != null) {
			GmManager.sendListToPlayer(activeChar);
		}
	}
}