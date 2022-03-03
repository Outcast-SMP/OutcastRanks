package me.illusion.outcastranks.Ranks;

import com.google.common.collect.Maps;
import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Permissions.RankPermissions;
import me.illusion.outcastranks.Util.Communication.LogMe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RankData {
    private static Map<String, String> rankList = Maps.newLinkedHashMap();
    private static Map<String, String> userRanks = Maps.newHashMap();

    private static Map<String, String> rankWaiting = Maps.newHashMap();

    private String rankName, playerName;
    private Integer rankIndex;
    private UUID playerID;

    public RankData() {}

    public RankData(String rankName) {
        this.rankName = rankName;
    }

    public RankData(Integer rankIndex) {
        this.rankIndex = rankIndex;
    }

    public RankData(UUID playerID) {
        this.playerID = playerID;
    }

    public RankData(String rankName, UUID playerID) {
        this.rankName = rankName;
        this.playerID = playerID;
    }

    public RankData(String rankName, String playerName) {
        this.rankName = rankName;
        this.playerName = playerName;
    }

    public RankData(String rankName, Integer rankIndex) {
        this.rankName = rankName;
        this.rankIndex = rankIndex;
    }

    public static Map<String, String> getRankListMap() {
        return rankList;
    }

    public static Map<String, String> getUserRanksMap() {
        return userRanks;
    }

    public static Set<String> getRankIndexes() {
        return getRankListMap().keySet();
    }

    public static Collection<String> getRanks() {
        return getRankListMap().values();
    }

    public static Map<String, String> getRankWaitingList() {
        return rankWaiting;
    }

    public static boolean isValidRank(String rankName) {
        return getRanks().contains(rankName);
    }

    public static boolean isValidRank(Integer rankIndex) {
        return getRankIndexes().contains(rankIndex.toString());
    }

    public static String getRankFromIndex(Integer rankIndex) {
        return getRankListMap().get(rankIndex.toString());
    }

    public static String getIndexFromRank(String rankName) {
        for (Map.Entry<String, String> entry : rankList.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(rankName)) {
                return entry.getKey();
            }
        }

        return "";
    }

    public static Set<String> getAllUsersWithARank() {
        return getUserRanksMap().keySet();
    }

    public static String getUsersWithRank(String rankName) {
        for (Map.Entry<String, String> entry : userRanks.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(rankName)) {
                return entry.getKey();
            }
        }

        return "";
    }

    public static String getUserRank(UUID playerID) {
        return getUserRanksMap().get(playerID.toString());
    }

    public static boolean userHasRank(UUID playerID) {
        return getUserRanksMap().containsKey(playerID.toString());
    }

    public boolean addUserToWaitingList() {
        if (!isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        return addUserToWaitingList(playerName, rankName);
    }

    public boolean removeUserFromWaitingList() {
        return removeUserFromWaitingList(playerName);
    }

    public boolean addRank() {
        if (isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " has already been created.").Error();
            return false;
        }

        if (isValidRank(rankIndex)) {
            new LogMe("A rank already has that specified index (" + rankIndex + ").").Error();
            return false;
        }

        return addRank(rankIndex, rankName);
    }

    public boolean removeRank() {
        if (!isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        Integer getRankIndex = Integer.parseInt(getIndexFromRank(rankName));

        if (!new RankCommands(rankName).removeAllRankCommands())
            new LogMe("Unable to remove commands from this rank (" + rankName + ").").Error();

        if (!new RankChat(rankName).removeRankChatColor())
            new LogMe("Unable to remove the chat color from this rank (" + rankName + ").").Error();

        if (!new RankPrefix(rankName).removeRankPrefix())
            new LogMe("Unable to remove the prefix from this rank (" + rankName + ").").Error();


        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (userHasRank(onlinePlayer.getUniqueId())) {
                String getUserRank = getUserRank(onlinePlayer.getUniqueId());

                if (rankName.equalsIgnoreCase(getUserRank)) {
                    userRanks.remove(onlinePlayer.getUniqueId().toString(), getUserRank);
                    new RankPermissions(onlinePlayer).removeRankPermissionsFromUser();
                }
            }
        }

        RankPermissions.removeAllRankPermissions(rankName);

        return removeRank(getRankIndex);
    }

    public boolean addUserToRank() {
        if (!isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        return addUserToRank(playerID, rankName);
    }

    public boolean removeUserFromRank() {
        if (!userHasRank(playerID)) {
            new LogMe("Cannot remove rank from -> " + playerID + ".").Error();
            return false;
        }

        return removeUserFromRank(playerID);
    }

    public boolean addUserToDefaultRank() {
        if (userHasRank(playerID)) {
            return false;
        }

        String getDefaultRank = OutcastRanks.getInstance().config.getItem("ranks.default-rank").toString();

        if (getDefaultRank.equalsIgnoreCase("none") || !isValidRank(getDefaultRank)) {
            new LogMe("Unknown rank " + getDefaultRank + ".").Error();
            return false;
        }

        return new RankData(getDefaultRank, playerID).addUserToRank();
    }

    private boolean addRank(Integer rankIndex, String rankName) {
        try {
            getRankListMap().put(rankIndex.toString(), rankName);
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean addUserToRank(UUID pID, String rankName) {
        try {
            getUserRanksMap().put(pID.toString(), rankName);
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean addUserToWaitingList(String playerName, String rankName) {
        try {
            getRankWaitingList().put(playerName, rankName);
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean removeRank(Integer rankIndex) {
        try {
            getRankListMap().remove(rankIndex.toString());
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean removeUserFromRank(UUID pID) {
        try {
            getUserRanksMap().remove(pID.toString());
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean removeUserFromWaitingList(String playerName) {
        try {
            getRankWaitingList().remove(playerName);
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
