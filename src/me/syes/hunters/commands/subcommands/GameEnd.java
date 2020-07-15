package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;
import me.syes.hunters.game.Game;

public class GameEnd extends SubCommand{

	@Override
	public void execute(Player p, String[] args) {
		if(!Core.getInstance().gameManager.isInGame(p)) {
			p.sendMessage("§cYou are not in a game!");
			return;
		}
		Game g = Core.getInstance().gameManager.getGame(p);
		g.endGame(true, null);
		p.sendMessage("§7Succesfully ended your current game.");
		return;
	}

	@Override
	public String help() {
		return "/game end";
	}

	@Override
	public String permission() {
		return "game.end";
	}

}
