package me.illusion.outcastranks.Ranks;

import com.google.common.collect.Maps;
import me.illusion.outcastranks.Util.Communication.LogMe;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RankPrefix {
    private static Map<String, String> rankPrefixes = Maps.newHashMap();

    private String rankName;

    public RankPrefix() {}

    public RankPrefix(String rankName) {
        this.rankName = rankName;
    }

    public static Map<String, String> getPrefixes() {
        return rankPrefixes;
    }

    public static Collection<String> getRankPrefixes() {
        return getPrefixes().values();
    }

    public static Set<String> getRanksWithPrefix() {
        return getPrefixes().keySet();
    }

    public static boolean rankHasPrefix(String rankName) {
        return getPrefixes().containsKey(rankName);
    }

    public static String getRankPrefix(String rankName) {
        if (!RankData.isValidRank(rankName))
            return "";

        if (!rankHasPrefix(rankName))
            return "";

        return getPrefixes().get(rankName);
    }

    public boolean addRankPrefix(String prefix) {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        getPrefixes().put(rankName, prefix);
        return true;
    }

    public boolean removeRankPrefix() {
        if (!RankData.isValidRank(rankName)) {
            new LogMe("Rank " + rankName + " doesn't exist.").Error();
            return false;
        }

        if (!rankHasPrefix(rankName)) {
            new LogMe("Rank " + rankName + " doesn't have a prefix.").Error();
            return false;
        }

        getPrefixes().remove(rankName);
        return true;
    }
}
