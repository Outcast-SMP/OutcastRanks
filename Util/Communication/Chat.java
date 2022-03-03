package me.illusion.outcastranks.Util.Communication;

import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Util.Config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {
    public static String watermark = new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.prefix") + " ";

    private Player ply;
    private CommandSender sender;
    private String message;

    public Chat(CommandSender sender, String message) {
        this.sender = sender;
        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public Chat(Player ply, String message) {
        this.ply = ply;
        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public void message(boolean bWatermark) {
        ply.sendMessage(bWatermark ? watermark + message : message);
    }

    public void messageSender(boolean bWatermark) {
        sender.sendMessage(bWatermark ? watermark + message : message);
    }
}
