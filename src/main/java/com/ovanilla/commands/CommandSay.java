package com.ovanilla.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandSay implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /* Get the current player or the target player */
        boolean hasTarget = args.length == 2 && sender.isOp();
        Player player = hasTarget ? Bukkit.getPlayer(args[0]) : null;
        String message = String.join(" ", args);
        message = ChatColor.translateAlternateColorCodes('&', message);

        if(player == null) {
            /* Send to all players */
            sender.getServer().broadcastMessage(message);
            return true;
        }

        player.sendTitle(message, ChatMessageUtil.format("Message from %s", sender.getName()), 10, 70, 20);
        return true;
    }
}