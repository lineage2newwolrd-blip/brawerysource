package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.ProtectType;
import org.mmocore.gameserver.configuration.config.protection.BaseProtectSetting;
import org.mmocore.gameserver.network.lineage.components.GameServerPacket;
import org.strixplatform.StrixPlatform;
import org.strixplatform.utils.StrixClientData;
import smartguard.core.properties.GuardProperties;
import smartguard.spi.SmartGuardSPI;

public class VersionCheck extends GameServerPacket {
	private final byte[] key;
	private final StrixClientData clientData;

	public VersionCheck(final byte[] key) {
		this.key = key;
		this.clientData = null;
		if (key != null && BaseProtectSetting.protectType == ProtectType.SMART && GuardProperties.ProtectionEnabled)
			SmartGuardSPI.getSmartGuardService().getLicenseManager().cryptInternalData(key);
	}

	public VersionCheck(final byte[] key, final StrixClientData clientData) {
		this.key = key;
		this.clientData = clientData;
	}

	@Override
	public void writeData() {
		if (key == null || key.length == 0) {
			writeC(0x00);
			if (clientData != null && StrixPlatform.getInstance().isBackNotificationEnabled()) {
				writeC(clientData.getServerResponse().ordinal());
			}
			return;
		}
		writeC(0x01);
		writeB(key);
		writeD(0x01);
		writeD(0x00);
		writeC(0x00);
		writeD(0x00); // Seed (obfuscation key)
	}
}