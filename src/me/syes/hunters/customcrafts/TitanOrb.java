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

public class TitanOrb extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 5);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§5Titan Orb");
        List<String> al = new ArrayList<String>();
        al.add("§9Immortal");
        al.add("§7Grants invinciblity for 5 seconds.");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#G#", "#H#", "#D#" });
        recipe.setIngredient('G', Material.GOLD_BLOCK).setIngredient('H', Material.SKULL_ITEM, 3).setIngredient('D', Material.DIAMOND_BOOTS);
        return recipe;
	}

}
