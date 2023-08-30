package net.kamiland.levia.utils;

import net.kamiland.levia.Levia;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

public class CardTemplate {
    private static MultipleCardComponent card;

    public static MultipleCardComponent getUnknownCommandCard() {
        card = new CardBuilder()
                .setSize(Size.LG)
                .setTheme(Theme.WARNING)
                .addModule(new HeaderModule(new PlainTextElement("未知的指令")))
                .addModule(new SectionModule(new PlainTextElement("使用指令 $帮助 查看帮助")))
                .addModule(new ContextModule(Levia.CONTEXT))
                .build();

        return card;
    }

    public static MultipleCardComponent getWrongUsageCard() {
        card = new CardBuilder()
                .setSize(Size.LG)
                .setTheme(Theme.DANGER)
                .addModule(new HeaderModule(new PlainTextElement("错误的用法")))
                .addModule(new SectionModule(new PlainTextElement("使用指令 $帮助 查看帮助")))
                .addModule(new ContextModule(Levia.CONTEXT))
                .build();

        return card;
    }
}
