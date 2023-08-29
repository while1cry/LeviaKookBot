package net.kamiland.levia.listeners;

import net.kamiland.levia.Levia;
import net.kamiland.levia.functions.ChatManager;
import snw.jkook.JKook;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageUpdateEvent;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.ImageElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContainerModule;
import snw.jkook.message.component.card.module.ContextModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

import java.util.Collections;

public class ChannelMessageUpdate implements Listener {
    @EventHandler
    public void onChannelMessageUpdate(ChannelMessageUpdateEvent e) {
        String message = e.getContent();
        if (ChatManager.isNonlegal(message)) {
            TextChannelMessage msg = JKook.getHttpAPI().getTextChannelMessage(e.getMessageId());
            // 违规内容，构建回复卡片
            MultipleCardComponent card = new CardBuilder()
                    .setSize(Size.LG)
                    .setTheme(Theme.DANGER)
                    .addModule(new HeaderModule(new PlainTextElement("哈喽！")))
                    .addModule(new SectionModule(new MarkdownElement("好像有个词儿在我的观察列表里哦。别担心，我们可以用更友好的方式交流。你需要什么帮助呢？")))
                    .addModule(new ContainerModule(Collections.singletonList(new ImageElement("https://www.copmc.cn/download/images/levia2.png", "Levia", false))))
                    .addModule(new ContextModule(Levia.CONTEXT))
                    .build();

            msg.replyTemp(card);

            msg.delete();
        }
    }
}
