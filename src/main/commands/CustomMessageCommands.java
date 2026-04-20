package com.ignluc4s.spiritautomessage.commands;

import com.ignluc4s.spiritautomessage.SpiritAutoMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomMessageCommand implements CommandExecutor {

    private final SpiritAutoMessage plugin;
    private final String messageKey;

    public CustomMessageCommand(SpiritAutoMessage plugin, String messageKey) {
        this.plugin = plugin;
        this.messageKey = messageKey;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = plugin.getConfigManager().getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "§cThis command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (!plugin.getJsonMessageManager().hasMessage(messageKey)) {
            player.sendMessage(prefix + "§cMessage not found: " + messageKey);
            return true;
        }

        plugin.getJsonMessageManager().sendMessage(player, messageKey);
        return true;
    }
}
