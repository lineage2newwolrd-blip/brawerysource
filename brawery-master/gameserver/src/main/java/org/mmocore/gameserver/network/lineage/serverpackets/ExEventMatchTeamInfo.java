package org.mmocore.gameserver.network.lineage.serverpackets;


import org.mmocore.gameserver.network.lineage.components.GameServerPacket;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.Servitor;

import java.util.ArrayList;
import java.util.List;


public class ExEventMatchTeamInfo extends GameServerPacket {
    private final List<EventMatchTeamInfo> members = new ArrayList<>();
    private int leader_id;
    private final int side;

    public ExEventMatchTeamInfo(final List<Player> group, final Player exclude) {
        leader_id = group.get(0).getObjectId();
        side = group.get(0).getTeam().ordinal();

        for (final Player member : group) {
            if (member != exclude) {
                members.add(new EventMatchTeamInfo(member));
            }
        }
    }

    public ExEventMatchTeamInfo(final List<Player> group, int team)
    {
        leader_id = group.get(0).getObjectId();
        side = team;
        for (final Player member : group) {
                members.add(new EventMatchTeamInfo(member));
        }
    }

    @Override
    protected void writeData() {
        writeD(0x00);// Race id
        writeC(side);//side
        writeD(members.size());
        for(EventMatchTeamInfo member : members) {
            writeD(member.id);
            writeS(member.name);
            writeD(member.curHp);
            writeD(member.maxHp);
            writeD(member.curMp);
            writeD(member.maxMp);
            writeD(member.curCp);
            writeD(member.maxCp);
            writeD(member.level);
            writeD(member.class_id);
            writeD(0);//unknown
            writeD(0);//unknown
        }
    }

    public static class EventMatchTeamInfo {
        public final String name;
        public final int id;
        public final int curCp;
        public final int maxCp;
        public final int curHp;
        public final int maxHp;
        public final int curMp;
        public final int maxMp;
        public final int level;
        public final int class_id;
        public final int race_id;
        public final int pet_id;
        public String pet_Name;
        public int pet_NpcId;
        public int pet_curHp;
        public int pet_maxHp;
        public int pet_curMp;
        public int pet_maxMp;
        public int pet_level;

        public EventMatchTeamInfo(final Player member) {
            name = member.getName();
            id = member.getObjectId();
            curCp = (int) member.getCurrentCp();
            maxCp = member.getMaxCp();
            curHp = (int) member.getCurrentHp();
            maxHp = (int) member.getMaxHp();
            curMp = (int) member.getCurrentMp();
            maxMp = member.getMaxMp();
            level = member.getLevel();
            class_id = member.getPlayerClassComponent().getClassId().getId();
            race_id = member.getPlayerTemplateComponent().getPlayerRace().ordinal();

            final Servitor pet = member.getServitor();
            if (pet != null) {
                pet_id = pet.getObjectId();
                pet_NpcId = pet.getNpcId() + 1000000;
                pet_Name = pet.getName();
                pet_curHp = (int) pet.getCurrentHp();
                pet_maxHp = (int) pet.getMaxHp();
                pet_curMp = (int) pet.getCurrentMp();
                pet_maxMp = pet.getMaxMp();
                pet_level = pet.getLevel();
            } else {
                pet_id = 0;
            }
        }
    }
}