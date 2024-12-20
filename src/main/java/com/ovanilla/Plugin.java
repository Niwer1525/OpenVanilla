package com.ovanilla;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ovanilla.commands.CommandEnderChest;
import com.ovanilla.commands.CommandFaction;
import com.ovanilla.commands.CommandInvsee;
import com.ovanilla.commands.CommandSay;
import com.ovanilla.commands.homes.CommandDelHome;
import com.ovanilla.commands.homes.CommandHome;
import com.ovanilla.commands.homes.CommandHomeList;
import com.ovanilla.commands.homes.CommandSetHome;
import com.ovanilla.commands.hub.CommandHub;
import com.ovanilla.commands.hub.CommandSetHub;
import com.ovanilla.commands.money.CommandPay;
import com.ovanilla.commands.money.CommandWallet;

public class Plugin extends JavaPlugin {

    private static final Logger LOGGER = Logger.getLogger("Open Vanilla");
    private static final Map<String, PlayerData> PLAYER_DATA = new HashMap<>();
    
    private static ServerData serverData = new ServerData();
    private static Plugin instance;
    
    @Override
    public void onEnable() {
        instance = this; // Set the instance
        this.getLogger().info("Open Vanilla is now Loaded !");

        /* Server data */
        serverData = ServerData.load();

        /* Commands */
        {
            /* Homes */
            getCommand("sethome").setExecutor(new CommandSetHome());
            getCommand("delhome").setExecutor(new CommandDelHome());
            getCommand("homelist").setExecutor(new CommandHomeList());
            getCommand("home").setExecutor(new CommandHome());
            
            /* Hub */
            getCommand("hub").setExecutor(new CommandHub());
            getCommand("sethub").setExecutor(new CommandSetHub());

            /* Money */
            getCommand("wallet").setExecutor(new CommandWallet());
            getCommand("pay").setExecutor(new CommandPay());

            /* Other */
            getCommand("invsee").setExecutor(new CommandInvsee());
            getCommand("ec").setExecutor(new CommandEnderChest());
            getCommand("broadcast").setExecutor(new CommandSay());
            getCommand("faction").setExecutor(new CommandFaction());
        }

        /* Event listner */
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Open Vanilla is now Disabled !");

        /* Server data */
        serverData.save();
    }

    public static Plugin instance() { return instance; }

    public static boolean isSenderPlayer(CommandSender sender) { return sender instanceof Player; }

    /**
     * Get the player data of the sender (This assume that the sender is a player)
     * @param sender The sender
     * @return The player data
     */
    public static PlayerData getPlayerData(CommandSender sender) { return getPlayerData((Player)sender); }

    public static PlayerData getPlayerData(Player sender) {
        if(!PLAYER_DATA.containsKey(sender.getName())) // If the player data is not already stored
            setPlayerData(sender, new PlayerData());

        return PLAYER_DATA.get(sender.getName()); // Return the player data
    }
    
    public static void setPlayerData(Player player, PlayerData data) { PLAYER_DATA.put(player.getName(), data); }

    public static ServerData getServerData() { return serverData; }
    
    public static void log(Object obj) { LOGGER.info(obj.toString()); }
}