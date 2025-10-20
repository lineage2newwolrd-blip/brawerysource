package org.mmocore.gameserver.phantoms;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.clientCustoms.PhantomConfig;
import org.mmocore.gameserver.data.xml.holder.PhantomHolder;
import org.mmocore.gameserver.data.xml.holder.PhantomOnlineHolder;
import org.mmocore.gameserver.data.xml.holder.PhantomSpawnHolder;
import org.mmocore.gameserver.manager.ReflectionManager;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoint;
import org.mmocore.gameserver.phantoms.ai.PhantomAiType;
import org.mmocore.gameserver.phantoms.model.Phantom;
import org.mmocore.gameserver.phantoms.template.PhantomSpawnTemplate;
import org.mmocore.gameserver.phantoms.template.PhantomTemplate;
import org.mmocore.gameserver.templates.item.ItemTemplate.Grade;
import org.mmocore.gameserver.utils.Location;
import org.mmocore.gameserver.world.GameObjectsStorage;
import org.mmocore.gameserver.world.World;
import org.mmocore.gameserver.world.WorldRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by Hack
 * Date: 21.08.2016 3:31
 * <p>
 * TODO list:
 * - запретить брать ники, которые заняты ботами
 */
public class PhantomLoader {
    private static final Logger log = LoggerFactory.getLogger(PhantomLoader.class);
    private static final PhantomLoader instance = new PhantomLoader();
    private Future<?> spawnWaveTask;

    public static PhantomLoader getInstance() {
        return instance;
    }

    private void spawnPhantom(Location location, PhantomAiType type, Grade minGrade, Grade maxGrade, SuperPoint superPoint) {
        PhantomTemplate template = getTemplateForSpawn(type, minGrade, maxGrade);
        if (template == null)
            return;
        final WorldRegion region = World.getRegion(location);
        if (region == null || !region.isActive()) {
            return;
        }
        Phantom phantom = PhantomFactory.getInstance().create(template);
        PhantomOnlineHolder.getInstance().addPhantom(phantom);
        phantom.setLoc(location);
        phantom.setHeading(Rnd.get(65535));
        phantom.setSuperPoint(superPoint);
        phantom.schedulePhantomSpawn();
    }

    private PhantomTemplate getTemplateForSpawn(PhantomAiType type, Grade minGrade, Grade maxGrade) {
        for (int i = 0; i < 20; i++) {
            PhantomTemplate template = PhantomHolder.getInstance().getRandomPhantomTemplate(type, minGrade, maxGrade);
            if (template != null && !PhantomOnlineHolder.getInstance().contains(template.getName()))
                return template;
            /*if (PhantomOnlineHolder.getInstance().contains(template.getName())) {
                Phantom ph = PhantomOnlineHolder.getInstance().getPhantom(template.getName());
                if(ph!=null && ph.isDeleted() && getGrade(ph).ordinal() >= minGrade.ordinal() && getGrade(ph).ordinal() <= maxGrade.ordinal()){
                    template.setClassId(ph.getClassId());
                    template.setRace(ph.getRaceId());
                    template.setSex(ph.getSex());
                    template.setFace(ph.getAppearanceComponent().getFace());
                    template.setHair(ph.getAppearanceComponent().getHairStyle());
                    template.setHairColor(ph.getAppearanceComponent().getHairColor());
                    template.setTitle(ph.getTitle());
                    template.setTitleColor(ph.getAppearanceComponent().getTitleColor());
                    template.setNameColor(ph.getAppearanceComponent().getNameColor());
                    PhantomOnlineHolder.getInstance().deletePhantom(ph);
                    return template;
                }
            } else
                return template;*/
        }
        log.warn("Can't find free PhantomTemplate! Please add more phantom templates in xml storage." +
                " [min grade: " + minGrade + "] [max grade: " + maxGrade + "].");
        return null;
    }

    private void spawnWave() {
        int playerCount = (int)GameObjectsStorage.getPlayerStream().filter(p->!p.isPhantom()).count();
        Map<Grade, Long> levelMap = GameObjectsStorage.getPlayerStream().filter(p->!p.isPhantom())
                .collect(Collectors.groupingBy(this::getGrade, Collectors.counting()));
        Grade playerGrade = getMaxGrade(levelMap, playerCount);

        Grade gradeMax, gradeMin;
        int counter;
        for (PhantomSpawnTemplate template : PhantomSpawnHolder.getInstance().getSpawns()) {
            gradeMax = template.getGradeMax().ordinal() > playerGrade.ordinal() ? playerGrade : template.getGradeMax();
            gradeMin = template.getGradeMin();
            counter = getGradeCounter(gradeMin, gradeMax, levelMap, template.getCount(), playerCount);
            for (int i = 0; i < counter; i++) {
                spawnPhantom(template.getSpawnRange().getRandomLoc(ReflectionManager.DEFAULT.getGeoIndex()), template.getType(),
                        gradeMin, gradeMax, template.getSuperPoint());
            }
        }
    }

    private Grade getGrade(Player player) {
        final int level = player.getLevel();
        if(level>=20 && level < 40){
            return Grade.D;
        } else if(level>=40 && level < 52){
            return Grade.C;
        } else if(level>=52 && level < 61){
            return Grade.B;
        } else if(level>=61 && level < 76){
            return Grade.A;
        } else if(level>=76 && level < 80){
            return Grade.S;
        } else if(level>=80 && level < 84){
            return Grade.S80;
        } else if(level>=84){
            return Grade.S84;
        }
        return Grade.NONE;
    }

    private static Grade getMaxGrade(Map<Grade, Long> levelMap, int playerCount) {
        Grade result = Grade.NONE;
        int cut_count = (int) Math.ceil(playerCount * 0.7);
        for(Grade grade : Grade.values()){
            result = grade;
            if(levelMap.get(grade)==null) {
                continue;
            }
            cut_count -= levelMap.get(grade);
            if(cut_count<=0)
                return result;
        }
        return result;
    }

    private static int getGradeCounter(Grade min, Grade max, Map<Grade, Long> levelMap, int maxcount, int playerCount) {
        double count = 0;
        for(Grade grade : Grade.values()){
            if(levelMap.get(grade)==null)
                continue;
            if(grade.ordinal() >= min.ordinal() && grade.ordinal() <=max.ordinal()){
                count = Math.max(count, 1. * maxcount * levelMap.get(grade) / playerCount);
            }
        }
        return (int)Math.ceil(count);
    }

    public Future<?> getSpawnWaveTask() {
        return spawnWaveTask;
    }

    public void load() {
        spawnWaveTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new SpawnWaveTask(),
                PhantomConfig.firstWaveDelay * 60000, PhantomConfig.waveRespawn * 60000);
        log.info("Phantom System: completely loaded.");
    }

    private class SpawnWaveTask implements Runnable {
        @Override
        public void run() {
            try {
                spawnWave();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
