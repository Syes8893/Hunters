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

public class MysticCodex extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.BOOK, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§eMystic Codex");
        List<String> al = new ArrayList<String>();
        al.add("§9Mystery");
        al.add("§7Gives a random custom craft.");
        im.setLore((List)al);
        im.addEnchant(Enchantment.LUCK, 1, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "SFS", "ISI", "DID" });
        recipe.setIngredient('S', Material.STICK).setIngredient('F', Material.FEATHER).setIngredient('I', Material.IRON_INGOT).setIngredient('D', Material.DIAMOND);
        return recipe;
	}

}
