package me.syes.hunters.commands.subcommands;

import org.bukkit.entity.Player;

import me.syes.hunters.Core;

public class GameRecipes extends SubCommand {

	@Override
	public void execute(Player p, String[] args) {
		p.openInventory(Core.getInstance().craftsManager.getCraftsMenu(1));
	}

	@Override
	public String help() {
		return "/game recipes";
	}

	@Override
	public String permission() {
		return "game.recipes";
	}

}
