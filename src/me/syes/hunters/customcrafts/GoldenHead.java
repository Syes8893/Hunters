package me.syes.hunters.customcrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldenHead extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§6Golden Head");
        List<String> al = new ArrayList<String>();
        al.add("§9Panacea");
        al.add("§7Heals §c4\u2764 §7over 10s and gives");
        al.add("§7an additional §62\u2764§7.");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#G#", "GRG", "#G#" });
        recipe.setIngredient('G', Material.GOLD_INGOT).setIngredient('R', Material.SKULL_ITEM, 3);
        return recipe;
	}

}
