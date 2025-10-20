package smartguard;

import org.jts.protection.network.ProtectionGameCrypt;
import org.mmocore.gameserver.database.DatabaseFactory;
import org.mmocore.gameserver.handler.admincommands.AdminCommandHandler;
import org.mmocore.gameserver.world.GameObjectsStorage;
import smartguard.api.ISmartGuardService;
import smartguard.api.integration.ISmartPlayer;
import smartguard.api.integration.IWorldService;
import smartguard.core.utils.LogUtils;
import smartguard.integration.PWDatabaseConnection;
import smartguard.integration.SmartPlayer;
import smartguard.menu.SmartGuardMenu;
import smartguard.spi.SmartGuardSPI;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SmartGuard {
	public static void main(String[] args) {
		try {
			if (!SmartGuard.class.getProtectionDomain().getCodeSource().equals(ProtectionGameCrypt.class.getProtectionDomain().getCodeSource())) {
				LogUtils.log("Error! Library [smrt-core-1.0.jar] is not first in your classpath list, SmartGuard will not work properly!");
				return;
			}
		} catch (Exception e) {
			LogUtils.log(e);
		}

		if (args.length == 0) {
			LogUtils.log("Main class not specified!");
			return;
		}

		SmartGuardSPI.setDatabaseService(() -> new PWDatabaseConnection(DatabaseFactory.getInstance().getConnection()));

		SmartGuardSPI.setWorldService(new IWorldService() {
			@Override
			public ISmartPlayer getPlayerByObjectId(int objectId) {
				return new SmartPlayer(GameObjectsStorage.getPlayer(objectId));
			}

			@Override
			public List<ISmartPlayer> getAllPlayers() {
				return GameObjectsStorage.getPlayerStream().map(SmartPlayer::new).collect(Collectors.toList());
			}
		});

		ISmartGuardService svc = SmartGuardSPI.getSmartGuardService();

		if (!svc.init()) {
			LogUtils.log("Failed to init SmartGuard!");
			return;
		}

		Runtime.getRuntime().addShutdownHook(new Thread(() -> SmartGuardSPI.getSmartGuardService().getSmartGuardBus().onShutdown()));

		try {
			LogUtils.log("SmartGuard has been initialized.");

			Class<?> c = Class.forName(args[0]);
			Method main = c.getDeclaredMethod("main", String[].class);
			String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
			main.invoke(null, (Object) mainArgs);
		} catch (Exception e) {
			LogUtils.log("GameServer failed to start!", e);
			return;
		}

		SmartGuardSPI.getSmartGuardService().getSmartGuardBus().onStartup();

		try {
			AdminCommandHandler.getInstance().registerAdminCommandHandler(new SmartGuardMenu());
		} catch (Exception e) {
			LogUtils.log("Error initializing SmartGuard AdminCommandHandler!", e);
		}
	}
}