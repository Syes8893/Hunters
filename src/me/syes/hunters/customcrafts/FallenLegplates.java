package me.syes.hunters.customcrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class FallenLegplates extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta im = (LeatherArmorMeta) item.getItemMeta();
        im.setColor(Color.OLIVE);
        im.setDisplayName("§dFallen's Legplates");
        List<String> al = new ArrayList<String>();
        al.add("§9Rejuvenate");
        al.add("§7Heals §c1.5\u2764 §7on damage taken.");
        al.add("§83s cooldown.");
        al.add("§7");
        al.add("§9Protection III");
        al.add("§7Reduces all damage taken by 4.8%.");
        im.setLore((List)al);
        im.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        im.addEnchant(Enchantment.DURABILITY, 100, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "DGD", "D#D", "I#I" });
        recipe.setIngredient('D', Material.DIAMOND).setIngredient('G', Material.GOLDEN_APPLE).setIngredient('I', Material.IRON_INGOT);
        return recipe;
	}

}
