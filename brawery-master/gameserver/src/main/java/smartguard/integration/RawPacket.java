package smartguard.integration;

import org.mmocore.gameserver.network.lineage.serverpackets.L2GameServerPacket;

import java.nio.ByteBuffer;

public class RawPacket extends L2GameServerPacket {
	byte[] data;

	public RawPacket(ByteBuffer byteBuffer) {
		data = new byte[byteBuffer.remaining()];
		byteBuffer.get(data, 0, data.length);
	}

	@Override
	protected void writeImpl() {
		writeB(data);
	}
}
