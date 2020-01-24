package ltd.indigostudios.dynamicfly;

import ltd.indigostudios.dynamicfly.api.hooks.*;
import ltd.indigostudios.dynamicfly.listeners.ClaimChangeListener;
import ltd.indigostudios.dynamicfly.listeners.PlayerJoinListener;
import ltd.indigostudios.dynamicfly.listeners.PlayerMoveListener;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicFly extends JavaPlugin {

    private FlyCmd flyCmd = new FlyCmd();
    private String[] claimDependencies = {"GriefPrevention", "WorldGuard", "BentoBox"};

    private static DynamicFly instance;

    public void onLoad() {
        if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            WorldGuardHook.loadFlag();
        }
    }

    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        loadClaimDependencies();
        getCommand(flyCmd.fly).setExecutor(flyCmd);
        getCommand(flyCmd.fly).setTabCompleter(flyCmd);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new ClaimChangeListener(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "DynamicFly enabled!");
    }

    public static DynamicFly getInstance() {
        return instance;
    }

    private void loadClaimDependencies() {
        PluginHook.registerHook(new MainHook());
        for (int i = 0; i < claimDependencies.length; i++) {
            if (getServer().getPluginManager().isPluginEnabled(claimDependencies[i])) {
                // TODO: some fancy reflection instead?
                switch (i) {
                    case 0:
                        PluginHook.registerHook(new GriefPreventionHook());
                        break;
                    case 1:
                        PluginHook.registerHook(new WorldGuardHook());
                        break;
                    case 2:
                        PluginHook.registerHook(new BentoBoxHook());
                        break;
                }
            }
        }
    }
}
