package me.illusion.outcastranks.Events;

import me.illusion.outcastranks.Permissions.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.UUID;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player ply = e.getPlayer();
        UUID pID = ply.getUniqueId();

        PermissionAttachment attach = Permissions.getAllPermissions().get(pID.toString());
        ply.removeAttachment(attach);
    }
}
