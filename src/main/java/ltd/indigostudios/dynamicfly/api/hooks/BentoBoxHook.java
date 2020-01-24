package ltd.indigostudios.dynamicfly.api.hooks;

import ltd.indigostudios.dynamicfly.api.FlightManager;
import ltd.indigostudios.dynamicfly.api.enums.FlightPermission;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.database.objects.Island;

import java.util.Optional;

public class BentoBoxHook extends PluginHook implements Configurable {

    public BentoBoxHook() {
        super(BentoBox.getInstance());
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

        Optional<Island> islandOptional = BentoBox.getInstance().getIslands().getIslandAt(location);
        if (islandOptional.isPresent()) {
            Island island = islandOptional.get();
            if (!island.isDeleted()) {
                if (flightManager.getFlightType() == FlightPermission.TRUSTED_CLAIMS) {
                    if (island.getMemberSet() != null) {
                        return island.getMemberSet().contains(player.getUniqueId());
                    }
                } else if (flightManager.getFlightType() == FlightPermission.OWN_CLAIMS) {
                    if (island.getOwner() != null) {
                        return island.getOwner().equals(player.getUniqueId());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Object getGenericClaimAt(Location location) {
        return BentoBox.getInstance().getIslands().getIslandAt(location).orElse(null);
    }
}
