package org.mmocore.gameserver.scripts.npc.model.events.fightClub;

import org.mmocore.gameserver.ai.CtrlIntention;
import org.mmocore.gameserver.network.lineage.serverpackets.Moving.ValidateLocation;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.entity.events.impl.fightclub.CaptureTheFlagEvent;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.ActionFail;
import org.mmocore.gameserver.network.lineage.serverpackets.MyTargetSelected;
import org.mmocore.gameserver.network.lineage.serverpackets.StatusUpdate;
import org.mmocore.gameserver.templates.npc.NpcTemplate;

public class FlagHolderInstance extends NpcInstance
{
	private static final long serialVersionUID = -2427852972704724529L;

	public FlagHolderInstance(int objectId, NpcTemplate template)
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

		if (player.getTarget() != this)
		{
			player.setTarget(this);
			if (player.getTarget() == this)
				player.sendPacket(new MyTargetSelected(getObjectId(), player.getLevel() - getLevel()), makeStatusUpdate(StatusUpdate.CUR_HP, StatusUpdate.MAX_HP));

			player.sendPacket(new ValidateLocation(this), ActionFail.STATIC);
			return;
		}

		if (!isInRange(player, INTERACTION_DISTANCE))
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
			if (player.getFightClubEvent() instanceof CaptureTheFlagEvent)
				((CaptureTheFlagEvent)player.getFightClubEvent()).talkedWithFlagHolder(player, this);
		}
	}
}
