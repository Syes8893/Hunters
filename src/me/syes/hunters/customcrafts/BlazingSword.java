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

public class BlazingSword extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.STONE_SWORD, 1, (short) 127);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§cBlazing Sword");
        List<String> al = new ArrayList<String>();
        al.add("§9Fire Aspect I");
        al.add("§7Ignites players on hit.");
        al.add("§8Breaks after 5 uses.");
        im.setLore((List)al);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#D#", "FDF", "#I#" });
        recipe.setIngredient('D', Material.DIAMOND).setIngredient('F', Material.FLINT).setIngredient('I', Material.IRON_SWORD);
        return recipe;
	}

}
