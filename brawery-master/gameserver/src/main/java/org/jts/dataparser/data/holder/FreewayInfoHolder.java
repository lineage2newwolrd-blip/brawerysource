package org.jts.dataparser.data.holder;

import org.jts.dataparser.data.annotations.Element;
import org.jts.dataparser.data.holder.freeway.Freeway;
import org.mmocore.commons.data.AbstractHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Camelion
 * @date : 27.08.12 13:14
 */
public class FreewayInfoHolder extends AbstractHolder {
    private static FreewayInfoHolder ourInstance = new FreewayInfoHolder();
    private final Map<Integer, Freeway> freewayInfo = new HashMap<>();
    @Element(start = "freeway_begin", end = "freeway_end")
    private List<Freeway> freeways;

    private FreewayInfoHolder() {
    }

    public static FreewayInfoHolder getInstance() {
        return ourInstance;
    }

    @Override
    public int size() {
        return freeways.size();
    }

    public List<Freeway> getFreeways() {
        return freeways;
    }

    public Freeway getFreewayInfo(int freeway_id) {
        return freewayInfo.get(freeway_id);
    }

    @Override
    public void afterParsing() {
        super.afterParsing();
        for(Freeway freeway : freeways)
            freewayInfo.put(freeway.freeway_id, freeway);
    }

    @Override
    public void clear() {
        freeways.clear();
    }
}