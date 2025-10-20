package handler.voicecommands;

import org.jts.dataparser.data.holder.setting.common.PlayerRace;
import org.mmocore.commons.database.dao.JdbcEntityState;
import org.mmocore.commons.threading.RunnableImpl;
import org.mmocore.gameserver.ThreadPoolManager;
import org.mmocore.gameserver.configuration.config.clientCustoms.OtherCustom;
import org.mmocore.gameserver.data.htm.HtmCache;
import org.mmocore.gameserver.data.xml.holder.*;
import org.mmocore.gameserver.handler.voicecommands.IVoicedCommandHandler;
import org.mmocore.gameserver.handler.voicecommands.VoicedCommandHandler;
import org.mmocore.gameserver.listener.actor.player.OnAnswerListener;
import org.mmocore.gameserver.listener.script.OnInitScriptListener;
import org.mmocore.gameserver.model.dress.DressArmorData;
import org.mmocore.gameserver.model.dress.DressCloakData;
import org.mmocore.gameserver.model.dress.DressWeaponData;
import org.mmocore.gameserver.network.lineage.components.HtmlMessage;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ConfirmDlg;
import org.mmocore.gameserver.network.lineage.serverpackets.InventoryUpdate;
import org.mmocore.gameserver.network.lineage.serverpackets.ShopPreviewInfo;
import org.mmocore.gameserver.network.lineage.serverpackets.ShortCutRegister;
import org.mmocore.gameserver.object.Inventory;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.object.components.player.interfaces.ShortCut;
import org.mmocore.gameserver.templates.item.ArmorTemplate.ArmorType;
import org.mmocore.gameserver.templates.item.ItemTemplate;
import org.mmocore.gameserver.templates.item.ItemType;
import org.mmocore.gameserver.templates.item.WeaponTemplate.WeaponType;
import org.mmocore.gameserver.utils.Language;
import org.mmocore.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iRock
 * Date: 24.10.2017
 */
public class DressMe implements IVoicedCommandHandler, OnInitScriptListener {
    private static final Logger _log = LoggerFactory.getLogger(DressMe.class);

    private static Map<Integer, DressWeaponData> SWORD;
    private static Map<Integer, DressWeaponData> BLUNT;
    private static Map<Integer, DressWeaponData> DAGGER;
    private static Map<Integer, DressWeaponData> BOW;
    private static Map<Integer, DressWeaponData> POLE;
    private static Map<Integer, DressWeaponData> FIST;
    private static Map<Integer, DressWeaponData> DUAL;
    private static Map<Integer, DressWeaponData> DUALFIST;
    private static Map<Integer, DressWeaponData> BIGSWORD;
    private static Map<Integer, DressWeaponData> ROD;
    private static Map<Integer, DressWeaponData> BIGBLUNT;
    private static Map<Integer, DressWeaponData> CROSSBOW;
    private static Map<Integer, DressWeaponData> RAPIER;
    private static Map<Integer, DressWeaponData> ANCIENTSWORD;
    private static Map<Integer, DressWeaponData> DUALDAGGER;

    private String[] _commandList = new String[] {
            "dressme",
            "dressme-armor",
            "dressme-cloak",
            "dressme-weapon",
            "dress-armor",
            "dress-cloak",
            "dress-weapon",
            "dress-armorpage",
            "dress-cloakpage",
            "dress-weaponpage",
            "undressme",
            "undressme-armor",
            "undressme-cloak",
            "undressme-weapon" };

    @Override
    public boolean useVoicedCommand(String command, Player player, String args)
    {
        /*if(command.equals("dressme") && args!= null && !args.isEmpty()) {
            String[] render = args.split(" ");
            args = Strings.joinStrings(" ", render, 1, render.length);
            command = render[0];
        }*/
        switch (command) {
            case "dressme": {
                HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/dressme.htm");
                player.sendPacket(html);
                return true;
            }
            case "dressme-armor": {
                HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/index-armor.htm");
                String template = HtmCache.getInstance().getHtml("command/dressme/template-armor.htm", player);
                String block;
                String list = "";

                if (args == null)
                    args = "1";

                String[] param = args.split(" ");

                final int page = param[0].length() > 0 ? Integer.parseInt(param[0]) : 1;
                final int perpage = 6;
                int counter = 0;

                int preCount = 0;
                for (DressArmorData dress : DressArmorHolder.getInstance().getAllDress()) {
                    if(dress==null)
                        continue;
                    if (preCount < (page - 1) * perpage) {
                        preCount++;
                        continue;
                    }
                    if (player.getPlayerTemplateComponent().getPlayerRace() == PlayerRace.kamael) {
                        ItemTemplate chest = ItemTemplateHolder.getInstance().getTemplate(dress.getChest());
                        if (chest == null)
                            continue;
                        if (chest.getItemType() == ArmorType.HEAVY || chest.getItemType() == ArmorType.MAGIC)
                            continue;
                    }

                    block = template;
                    String dress_name = dress.getName();
                    if (dress_name.length() > 29)
                        dress_name = dress_name.substring(0, 29) + "...";
                    block = block.replace("{bypass}", "bypass -h user_dress-armorpage " + (dress.getId()));
                    block = block.replace("{name}", dress_name);
                    block = block.replace("{price}", Util.formatPay(player, dress.getPriceCount(), dress.getPriceId()));
                    block = block.replace("{icon}", dress.get_icon());
                    list += block;
                    counter++;
                    if (counter >= perpage)
                        break;
                }

                double count = Math.ceil((double) DressArmorHolder.getInstance().size() / (double) perpage);
                int inline = 1;
                String navigation = "";

                for (int i = 1; i <= count; i++) {
                    if (i == page)
                        navigation += "<td width=25 align=center valign=top><button value=\"[" + i + "]\" action=\"bypass -h user_dressme-armor " + i + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                    else
                        navigation += "<td width=25 align=center valign=top><button value=\"" + i + "\" action=\"bypass -h user_dressme-armor " + i + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";

                    if (inline == 7) {
                        navigation += "</tr><tr>";
                        inline = 0;
                    }
                    inline++;
                }
                navigation += "<td width=25 align=center valign=top><button value=\"Back\" action=\"bypass -h user_dressme\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                html = html.replace("{list}", list);
                html = html.replace("{navigation}", navigation);

                player.sendPacket(html);
                return true;
            }
            case "dressme-cloak": {
                HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/index-cloak.htm");
                String template = HtmCache.getInstance().getHtml("command/dressme/template-cloak.htm", player);
                String block;
                String list = "";

                if (args == null)
                    args = "1";

                String[] param = args.split(" ");

                final int page = param[0].length() > 0 ? Integer.parseInt(param[0]) : 1;
                final int perpage = 6;
                int counter = 0;

                for (int i = (page - 1) * perpage; i < DressCloakHolder.getInstance().size(); i++) {
                    DressCloakData cloak = DressCloakHolder.getInstance().getCloak(i + 1);

                    if (cloak != null) {
                        block = template;

                        String cloak_name = cloak.getName();

                        if (cloak_name.length() > 29)
                            cloak_name = cloak_name.substring(0, 29) + "...";

                        block = block.replace("{bypass}", "bypass -h user_dress-cloakpage " + (i + 1));
                        block = block.replace("{name}", cloak_name);
                        block = block.replace("{price}", Util.formatPay(player, cloak.getPriceCount(), cloak.getPriceId()));
                        block = block.replace("{icon}", cloak.get_icon());
                        list += block;
                    }

                    counter++;

                    if (counter >= perpage)
                        break;
                }

                double count = Math.ceil((double) DressCloakHolder.getInstance().size() / (double) perpage);
                int inline = 1;
                String navigation = "";

                for (int i = 1; i <= count; i++) {
                    if (i == page)
                        navigation += "<td width=25 align=center valign=top><button value=\"[" + i + "]\" action=\"bypass -h user_dressme-cloak " + i + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                    else
                        navigation += "<td width=25 align=center valign=top><button value=\"" + i + "\" action=\"bypass -h user_dressme-cloak " + i + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";

                    if (inline == 7) {
                        navigation += "</tr><tr>";
                        inline = 0;
                    }
                    inline++;
                }
                navigation += "<td width=25 align=center valign=top><button value=\"Back\" action=\"bypass -h user_dressme\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                html = html.replace("{list}", list);
                html = html.replace("{navigation}", navigation);

                player.sendPacket(html);
                return true;
            }
            case "dressme-weapon": {
                ItemInstance slot = player.getInventory().getPaperdollItem(Inventory.PAPERDOLL_RHAND);
                if (slot == null) {
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Ошибка: Оружие должно быть надето!" : "Error: Weapon must be equiped!");
                    useVoicedCommand("dressme", player, null);
                    return false;
                }

                ItemType type = slot.getItemType();

                HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/index-weapon.htm");
                String template = HtmCache.getInstance().getHtml("command/dressme/template-weapon.htm", player);
                String block;
                String list = "";

                if (args == null)
                    args = "1";

                String[] param = args.split(" ");

                final int page = param[0].length() > 0 ? Integer.parseInt(param[0]) : 1;
                final int perpage = 6;
                int counter = 0;
                Map<Integer, DressWeaponData> map = initMap(type.toString());

                if (map == null) {
                    _log.error("Dress me system: Weapon Map is null.");
                    return false;
                }

                for (int i = (page - 1) * perpage; i < map.size(); i++) {
                    DressWeaponData weapon = map.get(i + 1);
                    if (weapon != null) {
                        block = template;

                        String cloak_name = weapon.getName();

                        if (cloak_name.length() > 29)
                            cloak_name = cloak_name.substring(0, 29) + "...";

                        block = block.replace("{bypass}", "bypass -h user_dress-weaponpage " + weapon.getId());
                        block = block.replace("{name}", cloak_name);
                        block = block.replace("{price}", Util.formatPay(player, weapon.getPriceCount(), weapon.getPriceId()));
                        block = block.replace("{icon}", weapon.get_icon());
                        list += block;
                    }

                    counter++;

                    if (counter >= perpage)
                        break;
                }

                double count = Math.ceil((double) map.size() / (double) perpage);
                int inline = 1;
                String navigation = "";

                for (int i = 1; i <= count; i++) {
                    if (i == page)
                        navigation += "<td width=25 align=center valign=top><button value=\"[" + i + "]\" action=\"bypass -h user_dressme-weapon " + i + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                    else
                        navigation += "<td width=25 align=center valign=top><button value=\"" + i + "\" action=\"bypass -h user_dressme-weapon " + i + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";

                    if (inline == 7) {
                        navigation += "</tr><tr>";
                        inline = 0;
                    }
                    inline++;
                }
                navigation += "<td width=25 align=center valign=top><button value=\"Back\" action=\"bypass -h user_dressme\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                html = html.replace("{list}", list);
                html = html.replace("{navigation}", navigation);

                player.sendPacket(html);
                return true;
            }
            case "dress-armorpage": {
                final int set = Integer.parseInt(args.split(" ")[0]);
                DressArmorData dress = DressArmorHolder.getInstance().getArmor(set);
                if (dress != null) {
                    HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/dress-armor.htm");

                    Inventory inv = player.getInventory();

                    ItemInstance my_chest = inv.getPaperdollItem(Inventory.PAPERDOLL_CHEST);
                    html = html.replace("{my_chest_icon}", my_chest == null ? "icon.NOIMAGE" : my_chest.getTemplate().getIcon());
                    ItemInstance my_legs = inv.getPaperdollItem(Inventory.PAPERDOLL_LEGS);
                    html = html.replace("{my_legs_icon}", my_legs == null ? "icon.NOIMAGE" : my_legs.getTemplate().getIcon());
                    ItemInstance my_gloves = inv.getPaperdollItem(Inventory.PAPERDOLL_GLOVES);
                    html = html.replace("{my_gloves_icon}", my_gloves == null ? "icon.NOIMAGE" : my_gloves.getTemplate().getIcon());
                    ItemInstance my_feet = inv.getPaperdollItem(Inventory.PAPERDOLL_FEET);
                    html = html.replace("{my_feet_icon}", my_feet == null ? "icon.NOIMAGE" : my_feet.getTemplate().getIcon());

                    html = html.replace("{bypass}", "bypass -h user_dress-armor " + set);
                    html = html.replace("{name}", dress.getName());
                    html = html.replace("{price}", Util.formatPay(player, dress.getPriceCount(), dress.getPriceId()));

                    ItemTemplate chest = ItemTemplateHolder.getInstance().getTemplate(dress.getChest());
                    html = html.replace("{chest_icon}", chest.getIcon());
                    html = html.replace("{chest_name}", chest.getName());
                    html = html.replace("{chest_grade}", chest.getItemGrade().name());

                    if (dress.getLegs() != -1) {
                        ItemTemplate legs = ItemTemplateHolder.getInstance().getTemplate(dress.getLegs());
                        html = html.replace("{legs_icon}", legs.getIcon());
                        html = html.replace("{legs_name}", legs.getName());
                        html = html.replace("{legs_grade}", legs.getItemGrade().name());
                    } else {
                        html = html.replace("{legs_icon}", "icon.NOIMAGE");
                        html = html.replace("{legs_name}", "<font color=FF0000>...</font>");
                        html = html.replace("{legs_grade}", "NO");
                    }

                    if (dress.getGloves() != -1) {
                        ItemTemplate gloves = ItemTemplateHolder.getInstance().getTemplate(dress.getGloves());
                        html = html.replace("{gloves_icon}", gloves.getIcon());
                        html = html.replace("{gloves_name}", gloves.getName());
                        html = html.replace("{gloves_grade}", gloves.getItemGrade().name());
                    } else {
                        html = html.replace("{gloves_icon}", "icon.NOIMAGE");
                        html = html.replace("{gloves_name}", "<font color=FF0000>...</font>");
                        html = html.replace("{gloves_grade}", "NO");
                    }

                    if (dress.getFeet() != -1) {
                        ItemTemplate feet = ItemTemplateHolder.getInstance().getTemplate(dress.getFeet());
                        html = html.replace("{feet_icon}", feet.getIcon());
                        html = html.replace("{feet_name}", feet.getName());
                        html = html.replace("{feet_grade}", feet.getItemGrade().name());
                    } else {
                        html = html.replace("{feet_icon}", "icon.NOIMAGE");
                        html = html.replace("{feet_name}", "<font color=FF0000>...</font>");
                        html = html.replace("{feet_grade}", "NO");
                    }

                    player.sendPacket(html);
                    return true;
                } else
                    return false;

            }
            case "dress-cloakpage": {
                final int set = Integer.parseInt(args.split(" ")[0]);
                DressCloakData cloak = DressCloakHolder.getInstance().getCloak(set);
                if (cloak != null) {
                    HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/dress-cloak.htm");

                    Inventory inv = player.getInventory();

                    ItemInstance my_cloak = inv.getPaperdollItem(Inventory.PAPERDOLL_BACK);
                    html = html.replace("{my_cloak_icon}", my_cloak == null ? "icon.NOIMAGE" : my_cloak.getTemplate().getIcon());

                    html = html.replace("{bypass}", "bypass -h user_dress-cloak " + cloak.getId());
                    html = html.replace("{name}", cloak.getName());
                    html = html.replace("{price}", Util.formatPay(player, cloak.getPriceCount(), cloak.getPriceId()));

                    ItemTemplate item = ItemTemplateHolder.getInstance().getTemplate(cloak.getCloakId());
                    html = html.replace("{item_icon}", item.getIcon());
                    html = html.replace("{item_name}", item.getName());
                    html = html.replace("{item_grade}", item.getItemGrade().name());

                    player.sendPacket(html);
                    return true;
                } else
                    return false;
            }
            case "dress-weaponpage": {
                final int set = Integer.parseInt(args.split(" ")[0]);
                DressWeaponData weapon = DressWeaponHolder.getInstance().getWeapon(set);
                if (weapon != null) {
                    HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/dress-weapon.htm");

                    Inventory inv = player.getInventory();

                    ItemInstance my_weapon = inv.getPaperdollItem(Inventory.PAPERDOLL_RHAND);

                    html = html.replace("{my_weapon_icon}", my_weapon == null ? "icon.NOIMAGE" : my_weapon.getTemplate().getIcon());

                    html = html.replace("{bypass}", "bypass -h user_dress-weapon " + weapon.getId());
                    html = html.replace("{name}", weapon.getName());
                    html = html.replace("{price}", Util.formatPay(player, weapon.getPriceCount(), weapon.getPriceId()));

                    ItemTemplate item = ItemTemplateHolder.getInstance().getTemplate(weapon.getWeaponId());
                    html = html.replace("{item_icon}", item.getIcon());
                    html = html.replace("{item_name}", item.getName());
                    html = html.replace("{item_grade}", item.getItemGrade().name());

                    player.sendPacket(html);
                    return true;
                } else
                    return false;
            }
            case "dress-armor": {
                final int set = Integer.parseInt(args.split(" ")[0]);

                DressArmorData dress = DressArmorHolder.getInstance().getArmor(set);
                Inventory inv = player.getInventory();

                ItemInstance chest = inv.getPaperdollItem(Inventory.PAPERDOLL_CHEST);

                if (chest == null) {
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Ошибка: Доспехи должны быть надеты." : "Error: Chest must be equiped.");
                    useVoicedCommand("dress-armorpage", player, args);
                    return false;
                }

                if (player.getInventory().getCountOf(dress.getPriceId()) >= dress.getPriceCount()) {
                    player.getInventory().destroyItemByItemId(dress.getPriceId(), dress.getPriceCount());
                    tryDress(player, chest.getTemplate(), set);
                    useVoicedCommand("dressme-armor", player, null);
                    return true;
                } else {
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Ошибка: Недостаточно средств для оплаты." : "Error: You don't have items to pay.");
                    return false;
                }

            }
            case "dress-cloak": {
                final int cloak_id = Integer.parseInt(args.split(" ")[0]);

                DressCloakData cloak_data = DressCloakHolder.getInstance().getCloak(cloak_id);

                ItemTemplate cloak = ItemTemplateHolder.getInstance().getTemplate(cloak_data.getCloakId());

                if (cloak == null) {
                    useVoicedCommand("dress-cloakpage", player, args);
                    return false;
                }

                if (player.getInventory().getCountOf(cloak_data.getPriceId()) >= cloak_data.getPriceCount()) {
                    player.getInventory().destroyItemByItemId(cloak_data.getPriceId(), cloak_data.getPriceCount());
                    tryDress(player, cloak, cloak_data.getId());
                    useVoicedCommand("dressme-cloak", player, null);
                    return true;
                } else {
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Ошибка: Недостаточно средств для оплаты." : "Error: You don't have items to pay.");
                    return false;
                }

            }
            case "dress-weapon": {
                final int set = Integer.parseInt(args.split(" ")[0]);

                DressWeaponData weapon_data = DressWeaponHolder.getInstance().getWeapon(set);
                Inventory inv = player.getInventory();

                ItemInstance weapon = inv.getPaperdollItem(Inventory.PAPERDOLL_RHAND);

                if (weapon == null) {
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Ошибка: Оружие должно быть надето." : "Error: Weapon must be equiped.");
                    useVoicedCommand("dress-weaponpage", player, args);
                    return false;
                }

                if (!weapon.getItemType().toString().equals(weapon_data.getType())) {
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Ошибка: Оружие должно быть такого же типа." : "Error: Weapon must be equals type.");
                    useVoicedCommand("dressme-weapon", player, null);
                    return false;
                }

                if (player.getInventory().getCountOf(weapon_data.getPriceId()) >= weapon_data.getPriceCount()) {
                    player.getInventory().destroyItemByItemId(weapon_data.getPriceId(), weapon_data.getPriceCount());
                    tryDress(player, weapon.getTemplate(), weapon_data.getId());
                    useVoicedCommand("dressme-weapon", player, null);
                    return true;
                } else {
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ? "Ошибка: Недостаточно средств для оплаты." : "Error: You don't have items to pay.");
                    return false;
                }
            }
            case "undressme": {
                HtmlMessage html = new HtmlMessage(5).setFile("command/dressme/undressme.htm");
                player.sendPacket(html);
                return true;
            }
            case "undressme-armor": {
                String message = player.getLanguage() == Language.RUSSIAN ? "Вы действительно хотите удалить внешний вид из Доспехов?" : "Do you really want to remove visual effect from Armor?";
                player.ask(new ConfirmDlg(SystemMsg.S1, 60000).addString(message), new ActionAnswerListener(player, 0));

                return true;
            }
            case "undressme-weapon": {

                String message = player.getLanguage() == Language.RUSSIAN ? "Вы действительно хотите удалить внешний вид из Оружия?" : "Do you really want to remove visual effect from Weapon?";
                player.ask(new ConfirmDlg(SystemMsg.S1, 60000).addString(message), new ActionAnswerListener(player, 1));

                return true;
            }
            default:
                return false;
        }
    }

    private Map<Integer, DressWeaponData> initMap(String type)
    {
        switch (type) {
            case "Sword":
                return SWORD;
            case "Blunt":
                return BLUNT;
            case "Dagger":
                return DAGGER;
            case "Bow":
                return BOW;
            case "Pole":
                return POLE;
            case "Fist":
                return FIST;
            case "Dual Sword":
                return DUAL;
            case "Dual Fist":
                return DUALFIST;
            case "Big Sword":
                return BIGSWORD;
            case "Rod":
                return ROD;
            case "Big Blunt":
                return BIGBLUNT;
            case "Crossbow":
                return CROSSBOW;
            case "Rapier":
                return RAPIER;
            case "Ancient Sword":
                return ANCIENTSWORD;
            case "Dual Dagger":
                return DUALDAGGER;
            default:
                _log.error("Dress me system: Unknown type: " + type);
                return null;
        }
    }

    private int parseWeapon()
    {
        int Sword = 1, Blunt = 1, Dagger = 1, Bow = 1, Pole = 1, Fist = 1, DualSword = 1, DualFist = 1, BigSword = 1, Rod = 1, BigBlunt = 1, Crossbow = 1, Rapier = 1, AncientSword = 1, DualDagger = 1;

        for(DressWeaponData weapon : DressWeaponHolder.getInstance().getAllWeapons())
        {
            switch (weapon.getType()) {
                case "Sword":
                    SWORD.put(Sword, weapon);
                    Sword++;
                    break;
                case "Blunt":
                    BLUNT.put(Blunt, weapon);
                    Blunt++;
                    break;
                case "Dagger":
                    DAGGER.put(Dagger, weapon);
                    Dagger++;
                    break;
                case "Bow":
                    BOW.put(Bow, weapon);
                    Bow++;
                    break;
                case "Pole":
                    POLE.put(Pole, weapon);
                    Pole++;
                    break;
                case "Fist":
                    FIST.put(Fist, weapon);
                    Fist++;
                    break;
                case "Dual Sword":
                    DUAL.put(DualSword, weapon);
                    DualSword++;
                    break;
                case "Dual Fist":
                    DUALFIST.put(DualFist, weapon);
                    DualFist++;
                    break;
                case "Big Sword":
                    BIGSWORD.put(BigSword, weapon);
                    BigSword++;
                    break;
                case "Rod":
                    ROD.put(Rod, weapon);
                    Rod++;
                    break;
                case "Big Blunt":
                    BIGBLUNT.put(BigBlunt, weapon);
                    BigBlunt++;
                    break;
                case "Crossbow":
                    CROSSBOW.put(Crossbow, weapon);
                    Crossbow++;
                    break;
                case "Rapier":
                    RAPIER.put(Rapier, weapon);
                    Rapier++;
                    break;
                case "Ancient Sword":
                    ANCIENTSWORD.put(AncientSword, weapon);
                    AncientSword++;
                    break;
                case "Dual Dagger":
                    DUALDAGGER.put(DualDagger, weapon);
                    DualDagger++;
                    break;
                default:
                    _log.error("Dress me system: Can't find type: " + weapon.getType());
                    break;
            }
        }

        return 0;
    }

    private static void undress(Player player, ItemInstance item)
    {
        item.setVisualItemId(0);
        item.setJdbcState(JdbcEntityState.UPDATED);
        item.update();

        player.sendPacket(new InventoryUpdate().addModifiedItem(item));
        player.getShortCutComponent().getAllShortCuts().stream().filter(sc -> sc.getId() == item.getObjectId()
                && sc.getType() == ShortCut.TYPE_ITEM).forEach(sc -> player.sendPacket(new ShortCutRegister(player, sc)));

        player.sendMessage(player.getLanguage() == Language.RUSSIAN ?"Внешний вид из " + item.getName() + " был удален.":"Visual change from " + item.getName() + " has been remove.");
    }

    private static void tryDress(Player player, ItemTemplate item, int visual)
    {
        int paperdoll = Inventory.getPaperdollIndex(item.getBodyPart());
        if(paperdoll < 0)
            return;

        Map<Integer, Integer> itemList = new HashMap<>();
        ItemTemplate template = ItemTemplateHolder.getInstance().getTemplate(visual);
        if(template==null)
            return;
        if(player.getPlayerTemplateComponent().getPlayerRace() == PlayerRace.kamael)
        {
            if(template.getItemType() == ArmorType.HEAVY || template.getItemType() == ArmorType.MAGIC || template.getItemType() == ArmorType.SIGIL || template.getItemType() == WeaponType.NONE)
                return;
        }
        else
        {
            if(template.getItemType() == WeaponType.CROSSBOW || template.getItemType() == WeaponType.RAPIER || template.getItemType() == WeaponType.ANCIENTSWORD)
                return;
        }

        switch(paperdoll){
            case Inventory.PAPERDOLL_CHEST:
                DressArmorData dress = DressArmorHolder.getInstance().getArmor(visual);
                itemList.put(Inventory.PAPERDOLL_CHEST, dress.getChest());
                itemList.put(Inventory.PAPERDOLL_LEGS, dress.getLegs());
                itemList.put(Inventory.PAPERDOLL_GLOVES, dress.getGloves());
                itemList.put(Inventory.PAPERDOLL_FEET, dress.getFeet());
                itemList.put(Inventory.PAPERDOLL_HAIR, dress.getHelmet());
                itemList.put(Inventory.PAPERDOLL_BACK, dress.getCloak());
                break;
            case Inventory.PAPERDOLL_LRHAND:
            case Inventory.PAPERDOLL_RHAND:
                DressWeaponData weapon = DressWeaponHolder.getInstance().getWeapon(visual);
                itemList.put(paperdoll, weapon.getWeaponId());
                break;
            case Inventory.PAPERDOLL_BACK:
                DressCloakData cloak = DressCloakHolder.getInstance().getCloak(visual);
                itemList.put(paperdoll, cloak.getCloakId());
                break;
        }

        if(!itemList.isEmpty())
        {
            player.sendPacket(new ShopPreviewInfo(itemList));
            // Schedule task
            if(player.getWearTask()!=null) {
                player.getWearTask().cancel(true);
                player.setWearTask(null);
            }
            player.setWearTask(ThreadPoolManager.getInstance().schedule(new RemoveWearItemsTask(player), 30 * 1000L));
        }
    }

    @Override
    public void onInit()
    {
        SWORD = new HashMap<>();
        BLUNT = new HashMap<>();
        DAGGER = new HashMap<>();
        BOW = new HashMap<>();
        POLE = new HashMap<>();
        FIST = new HashMap<>();
        DUAL = new HashMap<>();
        DUALFIST = new HashMap<>();
        BIGSWORD = new HashMap<>();
        ROD = new HashMap<>();
        BIGBLUNT = new HashMap<>();
        CROSSBOW = new HashMap<>();
        RAPIER = new HashMap<>();
        ANCIENTSWORD = new HashMap<>();
        DUALDAGGER = new HashMap<>();

        parseWeapon();
        VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
        _log.info("Dress me system: Load " + DressArmorHolder.getInstance().getAllDress().size() + " armors, " + DressWeaponHolder.getInstance().getAllWeapons().size() + " weapons.");
    }

    private static class ActionAnswerListener implements OnAnswerListener
    {
        private final Player player;
        private final int action;

        private ActionAnswerListener(Player player, int action)
        {
            this.player = player;
            this.action = action;
        }

        @Override
        public void sayYes()
        {
            if(action == 0)// Armor
            {
                Inventory inv = player.getInventory();
                ItemInstance chest = inv.getPaperdollItem(Inventory.PAPERDOLL_CHEST);

                if(chest == null){
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ?"Доспехи должны быть надеты!":"Armor must be equiped!");
                    return;
                }

                undress(player, chest);

                player.sendUserInfo(true);
                player.broadcastUserInfo(true);
            }
            else if(action == 1)// Weapon
            {
                Inventory inv = player.getInventory();
                ItemInstance weapon = inv.getPaperdollItem(Inventory.PAPERDOLL_RHAND);

                if(weapon == null){
                    player.sendMessage(player.getLanguage() == Language.RUSSIAN ?"Оружие должно быть надето!":"Weapon must be equiped!");
                    return;
                }

                undress(player, weapon);

                player.sendUserInfo(true);
                player.broadcastUserInfo(true);
            }
        }

        @Override
        public void sayNo() {
        }

    }

    private static class RemoveWearItemsTask extends RunnableImpl
    {
        private Player _activeChar;

        public RemoveWearItemsTask(Player activeChar)
        {
            _activeChar = activeChar;
        }

        @Override
        public void runImpl() throws Exception
        {
            _activeChar.sendPacket(SystemMsg.YOU_ARE_NO_LONGER_TRYING_ON_EQUIPMENT_);
            _activeChar.sendUserInfo(true);
        }
    }

    @Override
    public String[] getVoicedCommandList()
    {
        return OtherCustom.AllowDressService ? _commandList : new String[0];
    }
}
