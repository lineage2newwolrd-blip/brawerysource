package org.mmocore.gameserver.scripts.npc.model.events.fightClub;

import org.mmocore.gameserver.ai.CtrlIntention;
import org.mmocore.gameserver.network.lineage.serverpackets.Moving.ValidateLocation;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.entity.events.impl.fightclub.FFATreasureHuntEvent;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.ActionFail;
import org.mmocore.gameserver.network.lineage.serverpackets.MyTargetSelected;
import org.mmocore.gameserver.network.lineage.serverpackets.StatusUpdate;
import org.mmocore.gameserver.templates.npc.NpcTemplate;

public class TreasureChestInstance extends NpcInstance
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9222305066612059039L;

	public TreasureChestInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void onAction(Player player, boolean shift)
	{
		if (!isTargetable())
		{
			player.sendActionFailed();
			return;
		}

		if (player.getTarget() == null || !player.getTarget().equals(this))
		{
			player.setTarget(this);
			player.sendPacket(new MyTargetSelected(getObjectId(), player.getLevel() - getLevel()), makeStatusUpdate(StatusUpdate.CUR_HP, StatusUpdate.MAX_HP));
			player.sendPacket(new ValidateLocation(this), ActionFail.STATIC);
			return;
		}

		if (!isInRange(player, (long) INTERACTION_DISTANCE))
		{
			if (player.getAI().getIntention() != CtrlIntention.AI_INTENTION_INTERACT)
				player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this, null);
			return;
		}
		
		if (player.isSitting() || player.isAlikeDead())
			return;

		player.sendActionFailed();
		player.stopMove(false);
		
		if (player.isInFightClub())
		{
			boolean shouldDisappear = false;
			if (player.getFightClubEvent() instanceof FFATreasureHuntEvent)
				shouldDisappear = ((FFATreasureHuntEvent)player.getFightClubEvent()).openTreasure(player, this);
			
			if (shouldDisappear)
				deleteMe();
		}
	}
}
