package me.illusion.outcastranks.Events;

import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

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
