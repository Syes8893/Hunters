package me.syes.hunters.customcrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class KevlarVest extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1, (short) 238);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§7Kevlar Vest");
        List<String> al = new ArrayList<String>();
        al.add("§9Immunity");
        al.add("§7Nullifies all damage taken.");
        al.add("§8Breaks after 3 hits.");
        al.add("§7");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "D#D", "FFF", "III" });
        recipe.setIngredient('D', Material.DIAMOND).setIngredient('F', Material.FLINT).setIngredient('I', Material.IRON_INGOT);
        return recipe;
	}

}
