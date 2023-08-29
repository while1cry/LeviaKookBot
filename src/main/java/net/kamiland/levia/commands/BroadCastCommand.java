package net.kamiland.levia.commands;

import net.kamiland.levia.Levia;
import net.kamiland.levia.functions.BroadCast;
import net.kamiland.levia.settings.Config;
import snw.jkook.JKook;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.CommandSender;
import snw.jkook.entity.Guild;
import snw.jkook.entity.User;
import snw.jkook.entity.channel.TextChannel;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BroadCastCommand implements CommandExecutor {

    @Override
    public void onCommand(CommandSender sender, Object[] args, Message message) {
        if (message instanceof TextChannelMessage && sender instanceof User) {
            Guild guild = ((TextChannelMessage) message).getChannel().getGuild();
            TextChannel channel = ((TextChannelMessage) message).getChannel();
            TextChannelMessage msg = (TextChannelMessage) message;
            User user = (User) sender;

            // 判断 guild 的身份
            if (!guild.getId().equals("9754303612253342")) {
                Levia.getInstance().getLogger().info(guild.getId() + " 并不是官方服务器!");
                return;
            }

            MultipleCardComponent card;
            // 判断 user 的身份
            if (Config.OWNERS.contains(user.getId()) || Config.ADMINS.contains(user.getId())) {
                if (args == null || args.length < 1) {
                    // 参数不足，构建回复卡片
                    card = new CardBuilder()
                            .setSize(Size.LG)
                            .setTheme(Theme.DANGER)
                            .addModule(new HeaderModule(new PlainTextElement("参数异常")))
                            .addModule(new SectionModule(new PlainTextElement("请使用指令 $help 查看更多信息")))
                            .addModule(new ContextModule(Levia.CONTEXT))
                            .build();

                    msg.reply(card);
                    return;
                }

                // 如果只有一个参数
                if (args.length == 1) {
                    String chnId = args[0].toString();
                    if (chnId.startsWith("(chn)") && chnId.endsWith("(chn)")) {
                        chnId = chnId.replaceAll("\\(chn\\)", "");

                        BroadCast.waitUser(user, channel, Collections.singletonList((TextChannel) JKook.getHttpAPI().getChannel(chnId)));
                        // 构建回复卡片，使用户回复
                        card = new CardBuilder()
                                .setSize(Size.LG)
                                .setTheme(Theme.INFO)
                                .addModule(new HeaderModule(new PlainTextElement("下一步")))
                                .addModule(new SectionModule(new PlainTextElement("请在此处发送需要广播的内容")))
                                .addModule(new ContextModule(Levia.CONTEXT))
                                .build();

                        msg.reply(card);
                    } else {
                        // 参数异常，构建回复卡片
                        card = new CardBuilder()
                                .setSize(Size.LG)
                                .setTheme(Theme.DANGER)
                                .addModule(new HeaderModule(new PlainTextElement("参数异常")))
                                .addModule(new SectionModule(new PlainTextElement("请使用指令 $help 查看更多信息")))
                                .addModule(new ContextModule(Levia.CONTEXT))
                                .build();

                        msg.reply(card);
                    }
                } else {
                    List<TextChannel> channels = new ArrayList<>();
                    for (Object obj : args) {
                        String chnId = obj.toString();

                        if (chnId.startsWith("(chn)") && chnId.endsWith("(chn)")) {
                            chnId = chnId.replaceAll("\\(chn\\)", "");

                            channels.add((TextChannel) JKook.getHttpAPI().getChannel(chnId));
                        }
                    }

                    BroadCast.waitUser(user, channel, channels);

                    // 构建回复卡片，使用户回复
                    card = new CardBuilder()
                            .setSize(Size.LG)
                            .setTheme(Theme.INFO)
                            .addModule(new HeaderModule(new PlainTextElement("下一步")))
                            .addModule(new SectionModule(new PlainTextElement("请在此处发送需要广播的内容")))
                            .addModule(new ContextModule(Levia.CONTEXT))
                            .build();

                    msg.reply(card);
                }
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