package ltd.indigostudios.dynamicfly.api.hooks;

import ltd.indigostudios.dynamicfly.DynamicFly;
import org.bukkit.configuration.ConfigurationSection;

interface Configurable {

    String mainPath = "plugin-settings";

    String getSettingsPath();

    default <T> T getSetting(String setting, Class<?> clazz) {
        return (T) getConfig().getObject(getSettingsPath() + "." + setting, clazz);
    }

    default void setSetting(String settingPath, Object setting) {
        getConfig().set(getSettingsPath() + "." + settingPath, setting);
    }

    default ConfigurationSection getConfig() {
        return DynamicFly.getInstance().getConfig();
    }
}
