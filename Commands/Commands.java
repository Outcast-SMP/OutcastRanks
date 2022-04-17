package me.illusion.outcastranks.Commands;

import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Permissions.RankPermissions;
import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankCommands;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;
import me.illusion.outcastranks.Util.Communication.Chat;
import me.illusion.outcastranks.Util.Communication.Format;
import me.illusion.outcastranks.Util.Communication.LogMe;
import me.illusion.outcastranks.Util.Config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String errorMsg = new ConfigData(OutcastRanks.getInstance().chatMessages)
                .getStringFromConfig("chat.error.incorrect-command-use");

        if (!(sender instanceof Player)) {
            if (args.length <= 0) {
                new Chat(sender, errorMsg).messageSender(true);
                return true;
            }

            String commandName = args[0];

            if (args.length == 1) {
                if (commandName.equals("reload")) {
                    OutcastRanks.getInstance().config.reload();
                    OutcastRanks.getInstance().rankData.reload();
                    OutcastRanks.getInstance().tablist.reload();
                    OutcastRanks.getInstance().chatMessages.reload();

                    new Chat(sender, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.success.reload")).message(true);
                    return true;
                }

                new Chat(sender, errorMsg).messageSender(true);
                return true;
            }
            return true;
        }

        Player ply = (Player) sender;

        if (args.length <= 0) {
            new Chat(ply, errorMsg).message(true);
            return true;
        }

        String commandName = args[0];

        if (args.length == 1) {
            if (commandName.equals("reload")) {
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.reload"))) {
                    new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.error.insufficient-permission")).message(true);
                    return true;
                }

                OutcastRanks.getInstance().config.reload();
                OutcastRanks.getInstance().rankData.reload();
                OutcastRanks.getInstance().tablist.reload();
                OutcastRanks.getInstance().chatMessages.reload();

                new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                        .getStringFromConfig("chat.success.reload")).message(true);
                return true;
            }

            new Chat(ply, errorMsg).message(true);
            return true;
        }

        String rankName = args[1];

        switch (commandName) {
            case "add":
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.add"))) {
                    new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.error.insufficient-permission")).message(true);
                    return true;
                }

                if (args[2] == null) {
                    new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.error.add-1.incorrect-command-use")).message(true);
                    return true;
                }

                String addSubCommandName = args[2];

                switch (addSubCommandName) {
                    case "command":
                        if (args[3] == null) {
                            new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.add-2.incorrect-command-use")).message(true);
                            return true;
                        }

                        String addCommand = args[3];

                        if (!new RankCommands(rankName).addRankCommand(addCommand)) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.add-command.message"),
                                    "#COMMAND", addCommand)).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.add-command.message"),
                                "#RANK", rankName,
                                "#COMMAND", addCommand)).message(true);
                        break;
                    case "permission":
                        if (args[3] == null) {
                            new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.add-3.incorrect-command-use")).message(true);
                            return true;
                        }

                        String addPermission = args[3];

                        if (!new RankPermissions(rankName).addPermissionToRank(addPermission)) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.add-permission.message"),
                                    "#PERMISSION", addPermission)).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.add-permission.message"),
                                "#RANK", rankName,
                                "#PERMISSION", addPermission)).message(true);
                        break;
                    default:
                        Integer addRankIndex = Integer.parseInt(addSubCommandName);

                        if (!new RankData(rankName, addRankIndex).addRank()) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.add-rank.message"),
                                    "#RANK", rankName,
                                    "#INDEX", addRankIndex.toString())).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.add-rank.message"),
                                "#RANK", rankName,
                                "#INDEX", addRankIndex.toString())).message(true);
                        break;
                }
                break;
            case "remove":
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.remove"))) {
                    new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.error.insufficient-permission")).message(true);
                    return true;
                }

                if (args[2] == null) {
                    new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.error.remove-1.incorrect-command-use")).message(true);
                    return true;
                }

                String removeSubCommandName = args[2];

                switch (removeSubCommandName) {
                    case "command":
                        if (args[3] == null) {
                            new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.remove-2.incorrect-command-use")).message(true);
                            return true;
                        }

                        String removeCommand = args[3];

                        if (!new RankCommands(rankName).removeRankCommand(removeCommand)) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.remove-command.message"),
                                    "#COMMAND", removeCommand)).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.remove-command.message"),
                                "#RANK", rankName,
                                "#COMMAND", removeCommand)).message(true);
                        break;
                    case "permission":
                        if (args[3] == null) {
                            new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.remove-3.incorrect-command-use")).message(true);
                            return true;
                        }

                        String removePermission = args[3];

                        if (!new RankPermissions(rankName).removeRankPermission(removePermission)) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.remove-permission.message"),
                                    "#PERMISSION", removePermission)).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.remove-permission.message"),
                                "#RANK", rankName,
                                "#PERMISSION", removePermission)).message(true);
                        break;
                    default:
                        if (!new RankData(rankName).removeRank()) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.remove-rank.message"),
                                    "#RANK", rankName)).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.remove-rank.message"),
                                "#RANK", rankName)).message(true);
                        break;
                }
                break;
            case "edit":
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.edit"))) {
                    new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.error.insufficient-permission")).message(true);
                    return true;
                }

                if (args[2] == null) {
                    new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                            .getStringFromConfig("chat.error.edit-1.incorrect-command-use")).message(true);
                    return true;
                }

                String editSubCommandName = args[2];

                switch (editSubCommandName) {
                    case "prefix":
                        if (args[3] == null) {
                            new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.edit-2.incorrect-command-use")).message(true);
                            return true;
                        }

                        String editPrefix = args[3];

                        if (!new RankPrefix(rankName).addRankPrefix(editPrefix)) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.edit-prefix.message"),
                                    "#RANK", rankName)).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.edit-prefix.message"),
                                "#RANK", rankName,
                                "#PREFIX", editPrefix)).message(true);
                        break;
                    case "chatcolor":
                        if (args[3] == null) {
                            new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.edit-3.incorrect-command-use")).message(true);
                            return true;
                        }

                        String editChatcolor = args[3];

                        if (!new RankChat(rankName).addRankChatColor(editChatcolor)) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.edit-chatcolor.message"),
                                    "#RANK", rankName)).message(true);
                            return true;
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.edit-chatcolor.message"),
                                "#RANK", rankName,
                                "#COLOR", editChatcolor)).message(true);
                        break;
                    case "rank":
                        if (args[3] == null) {
                            new Chat(ply, new ConfigData(OutcastRanks.getInstance().chatMessages)
                                    .getStringFromConfig("chat.error.edit-4.incorrect-command-use")).message(true);
                            return true;
                        }

                        String editPlayerRank = args[3];
                        Player editPlayerRankObject = Bukkit.getPlayer(editPlayerRank);

                        if (editPlayerRankObject == null) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.info.edit-rank.waiting-list.message"),
                                    "#PLAYER", editPlayerRank)).message(true);

                            if (!new RankData(rankName, editPlayerRank).addUserToWaitingList()) {
                                new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                                .getStringFromConfig("chat.error.edit-rank.waiting-list.message"),
                                        "#PLAYER", editPlayerRank)).message(true);
                                return true;
                            }

                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.success.edit-rank.waiting-list.message"),
                                    "#PLAYER", editPlayerRank)).message(true);
                            return true;
                        }

                        if (RankData.userHasRank(editPlayerRankObject.getUniqueId())) {
                            String oldRank = RankData.getUserRank(editPlayerRankObject.getUniqueId());

                            if (!new RankPermissions(editPlayerRankObject).removeRankPermissionsFromUser()) {
                                new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                                .getStringFromConfig("chat.error.edit-rank.remove-permissions.message"),
                                        "#PLAYER", editPlayerRank)).message(true);
                            }
                        }

                        if (!new RankData(rankName, editPlayerRankObject.getUniqueId()).addUserToRank()) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.edit-rank.message"),
                                    "#PLAYER", editPlayerRank,
                                    "#RANK", rankName)).message(true);
                            return true;
                        }

                        if (!new RankPermissions(ply).addRankPermissionsToUser()) {
                            new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                            .getStringFromConfig("chat.error.edit-rank.add-permissions.message"),
                                    "#PLAYER", editPlayerRank)).message(true);
                        }

                        new Chat(ply, Format.formatString(new ConfigData(OutcastRanks.getInstance().chatMessages)
                                        .getStringFromConfig("chat.success.edit-rank.message"),
                                "#PLAYER", editPlayerRank,
                                "#RANK", rankName)).message(true);
                        break;
                }
                break;
            default:
                new Chat(ply, errorMsg).message(true);
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String commandName = command.getName();

        if (commandName.equals("ranks")) {
            String subCommandName = args[0];
            List<String> itemList = new ArrayList<>();

            if (args.length == 1) {
                itemList.add("add");
                itemList.add("remove");
                itemList.add("edit");
                itemList.add("reload");
            }

            List<String> subItemList = new ArrayList<>();

            switch (subCommandName) {
                case "add":
                    switch (args.length) {
                        case 2:
                            if (!RankData.getRanks().isEmpty()) {
                                subItemList.addAll(RankData.getRanks());
                            }
                            else {
                                subItemList.add("[Rank Name]");
                            }
                            break;
                        case 3:
                            if (!RankData.getRanks().isEmpty()) {
                                subItemList.add("command");
                                subItemList.add("permission");
                            }
                            subItemList.add("[Rank Index]");
                            break;
                        case 4:
                            if (args[2].equals("command")) {
                                subItemList.add("add");
                                subItemList.add("remove");
                                subItemList.add("edit");
                                subItemList.add("[Command Prefix]");
                            }
                            else if (args[2].equals("permission")) {
                                subItemList.add("[e.g. minecraft.command.gamemode]");
                            }
                            break;
                    }
                    return subItemList;
                case "remove":
                    switch (args.length) {
                        case 2:
                            if (!RankData.getRanks().isEmpty()) {
                                subItemList.addAll(RankData.getRanks());
                            }
                            else {
                                subItemList.add("[No Ranks]");
                            }
                            break;
                        case 3:
                            if (!RankData.getRanks().isEmpty()) {
                                subItemList.add("command");
                                subItemList.add("permission");
                            }
                            break;
                        case 4:
                            if (args[2].equals("command")) {
                                String[] rankCommands = RankCommands.getRankCommands(args[1]).split("\\s*,\\s*");

                                if (rankCommands != null) {
                                    subItemList.addAll(Arrays.asList(rankCommands));
                                }
                                else {
                                    subItemList.add("[No Commands]");
                                }
                            }
                            else if (args[2].equals("permission")) {
                                String[] rankPermissions = RankPermissions.getRankPermissions(args[1]).split("\\s*,\\s*");

                                if (rankPermissions != null) {
                                    subItemList.addAll(Arrays.asList(rankPermissions));
                                }
                                else {
                                    subItemList.add("[No Permissions]");
                                }
                            }
                            break;
                    }
                    return subItemList;
                case "edit":
                    switch (args.length) {
                        case 2:
                            if (!RankData.getRanks().isEmpty()) {
                                subItemList.addAll(RankData.getRanks());
                            }
                            else {
                                subItemList.add("[No Ranks]");
                            }
                            break;
                        case 3:
                            if (!RankData.getRanks().isEmpty()) {
                                subItemList.add("prefix");
                                subItemList.add("chatcolor");
                                subItemList.add("rank");
                            }
                            break;
                        case 4:
                            if (args[2].equals("prefix")) {
                                subItemList.add("[Rank Prefix]");
                            }
                            else if (args[2].equals("chatcolor")) {
                                subItemList.add("&[Color Code]");
                                subItemList.add("none");
                            }
                            else if (args[2].equals("rank")) {
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    subItemList.add(onlinePlayers.getName());
                                }
                            }
                            break;
                    }
                    return subItemList;
            }
            return itemList;
        }
        return null;
    }
}
