package org.mmocore.gameserver.object.components.player.custom;

import org.mmocore.commons.lang.reference.HardReference;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.object.Player;

/**
 * @author iRock
 * @date 17:29/13.01.2019
 */
public class PvPRewardTask extends RunnableImpl
{
	private HardReference<Player> playerRef;
	private HardReference<Player> killerRef;

	public PvPRewardTask(Player player, Player killer)
	{
		playerRef = player.getRef();
		killerRef = killer.getRef();
	}

	@Override
	public void runImpl() throws Exception
	{
		Player player = playerRef.get();
		if(player == null)
			return;
		player.getPvpComponent().stopBlockReward();

		player = killerRef.get();
		if(player == null)
			return;
		player.getPvpComponent().removeVictim(player);
	}
}
