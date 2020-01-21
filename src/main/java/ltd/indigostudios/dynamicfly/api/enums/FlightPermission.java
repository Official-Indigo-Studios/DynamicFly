package ltd.indigostudios.dynamicfly.api.enums;

public enum FlightPermission {

    GLOBAL("global"), TRUSTED_CLAIMS("claims.trusted"), OWN_CLAIMS("claims"), NONE("");

    private final String perm;

    FlightPermission(String perm) {
        this.perm = perm;
    }

    public String getPerm() {
        return perm;
    }

    public String getFullPerm() {
        String permPrefix = "dynamicfly.type.";
        return permPrefix + getPerm();
    }
}
