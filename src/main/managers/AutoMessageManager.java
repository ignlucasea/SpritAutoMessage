package com.ignluc4s.spiritautomessage.managers;

import com.ignluc4s.spiritautomessage.SpiritAutoMessage;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

public class AutoMessageManager {

    private final SpiritAutoMessage plugin;
    private BukkitTask task;
    private int currentIndex = 0;
    private String lastMessage = null;
    private final Random random;

    public AutoMessageManager(SpiritAutoMessage plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }

    public void startTask() {
        if (!plugin.getConfigManager().isAutoMessagesEnabled()) {
            return;
        }

        List<String> messages = plugin.getConfig().getStringList("auto-messages.messages");
        if (messages.isEmpty()) {
            plugin.getLogger().warning("No auto-messages configured!");
            return;
        }

        int cooldown = plugin.getConfigManager().getAutoMessageCooldown();

        task = plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            if (plugin.getServer().getOnlinePlayers().isEmpty()) {
                return;
            }

            String messageKey = getNextMessage(messages);
            if (messageKey != null) {
                plugin.getJsonMessageManager().broadcastMessage(messageKey);
            }
        }, cooldown * 20L, cooldown * 20L);

        plugin.getLogger().info("Auto-message task started with " + cooldown + " seconds cooldown!");
    }

    private String getNextMessage(List<String> messages) {
        if (messages.isEmpty()) {
            return null;
        }

        if (messages.size() == 1) {
            return messages.get(0);
        }

        if (plugin.getConfigManager().isRandomizeEnabled()) {
            String selectedMessage;
            int attempts = 0;
            int maxAttempts = 50;

            do {
                selectedMessage = messages.get(random.nextInt(messages.size()));
                attempts++;
            } while (selectedMessage.equals(lastMessage) && attempts < maxAttempts);

            lastMessage = selectedMessage;
            return selectedMessage;
        } else {
            String message = messages.get(currentIndex);
            currentIndex = (currentIndex + 1) % messages.size();
            return message;
        }
    }

    public void stopTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
