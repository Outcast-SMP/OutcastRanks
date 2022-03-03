package me.illusion.outcastranks.Ranks;

import com.google.common.collect.Maps;
import me.illusion.outcastranks.Util.Communication.LogMe;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import java.util.Map;

public class RankChat {
    private static Map<String, String> rankChatColors = Maps.newHashMap();

    private String rankName;

    public RankChat() {}

    public RankChat(String rankName) {
        this.rankName = rankName;
    }

    public static Map<String, String> getRankChatColors() {
        return rankChatColors;
    }

    public static ChatColor getColorFromString(String colorCharacter) {
        return ChatColor.getByChar(colorCharacter.replace("§", ""));
    }

    public static String getChatColorFromPrefix(String prefix) {
        return prefix.substring(Math.max(prefix.length() - 2, 0));
    }

    public static String getRankChatColor(String rankName) {
        if (RankData.isValidRank(rankName)) {
            return getRankChatColors().get(rankName);
        }

        return "";
    }

    public static boolean rankHasChatColor(String rankName) {
        return getRankChatColors().containsKey(rankName);
    }

    public boolean addRankChatColor(String chatColor) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        String rankChatColor = chatColor.replace("§", "&");

        if (!validColor(rankChatColor)) {
            new LogMe("Invalid color " + chatColor).Error();
            return false;
        }

        return addColor(rankName, chatColor);
    }

    public boolean removeRankChatColor() {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        return removeColor(rankName);
    }

    public void setTeamChatColor(Team team, String chatColor) {
        if (!chatColor.startsWith("§")) {
            team.setColor(org.bukkit.ChatColor.WHITE);
            return;
        }

        ChatColor result = getColorFromString(chatColor);

        if (result == null) {
            team.setColor(org.bukkit.ChatColor.WHITE);
            return;
        }
        team.setColor(result);
    }

    private boolean addColor(String rankName, String color) {
        try {
            getRankChatColors().put(rankName, color);
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean removeColor(String rankName) {
        try {
            getRankChatColors().remove(rankName);
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean validColor(String color) {
        if (color.startsWith("&")) {
            return color.contains("0") || color.contains("1") || color.contains("2")
                    || color.contains("3") || color.contains("4") || color.contains("5")
                    || color.contains("6") || color.contains("7") || color.contains("8")
                    || color.contains("9") || color.contains("a") || color.contains("b")
                    || color.contains("c") || color.contains("d") || color.contains("e")
                    || color.contains("f") || color.contains("k") || color.contains("l")
                    || color.contains("m") || color.contains("n") || color.contains("o")
                    || color.contains("r");
        }

        return false;
    }
}
