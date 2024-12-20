package com.ovanilla;

import java.io.Serializable;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public record Home(String name, Location position) implements Serializable, ConfigurationSerializable {

    @Override
    public Map<String, Object> serialize() {
        return Map.of("name", name, "position", position);
    }

    public static Home deserialize(Map<String, Object> data) {
        return new Home((String) data.get("name"), (Location) data.get("position"));
    }
}