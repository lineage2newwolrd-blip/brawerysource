package org.mmocore.gameserver.network.xmlrpc.model;

public class ServerRates
{
	private final double xp, sp;
	private final double questDrop, questReward;
	private final double drop, spoil;
	private final double raid;

	public ServerRates(double xp, double sp, double questDrop, double questReward, double drop, double spoil, double raid)
	{
		this.xp = xp;
		this.sp = sp;
		this.questDrop = questDrop;
		this.questReward = questReward;
		this.drop = drop;
		this.spoil = spoil;
		this.raid = raid;
	}
}