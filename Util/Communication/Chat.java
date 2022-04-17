package me.illusion.outcastranks.Util.Communication;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Util.Config.ConfigData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {
    private Player ply;
    private CommandSender sender;
    private String message;

    public Chat(CommandSender sender, String message) {
        this.sender = sender;
        this.message = IridiumColorAPI.process(message);
    }

    public Chat(Player ply, String message) {
        this.ply = ply;
        this.message = IridiumColorAPI.process(message);
    }

    public static String getPrefix() {
        return new ConfigData(OutcastRanks.getInstance().config).getStringFromConfig("ranks.prefix") + " ";
    }

    public void message(boolean bWatermark) {
        ply.sendMessage(bWatermark ? getPrefix() + message : message);
    }

    public void messageSender(boolean bWatermark) {
        sender.sendMessage(bWatermark ? getPrefix() + message : message);
    }
}
