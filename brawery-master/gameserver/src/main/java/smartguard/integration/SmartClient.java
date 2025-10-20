package smartguard.integration;

import org.mmocore.gameserver.network.lineage.GameClient;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.NpcHtmlMessage;
import org.mmocore.gameserver.network.lineage.serverpackets.ServerClose;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import smartguard.api.entity.IHwidEntity;
import smartguard.api.integration.AbstractSmartClient;
import smartguard.api.integration.ISmartPlayer;
import smartguard.api.network.NetworkStatus;

import java.nio.ByteBuffer;

public class SmartClient extends AbstractSmartClient {

	private GameClient client;

	public SmartClient(GameClient client) {
		this.client = client;
	}

	@Override
	public String getAccountName() {
		return client.getLogin();
	}

	@Override
	public void closeConnection(boolean sendServerClose, boolean defer) {
		if (sendServerClose)
			client.close(ServerClose.STATIC);
		else if (defer)
			client.closeLater();
		else
			client.closeNow(true);
	}

	@Override
	public NetworkStatus getConnectionStatus() {
		if (client.getConnection() == null || client.getConnection().getSocket() == null)
			return NetworkStatus.DISCONNECTED;

		switch (client.getState()) {
			case CONNECTED:
			case AUTHED:
				return NetworkStatus.CONNECTED;
			case IN_GAME:
				return NetworkStatus.IN_GAME;
			default:
				return NetworkStatus.DISCONNECTED;
		}
	}

	@Override
	public void setHwid(IHwidEntity iHwidEntity) {
		client.setHWID(iHwidEntity.getPlain());
	}

	@Override
	public void sendRawPacket(ByteBuffer byteBuffer) {
		client.sendPacket(new RawPacket(byteBuffer));
	}

	@Override
	public void closeWithRawPacket(ByteBuffer byteBuffer) {
		client.close(new RawPacket(byteBuffer));
	}

	@Override
	public void sendHtml(String s) {
		NpcHtmlMessage html = new NpcHtmlMessage(5, s);
		client.sendPacket(html);
	}

	@Override
	public void sendMessage(String s) {
		client.sendPacket(new SystemMessage(SystemMsg.S1).addString(s));
	}

	@Override
	public String getIpAddr() {
		return client.getConnection().getSocket().getInetAddress().getHostAddress();
	}

	@Override
	public Object getNativeClient() {
		return client;
	}

	@Override
	public ISmartPlayer getPlayer() {
		if (client.getActiveChar() == null)
			return null;

		return new SmartPlayer(client.getActiveChar(), this);
	}
}
