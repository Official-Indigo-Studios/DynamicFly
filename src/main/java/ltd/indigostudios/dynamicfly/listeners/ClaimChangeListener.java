package ltd.indigostudios.dynamicfly.listeners;

import ltd.indigostudios.dynamicfly.api.events.ClaimChangeEvent;
import ltd.indigostudios.dynamicfly.api.FlightManager;
import ltd.indigostudios.dynamicfly.api.enums.Messages;
import ltd.indigostudios.dynamicfly.api.events.ClaimEnterEvent;
import ltd.indigostudios.dynamicfly.api.events.ClaimLeaveEvent;
import ltd.indigostudios.dynamicfly.api.events.ClaimTransitionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class ClaimChangeListener implements Listener {

    private static Set<Player> exemptPlayers = new HashSet<>();

    @EventHandler
    public void onClaimChange(ClaimChangeEvent event) {
        if (!exemptPlayers.contains(event.getPlayer())) {
            FlightManager fm = new FlightManager(event.getPlayer());
            if (fm.canFlyHere(event.getTo())) {
                if (fm.wantsToFly() && !fm.isFlightToggledOn()) {
                    fm.toggleFly(true, false);
                    event.getPlayer().sendMessage(Messages.FLIGHT_ON.getFullMessage());
                }
            } else {
                if (fm.wantsToFly()) {
                    if (fm.isFlightToggledOn()) {
                        event.getPlayer().sendMessage(Messages.FLIGHT_OFF.getFullMessage());
                    }
                    fm.toggleFly(false, false);
                }
            }
        }
    }

    public static Set<Player> getExemptPlayers() {
        return exemptPlayers;
    }
}
