package net.kamiland.levia.settings;

import net.kamiland.levia.Levia;
import snw.jkook.config.file.FileConfiguration;
import snw.jkook.config.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Config {

    public static List<String> WELCOME_CHANNELS;
    public static List<String> OWNERS;
    public static List<String> ADMINS;

    /**
     * Load all variables of config.yml
     *
     * @author while1cry
     * @throws IOException If the configuration file does not exist
     */
    public static void loadAll() throws IOException {
        File file = new File(Levia.getInstance().getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Starting initializing config.yml

        config.options().copyDefaults(true);
        Map<String, Object> map = new LinkedHashMap<>();

        List<String> welcomeChannels = new ArrayList<>();
        welcomeChannels.add("2862856161765616");
        map.put("welcome-channels", welcomeChannels);

        List<String> owners = new ArrayList<>();
        owners.add("2673173047");
        owners.add("135912549");
        map.put("owners", owners);

        List<String> admins = new ArrayList<>();
        admins.add("Unknown");
        map.put("admins", admins);

        config.addDefaults(map);

        config.save(file);

        // Starting loading variables of config.yml

        WELCOME_CHANNELS = config.getStringList("welcome-channels");
        OWNERS = config.getStringList("owners");
        ADMINS = config.getStringList("admins");
    }

    public static void putAdmin(String id) throws IOException {
        File file = new File(Levia.getInstance().getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> admins = config.getStringList("admins");
        admins.add(id);
        config.set("admins", admins);

        config.save(file);

        loadAll();
    }

    public static void removeAdmin(String id) throws IOException {
        File file = new File(Levia.getInstance().getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> admins = config.getStringList("admins");
        admins.remove(id);
        config.set("admins", admins);

        config.save(file);

        loadAll();
    }
}
