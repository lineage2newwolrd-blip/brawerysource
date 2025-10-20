package org.mmocore.gameserver.scripts.npc.model;


import org.mmocore.gameserver.model.instances.MonsterInstance;
import org.mmocore.gameserver.network.lineage.serverpackets.L2GameServerPacket;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.templates.npc.NpcTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by iRock
 */
public final class ZakenAnchorInstance extends MonsterInstance
{

    public ZakenAnchorInstance(int objectId, NpcTemplate template)
    {
        super(objectId, template);
        setUndying(true);
    }

    @Override
    public void broadcastCharInfo()
    {
        return;
    }

    @Override
    public boolean isFearImmune()
    {
        return true;
    }

    @Override
    public boolean isParalyzeImmune()
    {
        return true;
    }

    @Override
    public boolean isLethalImmune()
    {
        return true;
    }

    @Override
    public void onAction(final Player player, final boolean shift)
    {
        player.sendActionFailed();
    }

    @Override
    public List<L2GameServerPacket> addPacketList(final Player forPlayer, final Creature dropper)
    {
        // если не обезврежена и не овнер, ниче не показываем
            return Collections.emptyList();
    }

    @Override
    public void checkAndRemoveInvisible()
    {
        return;
    }

}