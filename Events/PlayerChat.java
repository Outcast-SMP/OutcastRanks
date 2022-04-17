package me.illusion.outcastranks.Events;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Ranks.RankChat;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Ranks.RankPrefix;
import me.illusion.outcastranks.Util.Config.ConfigData;
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
            e.setFormat(formatChat("#MESSAGE", "%2$s", "#NAME", "%1$s", "#RANK", ""));
            return;
        }

        String userRank = RankData.getUserRank(pID);
        String chatColor = IridiumColorAPI.process("&f");

        if (RankChat.rankHasChatColor(userRank)) {
            chatColor = IridiumColorAPI.process(RankChat.getRankChatColor(userRank));
        }

        if (RankPrefix.rankHasPrefix(userRank)) {
            String rankPrefix = IridiumColorAPI.process(RankPrefix.getRankPrefix(userRank));

            e.setFormat(formatChat("#MESSAGE", chatColor + "%2$s", "#NAME", "%1$s", "#RANK", rankPrefix));
            return;
        }

        e.setFormat(formatChat("#MESSAGE", chatColor + "%2$s", "#NAME", "%1$s", "#RANK", ""));
    }

    private String formatChat(String messageHolder, String message, String nameHolder, String name, String rankHolder, String rank) {
        String formatMessage = new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.chat-format");

        if (rankHolder != null) {
            if (formatMessage.contains(rankHolder))
                formatMessage = formatMessage.replace("#RANK", rank);
        }

        if (rankHolder != null) {
            if (formatMessage.contains(nameHolder))
                formatMessage = formatMessage.replace("#NAME", name);
        }

        if (rankHolder != null) {
            if (formatMessage.contains(messageHolder))
                formatMessage = formatMessage.replace("#MESSAGE", message);
        }

        return formatMessage;
    }
}
