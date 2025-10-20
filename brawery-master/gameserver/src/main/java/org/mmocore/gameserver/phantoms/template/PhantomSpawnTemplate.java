package org.mmocore.gameserver.phantoms.template;

import org.mmocore.commons.utils.Rnd;
import org.mmocore.gameserver.model.Territory;
import org.mmocore.gameserver.object.components.npc.superPoint.SuperPoint;
import org.mmocore.gameserver.phantoms.ai.PhantomAiType;
import org.mmocore.gameserver.templates.item.ItemTemplate;
import org.mmocore.gameserver.templates.spawn.SpawnRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hack
 * Date: 22.08.2016 4:41
 */
public class PhantomSpawnTemplate {
    private PhantomAiType type; //unused, TODO
    private int count;
    private ItemTemplate.Grade gradeMin, gradeMax;
    private List<SpawnRange> territory = new ArrayList<>(1);
    private SuperPoint superPoint;

    public PhantomSpawnTemplate() {
    }

    public PhantomAiType getType() {
        return type;
    }

    public void setType(PhantomAiType type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ItemTemplate.Grade getGradeMin() {
        return gradeMin;
    }

    public void setGradeMin(ItemTemplate.Grade gradeMin) {
        this.gradeMin = gradeMin;
    }

    public ItemTemplate.Grade getGradeMax() {
        return gradeMax;
    }

    public void setGradeMax(ItemTemplate.Grade gradeMax) {
        this.gradeMax = gradeMax;
    }

    public SpawnRange getSpawnRange() {
        return territory.get(Rnd.get(territory.size()));
    }

    public void addSpawnRange(SpawnRange territory) {
        this.territory.add(territory);
    }

    public SuperPoint getSuperPoint() {
        return superPoint;
    }

    public void setSuperPoint(SuperPoint superPoint) {
        this.superPoint = superPoint;
    }
}
