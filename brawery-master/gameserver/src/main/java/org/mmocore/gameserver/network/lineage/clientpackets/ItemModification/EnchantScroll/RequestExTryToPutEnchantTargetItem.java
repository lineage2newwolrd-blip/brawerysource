package org.mmocore.gameserver.network.lineage.clientpackets.ItemModification.EnchantScroll;

import org.mmocore.gameserver.data.xml.holder.DressArmorHolder;
import org.mmocore.gameserver.data.xml.holder.DressWeaponHolder;
import org.mmocore.gameserver.model.items.etcitems.EnchantScrolls.EnchantScrollInfo;
import org.mmocore.gameserver.model.items.etcitems.EnchantScrolls.EnchantScrollManager;
import org.mmocore.gameserver.network.lineage.clientpackets.ItemModification.abstracts.AbstractEnchantPacket;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ItemModification.EnchantScroll.ExPutEnchantTargetItemResult;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;
import org.mmocore.gameserver.object.components.player.inventory.PcInventory;
import org.mmocore.gameserver.templates.item.ItemTemplate;
import org.mmocore.gameserver.utils.Log;

public class RequestExTryToPutEnchantTargetItem extends AbstractEnchantPacket {
    private int objectId;

    @Override
    protected void readImpl() {
        objectId = readD();
    }

    @Override
    protected void runImpl() {
        Player player = getClient().getActiveChar();
        if (player == null)
            return;

        if (!isValidPlayer(player)) {
            player.sendPacket(ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }

        PcInventory inventory = player.getInventory();
        ItemInstance itemToEnchant = inventory.getItemByObjectId(objectId);
        ItemInstance scroll = player.getEnchantScroll();

        if (itemToEnchant == null || scroll == null) {
            Log.audit("[EnchantTargetItem]", "Player(ID:" + player.getObjectId() + ") name: " + player.getName() + " used packetHack or another programm, and send wrong packet!");
            player.sendPacket(SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            player.sendPacket(ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }

        Log.add(player.getName() + "|Trying to put enchant|" + itemToEnchant.getItemId() + "|+" + itemToEnchant.getEnchantLevel() + '|' + itemToEnchant.getObjectId(), "enchants");

        if (inventory.getItemByObjectId(scroll.getObjectId()) == null) {
            player.sendPacket(SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            player.sendPacket(ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }

        EnchantScrollInfo esi = EnchantScrollManager.getScrollInfo(scroll.getItemId());

        if (esi == null) {
            if(altEnchant(player, itemToEnchant, scroll))
                return;
            player.sendPacket(ExPutEnchantTargetItemResult.FAIL);
            player.sendPacket(SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            player.setEnchantScroll(null);
            return;
        }

        if (!checkItem(itemToEnchant, esi)) {
            player.sendPacket(ExPutEnchantTargetItemResult.FAIL);
            player.sendPacket(SystemMsg.INAPPROPRIATE_ENCHANT_CONDITIONS);
            player.setEnchantScroll(null);
            return;
        }

        if (itemToEnchant.getEnchantLevel() >= esi.getMax() || itemToEnchant.getEnchantLevel() < esi.getMin()) {
            player.sendPacket(ExPutEnchantTargetItemResult.FAIL);
            player.sendPacket(SystemMsg.INAPPROPRIATE_ENCHANT_CONDITIONS);
            player.setEnchantScroll(null);
            return;
        }

        // Запрет на заточку чужих вещей, баг может вылезти на серверных лагах
        if (itemToEnchant.getOwnerId() != player.getObjectId()) {
            Log.audit("[EnchantTargetItem]", "Player(ID:" + player.getObjectId() + ") name: " + player.getName() + " used packetHack or another programm, and send wrong packet!");
            player.sendPacket(ExPutEnchantTargetItemResult.FAIL);
            player.setEnchantScroll(null);
            return;
        }

        player.sendPacket(ExPutEnchantTargetItemResult.SUCCESS);
    }

    @Deprecated
    private static boolean altEnchant(Player activeChar, ItemInstance itemToEnchant, ItemInstance scroll)
    {
        int scrollId = scroll.getItemId();

        // Запрет на заточку чужих вещей, баг может вылезти на серверных лагах
        if(itemToEnchant.getOwnerId() != activeChar.getObjectId())
        {
            Log.audit("[EnchantTargetItem]", "Player(ID:" + activeChar.getObjectId() + ") name: " + activeChar.getName() + " used packetHack or another programm, and send wrong packet!");
            return false;
        }
        // Visual
        if(DressArmorHolder.getInstance().getAllDress().stream().anyMatch(a->a.getId() == scrollId) && (itemToEnchant.getBodyPart() == ItemTemplate.SLOT_CHEST || itemToEnchant.getBodyPart() == ItemTemplate.SLOT_FULL_ARMOR))
        {
            activeChar.sendPacket(ExPutEnchantTargetItemResult.SUCCESS);
            return true;
        }
        if(DressWeaponHolder.getInstance().getAllWeapons().stream().anyMatch(w->w.getId() == scrollId) && (itemToEnchant.getBodyPart() == ItemTemplate.SLOT_LR_HAND || itemToEnchant.getBodyPart() == ItemTemplate.SLOT_R_HAND))
        {
            activeChar.sendPacket(ExPutEnchantTargetItemResult.SUCCESS);
            return true;
        }

        return false;
    }
}