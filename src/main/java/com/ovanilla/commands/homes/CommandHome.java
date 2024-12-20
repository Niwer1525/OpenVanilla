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

public class CommandHome implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /* Get the current player or the target player */
        Player player = null;
        if(args.length == 1 && Plugin.isSenderPlayer(sender)) player = (Player) sender;
        else if(args.length == 2 && sender.isOp()) player = sender.getServer().getPlayer(args[1]);

        if(player == null) {
            sender.sendMessage(ChatMessageUtil.format("No player found !"));
            return false;
        }

        final PlayerData DATA = Plugin.getPlayerData(sender);
        final String HOME_NAME = args[0];
        final Home HOME = DATA.getHome(HOME_NAME);

        if(HOME == null) {
            sender.sendMessage(ChatMessageUtil.format("Home not found !"));
            return true;
        }

        player.teleport(HOME.position());
        player.sendMessage(ChatMessageUtil.format("You have been teleported to %s%s%s !", ChatColor.RED, HOME_NAME, ChatColor.RESET));

        return true;
    }

}