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

public class ScrollOfWhisdom extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§eScroll of Whisdom");
        List<String> al = new ArrayList<String>();
        al.add("§9Intelligence");
        al.add("§7Gives a random positive");
        al.add("§7effect for 10s.");
        im.setLore((List)al);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addEnchant(Enchantment.DURABILITY, 1, true);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#G#", "DID", "#G#" });
        recipe.setIngredient('G', Material.GOLD_INGOT).setIngredient('I', Material.IRON_INGOT).setIngredient('D', Material.DIAMOND);
        return recipe;
	}

}
