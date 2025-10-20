package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.ProtectType;
import org.mmocore.gameserver.configuration.config.ServerConfig;
import org.mmocore.gameserver.configuration.config.protection.BaseProtectSetting;
import org.mmocore.gameserver.network.lineage.GameClient;
import org.mmocore.gameserver.network.lineage.serverpackets.SendStatus;
import org.mmocore.gameserver.network.lineage.serverpackets.ServerClose;
import org.mmocore.gameserver.network.lineage.serverpackets.VersionCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.strixplatform.StrixPlatform;
import org.strixplatform.logging.Log;
import org.strixplatform.managers.ClientGameSessionManager;
import org.strixplatform.managers.ClientProtocolDataManager;
import org.strixplatform.utils.StrixClientData;
import smartguard.core.properties.GuardProperties;

/**
 * packet type id 0x0E
 * format:	cdbd
 */
public class ProtocolVersion extends L2GameClientPacket {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolVersion.class);
	private static final short BasePacketSize = 4 + 256;
	private int version;
	//Protection section
	private byte[] data;
	//Protection section end
	private boolean hasExtraData = false;
	private int dataChecksum;

	@Override
	protected void readImpl() {
		version = readD();
		if (BaseProtectSetting.protectType == ProtectType.SMART && GuardProperties.ProtectionEnabled && _buf.remaining() >= BasePacketSize + 2) {
			_buf.position(_buf.position() + BasePacketSize);
			int dataLen = readH();
			if (_buf.remaining() >= dataLen) {
				hasExtraData = true;
			}
		} else if (BaseProtectSetting.protectType == ProtectType.STRIX && StrixPlatform.getInstance().isPlatformEnabled()) {
			try {
				if (_buf.remaining() >= StrixPlatform.getInstance().getProtocolVersionDataSize()) {
					data = new byte[StrixPlatform.getInstance().getClientDataSize()];
					readB(data);
					dataChecksum = readD();
				}
			} catch (final Exception e) {
				Log.error("Client [IP=" + getClient().getIpAddr() + "] used unprotected client. Disconnect...");
				getClient().close(new VersionCheck(null));
			}
		}
	}

	@Override
	protected void runImpl() {
		GameClient client = getClient();
		if (version == -2) {
			_client.closeNow(false);
			return;
		} else if (version == -3) {
			LOGGER.info("Status request from IP : " + getClient().getIpAddr());
			client.close(new SendStatus());
			return;
		} else if (version < ServerConfig.MIN_PROTOCOL_REVISION || version > ServerConfig.MAX_PROTOCOL_REVISION) {
			LOGGER.warn("Unknown protocol revision : " + version + ", client : " + _client);
			client.close(new VersionCheck(null));
			return;
		}

		//Protection section
		if (BaseProtectSetting.protectType == ProtectType.SMART && GuardProperties.ProtectionEnabled) {
			if (!hasExtraData) {
				// если нету доп. данных - попытка входа без защиты
				client.close(ServerClose.STATIC);
				return;
			}

		} else if (BaseProtectSetting.protectType == ProtectType.STRIX && StrixPlatform.getInstance().isPlatformEnabled()) {
			if (data == null) {
				Log.error("Client [IP=" + client.getIpAddr() + "] used unprotected client. Disconnect...");
				client.close(new VersionCheck(null));
				return;
			} else {
				final StrixClientData clientData = ClientProtocolDataManager.getInstance().getDecodedData(data, dataChecksum);
				if (clientData == null) {
					Log.error("Decode client data failed. See Strix-Platform log file. Disconected client " + client.getIpAddr());
					client.close(new VersionCheck(null));
					return;
				}
				if (!ClientGameSessionManager.getInstance().checkServerResponse(clientData)) {
					client.close(new VersionCheck(null, clientData));
					return;
				}
				client.setStrixClientData(clientData);
				client.setHWID(clientData.getClientHWID());
			}
		}
		//Protection section end
		client.setRevision(version);
		client.setChronicle(ServerConfig.CHRONICLE_VERSION);
		client.sendPacket(new VersionCheck(client.enableCrypt()));
	}
}