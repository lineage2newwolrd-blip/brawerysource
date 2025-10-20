package org.mmocore.gameserver.model.entity.events.impl.fightclub;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubTeam;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.model.entity.events.objects.CTFCombatFlagObject;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.Location;

public class CaptureTheFlagEvent extends AbstractFightClub//TODO sprawdzic czy flaga mi zostanie po skonczeniu eventu
{

	private enum BaseType {
		Blue(53007),
		Red(53006),
		Green(53008),
		Purple(53009),
		Yellow(53010);

		private int FLAG_HOLDER_ID;

		BaseType(int baseId) {
			FLAG_HOLDER_ID = baseId;
		}
	}

	private enum FlagType {
		Blue(53002),
		Red(53001),
		Green(53003),
		Purple(53004),
		Yellow(53005);

		private int FLAG_TO_STEAL_ID;

		FlagType(int baseId) {
			FLAG_TO_STEAL_ID = baseId;
		}
	}

	//private static final int FLAG_TO_STEAL_ID = 53011;//headquarters
	private CaptureFlagTeam[] flagTeams;
	private final int badgesCaptureFlag;

	public CaptureTheFlagEvent(MultiValueSet<String> set) {
		super(set);
		badgesCaptureFlag = set.getInteger("badgesCaptureFlag");
	}

	@Override
	protected int getBadgesEarned(FightClubPlayer fPlayer, int currentValue, boolean isTopKiller) {
		int newValue = currentValue + addMultipleBadgeToPlayer(fPlayer.getEventSpecificScore("capture"), badgesCaptureFlag);
		return super.getBadgesEarned(fPlayer, newValue, isTopKiller);
	}

	@Override
	public void onKilled(Creature actor, Creature victim) {
		try {
			if (actor != null && actor.isPlayable()) {
				FightClubPlayer realActor = getFightClubPlayer(actor.getPlayer());
				if (victim.isPlayer() && realActor != null) {
					realActor.increaseKills(true);
					updatePlayerScore(realActor);
					sendMessageToPlayer(realActor, MESSAGE_TYPES.GM, (realActor.getPlayer().getLanguage() == Language.RUSSIAN ? "Вы убили " : "You have killed ") + victim.getName());
				}
				actor.getPlayer().sendUserInfo();
			}

			if (victim.isPlayer()) {
				FightClubPlayer realVictim = getFightClubPlayer(victim);
				realVictim.increaseDeaths();
				if (actor != null)
					sendMessageToPlayer(realVictim, MESSAGE_TYPES.GM, (realVictim.getPlayer().getLanguage() == Language.RUSSIAN ? "Вас убил " : "You have been killed by ") + actor.getName());
				victim.getPlayer().sendUserInfo();

				CaptureFlagTeam flagTeam = getTeam(realVictim.getTeam());
				//If victim was holding flag
				if (flagTeam != null && flagTeam.thisTeamHolder != null && flagTeam.thisTeamHolder.playerHolding.equals(realVictim)) {
					spawnFlag(getTeam(flagTeam.thisTeamHolder.teamFlagOwner));

					flagTeam.thisTeamHolder = null;
				}
			}

			super.onKilled(actor, victim);
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag OnKilled!", e);
		}
	}

	@Override
	public void startEvent() {
		try {
			super.startEvent();
			flagTeams = new CaptureFlagTeam[getTeams().size()];
			int i = 0;
			for (FightClubTeam team : getTeams()) {
				CaptureFlagTeam flagTeam = new CaptureFlagTeam();
				flagTeam.team = team;
				spawnHolder(flagTeam);
				spawnFlag(flagTeam);
				flagTeams[i] = flagTeam;
				i++;
			}
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag startEvent!", e);
		}
	}

	@Override
	public void stopEvent() {
		try {
			super.stopEvent();
			for (CaptureFlagTeam iFlagTeam : flagTeams) {
				if (iFlagTeam.flag != null)
					iFlagTeam.flag.deleteMe();
				if (iFlagTeam.holder != null)//delete
					iFlagTeam.holder.deleteMe();//delete
				if (iFlagTeam.thisTeamHolder != null && iFlagTeam.thisTeamHolder.enemyFlagHoldByPlayer != null)
					iFlagTeam.thisTeamHolder.enemyFlagHoldByPlayer.despawnObject(this);

			}
			flagTeams = null;
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag stopEvent!", e);
		}
	}

	/**
	 * Team with Npc Holder, Npc Flag and TeamHolder - guy who is carrying the flag
	 */
	private static class CaptureFlagTeam {
		private volatile FightClubTeam team;
		private volatile NpcInstance holder;//delete
		private volatile NpcInstance flag;
		private volatile CaptureFlagHolder thisTeamHolder;
	}

	/**
	 * One guy team from @flagTeam - carrying the @enemyFlag
	 */
	private static class CaptureFlagHolder {
		private volatile FightClubPlayer playerHolding;
		private volatile CTFCombatFlagObject enemyFlagHoldByPlayer;
		private volatile FightClubTeam teamFlagOwner;
	}


	/**
	 * @return should it disappear?
	 */
	public synchronized boolean tryToTakeFlag(Player player, NpcInstance flag) {
		try {
			FightClubPlayer fPlayer = getFightClubPlayer(player);
			if (fPlayer == null)
				return false;
			if (getState() != EVENT_STATE.STARTED)
				return false;

			for (CaptureFlagTeam flagTeam : flagTeams)
				if (flagTeam.flag != null && flagTeam.flag.equals(flag)) {
					//player talked with his flag
					if (fPlayer.getTeam().equals(flagTeam.team)) {
						giveFlagBack(fPlayer, flagTeam);
						return false;
					}
					//player talked with enemy flag
					else {
						return getEnemyFlag(fPlayer, flagTeam);
					}
				}
			return false;

		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag tryToTakeFlag!", e);
			return false;
		}
	}

	public synchronized void talkedWithFlagHolder(Player player, NpcInstance holder) {
		try {
			FightClubPlayer fPlayer = getFightClubPlayer(player);
			if (fPlayer == null)
				return;
			if (getState() != EVENT_STATE.STARTED)
				return;

			for (CaptureFlagTeam flagTeam : flagTeams)
				if (flagTeam.holder != null && flagTeam.holder.equals(holder)) {
					//player talked with his holder
					if (fPlayer.getTeam().equals(flagTeam.team)) {
						giveFlagBack(fPlayer, flagTeam);
					}
				}
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag talkedWithFlagHolder!", e);
		}
	}

	/**
	 * @fPlayer from talked to Flag/Holder of @enemyFlagTeam
	 */
	private boolean getEnemyFlag(FightClubPlayer fPlayer, CaptureFlagTeam enemyFlagTeam) {
		try {
			CaptureFlagTeam goodTeam = getTeam(fPlayer.getTeam());
			Player player = fPlayer.getPlayer();

			if (enemyFlagTeam.flag != null) {
				enemyFlagTeam.flag.deleteMe();
				enemyFlagTeam.flag = null;

				//Adding flag
				CTFCombatFlagObject flag = new CTFCombatFlagObject();
				flag.spawnObject(this);
				player.getInventory().addItem(flag.getItem());
				player.getInventory().equipItem(flag.getItem());


				CaptureFlagHolder holder = new CaptureFlagHolder();
				holder.enemyFlagHoldByPlayer = flag;
				holder.playerHolding = fPlayer;
				holder.teamFlagOwner = enemyFlagTeam.team;
				goodTeam.thisTeamHolder = holder;

				sendMessageToTeam(enemyFlagTeam.team, MESSAGE_TYPES.CRITICAL, "Someone stolen your Flag!");
				sendMessageToTeam(goodTeam.team, MESSAGE_TYPES.CRITICAL, fPlayer.getPlayer().getName() + " stolen flag from " + enemyFlagTeam.team.getName() + " Team!");

				return true;
			}
			return false;
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag talkedWithFlagHolder!", e);
			return false;
		}
	}

	private CaptureFlagTeam getTeam(FightClubTeam team) {
		if (team == null)
			return null;
		try {
			for (CaptureFlagTeam iFlagTeam : flagTeams)
				if (iFlagTeam.team != null && iFlagTeam.team.equals(team))
					return iFlagTeam;
			return null;
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag getTeam!", e);
			return null;
		}
	}

	/**
	 * @param fPlayer  - player talking to holder/flag
	 * @param flagTeam - his flagTeam
	 */
	private void giveFlagBack(FightClubPlayer fPlayer, CaptureFlagTeam flagTeam) {
		try {
			CaptureFlagHolder flagHolder = flagTeam.thisTeamHolder;
			if (flagHolder != null && fPlayer.equals(flagHolder.playerHolding)) {
				flagHolder.enemyFlagHoldByPlayer.despawnObject(this);

				spawnFlag(getTeam(flagHolder.teamFlagOwner));

				flagTeam.thisTeamHolder = null;
				flagTeam.team.incScore(1);

				for (FightClubTeam team : getTeams()) {
					if (team.equals(flagTeam.team))
						sendMessageToTeam(team, MESSAGE_TYPES.CRITICAL, "You have gained score!");
					else
						sendMessageToTeam(team, MESSAGE_TYPES.CRITICAL, flagTeam.team.getName() + " team gained score!");
				}

				updateScreenScores();
				fPlayer.increaseEventSpecificScore("capture");
			}
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag giveFlagBack!", e);
		}
	}

	private Location getFlagHolderSpawnLocation(FightClubTeam team) {
		return getMap().getKeyLocations()[team.getIndex() - 1];
	}

	private void spawnFlag(CaptureFlagTeam flagTeam) {
		try {
			NpcInstance flag = spawnNpc(FlagType.values()[flagTeam.team.getIndex() - 1].FLAG_TO_STEAL_ID, getFlagHolderSpawnLocation(flagTeam.team), 0);
			flag.setName(flagTeam.team.getName() + " Flag");
			flag.broadcastCharInfo();
			flagTeam.flag = flag;
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag spawnFlag!", e);
		}
	}

	private void spawnHolder(CaptureFlagTeam flagTeam) {
		try {
			NpcInstance holder = spawnNpc(BaseType.values()[flagTeam.team.getIndex() - 1].FLAG_HOLDER_ID, getFlagHolderSpawnLocation(flagTeam.team), 0);
			holder.setName(flagTeam.team.getName() + " Flag");
			holder.broadcastCharInfo();
			flagTeam.holder = holder;
		} catch (Exception e) {
			_log.error("Error on CaptureTheFlag spawnBase!", e);
		}
	}

	@Override
	public String getVisibleTitle(Player player, String currentTitle, boolean toMe) {
		FightClubPlayer fPlayer = getFightClubPlayer(player);

		if (fPlayer == null)
			return currentTitle;

		return "Kills: " + fPlayer.getKills(true) + " Deaths: " + fPlayer.getDeaths();
	}

	@Override
	public EventType getType() {
		return EventType.FIGHT_CLUB_EVENT;
	}

}
