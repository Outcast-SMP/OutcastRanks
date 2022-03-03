package me.illusion.outcastranks.GUI.Extra;

import me.illusion.outcastranks.Events.PlayerDeath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistPrefix {
    private Team team;

    public TablistPrefix(String teamName) {
        Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

        Team team = score.getTeam(teamName);

        if (team == null) {
            score.registerNewTeam(teamName);
        }
        this.team = team;
    }

    public void setTeam(Player ply, String prefix, String chatColor) {
        team.setPrefix(prefix + " ");
        setTeamChatColor(team, chatColor);
        team.addEntry(ply.getName());
    }

    private void setTeamChatColor(Team team, String chatColor) {
        if (!chatColor.startsWith("§")) {
            team.setColor(org.bukkit.ChatColor.WHITE);
            return;
        }

        org.bukkit.ChatColor result = this.getColorFromString(chatColor);

        if (result == null) {
            team.setColor(org.bukkit.ChatColor.WHITE);
            return;
        }
        team.setColor(result);
    }

    private org.bukkit.ChatColor getColorFromString(String color) {
        switch (color) {
            case "§1":
                return org.bukkit.ChatColor.DARK_BLUE;
            case "§2":
                return org.bukkit.ChatColor.DARK_GREEN;
            case "§3":
                return org.bukkit.ChatColor.DARK_AQUA;
            case "§4":
                return org.bukkit.ChatColor.DARK_RED;
            case "§5":
                return org.bukkit.ChatColor.DARK_PURPLE;
            case "§6":
                return org.bukkit.ChatColor.GOLD;
            case "§7":
                return org.bukkit.ChatColor.GRAY;
            case "§8":
                return org.bukkit.ChatColor.DARK_GRAY;
            case "§9":
                return org.bukkit.ChatColor.BLUE;
            case "§0":
                return org.bukkit.ChatColor.BLACK;
            case "§a":
                return org.bukkit.ChatColor.GREEN;
            case "§b":
                return org.bukkit.ChatColor.AQUA;
            case "§c":
                return org.bukkit.ChatColor.RED;
            case "§d":
                return org.bukkit.ChatColor.LIGHT_PURPLE;
            case "§e":
                return org.bukkit.ChatColor.YELLOW;
            case "§f":
                return org.bukkit.ChatColor.WHITE;
        }
        return null;
    }
}
