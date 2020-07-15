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

public class ArtemisBow extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§3Artemis Bow");
        List<String> al = new ArrayList<String>();
        al.add("§9Aiming");
        al.add("§7Arrows will home towards players");
        al.add("§7within 5 blocks.");
        al.add("§8Shot arrows deal 25% less damage.");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#SR", "S#E", "#SR" });
        recipe.setIngredient('S', Material.STICK).setIngredient('E', Material.EYE_OF_ENDER).setIngredient('R', Material.STRING);
        return recipe;
	}
}
