package ltd.indigostudios.dynamicfly.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

public class ClaimChangeEvent extends Event {

    private Player player;
    private Plugin plugin;
    private Location from;
    private Location to;

    private static HandlerList handlers = new HandlerList();

    public ClaimChangeEvent(Player player, Plugin plugin, Location from, Location to) {
        this.player = player;
        this.plugin = plugin;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
