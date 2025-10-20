package org.mmocore.gameserver.scripts.ai.pts.lair_of_antaras;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.configuration.config.AllSettingsConfig;
import org.mmocore.gameserver.model.instances.NpcInstance;
import org.mmocore.gameserver.model.quest.QuestState;
import org.mmocore.gameserver.model.team.PlayerGroup;
import org.mmocore.gameserver.model.zone.Zone;
import org.mmocore.gameserver.network.lineage.components.NpcString;
import org.mmocore.gameserver.object.Creature;
import org.mmocore.gameserver.object.Playable;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.npc.AggroList.HateInfo;
import org.mmocore.gameserver.skills.SkillEntry;
import org.mmocore.gameserver.tables.SkillTable;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.utils.NpcUtils;
import org.mmocore.gameserver.utils.ReflectionUtils;
import org.mmocore.gameserver.utils.version.Chronicle;
import org.mmocore.gameserver.utils.version.ChronicleCheck;

import java.util.Map;

/**
 * @author KilRoy & iRock
 */
@ChronicleCheck(Chronicle.HIGH_FIVE)
public class ai_behemoth_leader extends ai_antaras_cave_raid_basic {
    private static Zone _poisonZone = ReflectionUtils.getZone("[behemoth1_poison]");
    private static Zone _petrifyZone  = ReflectionUtils.getZone("[behemoth1_petrify]");
    private static final int USE_SKILL03_TIME = 4000;
    private static final int USE_SKILL03A_TIME = 4100;
    private static final int USE_SKILL04_TIME = 4001;

    private static final int object1a = 18964;
    private static final int object1b = 18965;
    private static final int object1c = 18966;
    private int i_ai0;
    private int i_ai1;
    private int i_ai3;
    private static final SkillEntry channeling_skill = SkillTable.getInstance().getSkillEntry(6742, 1);

    public ai_behemoth_leader(NpcInstance actor) {
        super(actor);
        i_ai0 = 0;
        i_ai1 = 0;
        i_ai3 = 0;
    }


    @Override
    protected boolean thinkActive()
    {
        if(i_ai0!=0)
            i_ai0 = 0;
        if(i_ai3!=0) {
            i_ai3 = 0;
            _poisonZone.setActive(false);
            _petrifyZone.setActive(false);
        }
        return super.thinkActive();
    }

    @Override
    protected void onEvtTimerFiredEx(int timer_id, Object arg1, Object arg2) {
        if (getActor() == null || getActor().isDead()) {
            return;
        }

        if (timer_id == USE_SKILL03_TIME) {
            final int i1 = i_ai0 == 1? Rnd.get(5) : -1;
            NpcInstance npc;
            switch (i1) {
                case 0:
                    npc = NpcUtils.spawnSingle(object1a, new Location(145280, 120489, -3912, 57917));
                    npc.setNpcState(1);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1b, new Location(145941, 119081, -3912, 16383));
                    npc.setNpcState(2);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1c, new Location(146116, 120316, -3912, 37739));
                    npc.setNpcState(3);
                    npc.doCast(channeling_skill, getActor(), false);
                    break;
                case 1:
                    npc = NpcUtils.spawnSingle(object1a, new Location(145280, 120489, -3912, 57917));
                    npc.setNpcState(1);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1c, new Location(145941, 119081, -3912, 16383));
                    npc.setNpcState(2);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1b, new Location(146116, 120316, -3912, 37739));
                    npc.setNpcState(3);
                    npc.doCast(channeling_skill, getActor(), false);
                    break;
                case 2:
                    npc = NpcUtils.spawnSingle(object1b, new Location(145280, 120489, -3912, 57917));
                    npc.setNpcState(1);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1a, new Location(145941, 119081, -3912, 16383));
                    npc.setNpcState(2);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1c, new Location(146116, 120316, -3912, 37739));
                    npc.setNpcState(3);
                    npc.doCast(channeling_skill, getActor(), false);
                    break;
                case 3:
                    npc = NpcUtils.spawnSingle(object1b, new Location(145280, 120489, -3912, 57917));
                    npc.setNpcState(1);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1c, new Location(145941, 119081, -3912, 16383));
                    npc.setNpcState(2);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1a, new Location(146116, 120316, -3912, 37739));
                    npc.setNpcState(3);
                    npc.doCast(channeling_skill, getActor(), false);
                    break;
                case 4:
                    npc = NpcUtils.spawnSingle(object1c, new Location(145280, 120489, -3912, 57917));
                    npc.setNpcState(1);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1a, new Location(145941, 119081, -3912, 16383));
                    npc.setNpcState(2);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1b, new Location(146116, 120316, -3912, 37739));
                    npc.setNpcState(3);
                    npc.doCast(channeling_skill, getActor(), false);
                    break;
                case 5:
                    npc = NpcUtils.spawnSingle(object1c, new Location(145280, 120489, -3912, 57917));
                    npc.setNpcState(1);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1b, new Location(145941, 119081, -3912, 16383));
                    npc.setNpcState(2);
                    npc.doCast(channeling_skill, getActor(), false);
                    npc = NpcUtils.spawnSingle(object1a, new Location(146116, 120316, -3912, 37739));
                    npc.setNpcState(3);
                    npc.doCast(channeling_skill, getActor(), false);
                    break;
            }
            AddTimerEx(USE_SKILL03A_TIME, 15 * 1000);
        } else if (timer_id == USE_SKILL03A_TIME) {
            i_ai1 = 0;
            getActor().getAroundNpc(4000, 200).stream().filter(npc -> npc != null && (npc.getNpcId() == object1a || npc.getNpcId() == object1b || npc.getNpcId() == object1c)).forEach(npc -> {
                if(!npc.isDead())
                    i_ai1++;
                npc.deleteMe();
            });
            if (i_ai0 == 1 && i_ai1 > 0)
            {
                _poisonZone.setActive(true);
            }
            else if(i_ai0 == 1 && i_ai1 == 0){
                _poisonZone.setActive(false);
            }
            i_ai0 = 0;
        }
        else if (timer_id == USE_SKILL04_TIME)
        {
            if (i_ai0 == 1){
                AddTimerEx(USE_SKILL04_TIME, (30 + Rnd.get(30)) * 1000);
                if (i_ai3 == 0)
                {
                    _petrifyZone.setActive(true);
                    i_ai3 = 1;
                }
                else if (i_ai3 == 1)
                {
                    _petrifyZone.setActive(false);
                    i_ai3 = 0;
                }
            }
        }
        super.onEvtTimerFiredEx(timer_id, arg1, arg2);
    }

    @Override
    protected void onEvtAttacked(final Creature attacker, final SkillEntry skill, final int damage) {
        if (i_ai0 == 0) {
            i_ai0 = 1;
            AddTimerEx(USE_SKILL03_TIME, (60 + Rnd.get(30)) * 1000);
            AddTimerEx(USE_SKILL04_TIME, (30 + Rnd.get(30)) * 1000);
            //AddTimerEx(USE_SKILL03A_TIME, 300000L);
        }
        super.onEvtAttacked(attacker, skill, damage);
    }

    @Override
    protected void onEvtDead(Creature killer) {
        _poisonZone.setActive(false);
        _petrifyZone.setActive(false);
        i_ai0 = 0;
        i_ai3 = 0;

        final NpcInstance npc = NpcUtils.spawnSingle(32885, getActor().getLoc(), 3600000L); // corpse_behemoth_leader
        if (killer != null) {
            npc.setTitleNpcString(NpcString.KILLED_BY_S1);
            npc.setTitle(killer.getPlayer().getName());
            npc.broadcastCharInfo();
            final Player player = killer.getPlayer();
            if (player != null) {
                final PlayerGroup pg = player.getPlayerGroup();
                if (pg != null) {
                    QuestState qs;
                    final Map<Playable, HateInfo> aggro = getActor().getAggroList().getPlayableMap();
                    for (Player pl : pg) {
                        if (pl != null && !pl.isDead() && aggro.containsKey(pl) && (getActor().isInRangeZ(pl, AllSettingsConfig.ALT_PARTY_DISTRIBUTION_RANGE) || getActor().isInRangeZ(killer, AllSettingsConfig.ALT_PARTY_DISTRIBUTION_RANGE))) {
                            qs = pl.getQuestState(456);
                            if (qs != null && qs.getCond() == 1) {
                                qs.setMemoState("RaidKilled", npc.getObjectId());
                            }
                        }
                    }
                }
            }
        }
        super.onEvtDead(killer);
        getActor().endDecayTask();
    }
}