package me.illusion.outcastranks.Events;

import me.illusion.outcastranks.OutcastRanks;
import me.illusion.outcastranks.Permissions.Permissions;
import me.illusion.outcastranks.Permissions.RankPermissions;
import me.illusion.outcastranks.Ranks.RankData;
import me.illusion.outcastranks.Util.Communication.Chat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player ply = e.getPlayer();
        UUID pID = ply.getUniqueId();

        PermissionAttachment attach = ply.addAttachment(OutcastRanks.getInstance());
        Permissions.getAllPermissions().put(pID.toString(), attach);

        if (RankData.getRankWaitingList().containsKey(ply.getName())) {
            if (!new RankData(RankData.getRankWaitingList().get(ply.getName()), pID).addUserToRank()) {
                new Chat(ply, "&cUnable to add " + ply.getName() + " to the rank '" + RankData.getRankWaitingList().get(pID.toString()) + "'.").message(true);
            }
            else {
                if (!new RankData(RankData.getRankWaitingList().get(pID.toString()), ply.getName()).removeUserFromWaitingList()) {
                    new Chat(ply, "&cUnable to remove " + ply.getName() + " from the rank waiting list.").message(true);
                }
            }
        }

        new RankData(pID).addUserToDefaultRank();
        new RankPermissions(ply).addRankPermissionsToUser();
    }
}
