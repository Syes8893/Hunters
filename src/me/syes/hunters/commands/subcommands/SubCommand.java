package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

public abstract class SubCommand {
	
	public abstract void execute(Player p, String[] args);
	
	public abstract String help();
	
	public abstract String permission();

}
