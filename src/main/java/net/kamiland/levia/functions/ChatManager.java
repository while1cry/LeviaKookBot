package net.kamiland.levia.functions;

import net.kamiland.levia.settings.Chat;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {

    public static boolean isNonlegal(String message) {
        message = message.toLowerCase(Locale.ROOT);
        String rawMessage = message;
        double score = 0.0;
        double highest = 50.0;

        for (String word : Chat.WHITELIST) {
            message = message.replaceAll(word, "");
        }

        message = message.replaceAll("[\\p{Punct}\\s]+", "");

        int count = 0;
        for (String word : Chat.FILTER_LIST) {
            while (message.contains(word)) {
                count++;
                message = message.replaceFirst(word, "");
            }
        }

        score += count * 20.0;

        if (Chat.URL_CHECK) {
            if (containsUrlOrIp(message) || containsUrlOrIp(rawMessage)) {
                score += 30.0;
            }
        }

        if (Chat.STRICT_MODE) {
            return score > 0.0;
        } else {
            return score >= highest;
        }
    }

    private static boolean containsUrlOrIp(String text) {
        String urlPattern = "\\b(?:https?|ftp)://\\S+\\b";
        String ipPattern = "\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b";

        Pattern urlRegex = Pattern.compile(urlPattern);
        Pattern ipRegex = Pattern.compile(ipPattern);

        Matcher urlMatcher = urlRegex.matcher(text);
        Matcher ipMatcher = ipRegex.matcher(text);

        return urlMatcher.find() || ipMatcher.find();
    }
}
