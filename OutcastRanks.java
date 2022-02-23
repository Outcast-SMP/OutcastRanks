package me.illusion.outcastranks;

import me.illusion.outcastcore.OutcastCore;
import me.illusion.outcastranks.API.Ranks;
import me.illusion.outcastranks.Commands.Commands;
import me.illusion.outcastranks.Util.Communication.LogMe;
import me.illusion.outcastranks.Util.Config.ConfigState;
import me.illusion.outcastranks.Util.Config.CreateConfig;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class OutcastRanks extends JavaPlugin {
    static OutcastRanks instance = null;

    public OutcastCore coreAPI = null;
    public CreateConfig config, rankData, rankWaitingList;

    private Permission permission = null;
    private static Ranks rankAPI;

    @Override
    public void onEnable() {
        instance = this;
        coreAPI = (OutcastCore) Bukkit.getPluginManager().getPlugin("OutcastCore");

        if (coreAPI == null) {
            new LogMe("Unable to find OutcastCore!").Error();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!setupPermissions()) {
            new LogMe("No Vault found!").Error();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new LogMe("Ranks starting...").Warning();

        config = new CreateConfig("config.yml", "Outcast/Ranks");
        rankData = new CreateConfig("ranks.yml", "Outcast/Ranks");
        rankWaitingList = new CreateConfig("ranks-waiting.yml", "Outcast/Ranks");

        new ConfigState(config).configDefaults();
        new ConfigState(rankData).loadConfig();
        new ConfigState(rankWaitingList).loadWaitingList();

        setupCommands();
        setupListeners();
    }

    @Override
    public void onDisable() {
        new LogMe("Ranks stopping...").Warning();

        new ConfigState(rankData).saveConfig();
        new ConfigState(rankWaitingList).saveWaitingList();
    }

    public static OutcastRanks getInstance() {
        return instance;
    }

    public Permission getPermissions() {
        return this.permission;
    }

    public Ranks getRankAPI() {
        return rankAPI;
    }

    private void setupCommands() {
        getCommand("ranks").setExecutor(new Commands());
    }

    private void setupListeners() {
        String packageName = getClass().getPackage().getName();
        for (Class<?> cl : new Reflections(packageName + ".Events").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) cl.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);

        if (rsp != null) {
            permission = rsp.getProvider();
        }

        return (permission != null);
    }
}
