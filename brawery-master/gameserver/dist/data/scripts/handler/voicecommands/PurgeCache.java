package handler.voicecommands;

import org.mmocore.gameserver.handler.voicecommands.IVoicedCommandHandler;
import org.mmocore.gameserver.handler.voicecommands.VoicedCommandHandler;
import org.mmocore.gameserver.listener.script.OnInitScriptListener;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.variables.PlayerVariables;

public class PurgeCache implements IVoicedCommandHandler, OnInitScriptListener
{
	private static final String[] COMMANDS_LIST = new String[] { "cache" };
	@Override
	public boolean useVoicedCommand(String command, Player player, String args)
	{
		if(player.getPlayerVariables().getLong(PlayerVariables.CACHE_TIMEOUT) < System.currentTimeMillis()) {
			player.getPlayerVariables().set(PlayerVariables.CACHE_TIMEOUT, System.currentTimeMillis() + 60000L, System.currentTimeMillis() + 60000L);
			player.purgeCache();
			return true;
		} else{
			player.sendPacket(SystemMsg.THIS_FUNCTION_IS_INACCESSIBLE_RIGHT_NOW);
		}
		return false;
	}

    @Override
    public void onInit()
    {
        VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
    }

	@Override
	public String[] getVoicedCommandList()
	{
		return COMMANDS_LIST;
	}
}
