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

public class BlazingBow extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.BOW, 1, (short) 380);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§cBlazing Bow");
        List<String> al = new ArrayList<String>();
        al.add("§9Flame");
        al.add("§7Shoots fire arrows,");
        al.add("§7hit players will be ignited.");
        al.add("§8Breaks after 5 uses.");
        al.add("§7");
        im.setLore((List)al);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#L#", "IBI", "#F#" });
        recipe.setIngredient('L', Material.FLINT).setIngredient('I', Material.IRON_INGOT).setIngredient('F', Material.FEATHER).setIngredient('B', Material.BOW);
        return recipe;
	}

}
