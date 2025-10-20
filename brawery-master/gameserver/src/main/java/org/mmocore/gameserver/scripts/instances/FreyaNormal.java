package org.mmocore.gameserver.scripts.instances;

import org.mmocore.commons.geometry.CustomPolygon;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.ai.CtrlEvent;
import org.mmocore.gameserver.listener.actor.OnCurrentHpDamageListener;
import org.mmocore.gameserver.listener.actor.OnDeathListener;
import org.mmocore.gameserver.listener.actor.ai.OnAiEventListener;
import org.mmocore.gameserver.listener.zone.OnZoneEnterLeaveListener;
import org.mmocore.gameserver.model.Territory;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.QuestState;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.network.lineage.components.NpcString;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.*;
import org.mmocore.gameserver.network.lineage.serverpackets.ExShowScreenMessage.ScreenMessageAlign;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.scripts.ai.freya.FreyaStandNormal;
import org.mmocore.gameserver.scripts.ai.freya.FreyaThrone;
import org.mmocore.gameserver.scripts.ai.freya.IceKnightNormal;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pchayka & iRock
 * <p/>
 * Инстанс Фреи в режиме общей сложности.
 */

public class FreyaNormal extends Reflection {
    private static final int FreyaThroneNpc = 29177;
    private static final int FreyaStandNormalNpc = 29179;
    private static final int IceKnightNormalNpc = 18855; //state 1 - in ice, state 2 - ice shattering, then normal state
    private static final int IceKnightLeaderNormal = 25699;
    private static final int IceCastleBreath = 18854;
    private static final int Glacier = 18853; // state 1 - falling, state 2 - waiting
    private static final int IceCastleController = 18932; // state 1-7
    private static final int Sirra = 32762;
    private static final int Jinia = 18850;
    private static final int Kegor = 18851;

    private static final int[] _eventTriggers = {
            23140202,
            23140204,
            23140206,
            23140208,
            23140212,
            23140214,
            23140216
    };

    private Zone damagezone = null, attackUp = null, pcbuff = null, pcbuff2 = null;

    private ScheduledFuture<?> stageTask = null;
    private ScheduledFuture<?> firstStageGuardSpawn = null;
    private ScheduledFuture<?> secondStageGuardSpawn = null;
    private ScheduledFuture<?> thirdStageGuardSpawn = null;
    private ScheduledFuture<?> newKnightTask = null;

    private ZoneListener _epicZoneListener = new ZoneListener();
    private ZoneListenerL _landingZoneListener = new ZoneListenerL();
    private ZoneListenerS _startZoneListener = new ZoneListenerS();
    private DeathListener _deathListener = new DeathListener();
    private CurrentHpListener _currentHpListener = new CurrentHpListener();
    private EventListener _eventListener = new EventListener();

    private boolean _entryLocked = false;
    private boolean _startLaunched = false;
    private boolean _freyaLaunched = false;
    private boolean _freyaSlayed = false;
    private boolean _thirdStageActive = false;

    private int _damageLevel = 0;
    private int _contollerLevel = 0;
    private int _stage;

    private AtomicInteger raidplayers = new AtomicInteger();

    private static final Territory centralRoom = new Territory().add(new CustomPolygon(8).add(114381, -113986).add(113908, -114463).add(113908, -115138).add(114384, -115612).add(115058, -115612).add(115532, -115135).add(115532, -114461).add(115056, -113985).setZmax(-11225).setZmin(-11225));
    private static final Location[] _points = new Location[]{
            new Location(113845, -113515, -11168, 57343),
            new Location(113380, -113984, -11176, 57343),
            new Location(113380, -115617, -11168, 8191),
            new Location(113848, -116082, -11168, 8191),
            new Location(115593, -116083, -11168, 24575),
            new Location(116059, -115615, -11168, 24575),
            new Location(116059, -113982, -11176, 40959),
            new Location(115593, -113516, -11168, 40959),
            new Location(112940, -114121, -10960, 0),
            new Location(112940, -114450, -10960, 0),
            new Location(112940, -115142, -10960, 0),
            new Location(112940, -115473, -10960, 0),
            new Location(116500, -115473, -10960, 32767),
            new Location(116500, -115140, -10960, 32767),
            new Location(116500, -114447, -10960, 32767),
            new Location(116500, -114118, -10960, 32767)

    };

    private Map<Location,NpcInstance> _staticKnights = new ConcurrentHashMap<>();
    private List<Location> _newKnightsLoc = new ArrayList<>();

    @Override
    protected void onCreate()
    {
        super.onCreate();

        attackUp = getZone("[freya_attack_up]");
        pcbuff = getZone("[freya_pc_buff1]");
        pcbuff2 = getZone("[freya_pc_buff2]");
        getZone("[freya_normal_epic]").addListener(_epicZoneListener);
        getZone("[freya_landing_room_epic]").addListener(_landingZoneListener);
        getZone("[freya_starting_room_epic]").addListener(_startZoneListener);
    }

    private void manageDamageZone(int level)
    {
        if(damagezone != null)
            damagezone.setActive(false);

        switch(level)
        {
            case 0:
                return;
            case 1:
                damagezone = getZone("[freya_normal_freezing_01]");
                break;
            case 2:
                damagezone = getZone("[freya_normal_freezing_02]");
                break;
            case 3:
                damagezone = getZone("[freya_normal_freezing_03]");
                break;
            case 4:
                damagezone = getZone("[freya_normal_freezing_04]");
                break;
            case 5:
                damagezone = getZone("[freya_normal_freezing_05]");
                break;
            case 6:
                damagezone = getZone("[freya_normal_freezing_06]");
                break;
            case 7:
                damagezone = getZone("[freya_normal_freezing_07]");
                break;
            default:
                break;
        }
        if(damagezone != null)
            damagezone.setActive(true);
    }

    private void manageAttackUpZone(boolean disable)
    {
        if(attackUp != null && disable)
        {
            attackUp.setActive(false);
            return;
        }
        if(attackUp != null)
            attackUp.setActive(true);
    }

    private void managePcBuffZone(boolean disable)
    {
        if(pcbuff != null && pcbuff2 != null && disable)
        {
            pcbuff.setActive(false);
            pcbuff2.setActive(false);
            return;
        }
        if(pcbuff != null)
            pcbuff.setActive(true);
        if(pcbuff2 != null)
            pcbuff2.setActive(true);
    }

    private void manageCastleController(int state)
    {
        // 1-7 enabled, 8 - disabled
        getNpcs().stream().filter(n -> n.getNpcId() == IceCastleController).forEach(n -> n.setNpcState(state));
    }

    private void manageStorm(boolean active)
    {
        getPlayers().stream().filter(p -> !p.isDead()).forEach(p -> {
            for (int _eventTrigger : _eventTriggers)
                p.sendPacket(new EventTrigger(_eventTrigger, active));
        });
    }

    private class StartNormalFreya extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            _entryLocked = true;
            closeDoor(23140101);
            for(Player player : getPlayers())
            {
                QuestState qs = player.getQuestState(10286);
                if(qs != null && qs.getCond() == 5)
                    qs.setCond(6);
                player.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_FREYA_OPENING);
            }
            stageTask = ThreadPoolManager.getInstance().schedule(new PreStage(), 55000L); // 53.5sec for movie
        }
    }

    private class PreStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            _stage = 0;
            getPlayers().forEach(Player::leaveMovieMode);
            //Spawning Freya Throne
            NpcInstance freyaTrhone = addSpawnWithoutRespawn(FreyaThroneNpc, new Location(114720, -117085, -11088, 15956), 0);
            freyaTrhone.addListener(_deathListener);
            freyaTrhone.addListener(_eventListener);

            //spawning few guards
            for(int i = 0; i < 5; i++)
                addSpawnWithoutRespawn(IceKnightNormalNpc, Territory.getRandomLoc(centralRoom, getGeoIndex()), 0).addListener(_eventListener);
            stageTask = ThreadPoolManager.getInstance().schedule(new PreStageM(freyaTrhone), 40000L);
        }
    }

    private class PreStageM extends RunnableImpl {
        NpcInstance _freyaTrhone; FreyaThrone _freyaAI;
        public PreStageM(NpcInstance freyaTrhone) // 1 - light, 2 - normal, 3 - hard, 4 - extreme
        {
            _freyaTrhone = freyaTrhone;
            _freyaAI = (FreyaThrone)freyaTrhone.getAI();
        }

        @Override
        public void runImpl() throws Exception {
            if(!_freyaLaunched) {
                _freyaLaunched = true;
                _freyaAI.addTaskMove(new Location(114720, -114796, -11200), true);
                for (Player player : getPlayers())
                    player.sendPacket(new ExShowScreenMessage(NpcString.FREYA_HAS_STARTED_TO_MOVE, 4000, ScreenMessageAlign.MIDDLE_CENTER, true));
            }
        }
    }

    private class FirstStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            _contollerLevel = 1;
            manageCastleController(_contollerLevel);
            _staticKnights.clear();
            for (Location point : _points) {
                NpcInstance knight = addSpawnWithoutRespawn(IceKnightNormalNpc, point, 0);
                knight.addListener(_eventListener);
                setStaticKnight(point, knight);
            }


            firstStageGuardSpawn = ThreadPoolManager.getInstance().scheduleAtFixedRate(new GuardSpawnTask(1), 2000L, 45000L);
        }
    }

    private class GuardSpawnTask extends RunnableImpl
    {
        private int _mode, _knightCount = 0, _breathCount = 0, _glacierCount = 0;
        private NpcInstance _boss;

        public GuardSpawnTask(int mode) // 1 - light, 2 - normal, 3 - hard, 4 - extreme
        {
            _mode = Math.max(Math.min(4, mode), 1);
        }

        @Override
        public void runImpl() throws Exception
        {
            if(FreyaNormal.this.isCollapseStarted())
                return;

            int count = 0;
            for(NpcInstance npc : getNpcs())
                if(!npc.isDead() && ++count > 60)
                    return;

            switch(_mode)
            {
                case 1:
                    _knightCount = 2;
                    _breathCount = 1;
                    _glacierCount = 1;
                    break;
                case 2:
                    _knightCount = 2;
                    _breathCount = 0;
                    _glacierCount = 1;
                    break;
                case 3:
                    _knightCount = 2;
                    _breathCount = 1;
                    _glacierCount = 1;
                    break;
                case 4:
                    break;
                default:
                    break;
            }
            count = 0;
            List<Location> points = new ArrayList<>(_staticKnights.keySet());
            Collections.shuffle(points, new Random(System.nanoTime()));
            for (Location point : points) {
                if(count == _knightCount)
                    break;
                NpcInstance knight = _staticKnights.get(point);
                if (knight != null && !knight.isDead() && knight.getNpcId() == IceKnightNormalNpc) {
                    IceKnightNormal knightAI = (IceKnightNormal) knight.getAI();
                    knightAI.DropIce();
                    count++;
                    _newKnightsLoc.add(point);
                    setStaticKnight(point,null);
                }


            }
            newKnightTask = ThreadPoolManager.getInstance().schedule(new GuardSpawn(_newKnightsLoc), 15000L);
            _boss = getAllByNpcId(_mode == 4 ? FreyaStandNormalNpc : FreyaThroneNpc, true).isEmpty() ? null : getAllByNpcId(_mode == 3 ? FreyaStandNormalNpc : FreyaThroneNpc, true).get(0);
            for (int i = 0; i < _breathCount; i++)
                if (_boss != null)
                    addSpawnWithoutRespawn(IceCastleBreath, Location.findAroundPosition(_boss, 250, 270), 0);
            if(Rnd.chance(80) && getAllByNpcId(Glacier,true).size() < 7) {
                for (int i = 0; i < _glacierCount; i++) {
                    NpcInstance glacier = addSpawnWithoutRespawn(Glacier, Territory.getRandomLoc(centralRoom, getGeoIndex()), 0);
                    glacier.addListener(_deathListener);
                }

            }
            manageDamageZone(countGlacier());
        }
    }

    private class GuardSpawn extends RunnableImpl {
        private List<Location> _knights;

        public GuardSpawn(List<Location> newKnights)
        {
            _knights = newKnights;
        }

        @Override
        public void runImpl() throws Exception {
            for (Location knightSpawn : _knights) {
                NpcInstance knight = addSpawnWithoutRespawn(IceKnightNormalNpc, knightSpawn, 0);
                knight.addListener(_deathListener);
                knight.addListener(_eventListener);
                setStaticKnight(knightSpawn,knight);
            }
            _newKnightsLoc.clear();
        }
    }

    private synchronized void setStaticKnight(Location loc, NpcInstance knight){
        if(knight==null)
            _staticKnights.remove(loc);
        else
            _staticKnights.put(loc,knight);
    }

    private int countGlacier(){

        _damageLevel = getAllByNpcId(Glacier,true).size();
        if(_damageLevel>7)
            _damageLevel = 7;
        else if(_damageLevel <0)
            _damageLevel = 0;
        return _damageLevel;
    }

    private class PreSecondStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            firstStageGuardSpawn.cancel(true);
            newKnightTask.cancel(true);
            getNpcs().stream().filter(n -> n.getNpcId() != Sirra && n.getNpcId() != IceCastleController).forEach(NpcInstance::deleteMe);
            manageDamageZone(0);
            for(Player p : getPlayers())
                p.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_FREYA_PHASE_A);
            stageTask = ThreadPoolManager.getInstance().schedule(new TimerToSecondStage(), 22000L); // 22.1 secs for movie
        }
    }

    private class TimerToSecondStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            manageCastleController(8);
            for(Player p : getPlayers()){
                p.sendPacket(new ExSendUIEvent(p, false, false, 60, 0, NpcString.TIME_REMAINING_UNTIL_NEXT_BATTLE));
                p.leaveMovieMode();
            }
            stageTask = ThreadPoolManager.getInstance().schedule(new SecondStage(), 60000L);
        }
    }

    private class SecondStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            _stage = 2;
            _contollerLevel = 3;
            manageCastleController(_contollerLevel);
            for(Player p : getPlayers())
                p.sendPacket(new ExShowScreenMessage(NpcString.BEGIN_STAGE_2_FREYA, 6000, ScreenMessageAlign.TOP_CENTER, true, 1, -1, true));
            //spawning few guards
            for (int i = 0; i < 3; i++)
                addSpawnWithoutRespawn(IceKnightNormalNpc, Territory.getRandomLoc(centralRoom, getGeoIndex()), 0);
            _staticKnights.clear();
            for (Location point : _points) {
                NpcInstance knight = addSpawnWithoutRespawn(IceKnightNormalNpc, point, 0);
                knight.addListener(_deathListener);
                setStaticKnight(point, knight);
            }
            secondStageGuardSpawn = ThreadPoolManager.getInstance().scheduleAtFixedRate(new GuardSpawnTask(2), 2000L, 30000L);
            stageTask = ThreadPoolManager.getInstance().schedule(new KnightCaptainSpawnMovie(), 60000L);
        }
    }

    private class KnightCaptainSpawnMovie extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            newKnightTask.cancel(true);
            getNpcs().forEach(NpcInstance::block);
            for(Player p : getPlayers())
                p.showQuestMovie(ExStartScenePlayer.SCENE_ICE_HEAVYKNIGHT_SPAWN);
            stageTask = ThreadPoolManager.getInstance().schedule(new KnightCaptainSpawn(), 7500L);
        }
    }

    private class KnightCaptainSpawn extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            getNpcs().stream().filter(n -> n.getNpcId() != Glacier).forEach(NpcInstance::unblock);
            NpcInstance knightLeader = addSpawnWithoutRespawn(IceKnightLeaderNormal, new Location(114707, -114799, -11199, 15956), 0);
            knightLeader.addListener(_deathListener);
            getPlayers().forEach(Player::leaveMovieMode);
        }
    }

    private class PreThirdStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            secondStageGuardSpawn.cancel(true);
            newKnightTask.cancel(true);
            manageDamageZone(0);
            manageCastleController(8);
            for(Player p : getPlayers())
                p.sendPacket(new ExSendUIEvent(p, false, false, 60, 0, NpcString.TIME_REMAINING_UNTIL_NEXT_BATTLE));
            getNpcs().stream().filter(n -> n.getNpcId() != Sirra && n.getNpcId() != IceCastleController).forEach(NpcInstance::deleteMe);
            stageTask = ThreadPoolManager.getInstance().schedule(new PreThirdStageM(), 60000L);
        }
    }

    private class PreThirdStageM extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            for(Player p : getPlayers())
                p.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_FREYA_PHASE_B);
            stageTask = ThreadPoolManager.getInstance().schedule(new ThirdStage(), 22000L); // 21.5 secs for movie
        }
    }

    private class ThirdStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            _stage = 3;
            _contollerLevel = 4;
            manageCastleController(_contollerLevel);
            manageAttackUpZone(false);
            manageStorm(true);
            _thirdStageActive = true;
            for(Player p : getPlayers())
            {
                p.sendPacket(new ExShowScreenMessage(NpcString.BEGIN_STAGE_3_FREYA, 6000, ScreenMessageAlign.TOP_CENTER, true, 1, -1, true));
                p.sendPacket(new ExChangeClientEffectInfo(2));
            }
            _staticKnights.clear();
            for (Location point : _points) {
                NpcInstance knight = addSpawnWithoutRespawn(IceKnightNormalNpc, point, 0);
                knight.addListener(_eventListener);
                setStaticKnight(point, knight);
            }
            thirdStageGuardSpawn = ThreadPoolManager.getInstance().scheduleAtFixedRate(new GuardSpawnTask(3), 2000L, 30000L);
            NpcInstance freyaStand = addSpawnWithoutRespawn(FreyaStandNormalNpc, new Location(114720, -117085, -11088, 15956), 0);
            FreyaStandNormal _freyaAI = (FreyaStandNormal) freyaStand.getAI();
            _freyaAI.addTaskMove(new Location(114720, -114796, -11200), true);
            freyaStand.addListener(_currentHpListener);
            freyaStand.addListener(_deathListener);
        }
    }

    private class PreForthStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            newKnightTask.cancel(true);
            getNpcs().forEach(NpcInstance::block);
            for(Player p : getPlayers())
            {
                p.block();
                p.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_KEGOR_INTRUSION);
            }
            stageTask = ThreadPoolManager.getInstance().schedule(new ForthStage(), 28000L); // 27 secs for movie
        }
    }

    private class ForthStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            _stage = 4;
            getNpcs().stream().filter(n -> n.getNpcId() != Glacier).forEach(NpcInstance::unblock);
            for(Player p : getPlayers())
            {
                p.unblock();
                p.sendPacket(new ExShowScreenMessage(NpcString.BEGIN_STAGE_4_FREYA, 6000, ScreenMessageAlign.TOP_CENTER, true, 1, -1, true));
                p.leaveMovieMode();
            }
            addSpawnWithoutRespawn(Jinia, new Location(114727, -114700, -11200, -16260), 0);
            addSpawnWithoutRespawn(Kegor, new Location(114690, -114700, -11200, -16260), 0);
            managePcBuffZone(false);
        }
    }

    private class FreyaDeathStage extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            setReenterTime(System.currentTimeMillis());
            //Guard spawn task cancellation
            stopTasks();
            //switching off zones
            manageDamageZone(0);
            manageAttackUpZone(true);
            managePcBuffZone(true);
            manageCastleController(8);
            //Deleting all NPCs + Freya corpse
            getNpcs().forEach(NpcInstance::deleteMe);
            //Cancelling zone info
            _thirdStageActive = false;
            manageStorm(false);
            //Movie + quest update
            for(Player p : getPlayers())
            {
                QuestState qs = p.getQuestState(10286);
                if(qs != null && qs.getCond() == 6)
                    qs.setCond(7);
                p.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_FREYA_ENDING_A);
                p.sendPacket(new ExChangeClientEffectInfo(1));
            }

            // Spawning Kegor + defeated Freya
            NpcInstance kegor2 = addSpawnWithoutRespawn(32761, new Location(114872, -114744, -11200, 32768), 0);
            kegor2.setNpcState(2);
            NpcInstance defeatedFreya = addSpawnWithoutRespawn(FreyaStandNormalNpc, new Location(114767, -114795, -11200), 0);
            defeatedFreya.setRHandId(15280);
            defeatedFreya.block();
            defeatedFreya.startDamageBlocked();
            defeatedFreya.setShowName(false);
            defeatedFreya.setTargetable(false);
            startCollapseTimer(600 * 1000L);
        }
    }

    public void notifyElimination()
    {
        getNpcs().forEach(NpcInstance::deleteMe);
        for(Player p : getPlayers())
            p.showQuestMovie(ExStartScenePlayer.SCENE_BOSS_FREYA_ENDING_B);
        stageTask = ThreadPoolManager.getInstance().schedule(new InstanceConclusion(), 57000L); // 56 secs for movie
    }

    private class InstanceConclusion extends RunnableImpl
    {
        @Override
        public void runImpl() throws Exception
        {
            getPlayers().forEach(Player::leaveMovieMode);
            startCollapseTimer(600 * 1000L);
            for(Player p : getPlayers())
                p.sendPacket(new SystemMessage(SystemMsg.THIS_DUNGEON_WILL_EXPIRE_IN_S1_MINUTES).addNumber(10));
        }
    }

    private class DeathListener implements OnDeathListener
    {
        @Override
        public void onDeath(Creature self, Creature killer)
        {
            if(self.getNpcId() == FreyaThroneNpc)
                ThreadPoolManager.getInstance().execute(new PreSecondStage());
            else if(self.getNpcId() == IceKnightLeaderNormal)
                ThreadPoolManager.getInstance().execute(new PreThirdStage());
            else if(self.getNpcId() == FreyaStandNormalNpc)
                ThreadPoolManager.getInstance().execute(new FreyaDeathStage());
            else if(self.getNpcId() == Glacier) {
                manageDamageZone(countGlacier());
            }
        }
    }

    public class EventListener implements OnAiEventListener {

        @Override
        public void onAiEvent(Creature actor, CtrlEvent evt, Object[] args) {
            if(actor.getNpcId() == FreyaThroneNpc && (evt == CtrlEvent.EVT_AGGRESSION ||evt == CtrlEvent.EVT_ATTACKED)) {
                if(_stage == 0){
                    _stage = 1;
                    ThreadPoolManager.getInstance().execute(new FirstStage());
                }
                //screen message
                if(!_freyaLaunched) {
                    stageTask.cancel(false);
                    for (Player player : getPlayers())
                        player.sendPacket(new ExShowScreenMessage(NpcString.FREYA_HAS_STARTED_TO_MOVE, 4000, ScreenMessageAlign.MIDDLE_CENTER, true));
                    _freyaLaunched = true;
                }
                actor.removeListener(_eventListener);
            }
            else if(_stage == 0 && actor.getNpcId() == IceKnightNormalNpc && (evt == CtrlEvent.EVT_AGGRESSION ||evt == CtrlEvent.EVT_ATTACKED)) {
                _stage = 1;
                ThreadPoolManager.getInstance().execute(new FirstStage());
                //screen message
                for (Player player : getPlayers())
                    player.sendPacket(new ExShowScreenMessage(NpcString.BEGIN_STAGE_1_FREYA, 6000, ScreenMessageAlign.TOP_CENTER, true, 1, -1, true));

                actor.removeListener(_eventListener);
            }
            else if(actor.getNpcId() == IceKnightNormalNpc && evt == CtrlEvent.EVT_ATTACKED && _staticKnights.containsValue(actor)) {
                for(Map.Entry<Location,NpcInstance> entry: _staticKnights.entrySet()) {
                    if (actor.equals(entry.getValue())) {
                        Location point = entry.getKey();
                        setStaticKnight(point,null);
                        actor.removeListener(_eventListener);
                        NpcInstance knight = addDelayedSpawn(IceKnightNormalNpc,point,0,25);
                        knight.addListener(_deathListener);
                        knight.addListener(_eventListener);
                        setStaticKnight(point,knight);
                        break;
                    }
                }

            }
            else if(_stage!= 0 &&  evt == CtrlEvent.EVT_AGGRESSION && !_staticKnights.containsValue(actor)) {
                actor.removeListener(_eventListener);
            }

        }
    }

    public class CurrentHpListener implements OnCurrentHpDamageListener
    {
        @Override
        public void onCurrentHpDamage(Creature actor, double damage, Creature attacker, SkillEntry skill)
        {
            if(actor.isDead() || actor.getNpcId() != FreyaStandNormalNpc)
                return;
            double newHp = actor.getCurrentHp() - damage;
            double maxHp = actor.getMaxHp();
            if(!_freyaSlayed && newHp <= 0.2 * maxHp)
            {
                _freyaSlayed = true;
                actor.removeListener(_currentHpListener);
                if(newHp > 0)
                    ThreadPoolManager.getInstance().execute(new PreForthStage());
            }
        }
    }

    public class ZoneListener implements OnZoneEnterLeaveListener
    {
        @Override
        public void onZoneEnter(Zone zone, Creature cha)
        {
            if(_entryLocked)
                return;

            if(!cha.isPlayer())
                return;

            Player player = cha.getPlayer();

            if(_thirdStageActive)
                player.sendPacket(new ExChangeClientEffectInfo(2));
            if(!_startLaunched && raidplayers.incrementAndGet() == getInstancedZone().getMinParty() && getZone("[freya_starting_room_epic]").getInsidePlayers().size()>0)
            {
                _startLaunched = true;
                stageTask = ThreadPoolManager.getInstance().schedule(new StartNormalFreya(), 30000L);
            }

        }

        @Override
        public void onZoneLeave(Zone zone, Creature cha)
        {
            if(!cha.isPlayer())
                return;
            raidplayers.decrementAndGet();
        }
    }

    public class ZoneListenerL implements OnZoneEnterLeaveListener
    {
        @Override
        public void onZoneEnter(Zone zone, Creature cha)
        {
            if(cha.isPlayer())
                cha.sendPacket(new ExChangeClientEffectInfo(1));
        }

        @Override
        public void onZoneLeave(Zone zone, Creature cha)
        {
        }
    }

    public class ZoneListenerS implements OnZoneEnterLeaveListener
    {
        @Override
        public void onZoneEnter(Zone zone, Creature cha)
        {
            if(_entryLocked)
                return;

            if(!cha.isPlayer())
                return;

            if(!_startLaunched && raidplayers.get() >= getInstancedZone().getMinParty())
            {
                _startLaunched = true;
                stageTask = ThreadPoolManager.getInstance().schedule(new StartNormalFreya(), 30000L);
            }
        }

        @Override
        public void onZoneLeave(Zone zone, Creature cha)
        {
        }
    }

    private void stopTasks()
    {
        if(stageTask != null)
            stageTask.cancel(false);
        if(newKnightTask != null)
            newKnightTask.cancel(false);
        if(firstStageGuardSpawn != null)
            firstStageGuardSpawn.cancel(false);
        if(secondStageGuardSpawn != null)
            secondStageGuardSpawn.cancel(false);
        if(thirdStageGuardSpawn != null)
            thirdStageGuardSpawn.cancel(false);
    }

    @Override
    protected void onCollapse()
    {
        stopTasks();
        super.onCollapse();
    }
}