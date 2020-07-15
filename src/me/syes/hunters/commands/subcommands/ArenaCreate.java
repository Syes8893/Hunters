package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;
import me.syes.hunters.arena.Arena;

public class ArenaCreate extends SubCommand{

	@Override
	public void execute(Player p, String[] args) {
		if(args.length < 2) {
			p.sendMessage("§cUsage: /arena create <name>");
			return;
		}
		Arena a = new Arena(args[1], p.getLocation(), Core.getInstance().arenaManager);
		p.sendMessage("§7Successfully created arena \"" + a.getName() + "\".");
	}

	@Override
	public String help() {
		return "/arena create <name>";
	}

	@Override
	public String permission() {
		return "arena.create";
	}

}
