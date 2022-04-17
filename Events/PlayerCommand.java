package me.illusion.outcastranks.Events;

import me.illusion.outcastranks.Ranks.RankCommands;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Util.Communication.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.UUID;

public class PlayerCommand implements Listener {
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        Player ply = e.getPlayer();
        UUID pID = ply.getUniqueId();

        String commandName = e.getMessage();
        String stripCommand = RankCommands.getPlainCommand(commandName);

        if (stripCommand == null)
            return;

        if (!RankData.userHasRank(pID) && !ply.isOp()) {
            new Chat(ply, "&cYou need to have a rank to access commands.").message(true);
            e.setCancelled(true);
            return;
        }

        String userRank = RankData.getUserRank(pID);

        if (!ply.isOp()) {
            if (!RankCommands.commandExistsForRank(userRank, stripCommand)) {
                new Chat(ply, "&cYour rank doesn't have access to this command.").message(true);
                e.setCancelled(true);
                return;
            }
            e.setCancelled(false);
        }
    }
}
