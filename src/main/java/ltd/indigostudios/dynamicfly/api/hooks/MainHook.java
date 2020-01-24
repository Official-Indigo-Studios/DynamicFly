package ltd.indigostudios.dynamicfly.api.hooks;

import ltd.indigostudios.dynamicfly.DynamicFly;
import ltd.indigostudios.dynamicfly.api.FlightManager;
import ltd.indigostudios.dynamicfly.api.enums.FlightPermission;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainHook extends PluginHook implements Configurable {

    public static MainHook mainHook;

    public MainHook() {
        super(DynamicFly.getInstance());
    }

    @Override
    public boolean isRegistered() {
        return mainHook != null;
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

    @Override
    public boolean canFlyHere(Location location, Player player) {
        FlightManager flightManager = new FlightManager(player);
        return flightManager.getFlightType() == FlightPermission.GLOBAL;
    }

    @Override
    public Object getGenericClaimAt(Location location) {
        return null;
    }
}
