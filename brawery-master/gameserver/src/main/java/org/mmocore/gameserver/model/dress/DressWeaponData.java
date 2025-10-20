package org.mmocore.gameserver.model.dress;

import org.mmocore.gameserver.data.xml.holder.ItemTemplateHolder;
import org.mmocore.gameserver.templates.item.ItemTemplate;

/**
 * Created by Hack
 * Date: 17.08.2016 17:53
 */
public class DressWeaponData {
    private final int _id;
    private final int _weapon;
    private final String _name;
    private final String _icon;
    private final String _type;
    private final int _priceId;
    private final long _priceCount;

    public DressWeaponData(int id, int weapon, String name, String type, int priceId, long priceCount) {
        _id = id;
        _weapon = weapon;
        _name = name;
        _type = type;
        _priceId = priceId;
        _priceCount = priceCount;
        ItemTemplate weaponTemplate = ItemTemplateHolder.getInstance().getTemplate(weapon);
        _icon = weaponTemplate != null? weaponTemplate.getIcon() : "";
    }

    public int getId() {
        return _id;
    }

    public int getWeaponId()
    {
        return _weapon;
    }

    public String getName() {
        return _name;
    }

    public String getType() {
        return _type;
    }

    public int getPriceId() {
        return _priceId;
    }

    public long getPriceCount() {
        return _priceCount;
    }

    public String get_icon() {
        return _icon;
    }
}
