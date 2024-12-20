package com.ovanilla;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.Lists;
import com.ovanilla.utils.DataFileUtil;

public class ServerData implements Serializable, ConfigurationSerializable {

    private static final File SERVER_DATA_FILE = new File("./plugins/open_vanilla/server_data.ovadat");
    private Location hubLocation;
    private final Map<String, Faction> factions = new HashMap<>();

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> data = new HashMap<>();

        data.put("hubLocation", hubLocation);
        data.put("factions", factions);

        return data;
    }

    /**
     * Required method for configuration serialization
     *
     * @param args map to deserialize
     * @return deserialized data file
     * @see ConfigurationSerializable
     */
    @SuppressWarnings("unchecked")
    public static ServerData deserialize(Map<String, Object> args) {
        final ServerData data = new ServerData();

        data.hubLocation = (Location) args.get("hubLocation");
        data.factions.putAll((Map<? extends String, ? extends Faction>) args.get("factions"));

        return data;
    }

    public Location getHubLocation() {
        if (hubLocation == null) // If the hub location is not set, return the spawn location of the first world
            return Plugin.instance().getServer().getWorlds().get(0).getSpawnLocation();
        
        return hubLocation;
    }

    public void setHubLocation(Location hubLocation) {
        this.hubLocation = hubLocation;
        save();
    }

    public Faction getDefaultFaction() {
        Faction faction = getFaction("None");
        if (faction == null) {
            faction = new Faction("None", Lists.newArrayList());
            addFaction(faction);
        }
        return faction;
    }

    public Faction getFaction(String name) {
        return factions.get(name);
    }

    public void addFaction(Faction faction) {
        factions.put(faction.name(), faction);
        save();
    }

    public void removeFaction(String name) {
        factions.remove(name);
        save();
    }

    public void save() {
        DataFileUtil.saveData(SERVER_DATA_FILE, this);
    }

    public static ServerData load() {
        var data = DataFileUtil.loadData(SERVER_DATA_FILE);
        return data == null ? new ServerData() : (ServerData)data;
    }
}
