package me.syes.hunters.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
	
	public static ItemStack getNamedItem(ItemStack item, String itemName) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(itemName.replace("&", "§"));
		item.setItemMeta(im);
		return item;
	}
	
}
