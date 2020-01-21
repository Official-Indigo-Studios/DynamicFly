package ltd.indigostudios.dynamicfly;

import ltd.indigostudios.dynamicfly.api.hooks.evaluators.WGFlightEvaluator;
import ltd.indigostudios.dynamicfly.listeners.ClaimChangeListener;
import ltd.indigostudios.dynamicfly.listeners.PlayerJoinListener;
import ltd.indigostudios.dynamicfly.listeners.PlayerMoveListener;
import ltd.indigostudios.dynamicfly.api.hooks.PluginHook;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicFly extends JavaPlugin {

    private FlyCmd flyCmd = new FlyCmd();
    private String[] claimDependencies = {"GriefPrevention", "WorldGuard", "BentoBox"};

    private static DynamicFly instance;

    public void onLoad() {
        if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            WGFlightEvaluator.loadFlag();
        }
    }

    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        PluginHook.getMainHook();
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
        for (String pluginName : claimDependencies) {
            if (getServer().getPluginManager().isPluginEnabled(pluginName)) {
                new PluginHook(getServer().getPluginManager().getPlugin(pluginName));
            }
        }
    }
}
