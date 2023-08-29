package net.kamiland.levia.commands;

import net.kamiland.levia.Levia;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HelpCommand implements CommandExecutor {
    @Override
    public void onCommand(CommandSender sender, Object[] args, Message message) {
        if (message instanceof TextChannelMessage && sender instanceof User) {
            List<String> completions;

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
                    completions = new ArrayList<>();
                    completions.add("$帮助 管理  ————  查看管理员相关指令");
                    completions.add("$帮助 广播  ————  查看广播相关指令");
                    completions.add("$帮助 聊天  ————  查看聊天相关指令");
                    completions.add("        ————————————        ");
                    completions.add("$莱薇娅 重载  ————  重载所有配置文件");

                    msg.reply(getCard(completions));
                } else {
                    completions = new ArrayList<>();

                    switch (args[0].toString().toLowerCase(Locale.ROOT)) {
                        case "管理":
                        case "admin":
                            completions.add("$管理 增加 <@用户>  ————  添加一个用户为管理");
                            completions.add("$管理 移除 <@用户>  ————  移除一个管理员用户");
                            break;
                        case "广播":
                        case "broadcast":
                            completions.add("$广播 <#频道1> (#频道2) (...)  ————  广播信息到指定频道(组)");
                            break;
                        case "聊天":
                        case "chat":
                            completions.add("$聊天 严格  ————  切换严格审查模式");
                            completions.add("$聊天 网址检测  ————  切换网址检测模式");
                            completions.add("$聊天 屏蔽词 增加 <屏蔽词>  ————  增加屏蔽词到屏蔽词列表");
                            completions.add("$聊天 屏蔽词 移除 <屏蔽词>  ————  从屏蔽词列表移除屏蔽词");
                            completions.add("$聊天 屏蔽词 列表  ————  显示屏蔽词列表");
                            break;
                        default:
                            msg.reply(
                              new CardBuilder()
                                      .setSize(Size.LG)
                                      .setTheme(Theme.WARNING)
                                      .addModule(new HeaderModule(new PlainTextElement("未知的指令")))
                                      .addModule(new SectionModule(new PlainTextElement("请使用指令 $help 查看更多信息")))
                                      .addModule(new ContextModule(Levia.CONTEXT))
                                      .build()
                            );
                            return;
                    }

                    msg.reply(getCard(completions));
                }
            } else {
                msg.reply("功能开发中...");
            }
        }
    }

    private static MultipleCardComponent getCard(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);

        MultipleCardComponent card = new CardBuilder()
                .setSize(Size.LG)
                .setTheme(Theme.INFO)
                .addModule(new HeaderModule(new PlainTextElement("指令帮助")))
                .addModule(new SectionModule(new PlainTextElement(sb.toString())))
                .build();

        return card;
    }
}
