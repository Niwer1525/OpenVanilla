package com.ovanilla.commands.money;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ovanilla.PlayerData;
import com.ovanilla.Plugin;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class CommandWallet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /* Get the current player or the target player */
        Player player = (Player) sender;
        if(args.length >= 2 && sender.isOp()) player = sender.getServer().getPlayer(args[1]);

        final int AMOUNT = args.length >= 1 ? Integer.parseInt(args[0]) : 0;
        final String TYPE = args.length >= 3 ? args[2] : "default";
        final PlayerData DATA = Plugin.getPlayerData(player);
        
        switch (TYPE) {
            case "give":
                DATA.addMoney(AMOUNT);
                sender.sendMessage(ChatMessageUtil.formatWithColor("You have given &c%s&r$ to &c%s&r!", AMOUNT, player.getName()));
                break;
            case "remove":
                DATA.removeMoney(AMOUNT);
                sender.sendMessage(ChatMessageUtil.formatWithColor("You have removed &c%s&r$ from &c%s&r!", AMOUNT, player.getName()));
                break;
            case "set":
                DATA.setMoney(AMOUNT);
                sender.sendMessage(ChatMessageUtil.formatWithColor("You have set &c%s&r$ to &c%s&r!", AMOUNT, player.getName()));
                break;
            default:
                sender.sendMessage(ChatMessageUtil.formatWithColor("You have &c%s&r$ in your wallet !", DATA.getMoney()));
                break;
        }

        return true;
    }
}
