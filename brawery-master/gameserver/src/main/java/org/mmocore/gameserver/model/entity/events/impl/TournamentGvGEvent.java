package org.mmocore.gameserver.model.entity.events.impl;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.data.xml.holder.EventHolder;
import org.mmocore.gameserver.handler.admincommands.AdminCommandHandler;
import org.mmocore.gameserver.handler.admincommands.IAdminCommandHandler;
import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.objects.CustomPlayerSnapshotObject;
import org.mmocore.gameserver.model.entity.olympiad.Olympiad;
import org.mmocore.gameserver.model.team.Party;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchManage;
import org.mmocore.gameserver.network.lineage.serverpackets.ExEventMatchTeamUnlocked;
import org.mmocore.gameserver.network.lineage.serverpackets.SystemMessage;
import org.mmocore.gameserver.object.ObservePoint;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.Strings;
import org.mmocore.gameserver.world.GameObjectsStorage;

import java.util.List;

public class TournamentGvGEvent extends AbstractCustomStarterEvent
{
	private static int _id = 0; // TODO: List
	private static EventCommandHandler voiceHandler = null;

	private static final class EventCommandHandler implements IAdminCommandHandler
	{
		enum Commands
		{
			admin_eventmatcharena,
			admin_eventmatchview,
			admin_eventmatch
		}

		@Override
		public boolean useAdminCommand(Enum<?> comm, String[] wordList, String fullString, Player activeChar)
		{
			final TournamentGvGEvent event = EventHolder.getInstance().getEvent(EventType.PVP_EVENT, _id);
			if (event == null)
				return false;

			Commands c = (Commands)comm;
			final TournamentGvGBattleEvent battleEvent;
			switch(c)
			{
				case admin_eventmatcharena:
					if(wordList.length < 2)
					{
						activeChar.sendMessage("USAGE://" + wordList[0] + " [arena_id]");
						return false;
					}
					try {
						int arena = Integer.parseInt(wordList[1]);
						event.switchArena(activeChar, arena);
						battleEvent = activeChar.getEvent(TournamentGvGBattleEvent.class);
						event.battleMenu(activeChar, battleEvent);
						return true;
					}
					catch (NumberFormatException e) {
						activeChar.sendPacket(new CustomMessage("CustomBattleEvent.InvalidArena"));
						return false;
					}
				case admin_eventmatchview:
					if(wordList.length < 2)
					{
						activeChar.sendMessage("USAGE://" + wordList[0] + " [player]");
						return false;
					}
					final Player player = GameObjectsStorage.getPlayer(wordList[1]);
					if(player == null){
						activeChar.sendMessage("ERROR://Player not found");
						return false;
					}
					if(player.getObservableEvent() == activeChar.getEvent(TournamentGvGBattleEvent.class)) {
						player.leaveMatchObserverMode();
						return true;
					}
					return event.addObserver(activeChar, player);
				case admin_eventmatch:
					if(wordList.length < 2)
					{
						battleEvent = activeChar.getEvent(TournamentGvGBattleEvent.class);
						event.battleMenu(activeChar, battleEvent);
						return true;
					}
					if(wordList[1].equals("create"))
					{
						if(wordList.length < 5)
						{
							activeChar.sendMessage("USAGE://" + wordList[0] +" " + wordList[1] + " [arena_id] [blue_name] [red_name]");
							return false;
						}
						if(wordList.length < 10)
						try {
							int arena = Integer.parseInt(wordList[2]);
							event.createEvent(activeChar, wordList[3], wordList[4], arena);
							battleEvent = activeChar.getEvent(TournamentGvGBattleEvent.class);
							event.battleMenu(activeChar, battleEvent);
							return true;
						} catch (NumberFormatException e) {
							activeChar.sendPacket(new CustomMessage("CustomBattleEvent.InvalidArena"));
							return false;
						}

						event.createEvent(activeChar, wordList[3], wordList[4], 0);
						battleEvent = activeChar.getEvent(TournamentGvGBattleEvent.class);
						event.battleMenu(activeChar, battleEvent);
						return true;
					}

					battleEvent = activeChar.getEvent(TournamentGvGBattleEvent.class);

					if(battleEvent==null){
						activeChar.sendMessage("ERROR://Event not found");
						return false;
					}

					if(wordList[1].equals("remove"))
					{
						battleEvent.stopEvent();
						event.battleMenu(activeChar, null);
						return true;
					}

					if(wordList[1].equals("start"))
					{
						if (activeChar.isInMatch() && battleEvent.isPaused())
						{
							battleEvent.pause(false);
							return true;
						}

						if (activeChar.isInMatch() && battleEvent.isInProgress())
						{
							battleEvent.startNextRound(true, true);
							battleEvent.teleportPlayers("participants", null);
							battleEvent.reCalcNextTime(false);
							return true;
						}

						battleEvent.startNextRound(false, false);
						battleEvent.teleportPlayers("to_arena", null);
						battleEvent.reCalcNextTime(false);
						return true;

					}
					if(wordList[1].equals("end"))
					{
						battleEvent.chooseWinner();
						return true;
					}
					if(wordList[1].equals("pause"))
					{
						battleEvent.pause(true);
						return true;
					}
					if(wordList[1].equals("leader"))
					{
						if (wordList.length < 5)
						{
							activeChar.sendPacket(SystemMsg.THERE_IS_NO_OPPONENT_TO_RECEIVE_YOUR_CHALLENGE_FOR_A_DUEL);
							return false;
						}
						int teamId = Integer.parseInt(wordList[3]);
						boolean success = event.setParty(activeChar, battleEvent, teamId, wordList[4]);
						event.battleMenu(activeChar, battleEvent);
						return success;
					}
					if(wordList[1].equals("unlock"))
					{
						int teamId = Integer.parseInt(wordList[3]);
						TeamType team = TeamType.values()[teamId];
						battleEvent.removeObjects(team);
						activeChar.sendPacket(new ExEventMatchTeamUnlocked(teamId));
						return true;
					}
					if(wordList[1].equals("score"))
					{
						try {
							int blue = Integer.parseInt(wordList[3]);
							int red = Integer.parseInt(wordList[4]);
							battleEvent.setScore(blue, red);
							return true;
						} catch (NumberFormatException e) {
							activeChar.sendMessage("Invalid score result");
							return false;
						}
					}
					if(wordList[1].equals("msg"))
					{
						if(wordList.length < 6)
						{
							return false;
						}
						battleEvent.message(Strings.joinStrings(" ", wordList,4,-1).replace("\"", ""));
						return true;
					}
					if(wordList[1].equals("firecracker"))
					{
						if (wordList.length < 3)
						{
							return false;
						}
						battleEvent.handleFirecracker(activeChar, wordList[2]);
						return true;
					}
					if(wordList[1].equals("fence"))
					{
						if (wordList.length < 4)
						{
							return false;
						}
						try {
							int state = Integer.parseInt(wordList[3]);
							if(state == 1)
								battleEvent.fenceDown();
							else if(state == 2)
								battleEvent.fenceUp();
							return true;
						} catch (NumberFormatException e) {
							activeChar.sendMessage("Invalid fence request");
							return false;
						}
					}
					if(wordList[1].equals("lock")||wordList[1].equals("skill_rule")||wordList[1].equals("item_rule")||wordList[1].equals("dispelall"))
					{
						return true;
					}

					activeChar.sendMessage("ERROR://" + wordList[0] + " " + wordList[1] + " not found this command");
					break;


				default:
					activeChar.sendMessage("ERROR://" + wordList[0] + " not found any command");
			}
			return false;
		}

		@Override
		public Enum[] getAdminCommandEnum()
		{
			return Commands.values();
		}

		@Override
		public String[] getAdminCommandString() {
			return null;
		}
	}

	private void battleMenu(Player activeChar, TournamentGvGBattleEvent event){
		if(event==null){
			activeChar.sendPacket(new ExEventMatchManage());
			return;
		}
		final ExEventMatchManage packet = new ExEventMatchManage(event.getMenuState(), event.getTeamName1(), event.getMenuTeamState(TeamType.BLUE), event.getTeamName2(), event.getMenuTeamState(TeamType.RED), getArenaId(event.getId()));
		event.setMenuPlayers(packet);
		activeChar.sendPacket(packet);
	}

	private boolean setParty(Player activeChar, TournamentGvGBattleEvent event, int teamId, String player)
	{
		if(event==null){
			activeChar.sendMessage("ERROR://Event not found");
			return false;
		}

		final TeamType team = TeamType.values()[teamId];
		final Player leader = GameObjectsStorage.getPlayer(player);
		if (leader == null)
		{
			activeChar.sendPacket(SystemMsg.THERE_IS_NO_OPPONENT_TO_RECEIVE_YOUR_CHALLENGE_FOR_A_DUEL);
			return false;
		}

		if (leader.isBusy())
		{
			activeChar.sendPacket(new SystemMessage(SystemMsg.C1_IS_ON_ANOTHER_TASK).addName(leader));
			return false;
		}

		final Party party = leader.getParty();
		if (party == null)
		{
			addParticipant(event, leader, team, 1);
			return true;
		}

		for (Player member : party)
			addParticipant(event, member, team, member.equals(leader)? 1 : 0);

		return true;
	}

	private boolean addObserver(Player activeChar, Player spectator){

		if(spectator.isInOlympiadMode() || Olympiad.isRegisteredInComp(spectator))
		{
			activeChar.sendPacket(SystemMsg.YOU_MAY_NOT_OBSERVE_A_GRAND_OLYMPIAD_GAMES_MATCH_WHILE_YOU_ARE_ON_THE_WAITING_LIST);
			return false;
		}

		if (spectator.isInCombat() || spectator.getPvpFlag() > 0 || spectator.getEvent(SingleMatchEvent.class) != null)
		{
			activeChar.sendPacket(SystemMsg.YOU_CANNOT_OBSERVE_WHILE_YOU_ARE_IN_COMBAT);
			return false;
		}

		final TournamentGvGBattleEvent event = activeChar.getEvent(TournamentGvGBattleEvent.class);
		if(event == null){
			activeChar.sendMessage("ERROR://Event not found");
			return false;
		}

		if (spectator.getServitor() != null)
			spectator.getServitor().unSummon(false, false);

		final ObservePoint op = spectator.getObservePoint();
		if (op != null) {
			final AbstractCustomObservableEvent e = spectator.getEvent(AbstractCustomObservableEvent.class);
			if (e != null && e.getReflection() == op.getReflection())
				e.removeObserver(spectator);
		}

		final Reflection r = event.getReflection();
		if (r != null && !r.isCollapseStarted() && event.getObserverCoords() != null)
		{
			spectator.enterMatchObserverMode(event.getObserverCoords(), r, event, 1);
			event.addObserver(spectator);
			return true;
		}
		return false;

	}

	private boolean createEvent(Player activeChar, String blueName, String redName, int arenaId){

		AbstractCustomBattleEvent abstractEvent = getBattleEvent(activeChar, activeChar, arenaId);
		if (abstractEvent == null){
			activeChar.sendPacket(new CustomMessage("CustomBattleEvent.InvalidArena"));
			return false;
		}

		final AbstractCustomBattleEvent event = activeChar.getEvent(TournamentGvGBattleEvent.class);
		if(event!=null){
			activeChar.sendMessage("ERROR://Event already created");
			return false;
		}
		final TournamentGvGBattleEvent newEvent = new TournamentGvGBattleEvent(abstractEvent.getId(), this, blueName, redName);
		addParticipant(newEvent, activeChar, TeamType.NONE, 0);
		return true;

	}

	private boolean switchArena(Player activeChar, int arenaId){

		AbstractCustomBattleEvent abstractEvent = getBattleEvent(activeChar, activeChar, arenaId);
		if (abstractEvent == null){
			activeChar.sendPacket(new CustomMessage("CustomBattleEvent.InvalidArena"));
			return false;
		}

		final AbstractCustomBattleEvent event = activeChar.getEvent(TournamentGvGBattleEvent.class);
		if(event==null){
			activeChar.sendMessage("ERROR://Currently Event not found");
			return false;
		}
		if(arenaId == getArenaId(event.getId())){
			activeChar.sendMessage("ERROR://Selected arena already entered");
			return false;
		}

		try {

			final TournamentGvGBattleEvent newEvent = new TournamentGvGBattleEvent(abstractEvent.getId(), this, event.getTeamName1(), event.getTeamName2());
			List<CustomPlayerSnapshotObject> team;

			team = event.getObjects(TeamType.NONE);
			for (CustomPlayerSnapshotObject member : team)
				updateParticipant(event, newEvent, member);

			team = event.getObjects(TeamType.BLUE);
			for (CustomPlayerSnapshotObject member : team)
				updateParticipant(event, newEvent, member);

			team = event.getObjects(TeamType.RED);
			for (CustomPlayerSnapshotObject member : team)
				updateParticipant(event, newEvent, member);

			newEvent.startNextRound(false, true);
			newEvent.teleportPlayers("new_arena", null);

			event._state = AbstractCustomBattleEvent.State.STARTED;
			event.stopEvent();

			newEvent.reCalcNextTime(false);
		}
		catch(Exception e){
			activeChar.sendMessage("ERROR://Cannot change arena");
			return false;
		}
		return true;

	}

	public TournamentGvGEvent(MultiValueSet<String> set)
	{
		super(set);

		if (voiceHandler == null)
		{
			voiceHandler = new EventCommandHandler();
			AdminCommandHandler.getInstance().registerAdminCommandHandler(voiceHandler);
		}
		_id = getId();
	}

	@Override
	public boolean canDuel(Player player1, Player player2, boolean first)
	{return false;}

	@Override
	public void askDuel(Player player, Player targetPlayer, int arenaId)
	{}

	@Override
	public void createDuel(Player player1, Player player2, int arenaId)
	{}
}