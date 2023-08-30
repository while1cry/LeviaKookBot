package net.kamiland.levia.commands;

import net.kamiland.levia.Levia;
import net.kamiland.levia.settings.Config;
import net.kamiland.levia.utils.CardTemplate;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.CommandSender;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.message.Message;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;
import java.util.Locale;

public class LeviaCommand implements CommandExecutor {

    @Override
    public void onCommand(CommandSender sender, Object[] args, Message message) {
        if (message instanceof TextChannelMessage && sender instanceof User) {
            Guild guild = ((TextChannelMessage) message).getChannel().getGuild();
            TextChannelMessage msg = (TextChannelMessage) message;
            User user = (User) sender;

            // 判断 guild 的身份
            if (! Config.SERVER_LIST.contains(guild.getId())) {
                Levia.getInstance().getLogger().info(guild.getId() + " 并不是官方服务器!");
                return;
            }

            MultipleCardComponent card;
            // 判断 user 的身份
            if (Config.OWNERS.contains(user.getId()) || Config.ADMINS.contains(user.getId())) {
                if (args == null || args.length < 1) {
                    // 参数不足，构建回复卡片
                    msg.reply(CardTemplate.getWrongUsageCard());
                    return;
                }

                switch (args[0].toString().toLowerCase(Locale.ROOT)) {
                    case "重载":
                    case "reload":
                        Levia.getInstance().reloadPlugin();

                        // 构建回复卡片
                        card = new CardBuilder()
                                .setSize(Size.LG)
                                .setTheme(Theme.SUCCESS)
                                .addModule(new HeaderModule(new PlainTextElement("操作完成")))
                                .addModule(new SectionModule(new PlainTextElement("莱薇娅已重载所有配置文件")))
                                .addModule(new ContextModule(Levia.CONTEXT))
                                .build();

                        msg.reply(card);
                        return;
                }

                // 未知指令，构建回复卡片
                msg.reply(CardTemplate.getUnknownCommandCard());
            } else {
                // 构建回复卡片，无权操作
                card = new CardBuilder()
                        .setSize(Size.LG)
                        .setTheme(Theme.DANGER)
                        .addModule(new HeaderModule(new PlainTextElement("操作失败")))
                        .addModule(new SectionModule(new PlainTextElement("你没有足够的权限执行此操作")))
                        .addModule(new ContextModule(Levia.CONTEXT))
                        .build();

                msg.reply(card);
            }
        }
    }
}
