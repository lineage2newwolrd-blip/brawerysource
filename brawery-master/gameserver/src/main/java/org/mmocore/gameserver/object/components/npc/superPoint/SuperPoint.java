package org.mmocore.gameserver.object.components.npc.superPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Felixx Company: J Develop Station
 */
public class SuperPoint {
    private String name;
    private boolean isRunning;
    private SuperPointType type;
    private SuperPointRail rail;
    private List<SuperPoinCoordinate> points = new ArrayList<>();

    public SuperPoint(String name){
        this.name = name;
    }

    public void addMoveCoordinats(SuperPoinCoordinate coords) {
        points.add(coords);
    }

    public List<SuperPoinCoordinate> getCoordinats() {
        return points;
    }

    public SuperPointType getType() {
        return type;
    }

    public void setType(SuperPointType type) {
        this.type = type;
    }

    public SuperPointRail getRail() {
        return rail;
    }

    public void setRail(SuperPointRail rail) {
        this.rail = rail;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public String getName() {
        return name;
    }
}