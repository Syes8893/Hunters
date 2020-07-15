package me.syes.hunters.customcrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class WarpOrb extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.EYE_OF_ENDER, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§3Warp Orb");
        List<String> al = new ArrayList<String>();
        al.add("§9Escape");
        al.add("§7Teleports you to back to spawn");
        al.add("§7and heals §c1.5\u2764§7.");
        al.add("§82s cooldown.");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#F#", "IDI", "#F#" });
        recipe.setIngredient('F', Material.FEATHER).setIngredient('D', Material.DIAMOND).setIngredient('I', Material.IRON_INGOT);
        return recipe;
	}

}
