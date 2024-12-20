package com.ovanilla.utils.math;

import org.bukkit.Location;
import org.bukkit.World;

public record Vector3i(int x, int y, int z) {

    public Vector3i(float x, float y, float z) {
        this((int) x, (int) y, (int) z);
    }

    public Vector3i(double x, double y, double z) {
        this((int) x, (int) y, (int) z);
    }

    public Location asLocation(World world) {
        return new Location(world, x, y, z);
    }
}
