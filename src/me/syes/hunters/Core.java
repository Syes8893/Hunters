package me.syes.hunters;

import org.bukkit.plugin.java.JavaPlugin;

import me.syes.hunters.arena.ArenaManager;
import me.syes.hunters.commands.ArenaCommandHandler;
import me.syes.hunters.commands.GameCommandHandler;
import me.syes.hunters.customcrafts.CraftsManager;
import me.syes.hunters.game.GameHandler;
import me.syes.hunters.game.GameManager;
import me.syes.hunters.game.SpectatorHandler;
import me.syes.hunters.scoreboard.ScoreboardManager;

public class Core extends JavaPlugin {
	
	public static Core instance;
	
	public ArenaManager arenaManager;
	public GameManager gameManager;
	public CraftsManager craftsManager;
	public ScoreboardManager scoreboardManager;
	
	public void onEnable() {
		//Register Managers
		this.arenaManager = new ArenaManager();
		this.gameManager = new GameManager();
		this.craftsManager = new CraftsManager();
		this.scoreboardManager = new ScoreboardManager();
		
		//Register Commands
		getCommand("arena").setExecutor(new ArenaCommandHandler());
		getCommand("game").setExecutor(new GameCommandHandler());
		
		//Register Listeners
		getServer().getPluginManager().registerEvents(new GameHandler(), this);
		getServer().getPluginManager().registerEvents(scoreboardManager.getScoreboardHandler(), this);
		getServer().getPluginManager().registerEvents(new SpectatorHandler(), this);
		
		//Start Scoreboard
		scoreboardManager.runTaskTimer(this, 2, 2);
	}
	
	public void onDisable() {
		arenaManager.saveArenaFile();
	}
	
	public Core() {
		instance = this;
	}
	
	public static Core getInstance() {
		return instance;
	}

}
