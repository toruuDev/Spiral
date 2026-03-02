package use.spiral.utils;

import static use.spiral.Spiral.mc;

public class Utils {
    public static String getClientDir() {
        return mc.runDirectory.toString() + "/Spiral";
    }
}
