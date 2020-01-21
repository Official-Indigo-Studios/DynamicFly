package ltd.indigostudios.dynamicfly.listeners;

import ltd.indigostudios.dynamicfly.api.FlightManager;
import ltd.indigostudios.dynamicfly.api.enums.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOnGround()) {
            if (player.hasPermission("dynamicfly.safelogin")) {
                FlightManager flightManager = new FlightManager(player);
                if (flightManager.canFlyHere(player.getLocation())) {
                    flightManager.setWantsToFly(true);
                    flightManager.toggleFly(true);
                    player.sendMessage(Messages.FLIGHT_ON.getFullMessage());
                } else {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0, true, false));
                }
            }
        }
    }
}
