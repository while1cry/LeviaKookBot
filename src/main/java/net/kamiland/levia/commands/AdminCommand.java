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
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.module.ContextModule;
import snw.jkook.message.component.card.module.HeaderModule;
import snw.jkook.message.component.card.module.SectionModule;

import java.io.IOException;
import java.util.Locale;

public class AdminCommand implements CommandExecutor {

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

            // 判断 user 的身份
            MultipleCardComponent card;
            if (Config.OWNERS.contains(user.getId())) {
                if (args == null || args.length < 2) {
                    // 参数异常，构建回复卡片
                    msg.reply(CardTemplate.getWrongUsageCard());

                } else {
                    String id;
                    switch (args[0].toString().toLowerCase(Locale.ROOT)) {
                        case "增加":
                        case "add":
                            // 增加管理员

                            id = args[1].toString();
                            if (id.startsWith("(met)") && id.endsWith("(met)")) {
                                id = id.replaceAll("\\(met\\)", "");

                                if (! Config.ADMINS.contains(id)) {
                                    // 如果Admins里不存在这个用户
                                    try {
                                        // Put这个用户
                                        Config.putAdmin(id);

                                        //构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.SUCCESS)
                                                .addModule(new HeaderModule(new PlainTextElement("操作成功")))
                                                .addModule(new SectionModule(new MarkdownElement("已成功为 (met)" + id + "(met) 添加管理员身份")))
                                                .addModule(new SectionModule(new PlainTextElement("注: 此身份只对莱薇娅有效!")))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    } catch (IOException ignore) {
                                        // 发生异常，构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.DANGER)
                                                .addModule(new HeaderModule(new PlainTextElement("发生异常")))
                                                .addModule(new SectionModule(new PlainTextElement("操作权限组时发生了异常!")))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    }
                                } else {
                                    // Admins里已有这个用户
                                    // 构建回复卡片
                                    card = new CardBuilder()
                                            .setSize(Size.LG)
                                            .setTheme(Theme.DANGER)
                                            .addModule(new HeaderModule(new PlainTextElement("操作失败")))
                                            .addModule(new SectionModule(new PlainTextElement("这个用户已经是管理员了!")))
                                            .addModule(new ContextModule(Levia.CONTEXT))
                                            .build();

                                    msg.reply(card);
                                }
                            } else {
                                // 参数异常，构建回复卡片
                                msg.reply(CardTemplate.getWrongUsageCard());
                            }
                            return;
                        case "移除":
                        case "remove":
                            // 移除管理员
                            id = args[1].toString();
                            if (id.startsWith("(met)") && id.endsWith("(met)")) {
                                id = id.replaceAll("(met)", "");

                                if (Config.ADMINS.contains(id)) {
                                    // 移除这个管理员
                                    try {
                                        // Put这个用户
                                        Config.removeAdmin(id);

                                        //构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.SUCCESS)
                                                .addModule(new HeaderModule(new PlainTextElement("操作成功")))
                                                .addModule(new SectionModule(new MarkdownElement("已成功移除 (met)" + id + "(met) 的管理员身份")))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    } catch (IOException ignore) {
                                        // 发生异常，构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.DANGER)
                                                .addModule(new HeaderModule(new PlainTextElement("发生异常")))
                                                .addModule(new SectionModule(new PlainTextElement("操作权限组时发生了异常!")))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    }
                                } else {
                                    // 不存在这个管理员，构建回复卡片
                                    card = new CardBuilder()
                                            .setSize(Size.LG)
                                            .setTheme(Theme.DANGER)
                                            .addModule(new HeaderModule(new PlainTextElement("操作失败")))
                                            .addModule(new SectionModule(new PlainTextElement("这个用户并不是管理员!")))
                                            .addModule(new ContextModule(Levia.CONTEXT))
                                            .build();

                                    msg.reply(card);
                                }
                            } else {
                                // 参数异常，构建回复卡片
                                msg.reply(CardTemplate.getWrongUsageCard());
                            }
                            return;
                    }

                    // 未知指令，构建回复卡片
                    msg.reply(CardTemplate.getUnknownCommandCard());
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
