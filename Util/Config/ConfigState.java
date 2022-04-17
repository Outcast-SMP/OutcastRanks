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

    public void configChatMessages() {
        this.config.setDefault("chat.error.incorrect-command-use", "&cIncorrect command usage. Please use /ranks [add | remove | edit | reload]");
        this.config.setDefault("chat.error.insufficient-permission", "&cYou don't have permission to do this.");

        this.config.setDefault("chat.success.reload", "&aConfigs reloaded.");

        this.config.setDefault("chat.error.add-1.incorrect-command-use", "&cIncorrect command usage. Please use /ranks add [command | permission | rankName]");
        this.config.setDefault("chat.error.add-2.incorrect-command-use", "&cIncorrect command usage. Please use /ranks add command [commandName]");
        this.config.setDefault("chat.error.add-3.incorrect-command-use", "&cIncorrect command usage. Please use /ranks add permission [permissionName]");

        this.config.setDefault("chat.error.remove-1.incorrect-command-use", "&cIncorrect command usage. Please use /ranks remove [command | permission | rankName]");
        this.config.setDefault("chat.error.remove-2.incorrect-command-use", "&cIncorrect command usage. Please use /ranks remove command [commandName]");
        this.config.setDefault("chat.error.remove-3.incorrect-command-use", "&cIncorrect command usage. Please use /ranks remove permission [permissionName]");

        this.config.setDefault("chat.error.edit-1.incorrect-command-use", "&cIncorrect command usage. Please use /ranks edit [prefix | chatcolor | rank]");
        this.config.setDefault("chat.error.edit-2.incorrect-command-use", "&cIncorrect command usage. Please use /ranks edit prefix [rankPrefix]");
        this.config.setDefault("chat.error.edit-3.incorrect-command-use", "&cIncorrect command usage. Please use /ranks edit chatcolor [rankChatcolor]");
        this.config.setDefault("chat.error.edit-4.incorrect-command-use", "&cIncorrect command usage. Please use /ranks edit rank [playerName]");

        this.config.setDefault("chat.error.add-command.message", "&cUnable to add the command '#COMMAND'.");
        this.config.setDefault("chat.error.remove-command.message", "&cUnable to remove the command '#COMMAND'.");
        this.config.setDefault("chat.success.add-command.message", "&a#RANK now has access to the command '#COMMAND'.");
        this.config.setDefault("chat.success.remove-command.message", "&a#RANK no longer has access to the command '#COMMAND'.");

        this.config.setDefault("chat.error.add-permission.message", "&cUnable to add the permission '#PERMISSION'.");
        this.config.setDefault("chat.error.remove-permission.message", "&cUnable to remove the permission '#PERMISSION'.");
        this.config.setDefault("chat.success.add-permission.message", "&a#RANK now has access to the permission '#PERMISSION'.");
        this.config.setDefault("chat.success.remove-permission.message", "&a#RANK no longer has the permission '#PERMISSION'.");

        this.config.setDefault("chat.error.add-rank.message", "&cUnable to create the rank '#RANK', Index: #INDEX.");
        this.config.setDefault("chat.error.remove-rank.message", "&cUnable to remove the rank '#RANK'.");
        this.config.setDefault("chat.success.add-rank.message", "&aSuccessfully created the rank '#RANK', Index: #INDEX.");
        this.config.setDefault("chat.success.remove-rank.message", "&aSuccessfully removed the rank '#RANK'.");

        this.config.setDefault("chat.error.edit-prefix.message", "&cUnable to edit the prefix for the rank '#RANK'.");
        this.config.setDefault("chat.success.edit-prefix.message", "&aSuccessfully changed the prefix for the rank '#RANK', Prefix: #PREFIX&a.");

        this.config.setDefault("chat.error.edit-chatcolor.message", "&cUnable to edit the chat colour for the rank '#RANK'.");
        this.config.setDefault("chat.success.edit-chatcolor.message", "&aSuccessfully changed the chat colour for the rank '#RANK', Chat: #COLOR[Message]&a.");

        this.config.setDefault("chat.info.edit-rank.waiting-list.message", "&cUnable to find the player '#PLAYER'. Adding to the waiting list...");
        this.config.setDefault("chat.error.edit-rank.waiting-list.message", "&cUnable to add #PLAYER to the waiting list.");
        this.config.setDefault("chat.success.edit-rank.waiting-list.message", "&aSuccessfully added #PLAYER to the waiting list.");

        this.config.setDefault("chat.error.edit-rank.remove-permissions.message", "&cUnable to remove the old rank permissions from #PLAYER.");
        this.config.setDefault("chat.error.edit-rank.add-permissions.message", "&cUnable to add the rank permissions to #PLAYER.");

        this.config.setDefault("chat.error.edit-rank.message", "&cUnable to add #PLAYER to the rank '#RANK'.");
        this.config.setDefault("chat.success.edit-rank.message", "&aSuccessfully added #PLAYER to the rank '#RANK'.");

    }
}
