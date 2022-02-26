package me.illusion.outcastranks.Events;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;

public class PlayerDeath implements Listener {
    private static Map<String, Integer> deaths = Maps.newHashMap();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player ply = e.getEntity();
        addDeath(ply);
    }

    public static Map<String, Integer> getDeaths() {
        return deaths;
    }

    private void addDeath(Player ply) {
        if (!playerHasDied(ply)) {
            getDeaths().put(ply.getUniqueId().toString(), 1);
            return;
        }

        int previousDeaths = getDeaths().get(ply.getUniqueId().toString());
        getDeaths().put(ply.getUniqueId().toString(), (previousDeaths + 1));
    }

    private boolean playerHasDied(Player ply) {
        if (getDeaths().isEmpty())
            return false;

        return getDeaths().containsKey(ply.getUniqueId().toString());
    }
}
