package org.mmocore.gameserver.model.entity.events.fightclubmanager;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.lang.ArrayUtils;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.EventsConfig;
import org.mmocore.gameserver.data.htm.HtmCache;
import org.mmocore.gameserver.data.xml.holder.EventHolder;
import org.mmocore.gameserver.listener.actor.player.OnAnswerListener;
import org.mmocore.gameserver.model.base.ClassId;
import org.mmocore.gameserver.model.entity.events.Event;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.model.entity.events.impl.DuelEvent;
import org.mmocore.gameserver.model.entity.olympiad.Olympiad;
import org.mmocore.gameserver.network.lineage.components.ChatType;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ConfirmDlg;
import org.mmocore.gameserver.network.lineage.serverpackets.Say2;
import org.mmocore.gameserver.network.lineage.serverpackets.TutorialCloseHtml;
import org.mmocore.gameserver.network.lineage.serverpackets.TutorialShowHtml;
import org.mmocore.gameserver.network.lineage.serverpackets.TutorialShowQuestionMark;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.world.GameObjectsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FightClubEventManager
{
	public enum CLASSES
	{
		FIGHTERS(13113, ClassId.warrior, ClassId.trooper, ClassId.warder, ClassId.berserker, ClassId.m_soul_breaker, ClassId.f_soul_breaker, ClassId.scavenger, ClassId.bounty_hunter, ClassId.artisan, ClassId.warsmith, ClassId.orc_raider, ClassId.destroyer, ClassId.orc_monk, ClassId.tyrant, ClassId.gladiator, ClassId.warlord, ClassId.duelist, ClassId.dreadnought, ClassId.titan, ClassId.grand_khauatari, ClassId.maestro, ClassId.doombringer, ClassId.m_soul_hound, ClassId.f_soul_hound),
		TANKS(13112, ClassId.knight, ClassId.palus_knight, ClassId.shillien_knight, ClassId.paladin, ClassId.dark_avenger, ClassId.elven_knight, ClassId.temple_knight, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.eva_templar, ClassId.shillien_templar, ClassId.trickster),
		ARCHERS(13114, ClassId.hawkeye, ClassId.arbalester, ClassId.phantom_ranger, ClassId.silver_ranger, ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.ghost_sentinel, ClassId.fortune_seeker),
		DAGGERS(13114, ClassId.rogue, ClassId.assassin, ClassId.abyss_walker, ClassId.treasure_hunter, ClassId.elven_scout, ClassId.plains_walker, ClassId.adventurer, ClassId.wind_rider, ClassId.ghost_hunter),
		MAGES(13116, ClassId.wizard, ClassId.dark_wizard, ClassId.spellhowler, ClassId.elven_wizard, ClassId.spellsinger, ClassId.sorceror, ClassId.necromancer, ClassId.archmage, ClassId.soultaker, ClassId.mystic_muse, ClassId.storm_screamer),
		SUMMONERS(13118, ClassId.warlock, ClassId.elemental_summoner, ClassId.phantom_summoner, ClassId.arcana_lord, ClassId.elemental_master, ClassId.spectral_master),
		HEALERS(13115, ClassId.cleric, ClassId.shillien_oracle, ClassId.shillien_elder, ClassId.oracle, ClassId.elder, ClassId.bishop, ClassId.cardinal, ClassId.eva_saint, ClassId.shillien_saint, ClassId.dominator),
		SUPPORTS(13117, ClassId.orc_shaman, ClassId.inspector, ClassId.overlord, ClassId.warcryer, ClassId.prophet, ClassId.sword_singer, ClassId.bladedancer, ClassId.hierophant, ClassId.sword_muse, ClassId.spectral_dancer, ClassId.doomcryer, ClassId.judicator),

		LOW_MAGE(13116, ClassId.mage, ClassId.elven_mage, ClassId.dark_mage, ClassId.orc_mage),
		LOW_FIGHTER(13113, ClassId.fighter, ClassId.elven_fighter, ClassId.dark_fighter, ClassId.orc_fighter, ClassId.dwarven_fighter, ClassId.kamael_m_soldier, ClassId.kamael_f_soldier);

		private int _polyId;
		private ClassId[] _classes;
		private CLASSES(int transformId, ClassId... ids)
		{
			_polyId = transformId;
			_classes = ids;
		}

		public ClassId[] getClasses()
		{
			return _classes;
		}

		public int getPolyId()
		{
			return _polyId;
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(FightClubEventManager.class);
	private static FightClubEventManager _instance;

	public static final Location RETURN_LOC = new Location(83208, 147672, -3494);
	public static final String BYPASS = "_fightclub";

	private final Map<Integer, AbstractFightClub> _activeEvents = new ConcurrentHashMap<Integer, AbstractFightClub>();
	private final List<FightClubGameRoom> _rooms = new CopyOnWriteArrayList<FightClubGameRoom>();
	private final boolean _shutDown = false;
	private AbstractFightClub _nextEvent = null;
	private AbstractFightClub _currentEvent = null;

	public FightClubEventManager()
	{
		if(EventsConfig.ALLOW_FIGHT_CLUB)
		{
			if(EventsConfig.EVENT_RANDOM_TASK)
				ThreadPoolManager.getInstance().schedule(new RandomEventRunThread(null), 60000L); //run thread after 60 sec
			else
				startAutoEventsTasks();
		}
	}

	public boolean serverShuttingDown()
	{
		return _shutDown;
	}

	/*
	 * Player
	 */

	/**
	 * Looking for room, adding player and sending message Event MUST exist in one of the Rooms already!
	 * @param player player joining event
	 * @param event event to join
	 */
	public void signForEvent(Player player, AbstractFightClub event)
	{
		FightClubGameRoom roomFound = null;

		for(FightClubGameRoom room : getEventRooms(event))
			if(room.getSlotsLeft() > 0)
			{
				roomFound = room;
				break;
			}

		if(roomFound == null)
		{
			AbstractFightClub duplicatedEvent = prepareNewEvent(event);
			roomFound = createRoom(duplicatedEvent);
		}

		roomFound.addAlonePlayer(player);

		player.sendMessage((player.getLanguage() == Language.RUSSIAN ? "Вы зарегистрировались на ":"You just participated to ") + event.getName() + " Event!");
	}

	/**
	 * Checking if player can participate(all conditions) Checking if registration is open and if player isn't participated yet Signing for event
	 * @param player player to participate
	 * @param event event to participate
	 */
	public void trySignForEvent(Player player, AbstractFightClub event, boolean checkConditions)
	{
		if(checkConditions && !canPlayerParticipate(event, player, true, false)){ return; }

		if(!isRegistrationOpened(event))
		{
			player.sendMessage((player.getLanguage() == Language.RUSSIAN ? "Вы не можете зарегистрироваться на ":"You cannot participate in ") + event.getName() + " right now!");
		}
		else if(isPlayerRegistered(player))
		{
			player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Вы уже зарегистрировались на евент!":"You are already registered in event!");
		}
		else
		{
			signForEvent(player, event);
		}
	}

	/**
	 * Removing player from every room, that he is participated in. Sending Message
	 * @param player to participate
	 */
	public void unsignFromEvent(Player player)
	{
        requestEventPlayerMenuBypass(player, BYPASS +" leave");
		for(FightClubGameRoom room : _rooms)
			if(room.containsPlayer(player))
			{
				room.leaveRoom(player);
			}

		player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Регистрация на евент отменена!":"You were unregistered from Event!");
	}

	/**
	 * Is it still possible to Register Players for the Event?
	 * @param event event to rehister
	 * @return registration opened
	 */
	public boolean isRegistrationOpened(AbstractFightClub event)
	{
		for(FightClubGameRoom room : _rooms)
			if(room.getGame() != null && room.getGame().getEventId() == event.getEventId())
				return true;
		return false;
	}

	/**
	 * Is player registered to any event at the moment? Checking by Player and HWID
	 * @param player to check
	 * @return is registered
	 */
	public boolean isPlayerRegistered(Player player)
	{
		if(player.isInFightClub())
			return true;

		for(FightClubGameRoom room : _rooms)
			for(Player iPlayer : room.getAllPlayers())
				if(iPlayer.equals(player) || EventsConfig.FIGHT_CLUB_HWID_CHECK && iPlayer.getHwid()!=null && iPlayer.getHwid().equals(player.getHwid()))
					return true;
		return false;
	}

	/*
	 * Rooms
	 */

	public void startEventCountdown(AbstractFightClub event)
	{
		if(!EventsConfig.ALLOW_FIGHT_CLUB || ArrayUtils.arrayContains(parseDisallowEvents(EventsConfig.FIGHT_CLUB_DISALLOW_EVENT), event.getEventId()))
			return;

		FightClubLastStatsManager.getInstance().clearStats();
		_nextEvent = event;

		final AbstractFightClub duplicatedEvent = prepareNewEvent(event);
		createRoom(duplicatedEvent);

		sendToAllMsg(duplicatedEvent, "Registration to " + duplicatedEvent.getName() + " started!");
		sendEventInvitations(event);

		ThreadPoolManager.getInstance().schedule((Runnable) () -> {
			// After 2 minutes
			sendToAllMsg(duplicatedEvent, duplicatedEvent.getName() + " Event will start in 1 minute!");
			notifyConditions(duplicatedEvent);
			try
			{
				Thread.sleep(45000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			// After 45 seconds
			sendToAllMsg(duplicatedEvent, duplicatedEvent.getName() + " Event will start in 15 seconds!");
			notifyConditions(duplicatedEvent);
			try
			{
				Thread.sleep(15000);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			// After 15 seconds
			sendToAllMsg(duplicatedEvent, duplicatedEvent.getName() + " Event Started!");

			if(!EventsConfig.ALLOW_FIGHT_CLUB || ArrayUtils.arrayContains(parseDisallowEvents(EventsConfig.FIGHT_CLUB_DISALLOW_EVENT), duplicatedEvent.getEventId()))// Protection for my mistakes
				return;
			startEvent(duplicatedEvent);
		}, 60000L);
	}

	/**
	 * Checking {@link #canPlayerParticipate(AbstractFightClub, Player, boolean, boolean)} for every player participated in event. Sending Message, checking all condtions
	 * @param event to check
	 */
	private void notifyConditions(AbstractFightClub event)
	{
		for(FightClubGameRoom room : getEventRooms(event))
		{
			for(Player player : room.getAllPlayers())
			{
				canPlayerParticipate(event, player, true, false);
			}
		}
	}

	/**
	 * Creating event from every Room
	 * @param event
	 */
	private void startEvent(AbstractFightClub event)
	{
		List<FightClubGameRoom> eventRooms = getEventRooms(event);

		if(EventsConfig.FIGHT_CLUB_EQUALIZE_ROOMS)
			equalizeRooms(eventRooms);

		for(FightClubGameRoom room : eventRooms)
		{
			_rooms.remove(room);
			if(room.getPlayersCount() < (event.isTeamed() ? room.getTeamsCount() : 2))
				continue;
			room.getGame().prepareEvent(room);
		}
	}

	/**
	 * Equilizing players count in All rooms in List
	 * @param eventRooms rooms to equalize
	 */
	private void equalizeRooms(Collection<FightClubGameRoom> eventRooms)
	{
		// getting all players count
		double players = 0.0;
		for(FightClubGameRoom room : eventRooms)
			players += room.getPlayersCount();

		// Getting average
		double average = players / eventRooms.size();
		// Getting players to change room and removing
		List<Player> playersToChange = new ArrayList<Player>();

		for(FightClubGameRoom room : eventRooms)
		{
			int before = room.getPlayersCount();
			int toRemove = room.getPlayersCount() - (int) Math.ceil(average);
			for(int i = 0; i < toRemove; i++)
			{
				Player player = room.getAllPlayers().get(0);
				room.leaveRoom(player);
				playersToChange.add(player);
			}
			LOG.info("Equalizing FC Room, before:" + before + " toRemove:" + toRemove + " after:" + room.getPlayersCount() + " to Change:" + playersToChange.size());
		}

		// Adding to other room
		for(FightClubGameRoom room : eventRooms)
		{
			int toAdd = (int) Math.floor(average) - room.getPlayersCount();

			for(int i = 0; i < toAdd; i++)
			{
				Player player = playersToChange.remove(0);
				room.addAlonePlayer(player);
			}
			LOG.info("Equalizing FC Room, Final:" + room.getPlayersCount());
		}
	}

	/**
	 * Getting All rooms where Game is same type as event
	 * @param event type of the event
	 * @return all rooms with type of the event
	 */
	private List<FightClubGameRoom> getEventRooms(AbstractFightClub event)
	{
		List<FightClubGameRoom> eventRooms = new ArrayList<FightClubGameRoom>();

		for(FightClubGameRoom room : _rooms)
			if(room.getGame() != null && room.getGame().getEventId() == event.getEventId())
				eventRooms.add(room);

		return eventRooms;
	}

	/**
	 * Sending "Would you like to join XXX event?" Yes/No invitation, to event player in game that meets criteria
	 * @param event event player have to join
	 */
	private void sendEventInvitations(AbstractFightClub event)
	{
		for(Player player : GameObjectsStorage.getPlayers())
			if(canPlayerParticipate(event, player, false, true) && (player.getEvent(AbstractFightClub.class) == null))
			{
				player.ask(new ConfirmDlg(SystemMsg.S1, 60000).addString((player.getLanguage() == Language.RUSSIAN ? "Вы хотите участвовать в ":"Would you like to join ") + event.getName() + " event?"), new AnswerEventInvitation(player, event));
			}
	}

	private class AnswerEventInvitation implements OnAnswerListener
	{
		private final Player _player;
		private final AbstractFightClub _event;

		private AnswerEventInvitation(Player player, AbstractFightClub event)
		{
			_player = player;
			_event = event;
		}

		@Override
		public void sayYes()
		{
			trySignForEvent(_player, _event, false);
		}

		@Override
		public void sayNo()
		{}
	}

	public FightClubGameRoom createRoom(AbstractFightClub event)
	{
		FightClubGameRoom newRoom;
		try {
			newRoom = new FightClubGameRoom(event);
			_rooms.add(newRoom);
		}
		catch (IllegalArgumentException exc){
			LOG.error("Not found maps for FightClub event: " + event.getClass().getSimpleName());
			newRoom = null;
		}
		return newRoom;
	}

	public AbstractFightClub getNextEvent()
	{
		return _nextEvent;
	}

	public AbstractFightClub getCurrentEvent()
	{
		return _currentEvent;
	}

	public void setCurrentEvent(AbstractFightClub event)
	{
		_currentEvent = event;
	}

	/*
	 * Other
	 */
	private void sendErrorMessageToPlayer(Player player, String msg)
	{
		player.sendPacket(new Say2(player.getObjectId(), ChatType.COMMANDCHANNEL_ALL, "Error", msg, player.getLanguage()));
		player.sendMessage(msg);
	}

	public void sendToAllMsg(AbstractFightClub event, String msg)
	{
		Say2 packet = new Say2(0, ChatType.CRITICAL_ANNOUNCE, event.getName(), msg, null);
		for(Player player : GameObjectsStorage.getPlayers())
			player.sendPacket(packet);
	}

	private AbstractFightClub prepareNewEvent(AbstractFightClub event)
	{
		MultiValueSet<String> set = event.getSet();
		AbstractFightClub duplicatedEvent = null;
		try
		{
			@SuppressWarnings("unchecked")
			Class<Event> eventClass = (Class<Event>) Class.forName(event.getClass().getCanonicalName()); //Class.forName(set.getString("eventClass"));
			Constructor<Event> constructor = eventClass.getConstructor(MultiValueSet.class);
			duplicatedEvent = (AbstractFightClub) constructor.newInstance(set);
			/*Class<? extends AbstractFightClub> eventClass = event.getClass(); //Class.forName(set.getString("eventClass"));
			Constructor<? extends AbstractFightClub> constructor = eventClass.getConstructor(MultiValueSet.class);
			duplicatedEvent = constructor.newInstance(set);*/

			duplicatedEvent.clearSet();
			// duplicatedEvent.setObjects(event.getObjects());
				_activeEvents.put(duplicatedEvent.getObjectId(), duplicatedEvent);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return duplicatedEvent;
	}

	private void startAutoEventsTasks()
	{
		AbstractFightClub closestEvent = null;
		long closestEventTime = Long.MAX_VALUE;

		for(int i : EventHolder.getInstance().getEventIDs(EventType.FIGHT_CLUB_EVENT))
		{
			AbstractFightClub event = EventHolder.getInstance().getEvent(EventType.FIGHT_CLUB_EVENT, i);

			if(event.isAutoTimed())
			{
				Calendar nextEventDate = getClosestEventDate(event.getAutoStartTimes());

				ThreadPoolManager.getInstance().schedule(new EventRunThread(event), nextEventDate.getTimeInMillis() - System.currentTimeMillis());

				// Closest Event
				if(closestEventTime > nextEventDate.getTimeInMillis())
				{
					closestEvent = event;
					closestEventTime = nextEventDate.getTimeInMillis();
				}
			}
		}

		_nextEvent = closestEvent;
	}

	private final SimpleDateFormat DATE = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	private class RandomEventRunThread extends RunnableImpl
	{
		private AbstractFightClub _event;

		private RandomEventRunThread(AbstractFightClub event)
		{
			_event = event;
		}

		@Override
		public void runImpl() throws Exception
		{
			if(!EventsConfig.ALLOW_FIGHT_CLUB)
			{
				LOGGER.info("Event Engine: Event is stoped now.");
				return;
			}

			if(_event != null)
			{
				startEventCountdown(_event);
				_currentEvent = _event;
				LOGGER.info("Event Engine: Event launched -> " + _event.getName() + ".");
			}

			int next = getRandom();
			AbstractFightClub event = EventHolder.getInstance().getEvent(EventType.FIGHT_CLUB_EVENT, next);
			_event = event;

			long time = EventsConfig.EVENT_RANDOM_TIME * 60 * 1000L;
			String date = DATE.format(new Date(System.currentTimeMillis() + time));
			LOGGER.info("Event Engine: Next event -> " + event.getName() + ". Started at " + date);

			Thread.sleep(60000L);

			ThreadPoolManager.getInstance().schedule(new RandomEventRunThread(_event), time);
			_nextEvent = _event;
		}

		private int getRandom()
		{
            boolean is = false;
			int next = 1;
			while(!is)
			{
				next = Rnd.get(EventHolder.getInstance().getEventIDs(EventType.FIGHT_CLUB_EVENT));
				if(!ArrayUtils.arrayContains(parseDisallowEvents(EventsConfig.FIGHT_CLUB_DISALLOW_EVENT), next))
					is = true;
			}
			return next;
		}
	}

    protected Integer[] parseDisallowEvents(String ids)
    {
        if(ids == null || ids.isEmpty())
            return null;
        StringTokenizer st = new StringTokenizer(ids, ";");
        Integer[] realIds = new Integer[st.countTokens()];
        int index = 0;
        while(st.hasMoreTokens())
        {
            realIds[index] = Integer.parseInt(st.nextToken());
            index++;
        }
        return realIds;
    }

	/**
	 * Choosing closest Hour and Minute from Array and converting it to Calendar
	 * @param dates {{hour, minute}, {hour, minute}} - of event start
	 * @return Calendar of closest date
	 */
	private Calendar getClosestEventDate(int[][] dates)
	{
		Calendar tempCalendar = Calendar.getInstance();
		tempCalendar.set(Calendar.SECOND, 0);

		Calendar eventCalendar = Calendar.getInstance();

		boolean found = false;
		long smallest = Long.MAX_VALUE;// In case, we need to make it in next day

		for(int[] hourMin : dates)
		{
			tempCalendar.set(Calendar.HOUR_OF_DAY, hourMin[0]);
			tempCalendar.set(Calendar.MINUTE, hourMin[1]);
			long timeInMillis = tempCalendar.getTimeInMillis();

			// If time is smaller than current
			if(timeInMillis < System.currentTimeMillis())
			{
				if(timeInMillis < smallest)
					smallest = timeInMillis;
				continue;
			}

			// If event time wasnt choosen yet or its smaller than current Event Time
			if(!found || timeInMillis < eventCalendar.getTimeInMillis())
			{
				found = true;
				eventCalendar.setTimeInMillis(timeInMillis);
			}
		}

		if(!found)
			eventCalendar.setTimeInMillis(smallest + 86400000);// Smallest time + One Day

		return eventCalendar;
	}

	private class EventRunThread extends RunnableImpl
	{
		private final AbstractFightClub _event;

		private EventRunThread(AbstractFightClub event)
		{
			_event = event;
		}

		@Override
		public void runImpl() throws Exception
		{
			startEventCountdown(_event);

			if(!_event.isAutoTimed())
				return;

			Thread.sleep(60000L);

			Calendar nextEventDate = getClosestEventDate(_event.getAutoStartTimes());

			ThreadPoolManager.getInstance().schedule(new EventRunThread(_event), nextEventDate.getTimeInMillis() - System.currentTimeMillis());
		}
	}

	public boolean canPlayerParticipate(AbstractFightClub event, Player player, boolean sendMessage, boolean justMostImportant)
	{
		if(player == null)
			return false;

		if(player.getLevel() < event.getMinLevel() || player.getLevel() > event.getMaxLevel())
		{
			if(sendMessage)
				sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Ваш класс не подходит по уровню!":"Your class does not meet the level conditions!");
			return false;
		}

		if(player.isBlocked())
		{
			if(sendMessage)
				sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Занятые игроки не могут участвовать на евенте!":"Blocked players cannot join Event!");
			return false;
		}

		if(player.getCursedWeaponEquippedId() > 0)
		{
			if(sendMessage)
				sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Игроки с проклятым оружием не могут участвовать на евенте!":"Cursed Weapon owners may not participate in Event!");
			return false;
		}

		if(Olympiad.isRegistered(player))
		{
			if(sendMessage)
				sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Зарегистрированные на Олимпиаду игроки не могут участвовать на евенте!":"Players registered to Olympiad Match may not participate in Event!");
			return false;
		}

		if(player.isInOlympiadMode() || player.getOlympiadGame() != null)
		{
			if(sendMessage)
				sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Игроки на Олимпиаде не могут участвовать на евенте!":"Players fighting in Olympiad Match may not participate in Event!");
			return false;
		}

		if(player.isInObserverMode())
		{
			if(sendMessage)
				sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Наблюдающие игроки не могут участвовать на евенте!":"Players in Observation mode may not participate in Event!");
			return false;
		}

		if(!justMostImportant)
		{
			if(player.isDead() || player.isAlikeDead())
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Мертвые игроки не могут участвовать на евенте!":"Dead players may not participate in Event!");
				return false;
			}

			if(player.isBlocked())
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Занятые игроки не могут участвовать на евенте!":"Blocked players may not participate in Event!");
				return false;
			}

			if(!player.isInPeaceZone() && player.getPvpFlag() > 0)
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Игроки в драке не могут участвовать на евенте!":"Players in PvP Battle may not participate in Event!");
				return false;
			}

			if(player.isInCombat())
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Игроки в драке не могут участвовать на евенте!":"Players in Combat may not participate in Event Battle!");
				return false;
			}

			if(player.getEvent(DuelEvent.class) != null)
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Игроки в дуэли не могут участвовать на евенте!":"Players engaged in Duel may not participate in Event Battle!");
				return false;
			}

			if(player.getKarma() > 0)
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "ПК не могут участвовать на евенте!":"Chaotic players may not participate in Event!");
				return false;
			}

			if(player.isInOfflineMode())
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Игроки в оффлайн моде не могут участвовать на евенте!":"Players in Offline mode may not participate in Event!");
				return false;
			}

			if(player.isInStoreMode())
			{
				if(sendMessage)
					sendErrorMessageToPlayer(player, player.getLanguage() == Language.RUSSIAN ? "Игроки на продаже не могут участвовать на евенте!":"Players in Store mode may not participate in Event!");
				return false;
			}
		}

		return true;
	}

	public void requestEventPlayerMenuBypass(Player player, String bypass)
	{
		player.sendPacket(TutorialCloseHtml.STATIC);

		// Getting event
		AbstractFightClub event = player.getFightClubEvent();
		if(event == null)
			return;

		// Getting fPlayer
		FightClubPlayer fPlayer = event.getFightClubPlayer(player);
		if(fPlayer == null)
			return;

		// Player isnt viewing main event page now
		fPlayer.setShowTutorial(false);

		// Checking if its the right bypass
		if(!bypass.startsWith(BYPASS))
			return;

		// Getting action
		StringTokenizer st = new StringTokenizer(bypass, " ");
		st.nextToken();// _fightclub

		String action = st.nextToken();
        if(action.equals("leave"))
            askQuestion(player, player.getLanguage() == Language.RUSSIAN ? "Вы действительно хотите покинуть евент?":"Are you sure You want to leave the event?");

	}

	public void sendEventPlayerMenu(Player player)
	{
		AbstractFightClub event = player.getFightClubEvent();
		if(event == null || event.getFightClubPlayer(player) == null){ return; }

		FightClubPlayer fPlayer = event.getFightClubPlayer(player);

		// Player is viewing main event page now
		fPlayer.setShowTutorial(true);


		String html = HtmCache.getInstance().getHtml("events/fightclub_menu.htm", player);
		html = html.replace("%event_name%", event.getName());
		html = html.replace("%event_desc%", event.getDescription());
		html = html.replace("%event_bypass%", BYPASS);

		player.sendPacket(new TutorialShowHtml(html));
		player.sendPacket(new TutorialShowQuestionMark(100));
	}

	private void leaveEvent(Player player)
	{
		AbstractFightClub event = player.getFightClubEvent();
		if(event == null)
			return;

		if(event.leaveEvent(player, true))
			player.sendMessage("You have left the event!");
	}

	private void askQuestion(Player player, String question)
	{
		ConfirmDlg packet = new ConfirmDlg(SystemMsg.S1, 60000).addString(question);
		player.ask(packet, new AskQuestionAnswerListener(player));
	}

	private class AskQuestionAnswerListener implements OnAnswerListener
	{
		private final Player _player;

		private AskQuestionAnswerListener(Player player)
		{
			_player = player;
		}

		@Override
		public void sayYes()
		{
			leaveEvent(_player);
		}

		@Override
		public void sayNo()
		{}

	}

	public AbstractFightClub getEventByObjId(int objId)
	{
		return _activeEvents.get(objId);
	}

	public AbstractFightClub removeEventObjId(int objId)
	{
		return _activeEvents.remove(objId);
	}

	public static FightClubEventManager getInstance()
	{
		if(_instance == null)
			_instance = new FightClubEventManager();
		return _instance;
	}
}
