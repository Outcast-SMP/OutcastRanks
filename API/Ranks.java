package me.illusion.outcastranks.API;

import com.google.common.collect.Maps;
import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Permissions.RankPermissions;
import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankCommands;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;
import me.illusion.outcastranks.Util.Communication.Chat;
import me.illusion.outcastranks.Util.Config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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

    public RankCommands getRankCommands() {
        return new RankCommands();
    }

    public RankCommands getRankCommands(String rankName) {
        return new RankCommands(rankName);
    }

    public RankChat getRankChat() {
        return new RankChat();
    }

    public RankChat getRankChat(String rankName) {
        return new RankChat(rankName);
    }

    public RankPrefix getRankPrefix() {
        return new RankPrefix();
    }

    public RankPrefix getRankPrefix(String rankName) {
        return new RankPrefix(rankName);
    }
}
