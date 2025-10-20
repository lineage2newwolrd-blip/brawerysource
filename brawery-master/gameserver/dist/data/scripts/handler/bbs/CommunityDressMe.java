package handler.bbs;

import org.mmocore.gameserver.network.lineage.components.HtmlMessage;
import org.mmocore.gameserver.object.Player;

/**
 * 
 * Date: 21.05.2017 23:10
 */
public class CommunityDressMe extends ScriptBbsHandler {

    @Override
    public String[] getBypassCommands() {
        return new String[] { "_bbsdressme" };
    }

    @Override
    public void onBypassCommand(Player player, String bypass) {
        if(bypass.startsWith("_bbsdressme")) {
            HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/dressme.htm");
            player.sendPacket(html);
        }
    }

    @Override
    public void onWriteCommand(Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5) {}
}
