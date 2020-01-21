package ltd.indigostudios.dynamicfly.api;

import ltd.indigostudios.dynamicfly.api.enums.FlightPermission;
import ltd.indigostudios.dynamicfly.api.hooks.evaluators.FlightEvaluator;
import ltd.indigostudios.dynamicfly.DynamicFly;
import ltd.indigostudios.dynamicfly.api.hooks.PluginHook;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class FlightManager {

    private Player player;

    private static NamespacedKey wantsToFlyMeta = new NamespacedKey(DynamicFly.getInstance(), "wantFly");

    public FlightManager(Player player) {
        this.player = player;
    }

    public FlightPermission getFlightType() {
        for (FlightPermission perm : FlightPermission.values()) {
            if (player.hasPermission(perm.getFullPerm())) {
                return perm;
            }
        }
        return FlightPermission.NONE;
    }

    public boolean canFlyHere(Location location) {
        for (PluginHook pluginHook : PluginHook.getDetectedHooks()) {
            FlightEvaluator fe = pluginHook.getFlightEvaluator();
            if (fe.canFlyHere(location, player)) {
                return true;
            }
        }
        return false;
    }

    public void toggleFly(boolean canFly) {
        player.setAllowFlight(canFly);
        if (!player.isOnGround()) {
            player.setFlying(canFly);
        }
    }

    public boolean isFlightToggledOn() {
        return player.getAllowFlight();
    }

    public boolean wantsToFly() {
        return player.getPersistentDataContainer().has(wantsToFlyMeta, PersistentDataType.INTEGER);
    }

    public void setWantsToFly(boolean wants) {
        // does not want to fly anymore
        if (wantsToFly() && !wants) {
            player.getPersistentDataContainer().remove(wantsToFlyMeta);
        } else if (!wantsToFly() && wants) { // wants to fly
            player.getPersistentDataContainer().set(wantsToFlyMeta, PersistentDataType.INTEGER, 1);
        }
    }
}
