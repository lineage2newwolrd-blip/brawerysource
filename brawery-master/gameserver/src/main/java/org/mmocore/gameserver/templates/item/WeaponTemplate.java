package org.mmocore.gameserver.templates.item;

import org.mmocore.gameserver.stats.Stats;
import org.mmocore.gameserver.stats.funcs.FuncTemplate;
import org.mmocore.gameserver.templates.StatsSet;

public final class WeaponTemplate extends ItemTemplate {
    private final int _soulShotCount;
    private final int _spiritShotCount;
    private final int _kamaelConvert;
    private final int _rndDam;
    private final int _atkReuse;
    private final int _mpConsume;
    private int _critical;

    public WeaponTemplate(final StatsSet set) {
        super(set);
        type = set.getEnum("type", WeaponType.class);
        _soulShotCount = set.getInteger("soulshots", 0);
        _spiritShotCount = set.getInteger("spiritshots", 0);
        _kamaelConvert = set.getInteger("kamael_convert", 0);

        _rndDam = set.getInteger("rnd_dam", 0);
        _atkReuse = set.getInteger("atk_reuse", 0);
        _mpConsume = set.getInteger("mp_consume", 0);

        if (getItemType() == WeaponType.NONE) {
            _type1 = TYPE1_SHIELD_ARMOR;
            _type2 = TYPE2_SHIELD_ARMOR;
        } else {
            _type1 = TYPE1_WEAPON_RING_EARRING_NECKLACE;
            _type2 = TYPE2_WEAPON;
        }

        if (getItemType() == WeaponType.PET) {
            _type1 = ItemTemplate.TYPE1_WEAPON_RING_EARRING_NECKLACE;

            if (_bodyPart == ItemTemplate.SLOT_WOLF) {
                _type2 = ItemTemplate.TYPE2_PET_WOLF;
            } else if (_bodyPart == ItemTemplate.SLOT_GWOLF) {
                _type2 = ItemTemplate.TYPE2_PET_GWOLF;
            } else if (_bodyPart == ItemTemplate.SLOT_HATCHLING) {
                _type2 = ItemTemplate.TYPE2_PET_HATCHLING;
            } else {
                _type2 = ItemTemplate.TYPE2_PET_STRIDER;
            }

            _bodyPart = ItemTemplate.SLOT_R_HAND;
        }
    }

    /**
     * Returns the type of Weapon
     *
     * @return L2WeaponType
     */
    @Override
    public WeaponType getItemType() {
        return (WeaponType) type;
    }

    /**
     * Returns the ID of the Etc item after applying the mask.
     *
     * @return int : ID of the Weapon
     */
    @Override
    public long getItemMask() {
        return getItemType().mask();
    }

    /**
     * Returns the quantity of SoulShot used.
     *
     * @return int
     */
    public int getSoulShotCount() {
        return _soulShotCount;
    }

    /**
     * Returns the quatity of SpiritShot used.
     *
     * @return int
     */
    public int getSpiritShotCount() {
        return _spiritShotCount;
    }

    public int getCritical() {
        return _critical;
    }

    /**
     * Returns the random damage inflicted by the weapon
     *
     * @return int
     */
    public int getRandomDamage() {
        return _rndDam;
    }

    /**
     * Return the Attack Reuse Delay of the L2Weapon.<BR><BR>
     *
     * @return int
     */
    public int getAttackReuseDelay() {
        return _atkReuse;
    }

    /**
     * Returns the MP consumption with the weapon
     *
     * @return int
     */
    public int getMpConsume() {
        return _mpConsume;
    }

    /**
     * Возвращает разницу между длиной этого оружия и стандартной, то есть x-40
     */
    public int getAttackRange() {
        switch (getItemType()) {
            case BOW:
                return 460;
            case CROSSBOW:
                return 360;
            case POLE:
                return 40;
            default:
                return 0;
        }
    }

    @Override
    public void attachFunc(final FuncTemplate f) {
        //TODO для параметров set с дп,может считать стат с L2ItemInstance? (VISTALL)
        if (f._stat == Stats.CRITICAL_BASE && f._order == 0x08) {
            _critical = (int) Math.round(f._value / 10);
        }
        super.attachFunc(f);
    }

    public int getKamaelConvert() {
        return _kamaelConvert;
    }

    public enum WeaponType implements ItemType {
        NONE(1, "Shield", null, 0),
        SWORD(2, "Sword", Stats.SWORD_WPN_VULNERABILITY, 1),
        BLUNT(3, "Blunt", Stats.BLUNT_WPN_VULNERABILITY, 2),
        DAGGER(4, "Dagger", Stats.DAGGER_WPN_VULNERABILITY, 3),
        BOW(5, "Bow", Stats.BOW_WPN_VULNERABILITY, 6),
        POLE(6, "Pole", Stats.POLE_WPN_VULNERABILITY, 4),
        ETC(7, "Etc", null, 7),
        FIST(8, "Fist", Stats.FIST_WPN_VULNERABILITY, 5),
        DUAL(9, "Dual Sword", Stats.DUAL_WPN_VULNERABILITY, 8),
        DUALFIST(10, "Dual Fist", Stats.DUALFIST_WPN_VULNERABILITY, 9),
        BIGSWORD(11, "Big Sword", Stats.BIGSWORD_WPN_VULNERABILITY, 1), // Two Handed Swords
        PET(12, "Pet", Stats.FIST_WPN_VULNERABILITY, 1),
        ROD(13, "Rod", null, 10),
        BIGBLUNT(14, "Big Blunt", Stats.BIGBLUNT_WPN_VULNERABILITY, 2),
        CROSSBOW(15, "Crossbow", Stats.CROSSBOW_WPN_VULNERABILITY, 12),
        RAPIER(16, "Rapier", Stats.RAPIER_WPN_VULNERABILITY, 11),
        ANCIENTSWORD(17, "Ancient Sword", Stats.ANCIENT_WPN_VULNERABILITY, 13), // Kamael 2h sword
        DUALDAGGER(18, "Dual Dagger", Stats.DUALDAGGER_WPN_VULNERABILITY, 3);

        public static final WeaponType[] VALUES = values();

        private final long _mask;
        private final String _name;
        private final Stats _defence;
        private final int _attack_type;

        WeaponType(final int id, final String name, final Stats defence, final int type) {
            _mask = 1L << id;
            _name = name;
            _defence = defence;
            _attack_type = type;
        }

        public long mask() {
            return _mask;
        }

        public int type() {
            return _attack_type;
        }

        public Stats getDefence() {
            return _defence;
        }

        @Override
        public String toString() {
            return _name;
        }
    }
}