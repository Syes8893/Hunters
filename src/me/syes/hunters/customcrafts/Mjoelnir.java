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

public class Mjoelnir extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§eMjölnir");
        List<String> al = new ArrayList<String>();
        al.add("§9Thunderstorm");
        al.add("§7Strikes lightning on hit,");
        al.add("§7dealing §c1.5\u2764 §7of true damage.");
        al.add("§84s cooldown.");
        im.setLore((List)al);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "HDD", "#SD", "#S#" });
        recipe.setIngredient('H', Material.SKULL_ITEM, 3).setIngredient('D', Material.DIAMOND).setIngredient('S', Material.STICK);
        return recipe;
	}

}
