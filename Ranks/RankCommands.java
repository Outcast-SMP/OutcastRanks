public class RankCommands {
    private static Map<String, String> rankCommands = Maps.newHashMap();

    private String rankName;

    public RankCommands() {}

    public RankCommands(String rankName) {
        this.rankName = rankName;
    }

    public static Map<String, String> getRankCommands() {
        return rankCommands;
    }

    public static Set<String> getRanksWithCommands() {
        return getRankCommands().keySet();
    }

    public static boolean rankHasCommands(String rankName) {
        return getRankCommands().containsKey(rankName);
    }

    public static String getPlainCommand(String commandName) {
        String commandNameEdit = commandName.replace("/", "");

        if (commandNameEdit.contains(" ")) {
            int spaceIndex = commandNameEdit.indexOf(" ");
            if (spaceIndex != -1) {
                return commandNameEdit.substring(0, spaceIndex);
            }
        }

        return commandNameEdit;
    }

    public static String getRankCommands(String rankName) {
        if (RankData.isValidRank(rankName)) {
            return getRankCommands().get(rankName);
        }

        return "";
    }

    public static boolean commandExistsForRank(String rankName, String commandName) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        if (getRankCommands().isEmpty() || !getRankCommands().containsKey(rankName)) {
            new LogMe("Rank " + rankName + " doesn't have any commands.").Error();
            return false;
        }

        String[] rankCommands = getRankCommands(rankName).split("\\s*,\\s*");

        if (rankCommands == null)
            return false;

        for (String command : rankCommands) {
            if (command.equals(commandName) || command.equals("*"))
                return true;
        }
        return false;
    }

    public boolean addRankCommand(String commandName) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        String getRankCommands = getRankCommands().get(rankName);

        if (getRankCommands != null) {
            if (getRankCommands.contains(commandName + ",")) {
                new LogMe("Rank " + rankName + " already has the command '" + commandName + "'.").Error();
                return false;
            }

            return addCommand(rankName, getRankCommands + commandName);
        }

        return addCommand(rankName, commandName);
    }

    public boolean removeRankCommand(String commandName) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        String getRankCommands = getRankCommands(rankName);

        if (!getRankCommands.contains(commandName + ",")) {
            new LogMe("Rank " + rankName + " doesn't have the command " + commandName + ".").Error();
            return false;
        }

        return removeCommand(rankName, commandName);
    }

    public boolean removeAllRankCommands() {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        if (!getRankCommands().containsKey(rankName)) {
            new LogMe("Rank " + rankName + " doesn't have any commands.").Error();
            return false;
        }

        getRankCommands().remove(rankName);
        return true;
    }

    private boolean addCommand(String rankName, String commandName) {
        try {
            getRankCommands().put(rankName, commandName + ",");
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean removeCommand(String rankName, String commandName) {
        try {
            String commandList = getRankCommands(rankName);
            getRankCommands().put(rankName, commandList.replace(commandName + ",", ""));
            return true;
        }
        catch (ClassCastException | UnsupportedOperationException | NullPointerException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
