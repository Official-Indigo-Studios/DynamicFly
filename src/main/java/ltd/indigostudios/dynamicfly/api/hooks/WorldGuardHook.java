package ltd.indigostudios.dynamicfly.api.hooks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import ltd.indigostudios.dynamicfly.api.FlightManager;
import ltd.indigostudios.dynamicfly.api.enums.FlightPermission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Set;

public class WorldGuardHook extends PluginHook implements Configurable {

    public static StateFlag FLIGHT;

    public WorldGuardHook() {
        super(WorldGuardPlugin.inst());
    }

    @Override
    public String getSettingsPath() {
        return mainPath + "." + getPlugin().getName();
    }

    @Override
    public boolean canFlyHere(Location location, Player player) {
        FlightManager flightManager = new FlightManager(player);
        if (flightManager.getFlightType() == FlightPermission.NONE) return false;

        ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(location));
        if (regions != null && regions.size() > 0) {
            LocalPlayer wgPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            StateFlag.State hasAllowFlyTag = regions.queryState(wgPlayer, FLIGHT);
            // flight tag: ALLOW - anyone can fly here with permission (default)
            // DENY - no one can fly here
            if (hasAllowFlyTag != StateFlag.State.DENY) {
                if (flightManager.getFlightType() == FlightPermission.TRUSTED_CLAIMS) {
                    return regions.isMemberOfAll(wgPlayer);
                } else if (flightManager.getFlightType() == FlightPermission.OWN_CLAIMS) {
                    return regions.isOwnerOfAll(wgPlayer);
                }
            }
        }
        return false;
    }

    @Override
    public Object getGenericClaimAt(Location location) {
        Set<ProtectedRegion> regions = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(location)).getRegions();
        // no regions here, return null for the move listener/claim change events
        if (regions.size() < 1) {
            return null;
        }
        return regions;
    }

    public static void loadFlag() {
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        String flagName = "flight";
        try {
            StateFlag flag = new StateFlag(flagName, true);
            registry.register(flag);
            FLIGHT = flag;
            Bukkit.getConsoleSender().sendMessage("Flag enabled!");
        } catch (FlagConflictException exception) {
            Flag<?> existing = registry.get(flagName);
            if (existing instanceof StateFlag) {
                FLIGHT = (StateFlag) existing;
            } else {
                Bukkit.getLogger().severe("Could not register flag!");
            }
        }
    }
}
