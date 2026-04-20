package com.ignluc4s.spiritautomessage.managers;

import com.ignluc4s.spiritautomessage.SpiritAutoMessage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final SpiritAutoMessage plugin;
    private FileConfiguration config;

    public ConfigManager(SpiritAutoMessage plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public boolean isAutoMessagesEnabled() {
        return config.getBoolean("auto-messages.enabled", true);
    }

    public boolean isJoinMessagesEnabled() {
        return config.getBoolean("join-messages.enabled", true);
    }

    public int getAutoMessageCooldown() {
        return config.getInt("auto-messages.cooldown", 120);
    }

    public boolean isRandomizeEnabled() {
        return config.getBoolean("auto-messages.randomize", false);
    }

    public String getFirstTimeMessage() {
        return config.getString("join-messages.first-time", "first-time");
    }

    public String getDefaultJoinMessage() {
        return config.getString("join-messages.default", "default-join");
    }

    public String getShowWhenJoinedFrom() {
        return config.getString("join-messages.show-when-joined-from", "");
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', config.getString("prefix", "&8[&6Spirit&8] &7"));
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }
}
