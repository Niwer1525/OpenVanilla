package com.ovanilla;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ovanilla.utils.chat.ChatMessage.Action;
import com.ovanilla.utils.chat.ChatMessageUtil;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        /* Set join message */
        event.setJoinMessage(ChatMessageUtil.format("[%s+%s] %s", ChatColor.GREEN, ChatColor.RESET, player.getName()));
        
        /* Send help/join message */
        ChatMessageUtil.createMessage(player).append("Welcome to the server !")
            .setColor(ChatColor.RED)
            .setClickEvent(Action.RUN_COMMAND, "/help")
            .setHoverEvent(Action.SHOW_TEXT, ChatMessageUtil.createMessage(player).append("Click to get help"))
            .send();
    
        var data = PlayerData.load(getPlayerDataFile(player));
        Plugin.setPlayerData(player, data);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = Plugin.getPlayerData(player);

        /* Set quit message */
        event.setQuitMessage(ChatMessageUtil.format("[%s-%s] %s", ChatColor.RED, ChatColor.RESET, player.getName()));

        /* Save the player data */
        playerData.save(getPlayerDataFile(player));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerData playerData = Plugin.getPlayerData(player);
        playerData.addDeath();
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = Plugin.getPlayerData(player);

        /* Format the new message */
        String message = event.getMessage();
        for(Player p : event.getRecipients()) // Highlight the player name
            message.replaceAll(p.getName(), ChatColor.RED + p.getName() + ChatColor.RESET);
        Plugin.log(event.getRecipients());

        /* Cancel the event and send the new message */
        event.setCancelled(true);
        player.sendMessage(ChatMessageUtil.formatWithColor("[&c%s&r] %s : %s", 
            playerData.getFaction().name(),
            player.getName(),
            message
        ));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        var block = event.getBlockPlaced();
        if(block.getType() == Material.TNT) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "You can't place TNT !");
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        var target = event.getEntity();
        var attacker = event.getDamager();

        if(target instanceof Player targetPlayer && attacker instanceof Player attackerPlayer) {
            var targetData = Plugin.getPlayerData(targetPlayer);
            var attackerData = Plugin.getPlayerData(attackerPlayer);

            if(targetData.getFaction() == attackerData.getFaction()) {
                event.setCancelled(true);
                attackerPlayer.sendMessage(ChatMessageUtil.formatWithColor("&cYou can't attack a player from the same faction !"));
            }
        }
    }

    private static File getPlayerDataFile(Player player) { return new File("./plugins/open_vanilla/data_"+player.getUniqueId()+".ovadat"); }
}