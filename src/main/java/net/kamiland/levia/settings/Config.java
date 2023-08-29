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
    public static String SERVER_ID;
    public static List<String> OWNERS;
    public static List<String> ADMINS;
    public static String WELCOME_CHANNEL;
    public static List<String> WELCOME_MESSAGES;

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

        map.put("server-id", "9754303612253342");

        List<String> owners = new ArrayList<>();
        owners.add("2673173047");
        owners.add("135912549");
        map.put("owners", owners);

        List<String> admins = new ArrayList<>();
        map.put("admins", admins);

        map.put("welcome-channel", "2862856161765616");

        List<String> welcomeMessages = new ArrayList<>();
        welcomeMessages.add("欢迎 %user%！第 %onw% 位新成员加入了我们，一起度过美好的 %time% 吧！\uD83C\uDF1F");
        welcomeMessages.add("大家好，新朋友 %user% 到来！你是第 %onw% 位加入的成员，期待与你一同探索！\uD83C\uDF89");
        welcomeMessages.add("新人报到！欢迎 %user% 加入我们，第 %onw% 位新成员。希望你在这里有愉快的时光！\uD83D\uDC4B");
        welcomeMessages.add("嗨，%user%！欢迎加入像素之洲，你是第 %onw% 位新成员。希望你在这里交到志同道合的朋友！\uD83C\uDF3A");
        welcomeMessages.add("新伙伴 %user% 报到！第 %onw% 位成员加入，让我们一起在像素之洲中留下美好的回忆！\uD83C\uDF88");
        welcomeMessages.add("热烈欢迎新成员 %user%！你是第 %onw% 位加入的，与我们一起分享快乐时光吧！\uD83C\uDF20");
        welcomeMessages.add("新的面孔，新的故事。欢迎 %user% 加入我们，第 %onw% 位新成员。祝你在像素之洲中度过愉快的时光！\uD83C\uDF8A");
        welcomeMessages.add("加入派对！%user%，欢迎你的加入。你是第 %onw% 位新成员，一起畅所欲言吧！\uD83E\uDD73");
        welcomeMessages.add("新朋友，你好呀！%user%，欢迎来到像素之洲，希望你在这里找到属于你的角落。\uD83D\uDC4B");
        welcomeMessages.add("新人到来！%user%，欢迎你的加入。作为第 %onw% 位成员，愿你在像素之洲中度过愉快的时光！\uD83C\uDF08");
        map.put("welcome-messages", welcomeMessages);

        config.addDefaults(map);

        config.save(file);

        // Starting loading variables of config.yml

        SERVER_ID = config.getString("server-id");
        OWNERS = config.getStringList("owners");
        ADMINS = config.getStringList("admins");
        WELCOME_CHANNEL = config.getString("welcome-channel");
        WELCOME_MESSAGES = config.getStringList("welcome-messages");
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
