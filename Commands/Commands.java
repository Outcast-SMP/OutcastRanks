public class Commands implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length <= 0) {
                new Chat(sender, "&cIncorrect command usage. Please use /ranks [add | remove | edit | reload]").messageSender(true);
                return true;
            }

            String commandName = args[0];

            if (args.length == 1) {
                if (commandName.equals("reload")) {
                    OutcastRanks.getInstance().config.reload();
                    OutcastRanks.getInstance().rankData.reload();
                    new Chat(sender, "&aConfigs reloaded.").messageSender(true);
                    return true;
                }

                new Chat(sender, "&cIncorrect command usage. Please use /ranks [add | remove | edit | reload]").messageSender(true);
                return true;
            }
            return true;
        }

        Player ply = (Player) sender;

        if (args.length <= 0) {
            new Chat(ply, "&cIncorrect command usage. Please use /ranks [add | remove | edit | reload]").message(true);
            return true;
        }

        String commandName = args[0];

        if (args.length == 1) {
            if (commandName.equals("reload")) {
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.reload"))) {
                    new Chat(ply, "&cYou don't have permission to do this.").message(true);
                    return true;
                }

                OutcastRanks.getInstance().config.reload();
                OutcastRanks.getInstance().rankData.reload();
                new Chat(ply, "&aConfigs reloaded.").message(true);
                return true;
            }

            new Chat(ply, "&cIncorrect command usage. Please use /ranks [add | remove | edit | reload]").message(true);
            return true;
        }

        String rankName = args[1];

        switch (commandName) {
            case "add":
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.add"))) {
                    new Chat(ply, "&cYou don't have permission to do this.").message(true);
                    return true;
                }

                if (args[2] == null) {
                    new Chat(ply, "&cIncorrect command usage. Please use /ranks add [command | permission | rankName]").message(true);
                    return true;
                }

                String addSubCommandName = args[2];

                switch (addSubCommandName) {
                    case "command":
                        if (args[3] == null) {
                            new Chat(ply, "&cIncorrect command usage. Please use /ranks add command [commandName]").message(true);
                            return true;
                        }

                        String addCommand = args[3];

                        if (!new RankCommands(rankName).addRankCommand(addCommand)) {
                            new Chat(ply, "&cUnable to add the command '" + addCommand + "'.").message(true);
                            return true;
                        }

                        new Chat(ply, "&a" + rankName + " now has access to the command '" + addCommand + "'.").message(true);
                        break;
                    case "permission":
                        if (args[3] == null) {
                            new Chat(ply, "&cIncorrect command usage. Please use /ranks add permission [permissionName]").message(true);
                            return true;
                        }

                        String addPermission = args[3];

                        if (!new RankPermissions(rankName).addPermissionToRank(addPermission)) {
                            new Chat(ply, "&cUnable to add the permission '" + addPermission + "'.").message(true);
                            return true;
                        }

                        new Chat(ply, "&a" + rankName + " now has access to the permission '" + addPermission + "'.").message(true);
                        break;
                    default:
                        Integer addRankIndex = Integer.parseInt(addSubCommandName);

                        if (!new RankData(rankName, addRankIndex).addRank()) {
                            new Chat(ply, "&cUnable to create the rank '" + rankName + "', Index: " + addRankIndex + ".").message(true);
                            return true;
                        }

                        new Chat(ply, "&aSuccessfully created the rank '" + rankName + "', Index: " + addRankIndex + ".").message(true);
                        break;
                }
                break;
            case "remove":
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.remove"))) {
                    new Chat(ply, "&cYou don't have permission to do this.").message(true);
                    return true;
                }

                if (args[2] == null) {
                    new Chat(ply, "&cIncorrect command usage. Please use /ranks remove [command | permission | rankName]").message(true);
                    return true;
                }

                String removeSubCommandName = args[2];

                switch (removeSubCommandName) {
                    case "command":
                        if (args[3] == null) {
                            new Chat(ply, "&cIncorrect command usage. Please use /ranks remove command [commandName]").message(true);
                            return true;
                        }

                        String removeCommand = args[3];

                        if (!new RankCommands(rankName).removeRankCommand(removeCommand)) {
                            new Chat(ply, "&cUnable to remove the command '" + removeCommand + "'.").message(true);
                            return true;
                        }

                        new Chat(ply, "&a" + rankName + " no longer has access to the command '" + removeCommand + "'.").message(true);
                        break;
                    case "permission":
                        if (args[3] == null) {
                            new Chat(ply, "&cIncorrect command usage. Please use /ranks remove permission [permissionName]").message(true);
                            return true;
                        }

                        String removePermission = args[3];

                        if (!new RankPermissions(rankName).removeRankPermission(removePermission)) {
                            new Chat(ply, "&cUnable to remove the permission '" + removePermission + "'.").message(true);
                            return true;
                        }

                        new Chat(ply, "&a" + rankName + " no longer has the permission '" + removePermission + "'.").message(true);
                        break;
                    default:
                        if (!new RankData(rankName).removeRank()) {
                            new Chat(ply, "&cUnable to remove the rank '" + rankName + "'.").message(true);
                            return true;
                        }

                        new Chat(ply, "&aSuccessfully removed the rank '" + rankName + "'.").message(true);
                        break;
                }
                break;
            case "edit":
                if (!ply.hasPermission(new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.permissions.edit"))) {
                    new Chat(ply, "&cYou don't have permission to do this.").message(true);
                    return true;
                }

                if (args[2] == null) {
                    new Chat(ply, "&cIncorrect command usage. Please use /ranks edit [prefix | chatcolor | rank]").message(true);
                    return true;
                }

                String editSubCommandName = args[2];

                switch (editSubCommandName) {
                    case "prefix":
                        if (args[3] == null) {
                            new Chat(ply, "&cIncorrect command usage. Please use /ranks edit prefix [rankPrefix]").message(true);
                            return true;
                        }

                        String editPrefix = args[3];

                        if (!new RankPrefix(rankName).addRankPrefix(editPrefix)) {
                            new Chat(ply, "&cUnable to edit the prefix for the rank '" + rankName + "'.").message(true);
                            return true;
                        }

                        new Chat(ply, "&aSuccessfully changed the prefix for the rank '" + rankName + "', Prefix: " + editPrefix + "&a.").message(true);
                        break;
                    case "chatcolor":
                        if (args[3] == null) {
                            new Chat(ply, "&cIncorrect command usage. Please use /ranks edit chatcolor [rankChatcolor]").message(true);
                            return true;
                        }

                        String editChatcolor = args[3];

                        if (!new RankChat(rankName).addRankChatColor(editChatcolor)) {
                            new Chat(ply, "&cUnable to edit the chat colour for the rank '" + rankName + "'.").message(true);
                            return true;
                        }

                        new Chat(ply, "&aSuccessfully changed the chat colour for the rank '" + rankName + "', Chat: " + editChatcolor + "[Message]&a.").message(true);
                        break;
                    case "rank":
                        if (args[3] == null) {
                            new Chat(ply, "&cIncorrect command usage. Please use /ranks edit rank [playerName]").message(true);
                            return true;
                        }

                        String editPlayerRank = args[3];
                        Player editPlayerRankObject = Bukkit.getPlayer(editPlayerRank);

                        if (editPlayerRankObject == null) {
                            new Chat(ply, "&cUnable to find the player '" + editPlayerRank + "'. Adding to the waiting list.").message(true);

                            if (!new RankData(rankName, editPlayerRank).addUserToWaitingList()) {
                                new Chat(ply, "&cUnable to add " + editPlayerRank + " to the waiting list.").message(true);
                                return true;
                            }

                            new Chat(ply, "&aSuccessfully added " + editPlayerRank + " to the waiting list.").message(true);
                            return true;
                        }

                        if (RankData.userHasRank(editPlayerRankObject.getUniqueId())) {
                            String oldRank = RankData.getUserRank(editPlayerRankObject.getUniqueId());

                            if (!new RankPermissions(editPlayerRankObject).removeRankPermissionsFromUser()) {
                                new Chat(ply, "&cUnable to remove the old rank permissions from " + editPlayerRank + ".").message(true);
                            }
                        }

                        if (!new RankData(rankName, editPlayerRankObject.getUniqueId()).addUserToRank()) {
                            new Chat(ply, "&cUnable to add " + editPlayerRank + " to the rank '" + rankName + "'.").message(true);
                            return true;
                        }

                        if (!new RankPermissions(ply).addRankPermissionsToUser()) {
                            new Chat(ply, "&cUnable to add the rank permissions to " + editPlayerRank + ".").message(true);
                        }

                        new Chat(ply, "&aSuccessfully added " + editPlayerRank + " to the rank '" + rankName + "'.").message(true);
                        break;
                }
                break;
            default:
                new Chat(ply, "&cIncorrect command usage. Please use /ranks [add | remove | edit | reload]").message(true);
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
                            else if (args[2].equals("permission")) {
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
