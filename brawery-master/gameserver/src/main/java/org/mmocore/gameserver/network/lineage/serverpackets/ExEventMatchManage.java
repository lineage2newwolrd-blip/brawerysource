package org.mmocore.gameserver.network.lineage.serverpackets;

import org.mmocore.gameserver.network.lineage.components.GameServerPacket;

import java.util.ArrayList;
import java.util.List;

public class ExEventMatchManage extends GameServerPacket {

    private final List<PlayerInfo> playerList = new ArrayList<>();
    private final int state, redstate, bluestate, arena;
    private final String redName, blueName;
    public ExEventMatchManage(int state, String blueName, int bluestate, String redName, int redstate, int arena)
    {
        this.state = state;
        this.redName = redName;
        this.redstate = redstate;
        this.blueName = blueName;
        this.bluestate = bluestate;
        this.arena = arena;
    }

    public ExEventMatchManage()
    {
        state = 0;
        redName = "";
        redstate = 0;
        blueName = "";
        bluestate = 0;
        arena = 0;
    }

    public void addPlayer(int team, int objectId, String name, int classId, int level, int leader)
    {
        playerList.add(new PlayerInfo(team, objectId, name, classId, level, leader));
    }

    @Override
    protected void writeData() {
        writeD(arena); // Race id
        writeC(state); // state: 0 - not created, 1-not in progress, 2 - started, 3 - paused
        writeC(0x00); // fence: 0 - not setted, 1-column, 2-wall
        writeS(blueName); // team 2 name
        writeC(bluestate); // team 2 party status 0: not locked, 1 party locked
        writeS(redName); // Team 1 name
        writeC(redstate); // team 1 party status 0: not locked, 1 party locked
        writeD(playerList.size()); // players's size
        for(PlayerInfo member : playerList) {
            writeC(member.team); // player team id
            writeC(member.leader); // party representive (leader)
            writeD(member.objectId); // player object id
            writeS(member.name); // player name
            writeD(member.classId); // player class id
            writeD(member.level); // player level
        }
    }

    public static class PlayerInfo
    {
        public final int team, objectId, classId, level, leader;
        public final String name;
        public PlayerInfo(int team, int objectId, String name, int classId, int level, int leader){
            this.team = team;
            this.objectId = objectId;
            this.name = name;
            this.classId = classId;
            this.level = level;
            this.leader = leader;
        }
    }
}