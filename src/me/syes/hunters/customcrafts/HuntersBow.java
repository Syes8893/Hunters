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

public class HuntersBow extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§bHunters Bow");
        List<String> al = new ArrayList<String>();
        al.add("§9Huntsman");
        al.add("§7Hitting an enemy gives");
        al.add("§7Speed II for 4s.");
        al.add("§7");
        al.add("§9Power I");
        al.add("§7Arrows deal +75% damage.");
        im.setLore((List)al);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#SI", "F#D", "#SI" });
        recipe.setIngredient('S', Material.STICK).setIngredient('I', Material.IRON_INGOT).setIngredient('D', Material.DIAMOND).setIngredient('F', Material.FEATHER);
        return recipe;
	}

}
