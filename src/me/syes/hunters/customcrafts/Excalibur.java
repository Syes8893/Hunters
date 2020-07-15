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

public class Excalibur extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.GOLD_SWORD, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§dExcalibur");
        List<String> al = new ArrayList<String>();
        al.add("§9Consumption");
        al.add("§7Heals §c0.5\u2764 §7on hit.");
        al.add("§82s cooldown.");
        al.add("§7");
        al.add("§9Sharpness II");
        al.add("§7Melee hits deal +2.5 damage.");
        im.setLore((List)al);
        im.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        im.addEnchant(Enchantment.DURABILITY, 100, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "GIG", "DGD", "#S#" });
        recipe.setIngredient('G', Material.GOLD_INGOT).setIngredient('I', Material.IRON_BLOCK).setIngredient('D', Material.DIAMOND).setIngredient('S', Material.STICK);
        return recipe;
	}

}
