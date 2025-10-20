package smartguard.menu;

import org.mmocore.gameserver.handler.admincommands.IAdminCommandHandler;
import org.mmocore.gameserver.object.Player;
import smartguard.integration.SmartPlayer;
import smartguard.spi.SmartGuardSPI;

public class SmartGuardMenu implements IAdminCommandHandler {

	@Override
	public boolean useAdminCommand(Enum<?> comm, String[] wordList, String fullString, Player activeChar) {
		try {
			if (!activeChar.getPlayerAccess().SmartGuard || !activeChar.isGM())
				return false;
			String[] strings = fullString.split(" ");

			SmartGuardSPI.getSmartGuardService().getGmCommandProcessor().handle(new SmartPlayer(activeChar), strings[0], strings);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Enum<?>[] getAdminCommandEnum() {
		return SmartGuardSPI.getSmartGuardService().getGmCommandProcessor().getEnumCommands();
	}

	@Override
	public String[] getAdminCommandString() {
		return SmartGuardSPI.getSmartGuardService().getGmCommandProcessor().getCommands();
	}
}
