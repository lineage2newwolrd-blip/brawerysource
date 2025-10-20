package org.mmocore.gameserver.model.dress;

import org.mmocore.gameserver.data.xml.holder.ItemTemplateHolder;
import org.mmocore.gameserver.templates.item.ItemTemplate;

/**
 * Created by Hack
 * Date: 17.08.2016 17:50
 */
public class DressArmorData {
    private final int _id;
    private final String _name;
    private final String _icon;
    private final int _chest;
    private final int _legs;
    private final int _gloves;
    private final int _feet;
    private final int _helmet;
    private final int _cloak;
    private final int _priceId;
    private final long _priceCount;

    public DressArmorData(int id, String name, int chest, int legs, int gloves, int feet, int helmet, int cloak, int priceId, long priceCount) {
        _id = id;
        _name = name;
        _chest = chest;
        _legs = legs;
        _gloves = gloves;
        _feet = feet;
        _helmet = helmet;
        _cloak = cloak;
        _priceId = priceId;
        _priceCount = priceCount;
        ItemTemplate chestTemplate = ItemTemplateHolder.getInstance().getTemplate(chest);
        _icon = chestTemplate != null? chestTemplate.getIcon() : "";
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public int getChest() {
        return _chest;
    }

    public int getLegs() {
        return _legs;
    }

    public int getGloves() {
        return _gloves;
    }

    public int getFeet() {
        return _feet;
    }

    public int getHelmet()
    {
        return _helmet;
    }

    public int getCloak()
    {
        return _cloak;
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
