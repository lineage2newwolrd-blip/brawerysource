package org.mmocore.gameserver.network.xmlrpc.handler;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.mmocore.commons.net.xmlrpc.handler.Handler;
import org.mmocore.commons.net.xmlrpc.handler.Message;
import org.mmocore.gameserver.cache.CrestCache;
import org.mmocore.gameserver.configuration.config.community.CBasicConfig;
import org.mmocore.gameserver.data.xml.holder.ResidenceHolder;
import org.mmocore.gameserver.database.DatabaseFactory;
import org.mmocore.gameserver.manager.OlympiadHistoryManager;
import org.mmocore.gameserver.model.entity.Hero;
import org.mmocore.gameserver.model.entity.olympiad.Olympiad;
import org.mmocore.gameserver.model.entity.residence.Castle;
import org.mmocore.gameserver.model.entity.residence.Dominion;
import org.mmocore.gameserver.network.lineage.components.CustomMessage;
import org.mmocore.gameserver.tables.ClanTable;
import org.mmocore.gameserver.templates.StatsSet;
import org.mmocore.gameserver.utils.DDSReader;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.TimeUtils;
import org.mmocore.gameserver.world.GameObjectsStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KilRoy
 */
public class WorldHandler extends Handler {

    private final static String TOPPLAYER_SET = "SELECT c.char_name, cl.class_id, c.pvpkills, c.pkkills, cl.level, ifnull(round(sum(it.count)/1000000),0) FROM characters c join character_subclasses cl on cl.char_obj_id=c.obj_id left join items it on it.owner_id=c.obj_id and it.item_id = 57 where cl.isBase=1 and c.accesslevel=0 and c.account_name not like '~%' GROUP BY c.char_name, cl.class_id, c.pvpkills, c.pkkills, cl.level";

    private final static String QUERRY_TOPLEVEL = TOPPLAYER_SET + " ORDER BY cl.exp DESC LIMIT 0, ?";
    private final static String QUERRY_TOPADENA = TOPPLAYER_SET + " ORDER BY sum(it.count) DESC LIMIT 0, ?";
    private final static String QUERRY_TOPPVP = TOPPLAYER_SET + " ORDER BY `pvpkills` DESC LIMIT 0, ?";
    private final static String QUERRY_TOPPK = TOPPLAYER_SET + " ORDER BY `pkkills` DESC LIMIT 0, ?";
    private final static String QUERRY_TOPCLAN = "SELECT cd.clan_id, cd.clan_level, cd.reputation_score, ifnull(al.crest,\"\") ally_crest, ifnull(cd.crest,\"\") clan_crest FROM clan_data cd left join ally_data al on al.ally_id=cd.ally_id ORDER BY cd.clan_level DESC, cd.reputation_score DESC LIMIT 0, ?";
    private final static String QUERRY_BANLIST = "SELECT c.char_name, bans.baned, bans.unban, bans.reason, bans.endban FROM bans join characters c on c.obj_id=bans.obj_id WHERE endban IS NOT NULL AND endban>unix_timestamp() order by endban desc LIMIT 0, ?";
    private final static String QUERRY_PLAYERS = "SELECT count(*) FROM characters";
    /**
     * Пустой метод для запроса на сервер, чтобы понять, запущен он или нет.
     */
    public String idle()
    {
        return json(Message.OK);
    }

    /**
     * @return количество игроков онлайн
     */
    public String xmlrpcGetOnline()
    {
        return json(String.valueOf((int)(GameObjectsStorage.getAllPlayersSize() * CBasicConfig.increaseOnline)));
    }

    /**
     * @return количество игроков
     */
    public String xmlrpcGetPlayers()
    {
        int count = 0;
        try
        {
            conn = DatabaseFactory.getInstance().getConnection();
            statement = conn.prepareStatement(QUERRY_PLAYERS);
            resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                count  = resultSet.getInt(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return json(Message.DATABASE_ERROR);
        }
        finally
        {
            databaseClose(true);
        }
        return json(String.valueOf(Math.max(0, count)));
    }

    public String xmlrpcTopPK(int count)
    {
        List<TopPlayer> players = new ArrayList<TopPlayer>();
        try
        {
            conn = DatabaseFactory.getInstance().getConnection();
            statement = conn.prepareStatement(QUERRY_TOPPK);
            statement.setInt(1, count);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String clazz = getClassNameById(Integer.parseInt(resultSet.getString(2)));
                players.add(new TopPlayer(resultSet.getString(1), clazz, resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return json(Message.DATABASE_ERROR);
        }
        finally
        {
            databaseClose(true);
        }
        return json(players);
    }

    public String xmlrpcTopPvP(int count)
    {
        List<TopPlayer> players = new ArrayList<TopPlayer>();
        try
        {
            conn = DatabaseFactory.getInstance().getConnection();
            statement = conn.prepareStatement(QUERRY_TOPPVP);
            statement.setInt(1, count);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String clazz = getClassNameById(Integer.parseInt(resultSet.getString(2)));
                players.add(new TopPlayer(resultSet.getString(1), clazz, resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return json(Message.DATABASE_ERROR);
        }
        finally
        {
            databaseClose(true);
        }
        return json(players);
    }

    public String xmlrpcTopLevel(int count)
    {
        List<TopPlayer> players = new ArrayList<TopPlayer>();
        try
        {
            conn = DatabaseFactory.getInstance().getConnection();
            statement = conn.prepareStatement(QUERRY_TOPLEVEL);
            statement.setInt(1, count);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String clazz = getClassNameById(Integer.parseInt(resultSet.getString(2)));
                players.add(new TopPlayer(resultSet.getString(1), clazz, resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return json(Message.DATABASE_ERROR);
        }
        finally
        {
            databaseClose(true);
        }
        return json(players);
    }

    public String xmlrpcTopAdena(int count)
    {
        List<TopPlayer> players = new ArrayList<TopPlayer>();
        try
        {
            conn = DatabaseFactory.getInstance().getConnection();
            statement = conn.prepareStatement(QUERRY_TOPADENA);
            statement.setInt(1, count);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                String clazz = getClassNameById(Integer.parseInt(resultSet.getString(2)));
                players.add(new TopPlayer(resultSet.getString(1), clazz, resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return json(Message.DATABASE_ERROR);
        }
        finally
        {
            databaseClose(true);
        }
        return json(players);
    }

    public String xmlrpcHeroes()
    {
        List<HeroPlayer> players = new ArrayList<HeroPlayer>();
        for(StatsSet hero : Hero.getInstance().getAllHeroes().values())
        {
            int[] stat = OlympiadHistoryManager.getInstance().getMatchStat(hero.getInteger(Olympiad.CHAR_ID));
            String clazz = getClassNameById(hero.getInteger(Olympiad.CLASS_ID));
            players.add(new HeroPlayer(hero.getString(Olympiad.CHAR_NAME), clazz, String.valueOf(stat[0]), String.valueOf(stat[1])));
        }

        return json(players);
    }

    public String getTopClan(int count)
    {
        List<TopClan> clans = new ArrayList<TopClan>();
        try
        {
            conn = DatabaseFactory.getInstance().getConnection();
            statement = conn.prepareStatement(QUERRY_TOPCLAN);
            statement.setInt(1, count);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int clandId = resultSet.getInt(1);
                String clanLevel = resultSet.getString(2);
                String reputationScore = resultSet.getString(3);
                byte[] allyCrest = resultSet.getBytes(4);
                byte[] clanCrest = resultSet.getBytes(5);
                clans.add(new TopClan(ClanTable.getInstance().getClan(clandId).getName(), String.valueOf(ClanTable.getInstance().getClan(clandId).getAllSize()), reputationScore, ClanTable.getInstance().getClan(clandId).getLeaderName(), clanLevel, allyCrest.length==0? "": Base64.encode(DDSReader.convertToPng(allyCrest, 8, 12)), clanCrest.length==0? "": Base64.encode(DDSReader.convertToPng(clanCrest, 16, 12))));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return json(Message.DATABASE_ERROR);
        }
        finally
        {
            databaseClose(true);
        }
        return json(clans);
    }

    public String getCastle()
    {
        String name, owner, nextSiege, nextWar;
        Integer[] flags;
        String clanCrest = "", allyCrest = "";

        List<Castle> castles = ResidenceHolder.getInstance().getResidenceList(Castle.class);
        List<CastleInfo> infos = new ArrayList<CastleInfo>(castles.size());
        for(Castle castle : castles)
        {
            name = castle.getName();
            owner = ClanTable.getInstance().getClanName(castle.getOwnerId());
            if(owner.equals(StringUtils.EMPTY))
                owner = "NPC";
            nextSiege = castle.getSiegeDate().toEpochSecond() == 0? "Unknown" : TimeUtils.dateTimeFormat(castle.getSiegeDate());
            Dominion dominion = ResidenceHolder.getInstance().getResidence(Dominion.class, castle.getId()+80);
            if(dominion!=null){
                if(dominion.getSiegeDate().toEpochSecond()==0)
                    nextWar = "Unknown";
                else
                    nextWar = TimeUtils.dateTimeFormat(dominion.getSiegeDate());
                flags = dominion.getFlags();
            } else {
                nextWar = "Unknown";
                flags = new Integer[0];
            }
            if(castle.getOwnerId()!=0){
                byte[] buffer = CrestCache.getInstance().getCrestData(CrestCache.CrestType.PLEDGE, ClanTable.getInstance().getClan(castle.getOwnerId()).getCrestId());
                clanCrest = Base64.encode(DDSReader.convertToPng(buffer, 16, 12));
                if(ClanTable.getInstance().getClan(castle.getOwnerId()).getAlliance() != null){
                    buffer = CrestCache.getInstance().getCrestData(CrestCache.CrestType.ALLY, ClanTable.getInstance().getClan(castle.getOwnerId()).getAlliance().getAllyCrestId());
                    allyCrest = Base64.encode(DDSReader.convertToPng(buffer, 16, 12));
                }
            }
            infos.add(new CastleInfo(name, owner, nextSiege, nextWar, flags, allyCrest, clanCrest));
        }

        return json(infos);
    }

    public String getBanList(int count)
    {
        List<BannedPlayer> bans = new ArrayList<BannedPlayer>();
        try
        {
            conn = DatabaseFactory.getInstance().getConnection();
            statement = conn.prepareStatement(QUERRY_BANLIST);
            statement.setInt(1, count);
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int end = resultSet.getInt(5);
                bans.add(new BannedPlayer(resultSet.getString(1), resultSet.getString(2), end<Integer.MAX_VALUE? resultSet.getString(3): "Permanent", resultSet.getString(4)));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return json(Message.DATABASE_ERROR);
        }
        finally
        {
            databaseClose(true);
        }
        return json(bans);
    }

    private static String getClassNameById(int classId)
    {
        if((classId < 0 || classId > 136) || (classId > 118 && classId < 123) || (classId > 57 && classId < 88))
        {
            return new CustomMessage("utils.classId.name.default").toString(Language.ENGLISH);
        }
        return new CustomMessage("utils.classId.name." + classId).toString(Language.ENGLISH);
    }

    public class TopClan
    {
        private String name, count, rep, liderName, lvl, allyCrest, clanCrest;

        public TopClan(String name, String count, String rep, String liderName, String lvl, String allyCrest, String clanCrest)
        {
            this.name = name;
            this.count = count;
            this.rep = rep;
            this.liderName = liderName;
            this.lvl = lvl;
            this.allyCrest = allyCrest;
            this.clanCrest = clanCrest;
        }
    }

    public class CastleInfo
    {
        private String name, owner, siege_date, tw_date, allyCrest, clanCrest;
        private List<String> wards = new ArrayList<String>();

        public CastleInfo(String name, String owner, String siege_date, String tw_date, Integer[] wards, String allyCrest, String clanCrest)
        {
            this.name = name;
            this.owner = owner;
            this.siege_date = siege_date;
            this.tw_date = tw_date;
            this.allyCrest = allyCrest;
            this.clanCrest = clanCrest;
            for(int ward : wards)
                this.wards.add(String.valueOf(ward-80));
        }
    }

    public class BannedPlayer
    {
        private String name, ban_date, ban_end, reason;

        public BannedPlayer(String name, String ban_date, String ban_end, String reason)
        {
            this.name = name;
            this.ban_date = ban_date;
            this.ban_end = ban_end;
            this.reason = reason;
        }
    }

    public class TopPlayer{

        private String name, clazz, pvp, pk, level, adena;

        public TopPlayer(String name, String clazz, String pvp, String pk, String level, String adena){
            this.name=name;
            this.clazz=clazz;
            this.pvp=pvp;
            this.pk=pk;
            this.level=level;
            this.adena=adena;
        }
    }

    public class HeroPlayer{

        private String name, clazz, matches, wins;

        public HeroPlayer(String name, String clazz, String matches, String wins){
            this.name=name;
            this.clazz=clazz;
            this.matches=matches;
            this.wins=wins;
        }
    }
}