package ltd.indigostudios.dynamicfly.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ClaimLeaveEvent extends ClaimChangeEvent {

    public ClaimLeaveEvent(Player player, Plugin plugin, Location from, Location to) {
        super(player, plugin, from, to);
    }
}
