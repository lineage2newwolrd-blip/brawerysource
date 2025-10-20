package org.mmocore.gameserver.data.xml.holder;

import org.jts.dataparser.data.holder.FreewayInfoHolder;
import org.jts.dataparser.data.holder.freeway.Freeway;
import org.mmocore.commons.data.AbstractHolder;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoinCoordinate;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoint;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPointRail;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Felixx Company: J Develop Station
 */
public final class SuperPointHolder extends AbstractHolder {
    private static final SuperPointHolder INSTANCE = new SuperPointHolder();

    private final Map<String, SuperPoint> superPointMap = new HashMap<>();

    private SuperPointHolder() {
    }

    public static SuperPointHolder getInstance() {
        return INSTANCE;
    }

    public void addSuperPoints(final String pointName, final SuperPoint superPoints) {
        superPointMap.put(pointName, superPoints);
    }

    public SuperPoint getSuperPointsByName(final String pointName) {
        return superPointMap.get(pointName);
    }

    @Override
    public int size() {
        return superPointMap.size();
    }

    @Override
    public void clear() {
        superPointMap.clear();
    }

    @Override
    public void afterParsing() {
        super.afterParsing();
        for(Freeway freeway : FreewayInfoHolder.getInstance().getFreeways()) {
            final String pointId = String.valueOf(freeway.freeway_id);
            final SuperPoint superpoint = new SuperPoint(pointId);
            for (final Freeway.FreewayNode pointElement : freeway.nodes) {
                final int x = pointElement.pos.x;
                final int y = pointElement.pos.y;
                final int z = pointElement.pos.z;

                final SuperPoinCoordinate coords = new SuperPoinCoordinate(x, y, z);
                coords.setDelayInSec(pointElement.delay);
                coords.setSocialId(pointElement.social);

                superpoint.addMoveCoordinats(coords);
            }
            addSuperPoints(pointId, superpoint);
        }
    }
}