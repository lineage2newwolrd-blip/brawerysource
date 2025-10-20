package org.mmocore.gameserver.configuration.config;

import org.mmocore.commons.configuration.annotations.Configuration;
import org.mmocore.commons.configuration.annotations.Setting;
import org.mmocore.gameserver.utils.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by Mangol on 28.11.2015.
 */
@Configuration("events.json")
public class EventsConfig {
    @Setting(name = "UndergroundColiseum")
    public static boolean EVENT_UNDERGROUND_COLISEUM;

    @Setting(name = "UndergroundColiseumMemberCount")
    public static int EVENT_UNDERGROUND_COLISEUM_MEMBER_COUNT;

    @Setting(name = "AllowFightClub")
    public static boolean ALLOW_FIGHT_CLUB;
    @Setting(name = "FightClubHwidCheck")
    public static boolean FIGHT_CLUB_HWID_CHECK;
    @Setting(name = "FightClubNotAllowedEvent")
    public static String FIGHT_CLUB_DISALLOW_EVENT;
    @Setting(name = "FightClubEqualizeRooms")
    public static boolean FIGHT_CLUB_EQUALIZE_ROOMS;
    @Setting(name = "RandomEvent")
    public static boolean EVENT_RANDOM_TASK;
    @Setting(name = "RandomEventTime")
    public static int EVENT_RANDOM_TIME;
    @Setting(name = "FightClubReward")
    public static int FIGHT_CLUB_REWARD_ITEM;
    @Setting(name = "FightClubRandomItems", method = "parseFCRandomReward")
    public static Map<Integer, Integer> FIGHT_CLUB_RANDOM_ITEMS;
    public static boolean AllowJoinParty;
    public static int TempHeroDurationMin;
    public static boolean AllowHeroChatForTempHero;

    @Setting(minValue = 0, maxValue = 100)
    public static int dropChance;

    @Setting(minValue = 0, maxValue = 100)
    public static int explosionChance;

    @Setting(name = "ItemOnLevelUpActive")
    public static boolean EVENT_ItemOnLevelUpActive;

    @Setting(name = "lvlsForReward", canNull = true)
    public static String[] EVENT_IOLU_LvlsForReward = new String[0];

    @Setting(name = "lvlsForRewardItems", canNull = true)
    public static int[] EVENT_IOLU_LvlsForRewardItems = new int[0];

    @Setting(name = "lvlsForRewardCount", canNull = true)
    public static int[] EVENT_IOLU_LvlsForRewardCount = new int[0];

    @Setting(name = "lvlsForTeleport", canNull = true)
    public static String[] EVENT_IOLU_LvlsForTeleport = new String[0];

    @Setting(name = "locForTeleport", canNull = true)
    public static String[] EVENT_IOLU_LocForTeleport = new String[0];

    @Setting(name = "lvlsForRewardHtm", canNull = true)
    public static String[] EVENT_IOLU_LvlsForRewardHtm = new String[0];

    @Setting(name = "locNameForTeleport", canNull = true)
    public static String[] EVENT_IOLU_LocNameForTeleport = new String[0];

    private void parseFCRandomReward(String value) {
        FIGHT_CLUB_RANDOM_ITEMS = Util.parseConfigMap(value, Integer.class, Integer.class);
    }
}
