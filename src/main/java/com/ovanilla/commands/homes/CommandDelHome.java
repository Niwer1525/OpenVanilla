package com.ovanilla.commands.homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.ovanilla.PlayerData;
import com.ovanilla.Plugin;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandDelHome implements CommandExecutor {
    
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
        
        final PlayerData DATA = Plugin.getPlayerData(sender);
        final String HOME_NAME = args[0];

        if(!DATA.hasHome(HOME_NAME)) {
            sender.sendMessage(ChatMessageUtil.format("%sHome %s\"%s\"%s does not exist !", ChatColor.RED, ChatColor.RESET, HOME_NAME, ChatColor.RED));
            return true;
        }

        DATA.removeHome(HOME_NAME);
        sender.sendMessage(ChatMessageUtil.format("Home %s%s%s has been deleted !", ChatColor.RED, HOME_NAME, ChatColor.RESET));

        return true;
    }

}
