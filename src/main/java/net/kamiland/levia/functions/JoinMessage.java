package net.kamiland.levia.functions;

import net.kamiland.levia.settings.Config;
import net.kamiland.levia.settings.Data;
import snw.jkook.entity.User;

import java.util.Random;

public class JoinMessage {
    public static String getJoinMessage(User user) {
        String[] messages = Config.WELCOME_MESSAGES.toArray(new String[0]);
        Random random = new Random();
        int i = random.nextInt(messages.length);

        String onw = Data.getData("onw");
        if (onw == null) {
            onw = "0";
        }

        onw = String.valueOf(Integer.parseInt(onw) + 1);

        String message = messages[i];
        message = message
                .replaceAll("%user%", "(met)" + user.getId() + "(met)")
                .replaceAll("%onw%", onw);

        return message;
    }
}
