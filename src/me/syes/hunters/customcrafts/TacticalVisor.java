package me.syes.hunters.customcrafts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class TacticalVisor extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("§aTactical Visor");
        List<String> al = new ArrayList<String>();
        al.add("§9Nuclear Strike");
        al.add("§7Requests an airstrike damaging");
        al.add("§7all players for up to §c4\u2764§7.");
        al.add("§8Also damages user.");
        im.setLore((List)al);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "GID", "III", "DIG" });
        recipe.setIngredient('G', Material.GOLD_INGOT).setIngredient('D', Material.DIAMOND).setIngredient('I', Material.IRON_INGOT);
        return recipe;
	}

}
