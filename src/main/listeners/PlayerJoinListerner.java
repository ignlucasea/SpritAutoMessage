package com.ignluc4s.spiritautomessage.listeners;

import com.ignluc4s.spiritautomessage.SpiritAutoMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final SpiritAutoMessage plugin;

    public PlayerJoinListener(SpiritAutoMessage plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getConfigManager().isJoinMessagesEnabled()) {
            return;
        }

        // Small delay to let everything load
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            processJoinMessage(player);
        }, 5L);
    }

    private void processJoinMessage(Player player) {
        boolean isFirstTime = plugin.getPlayerDataManager().isFirstTimeJoin(player.getUniqueId());
        String showWhenJoinedFrom = plugin.getConfigManager().getShowWhenJoinedFrom();

        // Check if we need to verify the server they came from
        if (!showWhenJoinedFrom.isEmpty()) {
            String previousServer = plugin.getPlayerDataManager().getPreviousServer(player.getUniqueId());
            
            // Only enforce the check if we have valid previous server data
            if (previousServer != null && !previousServer.equals("Unknown Server")) {
                if (!previousServer.equalsIgnoreCase(showWhenJoinedFrom)) {
                    plugin.getLogger().info("Skipping join message for " + player.getName() + " - came from: " + previousServer + ", expected: " + showWhenJoinedFrom);
                    return;
                }
            }
            // If previousServer is null or "Unknown Server", allow the message (first network join or restart)
        }

        if (isFirstTime) {
            String firstTimeMessage = plugin.getConfigManager().getFirstTimeMessage();
            plugin.getJsonMessageManager().sendMessage(player, firstTimeMessage);
            plugin.getPlayerDataManager().markAsJoined(player.getUniqueId());
        } else {
            String defaultMessage = plugin.getConfigManager().getDefaultJoinMessage();
            plugin.getJsonMessageManager().sendMessage(player, defaultMessage);
        }
    }
}
