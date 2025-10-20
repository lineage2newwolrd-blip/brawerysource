package org.mmocore.gameserver.network.lineage.clientpackets;

import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.object.Player;

public class RequestOustPartyMember extends L2GameClientPacket {
    //Format: cS
    private String _name;

    @Override
    protected void readImpl() {
        _name = readS(16);
    }

    @Override
    protected void runImpl() {
        final Player activeChar = getClient().getActiveChar();
        if (activeChar == null) {
            return;
        }

        final Party party = activeChar.getParty();
        if (party == null || !activeChar.getParty().isLeader(activeChar)) {
            activeChar.sendActionFailed();
            return;
        }

        if (activeChar.isInOlympiadMode()) {
            activeChar.sendMessage(new CustomMessage("org.mmocore.gameserver.clientpackets.RequestOustPartyMember.Olympiad"));
            return;
        }

        final Player member = party.getPlayerByName(_name);

        if (member == activeChar) {
            activeChar.sendActionFailed();
            return;
        }

        if (member == null) {
            activeChar.sendActionFailed();
            return;
        }

        party.removePartyMember(member, true);
    }
}