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

public class KingsCrown extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.GOLD_HELMET, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§6King's Crown");
        List<String> al = new ArrayList<String>();
        al.add("§9Life Drain");
        al.add("§7Gives §64\u2764 §7on kill for 20s.");
        al.add("§7");
        al.add("§9Protection IV");
        al.add("§7Reduces all damage taken by 6.4%.");
        im.setLore((List)al);
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        im.addEnchant(Enchantment.DURABILITY, 100, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "IGI", "DDD", "###" });
        recipe.setIngredient('I', Material.IRON_INGOT).setIngredient('G', Material.GOLD_BLOCK).setIngredient('D', Material.DIAMOND);
        return recipe;
	}

}
