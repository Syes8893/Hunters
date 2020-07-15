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

public class HealingOrb extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.INK_SACK, 1, (short) 10);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§aHealing Orb");
        List<String> al = new ArrayList<String>();
        al.add("§9Cure");
        al.add("§7Instantly heals §c2\u2764§f.");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#G#", "GAG", "#G#" });
        recipe.setIngredient('G', Material.GOLD_INGOT).setIngredient('A', Material.APPLE);
        return recipe;
	}

}
