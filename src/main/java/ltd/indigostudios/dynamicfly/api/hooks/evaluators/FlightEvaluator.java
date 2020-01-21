package ltd.indigostudios.dynamicfly.api.hooks.evaluators;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface FlightEvaluator {

    boolean canFlyHere(Location location, Player player);

    Object getGenericClaimAt(Location location);

    static FlightEvaluator setFlightEvaluator(Plugin plugin) {
        switch (plugin.getName()) {
            case "GriefPrevention":
                return new GPFlightEvaluator();
            case "WorldGuard":
                return new WGFlightEvaluator();
            case "BentoBox":
                return new BBFlightEvaluator();
            default:
                break;
        }
        return null;
    }
}
