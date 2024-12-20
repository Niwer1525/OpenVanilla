package com.ovanilla.commands.hub;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.Plugin;
import com.ovanilla.ServerData;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandSetHub implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player PLAYER = (Player) sender;
        final ServerData DATA = Plugin.getServerData();
        final Location NEW_HUB_LOCATION = PLAYER.getLocation();

        DATA.setHubLocation(NEW_HUB_LOCATION);
        PLAYER.sendMessage(ChatMessageUtil.format("The HUB has been set to %s%d, %d, %d%s !", 
            ChatColor.RED,
            NEW_HUB_LOCATION.getBlockX(),
            NEW_HUB_LOCATION.getBlockY(),
            NEW_HUB_LOCATION.getBlockZ(),
            ChatColor.RESET
        ));

        return true;
    }
}
