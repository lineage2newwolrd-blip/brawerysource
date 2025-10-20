package org.mmocore.gameserver.handler.admincommands.impl;

import org.mmocore.gameserver.handler.admincommands.IAdminCommandHandler;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.world.World;
import org.strixplatform.managers.ClientBanManager;
import org.strixplatform.utils.BannedHWIDInfo;

import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 * @author iRock
 * @date 06.02.19
 */
public class StrixGuardMenu implements IAdminCommandHandler {

    @Override
    public boolean useAdminCommand(Enum<?> comm, String[] wordList, String fullString, Player activeChar) {
        final Commands command = (Commands) comm;
        final StringTokenizer st = new StringTokenizer(fullString);
        if (activeChar.getPlayerAccess().StrixGuard || activeChar.isGM()) {
            switch (command) {
                case admin_ban_hwid:
                    st.nextToken();
                    Player targetPlayer = null;
                    if(activeChar.getTarget() != null)
                    {
                        targetPlayer = activeChar.getTarget().getPlayer();
                    }
                    else
                    {
                        final String playeraName = st.nextToken();
                        targetPlayer = World.getPlayer(playeraName);
                    }

                    if(targetPlayer != null)
                    {
                        try
                        {
                            final Long time = Long.parseLong(st.nextToken());
                            final String reason = st.nextToken();
                            final BannedHWIDInfo bhi = new BannedHWIDInfo(targetPlayer.getNetConnection().getStrixClientData().getClientHWID(), (System.currentTimeMillis() + time * 60 * 1000), reason, activeChar.getName());
                            ClientBanManager.getInstance().tryToStoreBan(bhi);
                            final String bannedOut = "Player [Name:{" + targetPlayer.getName() + "}HWID:{" + targetPlayer.getNetConnection().getStrixClientData().getClientHWID() + "}] banned on [" + time + "] minutes from [" + reason + "] reason.";
                            activeChar.sendAdminMessage(bannedOut);
                            org.strixplatform.logging.Log.audit(bannedOut);
                            targetPlayer.sendMessage("You banned on [" + time + "] minutes. Reason: " + reason);
                            targetPlayer.kick();
                        }
                        catch(final Exception e)
                        {
                            if(e instanceof SQLException)
                            {
                                activeChar.sendAdminMessage("Unable to store ban in database. Please check Strix-Platform error log!");
                                org.strixplatform.logging.Log.error("Exception on GM trying store ban. Exception: " + e.getLocalizedMessage());
                            }
                            else
                            {
                                activeChar.sendAdminMessage("Command syntax: //ban_hwid PLAYER_NAME(or target) TIME(in minutes) REASON(255 max)");
                            }
                            break;
                        }
                    }
                    else
                    {
                        activeChar.sendAdminMessage("Unable to find this player.");
                    }
                    break;
                case admin_unban_hwid:
                    st.nextToken();
                    final String playeraHWID = st.nextToken();

                    if(playeraHWID != null && playeraHWID.length() == 32)
                    {
                        try
                        {
                            ClientBanManager.getInstance().tryToDeleteBan(playeraHWID);
                            activeChar.sendAdminMessage("Player unbaned and delete from database.");
                        }
                        catch(final Exception e)
                        {
                            if(e instanceof SQLException)
                            {
                                activeChar.sendAdminMessage("Unable to delete ban from database. Please check Strix-Platform error log!");
                                org.strixplatform.logging.Log.error("Exception on GM trying delete ban. Exception: " + e.getLocalizedMessage());
                            }
                            break;
                        }
                    }
                    else
                    {
                        activeChar.sendAdminMessage("Command syntax: //unban_hwid HWID_STRING(size 32)");
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public Enum<?>[] getAdminCommandEnum() {
        return Commands.values();
    }

    @Override
    public String[] getAdminCommandString() {
        return null;
    }

    private enum Commands {
        admin_ban_hwid,
        admin_unban_hwid
    }
}
