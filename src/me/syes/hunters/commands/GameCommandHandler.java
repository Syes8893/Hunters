package me.syes.hunters.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.syes.hunters.commands.subcommands.GameEnd;
import me.syes.hunters.commands.subcommands.GameJoin;
import me.syes.hunters.commands.subcommands.GameLeave;
import me.syes.hunters.commands.subcommands.GameRecipes;
import me.syes.hunters.commands.subcommands.GameSpectate;
import me.syes.hunters.commands.subcommands.GameStart;
import me.syes.hunters.commands.subcommands.SubCommand;

public class GameCommandHandler implements CommandExecutor {
	
	private HashMap<String, SubCommand> commands;
	
	public GameCommandHandler() {
		this.commands = new HashMap<String, SubCommand>();
		registerCommands();
	}
	
	public void registerCommands() {
		this.commands.put("join", new GameJoin());
		this.commands.put("leave", new GameLeave());
		this.commands.put("spectate", new GameSpectate());
		this.commands.put("start", new GameStart());
		this.commands.put("end", new GameEnd());
		this.commands.put("recipes", new GameRecipes());
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		if(!(sender instanceof Player))
			return true;
		p = (Player) sender;
		if(args.length == 0 || (args[0] != null && args[0].equalsIgnoreCase("help"))) {
			sendHelpMenu(p);
			return true;
		}
		for(String str : commands.keySet())
			if(str.equalsIgnoreCase(args[0])) {
				if(!p.hasPermission(commands.get(str).permission())) {
					p.sendMessage("§cInsufficient permission to execute this command.");
					return true;
				}
				commands.get(str).execute(p, args);
				return true;
			}
		sender.sendMessage("§cUnknown command, use /game help for a list of arena commands.");
		return true;
	}

	private void sendHelpMenu(Player p) {
		p.sendMessage("§7§m------------------------------");
		p.sendMessage("          §9§lGame Commands");
		for(SubCommand cmd : commands.values())
			if(p.hasPermission(cmd.permission()))
				p.sendMessage("§7- §f" + cmd.help().replace("<", "§6<").replace(">", ">§f").replace("[", "§a[").replace("]", "]§f"));
		p.sendMessage("§7§m------------------------------");
	}

}
