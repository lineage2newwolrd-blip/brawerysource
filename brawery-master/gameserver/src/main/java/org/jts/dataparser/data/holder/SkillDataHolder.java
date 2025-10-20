package org.jts.dataparser.data.holder;

import org.jts.dataparser.data.annotations.Element;
import org.jts.dataparser.data.holder.skilldata.SkillData;
import org.mmocore.commons.data.AbstractHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KilRoy
 */
public class SkillDataHolder extends AbstractHolder {
    private static SkillDataHolder ourInstance = new SkillDataHolder();
    private final Map<Integer, SkillData> skillInfo = new HashMap<>();
    @Element(start = "skill_begin", end = "skill_end")
    private List<SkillData> skills;

    private SkillDataHolder() {
    }

    public static SkillDataHolder getInstance() {
        return ourInstance;
    }

    public SkillData getSkillInfo(int skill_pts_id) {
        return skillInfo.get(skill_pts_id);
    }

    public SkillData getSkillInfo(int skill_id, int level) {
        return skillInfo.get(skill_id << 16 | level);
    }

    @Override
    public void afterParsing() {
        super.afterParsing();
        for(SkillData skill : skills)
            skillInfo.put(skill.skill_id << 16 | skill.level, skill);
    }
    @Override
    public int size() {
        return skills.size();
    }

    @Override
    public void clear() {
        skills.clear();
    }
}