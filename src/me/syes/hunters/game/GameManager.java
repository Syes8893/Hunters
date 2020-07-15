package me.syes.hunters.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;
import me.syes.hunters.arena.Arena;

public class GameManager {
	
	public HashMap<Player, Game> playerGame;
	public ArrayList<Game> runningGames;
	
	public GameManager() {
		playerGame = new HashMap<Player, Game>();
		runningGames = new ArrayList<Game>();
	}
	
	public Game getAvailableGame() {
		for(Game g : runningGames)
			if(g.isJoinable())
				return g;
		if(Core.getInstance().arenaManager.getRandomArena() == null)
			return null;
		return new Game(Core.getInstance().arenaManager.getRandomArena());
	}
	
	public boolean isInGame(Player p) {
		if(playerGame.containsKey(p))
			return true;
		return false;
	}
	
	public Game getArenaGame(Arena a) {
		if(a.isInUse())
			for(Game g : runningGames)
				if(g.getArena() == a)
					return g;
		return new Game(a);
	}
	
	public Game getGame(Player p) {
		return playerGame.get(p);
	}
	
	public void setGame(Player p, Game g) {
		playerGame.put(p, g);
	}
	
	public void removeGame(Player p) {
		playerGame.remove(p);
		p.teleport(Core.getInstance().arenaManager.getSpawn());
	}
	
}
