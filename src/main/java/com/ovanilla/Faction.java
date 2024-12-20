package com.ovanilla;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

public record Faction(String name, List<String> players) implements Serializable, ConfigurationSerializable {

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> data = Map.of("name", name, "players", players);
        return data;
    }

    public static Faction deserialize(List<String> args) {
        return new Faction(args.get(0), args.subList(1, args.size()));
    }
}