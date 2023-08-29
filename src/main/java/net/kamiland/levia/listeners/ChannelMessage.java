package net.kamiland.levia.listeners;

import net.kamiland.levia.Levia;
import net.kamiland.levia.functions.BroadCast;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.TextChannel;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

public class ChannelMessage implements Listener {

    @EventHandler
    public void onChannelMessage(ChannelMessageEvent e) {
        Guild guild = e.getChannel().getGuild();
        TextChannel chn = e.getChannel();
        TextChannelMessage msg = e.getMessage();

        User user = msg.getSender();

        // 判断 guild 的身份
        if (! guild.getId().equals("9754303612253342")) {
            Levia.getInstance().getLogger().info(guild.getId() + " 并不是官方服务器!");
            return;
        }

        MultipleCardComponent card;
        // 判断 user 的身份
        if (BroadCast.getWaitingMap().containsKey(user) && BroadCast.getWaitingMap().get(user).getId().equals(chn.getId())) {
            TextChannel[] targetChannels = BroadCast.getBroadCastChannels(user).toArray(new TextChannel[0]);
            BaseComponent bc = msg.getComponent();

            card = new CardBuilder()
                    .setSize(Size.LG)
                    .setTheme(Theme.INFO)
                    .addModule(new HeaderModule(new PlainTextElement("中洲广播")))
                    .addModule(new SectionModule(new MarkdownElement(bc.toString())))
                    .addModule(new ContextModule(Levia.CONTEXT))
                    .build();

            for (TextChannel channel : targetChannels) {
                channel.sendComponent(card);
            }
            // 发送完成，清除map

            BroadCast.removeUser(user);

            // 发送完成，构建卡片
            card = new CardBuilder()
                    .setSize(Size.LG)
                    .setTheme(Theme.SUCCESS)
                    .addModule(new HeaderModule(new PlainTextElement("操作成功")))
                    .addModule(new SectionModule(new PlainTextElement("公告已被发送至目标频道")))
                    .addModule(new ContextModule(Levia.CONTEXT))
                    .build();

            msg.reply(card);
        }
    }
}
