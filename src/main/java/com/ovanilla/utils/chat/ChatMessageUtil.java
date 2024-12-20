package com.ovanilla.utils.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatMessageUtil {

    public static String format(String message, Object... args) {
        return String.format(message, args);
    }

    public static String formatWithColor(String message, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', format(message, args));
    }

    public static ChatMessage createMessage(Player target) {
        return new ChatMessage(target);
    }
}
