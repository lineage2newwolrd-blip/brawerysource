package org.mmocore.gameserver.templates;

/**
 * Created by Hack
 * Date: 06.06.2016 22:13
 */
public class QuestCustomParams {
    private int id;
    private int levelMin;
    private int levelMax;
    private double rateDrop;
    private double rateReward;

    public QuestCustomParams(int id, int levelMin, int levelMax, double rateDrop, double rateReward) {
        this.id = id;
        this.levelMin = levelMin;
        this.levelMax = levelMax;
        this.rateDrop = rateDrop;
        this.rateReward = rateReward;
    }

    public int getId() {
        return id;
    }

    public int getLevelMax() {
        return levelMax;
    }

    public int getLevelMin() {
        return levelMin;
    }

    public double getRateDrop() {
        return rateDrop;
    }

    public double getRateReward() {
        return rateReward;
    }
}
