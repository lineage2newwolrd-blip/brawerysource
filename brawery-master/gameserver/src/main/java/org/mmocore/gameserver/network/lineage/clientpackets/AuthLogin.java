package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.ProtectType;
import org.mmocore.gameserver.Shutdown;
import org.mmocore.gameserver.configuration.config.ServicesConfig;
import org.mmocore.gameserver.configuration.config.protection.BaseProtectSetting;
import org.mmocore.gameserver.database.dao.impl.HwidLocksDAO;
import org.mmocore.gameserver.network.authcomm.AuthServerCommunication;
import org.mmocore.gameserver.network.authcomm.SessionKey;
import org.mmocore.gameserver.network.authcomm.gs2as.PlayerAuthRequest;
import org.mmocore.gameserver.network.lineage.GameClient;
import org.mmocore.gameserver.network.lineage.serverpackets.LoginFail;
import org.mmocore.gameserver.network.lineage.serverpackets.ServerClose;
import org.strixplatform.StrixPlatform;
import org.strixplatform.logging.Log;
import smartguard.api.ISmartGuardService;
import smartguard.api.integration.SessionData;
import smartguard.core.properties.GuardProperties;
import smartguard.integration.SmartClient;
import smartguard.spi.SmartGuardSPI;

/**
 * cSdddddQd
 * loginName + keys must match what the loginserver used.
 */
public class AuthLogin extends L2GameClientPacket {
	private String loginName;
	private int playKey1;
	private int playKey2;
	private int loginKey1;
	private int loginKey2;
	private int languageType;

	private byte[] smartData = null;

	@Override
	protected void readImpl() {
		loginName = readS(32).toLowerCase();
		playKey2 = readD();
		playKey1 = readD();
		loginKey1 = readD();
		loginKey2 = readD();
		languageType = readD();
		readQ(); // unk1
		readD(); // unk2

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
		final GameClient client = getClient();

		final SessionKey key = new SessionKey(loginKey1, loginKey2, playKey1, playKey2);
		client.setSessionId(key);
		client.setLoginName(loginName);
		client.setLanguageType(languageType);

		//Protection section
		if (BaseProtectSetting.protectType == ProtectType.SMART && GuardProperties.ProtectionEnabled) {
			if (smartData != null) {
				ASmartClient smrtclient = new ASmartClient(client, loginName);
				smrtclient.setSessionData(new SessionData(loginKey1, loginKey2, playKey1, playKey2));

				ISmartGuardService svc = SmartGuardSPI.getSmartGuardService();
				if (!svc.getSmartGuardBus().checkAuthLogin(smrtclient, smartData)) {
					smrtclient.closeLater();
					return;
				}
			} else {
				client.close(ServerClose.STATIC);
				return;
			}
		} else if (BaseProtectSetting.protectType == ProtectType.STRIX && StrixPlatform.getInstance().isPlatformEnabled()) {
			if (client.getStrixClientData() != null) {
				client.getStrixClientData().setClientAccount(loginName);
				if (StrixPlatform.getInstance().isAuthLogEnabled()) {
					Log.auth("Account: [" + loginName + "] HWID: [" + client.getStrixClientData().getClientHWID() + "] SessionID: [" + client.getStrixClientData().getSessionId() + "] entered to Game Server");
				}
			} else {
				client.close(ServerClose.STATIC);
				return;
			}
		}
		//Protection section end
		if (Shutdown.getInstance().getMode() != Shutdown.NONE && Shutdown.getInstance().getSeconds() <= 15) {
			client.closeNow(false);
		} else {
			if (AuthServerCommunication.getInstance().isShutdown()) {
				client.close(new LoginFail(LoginFail.SYSTEM_ERROR_LOGIN_LATER));
				return;
			}
			if (!checkHwidAccess(client))
				return;

			final GameClient oldClient = AuthServerCommunication.getInstance().addWaitingClient(client);
			if (oldClient != null) {
				oldClient.close(ServerClose.STATIC);
			}

			AuthServerCommunication.getInstance().sendPacket(new PlayerAuthRequest(client));
		}
	}

	private boolean checkHwidAccess(GameClient client) {
		String hwid = client.getHWID();
		if (hwid != null && !hwid.equals("") && ServicesConfig.allowHwidLock) {
			String lockedHwid = HwidLocksDAO.getInstance().getHwid(client.getLogin());
			if (lockedHwid != null && !lockedHwid.equals(hwid)) {
				client.close(new LoginFail(LoginFail.INCORRECT_ACCOUNT_INFO_CONTACT_CUSTOMER_SUPPORT));
				return false;
			}
		}
		return true;
	}

	private static class ASmartClient extends SmartClient {
		private final String _accountName;

		public ASmartClient(GameClient client, String accountName) {
			super(client);
			_accountName = accountName;
		}

		@Override
		public String getAccountName() {
			return _accountName;
		}
	}

}