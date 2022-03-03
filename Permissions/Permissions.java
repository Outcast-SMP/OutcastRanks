package me.illusion.outcastranks.Permissions;

import com.google.common.collect.Maps;
import me.illusion.outcastranks.OutcastRanks;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Map;
import java.util.Set;

public class Permissions {
    private static Map<String, PermissionAttachment> playerPerms = Maps.newHashMap();

    private Player ply;
    private String permission;

    private OfflinePlayer offlinePly;

    public Permissions(Player ply) { this.ply = ply; }

    public Permissions(Player ply, String permission) {
        this.ply = ply;
        this.permission = permission;
    }

    public Permissions(OfflinePlayer offlinePly, String permission) {
        this.offlinePly = offlinePly;
        this.permission = permission;
    }

    public static Map<String, PermissionAttachment> getAllPermissions() {
        return playerPerms;
    }

    public void addPlayerPermission() {
        getAllPermissions().get(ply.getUniqueId().toString()).setPermission(permission, true);
    }

    public void addPlayerPermission(String worldName) {
        OutcastRanks.getInstance().getPermissions().playerAdd(worldName, offlinePly, permission);
    }

    public void removePlayerPermission() {
        getAllPermissions().get(ply.getUniqueId().toString()).unsetPermission(permission);
    }

    public void removePlayerPermission(String worldName) {
        OutcastRanks.getInstance().getPermissions().playerRemove(worldName, offlinePly, permission);
    }

    public Set<String> getPlayerPermissions() {
        if (!getAllPermissions().containsKey(ply.getUniqueId().toString()))
            return null;

        return getAllPermissions().get(ply.getUniqueId().toString()).getPermissions().keySet();
    }

    public boolean playerHasPermission() {
        if (!getAllPermissions().containsKey(ply.getUniqueId().toString()))
            return false;

        return getAllPermissions().get(ply.getUniqueId().toString()).getPermissions().containsKey(permission);
    }
}
