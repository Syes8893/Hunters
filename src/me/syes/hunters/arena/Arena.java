package me.syes.hunters.arena;

import org.bukkit.Location;

public class Arena {

	private String name;
	private Location spawn;
	
	private boolean inUse;
	
	public Arena(String name, Location spawn, ArenaManager arenaManager) {
		this.name = name;
		this.spawn = spawn;
		this.inUse = false;
		arenaManager.addArena(this);
	}

	public String getName() {
		return name;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public boolean isInUse() {
		return inUse;
	}

	public void setInUse(boolean inUse) {
		this.inUse = inUse;
	}

}
