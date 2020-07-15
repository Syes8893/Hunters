package me.syes.hunters.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.syes.hunters.Core;
import me.syes.hunters.game.Game;

public class GameSpectate extends SubCommand{

	@Override
	public void execute(Player p, String[] args) {
		if(Core.getInstance().gameManager.isInGame(p)) {
			p.sendMessage("§cYou are already in a game!");
			return;
		}
		Game g = null;
		if(args.length == 1)
			g = Core.getInstance().gameManager.getAvailableGame();
		if(args.length >= 2)
			if(Bukkit.getPlayer(args[1]) != null)
				if(Core.getInstance().gameManager.getGame(Bukkit.getPlayer(args[1])) != null)
					g = Core.getInstance().gameManager.getGame(Bukkit.getPlayer(args[1]));
		if(g == null) {
			p.sendMessage("§cCouldn't find an available game.");
			return;
		}
		g.setSpectator(p, true);
		p.sendMessage("§7Now spectating on arena \"" + g.getArena().getName() + "\".");
	}

	@Override
	public String help() {
		return "/game spectate [player]";
	}

	@Override
	public String permission() {
		return "game.spectate";
	}

}
