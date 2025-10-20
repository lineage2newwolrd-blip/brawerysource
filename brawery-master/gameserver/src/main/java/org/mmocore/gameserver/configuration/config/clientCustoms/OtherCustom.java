package org.mmocore.gameserver.configuration.config.clientCustoms;

import org.mmocore.commons.configuration.annotations.Configuration;
import org.mmocore.commons.configuration.annotations.Setting;

/**
 * Created by Hack
 * Date: 17.08.2016 5:23
 */

@Configuration("custom/otherCustom.json")
public class OtherCustom {
    public static boolean AllowDressService;

    public static boolean customOlyEnchant;
    public static int enchantOlyArmor;
    public static int enchantOlyWeapon;
    public static int enchantOlyJewelry;
    public static boolean enchantOlyExceptLowerItems;

    @Setting(name = "additionalOlyWinnerRewardItems", canNull = true)
    public static String additionalOlyWinnerRewardItems = "";
    public static int additionalOlyWinnerRewardCrp;
    public static int additionalOlyWinnerRewardFame;
    @Setting(name = "additionalOlyLoserRewardItems", canNull = true)
    public static String additionalOlyLoserRewardItems = "";
    public static int additionalOlyLoserRewardCrp;
    public static int additionalOlyLoserRewardFame;

    public static boolean allowTradePvp;
    public static boolean allowTransformPvp;

    public static boolean addNoblesseBlessing;

    @Setting(name = "crpRewardMonsters", canNull = true)
    public static String crpRewardMonsters = "";
    public static double crpRewardMinHatePercent;

    public static int pvpHolderSize;
    public static boolean allowPvpRewards;
    @Setting(name = "pvpRewards", canNull = true)
    public static String pvpRewards = "";
    public static int pvpRewardFame;
    public static int pvpRewardBlock;

    @Setting(name = "vipBuffs", canNull = true)
    public static String vipBuffs = "";
    public static boolean vipAccessByItemExists;
    public static boolean vipAccessForPremium;
    public static int vipAccessItemId;

    public static boolean allowAdditionalCastleRewards;
    @Setting(name = "gludioCastleRewards", canNull = true)
    public static String gludioCastleRewards = "";
    @Setting(name = "dionCastleRewards", canNull = true)
    public static String dionCastleRewards = "";
    @Setting(name = "giranCastleRewards", canNull = true)
    public static String giranCastleRewards = "";
    @Setting(name = "orenCastleRewards", canNull = true)
    public static String orenCastleRewards = "";
    @Setting(name = "adenCastleRewards", canNull = true)
    public static String adenCastleRewards = "";
    @Setting(name = "innadrilCastleRewards", canNull = true)
    public static String innadrilCastleRewards = "";
    @Setting(name = "goddardCastleRewards", canNull = true)
    public static String goddardCastleRewards = "";
    @Setting(name = "runeCastleRewards", canNull = true)
    public static String runeCastleRewards = "";
    @Setting(name = "shuttgartCastleRewards", canNull = true)
    public static String shuttgartCastleRewards = "";

    @Setting(name = "gludioLeaderCastleRewards", canNull = true)
    public static String gludioLeaderCastleRewards = "";
    @Setting(name = "dionLeaderCastleRewards", canNull = true)
    public static String dionLeaderCastleRewards = "";
    @Setting(name = "giranLeaderCastleRewards", canNull = true)
    public static String giranLeaderCastleRewards = "";
    @Setting(name = "orenLeaderCastleRewards", canNull = true)
    public static String orenLeaderCastleRewards = "";
    @Setting(name = "adenLeaderCastleRewards", canNull = true)
    public static String adenLeaderCastleRewards = "";
    @Setting(name = "innadrilLeaderCastleRewards", canNull = true)
    public static String innadrilLeaderCastleRewards = "";
    @Setting(name = "goddardLeaderCastleRewards", canNull = true)
    public static String goddardLeaderCastleRewards = "";
    @Setting(name = "runeLeaderCastleRewards", canNull = true)
    public static String runeLeaderCastleRewards = "";
    @Setting(name = "shuttgartLeaderCastleRewards", canNull = true)
    public static String shuttgartLeaderCastleRewards = "";

    @Setting(name = "ccNonLockableBosses", canNull = true)
    public static int[] ccNonLockableBosses;
    public static int ccMinLockableSize;

    public static boolean customDestructionEnchant;

    @Setting(name = "heroRewards", canNull = true)
    public static String heroRewards = "";
}
