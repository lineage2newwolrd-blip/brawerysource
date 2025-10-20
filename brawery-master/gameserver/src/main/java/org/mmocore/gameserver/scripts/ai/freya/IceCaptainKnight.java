package org.mmocore.gameserver.scripts.ai.freya;

import org.mmocore.commons.geometry.CustomPolygon;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ai.CtrlEvent;
import org.mmocore.gameserver.ai.Fighter;
import org.mmocore.gameserver.model.Territory;
import org.mmocore.gameserver.model.entity.Reflection;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.network.lineage.components.NpcString;
import org.mmocore.gameserver.network.lineage.serverpackets.ExShowScreenMessage;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.utils.ChatUtils;

/**
 * @author iRock
 */

public class IceCaptainKnight extends Fighter {
    private long _destroyTimer = 0;
    private boolean _destroyUsed = false;
    private boolean _isHard = false;
    Reflection r = _actor.getReflection();
    private static Territory centralRoom = new Territory().add(new CustomPolygon(8)
            .add(114264, -113672).add(113640, -114344).add(113640, -115240)
            .add(114264, -115912).add(115176, -115912).add(115800, -115272)
            .add(115800, -114328).add(115192, -113672)
            .setZmax(-11225).setZmin(-11225));

    public IceCaptainKnight(NpcInstance actor)
    {
        super(actor);
    }

    @Override
    protected void onEvtSpawn()
    {
        super.onEvtSpawn();
        _destroyTimer = System.currentTimeMillis();
        _isHard = r.getInstancedZoneId() == 144;
    }

    @Override
    protected void thinkAttack()
    {
        NpcInstance actor = getActor();
        if(!_destroyUsed && _destroyTimer + 60 * 1000L < System.currentTimeMillis())
        {
            _destroyUsed = true;
            int mode = Rnd.get(3);
            if(!r.isDefault())
                for(Player p : r.getPlayers())
                    p.sendPacket(new ExShowScreenMessage(NpcString.THE_SPACE_FEELS_LIKE_ITS_GRADUALLY_STARTING_TO_SHAKE, 5000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
            int count = _isHard ? 7 : 5;
            if(!r.isDefault())
                switch(mode)
                {
                    case 0:
                        ChatUtils.shout(getActor(), NpcString.ARCHER);//breath
                        for(int i = 0; i < count-2; i++)
                            r.addSpawnWithoutRespawn(18854, Territory.getRandomLoc(centralRoom, r.getGeoIndex()), 0);
                        break;
                    case 1:
                        ChatUtils.shout(getActor(), NpcString.MY_KNIGHTS);//knight
                        for(int i = 0; i < count; i++)
                            r.addSpawnWithoutRespawn(_isHard ? 18856 : 18855, Territory.getRandomLoc(centralRoom, r.getGeoIndex()), 0);
                        break;
                    case 2:
                        ChatUtils.shout(getActor(), NpcString.I_CAN_TAKE_IT_NO_LONGER);//glacier
                        for(int i = 0; i < count-2; i++)
                            r.addSpawnWithoutRespawn(18853, Territory.getRandomLoc(centralRoom, r.getGeoIndex()), 0);
                        break;
                }

        }
        // Оповещение минионов
        if(System.currentTimeMillis() - _lastFactionNotifyTime > _minFactionNotifyInterval)
        {
            _lastFactionNotifyTime = System.currentTimeMillis();
            actor.getReflection().getNpcs().stream().filter(npc -> npc.isMonster() && npc != actor).forEach(npc -> npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, actor.getAggroList().getMostHated(), 5));
        }

        super.thinkAttack();
    }

    @Override
    protected void teleportHome()
    {
    }

    @Override
    protected boolean isGlobalAggro(){return true;}
}