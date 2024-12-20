package com.ovanilla.commands.money;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandPay implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 2) return false;
        
        /* Get the current player or the target player */
        final Player PLAYER = sender.getServer().getPlayer(args[0]);
        final int AMOUNT = Integer.parseInt(args[1]);

        sender.sendMessage(ChatMessageUtil.formatWithColor("You have given &c%s&r to &c%s&r!", AMOUNT, PLAYER.getName()));
        PLAYER.sendMessage(ChatMessageUtil.formatWithColor("You have received &c%s&r from &c%s&r!", AMOUNT, sender.getName()));

        return true;
    }
}