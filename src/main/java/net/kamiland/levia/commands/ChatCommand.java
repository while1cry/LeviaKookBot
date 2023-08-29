package net.kamiland.levia.commands;

import net.kamiland.levia.Levia;
import net.kamiland.levia.settings.Chat;
import net.kamiland.levia.settings.Config;
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

public class ChatCommand implements CommandExecutor {
    @Override
    public void onCommand(CommandSender sender, Object[] args, Message message) {
        if (message instanceof TextChannelMessage && sender instanceof User) {
            Guild guild = ((TextChannelMessage) message).getChannel().getGuild();
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

                switch (args[0].toString().toLowerCase(Locale.ROOT)) {
                    case "严格":
                    case "strict":
                        Chat.setStrictMode(!Chat.STRICT_MODE);
                        // 操作完成，构建回复卡片
                        card = new CardBuilder()
                                .setSize(Size.LG)
                                .setTheme(Theme.SUCCESS)
                                .addModule(new HeaderModule(new PlainTextElement("操作完成")))
                                .addModule(new SectionModule(new PlainTextElement("严格审查模式已设为: " + Chat.STRICT_MODE)))
                                .addModule(new ContextModule(Levia.CONTEXT))
                                .build();

                        msg.reply(card);
                        return;
                    case "网址检测":
                    case "urlcheck":
                        Chat.setUrlCheck(!Chat.URL_CHECK);
                        // 操作完成，构建回复卡片
                        card = new CardBuilder()
                                .setSize(Size.LG)
                                .setTheme(Theme.SUCCESS)
                                .addModule(new HeaderModule(new PlainTextElement("操作完成")))
                                .addModule(new SectionModule(new PlainTextElement("URL检测模式已设为: " + Chat.URL_CHECK)))
                                .addModule(new ContextModule(Levia.CONTEXT))
                                .build();

                        msg.reply(card);
                        return;
                    case "屏蔽词":
                    case "filter":
                        if (args.length >= 2) {
                            switch (args[1].toString().toLowerCase(Locale.ROOT)) {
                                case "增加":
                                case "add":
                                    if (args.length < 3) {
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

                                    String word = args[2].toString().toLowerCase(Locale.ROOT);

                                    if (! Chat.FILTER_LIST.contains(word)) {
                                        Chat.addFilter(word);

                                        // 构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.SUCCESS)
                                                .addModule(new HeaderModule(new PlainTextElement("操作完成")))
                                                .addModule(new SectionModule(new PlainTextElement("已经添加敏感词: " + word)))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    } else {
                                        // 已经存在，构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.DANGER)
                                                .addModule(new HeaderModule(new PlainTextElement("操作失败")))
                                                .addModule(new SectionModule(new PlainTextElement("已经存在该敏感词")))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    }

                                    break;
                                case "移除":
                                case "remove":
                                    if (args.length < 3) {
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

                                    word = args[2].toString().toLowerCase(Locale.ROOT);

                                    if (Chat.FILTER_LIST.contains(word)) {
                                        Chat.removeFilter(word);

                                        // 构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.SUCCESS)
                                                .addModule(new HeaderModule(new PlainTextElement("操作完成")))
                                                .addModule(new SectionModule(new PlainTextElement("已经移除敏感词: " + word)))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    } else {
                                        // 已经存在，构建回复卡片
                                        card = new CardBuilder()
                                                .setSize(Size.LG)
                                                .setTheme(Theme.DANGER)
                                                .addModule(new HeaderModule(new PlainTextElement("操作失败")))
                                                .addModule(new SectionModule(new PlainTextElement("不存在该敏感词")))
                                                .addModule(new ContextModule(Levia.CONTEXT))
                                                .build();

                                        msg.reply(card);
                                    }

                                    break;
                                case "列表":
                                case "list":
                                    card = new CardBuilder()
                                            .setSize(Size.LG)
                                            .setTheme(Theme.INFO)
                                            .addModule(new HeaderModule(new PlainTextElement("操作完成")))
                                            .addModule(new SectionModule(new PlainTextElement(Chat.FILTER_LIST.toString())))
                                            .addModule(new ContextModule(Levia.CONTEXT))
                                            .build();

                                    msg.reply(card);
                            }
                            return;
                        }
                }

                // 未知指令，构建回复卡片
                card = new CardBuilder()
                        .setSize(Size.LG)
                        .setTheme(Theme.WARNING)
                        .addModule(new HeaderModule(new PlainTextElement("未知的指令")))
                        .addModule(new SectionModule(new PlainTextElement("请使用指令 $help 查看更多信息")))
                        .addModule(new ContextModule(Levia.CONTEXT))
                        .build();

                msg.reply(card);
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