package net.kamiland.levia;

import net.kamiland.levia.commands.*;
import net.kamiland.levia.listeners.ChannelMessage;
import net.kamiland.levia.listeners.ChannelMessageUpdate;
import net.kamiland.levia.listeners.UserClickButton;
import net.kamiland.levia.listeners.UserJoinGuild;
import net.kamiland.levia.settings.Chat;
import net.kamiland.levia.settings.Config;
import org.slf4j.Logger;
import snw.jkook.command.JKookCommand;
import snw.jkook.message.component.card.element.BaseElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.plugin.BasePlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class Levia extends BasePlugin {
    public static final List<BaseElement> CONTEXT = Arrays.asList(new MarkdownElement("莱薇娅 v1.4.1  |  [Github](https://github.com/while1cry/LeviaKookBot)"));

    private static Levia instance;
    private static Logger logger;

    @Override
    public void onEnable() {
        instance = this;

        initPlugin();

        loadSettings();
        regCommands();
        regListeners();
    }

    private void initPlugin() {
        logger = getInstance().getLogger();

        logger.info("Initialising plugin...");
    }

    private void loadSettings() {
        logger.info("Loading settings...");

        try {
            Config.loadAll();
            Chat.loadAll();
        } catch (IOException e) {
            logger.error("An error occurred while loading settings!", e);
        }
    }

    private void regCommands() {
        logger.info("Registering commands...");

        new JKookCommand("admin", "$").addAlias("管理").setExecutor(new AdminCommand()).register(getInstance());
        new JKookCommand("broadcast", "$").addAlias("广播").addAlias("bc").setExecutor(new BroadCastCommand()).register(getInstance());
        new JKookCommand("chat", "$").addAlias("聊天").setExecutor(new ChatCommand()).register(getInstance());
        new JKookCommand("help", "$").addAlias("帮助").setExecutor(new HelpCommand()).register(getInstance());
        new JKookCommand("levia", "$").addAlias("莱薇娅").setExecutor(new LeviaCommand()).register(getInstance());
    }

    private void regListeners() {
        logger.info("Registering listeners...");

        getCore().getEventManager().registerHandlers(getInstance(), new ChannelMessage());
        getCore().getEventManager().registerHandlers(getInstance(), new ChannelMessageUpdate());
        getCore().getEventManager().registerHandlers(getInstance(), new UserClickButton());
        getCore().getEventManager().registerHandlers(getInstance(), new UserJoinGuild());
    }

    public void reloadPlugin() {
        loadSettings();
    }

    public static Levia getInstance() {
        return instance;
    }
}
