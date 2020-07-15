package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;
import me.syes.hunters.game.Game;

public class GameLeave extends SubCommand{

	@Override
	public void execute(Player p, String[] args) {
		Game g = Core.getInstance().gameManager.getGame(p);
		if(g == null) {
			p.sendMessage("§cYou are currently not in a game.");
			return;
		}
		g.removePlayer(p, true);
	}

	@Override
	public String help() {
		return "/game leave";
	}

	@Override
	public String permission() {
		return "game.leave";
	}

}
