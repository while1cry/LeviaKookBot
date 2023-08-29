package net.kamiland.levia.settings;

import net.kamiland.levia.Levia;
import snw.jkook.config.file.FileConfiguration;
import snw.jkook.config.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Data {
    public static String getData(String key) {
        File file = new File(Levia.getInstance().getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getString(key);
    }

    public static void setData(String key, String value) {
        File file = new File(Levia.getInstance().getDataFolder(), "data.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(key, value);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
