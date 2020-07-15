package me.syes.hunters.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.syes.hunters.commands.subcommands.ArenaCreate;
import me.syes.hunters.commands.subcommands.ArenaList;
import me.syes.hunters.commands.subcommands.ArenaSetLobby;
import me.syes.hunters.commands.subcommands.ArenaTeleport;
import me.syes.hunters.commands.subcommands.SubCommand;

public class ArenaCommandHandler implements CommandExecutor {
	
	private HashMap<String, SubCommand> commands;
	
	public ArenaCommandHandler() {
		this.commands = new HashMap<String, SubCommand>();
		registerCommands();
	}
	
	public void registerCommands() {
		this.commands.put("create", new ArenaCreate());
		this.commands.put("list", new ArenaList());
		this.commands.put("setlobby", new ArenaSetLobby());
		this.commands.put("teleport", new ArenaTeleport());
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
		sender.sendMessage("§cUnknown command, use /arena help for a list of arena commands.");
		return true;
	}

	private void sendHelpMenu(Player p) {
		p.sendMessage("§7§m------------------------------");
		p.sendMessage("         §9§lArena Commands");
		for(SubCommand cmd : commands.values())
			if(p.hasPermission(cmd.permission()))
				p.sendMessage("§7- §f" + cmd.help().replace("<", "§6<").replace(">", ">§f").replace("[", "§a[").replace("]", "]§f"));
		p.sendMessage("§7§m------------------------------");
	}

}
