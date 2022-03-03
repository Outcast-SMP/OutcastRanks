package me.illusion.outcastranks.API;

import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankCommands;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;
import me.illusion.outcastranks.Util.Communication.LogMe;
import org.bukkit.ChatColor;

import java.util.*;

public class Ranks {
    public RankData getRankData() {
        return new RankData();
    }
    public RankData getRankData(String rankName) {
        return new RankData(rankName);
    }
    public RankData getRankData(Integer rankIndex) {
        return new RankData(rankIndex);
    }
    public RankData getRankData(UUID pID) {
        return new RankData(pID);
    }
    public RankData getRankData(String rankName, UUID pID) {
        return new RankData(rankName, pID);
    }
    public RankData getRankData(String rankName, String playerName) {
        return new RankData(rankName, playerName);
    }
    public RankData getRankData(String rankName, Integer rankIndex) {
        return new RankData(rankName, rankIndex);
    }

    public String getUserRank(UUID playerID)  {
        return RankData.getUserRanksMap().get(playerID.toString());
    }
    public boolean userHasRank(UUID playerID)  {
        return RankData.getUserRanksMap().containsKey(playerID.toString());
    }
    public boolean isValidRank(String rankName) {
        return RankData.getRanks().contains(rankName);
    }
    public boolean isValidRank(Integer rankIndex) {
        return RankData.getRankIndexes().contains(rankIndex.toString());
    }
    public String getIndexFromRank(String rankName) {
        for (Map.Entry<String, String> entry : RankData.getRankListMap().entrySet()) {
            if (entry.getValue().equalsIgnoreCase(rankName)) {
                return entry.getKey();
            }
        }

        return "";
    }
    public Set<String> getAllUsersWithARank() {
        return RankData.getUserRanksMap().keySet();
    }
    public String getUsersWithRank(String rankName) {
        for (Map.Entry<String, String> entry : RankData.getUserRanksMap().entrySet()) {
            if (entry.getValue().equalsIgnoreCase(rankName)) {
                return entry.getKey();
            }
        }

        return "";
    }


    public RankCommands getRankCommands() {
        return new RankCommands();
    }
    public RankCommands getRankCommands(String rankName) {
        return new RankCommands(rankName);
    }

    public boolean rankHasCommands(String rankName) {
        return RankCommands.getRankCommands().containsKey(rankName);
    }
    public String getPlainCommand(String commandName) {
        String commandNameEdit = commandName.replace("/", "");

        if (commandNameEdit.contains(" ")) {
            int spaceIndex = commandNameEdit.indexOf(" ");
            if (spaceIndex != -1) {
                return commandNameEdit.substring(0, spaceIndex);
            }
        }

        return commandNameEdit;
    }
    public String getCommandsForRank(String rankName) {
        if (RankData.isValidRank(rankName)) {
            return RankCommands.getRankCommands().get(rankName);
        }

        return "";
    }
    public boolean commandExistsForRank(String rankName, String commandName) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        if (RankCommands.getRankCommands().isEmpty() || !RankCommands.getRankCommands().containsKey(rankName)) {
            new LogMe("Rank " + rankName + " doesn't have any commands.").Error();
            return false;
        }

        String[] rankCommands = RankCommands.getRankCommands(rankName).split("\\s*,\\s*");

        if (rankCommands == null)
            return false;

        for (String command : rankCommands) {
            if (command.equals(commandName) || command.equals("*"))
                return true;
        }
        return false;
    }


    public RankChat getRankChat() {
        return new RankChat();
    }
    public RankChat getRankChat(String rankName) {
        return new RankChat(rankName);
    }

    public ChatColor getColorFromString(String colorCharacter) {
        return ChatColor.getByChar(colorCharacter.replace("§", ""));
    }
    public String getChatColorFromPrefix(String prefix) {
        return prefix.substring(Math.max(prefix.length() - 2, 0));
    }
    public String getRankChatColor(String rankName) {
        if (RankData.isValidRank(rankName)) {
            return RankChat.getRankChatColors().get(rankName);
        }

        return "";
    }
    public boolean rankHasChatColor(String rankName) {
        return RankChat.getRankChatColors().containsKey(rankName);
    }


    public RankPrefix getRankPrefix() {
        return new RankPrefix();
    }
    public RankPrefix getRankPrefix(String rankName) {
        return new RankPrefix(rankName);
    }

    public boolean rankHasPrefix(String rankName) {
        return RankPrefix.getPrefixes().containsKey(rankName);
    }
    public String getPrefixForRank(String rankName) {
        if (!RankData.isValidRank(rankName))
            return "";

        if (!rankHasPrefix(rankName))
            return "";

        return RankPrefix.getPrefixes().get(rankName);
    }
}
