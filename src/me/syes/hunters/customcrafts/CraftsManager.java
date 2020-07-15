package me.syes.hunters.customcrafts;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.syes.hunters.utils.ItemUtils;

public class CraftsManager {
    
	public ArrayList<CustomCraft> customCrafts;
	public ArrayList<CustomCraft> loadedCrafts;
	public HashMap<CustomCraft, Inventory> customCraftInventories;
	private ArrayList<Inventory> craftMenus;
	//private Inventory craftsMenu;
	
	public CraftsManager() {
		loadCrafts();
		registerCrafts();
		loadCraftsMenu();
		loadRecipes();
	}

	private void loadCrafts() {
    	customCrafts = new ArrayList<CustomCraft>();
		customCrafts.add(new GoldenHead());
		customCrafts.add(new HuntersBow());
		customCrafts.add(new Mjoelnir());
		customCrafts.add(new HealingOrb());
		customCrafts.add(new Excalibur());
		customCrafts.add(new KevlarVest());
		customCrafts.add(new BlazingBow());
		customCrafts.add(new HermesBoots());
		customCrafts.add(new KingsCrown());
		customCrafts.add(new TitanOrb());
		customCrafts.add(new VenomFang());
		customCrafts.add(new ScrollOfWhisdom());
		customCrafts.add(new WarpOrb());
		customCrafts.add(new Honey());
		customCrafts.add(new ArtemisBow());
		customCrafts.add(new VoidSword());
		customCrafts.add(new FallenLegplates());
		customCrafts.add(new AchillesSpear());
		customCrafts.add(new TacticalVisor());
		customCrafts.add(new MysticCodex());
		customCrafts.add(new BlazingSword());
	}

	private void registerCrafts() {
    	for(CustomCraft cc : customCrafts)
    		cc.registerCraft();
    }
	
    private void loadCraftsMenu() {
    	craftMenus = new ArrayList<Inventory>();
    	loadedCrafts = new ArrayList<CustomCraft>();
    	int craftsAmount = customCrafts.size();
    	while(craftsAmount > 0) {
    		Inventory i = Bukkit.createInventory(null, 45, "Custom Recipes");
    		int index = 10;
    		for(CustomCraft cc : getCustomCrafts()) {
    			if(index >= 35)
    				break;
    			if(loadedCrafts.contains(cc))
    				continue;
    			ShapedRecipe sr = (ShapedRecipe) cc.getRecipe(cc.getItem());
    			i.setItem(index, sr.getResult());
    			index++;
    			if((index+1)%9 == 0)
    				index+=2;
    			loadedCrafts.add(cc);
    		}
    		//i.setItem(40, ItemUtils.getNamedItem(new ItemStack(Material.BARRIER), "&cClose inventory"));
    		if(craftMenus.size() == 0 && craftsAmount > 21) {
        		i.setItem(44, ItemUtils.getNamedItem(new ItemStack(Material.ARROW), "&8Page " + (craftMenus.size()+2)));
    		}else if (craftMenus.size() >= 1 && craftsAmount > 27){
        		i.setItem(36, ItemUtils.getNamedItem(new ItemStack(Material.ARROW), "&8Page " + (craftMenus.size())));
        		i.setItem(44, ItemUtils.getNamedItem(new ItemStack(Material.ARROW), "&8Page " + (craftMenus.size()+2)));
    		}else if(craftMenus.size() >= 1 && craftsAmount < 27){
        		i.setItem(36, ItemUtils.getNamedItem(new ItemStack(Material.ARROW), "&8Page " + (craftMenus.size())));
    		}
    		craftMenus.add(i);
    		craftsAmount -= 21;
    	}
    	loadedCrafts.clear();
	}

	private void loadRecipes() {
		customCraftInventories = new HashMap<CustomCraft, Inventory>();
		for(CustomCraft cc : getCustomCrafts()) {
			Inventory i = Bukkit.createInventory(null, 45, "Crafting Recipe");
			ShapedRecipe sr = (ShapedRecipe) cc.getRecipe(cc.getItem());
			int index = 10;
			for(int a = 0; a < 3; a++)
				for(int b = 0; b < 3; b++) {
					i.setItem(index, sr.getIngredientMap().get(sr.getShape()[a].charAt(b)));
					index++;
					if((index-1)%3 == 0)
						index+=6;
				}
			i.setItem(24, sr.getResult());
			i.setItem(40, ItemUtils.getNamedItem(new ItemStack(Material.ARROW), "&7Go back"));
			customCraftInventories.put(cc, i);
		}
	}
	
    public Inventory getCraftsMenu(int page) {
    	if(page > craftMenus.size())
    		page = craftMenus.size();
    	return craftMenus.get(page-1);
	}

	public CustomCraft getCustomCraft(String itemName) {
		for(CustomCraft cc : customCrafts)
			if(cc.getItem().getItemMeta().getDisplayName() == itemName)
				return cc;
		return null;
	}

	public ArrayList<CustomCraft> getCustomCrafts() {
		return customCrafts;
	}
	
	public Inventory getCraftMenu(CustomCraft customCraft) {
		return customCraftInventories.get(customCraft);
	}

}
