package ltd.indigostudios.dynamicfly.api.enums;

import ltd.indigostudios.dynamicfly.api.Utils;

public enum Messages {
    FLIGHT_ON(Utils.format("Your flight has been toggled &aON &fbecause you have permission to fly here.")),
    FLIGHT_OFF(Utils.format("Your flight has been toggled &cOFF &fbecause you do not have permission to fly here.")),
    FLY_CMD_ON(Utils.format("Your flight has been toggled &aON&f.")),
    FLY_CMD_OFF(Utils.format("Your flight has been toggled &cOFF&f."));

    private final String message;
    private static String prefix = Utils.format("&7&l(&e&l!&7&l) &f");

    Messages(String message) {
        this.message = message;
    }

    public String getFullMessage() {
        return prefix + message;
    }

    public static String getPrefix() {
        return prefix;
    }
}
