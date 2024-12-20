package com.ovanilla.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class DataFileUtil
{
    public static boolean saveData(final File filePath, final Object data) {
        try {
            final File parent = filePath.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            final var out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(data);
            out.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Object loadData(final File filePath) {
        try {
            if(!filePath.exists()) return null;
            
            final var in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Object data = in.readObject();
            in.close();
            return data;
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

