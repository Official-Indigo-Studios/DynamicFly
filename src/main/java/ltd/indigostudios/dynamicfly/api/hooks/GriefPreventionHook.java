package ltd.indigostudios.dynamicfly.api.hooks;

import ltd.indigostudios.dynamicfly.api.FlightManager;
import ltd.indigostudios.dynamicfly.api.enums.FlightPermission;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GriefPreventionHook extends PluginHook implements Configurable {

    public GriefPreventionHook() {
        super(GriefPrevention.instance);
    }

    @Override
    public String getSettingsPath() {
        return mainPath + "." + getPlugin().getName();
    }

    @Override
    public boolean canFlyHere(Location location, Player player) {
        FlightManager flightManager = new FlightManager(player);
        if (flightManager.getFlightType() == FlightPermission.GLOBAL) return true;
        if (flightManager.getFlightType() == FlightPermission.NONE) return false;
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, null);
        if (claim != null) {
            // manage build containers access
            if (flightManager.getFlightType() == FlightPermission.TRUSTED_CLAIMS) {
                return hasPerms(claim, player);
            } else if (flightManager.getFlightType() == FlightPermission.OWN_CLAIMS) {
                return claim.ownerID.equals(player.getUniqueId());
            }
        }
        return false;
    }

    @Override
    public Object getGenericClaimAt(Location location) {
        return GriefPrevention.instance.dataStore.getClaimAt(location, false, null);
    }

    private boolean hasPerms(Claim claim, Player player) {
        String perm = getSetting("lowest-perms-to-fly", String.class);
        // if they return null, they have permission
        switch (perm.toUpperCase()) {
            case "MANAGE":
                return claim.allowGrantPermission(player) == null;
            case "BUILD":
                // not entirely sure why it's material specific when I see nothing involving it in GP
                return claim.allowBuild(player, Material.GRASS_BLOCK) == null;
            case "CONTAINERS":
                return claim.allowContainers(player) == null;
            case "ACCESS":
                return claim.allowAccess(player) == null;
            default:
                return false;
        }
    }
}
