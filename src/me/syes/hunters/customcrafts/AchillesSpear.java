package me.syes.hunters.customcrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class AchillesSpear extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.IRON_SPADE, 1, (short) 250);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§4Achilles' Spear");
        List<String> al = new ArrayList<String>();
        al.add("§9Vengeance");
        al.add("§7Upon hitting an enemy");
        al.add("§7destroys one of their armor pieces.");
        al.add("§8Breaks after 1 use.");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#D#", "DSD", "#S#" });
        recipe.setIngredient('D', Material.DIAMOND).setIngredient('S', Material.STICK);
        return recipe;
	}
	
}
