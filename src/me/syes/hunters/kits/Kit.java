package me.syes.hunters.kits;

import org.bukkit.inventory.ItemStack;

public abstract class Kit {
	
	protected String name;
	protected ItemStack icon;
	
	public abstract void giveKit();
	
	public abstract ItemStack getIcon();
	
	public abstract String getName();

}
