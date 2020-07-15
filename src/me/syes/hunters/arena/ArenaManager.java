package me.syes.hunters.arena;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.syes.hunters.Core;

public class ArenaManager {
	
	private File arenaFile;
    private FileConfiguration arenaConfig;
	
	public ArrayList<Arena> arenas;
	public Location lobbySpawn;
	
	public ArenaManager() {
		loadArenaFile();
		loadLobbySpawn();
		loadArenas();
	}

	private void loadArenaFile() {
		arenaFile = new File(Core.getInstance().getDataFolder(), "arenas.yml");
		if(!arenaFile.exists())
			createArenaFile();
        arenaConfig = new YamlConfiguration();
        try {
        	arenaConfig.load(arenaFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
	}

	private void createArenaFile() {
    	if (!arenaFile.exists()) {
    		arenaFile.getParentFile().mkdirs();
        	try {
				arenaFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

	private void loadLobbySpawn() {
        Location loc = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
        if(arenaConfig.getConfigurationSection("lobbyspawn") == null) {
        	System.out.println("[WARNING] No lobby spawn has been set, players will be teleported to default plugin spawn on join!");
        	return;
        }
    	loc.setWorld(Bukkit.getWorld(arenaConfig.getString("lobbyspawn" + "." + "world")));
        ConfigurationSection sec = arenaConfig.getConfigurationSection("lobbyspawn" + "." + "spawn");
        loc.setX(sec.getInt("x") + 0.5);
        loc.setY(sec.getInt("y") + 0.3);
        loc.setZ(sec.getInt("z") + 0.5);
        loc.setYaw(sec.getInt("yaw"));
        loc.setPitch(sec.getInt("pitch"));
        if(loc != null)
        	lobbySpawn = loc;
	}

	private void loadArenas() {
		arenas = new ArrayList<Arena>();
        Location loc = new Location(null, 0, 0, 0);
        for(String str : arenaConfig.getKeys(false)) {
        	if(str.equals("lobbyspawn"))
        		continue;
        	loc.setWorld(Bukkit.getWorld(arenaConfig.getString(str + "." + "world")));
	        ConfigurationSection sec = arenaConfig.getConfigurationSection(str + "." + "spawn");
	        loc.setX(sec.getInt("x") + 0.5);
	        loc.setY(sec.getInt("y") + 0.3);
	        loc.setZ(sec.getInt("z") + 0.5);
	        loc.setYaw(sec.getInt("yaw"));
	        loc.setPitch(sec.getInt("pitch"));
	        new Arena(str, loc, this);
        }
	}

	public void addArena(Arena a) {
		arenas.add(a);
	}

	public void removeArena(Arena a) {
		arenas.remove(a);
	}

	public void setSpawn(Location lobbySpawn) {
		this.lobbySpawn = lobbySpawn;
	}

	public Location getSpawn() {
		return this.lobbySpawn;
	}

	public Arena getArena(String name) {
		for(Arena a : arenas)
			if(a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	public Arena getRandomArena() {
		for(Arena a : arenas)
			if(!a.isInUse())
				return a;
		return null;
	}
	
	private void saveArenas() {
		for(Arena a : arenas) {
			arenaConfig.set(a.getName() + "." + "world", a.getSpawn().getWorld().getName());
			arenaConfig.set(a.getName() + "." + "spawn." + "x", a.getSpawn().getBlockX());
	        arenaConfig.set(a.getName() + "." + "spawn." + "y", a.getSpawn().getBlockY());
	        arenaConfig.set(a.getName() + "." + "spawn." + "z", a.getSpawn().getBlockZ());
	        arenaConfig.set(a.getName() + "." + "spawn." + "yaw", a.getSpawn().getYaw());
		}
	}
	
	private void saveSpawn() {
		if(lobbySpawn == null)
			return;
		arenaConfig.set("lobbyspawn" + "." + "world", lobbySpawn.getWorld().getName());
		arenaConfig.set("lobbyspawn" + "." + "spawn." + "x", lobbySpawn.getBlockX());
        arenaConfig.set("lobbyspawn" + "." + "spawn." + "y", lobbySpawn.getBlockY());
        arenaConfig.set("lobbyspawn" + "." + "spawn." + "z", lobbySpawn.getBlockZ());
        arenaConfig.set("lobbyspawn" + "." + "spawn." + "yaw", lobbySpawn.getYaw());
        arenaConfig.set("lobbyspawn" + "." + "spawn." + "pitch", lobbySpawn.getPitch());
	}
	
	public void saveArenaFile() {
		saveArenas();
		saveSpawn();
		try {
			arenaConfig.save(arenaFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
