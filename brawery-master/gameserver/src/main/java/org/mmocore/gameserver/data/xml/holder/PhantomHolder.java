package org.mmocore.gameserver.data.xml.holder;

import org.jts.dataparser.data.holder.setting.common.PlayerRace;
import org.mmocore.commons.data.AbstractHolder;
import org.mmocore.commons.math.random.RndSelector.RndNode;
import org.mmocore.commons.math.random.RndSelector;
import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.configuration.config.clientCustoms.PhantomConfig;
import org.mmocore.gameserver.model.base.ClassId;
import org.mmocore.gameserver.model.base.ClassLevel;
import org.mmocore.gameserver.phantoms.ai.PhantomAiType;
import org.mmocore.gameserver.phantoms.template.PhantomTemplate;
import org.mmocore.gameserver.templates.item.ItemTemplate.Grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hack
 * Date: 21.08.2016 6:10
 */
public class PhantomHolder extends AbstractHolder {
    private static final PhantomHolder instance = new PhantomHolder();
    private List<String> names = new ArrayList<>();
    private final Map<ClassLevel, RndSelector<ClassId>> class_ids = new HashMap<>();

    @SuppressWarnings("unchecked")
    public PhantomHolder() {
        for(ClassLevel level : ClassLevel.values()) {
            List<ClassId> level_list = new ArrayList<>();
            for (ClassId classId : ClassId.values()) {
                if (classId.getLevel() == level && classId!=ClassId.inspector && classId!=ClassId.judicator)
                    level_list.add(classId);
            }
            final RndNode<ClassId>[] nodes = level_list.stream().map(clazz -> RndNode.of(
                    clazz, (clazz.isMage()? 2 : 1))).toArray(RndNode[]::new);
            class_ids.put(level, RndSelector.of(nodes));
        }
    }

    public static PhantomHolder getInstance() {
        return instance;
    }

    public void addPhantomName(String name) {
        names.add(name);
    }

    public PhantomTemplate getRandomPhantomTemplate(PhantomAiType type, Grade minGrade, Grade maxGrade) {
        Grade rndGrade = Grade.values()[Rnd.get(minGrade.ordinal(), maxGrade.ordinal())];
        ClassId classId = getClass(rndGrade);
        if (classId == null) {
            _log.warn("Can't find template for grade: " + rndGrade);
            return null;
        }
        if (names.isEmpty()) {
            _log.warn("Can't find free name");
            return null;
        }
        PhantomTemplate template = new PhantomTemplate();
        final String name = Rnd.get(names);
        template.setType(type);
        template.setName(name);
        template.setGrade(rndGrade);
        template.setClassId(classId.getId());
        template.setRace(classId.getRace().getId());
        template.setSex(getSex(classId));
        template.setFace(Rnd.get(0,2));
        template.setHair(Rnd.get(0,4));
        template.setHairColor(Rnd.get(0,2));
        if(PhantomConfig.title!=null)
            template.setTitle(PhantomConfig.title);
        if(PhantomConfig.titleColor!=null)
            template.setTitleColor(getIntegerDecode(PhantomConfig.titleColor));
        if(PhantomConfig.nameColor!=null)
            template.setNameColor(getIntegerDecode(PhantomConfig.nameColor));
        return template;
    }

    public boolean isNameExists(String name) {
        for (String value : names)
            if (value.equalsIgnoreCase(name))
                return true;
        return false;

    }

    private ClassId getClass(Grade grade) {
        switch (grade){
            case NONE:
                return class_ids.get(ClassLevel.First).select();
            case D:
                return class_ids.get(ClassLevel.Second).select();
            case C:
            case B:
            case A:
                return class_ids.get(ClassLevel.Third).select();
            case S:
            case S80:
            case S84:
                return class_ids.get(ClassLevel.Fourth).select();
            default:
                return class_ids.get(ClassLevel.First).select();
        }

    }

    private int getSex(ClassId classId) {
        if(classId.getRace() == PlayerRace.kamael){
            if(classId.childOf(ClassId.kamael_f_soldier))
                return 1;
            else return 0;
        }
        return Rnd.get(0,1);
    }

    private int getIntegerDecode(String value) {
        return Integer.decode(value);
    }

    @Override
    public int size() {
        return names.size();
    }

    @Override
    public void clear() {
        names.clear();
    }
}
