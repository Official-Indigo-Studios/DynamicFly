package ltd.indigostudios.dynamicfly.listeners;

import ltd.indigostudios.dynamicfly.api.events.ClaimEnterEvent;
import ltd.indigostudios.dynamicfly.api.events.ClaimLeaveEvent;
import ltd.indigostudios.dynamicfly.api.events.ClaimTransitionEvent;
import ltd.indigostudios.dynamicfly.api.hooks.PluginHook;
import ltd.indigostudios.dynamicfly.api.events.ClaimChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class PlayerMoveListener implements Listener {

    public static Set<Player> exemptPlayers = new HashSet<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        evaluate(event.getPlayer(), event.getTo(), event.getFrom());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        evaluate(event.getPlayer(), event.getTo(), event.getFrom());
    }

    private void evaluate(Player player, Location to, Location from) {
        // they just rotated, no need to check
        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) return;

        Set<Plugin> plugins = PluginHook.getHookedPlugins();
        for (Plugin plugin : plugins) {
            PluginHook hook = PluginHook.getHook(plugin);
            if (hook.isRegistered()) {
                Object fromClaim = hook.getGenericClaimAt(from);
                Object toClaim = hook.getGenericClaimAt(to);
                // one claim or zero claims can be null (but not both)
                if (!(fromClaim == null && toClaim == null)) {
                    Bukkit.getPluginManager().callEvent(new ClaimChangeEvent(player, plugin, from, to));
                    // one claim is null and the other is not
                    if ((fromClaim == null || toClaim == null)) {
                        if (fromClaim == null) {
                            Bukkit.getPluginManager().callEvent(new ClaimEnterEvent(player, plugin, from, to));
                        } else {
                            Bukkit.getPluginManager().callEvent(new ClaimLeaveEvent(player, plugin, from, to));
                        }
                    } else if (!fromClaim.equals(toClaim)) { // neither are null and they are different claims
                        Bukkit.getPluginManager().callEvent(new ClaimTransitionEvent(player, plugin, from, to));
                    }
                }
            }
        }
    }
}
