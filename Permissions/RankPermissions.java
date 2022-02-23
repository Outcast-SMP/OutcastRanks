package me.illusion.outcastranks.Permissions;

import com.google.common.collect.Maps;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Util.Communication.LogMe;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class RankPermissions {
    private static Map<String, String> rankPermissions = Maps.newHashMap();

    private Player ply;
    private String rankName;

    public RankPermissions(Player ply) {
        this.ply = ply;
    }

    public RankPermissions(String rankName) {
        this.rankName = rankName;
    }

    public static Map<String, String> getRankPermissionMap() {
        return rankPermissions;
    }

    public static String getRankPermissions(String rankName) {
        if (RankData.isValidRank(rankName)) {
            return getRankPermissionMap().get(rankName);
        }

        return "";
    }

    public static boolean rankHasPermissions(String rankName) {
        return getRankPermissionMap().containsKey(rankName);
    }

    public static boolean rankHasPermission(String rankName, String permission) {
        if (!RankData.isValidRank(rankName)) {
            return false;
        }

        String getRankPermissions = getRankPermissions(rankName);
        return (getRankPermissions != null && getRankPermissions.contains(permission + ","));
    }

    public static void removeAllRankPermissions(String rankName) {
        getRankPermissionMap().remove(rankName);
    }

    public static Set<String> getRanksWithPermissions() {
        return getRankPermissionMap().keySet();
    }

    public boolean addPermissionToRank(String permission) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        if (!getRankPermissionMap().containsKey(rankName)) {
            getRankPermissionMap().put(rankName, permission + ",");

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String getUserRank = RankData.getUserRank(onlinePlayer.getUniqueId());

                if (getUserRank != null && getUserRank.equalsIgnoreCase(rankName)) {
                    new Permissions(onlinePlayer, permission).addPlayerPermission();
                }
            }
            return true;
        }

        String getRankPermissions = getRankPermissionMap().get(rankName);

        if (getRankPermissions.contains(permission + ",")) {
            new LogMe("Rank " + rankName + " already has the permission '" + permission + "'.").Error();
            return false;
        }

        getRankPermissionMap().put(rankName, getRankPermissions + permission + ",");

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String getUserRank = RankData.getUserRank(onlinePlayer.getUniqueId());

            if (getUserRank.equalsIgnoreCase(rankName)) {
                new Permissions(onlinePlayer, permission).addPlayerPermission();
            }
        }
        return true;
    }

    public boolean addRankPermissionsToUser() {
        if (!RankData.userHasRank(ply.getUniqueId())) {
            new LogMe(ply.getName() + " doesn't have a rank.").Error();
            return false;
        }

        String getUserRank = RankData.getUserRank(ply.getUniqueId());

        if (getRankPermissionMap().isEmpty() || !rankHasPermissions(getUserRank)) {
            new LogMe("No permission data for rank " + getUserRank + ".").Error();
            return false;
        }

        String getRankPerms = getRankPermissionMap().get(getUserRank);
        String correctPerms = getRankPerms.replace(",", " ");
        String[] listPerms = correctPerms.trim().split("\\s+");

        for (int i = 0; i <= listPerms.length - 1; i++) {
            if (listPerms[i] != null) {
                //HermesHiveRanks.GetInstance().coreAPI.GetInstance().log("Adding perm -> " + i + " Name -> " + listPerms[i]).Normal();
                new Permissions(ply, listPerms[i]).addPlayerPermission();
            }
        }
        return true;
    }

    public boolean removeRankPermissionsFromUser() {
        if (!RankData.userHasRank(ply.getUniqueId())) {
            new LogMe(ply.getName() + " doesn't have a rank.").Error();
            return false;
        }

        String getUserRank = RankData.getUserRank(ply.getUniqueId());

        if (getRankPermissionMap().isEmpty() || !rankHasPermissions(getUserRank)) {
            new LogMe("No permission data for rank " + getUserRank + ".").Error();
            return false;
        }

        String getRankPerms = getRankPermissionMap().get(getUserRank);
        String correctPerms = getRankPerms.replace(",", " ");
        String[] listPerms = correctPerms.trim().split("\\s+");

        for (int i = 0; i < listPerms.length; i++) {
            //new LogMe("Removing perm -> " + i + " Name -> " + listPerms[i]).Normal();
            new Permissions(ply, listPerms[i]).removePlayerPermission();
        }
        return true;
    }

    public boolean removeRankPermission(String permission) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        String getRankCommands = getRankPermissions(rankName);

        if (!getRankCommands.contains(permission + ",")) {
            new LogMe("Rank " + rankName + " doesn't have the permission " + permission).Error();
            return false;
        }

        getRankPermissionMap().remove(rankName);
        getRankPermissionMap().put(rankName, getRankCommands.replace(permission + ",", ""));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            String getUserRank = RankData.getUserRank(onlinePlayer.getUniqueId());

            if (getUserRank.equalsIgnoreCase(rankName)) {
                new Permissions(onlinePlayer, permission).removePlayerPermission();
            }
        }
        return true;
    }
}
