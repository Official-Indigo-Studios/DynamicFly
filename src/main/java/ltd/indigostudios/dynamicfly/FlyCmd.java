package ltd.indigostudios.dynamicfly;

import ltd.indigostudios.dynamicfly.api.FlightManager;
import ltd.indigostudios.dynamicfly.listeners.ClaimChangeListener;
import ltd.indigostudios.dynamicfly.api.Utils;
import ltd.indigostudios.dynamicfly.api.enums.Messages;
import ltd.indigostudios.dynamicfly.api.hooks.PluginHook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FlyCmd implements CommandExecutor, TabCompleter {

    public String fly = "fly";

    private final String[] toggles = {"on", "off"};

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player target;
        boolean flightMode;
        // check for reload
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (PluginHook.getMainHook().canReload(sender)) {
                DynamicFly.getInstance().reloadConfig();
                sender.sendMessage(Utils.format(Messages.getPrefix() + "Reloaded DynamicFly!"));
            } else {
                sender.sendMessage(Utils.format("&cSorry, you do not have permission to reload the plugin."));
            }
            return true;
        }
        // get the target
        if (args.length < 2) {
            if (sender instanceof Player) {
                target = (Player) sender;
            } else {
                sender.sendMessage(Utils.format("&cSorry, you cannot use this command on yourself."));
                return true;
            }
        } else {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(Utils.format("&cInvalid player found."));
                return true;
            }
        }
        // get the flight mode
        String modeString = "";
        if (args.length > 0) {
            modeString = args[0];
        }
        flightMode = getFlightMode(modeString, target);

        FlightManager flightManager = new FlightManager(target);
        // if you are trying to toggle yourself, check claim permissions
        if (target.equals(sender) && !flightManager.canFlyHere(target.getLocation())) {
            target.sendMessage(Utils.format("&cSorry, but you do not have permission to fly here."));
            return true;
        }

        // toggle fly
        flightManager.toggleFly(flightMode);
        flightManager.setWantsToFly(flightMode);
        if (flightManager.isFlightToggledOn()) {
            target.sendMessage(Messages.FLY_CMD_ON.getFullMessage());
        } else {
            target.sendMessage(Messages.FLY_CMD_OFF.getFullMessage());
        }
        if (!target.equals(sender)) {
            if (PluginHook.getMainHook().getSetting("force-fly-always-global", Boolean.class)) {
                ClaimChangeListener.getExemptPlayers().add(target);
            }
            sender.sendMessage(Utils.format(Messages.getPrefix() + "Toggled flight " + formattedFlyMode(flightMode) + " &ffor player &e" + target.getName()));
        }
        return true;
    }

    private boolean getFlightMode(String mode, Player player) {
        if (mode.equalsIgnoreCase("on")) {
            return true;
        } else if (mode.equalsIgnoreCase("off")) {
            return false;
        } else { // desired flight mode is opposite of current
            return !player.getAllowFlight();
        }
    }

    private String formattedFlyMode(boolean on) {
        if (on) {
            return Utils.format("&aON");
        }
        return Utils.format("&cOFF");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        Iterable<String> original = null;

        if (args.length == 1) {
            List<String> options = new ArrayList<>(Arrays.asList(toggles));
            if (PluginHook.getMainHook().canReload(sender)) {
                options.add("reload");
            }
            original = options;
        } else if (args.length == 2) {
            if (sender.hasPermission("dynamicfly.command.fly.others") && !args[0].equalsIgnoreCase("reload")) {
                List<String> players = new ArrayList<>();
                Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getName()));
                original = players;
            }
        }

        if (original != null) {
            StringUtil.copyPartialMatches(args[args.length - 1], original, completions);
        }
        Collections.sort(completions);
        return completions;
    }
}
