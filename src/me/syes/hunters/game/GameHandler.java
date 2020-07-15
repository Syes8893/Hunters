package me.syes.hunters.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.syes.hunters.Core;
import me.syes.hunters.customcrafts.CustomCraft;
import me.syes.hunters.utils.StringUtils;

public class GameHandler implements Listener {
	
	private HashSet<Player> onMjoelnirCooldown;
	private HashSet<Player> onExcaliburCooldown;
	private HashSet<Player> onVenomFangCooldown;
	private HashSet<Player> onWarpOrbCooldown;
	private HashSet<Player> onFallenLegplatesCooldown;
	
	public GameHandler() {
		onMjoelnirCooldown = new HashSet<Player>();
		onExcaliburCooldown = new HashSet<Player>();
		onVenomFangCooldown = new HashSet<Player>();
		onWarpOrbCooldown = new HashSet<Player>();
		onFallenLegplatesCooldown =  new HashSet<Player>();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.teleport(Core.getInstance().arenaManager.getSpawn());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(Core.getInstance().gameManager.isInGame(p))
			Core.getInstance().gameManager.getGame(p).removePlayer(p, true);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(Core.getInstance().gameManager.isInGame(p))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(Core.getInstance().gameManager.isInGame(p)) {
			e.setCancelled(true);
			//if(e.getBlock().getType() == Material.WORKBENCH)
				//p.openInventory(Bukkit.createInventory(null, InventoryType.WORKBENCH));
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player)e.getEntity();
		if(!Core.getInstance().gameManager.isInGame(p)) {
			e.setCancelled(true);
			return;
		}
		Game g = Core.getInstance().gameManager.getGame(p);
		if(!g.isPvpEnabled()) {
			e.setCancelled(true);
			return;
		}
		if(g.isSpectator(p))
			return;
		if(p.getInventory().getBoots() != null)
			if(p.getInventory().getBoots().getItemMeta().hasEnchant(Enchantment.PROTECTION_FALL))
				if(p.getInventory().getBoots().getItemMeta().getEnchantLevel(Enchantment.PROTECTION_FALL) == 10)
					if(e.getCause() == DamageCause.FALL) {
						e.setCancelled(true);
						return;
					}
		if(p.getInventory().getChestplate() != null)
			if(p.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE) {
				e.setDamage(0);
				return;
			}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onEntityDeath(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player)e.getEntity();
		if(!Core.getInstance().gameManager.isInGame(p)) {
			e.setCancelled(true);
			return;
		}
		if(e.getFinalDamage() >= p.getHealth()) {
			e.setCancelled(true);
			Core.getInstance().gameManager.getGame(p).killPlayer(p, false);
			return;
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if(!Core.getInstance().gameManager.isInGame(p))
			return;
		if(Core.getInstance().gameManager.getGame(p).isSpectator(p))
			return;
		Game g = Core.getInstance().gameManager.getGame(p);
		if(e.getItem().getItemStack().getType() == Material.BLAZE_POWDER) {
			e.setCancelled(true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30 * 20, 0));
			g.msgAll("§9[Hunters] §e" + p.getName() + " §fhas picked up a §cStrength Orb§f!");
		}else if(e.getItem().getItemStack().getType() == Material.SUGAR) {
			e.setCancelled(true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 30 * 20, 1));
			g.msgAll("§9[Hunters] §e" + p.getName() + " §fhas picked up a §bSpeed Orb§f!");
		}else if(e.getItem().getItemStack().getType() == Material.INK_SACK && e.getItem().getItemStack().getDurability() == (short) 8) {
			e.setCancelled(true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30 * 20, 1));
			g.msgAll("§9[Hunters] §e" + p.getName() + " §fhas picked up a §7Resistance Orb§f!");
		}/*else if(e.getItem().getItemStack().getType() == Material.EYE_OF_ENDER) {
			e.setCancelled(true);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30 * 20, 0));
			g.msgAll("§9[Hunters] §e" + p.getName() + " §fhas picked up a §dMystic Orb§f!");
		}*/
		if(e.isCancelled()) {
			p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 2, 1);
			e.getItem().remove();
		}
	}
    
    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent e) {
        if (e.getItem() != null && e.getItem().getType().equals((Object)Material.GOLDEN_APPLE)) {
            if (e.getItem().getItemMeta() != null && e.getItem().getItemMeta().hasDisplayName()
            		&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Golden Head")) {
                e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50 * 4, 1));
            }
        }
    }
    
    @EventHandler
    public void onPlayerEatHead(PlayerItemConsumeEvent e) {
        if (e.getItem() != null && e.getItem().getType().equals((Object)Material.GOLDEN_APPLE)) {
            if (e.getItem().getItemMeta() != null && e.getItem().getItemMeta().hasDisplayName()
            		&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Golden Head")) {
                e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 50 * 4, 1));
            }
        }
    }
	
	@EventHandler
	public void onPlayerUseRecipeBook(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() != Material.ENCHANTED_BOOK)
			return;
		p.openInventory(Core.getInstance().craftsManager.getCraftsMenu(1));
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerUseHealingOrb(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() != Material.INK_SACK)
			return;
		if(e.getItem().getDurability() != (short) 10)
			return;
		if(p.getHealth() == p.getMaxHealth()) {
			p.sendMessage("§cYou are already at full health!");
			return;
		}
		e.setCancelled(true);
		p.playSound(p.getLocation(), Sound.EAT, 2, 1);
		if(p.getHealth() <= p.getMaxHealth() - 4)
			p.setHealth(p.getHealth() + 4);
		else
			p.setHealth(p.getMaxHealth());
		p.sendMessage("§9[Hunters] §fYou consumed a §aHealing Orb §fand gained §c2\u2764§f!");
		ItemStack item = e.getItem();
    	if(item.getAmount() > 1) item.setAmount(item.getAmount()-1);
    	else p.setItemInHand(null);
	}
	
	@EventHandler
	public void onPlayerUseTitanOrb(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!Core.getInstance().gameManager.isInGame(p))
			return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() != Material.INK_SACK)
			return;
		if(e.getItem().getDurability() != (short) 5)
			return;
		e.setCancelled(true);
		p.sendMessage("§9[Hunters] §fYou are now invincible for §e5 §fseconds!");
		if(p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
			p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5 * 20, 4));
		ItemStack item = e.getItem();
    	if(item.getAmount() > 1) item.setAmount(item.getAmount()-1);
    	else p.setItemInHand(null);
	}
	
	@EventHandler
	public void onPlayerUseScrollOfWhisdom(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() != Material.PAPER)
			return;
		e.setCancelled(true);
		p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 2, 1);
		List<PotionEffect> effects = Arrays.asList(new PotionEffect(PotionEffectType.SPEED, 10 * 20, 2)
				, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20, 0)
				, new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 1)
				, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 20, 1)
				, new PotionEffect(PotionEffectType.ABSORPTION, 10 * 20, 1));
		PotionEffect pe = effects.get(new Random().nextInt(effects.size()));
		p.addPotionEffect(pe);
		p.sendMessage("§9[Hunters] §fYou used a §eScroll of Whisdom §fand gained §e"
		+ StringUtils.getFixedCaseName(pe.getType().getName()) + " " + (pe.getAmplifier()+1) + " §ffor §e" + pe.getDuration()/20 + " §fseconds!");
		ItemStack item = e.getItem();
    	if(item.getAmount() > 1) item.setAmount(item.getAmount()-1);
    	else p.setItemInHand(null);
	}
	
	@EventHandler
	public void onPlayerUseWarpOrb(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!Core.getInstance().gameManager.isInGame(p))
			return;
		if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if(onWarpOrbCooldown.contains(p)) {
			e.setCancelled(true);
			p.sendMessage("§9[Hunters] §cThis item is still on cooldown!");
			return;
		}
		if(e.getItem() == null)
			return;
		if(e.getItem().getType() != Material.EYE_OF_ENDER)
			return;
		e.setCancelled(true);
		p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
		p.teleport(Core.getInstance().gameManager.getGame(p).getArena().getSpawn());
		p.setFallDistance(0);
		p.sendMessage("§9[Hunters] §fYou used a §3Warp Orb §fand were teleported back to spawn, you also gained §c1.5\u2764§f!");
		if(p.getHealth() <= p.getMaxHealth() - 3)
			p.setHealth(p.getHealth() + 3);
		else
			p.setHealth(p.getMaxHealth());
		setCooldown(onWarpOrbCooldown, p, 2);
		ItemStack item = e.getItem();
    	if(item.getAmount() > 1) item.setAmount(item.getAmount()-1);
    	else p.setItemInHand(null);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerHit(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(e.isCancelled())
			return;
		Player p = (Player) e.getEntity();
		if(!Core.getInstance().gameManager.isInGame(p))
			return;
		Game g = Core.getInstance().gameManager.getGame(p);
		if(g.isSpectator(p))
			return;
		if(!g.isPvpEnabled())
			return;
		if(!(e.getDamager() instanceof Player) && !(e.getDamager() instanceof Projectile))
			return;
		if(p.getInventory().getLeggings() != null)
			if(p.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS) {
				if(!p.getInventory().getLeggings().getItemMeta().hasDisplayName())
					return;
				if(onFallenLegplatesCooldown.contains(p))
					return;
				if(p.getHealth() <= p.getMaxHealth() - 3)
					p.setHealth(p.getHealth() + 3);
				else
					p.setHealth(p.getMaxHealth());
				setCooldown(onFallenLegplatesCooldown, p, 3);
			}
		if(e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			for(PotionEffect pe : d.getActivePotionEffects())
				if(pe.getType().equals(PotionEffectType.INCREASE_DAMAGE))
					e.setDamage(e.getDamage() * 0.6);
			if(d.getItemInHand() == null)
				return;
			if(d.getItemInHand().getType() == Material.IRON_AXE){
				if(!d.getItemInHand().getItemMeta().hasDisplayName())
					return;
				if(onMjoelnirCooldown.contains(p))
					return;
				p.getLocation().getWorld().strikeLightningEffect(p.getLocation());
				p.damage(3);
				setCooldown(onMjoelnirCooldown, p, 4);
			}else if(d.getItemInHand().getType() == Material.GOLD_SWORD){
				if(!d.getItemInHand().getItemMeta().hasDisplayName())
					return;
				if(onExcaliburCooldown.contains(p))
					return;
				if(d.getHealth() <= d.getMaxHealth() - 1)
					d.setHealth(d.getHealth() + 1);
				else
					d.setHealth(d.getMaxHealth());
				setCooldown(onExcaliburCooldown, p, 2);
			}else if(d.getItemInHand().getType() == Material.WOOD_SWORD){
				if(!d.getItemInHand().getItemMeta().hasDisplayName())
					return;
				if(onVenomFangCooldown.contains(p))
					return;
				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 3 * 20, 1));
				setCooldown(onVenomFangCooldown, p, 9);
			}else if(d.getItemInHand().getType() == Material.DIAMOND_SWORD){
				if(!d.getItemInHand().getItemMeta().hasDisplayName())
					return;
				if(p.getHealth() > e.getFinalDamage())
					return;
				handleVoidSword(d, d.getItemInHand());
			}else if(d.getItemInHand().getType() == Material.IRON_SPADE){
				if(!d.getItemInHand().getItemMeta().hasDisplayName())
					return;
				ItemStack[] armour = p.getInventory().getArmorContents();
				int z = new Random().nextInt(armour.length);
				int checks = 0;
				while(armour[z].getType() == Material.AIR) {
					if(checks > 10) {
						return;
					}
					z = new Random().nextInt(armour.length);
					checks++;
				}
				p.sendMessage("§9[Hunters] §e" + d.getName() + " §fused §4Achilles' Spear §fon you and broke your "
						+ StringUtils.getFixedCaseName(armour[z].getType().toString()) + "§f!");
				armour[z].setType(Material.AIR);
				p.getInventory().setArmorContents(armour);
			}
			if(d.getInventory().getHelmet() == null)
				return;
			if(d.getInventory().getHelmet().getType() == Material.GOLD_HELMET) {
				if(!d.getInventory().getHelmet().getItemMeta().hasDisplayName())
					return;
				if(p.getHealth() > e.getFinalDamage())
					return;
				d.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * 20, 1));
			}
		}else if(e.getDamager() instanceof Arrow) {
			Arrow pj = (Arrow) e.getDamager();
			if(!(pj.getShooter() instanceof Player))
				return;
			Player d = (Player) pj.getShooter();
			for(MetadataValue enchantLevel : pj.getMetadata("huntsman"))
				if(enchantLevel.asString() == "I")
					d.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4 * 20, 1));
			for(MetadataValue enchantLevel : pj.getMetadata("aiming"))
				if(enchantLevel.asString() == "I")
					e.setDamage(e.getDamage() * 0.75);
		}
	}
	
	@EventHandler
	public void onArtemisBowShootEvent(EntityShootBowEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(!e.getBow().getItemMeta().hasDisplayName())
			return;
		if(!e.getBow().getItemMeta().getDisplayName().equals("§3Artemis Bow"))
			return;
		Arrow a = (Arrow) e.getProjectile();
		//((Player)e.getEntity()).sendMessage("§8Velocity Length: " + a.getVelocity().length());
		a.setMetadata("aiming", new FixedMetadataValue(Core.getInstance(), "I"));
		new BukkitRunnable() {
			public void run() {
				if(a.isOnGround() || a.isDead() || !a.isValid())
					this.cancel();
				if(a.getNearbyEntities(5, 5, 5).size() > 0)
					for(Entity et : a.getNearbyEntities(5, 5, 5))
						if(et instanceof Player) {
							Player t = (Player) et;
							if(Core.getInstance().gameManager.isInGame(t))
								if(!Core.getInstance().gameManager.getGame(t).alivePlayers.contains(t))
									return;
							if(et != e.getEntity()) {
								Vector v = new Vector();
								v.setX(et.getLocation().getX() - a.getLocation().getX());
								v.setY((et.getLocation().getY() + 1.5) - a.getLocation().getY());
								v.setZ(et.getLocation().getZ() - a.getLocation().getZ());
								v.multiply(0.25);
								if(v.length() < 1)
									v.multiply((3 * e.getForce()) * 1/v.length());
								a.setVelocity(v);
							}
						}
			}
		}.runTaskTimerAsynchronously(Core.getInstance(), 1, 1);
	}
	
	@EventHandler
	public void onTacticalVisorUse(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(!Core.getInstance().gameManager.isInGame(p))
			return;
		if(p.getItemInHand().getType() != Material.REDSTONE_COMPARATOR)
			return;
		if(!p.getItemInHand().getItemMeta().hasDisplayName())
			return;
		if(!Core.getInstance().gameManager.getGame(p).isPvpEnabled()) {
			p.sendMessage("§cYou can't use this item yet.");
			return;
		}
		new BukkitRunnable() {
			int timer = 6;
			public void run() {
				timer--;
				if(timer > 0)
					for(Player pl : Core.getInstance().gameManager.getGame(p).alivePlayers) {
						pl.sendMessage("§9[Hunters] §cAirstrike §farriving in §e" + timer + " §fseconds!");
					}
				if(timer == 0) {
					for(Player pl : Core.getInstance().gameManager.getGame(p).alivePlayers) {
						int damage = 0;
						if(pl.getHealth() > 8)
							damage = 8;
						else 
							damage = (int) pl.getHealth() - 1;
						pl.damage(damage);
						pl.getWorld().playEffect(pl.getLocation(), Effect.EXPLOSION_HUGE, 1);
						pl.sendMessage("§9[Hunters] §e" + p.getName() + " §frequested an §cAirstrike §fand damaged you for §c" + damage/2 + "\u2764§f!");
					}
					this.cancel();
					return;
				}
			}
		}.runTaskTimer(Core.getInstance(), 0, 20);
		
		ItemStack item = e.getItem();
    	if(item.getAmount() > 1) item.setAmount(item.getAmount()-1);
    	else p.setItemInHand(null);
	}
	
	@EventHandler
	public void onMysticCodexUse(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		//if(!Core.getInstance().gameManager.isInGame(p))
			//return;
		if(p.getItemInHand().getType() != Material.BOOK)
			return;
		if(!p.getItemInHand().getItemMeta().hasDisplayName())
			return;

		ItemStack item = e.getItem();
    	if(item.getAmount() > 1) item.setAmount(item.getAmount()-1);
    	else p.setItemInHand(null);
    	
		ItemStack is = Core.getInstance().craftsManager.getCustomCrafts().get(new Random().nextInt(Core.getInstance().craftsManager.getCustomCrafts().size())).getItem();
		if(p.getInventory().firstEmpty() == -1)
			p.getWorld().dropItemNaturally(p.getLocation(), is);
		else
			p.getInventory().addItem(is);
		p.sendMessage("§9[Hunters] §fYou received 1x " + is.getItemMeta().getDisplayName() + " §ffrom your §aMystic Codex§f!");
	}
	
	@EventHandler
	public void onBowShootEvent(EntityShootBowEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(!e.getBow().getItemMeta().hasDisplayName())
			return;
		if(!e.getBow().getItemMeta().getDisplayName().equals("§bHunters Bow"))
			return;
		Arrow a = (Arrow) e.getProjectile();
		a.setMetadata("huntsman", new FixedMetadataValue(Core.getInstance(), "I"));
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		if(!Core.getInstance().gameManager.isInGame(e.getPlayer()))
			return;
		if(!Core.getInstance().gameManager.getGame(e.getPlayer()).isJoinable())
			return;
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		if(((Player)e.getEntity()).getFoodLevel() < e.getFoodLevel())
			return;
		if(new Random().nextInt(50) != 0)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		//if(e.getRawSlot() >= e.getClickedInventory().getSize() || e.getRawSlot() <= -1)
			//return;
		//if(e.getClickedInventory().getItem(e.getRawSlot()) == null)
			//return;
		if(e.getInventory().getName() == "Custom Recipes") {
			e.setCancelled(true);
			if(e.getRawSlot() >= e.getInventory().getSize() || e.getRawSlot() <= -1)
				return;
			if(e.getInventory().getItem(e.getRawSlot()) == null)
				return;
			if(e.getSlot() == 36 && e.getInventory().getItem(e.getSlot()) != null) {
				p.openInventory(Core.getInstance().craftsManager.getCraftsMenu(
						Integer.parseInt(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().replace("§8Page ", ""))));
				return;
			}else if(e.getSlot() == 44 && e.getInventory().getItem(e.getSlot()) != null) {
				p.openInventory(Core.getInstance().craftsManager.getCraftsMenu(
						Integer.parseInt(e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().replace("§8Page ", ""))));
				return;
			}
			CustomCraft cc = Core.getInstance().craftsManager.getCustomCraft(e.getInventory().getItem(e.getRawSlot()).getItemMeta().getDisplayName());
			p.openInventory(Core.getInstance().craftsManager.getCraftMenu(cc));
		}
		if(e.getInventory().getTitle() == "Crafting Recipe") {
			e.setCancelled(true);
			if(e.getRawSlot() >= e.getInventory().getSize() || e.getRawSlot() <= -1)
				return;
			if(e.getInventory().getItem(e.getRawSlot()) == null)
				return;
			if(e.getInventory().getItem(e.getRawSlot()).getType() == Material.ARROW)
				p.openInventory(Core.getInstance().craftsManager.getCraftsMenu(1));
		}
	}
	
	private void handleVoidSword(Player p, ItemStack i) {
		ItemMeta im = i.getItemMeta();
		List<String> lore = im.getLore();
		int kills = Integer.parseInt(lore.get(2).replace("§8Kills: ", ""));
		lore.set(2, "§8Kills: " + (kills+1));
		im.removeEnchant(Enchantment.DAMAGE_ALL);
		if(kills == 0) {
			lore.add("§7");
			lore.add("§9Sharpness I");
			lore.add("§7Melee hits deal +1.25 damage.");
			im.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			p.sendMessage("§9[Hunters] §fYour §5Void Sword §fhas been upgraded to sharpness 1!");
		}else if(kills == 1) {
			lore.set(3, "§7");
			lore.set(4, "§9Sharpness II");
			lore.set(5, "§7Melee hits deal +2.5 damage.");
			im.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
			p.sendMessage("§9[Hunters] §fYour §5Void Sword §fhas been upgraded to sharpness 2!");
		}else if(kills == 2) {
			lore.set(3, "§7");
			lore.set(4, "§9Sharpness III");
			lore.set(5, "§7Melee hits deal +3.75 damage.");
			im.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
			p.sendMessage("§9[Hunters] §fYour §5Void Sword §fhas been upgraded to sharpness 3!");
		}
		im.setLore(lore);
		i.setItemMeta(im);
	}
	
	private void setCooldown(HashSet<Player> hashSet, Player p, int cooldown) {
		hashSet.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				hashSet.remove(p);
			}
		}.runTaskLater(Core.getInstance(), cooldown * 20);
	}
	
}
