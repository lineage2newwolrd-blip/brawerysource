package org.mmocore.gameserver.ai;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iRock on 05.06.2018.
 */
public class EventParam {

    private final Map<CtrlEventParam, Object> values = new HashMap<>();

    public <V> EventParam add( CtrlEventParam key, V value ) {
        if(value == null)
            return this;
        values.put(key, value);
        return this;
    }

    public <V> V get(CtrlEventParam key, Class<V> type) {
        try{
            return values.get(key) == null ? null : type.cast(values.get(key));
        }
        catch (ClassCastException cc){
            return null;
        }
    }

    public int getInt(CtrlEventParam key) {
        try{
            return values.get(key) == null ? 0 : (int) values.get(key);
        }
        catch (ClassCastException cc){
            return 0;
        }
    }

    public float getFloat(CtrlEventParam key) {
        try{
            return values.get(key) == null ? 0f : (float) values.get(key);
        }
        catch (ClassCastException cc){
            return 0f;
        }
    }

    public boolean getBool(CtrlEventParam key) {
        try{
            return values.get(key) != null && (boolean) values.get(key);
        }
        catch (ClassCastException cc){
            return false;
        }
    }

}
