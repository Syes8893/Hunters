package me.syes.hunters.game;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import me.syes.hunters.Core;
import me.syes.hunters.arena.Arena;
import me.syes.hunters.utils.ItemUtils;

public class Game {

	public enum GameMode {
		LOADING, WAITING,
		STARTING, INGAME, RESETTING
	}
	
	public ArrayList<Player> alivePlayers;
	public ArrayList<Player> deadPlayers;
	public ArrayList<Player> spectators;
	public ArrayList<Team> spectatorTeams;
	
	private ArrayList<Item> spawnedOrbs;
	
	private GameMode gameMode;
	
	private Arena a;
	
	private int countdownTime;
	private int gameTime;
	
	private GameManager gameManager;
	
	public Game(Arena a) {
		gameMode = GameMode.LOADING;
		this.a = a;
		a.setInUse(true);
		this.gameManager = Core.getInstance().gameManager;
		gameManager.runningGames.add(this);
		alivePlayers = new ArrayList<Player>();
		deadPlayers = new ArrayList<Player>();
		spectators = new ArrayList<Player>();
		spectatorTeams = new ArrayList<Team>();
		spawnedOrbs = new ArrayList<Item>();
		gameMode = GameMode.WAITING;
	}
	
	public void addPlayer(Player p) {
		//Fallback check, just in case
		if(gameManager.isInGame(p))
			return;
		
		if(gameMode != GameMode.WAITING && gameMode != GameMode.STARTING)
			return;
		alivePlayers.add(p);
		gameManager.setGame(p, this);
		msgAll("§9[Hunters] §e" + p.getName() + " §fjoined the game. §7(" + alivePlayers.size() + "/4)");
		p.teleport(a.getSpawn());
		resetPlayer(p);
		p.getInventory().addItem(ItemUtils.getNamedItem(new ItemStack(Material.BOW), "&aKit Selector &7(Right-Click)"));
		p.getInventory().addItem(ItemUtils.getNamedItem(new ItemStack(Material.ENCHANTED_BOOK), "&eRecipe Book &7(Right-Click)"));
		for(Player pl : spectators) {
			p.hidePlayer(pl);
			pl.getScoreboard().getTeam(a.getName()).addPlayer(p);
		}
		if(alivePlayers.size() == 2)
			startCountdown();
	}
	
	public void removePlayer(Player p, boolean leftGame) {
		for(Player pl : spectators) {
			p.showPlayer(pl);
			pl.getScoreboard().getTeam(a.getName()).removePlayer(p);
		}
		if((gameMode == GameMode.WAITING || gameMode == GameMode.STARTING) && alivePlayers.contains(p)) {
			resetPlayer(p);
			alivePlayers.remove(p);
			gameManager.removeGame(p);
			msgAll("§9[Hunters] §e" + p.getName() + " §fleft the game. §7(" + alivePlayers.size() + "/4)");
			p.sendMessage("§9[Hunters] §fYou left the game.");
		}
		if(spectators.contains(p)) {
			setSpectator(p, false);
			return;
		}
		if(alivePlayers.contains(p) && gameMode == GameMode.INGAME)
			killPlayer(p, leftGame);
	}
	
	public void killPlayer(Player p, boolean leftGame) {
		if(isJoinable())
			return;
		if(!alivePlayers.contains(p))
			return;
		alivePlayers.remove(p);
		deadPlayers.add(p);
		
		//Drop items because of custom death handling
		for(ItemStack i : p.getInventory()) {
			if(i!=null) 
				if(i.getType() != Material.AIR)
					p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.2, 0), i);
		}
		for(ItemStack i : p.getInventory().getArmorContents()) {
			if(i!=null)
				if(i.getType() != Material.AIR)
					p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.2, 0), i);
		}
		p.getWorld().dropItemNaturally(p.getLocation().add(0, 0.2, 0), new ItemStack(Material.SKULL_ITEM, 1,(short) 3));
		
		resetPlayer(p);
		if(!leftGame)
			setSpectator(p, true);
		else {
			gameManager.removeGame(p);
			p.sendMessage("§9[Hunters] §fYou left the game.");
		}
		msgAll("§9[Hunters] §e" + p.getName() + " §fwas eliminated.");
		if(alivePlayers.size() >= 2)
			msgAll("§9[Hunters] §fThere are §e" + alivePlayers.size() + " §fplayers remaining!");
	}
	
	public void startCountdown() {
		gameMode = GameMode.STARTING;
		countdownTime = 31;
		new BukkitRunnable() {
			public void run() {
				countdownTime--;
				if(gameMode == GameMode.INGAME) {
					this.cancel();
					return;
				}
				if(alivePlayers.size() < 2) {
					this.cancel();
					gameMode = GameMode.WAITING;
					return;
				}
				if(countdownTime == 0) {
					startGame();
					this.cancel();
					return;
				}
				if(countdownTime % 10 == 0 || countdownTime <= 5) {
					if(countdownTime <= 5)
						msgAll("§9[Hunters] §fGame starting in §c" + countdownTime + " §fseconds!");
					else if(countdownTime <= 15)
						msgAll("§9[Hunters] §fGame starting in §6" + countdownTime + " §fseconds!");
					else if(countdownTime <= 25)
						msgAll("§9[Hunters] §fGame starting in §e" + countdownTime + " §fseconds!");
					else if(countdownTime <= 30)
						msgAll("§9[Hunters] §fGame starting in §a" + countdownTime + " §fseconds!");
				}
			}
		}.runTaskTimer(Core.getInstance(), 0, 20);
	}
	
	public void startGame() {
		gameMode = GameMode.INGAME;
		for(Player p : alivePlayers) {
			p.closeInventory();
			p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 2, 1);
			p.teleport(a.getSpawn());
			resetPlayer(p);
			p.sendMessage("§9[Hunters] §fThe game has started!");
			p.sendMessage("§9[Hunters] §fYou have §e45 §fseconds to prepare for PvP!");
			p.getInventory().addItem(new ItemStack(Material.DIAMOND, 5));
			p.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 32));
			p.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, 16));
			p.getInventory().addItem(new ItemStack(Material.STICK, 8));
			p.getInventory().addItem(new ItemStack(Material.STRING, 4));
			p.getInventory().addItem(new ItemStack(Material.FLINT, 4));
			p.getInventory().addItem(new ItemStack(Material.FEATHER, 4));
			p.getInventory().addItem(new ItemStack(Material.APPLE, 2));
			//p.getInventory().addItem(new ItemStack(Material.WORKBENCH));
			p.getInventory().addItem(ItemUtils.getNamedItem(new ItemStack(Material.ENCHANTED_BOOK), "&eRecipe Book &7(Right-Click)"));
		}
		gameTime = 0;
		new BukkitRunnable() {
			public void run() {
				if(alivePlayers.size() == 1) {
					this.cancel();
					endGame(false, alivePlayers.get(0));
					return;
				}else if(alivePlayers.size() == 0) {
					this.cancel();
					endGame(true, null);
					return;
				}
				if((gameTime+1) == 45) {
					msgAll("§9[Hunters] §fThe grace period has ended, PvP is now §aenabled§f!");
				}
				if((gameTime+1) % 60 == 0) {
					spawnOrb();
				}
				if((gameTime+1) == 300) {
					//msgAll("§cSudden Death has begun, all players will now take continuous damage until the Game End.");
					msgAll("§9[Hunters] §fSudden Death has begun, all players health has been capped at §c5\u2764§f.");
					msgAll("§9[Hunters] §fPlayers will now take continuous wither damage until the Game End.");
					msgAll("§9[Hunters] §fGame ending in §c2 minutes!");
					for(Player p : alivePlayers) {
						p.setMaxHealth(10);
						p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 120, 0));
					}
				}
				if(gameTime+1 > 300 && (gameTime +1) % 15 == 0)
					for(Player p : alivePlayers)
						p.getWorld().strikeLightningEffect(p.getLocation());
				if((gameTime+1) == 420) {
					this.cancel();
					endGame(true, null);
					return;
				}
				gameTime++;
			}
		}.runTaskTimer(Core.getInstance(), 20, 20);
	}

	private void spawnOrb() {
		int nr = (int) (Math.random() * 3);
		//int nr = (int) (Math.random() * 4);
		Item i = null;
		if(nr == 0) {
			//a.getSpawn().getWorld().dropItem(a.getSpawn(), new ItemStack(Material.INK_SACK, 1, (short) 1));
			i = a.getSpawn().getWorld().dropItem(a.getSpawn(), new ItemStack(Material.BLAZE_POWDER));
			msgAll("§9[Hunters] §fA §cStrength Orb §fhas spawned at middle, collect it for a boost!");
			i.setCustomName("§c§lStrength Orb");
		}else if(nr == 1) {
			i = a.getSpawn().getWorld().dropItem(a.getSpawn(), new ItemStack(Material.SUGAR));
			msgAll("§9[Hunters] §fA §bSpeed Orb §fhas spawned at middle, collect it for a boost!");
			i.setCustomName("§b§lSpeed Orb");
		}else if(nr == 2) {
			i = a.getSpawn().getWorld().dropItem(a.getSpawn(), new ItemStack(Material.INK_SACK, 1, (short) 8));
			msgAll("§9[Hunters] §fA §7Resistance Orb §fhas spawned at middle, collect it for a boost!");
			i.setCustomName("§7§lResistance Orb");
		}/*else if(nr == 3) {
			i = a.getSpawn().getWorld().dropItem(a.getSpawn(), new ItemStack(Material.EYE_OF_ENDER));
			msgAll("§9[Hunters] §fA §dMystic Orb §fhas spawned at middle, collect it for a boost!");
			i.setCustomName("§d§lMystic Orb");
		}*/
		i.setCustomNameVisible(true);
		spawnedOrbs.add(i);
	}
	
	public void endGame(boolean draw, Player winner) {
		gameMode = GameMode.RESETTING;
		msgAll("§7§m------------------------------");
		msgAll("           §9§lHunters");
		msgAll("§7");
		if(draw) {
			msgAll("The game ended in a draw, there was no victorious player!");
		}else {
			msgAll("Congratulations to §e" + winner.getName() + " §ffor winning the game!");
		}
		msgAll("§7");
		msgAll("§7§m------------------------------");
		new BukkitRunnable() {
			public void run() {
				resetGame();
			}
		}.runTaskLater(Core.getInstance(), 200);
	}
	
	public void resetGame() {
		msgAll("§cResetting game...");
		gameMode = GameMode.RESETTING;
		for(Player p : alivePlayers) {
			Core.getInstance().gameManager.removeGame(p);
			resetPlayer(p);
			p.teleport(Core.getInstance().arenaManager.getSpawn());
		}
		for(Player p : spectators) {
			setSpectator(p, false);
			resetPlayer(p);
			p.teleport(Core.getInstance().arenaManager.getSpawn());
			//Core.getInstance().gameManager.removeGame(p);
		}
		for(Team t : spectatorTeams)
			t.unregister();
		for(Item i : spawnedOrbs)
			i.remove();
		alivePlayers.clear();
		deadPlayers.clear();
		spectators.clear();
		spectatorTeams.clear();
		spawnedOrbs.clear();
		for(Entity e : a.getSpawn().getWorld().getNearbyEntities(a.getSpawn(), 50, 20, 50))
			if(e.getType() == EntityType.ARROW || e.getType() == EntityType.DROPPED_ITEM || e.getType() == EntityType.ARMOR_STAND)
				e.remove();
		gameMode = GameMode.WAITING;
	}
	
	public void setSpectator(Player p, boolean setSpectator) {
		resetPlayer(p);
		if(setSpectator) {
			for(Player pl : alivePlayers)
				pl.hidePlayer(p);
			if(!deadPlayers.contains(p)) {
				p.teleport(a.getSpawn());
				gameManager.setGame(p, this);
			}
			p.setAllowFlight(true);
			
			Team t = p.getScoreboard().getTeam(a.getName());
			if(p.getScoreboard().getTeam(a.getName()) == null) {
				t = p.getScoreboard().registerNewTeam(a.getName());
				t.setAllowFriendlyFire(false);
				t.addPlayer(p);
				addSpectatorTeam(t);
			}
			for(Player pl : alivePlayers)
				t.addPlayer(pl);
			spectators.add(p);
			return;
		}
		for(Player pl : alivePlayers)
			pl.showPlayer(p);
		for(Player pl : deadPlayers)
			pl.showPlayer(p);
		p.setAllowFlight(false);
		
		spectatorTeams.remove(p.getScoreboard().getTeam(a.getName()));
		p.getScoreboard().getTeam(a.getName()).unregister();
		
		if(gameMode != GameMode.RESETTING)
			spectators.remove(p);
		
		if(deadPlayers.contains(p))
			deadPlayers.remove(p);
		gameManager.removeGame(p);
	}
	
	private void resetPlayer(Player p) {
		p.closeInventory();
		p.getInventory().clear();
		p.getInventory().setArmorContents((ItemStack[]) null);
		p.setFoodLevel(20);
		p.setSaturation(10);
		p.setMaxHealth(20);
		p.setHealth(20);
		p.setExp(0);
		p.setLevel(0);
		p.setGameMode(org.bukkit.GameMode.SURVIVAL);
		for(PotionEffect pe : p.getActivePotionEffects())
			p.removePotionEffect(pe.getType());
	}
	
	private void setSpectatorInv(Player p) {
	}

	public void msgAll(String string) {
		for(Player p : alivePlayers)
			p.sendMessage(string);
		for(Player p : spectators)
			p.sendMessage(string);
	}
	
	public boolean isJoinable() {
		if(gameMode == GameMode.WAITING || gameMode == GameMode.STARTING)
			return true;
		return false;
	}
	
	public boolean isPvpEnabled() {
		if(gameMode == GameMode.INGAME && gameTime >= 45)
			return true;
		return false;
	}
	
	public String getNextEvent() {
		if(gameTime < 45)
			return "PvP Enabled";
		if(gameTime < 240)
			return "Orb Spawn";
		if(gameTime < 300)
			return "Sudden Death";
		if(gameTime < 360)
			return "Orb Spawn";
		if(gameTime < 420)
			return "Game End";
		return "Game Ended";
	}
	
	public int getNextEventTime() {
		if(gameTime < 45)
			return 45-gameTime;
		if(gameTime < 240)
			return (240-gameTime)%60;
		if(gameTime < 300)
			return 300-gameTime;
		if(gameTime < 360)
			return 360-gameTime;
		if(gameTime < 420)
			return 420-gameTime;
		return 0;
	}
	
	public int getCountdowntime() {
		return countdownTime;
	}
	
	public int getGametime() {
		return gameTime;
	}
	
	public GameMode getGameMode() {
		return gameMode;
	}
	
	public Arena getArena() {
		return this.a;
	}
	
	public boolean isSpectator(Player p) {
		if(spectators.contains(p))
			return true;
		return false;
	}
	
	public void addSpectatorTeam(Team t) {
		spectatorTeams.add(t);
	}

}
