package pl.mobslayer.util;

import org.bukkit.Location;
import org.bukkit.World;
import pl.mobslayer.MobSlayer;

public class LocationUtil {

    public static Location parseLocation(String locationString) {
        String[] split = locationString.split(" ");
        if(locationString.length() != 4) {
            sendWrongFormatMessage(locationString);
            return null;
        }
        try {
            double x = Double.parseDouble(split[0]);
            double y = Double.parseDouble(split[1]);
            double z = Double.parseDouble(split[2]);
            World w = MobSlayer.getInstance().getServer().getWorld(split[3]);
            if(w == null) {
                sendWrongFormatMessage(locationString);
                return null;
            }
            return new Location(w, x, y, z);
        } catch(NumberFormatException e) {
            sendWrongFormatMessage(locationString);
            return null;
        }
    }

    private static void sendWrongFormatMessage(String locationString) {
        MobSlayer.getInstance().getLogger().severe(locationString + " is in wrong location format!");
    }

}
