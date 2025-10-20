package org.mmocore.gameserver.scripts.bosses;

import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.QuartzUtils;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.clientCustoms.CustomBossSpawnConfig;
import org.mmocore.gameserver.data.xml.holder.NpcHolder;
import org.mmocore.gameserver.listener.script.OnInitScriptListener;
import org.mmocore.gameserver.listener.script.OnReloadScriptListener;
import org.mmocore.gameserver.listener.zone.OnZoneEnterLeaveListener;
import org.mmocore.gameserver.manager.ServerVariables;
import org.mmocore.gameserver.manager.naia.NaiaTowerManager;
import org.mmocore.gameserver.model.instances.MonsterInstance;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.instances.RaidBossInstance;
import org.mmocore.gameserver.model.team.CommandChannel;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.network.lineage.serverpackets.MagicSkillUse;
import org.mmocore.gameserver.network.lineage.serverpackets.PlaySound;
import org.mmocore.gameserver.network.lineage.serverpackets.SocialAction;
import org.mmocore.gameserver.network.lineage.serverpackets.SpecialCamera;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.templates.npc.NpcTemplate;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.PositionUtils;
import org.mmocore.gameserver.utils.ReflectionUtils;
import org.mmocore.gameserver.utils.Util;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author pchayka
 */
public class BelethManager implements OnInitScriptListener, OnReloadScriptListener {
    private static final Logger _log = LoggerFactory.getLogger(BelethManager.class);
    protected static NpcInstance CAMERA;
    protected static NpcInstance CAMERA2;
    protected static NpcInstance CAMERA3;
    protected static NpcInstance CAMERA4;
    //private static final long _closeDoorTimeDuration = 600000L; // 10min
    private static final int _clonesRespawnTimeTimeDuration = 40000; // 40sec
    private static final int _movieWaitTimeDuration = 180000; // 3min
    private static final int _spawnWaitTimeDuration = 90000; // 90 sec
    private static final int _ringAvailableTime = 300000; // 5min
    private static final int _clearEntityTime = 600000; // 10min
    private static final long[] _belethRespawnTime = {8 * 24 * 60 * 60 * 1000, 10 * 60 * 60 * 1000}; // 8 days +- 10 hours
    private static final long _entityInactivityTime = 2 * 60 * 60 * 1000; // 2 hours
    private static final int _ringSpawnTime = 300000; // 5min
    private static final int _lastSpawnTime = 600000; // 10min
    private static final int DOOR = 20240001; // Throne door
    private static final int CORRDOOR = 20240002; // Corridor door
    private static final int COFFDOOR = 20240003; // Tomb door
    private static final int VORTEX = 29125; // Vortex.
    private static final int ELF = 29128; // Elf corpse.
    private static final int COFFIN = 32470; // Beleth's coffin.
    private static final int BELETH = 29118; // Beleth.
    private static final int CLONE = 29119; // Beleth's clone.
    private static final int locZ = -9353; // Z value for all of npcs
    private static final int[] VORTEXSPAWN = {
            16325,
            214983,
            -9353
    };
    private static final int[] COFFSPAWN = {
            12471,
            215602,
            -9360,
            49152
    };
    private static final int centerX = 16325; // Center of the room
    private static final int centerY = 213135;
    private static Zone _zone = ReflectionUtils.getZone("[Beleth_room]");
    private static ZoneListener _zoneListener = new ZoneListener();
    private static List<NpcInstance> _npcList = new ArrayList<>();
    private static CronExpression pattern = QuartzUtils.createCronExpression(CustomBossSpawnConfig.belethCron);
    private static boolean _entryLocked = false;
    private static boolean _ringAvailable = false;
    private static boolean _belethAlive = false;
    private static RaidBossInstance _beleth = null;
    private static int _belethIdx = -1;
    private static Map<MonsterInstance, Location> _clones = new ConcurrentHashMap<>();
    private static Location[] _cloneLoc = new Location[56];
    private static ScheduledFuture<?> cloneRespawnTask;
    private static ScheduledFuture<?> ringSpawnTask;
    private static ScheduledFuture<?> lastSpawnTask;

    private static CommandChannel _cc;

    private static NpcInstance spawn(int npcId, int x, int y, int z, int h) {
        Location loc = new Location(x, y, z);
        NpcTemplate template = NpcHolder.getInstance().getTemplate(npcId);
        NpcInstance npc = template.getNewInstance();
        npc.setSpawnedLoc(loc);
        npc.setLoc(loc);
        npc.setHeading(h);
        npc.spawnMe();
        return npc;
    }

    public static Zone getZone() {
        return _zone;
    }

    private static boolean checkPlayer(Player player) {
        if (player.isDead())
            return false;

        if (player.getLevel() < 80)
            return false;

        return NaiaTowerManager.getInstance().isValidPlayer(player);

    }

    public static boolean checkBossSpawnCond() {
        return ServerVariables.getLong("BelethKillTime", 0) <= System.currentTimeMillis();
    }

    public static void startSpawn() {
        ThreadPoolManager.getInstance().schedule(new BelethSpawnTask(), 30000L);
        //ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.beleth_spawn), _closeDoorTimeDuration);
        //ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.inactivity_check), _entityInactivityTime);
        //ReflectionUtils.getDoor(DOOR).openMe();
        //initSpawnLocs();
    }
    private static class BelethSpawnTask extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            ReflectionUtils.getDoor(DOOR).openMe();
            ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.show_movie), _movieWaitTimeDuration);
            ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.inactivity_check), _entityInactivityTime);
            initSpawnLocs();
        }
    }

    public static boolean isRingAvailable() {
        return _ringAvailable;
    }

    public static void setRingAvailable(boolean value) {
        _ringAvailable = value;
    }

    public static void setBelethDead() {
        if (_entryLocked && _belethAlive){
            ThreadPoolManager.getInstance().execute(new Camera(26));
            ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.beleth_dead), 10);
        }
    }

    private static void spawnClone(int id) {
        MonsterInstance clone;
        clone = (MonsterInstance) spawn(CLONE, _cloneLoc[id].x, _cloneLoc[id].y, locZ, 49152); // _cloneLoc[i].h
        _clones.put(clone, clone.getLoc());
    }

    private static void initSpawnLocs() {
        // Variables for Calculations
        double angle = Math.toRadians(22.5);
        int radius = 700;

        // Inner clone circle
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0)
                radius -= 50;
            else
                radius += 50;
            _cloneLoc[i] = new Location(centerX + (int) (radius * Math.sin(i * angle)), centerY + (int) (radius * Math.cos(i * angle)), PositionUtils.convertDegreeToClientHeading(270 - i * 22.5));
        }
        // Outer clone square
        radius = 1340;
        int mulX, mulY, addH = 3;
        double decX, decY;
        for (int i = 0; i < 16; i++) {
            if (i % 8 == 0)
                mulX = 0;
            else if (i < 8)
                mulX = -1;
            else
                mulX = 1;
            if (i == 4 || i == 12)
                mulY = 0;
            else if (i > 4 && i < 12)
                mulY = -1;
            else
                mulY = 1;
            if (i % 8 == 1 || i == 7 || i == 15)
                decX = 0.5;
            else
                decX = 1.0;
            if (i % 10 == 3 || i == 5 || i == 11)
                decY = 0.5;
            else
                decY = 1.0;
            if ((i + 2) % 4 == 0)
                addH++;
            _cloneLoc[i + 16] = new Location(centerX + (int) (radius * decX * mulX), centerY + (int) (radius * decY * mulY), PositionUtils.convertDegreeToClientHeading(180 + addH * 90));
        }
        // Octagon #2 - Another ring of clones like the inner square, that
        // spawns after some time
        angle = Math.toRadians(22.5);
        radius = 1000;
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0)
                radius -= 70;
            else
                radius += 70;
            _cloneLoc[i + 32] = new Location(centerX + (int) (radius * Math.sin(i * angle)), centerY + (int) (radius * Math.cos(i * angle)), _cloneLoc[i].h);
        }
        // Extra clones - Another 8 clones that spawn when beleth is close to
        // dying
        int order = 48;
        radius = 650;
        for (int i = 1; i < 16; i += 2) {
            if (i == 1 || i == 15)
                _cloneLoc[order] = new Location(_cloneLoc[i].x, _cloneLoc[i].y + radius, _cloneLoc[i + 16].h);
            else if (i == 3 || i == 5)
                _cloneLoc[order] = new Location(_cloneLoc[i].x + radius, _cloneLoc[i].y, _cloneLoc[i].h);
            else if (i == 7 || i == 9)
                _cloneLoc[order] = new Location(_cloneLoc[i].x, _cloneLoc[i].y - radius, _cloneLoc[i + 16].h);
            else if (i == 11 || i == 13)
                _cloneLoc[order] = new Location(_cloneLoc[i].x - radius, _cloneLoc[i].y, _cloneLoc[i].h);
            order++;
        }
    }

    public static CommandChannel getCC() {
        return _cc;
    }

    public static void setCC(CommandChannel cc) {
        _cc = cc;
    }

    public static boolean isBelethAlive() {
        return _belethAlive;
    }

    @Override
    public void onInit() {
        getZone().addListener(_zoneListener);
        _log.info("Beleth Manager: Loaded successfuly");
    }

    @Override
    public void onReload() {
        getZone().removeListener(_zoneListener);
    }

    private enum Event {
        none,
        show_movie,
        beleth_spawn,
        beleth_despawn,
        clone_despawn,
        clone_spawn,
        ring_unset,
        beleth_dead,
        entity_clear,
        inactivity_check,
        spawn_ring,
        spawn_extras
    }

    public static class ZoneListener implements OnZoneEnterLeaveListener {

        @Override
        public void onZoneEnter(Zone zone, Creature actor) {
            if (!actor.isPlayer())
                return;

            Player player = actor.getPlayer();

            if (player.isGM() && player.isDebug())
                return;

            if (!checkPlayer(player) || !player.isInParty() || !player.getParty().isInCommandChannel() ||
                    player.getParty().getCommandChannel() != _cc) {
                player.teleToClosestTown();
            }
        }

        @Override
        public void onZoneLeave(Zone zone, Creature actor) {
        }
    }

    private static class CloneRespawnTask extends RunnableImpl {
        @Override
        public void runImpl() {
            if (_clones == null || _clones.isEmpty())
                return;

            MonsterInstance nextclone;
            for (MonsterInstance clone : _clones.keySet())
                if (clone.isDead() || clone.isDeleted()) {
                    nextclone = (MonsterInstance) spawn(CLONE, _clones.get(clone).x, _clones.get(clone).y, locZ, 49152); // _cloneLoc[i].h
                    _clones.put(nextclone, nextclone.getLoc());
                    _clones.remove(clone);
                }
        }
    }

    public static class eventExecutor extends RunnableImpl {
        Event _event;

        eventExecutor(Event event) {
            _event = event;
        }

        @Override
        public void runImpl() throws Exception {
            switch (_event) {
                case show_movie:
                    _entryLocked = true;
                    ThreadPoolManager.getInstance().execute(new Camera(1));
                    ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.beleth_spawn), _spawnWaitTimeDuration);
                    break;
                case beleth_spawn:
                    _belethIdx = Rnd.get(_cloneLoc.length);
                    _beleth = (RaidBossInstance) spawn(BELETH, _cloneLoc[_belethIdx].x, _cloneLoc[_belethIdx].y, locZ, 49152);
                    _beleth.startImmobilized();
                    _belethAlive = true;
                    ThreadPoolManager.getInstance().execute(new eventExecutor(Event.clone_spawn)); // initial clones
                    ringSpawnTask = ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.spawn_ring), _ringSpawnTime); // inner ring
                    lastSpawnTask = ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.spawn_extras), _lastSpawnTime); // last clones
                    break;
                case clone_spawn:
                    MonsterInstance clone;
                    for(int i = 0; i < 32; i++)
                    {
                        if (i == _belethIdx)
                            continue;
                        clone = (MonsterInstance) spawn(CLONE, _cloneLoc[i].x, _cloneLoc[i].y, locZ, 49152); // _cloneLoc[i].h
                        _clones.put(clone, clone.getLoc());
                    }
                    cloneRespawnTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new CloneRespawnTask(), _clonesRespawnTimeTimeDuration, _clonesRespawnTimeTimeDuration);
                    break;
                case spawn_ring:
                    for (int i = 32; i < 48; i++)
                        spawnClone(i);
                    break;
                case spawn_extras:
                    for (int i = 48; i < 56; i++)
                        spawnClone(i);
                    break;
                case beleth_dead:
                    if (cloneRespawnTask != null) {
                        cloneRespawnTask.cancel(false);
                        cloneRespawnTask = null;
                    }
                    if (ringSpawnTask != null) {
                        ringSpawnTask.cancel(false);
                        ringSpawnTask = null;
                    }
                    if (lastSpawnTask != null) {
                        lastSpawnTask.cancel(false);
                        lastSpawnTask = null;
                    }
                    _beleth.deleteMe();
                    _npcList.add(spawn(ELF, _beleth.getLoc().x, _beleth.getLoc().y, locZ, _beleth.getHeading()));
                    _npcList.add(spawn(COFFIN, COFFSPAWN[0], COFFSPAWN[1], COFFSPAWN[2], COFFSPAWN[3]));
                    setRingAvailable(true);
                    _belethAlive = false;
                    if (CustomBossSpawnConfig.activateCustomSpawn)
                        ServerVariables.set("BelethKillTime", Util.getCronMillis(pattern)
                                + Rnd.get(0, CustomBossSpawnConfig.belethRandomMinutes * 60000));
                    else
                        ServerVariables.set("BelethKillTime", System.currentTimeMillis() + _belethRespawnTime[0]
                                + Rnd.get(-_belethRespawnTime[1], _belethRespawnTime[1]));
                    _log.info("Killed RaidBoss Beleth.");
                    for (Player i : _zone.getInsidePlayers())
                        i.sendMessage("Beleth's Lair will push you out in 10 minutes");
                    ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.clone_despawn), 10);
                    ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.ring_unset), _ringAvailableTime);
                    ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.entity_clear), _clearEntityTime);
                    break;
                case ring_unset:
                    setRingAvailable(false);
                    break;
                case entity_clear:
                    _npcList.stream().filter(n -> n != null).forEach(NpcInstance::deleteMe);
                    _npcList.clear();

                    // Close coffin and corridor doors
                    ReflectionUtils.getDoor(CORRDOOR).closeMe();
                    ReflectionUtils.getDoor(COFFDOOR).closeMe();

                    //oust players
                    for (Player i : _zone.getInsidePlayers()) {
                        i.teleToLocation(new Location(-11802, 236360, -3271));
                        i.sendMessage("Beleth's Lair has become unstable so you've been teleported out");
                    }
                    _entryLocked = false;
                    break;
                case clone_despawn:
                    _clones.keySet().forEach(MonsterInstance::deleteMe);
                    _clones.clear();
                    break;
                case inactivity_check:
                    if (!_beleth.isDead()) {
                        _beleth.deleteMe();
                        ThreadPoolManager.getInstance().schedule(new eventExecutor(Event.entity_clear), 10);
                    }
                    break;
            }
        }
    }

    private static class Camera implements Runnable
    {
        private int _taskId = 0;
        private static NpcInstance belethMovie = null;
        private static NpcInstance elfMovie = null;
        protected static ArrayList<NpcInstance> belethMovieMinoins = new ArrayList<>();

        public Camera(int taskId)
        {
            _taskId = taskId;
        }

        @Override
        public void run()
        {
            try
            {
                switch (_taskId)
                {
                    case 1:
                        _zone.getInsidePlayers().forEach(Player::enterMovieMode);
                        CAMERA = spawn(29120, 16323, 213142, -7000, 0);
                        CAMERA2 = spawn(29121,16323, 210741, -9357, 0);
                        CAMERA3 = spawn(29122,16323, 213170, -9357, 0);
                        CAMERA4 = spawn(29123,16323, 214917, -9356, 0);
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 1000, 75, -65, 0, 2500, 0, 0, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(2), 300);
                        break;
                    case 2:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 2400, -45, -45, 5000, 5000, 0, 0, 1, 0), false);
                        _zone.broadcastPacket(new PlaySound(PlaySound.Type.SOUND, "BS07_A", 1, CAMERA.getObjectId(), CAMERA.getX(), CAMERA.getY(), CAMERA.getZ()),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(3), 4900);
                        break;
                    case 3:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 3100, -120, -45, 5000, 5000, 0, 0, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(4), 4900);
                        break;
                    case 4:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA2, 2200, 130, 0, 0, 1500, -20, 15, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(5), 1400);
                        break;
                    case 5:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA2, 2300, 100, 0, 2000, 4500, 0, 10, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(6), 2500);
                        break;
                    case 6:
                        ReflectionUtils.getDoor(DOOR).closeMe();
                        ThreadPoolManager.getInstance().schedule(new Camera(7), 1700);
                        break;
                    case 7:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA4, 1500, 210, 0, 0, 1500, 0, 0, 1, 0), false);
                        _zone.broadcastPacket(new SpecialCamera(CAMERA4, 900, 255, 0, 5000, 6500, 0, 10, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(8), 6000);
                        break;
                    case 8:
                        _npcList.add(spawn(VORTEX, VORTEXSPAWN[0], VORTEXSPAWN[1], VORTEXSPAWN[2], 16384));
                        _zone.broadcastPacket(new SpecialCamera(CAMERA4, 900, 255, 0, 0, 1500, 0, 10, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(9), 1000);
                        break;
                    case 9:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA4, 1000, 255, 0, 7000, 17000, 0, 25, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(10), 3000);
                        break;
                    case 10:
                        belethMovie = spawn(BELETH, 16321, 214211, -9352, 49369);
                        ThreadPoolManager.getInstance().schedule(new Camera(11), 200);
                        break;
                    case 11:
                        _zone.broadcastPacket(new SocialAction(belethMovie.getObjectId(), 1),false);
                        for (int i = 0; i < 6; i++)
                        {
                            int x = (int) ((150 * Math.cos(i * 1.046666667)) + 16323);
                            int y = (int) ((150 * Math.sin(i * 1.046666667)) + 213059);
                            Location loc = new Location(x, y, -9357);
                            NpcInstance minion = NpcHolder.getInstance().getTemplate(CLONE).getNewInstance();
                            minion.setSpawnedLoc(loc);
                            minion.setLoc(loc);
                            minion.setHeading(49152);
                            belethMovieMinoins.add(minion);
                        }
                        ThreadPoolManager.getInstance().schedule(new Camera(12), 6800);
                        break;
                    case 12:
                        _zone.broadcastPacket(new SpecialCamera(belethMovie, 0, 270, -5, 0, 4000, 0, 0, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(13), 3500);
                        break;
                    case 13:
                        _zone.broadcastPacket(new SpecialCamera(belethMovie, 800, 270, 10, 3000, 6000, 0, 0, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(14), 5000);
                        break;
                    case 14:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA3, 100, 270, 15, 0, 5000, 0, 0, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(15), 100);
                        break;
                    case 15:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA3, 100, 270, 15, 3000, 6000, 0, 5, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(16), 1400);
                        break;
                    case 16:
                        belethMovie.decayMe();
                        belethMovie.setLoc(new Location(16323,213059,-9357,49152));
                        belethMovie.spawnMe();
                        ThreadPoolManager.getInstance().schedule(new Camera(17), 200);
                        break;
                    case 17:
                        _zone.broadcastPacket(new MagicSkillUse(belethMovie, belethMovie, 5532, 1, 2000, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(18), 2000);
                        break;
                    case 18:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA3, 700, 270, 20, 1500, 8000, 0, 0, 1, 0), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(19), 6900);
                        break;
                    case 19:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA3, 40, 260, 0, 0, 4000, 0, 0, 1, 0),false);
                        belethMovieMinoins.forEach(NpcInstance::spawnMe);
                        ThreadPoolManager.getInstance().schedule(new Camera(20), 3000);
                        break;
                    case 20:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA3, 40, 280, 0, 0, 4000, 5, 0, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(21), 3000);
                        break;
                    case 21:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA3, 5, 250, 5, 0, 15000, 20, 15, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(22), 1000);
                        break;
                    case 22:
                        _zone.broadcastPacket(new SocialAction(belethMovie.getObjectId(), 3),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(23), 4000);
                        break;
                    case 23:
                        _zone.broadcastPacket(new MagicSkillUse(belethMovie, belethMovie, 5533, 1, 2000, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(24), 6800);
                        break;
                    case 24:
                        belethMovieMinoins.forEach(NpcInstance::deleteMe);
                        belethMovieMinoins.clear();
                        belethMovie.deleteMe();
                        ThreadPoolManager.getInstance().schedule(new Camera(25), 3000);
                        break;
                    case 25:
                        CAMERA.deleteMe();
                        CAMERA2.deleteMe();
                        CAMERA3.deleteMe();
                        CAMERA4.deleteMe();
                        _zone.getInsidePlayers().forEach(Player::leaveMovieMode);
                        break;

                    case 26:
                        _zone.getInsidePlayers().forEach(Player::enterMovieMode);
                        belethMovie = spawn(BELETH, 16456, 213336, -9352, 49369);
                        CAMERA = spawn(29122, 16323, 213170, -9357, 0);
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 400, 290, 25, 0, 10000, 0, 0, 1, 0),false);
                        //_zone.broadcastPacket(new SpecialCamera(CAMERA, 400, 290, 25, 0, 10000, 0, 0, 1, 0),false);
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 400, 110, 25, 4000, 10000, 0, 0, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(27), 300);
                        break;
                    case 27:
                        _zone.broadcastPacket(new PlaySound(PlaySound.Type.SOUND, "BS07_D", 1, CAMERA.getObjectId(), CAMERA.getX(), CAMERA.getY(), CAMERA.getZ()),false);
                        _zone.broadcastPacket(new SocialAction(belethMovie.getObjectId(), 5), false);
                        ThreadPoolManager.getInstance().schedule(new Camera(28), 3500);
                        break;
                    case 28:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 400, 295, 25, 4000, 5000, 0, 0, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(29), 4500);
                        break;
                    case 29:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 400, 295, 10, 4000, 11000, 0, 25, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(30), 5000);
                        break;
                    case 30:
                        belethMovie.deleteMe();
                        ThreadPoolManager.getInstance().schedule(new Camera(31), 4000);
                        break;
                    case 31:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA, 250, 90, 25, 0, 10000, 0, 0, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(32), 2000);
                        break;
                    case 32:
                        elfMovie = spawn(ELF, 16323, 213170, -9357, 0);
                        CAMERA2 = spawn(29121, 14056, 213170, -9357, 0);
                        ThreadPoolManager.getInstance().schedule(new Camera(33), 3500);
                        break;
                    case 33:
                        _zone.broadcastPacket(new SpecialCamera(CAMERA2, 800, 180, 0, 0, 5000, 0, 10, 1, 0),false);
                        ThreadPoolManager.getInstance().schedule(new Camera(34), 1000);
                        break;
                    case 34:
                        ReflectionUtils.getDoor(CORRDOOR).openMe();
                        ReflectionUtils.getDoor(COFFDOOR).openMe();
                        ThreadPoolManager.getInstance().schedule(new Camera(35), 4000);
                        break;
                    case 35:
                        CAMERA.deleteMe();
                        CAMERA2.deleteMe();
                        elfMovie.deleteMe();
                        _zone.getInsidePlayers().forEach(Player::leaveMovieMode);
                        break;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}