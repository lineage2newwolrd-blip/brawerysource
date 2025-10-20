package handler.items;

import org.mmocore.gameserver.data.xml.holder.DressArmorHolder;
import org.mmocore.gameserver.data.xml.holder.DressWeaponHolder;
import org.mmocore.gameserver.network.lineage.serverpackets.ChooseInventoryItem;
import org.mmocore.gameserver.object.Playable;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;

import java.util.HashSet;
import java.util.Set;

/**
 * @author iRock
 * @date 13.02.2017
 */
public class DressImage extends ScriptItemHandler
{
	@Override
	public boolean useItem(Playable playable, ItemInstance item, boolean ctrl)
	{
		if(playable == null || !playable.isPlayer())
			return false;
		Player player = (Player) playable;

		if(player.getEnchantScroll() != null)
			return false;

		player.setEnchantScroll(item);
		player.sendPacket(new ChooseInventoryItem(item.getItemId()));
		return true;
	}

	@Override
	public final int[] getItemIds()
	{
		Set<Integer> set = new HashSet<>(DressArmorHolder.getInstance().size() + DressWeaponHolder.getInstance().size());
		DressArmorHolder.getInstance().getAllDress().stream().forEach(a->set.add(a.getId()));
		DressWeaponHolder.getInstance().getAllWeapons().stream().forEach(a->set.add(a.getId()));

		return set.stream().mapToInt(Number::intValue).toArray();
	}
}