package net.kamiland.levia.functions;

import snw.jkook.entity.User;
import snw.jkook.entity.channel.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BroadCast {
    private static Map<User, TextChannel> map = new HashMap<>();
    private static Map<User, List<TextChannel>> targetsMap = new HashMap<>();

    public static void waitUser(User user, TextChannel channel, List<TextChannel> targetChannels) {
        map.put(user, channel);
        targetsMap.put(user, targetChannels);
    }

    public static Map<User, TextChannel> getWaitingMap() {
        return map;
    }

    public static List<TextChannel> getBroadCastChannels(User user) {
        return targetsMap.get(user);
    }

    public static void removeUser(User user) {
        map.remove(user);
        targetsMap.remove(user);
    }
}
