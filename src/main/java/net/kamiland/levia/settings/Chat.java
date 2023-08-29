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

public class Chat {
    public static boolean STRICT_MODE;
    public static boolean URL_CHECK;
    public static List<String> FILTER_LIST;
    public static List<String> WHITELIST;

    /**
     * Load all variables of chat.yml
     *
     * @author while1cry
     * @throws IOException If the configuration file does not exist
     */
    public static void loadAll() throws IOException {
        File file = new File(Levia.getInstance().getDataFolder(), "chat.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.options().copyDefaults(true);

        Map<String, Object> map = new LinkedHashMap<>();

        map.put("strict-mode", false);
        map.put("url-check", true);

        List<String> filterWords = new ArrayList<>();
        filterWords.add("你妈"); // 默认敏感词
        map.put("filter-words", filterWords);

        List<String> whitelist = new ArrayList<>();
        whitelist.add("www.copmc.cn");
        map.put("whitelist", whitelist);

        config.addDefaults(map);

        config.save(file);

        STRICT_MODE = config.getBoolean("strict-mode");
        URL_CHECK = config.getBoolean("url-check");
        FILTER_LIST = config.getStringList("filter-words");
        WHITELIST = config.getStringList("whitelist");
    }

    public static void setStrictMode(boolean b) {
        File file = new File(Levia.getInstance().getDataFolder(), "chat.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        STRICT_MODE = b;

        config.set("strict-mode", b);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setUrlCheck(boolean b) {
        File file = new File(Levia.getInstance().getDataFolder(), "chat.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        URL_CHECK = b;

        config.set("url-check", b);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addFilter(String word) {
        File file = new File(Levia.getInstance().getDataFolder(), "chat.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        FILTER_LIST.add(word);

        List<String> list = config.getStringList("filter-words");
        list.add(word);

        config.set("filter-words", list);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFilter(String word) {
        File file = new File(Levia.getInstance().getDataFolder(), "chat.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        FILTER_LIST.remove(word);

        List<String> list = config.getStringList("filter-words");
        list.remove(word);

        config.set("filter-words", list);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
