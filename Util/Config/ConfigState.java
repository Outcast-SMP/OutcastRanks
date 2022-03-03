package me.illusion.outcastranks.Util.Config;

import me.illusion.outcastranks.Events.PlayerDeath;
import me.illusion.outcastranks.Permissions.RankPermissions;
import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankCommands;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;
import org.bukkit.entity.Player;

public class ConfigState {
    private CreateConfig config;

    public ConfigState(CreateConfig config) {
        this.config = config;
    }

    public void loadConfig() {
        this.config.loadMap("ranks.created", RankData.getRankListMap());
        this.config.loadMap("ranks.users", RankData.getUserRanksMap());
        this.config.loadMap("ranks.commands", RankCommands.getRankCommands());
        this.config.loadMap("ranks.permissions", RankPermissions.getRankPermissionMap());
        this.config.loadMap("ranks.chatcolor", RankChat.getRankChatColors());
        this.config.loadMap("ranks.prefix", RankPrefix.getPrefixes());
    }

    public void saveConfig() {
        if (!RankData.getRankListMap().isEmpty())
            this.config.saveMap("ranks.created", RankData.getRankListMap());

        if (!RankData.getUserRanksMap().isEmpty())
            this.config.saveMap("ranks.users", RankData.getUserRanksMap());

        if (!RankCommands.getRankCommands().isEmpty())
            this.config.saveMap("ranks.commands", RankCommands.getRankCommands());

        if (!RankPermissions.getRankPermissionMap().isEmpty())
            this.config.saveMap("ranks.permissions", RankPermissions.getRankPermissionMap());

        if (!RankChat.getRankChatColors().isEmpty())
            this.config.saveMap("ranks.chatcolor", RankChat.getRankChatColors());

        if (!RankPrefix.getPrefixes().isEmpty())
            this.config.saveMap("ranks.prefix", RankPrefix.getPrefixes());
    }

    public void loadStats() {
        this.config.loadMapInteger("stats.deaths", PlayerDeath.getDeaths());
    }

    public void saveStats() {
        if (!PlayerDeath.getDeaths().isEmpty())
            this.config.saveMap("stats.deaths", PlayerDeath.getDeaths());
    }

    public void configDefaults() {
        this.config.setDefault("ranks.prefix", "&8[&aRanks&8]");
        this.config.setDefault("ranks.default-rank", "none");
        this.config.setDefault("ranks.chat-format", "#RANK #NAME » #MESSAGE");

        this.config.setDefault("ranks.permissions.add", "oc.ranks.add");
        this.config.setDefault("ranks.permissions.remove", "oc.ranks.remove");
        this.config.setDefault("ranks.permissions.edit", "oc.ranks.edit");
        this.config.setDefault("ranks.permissions.reload", "oc.ranks.reload");
    }

    public void saveWaitingList() {
        if (!RankData.getRankWaitingList().isEmpty())
            this.config.saveMap("ranks.waiting", RankData.getRankWaitingList());
    }

    public void loadWaitingList() {
        this.config.loadMap("ranks.waiting", RankData.getRankWaitingList());
    }

    public void loadTablist() {
        this.config.setDefault("teams.0", "a");
        this.config.setDefault("teams.1", "b");
        this.config.setDefault("teams.2", "c");
        this.config.setDefault("teams.3", "d");
        this.config.setDefault("teams.4", "e");
        this.config.setDefault("teams.5", "f");
        this.config.setDefault("teams.6", "g");
        this.config.setDefault("teams.7", "h");
        this.config.setDefault("teams.8", "i");
        this.config.setDefault("teams.9", "j");
        this.config.setDefault("teams.10", "k");
        this.config.setDefault("teams.11", "l");
        this.config.setDefault("teams.12", "m");
        this.config.setDefault("teams.13", "n");
        this.config.setDefault("teams.14", "o");
        this.config.setDefault("teams.15", "p");
        this.config.setDefault("teams.16", "q");
        this.config.setDefault("teams.17", "r");
        this.config.setDefault("teams.18", "s");
        this.config.setDefault("teams.19", "t");
        this.config.setDefault("teams.20", "u");
        this.config.setDefault("teams.21", "v");
        this.config.setDefault("teams.22", "w");
        this.config.setDefault("teams.23", "x");
        this.config.setDefault("teams.24", "y");
        this.config.setDefault("teams.25", "z");
    }
}
