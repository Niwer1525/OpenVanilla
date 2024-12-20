package com.ovanilla;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.ovanilla.utils.DataFileUtil;

/**
 * Represents a player's data that is stored in the plugin.
 */
public class PlayerData implements Serializable, ConfigurationSerializable {

    private static final Faction DEFAULT_FACTION = Plugin.getServerData().getDefaultFaction();
    
    private final Map<String, Home> homes = new HashMap<>();
    private int maxHomes = 4;
    private int money;
    private int deathCount;
    private Faction faction = DEFAULT_FACTION;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("homes", homes);
        data.put("maxHomes", maxHomes);
        data.put("money", money);
        data.put("deathCount", deathCount);
        data.put("faction", faction.name());
        return data;
    }

    @SuppressWarnings("unchecked")
    public static PlayerData deserialize(Map<String, Object> data) {
        PlayerData playerData = new PlayerData();
        playerData.homes.putAll((Map<String, Home>) data.get("homes"));
        playerData.maxHomes = (int) data.get("maxHomes");
        playerData.money = (int) data.get("money");
        playerData.deathCount = (int) data.get("deathCount");
        playerData.faction = Plugin.getServerData().getFaction((String) data.get("faction"));
        return playerData;
    }

    public boolean hasReachedMaxHomes() {
        return homes.size() >= maxHomes;
    }

    public Home getHome(String homeName) {
        return homes.get(homeName);
    }

    public List<Home> getHomes() {
        return new ArrayList<>(homes.values());
    }

    public String getHomesNames(ChatColor startColor, ChatColor endColor) {
        StringBuilder homesNames = new StringBuilder();
        List<Home> homes = getHomes();

        for (int i = 0; i < homes.size(); i++) {
            Home home = homes.get(i);
            homesNames.append(startColor).append(home.name()).append(endColor);
            if (i != homes.size() - 1) homesNames.append(", "); // If it's not the last home, add a comma
        }

        return homesNames.toString();
    }

    public boolean hasHome(String homeName) {
        return homes.containsKey(homeName);
    }

    public void addHome(Home home) {
        homes.put(home.name(), home);
    }

    public void removeHome(String homeName) {
        homes.remove(homeName);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void removeMoney(int money) {
        this.money -= money;
    }

    public boolean hasEnoughMoney(int money) {
        return this.money >= money;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void addDeath() {
        deathCount++;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(String factionName) {
        Faction faction = Plugin.getServerData().getFaction(factionName);
        this.faction = faction == null ? DEFAULT_FACTION : faction;
    }

    public void save(File file) {
        DataFileUtil.saveData(file, this);
    }

    public static PlayerData load(File file) {
        var data = DataFileUtil.loadData(file);
        return data == null ? new PlayerData() : (PlayerData)data;
    }
}