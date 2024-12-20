package com.ovanilla.commands.homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.PlayerData;
import com.ovanilla.Plugin;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandHomeList implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /* Get the current player or the target player */
        Player player = null;
        if(args.length == 0 && Plugin.isSenderPlayer(sender)) player = (Player) sender;
        else if(args.length == 1 && sender.isOp()) player = sender.getServer().getPlayer(args[0]);

        if(player == null) {
            sender.sendMessage(ChatMessageUtil.format("No player found !"));
            return false;
        }

        final PlayerData DATA = Plugin.getPlayerData(player);
        sender.sendMessage(ChatMessageUtil.format("Home [%s] has been set to your current location !", DATA.getHomesNames(ChatColor.RED, ChatColor.RESET)));
        return true;
    }

}