package org.jts.protection.network;

import org.mmocore.gameserver.ProtectType;
import org.mmocore.gameserver.configuration.config.protection.BaseProtectSetting;
import org.strixplatform.StrixPlatform;
import org.strixplatform.network.cipher.StrixGameCrypt;
import smartguard.api.integration.SmartCrypt;
import smartguard.core.properties.GuardProperties;

/**
 * @author ALF
 */
public class ProtectionGameCrypt {
	private final byte[] inKey = new byte[16], outKey = new byte[16];
	private StrixGameCrypt strixCrypt;
	private SmartCrypt smartCrypt;
	private boolean isEnabled = false;

	public ProtectionGameCrypt() {
		if (BaseProtectSetting.protectType == ProtectType.SMART && GuardProperties.ProtectionEnabled) {
			smartCrypt = new SmartCrypt();
		} else if (BaseProtectSetting.protectType == ProtectType.STRIX && StrixPlatform.getInstance().isPlatformEnabled()) {
			strixCrypt = new StrixGameCrypt();
		}
	}

	public void setKey(final byte[] key) {
		if (BaseProtectSetting.protectType == ProtectType.SMART && GuardProperties.ProtectionEnabled) {
			smartCrypt.setKey(key);
		} else if (BaseProtectSetting.protectType == ProtectType.STRIX && StrixPlatform.getInstance().isPlatformEnabled()) {
			strixCrypt.setKey(key);
		} else {
			System.arraycopy(key, 0, inKey, 0, 16);
			System.arraycopy(key, 0, outKey, 0, 16);
		}
	}

	public boolean decrypt(final byte[] raw, final int offset, final int size) {
		if (!isEnabled)
			return true;

		if (smartCrypt != null) {
			smartCrypt.decrypt(raw, offset, size);
			return true;
		}
		if (strixCrypt != null) {
			return strixCrypt.decrypt(raw, offset, size);
		}

		int temp = 0;
		for (int i = 0; i < size; i++) {
			final int temp2 = raw[offset + i] & 0xFF;
			raw[offset + i] = (byte) (temp2 ^ inKey[i & 15] ^ temp);
			temp = temp2;
		}

		int old = inKey[8] & 0xff;
		old |= inKey[9] << 8 & 0xff00;
		old |= inKey[10] << 0x10 & 0xff0000;
		old |= inKey[11] << 0x18 & 0xff000000;

		old += size;

		inKey[8] = (byte) (old & 0xff);
		inKey[9] = (byte) (old >> 0x08 & 0xff);
		inKey[10] = (byte) (old >> 0x10 & 0xff);
		inKey[11] = (byte) (old >> 0x18 & 0xff);
		return true;
	}

	public boolean encrypt(final byte[] raw, final int offset, final int size) {
		if (!isEnabled) {
			isEnabled = true;
			return true;
		}

		if (smartCrypt != null) {
			smartCrypt.encrypt(raw, offset, size);
			return true;
		}
		if (strixCrypt != null) {
			return strixCrypt.encrypt(raw, offset, size);
		}

		int temp = 0;
		for (int i = 0; i < size; i++) {
			final int temp2 = raw[offset + i] & 0xFF;
			temp = temp2 ^ outKey[i & 15] ^ temp;
			raw[offset + i] = (byte) temp;
		}

		int old = outKey[8] & 0xff;
		old |= outKey[9] << 8 & 0xff00;
		old |= outKey[10] << 0x10 & 0xff0000;
		old |= outKey[11] << 0x18 & 0xff000000;

		old += size;

		outKey[8] = (byte) (old & 0xff);
		outKey[9] = (byte) (old >> 0x08 & 0xff);
		outKey[10] = (byte) (old >> 0x10 & 0xff);
		outKey[11] = (byte) (old >> 0x18 & 0xff);

		return true;
	}
}