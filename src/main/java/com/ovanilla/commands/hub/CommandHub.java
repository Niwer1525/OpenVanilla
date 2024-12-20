package com.ovanilla.commands.hub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.Plugin;
import com.ovanilla.ServerData;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandHub implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player PLAYER = (Player) sender;
        final ServerData DATA = Plugin.getServerData();

        PLAYER.teleport(DATA.getHubLocation());
        PLAYER.sendMessage(ChatMessageUtil.format("You have been teleported to the hub !"));

        return true;
    }
}
