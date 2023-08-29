package net.kamiland.levia.listeners;

import net.kamiland.levia.functions.JoinMessage;
import net.kamiland.levia.settings.Config;
import snw.jkook.JKook;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.TextChannel;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.user.UserJoinGuildEvent;
import snw.jkook.message.component.MarkdownComponent;

public class UserJoinGuild implements Listener {

    @EventHandler
    public void onUserJoinGuild(UserJoinGuildEvent e) {
        User user = e.getUser();
        TextChannel channel = (TextChannel) JKook.getHttpAPI().getChannel(Config.WELCOME_CHANNEL);

        channel.sendComponent(new MarkdownComponent(JoinMessage.getJoinMessage(user)));
    }
}
