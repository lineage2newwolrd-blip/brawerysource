package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.impl.KrateisCubeEvent;
import org.mmocore.gameserver.object.Player;

/**
 * @author VISTALL
 */
public class RequestExStartShowCrataeCubeRank extends L2GameClientPacket {
    @Override
    protected void readImpl() {
        //
    }

    @Override
    protected void runImpl() {
        final Player player = getClient().getActiveChar();
        if (player == null) {
            return;
        }

        if (player.isInFightClub())
        {
            FightClubPlayer fPlayer = player.getFightClubEvent().getFightClubPlayer(player);
            fPlayer.setShowRank(true);
        }
        else
        {
            final KrateisCubeEvent cubeEvent = player.getEvent(KrateisCubeEvent.class);
            if (cubeEvent == null) {
                return;
            }

            cubeEvent.showRank(player);
        }
    }
}