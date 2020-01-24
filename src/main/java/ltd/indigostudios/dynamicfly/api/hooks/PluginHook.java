package ltd.indigostudios.dynamicfly.api.hooks;

import ltd.indigostudios.dynamicfly.DynamicFly;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public abstract class PluginHook implements FlightEvaluator {

    private Plugin plugin;

    private static HashMap<Plugin, PluginHook> hooks = new HashMap<>();

    public PluginHook(Plugin plugin) {
        this.plugin = plugin;

        if (!(this instanceof MainHook)) {
            hooks.put(plugin, this);
        }
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public ConfigurationSection getConfig() {
        return DynamicFly.getInstance().getConfig();
    }

    public boolean isRegistered() {
        return hooks.containsKey(plugin);
    }

    public static Collection<PluginHook> getDetectedHooks() {
        return hooks.values();
    }

    public static Set<Plugin> getHookedPlugins() {
        return hooks.keySet();
    }

    public static PluginHook getHook(Plugin plugin) {
        return hooks.get(plugin);
    }

    public static MainHook getMainHook() {
        if (MainHook.mainHook == null) {
            MainHook.mainHook = new MainHook();
        }
        return MainHook.mainHook;
    }

    public static void registerHook(PluginHook hook) {
        hooks.put(hook.getPlugin(), hook);
    }

    public static void unregisterHook(PluginHook pluginHook) {
        hooks.remove(pluginHook.getPlugin());
    }
}
