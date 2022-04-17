package me.illusion.outcastranks.GUI;

import com.google.common.collect.Maps;
import com.iridium.iridiumcolorapi.IridiumColorAPI;
import me.illusion.outcastranks.Events.PlayerDeath;
import me.illusion.outcastranks.GUI.Extra.TablistPrefix;
import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;
import me.illusion.outcastranks.Util.Communication.LogMe;
import me.illusion.outcastranks.Util.Config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.UUID;

public class Tablist {
    private static Map<String, Integer> tasks = Maps.newHashMap();
    public int taskID;

    private Player ply;
    private Team team;

    public Tablist(Player ply) {
        this.ply = ply;
    }

    public static void displayRanks() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            UUID pID = onlinePlayer.getUniqueId();

            if (RankData.getRanks().isEmpty()) {
                break;
            }

            if (!RankData.userHasRank(pID)) {
                continue;
            }

            String userRank = RankData.getUserRank(pID);
            String rankPrefix = IridiumColorAPI.process(RankPrefix.getRankPrefix(userRank));
            String chatColor = RankChat.getRankChatColor(userRank);

            int rankIndex = Integer.parseInt(RankData.getIndexFromRank(userRank));

            if (chatColor == null || chatColor.equals(""))
                chatColor = IridiumColorAPI.process("&f");
            else
                chatColor = IridiumColorAPI.process(chatColor);

            onlinePlayer.setPlayerListName(rankPrefix + " " + onlinePlayer.getName() + " " + ChatColor.RED + getPlayerDeaths(onlinePlayer));

            for (int i = 0; i < 26; i++) {
                String tabValue = new ConfigData(OutcastRanks.getInstance().tablist).getStringFromConfig("teams." + i);

                if (ChatColor.stripColor(tabValue).equalsIgnoreCase("none")) {
                    continue;
                }

                if (i == rankIndex) {
                    new TablistPrefix(tabValue).setTeam(onlinePlayer, rankPrefix, chatColor);
                }
            }
        }
    }

    public void setID(int id) {
        tasks.put(ply.getUniqueId().toString(), id);
    }

    public int getID() {
        return tasks.get(ply.getUniqueId().toString());
    }

    public boolean hasID() {
        return tasks.containsKey(ply.getUniqueId().toString());
    }

    public void stop() {
        if (!new Tablist(ply).hasID()) {
            return;
        }

        Bukkit.getScheduler().cancelTask(tasks.get(ply.getUniqueId().toString()));
        tasks.remove(ply.getUniqueId().toString());
    }

    public void beginTablist() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(OutcastRanks.getInstance(), new Runnable() {
            int ticks = 0;
            Tablist tablist = new Tablist(ply);

            @Override
            public void run() {
                if (!tablist.hasID())
                    tablist.setID(taskID);

                if (ticks == 18)
                    ticks = 0;

                ticks++;
            }
        }, 0, 10);
    }

    private static int getPlayerDeaths(Player ply) {
        if (PlayerDeath.getDeaths().isEmpty() || !PlayerDeath.getDeaths().containsKey(ply.getUniqueId().toString()))
            return 0;

        return PlayerDeath.getDeaths().get(ply.getUniqueId().toString());
    }
}
