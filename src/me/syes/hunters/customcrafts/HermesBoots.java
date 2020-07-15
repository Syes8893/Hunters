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

public class HermesBoots extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§dHermes' Boots");
        List<String> al = new ArrayList<String>();
        al.add("§9Lightweight");
        al.add("§7Grants immunity to fall damage.");
        al.add("§7");
        al.add("§9Protection II");
        al.add("§7Reduces all damage taken by 3.2%.");
        im.setLore((List)al);
        im.addEnchant(Enchantment.PROTECTION_FALL, 10, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "D#D", "F#F", "F#F" });
        recipe.setIngredient('D', Material.DIAMOND).setIngredient('F', Material.FEATHER);
        return recipe;
	}

}
