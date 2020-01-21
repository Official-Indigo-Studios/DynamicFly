package ltd.indigostudios.dynamicfly.api.hooks;

import ltd.indigostudios.dynamicfly.DynamicFly;
import org.bukkit.command.CommandSender;

public class MainHook extends PluginHook {

    public static MainHook mainHook;

    public MainHook() {
        super(DynamicFly.getInstance());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean enabled) {
    }

    @Override
    public String getSettingsPath() {
        return "";
    }

    @Override
    public <T> T getSetting(String setting, Class<?> clazz) {
        return (T) getConfig().getObject(setting, clazz);
    }

    @Override
    public void setSetting(String settingPath, Object setting) {
        getConfig().set(settingPath, setting);
    }

    public boolean canReload(CommandSender sender) {
        return sender.hasPermission("dynamicfly.command.fly.reload");
    }
}
