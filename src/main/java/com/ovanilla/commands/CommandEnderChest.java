package com.ovanilla.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.Plugin;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandEnderChest implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!Plugin.isSenderPlayer(sender)) {
            sender.sendMessage(ChatMessageUtil.format("You must be a player to use this command !"));
            return false;
        }

        Player target = args.length == 1 && sender.isOp() ? Bukkit.getPlayer(args[0]) : null;
        if(target == null) {
            sender.sendMessage(ChatMessageUtil.format("No player found !"));
            return false;
        }

        ((Player)sender).openInventory(target.getEnderChest());
        return true;
    }
}
