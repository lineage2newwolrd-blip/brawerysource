package org.mmocore.gameserver.model.entity.events.impl.fightclub;

import org.mmocore.commons.collections.MultiValueSet;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.model.Skill;
import org.mmocore.gameserver.network.lineage.serverpackets.ExPVPMatchRecord;
import org.mmocore.gameserver.network.lineage.serverpackets.RadarControl;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.model.base.TeamType;
import org.mmocore.gameserver.model.entity.events.EventType;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubPlayer;
import org.mmocore.gameserver.model.entity.events.fightclubmanager.FightClubTeam;
import org.mmocore.gameserver.model.entity.events.impl.AbstractFightClub;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.skills.AbnormalEffect;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class DominationEvent extends AbstractFightClub//TODO sprawdzic czy flaga mi zostanie po skonczeniu eventu
{
	private static enum FlagType {
		Blue(53002),
		Red(53001),
		Green(53003),
		Purple(53004),
		Yellow(53005);

		private int FLAG_HOLDER_ID;

		private FlagType(int baseId) {
			FLAG_HOLDER_ID = baseId;
		}
	}

	private static final int BASE_CAPTURE_ID = 53012;
	private static final int BASE_TEAM_ID = 53011;
	private DominionBase[] _baseTeams;
	private final int _badgesDomination;

	private ScheduledFuture<?> _pointTimer;

	public DominationEvent(MultiValueSet<String> set) {
		super(set);
		_badgesDomination = set.getInteger("badgesDomination");
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
				} else if (victim.isPet()) {

				}
				actor.getPlayer().sendUserInfo();
			}

			if (victim.isPlayer()) {
				FightClubPlayer realVictim = getFightClubPlayer(victim);
				realVictim.increaseDeaths();
				if (actor != null)
					sendMessageToPlayer(realVictim, MESSAGE_TYPES.GM, (realVictim.getPlayer().getLanguage() == Language.RUSSIAN ? "Вас убил " : "You have been killed by ") + actor.getName());
				victim.getPlayer().sendUserInfo();

			}

			super.onKilled(actor, victim);
		} catch (Exception e) {
			_log.error("Error on Domination OnKilled!", e);
		}
	}

	@Override
	public void onMagicUse(final Creature actor, final Creature holder, final Skill skill) {
		if (skill != null && skill.getId() == 427 && actor.isPlayer() && holder.isNpc()) { //skill Spell Force on Dominion
			try {
				FightClubPlayer fPlayer = getFightClubPlayer(actor);
				if (fPlayer == null)
					return;
				if (getState() != EVENT_STATE.STARTED)
					return;

				for (DominionBase base : _baseTeams)
					if (base._holder != null && base._holder.equals(holder))
						getBase(fPlayer, base);

			} catch (Exception e) {
				_log.error("Error on Domination getBase!", e);
			}
		}
	}

	@Override
	public void startEvent() {
		try {
			super.startEvent();
			_baseTeams = new DominionBase[getMap().getKeyLocations().length];
			int index = 0;
			for (FightClubTeam team : getTeams()) {
				DominionBase base = new DominionBase();
				base._team = team;
				spawnHomeBase(base);
				base._startBase = true;
				_baseTeams[index] = base;
				index++;
			}
			for (int i = 0; i < getMap().getKeyLocations().length - getTeams().size(); i++) {
				DominionBase base = new DominionBase();
				base._holder = spawnNpc(BASE_CAPTURE_ID, getMap().getKeyLocations()[index], 0);
				_baseTeams[index] = base;
				index++;
			}
			_pointTimer = ThreadPoolManager.getInstance().scheduleAtFixedDelay(new PointTimer(), TIME_PLAYER_TELEPORTING * 1000 + TIME_PREPARATION_BEFORE_FIRST_ROUND * 1000, 5000L);
		} catch (Exception e) {
			_log.error("Error on Domination startEvent!", e);
		}
	}

	@Override
	public void startRound() {
		showScores(ExPVPMatchRecord.START);
		sendRadarEnemyBase();
		super.startRound();
	}

	@Override
	protected void updateScreenScores() {
	}

	@Override
	protected void updateScreenScores(Player player) {
	}

	private void sendRadarEnemyBase() {

		if (isTeamed() && getTeams().size() == 2)
			for (DominionBase base : _baseTeams)
				if (base._team != null && base._startBase) {

					RadarControl rc = new RadarControl(RadarControl.RadarState.SHOW_RADAR, RadarControl.RadarType.ARROW, getBaseSpawnLocation(base._team));

					for (FightClubPlayer iFPlayer : getPlayers(FIGHTING_PLAYERS))
						if (!base._team.equals(iFPlayer.getTeam()))
							iFPlayer.getPlayer().sendPacket(rc);
				}

	}

	@Override
	public boolean leaveEvent(Player player, boolean teleportTown) {
		showScores(ExPVPMatchRecord.FINISH, player);
		return super.leaveEvent(player, teleportTown);
	}

	@Override
	protected void sendMessageToFighting(MESSAGE_TYPES type, String msg, boolean skipJustTeleported) {
	}

	@Override
	protected void showScores(int type) {
		ExPVPMatchRecord p = getMatchRecord(type);
		if (p != null) {
			for (FightClubPlayer iFPlayer : getPlayers(FIGHTING_PLAYERS))
				iFPlayer.getPlayer().sendPacket();
		}
	}

	protected void showScores(int type, Player player) {
		if (player == null)
			return;
		ExPVPMatchRecord p = getMatchRecord(type);
		if (p != null)
			player.sendPacket(p);
	}

	private ExPVPMatchRecord getMatchRecord(int type) {
		FightClubTeam blueTeam = getTeam(TeamType.BLUE);
		if (blueTeam == null) return null;
		int blueScore = inScreenShowBeScoreNotKills() ? blueTeam.getScore() : getTeamTotalKills(blueTeam);

		FightClubTeam redTeam = getTeam(TeamType.RED);
		if (redTeam == null) return null;
		int redScore = inScreenShowBeScoreNotKills() ? redTeam.getScore() : getTeamTotalKills(redTeam);

		List<ExPVPMatchRecord.Member> blueList = new ArrayList<>(9);
		for (FightClubPlayer member : getTopMembers(blueTeam)) {
			if (member != null && blueList.size() < 9)
				blueList.add(new ExPVPMatchRecord.Member(member.getPlayer().getName(), member.getKills(true), member.getDeaths()));
		}

		List<ExPVPMatchRecord.Member> redList = new ArrayList<>(9);
		for (FightClubPlayer member : getTopMembers(redTeam)) {
			if (member != null && redList.size() < 9)
				redList.add(new ExPVPMatchRecord.Member(member.getPlayer().getName(), member.getKills(true), member.getDeaths()));
		}

		int winner = winnerTeam();
		return new ExPVPMatchRecord(type, TeamType.values()[winner], blueScore, redScore, blueList, redList);
	}

	@Override
	protected int getBadgesEarned(FightClubPlayer fPlayer, int currentValue, boolean isTopKiller) {
		int newValue = currentValue + addMultipleBadgeToPlayer(fPlayer.getEventSpecificScore("base"), _badgesDomination);
		return super.getBadgesEarned(fPlayer, newValue, isTopKiller);
	}

	@Override
	public void stopEvent() {
		try {
			super.stopEvent();
			for (DominionBase base : _baseTeams) {
				if (base._flag != null)
					base._flag.deleteMe();
				if (base._holder != null)
					base._holder.deleteMe();

			}
			_baseTeams = null;
			if (_pointTimer != null)
				_pointTimer.cancel(false);
			_pointTimer = null;
		} catch (Exception e) {
			_log.error("Error on Domination stopEvent!", e);
		}
	}

	@Override
	public void endRound() {
		super.endRound();
		if (_pointTimer != null)
			_pointTimer.cancel(false);
		_pointTimer = null;
	}

	/**
	 * Team with Npc Holder, Npc Flag and TeamHolder - guy who is carrying the flag
	 */
	private class DominionBase {
		private FightClubTeam _team;
		private NpcInstance _holder;
		private NpcInstance _flag;
		private boolean _startBase;
		private Map<FightClubTeam, Integer> _castingPoints = new HashMap<FightClubTeam, Integer>();
		private long _stopRegen = System.currentTimeMillis();
	}

	private class PointTimer extends RunnableImpl {
		@Override
		public void runImpl() throws Exception {
			for (DominionBase base : _baseTeams) {
				if (base._team != null && !base._startBase) {
					FightClubTeam team = base._team;
					team.incScore(1);
					if (base._stopRegen + 3500L < System.currentTimeMillis()) {
						if (base._castingPoints.containsKey(team))
							base._castingPoints.put(team, Math.min(base._castingPoints.get(team) + 10, 100));
						else
							base._castingPoints.put(team, 20);
					}
				} else
					for (FightClubTeam fteam : base._castingPoints.keySet())
						if (base._stopRegen + 3500L < System.currentTimeMillis())
							base._castingPoints.put(fteam, Math.max(base._castingPoints.get(fteam) - 10, 0));

			}
			showScores(ExPVPMatchRecord.UPDATE);
		}
	}

	private DominionBase getTeam(FightClubTeam team) {
		if (team == null)
			return null;
		try {
			for (DominionBase base : _baseTeams)
				if (base._team != null && base._team.equals(team))
					return base;
			return null;
		} catch (Exception e) {
			_log.error("Error on Domination getTeam!", e);
			return null;
		}
	}

	private Location getBaseSpawnLocation(FightClubTeam team) {
		return getMap().getKeyLocations()[team.getIndex() - 1];
	}

	private void spawnFlag(DominionBase base) {
		try {
			NpcInstance flag = spawnNpc(FlagType.values()[base._team.getIndex() - 1].FLAG_HOLDER_ID, base._holder.getLoc(), 0);
			flag.setName(base._team.getName() + " Flag");
			flag.broadcastCharInfo();
			base._flag = flag;
			if (base._team.getIndex() == 2) base._holder.startAbnormalEffect(AbnormalEffect.S_AIR_ROOT);
			else if (base._team.getIndex() == 1) base._holder.startAbnormalEffect(AbnormalEffect.IMPRISIONING_2);
		} catch (Exception e) {
			_log.error("Error on Domination spawnFlag!", e);
		}

	}

	private void spawnHomeBase(DominionBase base) {
		try {
			NpcInstance holder = spawnNpc(BASE_TEAM_ID, getBaseSpawnLocation(base._team), 0);
			holder.setName(base._team.getName() + " Base");
			holder.broadcastCharInfo();
			base._holder = holder;
		} catch (Exception e) {
			_log.error("Error on Domination spawnBase!", e);
		}
	}

    /*public void talkedWithBase(Player player, NpcInstance holder)
    {
        try
        {
            FightClubPlayer fPlayer = getFightClubPlayer(player);
            if (fPlayer == null)
                return;
            if (getState() != EVENT_STATE.STARTED)
                return;

            for (DominionBase base : _baseTeams)
                if (base._holder != null && base._holder.equals(holder)) {
                    if (fPlayer.getTeam().equals(base._team)) {//player talked with his holder
                        if (!player.isInRange(holder, Creature.INTERACTION_DISTANCE))
                        {
                            if (player.getAI().getIntention() != CtrlIntention.AI_INTENTION_INTERACT)
                                player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this, null);
                        }
                        return;
                    }
                    else if (!base._startBase)//player talked with not home base
                        player.getAI().Cast(DOMINATION_SKILL, base._holder, true, false);
                }
        }
        catch (Exception e)
        {
            _log.error("Error on Domination talkedWithBase!", e);
        }
    }*/

	public NpcInstance getBaseInstance(Player player, NpcInstance holder) {
		try {
			FightClubPlayer fPlayer = getFightClubPlayer(player);
			if (fPlayer == null)
				return null;

			if (getState() != EVENT_STATE.STARTED)
				return null;

			for (DominionBase base : _baseTeams)
				if (base._holder != null && base._holder.equals(holder)) {
					if (fPlayer.getTeam().equals(base._team)) {//player talked with his holder
						return null;
					} else if (!base._startBase) {//player talked with not home base
						return base._holder;
					}
				}
		} catch (Exception e) {
			_log.error("Error on Domination talkedWithBase!", e);
		}
		return null;
	}

	public void castingBase(Player player, NpcInstance holder) {
		try {
			FightClubPlayer fPlayer = getFightClubPlayer(player);
			FightClubTeam fTeam = fPlayer.getTeam();
			if (fPlayer == null)
				return;
			if (getState() != EVENT_STATE.STARTED)
				return;

			for (DominionBase base : _baseTeams)
				if (base._holder != null && base._holder.equals(holder)) {

					base._stopRegen = System.currentTimeMillis();
					if (fTeam.equals(base._team))//player casting his holder
					{
						player.abortCast(true, false);
						return;
					} else if (!base._startBase && base._team != null)//player casting enemy holder
					{
						if (base._castingPoints.containsKey(base._team))
							base._castingPoints.put(base._team, Math.max(base._castingPoints.get(base._team) - 15, 0));
						else {
							freeBase(fPlayer, base);
							return;
						}

						if (base._castingPoints.get(base._team) == 0) freeBase(fPlayer, base);
					} else if (!base._startBase) {//player casting free holder
						int points;

						for (FightClubTeam team : base._castingPoints.keySet()) {
							points = base._castingPoints.get(team);
							if (!fTeam.equals(team) && points > 0)
								base._castingPoints.put(team, Math.max(points - 5, 0));
						}

						if (base._castingPoints.containsKey(fTeam))
							base._castingPoints.put(fTeam, Math.min(base._castingPoints.get(fTeam) + 10, 100));
						else base._castingPoints.put(fTeam, 10);

						if (base._castingPoints.get(fTeam) == 100) getBase(fPlayer, base);
					}
				}

		} catch (Exception e) {
			_log.error("Error on Domination castingBase!", e);
		}
	}

	private boolean freeBase(FightClubPlayer fPlayer, DominionBase base) {
		try {
			//DominionBase goodTeam = getTeam(fPlayer.getTeam());
			Player player = fPlayer.getPlayer();
			if (base._flag != null) {
				sendMessageToTeam(base._team, MESSAGE_TYPES.CRITICAL, "Someone casting your Base!");
				base._flag.deleteMe();
				base._flag = null;
				base._team = null;
				base._holder.startAbnormalEffect(AbnormalEffect.NULL);
			}
			base._castingPoints.clear();
			return true;
		} catch (Exception e) {
			_log.error("Error on Domination freeBase!", e);
			return false;
		}
	}

	private boolean getBase(FightClubPlayer fPlayer, DominionBase base) {
		try {
			if (fPlayer.getTeam().equals(base._team))//player casting his holder
			{
				fPlayer.getPlayer().abortCast(true, false);
				return false;
			}
			base._stopRegen = System.currentTimeMillis();
			for (Creature npc : base._holder.getAroundCharacters(400, 200))
				if (npc.isPlayer() && npc.isCastingNow() && npc.getCastingTarget() == base._holder && npc.getCastingSkill().getId() == 427)
					npc.abortCast(true, false);

			if (base._team != null && base._team != fPlayer.getTeam()) {
				base._flag.deleteMe();
				base._flag = null;
				base._holder.startAbnormalEffect(AbnormalEffect.NULL);
				sendMessageToTeam(base._team, MESSAGE_TYPES.CRITICAL, "The enemy capture your Base!");
				sendMessageToTeam(fPlayer.getTeam(), MESSAGE_TYPES.CRITICAL, "Your team capture the Base!");
			} else if (base._team == null)
				sendMessageToTeam(fPlayer.getTeam(), MESSAGE_TYPES.CRITICAL, "Your team capture the Base");

			base._team = fPlayer.getTeam();
			base._team.incScore(10);
			base._castingPoints.clear();
			base._castingPoints.put(base._team, 100);
			fPlayer.increaseEventSpecificScore("base");
			spawnFlag(base);
			showScores(ExPVPMatchRecord.UPDATE);
			return true;
		} catch (Exception e) {
			_log.error("Error on Domination getBase!", e);
			return false;
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
