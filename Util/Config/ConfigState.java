package me.illusion.outcastranks.Util.Config;

import me.illusion.outcastranks.Permissions.RankPermissions;
import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankCommands;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;

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

    public void configDefaults() {
        this.config.setDefault("ranks.prefix", "&8[&aRanks&8]");
        this.config.setDefault("ranks.default-rank", "none");

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
}
