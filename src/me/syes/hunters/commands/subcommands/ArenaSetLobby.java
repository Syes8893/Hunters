package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;

public class ArenaSetLobby extends SubCommand {

	@Override
	public void execute(Player p, String[] args) {
		Core.getInstance().arenaManager.setSpawn(p.getLocation());
		p.sendMessage("§7Lobby spawn has been succesfully set to your location.");
	}

	@Override
	public String help() {
		return "/arena setlobby";
	}

	@Override
	public String permission() {
		return "arena.setlobby";
	}

}
