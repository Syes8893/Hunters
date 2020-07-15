package me.syes.hunters.customcrafts;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class CustomCraft {
	
	public abstract void registerCraft();

	public abstract ItemStack getItem();
	
	public abstract Recipe getRecipe(ItemStack item);

}
