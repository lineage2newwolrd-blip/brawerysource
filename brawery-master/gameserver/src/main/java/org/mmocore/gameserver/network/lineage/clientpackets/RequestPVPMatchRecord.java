package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.impl.UndergroundColiseumBattleEvent;
import org.mmocore.gameserver.network.lineage.serverpackets.ExPVPMatchRecord;
import org.mmocore.gameserver.object.Player;

/**
 * @author VISTALL
 */
public class RequestPVPMatchRecord extends L2GameClientPacket {
    @Override
    protected void readImpl() {
    }

    @Override
    protected void runImpl() {
            Player activeChar = getClient().getActiveChar();
        if (activeChar == null) {
            return;
        }
        if (activeChar.isInFightClub())
        {
            FightClubPlayer fPlayer = activeChar.getFightClubEvent().getFightClubPlayer(activeChar);
            fPlayer.setShowRank(true);
        }
        else {
            final UndergroundColiseumBattleEvent battleEvent = activeChar.getEvent(UndergroundColiseumBattleEvent.class);
            if (battleEvent == null) {
                return;
            }

            activeChar.sendPacket(new ExPVPMatchRecord(ExPVPMatchRecord.UPDATE, TeamType.NONE, battleEvent));
        }
    }
}