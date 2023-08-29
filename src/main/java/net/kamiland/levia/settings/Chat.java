package net.kamiland.levia.settings;

import net.kamiland.levia.Levia;
import snw.jkook.config.file.FileConfiguration;
import snw.jkook.config.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Chat {

    /**
     * Load all variables of chat.yml
     *
     * @author while1cry
     * @throws IOException If the configuration file does not exist
     */
    public static void loadAll() throws IOException {
        File file = Levia.getInstance().getDataFolder();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
    }
}
