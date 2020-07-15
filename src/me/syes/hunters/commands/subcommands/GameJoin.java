package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;
import me.syes.hunters.game.Game;

public class GameJoin extends SubCommand{

	@Override
	public void execute(Player p, String[] args) {
		if(Core.getInstance().gameManager.isInGame(p)) {
			p.sendMessage("§cYou are already in a game!");
			return;
		}
		/*Game g = Core.getInstance().gameManager.getAvailableGame();
		if(g == null) {
			p.sendMessage("§cCouldn't find an available game.");
			return;
		}
		g.addPlayer(p);*/
		Game g = null;
		if(args.length == 1)
			g = Core.getInstance().gameManager.getAvailableGame();
		if(args.length >= 2) {
			if(Core.getInstance().arenaManager.getArena(args[1]) != null)
				//if(Core.getInstance().gameManager.getArenaGame(Core.getInstance().arenaManager.getArena(args[1])) != null)
					g = Core.getInstance().gameManager.getArenaGame(Core.getInstance().arenaManager.getArena(args[1]));
			if(g == null) {
				p.sendMessage("§cCouldn't find an arena by the name of \"" + args[1] + "\".");
				return;
			}
		}
		g.addPlayer(p);
	}

	@Override
	public String help() {
		return "/game join [arena]";
	}

	@Override
	public String permission() {
		return "game.join";
	}

}
