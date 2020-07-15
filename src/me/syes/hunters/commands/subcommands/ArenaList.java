package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;
import me.syes.hunters.arena.Arena;

public class ArenaList extends SubCommand {

	@Override
	public void execute(Player p, String[] args) {
		p.sendMessage("§7§m------------------------------");
		p.sendMessage("      §9§lAvailable Arenas §f(" + Core.getInstance().arenaManager.arenas.size() + ")");
		for(Arena arena : Core.getInstance().arenaManager.arenas)
			if(arena.isInUse())
				p.sendMessage("§c- §f" + arena.getName() + " §7(in use)");
			else
				p.sendMessage("§7- §f" + arena.getName() + "");
		p.sendMessage("§7§m------------------------------");
	}

	@Override
	public String help() {
		return "/arena list";
	}

	@Override
	public String permission() {
		return "arena.list";
	}

}
