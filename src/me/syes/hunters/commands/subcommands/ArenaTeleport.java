package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;

public class ArenaTeleport extends SubCommand{

	@Override
	public void execute(Player p, String[] args) {
		if(args.length < 2) {
			p.sendMessage("§cUsage: /arena teleport <name>");
			return;
		}
		if(Core.getInstance().arenaManager.getArena(args[1]) == null) {
			p.sendMessage("§cCouldn't find an arena by the name of \"" + args[1] + "\".");
			return;
		}
		p.teleport(Core.getInstance().arenaManager.getArena(args[1]).getSpawn());
		p.sendMessage("§7Successfully teleported to arena " + Core.getInstance().arenaManager.getArena(args[1]).getName());
	}

	@Override
	public String help() {
		return "/arena teleport <name>";
	}

	@Override
	public String permission() {
		return "arena.teleport";
	}

}
