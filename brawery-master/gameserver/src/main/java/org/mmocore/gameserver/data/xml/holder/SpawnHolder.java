package org.mmocore.gameserver.data.xml.holder;

import org.mmocore.commons.data.AbstractHolder;
import org.mmocore.gameserver.templates.spawn.SpawnTemplate;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * @author VISTALL
 * @date 18:38/10.12.2010
 */
public final class SpawnHolder extends AbstractHolder {
    private final Map<String, List<SpawnTemplate>> spawns = new HashMap<>();
    private final List<Integer> npcs = new ArrayList<>();

    public static SpawnHolder getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void addSpawn(final String group, final SpawnTemplate spawn) {
        List<SpawnTemplate> spawns = this.spawns.get(group);
        if (spawns == null) {
            this.spawns.put(group, spawns = new ArrayList<>());
        }
        spawns.add(spawn);
    }

    public List<SpawnTemplate> getSpawn(final String name) {
        final List<SpawnTemplate> template = spawns.get(name);
        return template == null ? Collections.<SpawnTemplate>emptyList() : template;
    }

    @Override
    public int size() {
        int i = 0;
        for (final List<SpawnTemplate> l : spawns.values()) {
            i += l.size();
        }

        return i;
    }

    @Override
    public void clear() {
        spawns.clear();
    }

    public Map<String, List<SpawnTemplate>> getSpawns() {
        return spawns;
    }

    public boolean addToList(int id) {
        return npcs.add(id);
    }

    public boolean isManualSpawn(int id) {
        return npcs.contains(id);
    }

    private static class LazyHolder {
        private static final SpawnHolder INSTANCE = new SpawnHolder();
    }
}
