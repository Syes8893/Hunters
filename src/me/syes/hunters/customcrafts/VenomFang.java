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

public class VenomFang extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.WOOD_SWORD, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§2Venom Fang");
        List<String> al = new ArrayList<String>();
        al.add("§9Intoxicate");
        al.add("§7Poisons enemies on hit,");
        al.add("§7dealing §c1.5\u2764 §7of damage over 3s.");
        al.add("§89s cooldown.");
        im.setLore((List)al);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#G#", "#D#", "#S#" });
        recipe.setIngredient('G', Material.GOLD_BLOCK).setIngredient('D', Material.DIAMOND).setIngredient('S', Material.STICK);
        return recipe;
	}

}
