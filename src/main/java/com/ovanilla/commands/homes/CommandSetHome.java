package com.ovanilla.commands.homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.Home;
import com.ovanilla.PlayerData;
import com.ovanilla.Plugin;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandSetHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /* If it's the server */
        if(!Plugin.isSenderPlayer(sender)) {
            sender.sendMessage(ChatMessageUtil.format("%sYou must be a player to use this command !", ChatColor.RED));
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(ChatMessageUtil.format("%sYou must specify a home name !", ChatColor.RED));
            return true;
        }
        
        final Player PLAYER = (Player) sender;
        final PlayerData DATA = Plugin.getPlayerData(sender);

        /* Check if the sender can still create homes */
        if(DATA.hasReachedMaxHomes()) {
            sender.sendMessage(ChatMessageUtil.format("%sYou have reached the maximum amount of homes !", ChatColor.RED));
            return true;
        }

        final String HOME_NAME = args[0];
        if(DATA.hasHome(HOME_NAME)) {
            sender.sendMessage(ChatMessageUtil.format("%sA home with the name %s\"%s\"%s already exists !", ChatColor.RED, ChatColor.RESET, HOME_NAME, ChatColor.RED));
            return true;
        }

        DATA.addHome(new Home(HOME_NAME, PLAYER.getLocation()));
        sender.sendMessage(ChatMessageUtil.format("Home %s%s%s has been set to your current location !", ChatColor.RED, HOME_NAME, ChatColor.RESET));

        return true;
    }

}
