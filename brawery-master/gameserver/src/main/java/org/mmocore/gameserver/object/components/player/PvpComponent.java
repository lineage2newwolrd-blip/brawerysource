package org.mmocore.gameserver.object.components.player;

import gnu.trove.map.hash.TIntObjectHashMap;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.AllSettingsConfig;
import org.mmocore.gameserver.configuration.config.clientCustoms.OtherCustom;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.player.custom.PvPRewardTask;
import org.mmocore.gameserver.utils.ItemFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

/**
 * Created by iRock
 * Date: 12.01.2019 3:37
 */
public class PvpComponent {
    private Player player;
    private static final Logger _log = LoggerFactory.getLogger(PvpComponent.class);
    private static List<RewardTemplate> customRewardItems = new ArrayList<>();
    private static final long BLOCK_REWARD_TIME	=	OtherCustom.pvpRewardBlock * 1000L;
    private TIntObjectHashMap<Long> victims = new TIntObjectHashMap<>(OtherCustom.pvpHolderSize);
    private ScheduledFuture<?> blockReward = null;

    // init custom rewards
    static {
        try {
            parseRewards(customRewardItems, OtherCustom.pvpRewards);
        } catch (Exception e) {
            _log.warn("Can't load additional pvp reward items!");
        }
    }

    public PvpComponent(Player player) {
        this.player = player;
    }

    public void onKill(Creature victim)
    {
        if(!OtherCustom.allowPvpRewards)
            return;

        Player winner = getPlayer();
        Player looser = victim.getPlayer();
        if(winner == null || looser == null || winner == looser || winner.isInZoneBattle() || looser.isInZoneBattle() ||
                (winner.getHwid()!=null && winner.getHwid().equals(looser.getHwid())) || winner.isInFightClub() ||
                looser.isInFightClub()|| isBlockedVictim(looser) || Math.abs(winner.getLevel() - looser.getLevel()) > 10)
            return;

        List<Player> players;
        if(winner.getParty() == null)
            players = Collections.singletonList(winner);
        else
            players = winner.getParty().getPartyMembers().stream().filter(temp -> temp != null && temp.isInRange(winner, AllSettingsConfig.ALT_PARTY_DISTRIBUTION_RANGE)).collect(Collectors.toList());
        Player target = Rnd.get(players);
        for (RewardTemplate template : customRewardItems) {
            double rewardChance = template.getChance() + template.getChance() * ((double) (players.size() - 1) / 2.);
            if (Rnd.chance(rewardChance)) {
                ItemFunctions.addItem(target, template.getId(), template.getCount());
                break;
            }
        }
        if (OtherCustom.pvpRewardFame > 0)
            winner.setFame(winner.getFame() + OtherCustom.pvpRewardFame, "PvP Reward");

        startBlockReward(BLOCK_REWARD_TIME, looser);
        addVictim(looser.getObjectId());
    }

    public void addVictim(int objectId) {
        victims.put(objectId, System.currentTimeMillis() + BLOCK_REWARD_TIME);
    }

    public void removeVictim(Player player)
    {
        victims.remove(player.getObjectId());
    }

    public Player getPlayer() {
        return player;
    }

    private static void parseRewards(List<RewardTemplate> rewardList, String str) throws Exception {
        if (str != null && !str.equals("")) {
            String[] items = str.split(";");
            for (String item : items) {
                item = item.trim();
                String[] attrs = item.trim().split(",");
                rewardList.add(new RewardTemplate(Integer.parseInt(attrs[0]), Integer.parseInt(attrs[1]), Double.parseDouble(attrs[2])));
            }
        }
    }

    public boolean isBlockedVictim(Player player)
    {
        Long blk_time = victims.get(player.getObjectId());
        return blk_time != null && blk_time > System.currentTimeMillis();
    }

    public void startBlockReward(long time, Player player)
    {
        if(blockReward != null)
            return;

        blockReward = ThreadPoolManager.getInstance().schedule(new PvPRewardTask(player, getPlayer()), time);
    }

    public void stopBlockReward()
    {
        if(blockReward == null)
            return;
        blockReward.cancel(false);
        blockReward = null;
    }

    private static class RewardTemplate {
        private int id;
        private int count;
        private double chance;

        public RewardTemplate(int id, int count, double chance) {
            this.id = id;
            this.count = count;
            this.chance = chance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getChance() {
            return chance;
        }

        public void setChance(double chance) {
            this.chance = chance;
        }
    }
}
