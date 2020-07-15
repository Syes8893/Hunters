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
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Honey extends CustomCraft {

	public void registerCraft() {
        Bukkit.getServer().addRecipe(getRecipe(getItem()));
	}

	public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.POTION, 1, (short) 8227);
        PotionMeta im = (PotionMeta) item.getItemMeta();
        im.setDisplayName("§6Honey");
        List<String> al = new ArrayList<String>();
        al.add("§9Medicine");
        al.add("§7Heals §c2\u2764 §7over 4s.");
        im.setLore((List)al);
        im.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 4 * 20, 1), true);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        item.setItemMeta(im);
        return item;
	}

	public Recipe getRecipe(ItemStack item) {
        ShapedRecipe recipe = new ShapedRecipe(item);
        recipe.shape(new String[] { "#G#", "#A#", "#G#" });
        recipe.setIngredient('G', Material.GOLD_INGOT).setIngredient('A', Material.APPLE);
        return recipe;
	}

}
