package com.ignluc4s.spiritautomessage.listeners;

import com.ignluc4s.spiritautomessage.SpiritAutoMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final SpiritAutoMessage plugin;

    public PlayerQuitListener(SpiritAutoMessage plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Get the current server name (this will be their "previous" server when they join another)
        String currentServer = plugin.getServer().getServerName();
        
        // Store it for when they join another server
        plugin.getPlayerDataManager().setPreviousServer(player.getUniqueId(), currentServer);
    }
}
