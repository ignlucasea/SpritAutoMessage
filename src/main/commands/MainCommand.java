package com.ignluc4s.spiritautomessage.commands;

import com.ignluc4s.spiritautomessage.SpiritAutoMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

    private final SpiritAutoMessage plugin;

    public MainCommand(SpiritAutoMessage plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = plugin.getConfigManager().getPrefix();

        if (args.length == 0) {
            sender.sendMessage(prefix + "§7Version: §e" + plugin.getDescription().getVersion());
            sender.sendMessage("§7Use §e/" + label + " reload §7to reload the plugin.");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("spiritautomessage.reload")) {
                sender.sendMessage(prefix + "§cYou don't have permission to use this command!");
                return true;
            }

            plugin.reload();
            sender.sendMessage(prefix + "§aPlugin reloaded successfully!");
            return true;
        }

        sender.sendMessage(prefix + "§cUsage: /" + label + " reload");
        return true;
    }
}
