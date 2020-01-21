package ltd.indigostudios.dynamicfly.api.hooks;

import ltd.indigostudios.dynamicfly.DynamicFly;
import ltd.indigostudios.dynamicfly.api.hooks.evaluators.FlightEvaluator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class PluginHook {

    private Plugin plugin;
    private FlightEvaluator flightEvaluator;

    private static HashMap<Plugin, PluginHook> hooks = new HashMap<>();

    public PluginHook(Plugin plugin) {
        this.plugin = plugin;

        if (!(this instanceof MainHook)) {
            hooks.put(plugin, this);
        }
        flightEvaluator = FlightEvaluator.setFlightEvaluator(plugin);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public ConfigurationSection getConfig() {
        return DynamicFly.getInstance().getConfig();
    }

    public boolean isEnabled() {
        return getConfig().getBoolean(getSettingsPath() + ".enabled");
    }

    public void setEnabled(boolean enabled) {
        getConfig().set(getSettingsPath() + ".enabled", enabled);
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

    public String getSettingsPath() {
        return "plugin-settings." + plugin.getName();
    }

    public <T> T getSetting(String setting, Class<?> clazz) {
        return (T) getConfig().getObject(getSettingsPath() + "." + setting, clazz);
    }

    public void setSetting(String settingPath, Object setting) {
        getConfig().set(getSettingsPath() + "." + settingPath, setting);
    }

    public FlightEvaluator getFlightEvaluator() {
        return flightEvaluator;
    }

    public static MainHook getMainHook() {
        if (MainHook.mainHook == null) {
            MainHook.mainHook = new MainHook();
        }
        return MainHook.mainHook;
    }
}
