public class PlayerChat implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player ply = e.getPlayer();
        UUID pID = ply.getUniqueId();

        if (!RankData.userHasRank(pID)) {
            return;
        }

        String userRank = RankData.getUserRank(pID);
        String chatColor = ChatColor.translateAlternateColorCodes('&', "&f");

        if (RankChat.rankHasChatColor(userRank)) {
            chatColor = ChatColor.translateAlternateColorCodes('&', RankChat.getRankChatColor(userRank));
        }

        if (RankPrefix.rankHasPrefix(userRank)) {
            String rankPrefix = ChatColor.translateAlternateColorCodes('&', RankPrefix.getRankPrefix(userRank));

            e.setFormat(rankPrefix + " " + "%1$s:" + chatColor + " %2$s");
            return;
        }
        e.setFormat("%1$s:" + chatColor + " %2$s");
    }
}
