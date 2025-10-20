package org.mmocore.gameserver.scripts.npc.model.events;

import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.entity.events.impl.AbstractCustomBattleEvent;
import org.mmocore.gameserver.model.entity.events.impl.AbstractCustomObservableEvent;
import org.mmocore.gameserver.model.entity.events.impl.SingleMatchEvent;
import org.mmocore.gameserver.model.entity.events.impl.TournamentGvGBattleEvent;
import org.mmocore.gameserver.model.entity.olympiad.Olympiad;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.clientpackets.L2GameClientPacket;
import org.mmocore.gameserver.network.lineage.clientpackets.RequestBypassToServer;
import org.mmocore.gameserver.network.lineage.clientpackets.RequestOlympiadMatchList;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.network.lineage.components.HtmlMessage;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ExReceiveOlympiad;
import org.mmocore.gameserver.object.ObservePoint;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.templates.npc.NpcTemplate;
import org.mmocore.gameserver.utils.ChatUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomObservationManagerInstance extends NpcInstance
{
	private static Lock _lock = new ReentrantLock();
	private static volatile int _numOfBattles = 0;
	private static final AbstractCustomObservableEvent[] BATTLES = new AbstractCustomObservableEvent[99];
	private static final List<NpcInstance> BROADCASTERS = new CopyOnWriteArrayList<NpcInstance>();

	public CustomObservationManagerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
		setTitle("Event");
	}

	public static int registerBattle(AbstractCustomObservableEvent battle)
	{
		_lock.lock();
		try
		{
			for (int i = 0; i < BATTLES.length; i++)
				if (BATTLES[i] == null)
				{
					BATTLES[i] = battle;
					_numOfBattles++;
					return i;
				}
		}
		finally
		{
			_lock.unlock();
		}

		return -1;
	}

	public static void unRegisterBattle(AbstractCustomObservableEvent battle)
	{
		_lock.lock();
		try
		{
			for (int i = 0; i < BATTLES.length; i++)
				if (BATTLES[i] == battle)
				{
					BATTLES[i] = null;
					_numOfBattles--;
					return;
				}
		}
		finally
		{
			_lock.unlock();
		}
	}

	public static void broadcast(CustomMessage cm)
	{
		for (NpcInstance n : BROADCASTERS)
			ChatUtils.shout(n, cm);
	}

	private static void showMatchList(Player player)
	{
		if (_numOfBattles > 0)
		{
			player.sendPacket(new ExReceiveOlympiad.MatchList(BATTLES));
		} else {
			player.sendMessage(new CustomMessage("CustomBattleEvent.NoGames"));
		}
	}

	private static void addSpectator(int arenaId, Player player)
	{
		if(player.isInOlympiadMode() || Olympiad.isRegisteredInComp(player))
		{
			player.sendPacket(SystemMsg.YOU_MAY_NOT_OBSERVE_A_GRAND_OLYMPIAD_GAMES_MATCH_WHILE_YOU_ARE_ON_THE_WAITING_LIST);
			return;
		}

		if (player.isInCombat() || player.getPvpFlag() > 0 || player.getEvent(SingleMatchEvent.class) != null)
		{
			player.sendPacket(SystemMsg.YOU_CANNOT_OBSERVE_WHILE_YOU_ARE_IN_COMBAT);
			return;			
		}

		if (_numOfBattles <= 0)
			return;

		if (player.getServitor() != null)
			player.getServitor().unSummon(false, false);

		final ObservePoint op = player.getObservePoint();
		if (op != null)
			for (AbstractCustomObservableEvent e : BATTLES)
				if (e != null && e.getReflection() == op.getReflection())
				{
					e.removeObserver(player);
					break;
				}

		for (AbstractCustomObservableEvent e : BATTLES)
			if (e != null && e.getArenaId() == arenaId)
			{
				final Reflection r = e.getReflection();
				if (r != null && !r.isCollapseStarted() && e.getObserverCoords() != null && (e instanceof AbstractCustomBattleEvent))
				{
					player.enterMatchObserverMode(e.getObserverCoords(), r, e, 2);
					e.addObserver(player);
				}
				else if (r == null && e.getObserverCoords() != null && e instanceof TournamentGvGBattleEvent)
				{
					e.stopEvent();
				}
				return;
			}
	}

	@Override
	protected void onSpawn()
	{
		super.onSpawn();
		BROADCASTERS.add(this);
	}

	@Override
	protected void onDecay()
	{
		super.onDecay();
		BROADCASTERS.remove(this);
	}

	@Override
	public void onBypassFeedback(Player player, String command)
	{
		if(checkForDominionWard(player))
			return;

		// до всех проверок
		if (command.startsWith("_olympiad?")) // _olympiad?command=move_op_field&field=1
		{
			String[] ar = command.split("&");
			if (ar.length < 2)
				return;

			if (ar[0].equalsIgnoreCase("_olympiad?command=move_op_field"))
			{
				String[] command2 = ar[1].split("=");
				if (command2.length < 2)
					return;

				addSpectator(Integer.parseInt(command2[1]) - 1, player);
			}
			return;
		}

		if(!canBypassCheck(player, this))
			return;

		if(command.startsWith("MatchList"))
			showMatchList(player);
		else
			super.onBypassFeedback(player, command);
	}

	@Override
	public void showChatWindow(Player player, int val, Object... ar)
	{
		if(checkForDominionWard(player))
			return;

		String fileName;
		if(val > 0)
			fileName = getTemplate().getHtmRoot() + "manager-" + val + ".htm";
		else
			fileName = getTemplate().getHtmRoot() + "manager.htm";

		player.sendPacket(new HtmlMessage(this, fileName, val));
	}

	@Override
	public boolean altPassPacket(Player player, Class<? extends L2GameClientPacket> packet, Object... arg)
	{
		if (packet == RequestOlympiadMatchList.class)
		{
			showMatchList(player);
			return true;
		}

		return packet == RequestBypassToServer.class && arg.length == 1 && arg[0].equals("_olympiad?command=move_op_field");
	}
}