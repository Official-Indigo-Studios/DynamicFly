package ltd.indigostudios.dynamicfly.api.hooks;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface FlightEvaluator {

    boolean canFlyHere(Location location, Player player);

    Object getGenericClaimAt(Location location);
}
